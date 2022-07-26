package de.erdtmann.soft.pvService.model;

public class PvHome {

	private String leistung;
	private String battLadung;
	private String battRichtung;
	private String battLeistung;
	private String home;
	private String netz;
	private String netzRichtung;
	
	

	public PvHome(String leistung, String battLadung, String battRichtung, String battLeistung, String home,
			String netz, String netzRichtung) {
		super();
		this.leistung = leistung;
		this.battLadung = battLadung;
		this.battRichtung = battRichtung;
		this.battLeistung = battLeistung;
		this.home = home;
		this.netz = netz;
		this.netzRichtung = netzRichtung;
	}
	
	public String getLeistung() {
		return leistung;
	}
	public void setLeistung(String leistung) {
		this.leistung = leistung;
	}
	public String getBattLadung() {
		return battLadung;
	}
	public void setBattLadung(String battLadung) {
		this.battLadung = battLadung;
	}
	public String getBattLeistung() {
		return battLeistung;
	}
	public void setBattLeistung(String battLeistung) {
		this.battLeistung = battLeistung;
	}
	public String getHome() {
		return home;
	}
	public void setHome(String home) {
		this.home = home;
	}
	public String getNetz() {
		return netz;
	}
	public void setNetz(String netz) {
		this.netz = netz;
	}
	public String getBattRichtung() {
		return battRichtung;
	}
	public void setBattRichtung(String battRichtung) {
		this.battRichtung = battRichtung;
	}
	public String getNetzRichtung() {
		return netzRichtung;
	}
	public void setNetzRichtung(String netzRichtung) {
		this.netzRichtung = netzRichtung;
	}
	
	
}
