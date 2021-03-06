package com.example;

import java.util.Collection;
import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface MatchRepository extends JpaRepository<Match, Long> {
	
	/*
	 * Méthodes en lien avec le modèle JPA interprete par hibernate comme des requetes SQL
	 */
    Collection<Match> findByNomEquipeHome(String nom);
    Collection<Match> findByEquipeAway(String nom);
    Collection<Match> findByEquipeAwayOrNomEquipeHome(String nom, String nomAway);
    Collection<Match> findByNomEquipeHomeAndEquipeAwayAndJournee(String nom, String nomAway, int journee);
    Collection<Match> findByLigueAndSaisonAndJournee(String ligue, String saison, int journee);

    /*
     * Utilisation de l'annotation @Query afin de créer des requetes de la base SQL un peu plus complexes
     */
    
    @Query("select m from Match m " +
            "where ( m.saison = ?1 and m.journee < ?2 and m.nomEquipeHome = ?3 and m.resultat!='Non joue' ) "
            +  "or ( m.saison = ?1 and m.journee < ?2 and m.equipeAway = ?3 and m.resultat!='Non joue' )"
            + "ORDER BY m.journee DESC")
    List<Match> findBySaisonAndJourneeAndEquipes(String saison, int journee, String nom, Pageable page);
    
    @Query("select m from Match m " +
    		"where (m.saison = ?1 and m.journee < ?2 and m.nomEquipeHome = ?3 and m.resultat!='Non joue') " +
    		"or (m.saison = ?1 and m.journee < ?2 and m.equipeAway = ?3 and m.resultat!='Non joue')")
    Collection<Match> findByJourneeBefore(String saison,int journee, String nom);
    
    @Query("select m from Match m " +
    		"where m.saison = ?1 and m.journee < ?2 and m.nomEquipeHome = ?3 and m.resultat!='Non joue'")
    Collection<Match> findByJourneeBeforeHome(String saison, int journee, String nom);
    
    @Query("select m from Match m " +
    		"where m.saison = ?1 and m.journee < ?2 and m.equipeAway = ?3")
    Collection<Match> findByJourneeBeforeAway(String saison, int journee, String nom);

    @Query("select DISTINCT m.ligue from Match m ")
    Collection<Object> findLeagues(	);
    
    @Query("select DISTINCT m.saison from Match m where m.ligue = ?1 ORDER BY m.saison DESC")
    Collection<Object> findSaisonsByLeague(String league);
    
    @Query("select DISTINCT m.journee from Match m " +
    		"where m.ligue = ?1 and m.saison = ?2 ORDER BY m.journee DESC")
    Collection<Object> findDaysByLeagueAndSeason(String league, String saison);
    
    @Query("select DISTINCT m.nomEquipeHome from Match m " +
    		"where m.ligue = ?1 and m.saison = ?2 ORDER BY m.nomEquipeHome")
    Collection<Object> findEquipeHomeByLeagueAndSeason(String league, String saison);
    
    
    @Query("select m from Match m " +
            "WHERE (( m.ligue = ?1 and m.saison = ?2 and m.nomEquipeHome = ?3 ) "
            +  "OR ( m.ligue = ?1 and m.saison = ?2 and m.equipeAway = ?3 )) ORDER BY m.journee")
    Collection<Match> findByLigueAndSaisonAndEquipe(String ligue, String saison, String equipe);
    
    
    @Query("select m from Match m " +
    		"where ( m.ligue = ?1 and m.saison < ?2 and m.nomEquipeHome = ?3 AND m.equipeAway = ?4)")
    Collection<Match> findConfrontation(String ligue, String saison, String equipeH, String equipeA);
    
    
}