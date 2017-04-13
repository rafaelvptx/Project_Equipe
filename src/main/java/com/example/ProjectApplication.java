package com.example;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import csv.CsvReader;

@SpringBootApplication
public class ProjectApplication {
	
	public static void main(String[] args) throws FileNotFoundException, IOException {
		SpringApplication.run(ProjectApplication.class, args);
	}

	@Bean
	CommandLineRunner init(EquipeRepository accountRepository, MatchRepository matchsRepository) throws FileNotFoundException, IOException {
		
		Map<String, List<Match>> matchs = new CsvReader().CsvReaderTest();
		matchs.forEach((k,v)->{
			Equipe equipe = accountRepository.save(new Equipe(k));
			v.forEach((match)->{
				matchsRepository.save(new Match(equipe, match.getNomEquipeHome(), match.getScoreHome(), match.getEquipeAway(), match.getScoreAway(), match.getResultat(), match.getJournee(),match.getLigue() ,match.getSaison()));
			});			
		});
		
		matchsRepository.findAll().forEach(m ->{
			calculatePronostic(m, matchsRepository);
			matchsRepository.save(m);
		});

		return null;
	}
	
	private void calculatePronostic(Match m, MatchRepository matchRepository){

		int journee = m.getJournee();
		String teamHome = m.getNomEquipeHome();
		//System.out.println(teamHome);
		String teamAway = m.getEquipeAway();
		//System.out.println(teamAway);
		String saison = m.getSaison();
		//System.out.println(saison);

		//indiceSaison eqA
		float pourPerfSaisonA = calculIndice(matchRepository.findByJourneeBefore(saison,journee, teamHome), teamHome);
		//System.out.println("pourperfsaisonA:"+ pourPerfSaisonA);
		//indiceLieu eqA
		float pourPerfLieuA = calculIndice(matchRepository.findByJourneeBeforeHome(saison, journee, teamHome), teamHome);	
		//System.out.println("pourPerfLieuA: "+pourPerfLieuA);
		//indiceForme eqA
		float pourPerfFormeA = calculIndice(matchRepository.findBySaisonAndJourneeAndEquipes(saison, journee-5,journee-1, teamHome), teamHome);
		//System.out.println("pourPerfFormeA: "+pourPerfFormeA);

		//IndiceSaison eqB
		float pourPerfSaisonB = calculIndice(matchRepository.findByJourneeBefore(saison,journee, teamAway), teamAway);
		//System.out.println("pourPerfSaisonB: "+pourPerfSaisonB);
		//IndiceLieu eqB
		float pourPerfLieuB = calculIndice(matchRepository.findByJourneeBeforeAway(saison, journee, teamAway), teamAway);
		//System.out.println("pourPerfLieuB: "+pourPerfLieuB);
		//indiceForme eqB
		float pourPerfFormeB = calculIndice(matchRepository.findBySaisonAndJourneeAndEquipes(saison, journee-5,journee-1, teamAway), teamAway);
		//System.out.println("pourPerfFormeB: "+pourPerfFormeB);
		
		
		float indiceSaison = Math.min(((pourPerfSaisonA-pourPerfSaisonB)*10)+5, 10);
		float indiceLieu = Math.min(((pourPerfLieuA-pourPerfLieuB)*10)+5, 10);
		float indiceForme = Math.min(((pourPerfFormeA-pourPerfFormeB)*10)+5, 10);

		float chanceWinHome = (indiceSaison + 2 * indiceLieu + 3 * indiceForme)/6;
		
		m.setChanceWinHome(chanceWinHome * 10);
		m.setChanceWinAway((10-chanceWinHome)*10);
		
		System.out.println("Match : " + m + " " + indiceSaison + "//" + indiceLieu + "//" + indiceForme);
		
	}
	
	
	private float calculIndice(Collection<Match> listMatchs, String team) {
		// TODO Auto-generated method stub
		int pts=0;
		int compteurPossible = 0;
		for (Match aMatch : listMatchs) {
			if("Victoire".equals(aMatch.getResultat()) && team.equals(aMatch.getNomEquipeHome())){
				pts += 3;
			}else if("Defaite".equals(aMatch.getResultat()) && team.equals(aMatch.getEquipeAway())){
				pts += 3;
			}else if("Nul".equals(aMatch.getResultat())){
				pts += 1;
			}
			compteurPossible += 3;
		}
		//System.out.println("pts"+pts);
		//System.out.println("compteurpossible"+compteurPossible);
		
		return (float) pts/compteurPossible;

	}
}