
package com.example;

public class Resultat {

	private int day;
	private String teamHome;
	private String teamAway;
	private int scoreHome;
	private int scoreAway;
	private float chanceWinHome;
	private float chanceWinAway;

    public Resultat(int day, String nomEquipeHome, String equipeAdv, int scoreHome, int scoreAway, float chanceWinHome, float chanceWinAway) {
    	this.day = day;
    	this.teamHome = nomEquipeHome;
        this.teamAway = equipeAdv;
        this.scoreHome = scoreHome;
        this.scoreAway = scoreAway;
        this.chanceWinHome = chanceWinHome;
        this.chanceWinAway = chanceWinAway;
    }

	public int getDay() {
		return day;
	}

	public void setDay(int day) {
		this.day = day;
	}

	public String getTeamHome() {
		return teamHome;
	}

	public void setTeamHome(String teamHome) {
		this.teamHome = teamHome;
	}

	public String getTeamAway() {
		return teamAway;
	}

	public void setTeamAway(String teamAway) {
		this.teamAway = teamAway;
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
}
