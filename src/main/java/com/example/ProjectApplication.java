package com.example;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Collection;

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


	/**
	 * Met en base tous les fichiers contenus dans le fichier csv
	 * puis lance l'algorithme de calcul de probabilités de victoire
	 * pour chaque matchs
	 * 
	 * 
	 * @param matchsRepository
	 * @return null
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	@Bean
	CommandLineRunner init(MatchRepository matchsRepository) throws FileNotFoundException, IOException {


		new CsvReader().CsvReaderTest().forEach((match)->{
			matchsRepository.save(new Match(match.getNomEquipeHome(), match.getScoreHome(), match.getEquipeAway(), match.getScoreAway(), match.getResultat(), match.getJournee(),match.getLigue() ,match.getSaison()));
		});			

		matchsRepository.findAll().forEach(m ->{

			calculatePronostic(m, matchsRepository);

			matchsRepository.save(m);
		});

		return null;
	}



	/**
	 * 
	 * @param m
	 * @param matchRepository
	 */
	private void calculatePronostic(Match m, MatchRepository matchRepository){

		int journee = m.getJournee();
		String teamHome = m.getNomEquipeHome();
		String teamAway = m.getEquipeAway();
		String saison = m.getSaison();
		String ligue = m.getLigue();

		//indiceSaison eqA
		float pourPerfSaisonA = calculPourcentagePerformance(matchRepository.findByJourneeBefore(saison,journee, teamHome), teamHome);
		//indiceLieu eqA
		float pourPerfLieuA = calculPourcentagePerformance(matchRepository.findByJourneeBeforeHome(saison, journee, teamHome), teamHome);	
		//indiceForme eqA
		float pourPerfFormeA = calculPourcentagePerformance(matchRepository.findBySaisonAndJourneeAndEquipes(saison, journee-5,journee-1, teamHome), teamHome);
		//indiceConfrontation eqA
		float pourPerfConfrA = calculPourcentagePerformance(matchRepository.findConfrontation(ligue, saison, teamHome, teamAway), teamHome);

		//IndiceSaison eqB
		float pourPerfSaisonB = calculPourcentagePerformance(matchRepository.findByJourneeBefore(saison,journee, teamAway), teamAway);
		//IndiceLieu eqB
		float pourPerfLieuB = calculPourcentagePerformance(matchRepository.findByJourneeBeforeAway(saison, journee, teamAway), teamAway);
		//indiceForme eqB
		float pourPerfFormeB = calculPourcentagePerformance(matchRepository.findBySaisonAndJourneeAndEquipes(saison, journee-5,journee-1, teamAway), teamAway);
		//indiceConfrontation eqB
		float pourPerfConfrB = calculPourcentagePerformance(matchRepository.findConfrontation(ligue, saison, teamAway, teamHome), teamAway);


		/**
		 * L'indice permet de donner une note sur l'état de l'équipe en fonction des critères choisis
		 */
		float indiceSaison = Math.min(((pourPerfSaisonA-pourPerfSaisonB)*5)+5, 10);
		if(Float.isNaN(indiceSaison)){
			indiceSaison = 5.0f;
		}
		float indiceLieu = Math.min(((pourPerfLieuA-pourPerfLieuB)*5)+5, 10);
		if(Float.isNaN(indiceLieu)){
			indiceLieu = 5.0f;
		}
		float indiceForme = Math.min(((pourPerfFormeA-pourPerfFormeB)*5)+5, 10);
		if(Float.isNaN(indiceForme)){
			indiceForme = 5.0f;
		}
		float indiceConfront = (2*pourPerfConfrA + pourPerfConfrB)/3;
		if(Float.isNaN(indiceConfront)){
			indiceConfront = 5.0f;
		}

		/*
		 * L'algo permet de faire le lien entre les indices à l'aide de coefficients plus ou moins important 
		 * dans le but d'obtenir un % de Victoire probable
		 */
		float chanceWinHome = (indiceSaison + 2 * indiceLieu + 2 * indiceConfront + 3 * indiceForme)/8;



		m.setChanceWinHome(chanceWinHome * 10);
		m.setChanceWinAway((10-chanceWinHome)*10);

	}

	/**
	 * Le calcul du % de performance se fait en divisant le nombre de points pris sur la période choisi 
	 * par le nombre de point max possible (sur cette période)
	 * 
	 * @param listMatchs
	 * @param team
	 * @return
	 */

	private float calculPourcentagePerformance(Collection<Match> listMatchs, String team) {
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
		return (float) pts/compteurPossible;
	}
}