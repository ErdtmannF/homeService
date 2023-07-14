package de.erdtmann.soft.homeService.utils;

public enum Heizung {
	AN(true, "/ein"), AUS(false, "/aus");
	
	private boolean an;
	private String restPfad;
	
	Heizung(boolean an, String restPfad) {
		this.an = an;
		this.restPfad = restPfad;
	}

	public boolean isAn() {
		return an;
	}

	public String getRestPfad() {
		return restPfad;
	}

}
