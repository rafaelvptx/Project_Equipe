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

@CrossOrigin(origins = "http://localhost:3000")
@RestController
class MatchRestController {

	private final MatchRepository matchRepository;

	private final EquipeRepository equipeRepository;

	@Autowired
	MatchRestController(MatchRepository matchRepository,
			EquipeRepository equipeRepository) {
		this.matchRepository = matchRepository;
		this.equipeRepository = equipeRepository;
	}	

	@RequestMapping(method = RequestMethod.GET, value = "/byJournee/{equipeHome}/{equipeAway}")
	Match readMatchStatistique(@PathVariable String equipeHome,@PathVariable String equipeAway,  @RequestParam(value="journee") int journee) {
		return (Match) this.matchRepository.findByEquipeHomeNomAndEquipeAwayAndJournee(equipeHome, equipeAway, journee).toArray()[0];
	}

	@RequestMapping(method = RequestMethod.GET, value ="/leagues")
	List<Ligue> readLeagues(){
		List<Ligue> leagues = new ArrayList<>();
		this.matchRepository.findLeagues().forEach(l->{
			leagues.add(new Ligue((String) l));
		});
		return leagues;
	}

	@RequestMapping(method = RequestMethod.GET, value ="/{league}/saisons")
	List<Season> readSeasonsByLeague(@PathVariable String league){
		List<Season> seasons = new ArrayList<>();
		this.matchRepository.findSaisonsByLeague(league).forEach(s->{
			seasons.add(new Season((String) s));
		});
		return seasons;
	}

	@RequestMapping(method = RequestMethod.GET, value ="/{league}/{season}/journees")
	List<Day> readDaysByLeagueAndSeason(@PathVariable String league,@PathVariable String season){
		List<Day> days = new ArrayList<>();
		this.matchRepository.findDaysByLeagueAndSeason(league, season).forEach(d->{
			days.add(new Day((String) String.valueOf(d)));
		});
		return days;
	}


	@RequestMapping(method = RequestMethod.GET, value = "/{league}/{season}/{journee}/matchs")
	List<Resultat> readMatchsByJournees(@PathVariable String league, @PathVariable String season,@PathVariable int journee) {
		List<Resultat> resultats = new ArrayList<>();
		this.matchRepository.findByLigueAndSaisonAndJournee(league, season, journee).forEach(m->{
			resultats.add(new Resultat(
					(String) m.getNomEquipeHome(),
					(String) m.getEquipeAway(),
					(int) m.getScoreHome(),
					(int) m.getScoreAway(),
					(float) m.getChanceWinHome(),
					(float) m.getChanceWinAway()));
		});
		
		return resultats;
	}

	@RequestMapping(method = RequestMethod.GET, value = "/home/{equipe}")
	Collection<Match> readMatchsHome(@PathVariable String equipe) {
		return this.matchRepository.findByEquipeHomeNom(equipe);
	}

	@RequestMapping(method = RequestMethod.GET, value = "/away/{equipe}")
	Collection<Match> readMatchsAway(@PathVariable String equipe) {
		return this.matchRepository.findByEquipeAway(equipe);
	}

	@RequestMapping(method = RequestMethod.GET, value = "/all/{equipe}")
	Collection<Match> readMatchsAll(@PathVariable String equipe) {
		return this.matchRepository.findByEquipeAwayOrEquipeHomeNom(equipe, equipe);
	}


	@RequestMapping(method = RequestMethod.GET, value = "/{saison}/{journee}/{nom}")
	Collection<Match> readByJourneeBefore(@PathVariable String saison,@PathVariable int journee, @PathVariable String nom) {
		return this.matchRepository.findByJourneeBefore(saison,journee, nom);
	}

}