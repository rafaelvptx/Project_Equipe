package com.example;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/*
 * Mapping de requetage de l'api
 */
@CrossOrigin(origins = "http://localhost:3000")
@RestController
class MatchRestController {

	private final MatchRepository matchRepository;

	@Autowired
	MatchRestController(MatchRepository matchRepository) {
		this.matchRepository = matchRepository;
	}	

	/**
	 * 
	 * @param equipeHome
	 * @param equipeAway
	 * @param journee
	 * @return match entre deux equipe donnees pour une journee donnee
	 */
	@RequestMapping(method = RequestMethod.GET, value = "/byJournee/{equipeHome}/{equipeAway}")
	Match readMatchStatistique(@PathVariable String equipeHome,@PathVariable String equipeAway,  @RequestParam(value="journee") int journee) {
		return (Match) this.matchRepository.findByNomEquipeHomeAndEquipeAwayAndJournee(equipeHome, equipeAway, journee).toArray()[0];
	}

	
	/**
	 * 
	 * @return liste des ligues presentes en base
	 */
	@RequestMapping(method = RequestMethod.GET, value ="/leagues")
	List<Ligue> readLeagues(){
		List<Ligue> leagues = new ArrayList<>();
		this.matchRepository.findLeagues().forEach(l->{
			leagues.add(new Ligue((String) l));
		});
		return leagues;
	}

	/**
	 * 
	 * @param league
	 * @return liste des saison presentes en base pour une ligue donnee
	 */
	@RequestMapping(method = RequestMethod.GET, value ="/{league}/saisons")
	List<Season> readSeasonsByLeague(@PathVariable String league){
		List<Season> seasons = new ArrayList<>();
		this.matchRepository.findSaisonsByLeague(league).forEach(s->{
			seasons.add(new Season((String) s));
		});
		return seasons;
	}

	/**
	 * 
	 * @param league
	 * @param season
	 * @return liste des journees presentes en base pour une ligue et une saison donnee
	 */
	@RequestMapping(method = RequestMethod.GET, value ="/{league}/{season}/journees")
	List<Day> readDaysByLeagueAndSeason(@PathVariable String league,@PathVariable String season){
		List<Day> days = new ArrayList<>();
		this.matchRepository.findDaysByLeagueAndSeason(league, season).forEach(d->{
			days.add(new Day((String) String.valueOf(d)));
		});
		return days;
	}
	
	@RequestMapping(method = RequestMethod.GET, value ="/{league}/{season}/equipes")
	List<Team> readTeamsByLeagueAndSeason(@PathVariable String league,@PathVariable String season){
		List<Team> teams = new ArrayList<>();
		this.matchRepository.findEquipeHomeByLeagueAndSeason(league, season).forEach(t->{
			teams.add(new Team((String) String.valueOf(t)));
		});
		return teams;
	}


	/**
	 * 
	 * @param league
	 * @param season
	 * @param journee
	 * @return liste des matchs pour une journee d'une saison d'une ligue donnee
	 */
	@RequestMapping(method = RequestMethod.GET, value = "/{league}/{season}/{journee}/matchs")
	List<Resultat> readMatchsByJournees(@PathVariable String league, @PathVariable String season,@PathVariable int journee) {
		List<Resultat> resultats = new ArrayList<>();
		this.matchRepository.findByLigueAndSaisonAndJournee(league, season, journee).forEach(m->{
			resultats.add(new Resultat(
					(int) m.getJournee(),
					(String) m.getNomEquipeHome(),
					(String) m.getEquipeAway(),
					(int) m.getScoreHome(),
					(int) m.getScoreAway(),
					(float) m.getChanceWinHome(),
					(float) m.getChanceWinAway()));
		});
		
		return resultats;
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/{league}/{season}/{equipe}/matchsTeam")
	List<Resultat> readMatchsByEquipes(@PathVariable String league, @PathVariable String season,@PathVariable String equipe) {
		List<Resultat> resultats = new ArrayList<>();
		this.matchRepository.findByLigueAndSaisonAndEquipe(league, season, equipe).forEach(m->{
			resultats.add(new Resultat(
					(int) m.getJournee(),
					(String) m.getNomEquipeHome(),
					(String) m.getEquipeAway(),
					(int) m.getScoreHome(),
					(int) m.getScoreAway(),
					(float) m.getChanceWinHome(),
					(float) m.getChanceWinAway()));
		});
		
		return resultats;
	}

	/**
	 * 
	 * @param equipe
	 * @return liste de tous les matchs a domicile en base d une equipe donnee
	 */
	@RequestMapping(method = RequestMethod.GET, value = "/home/{equipe}")
	Collection<Match> readMatchsHome(@PathVariable String equipe) {
		return this.matchRepository.findByNomEquipeHome(equipe);
	}

	/**
	 * 
	 * @param equipe
	 * @return liste de tous les matchs à l'exterieur en base d'une equipe donnee
	 */
	@RequestMapping(method = RequestMethod.GET, value = "/away/{equipe}")
	Collection<Match> readMatchsAway(@PathVariable String equipe) {
		return this.matchRepository.findByEquipeAway(equipe);
	}
	
	
	/**
	 * 
	 * @param equipe
	 * @return liste de tous les matchs en base d'une equipe donnee
	 */
	@RequestMapping(method = RequestMethod.GET, value = "/all/{equipe}")
	Collection<Match> readMatchsAll(@PathVariable String equipe) {
		return this.matchRepository.findByEquipeAwayOrNomEquipeHome(equipe, equipe);
	}

	/**
	 * 
	 * @param saison
	 * @param journee
	 * @param nom
	 * @return les matchs d'une equipe pour une saison, une journee et une equipe donnee 
	 */
	@RequestMapping(method = RequestMethod.GET, value = "/{saison}/{journee}/{nom}")
	Collection<Match> readByJourneeBefore(@PathVariable String saison,@PathVariable int journee, @PathVariable String nom) {
		return this.matchRepository.findByJourneeBefore(saison,journee, nom);
	}

}