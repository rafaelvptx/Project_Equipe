package com.example;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface MatchRepository extends JpaRepository<Match, Long> {
    Collection<Match> findByNomEquipeHome(String nom);
    Collection<Match> findByEquipeAway(String nom);
    Collection<Match> findByEquipeAwayOrNomEquipeHome(String nom, String nomAway);
    Collection<Match> findByNomEquipeHomeAndEquipeAwayAndJournee(String nom, String nomAway, int journee);

    
    @Query("select m from Match m " +
            "where ( m.saison = ?1 and m.journee between ?2 and ?3 and m.nomEquipeHome = ?4 ) "
            +  "or ( m.saison = ?1 and m.journee between ?2 and ?3 and m.equipeAway = ?4 )")
    Collection<Match> findBySaisonAndJourneeAndEquipes(String saison, int debut, int fin, String nom);
    
    @Query("select m from Match m " +
    		"where (m.saison = ?1 and m.journee < ?2 and m.nomEquipeHome = ?3) " +
    		"or (m.saison = ?1 and m.journee < ?2 and m.equipeAway = ?3)")
    Collection<Match> findByJourneeBefore(String saison,int journee, String nom);
    
    @Query("select m from Match m " +
    		"where m.saison = ?1 and m.journee < ?2 and m.nomEquipeHome = ?3")
    Collection<Match> findByJourneeBeforeHome(String saison, int journee, String nom);
    
    @Query("select m from Match m " +
    		"where m.saison = ?1 and m.journee < ?2 and m.equipeAway = ?3")
    Collection<Match> findByJourneeBeforeAway(String saison, int journee, String nom);

    @Query("select DISTINCT m.ligue from Match m ")
    Collection<Object> findLeagues(	);
    
    @Query("select DISTINCT m.saison from Match m where m.ligue = ?1")
    Collection<Object> findSaisonsByLeague(String league);
    
    @Query("select DISTINCT m.journee from Match m " +
    		"where m.ligue = ?1 and m.saison = ?2")
    Collection<Object> findDaysByLeagueAndSeason(String league, String saison);
    
    Collection<Match> findByLigueAndSaisonAndJournee(String ligue, String saison, int journee);
    
    
    
}