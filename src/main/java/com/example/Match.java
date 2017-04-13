package com.example;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class Match {

    @JsonIgnore
    @ManyToOne
    private Equipe equipeHome;

    @Id
    @GeneratedValue
    private Long id;
    
    private String nomEquipeHome;
	private int scoreHome;
	private String equipeAway;
    private int scoreAway;
    private String resultat;
    private String ligue;
	private int journee;
    private String saison;
	private float chanceWinHome;
    private float chanceWinAway;
    
	public Match() { // jpa only
    }
    
	public Match(Equipe equipeHome, String nomEquipeHome, int scoreHome, String equipeAway, int scoreAway,
			String resultat, int journee, String ligue, String saison) {
		super();
		this.equipeHome = equipeHome;
		this.nomEquipeHome = nomEquipeHome;
		this.scoreHome = scoreHome;
		this.equipeAway = equipeAway;
		this.scoreAway = scoreAway;
		this.resultat = resultat;
		this.ligue = ligue;
		this.journee = journee;
		this.saison = saison;
	}
	
    public Match(Equipe equipeH,String nomEquipeHome, String equipeAdv, int scoreHome, int scoreAway, String resultat, int journee) {
        this.equipeHome = equipeH;
        this.nomEquipeHome = nomEquipeHome;
        this.equipeAway = equipeAdv;
        this.scoreHome = scoreHome;
        this.scoreAway = scoreAway;
        this.resultat = resultat;
        this.journee = journee;
    }
    
    public Match(String nomEquipeHome, String equipeAdv, int scoreHome, int scoreAway, float chanceWinHome, float chanceWinAway) {
        this.nomEquipeHome = nomEquipeHome;
        this.equipeAway = equipeAdv;
        this.scoreHome = scoreHome;
        this.scoreAway = scoreAway;
        this.chanceWinHome = chanceWinHome;
        this.chanceWinAway = chanceWinAway;
    }
    
    public String getLigue() {
		return ligue;
	}

	public void setLigue(String ligue) {
		this.ligue = ligue;
	}

    public String getSaison() {
		return saison;
	}

	public void setSaison(String saison) {
		this.saison = saison;
	}

    public float getChanceWinHome() {
		return chanceWinHome;
	}

	public void setChanceWinHome(float chanceWinHome) {
		this.chanceWinHome = chanceWinHome;
	}

	public float getChanceWinAway() {
		return chanceWinAway;
	}

	public void setChanceWinAway(float chanceWinAway) {
		this.chanceWinAway = chanceWinAway;
	}

    public int getJournee() {
		return journee;
	}

	public void setJournee(int journee) {
		this.journee = journee;
	}

	public String getNomEquipeHome() {
		return nomEquipeHome;
	}

	public void setNomEquipeHome(String nomEquipeHome) {
		this.nomEquipeHome = nomEquipeHome;
	}

    public Long getId() {
        return id;
    }

	public Equipe getEquipeHome() {
		return equipeHome;
	}

	public void setEquipeHome(Equipe equipeHome) {
		this.equipeHome = equipeHome;
	}

	public String getEquipeAway() {
		return equipeAway;
	}

	public void setEquipeAway(String equipeAway) {
		this.equipeAway = equipeAway;
	}

	public int getScoreHome() {
		return scoreHome;
	}

	public void setScoreHome(int scoreHome) {
		this.scoreHome = scoreHome;
	}

	public int getScoreAway() {
		return scoreAway;
	}

	public void setScoreAway(int scoreAway) {
		this.scoreAway = scoreAway;
	}

	public String getResultat() {
		return resultat;
	}

	public void setResultat(String resultat) {
		this.resultat = resultat;
	}
	
	public String toString(){
		return getEquipeHome().getNomEquipe() + " " + getEquipeAway() +" "+  getScoreHome() +" "+ getScoreAway() +" "+ getResultat() + 
				" " + getChanceWinHome() + " " + getChanceWinAway() + " " + getJournee() + " " + getSaison();
	}
    
    

}