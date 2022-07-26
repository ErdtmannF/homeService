package de.erdtmann.soft.pvService.utils;

public enum NetzFloatRegister implements ModbusRegister {

	VERBRAUCH_FROM_GRID(108, "W", 2),
	GRID_A_1(222, "A", 2),
	GRID_W_1(224, "W", 2),
	GRID_V_1(230, "V", 2),
	GRID_A_2(232, "A", 2),
	GRID_W_2(234, "W", 2),
	GRID_V_2(240, "V", 2),
	GRID_A_3(242, "A", 2),
	GRID_W_3(244, "W", 2),
	GRID_V_3(250, "V", 2),
	GRID_LEISTUNG(252, "W", 2);

	private int addr;
	private String einheit;
	private int anzahl;
	
	NetzFloatRegister(int addr, String einheit, int anzahl){
		this.addr = addr;
		this.einheit = einheit;
		this.anzahl = anzahl;
	}
	
	public int getAddr() {
		return addr;
	}
	public String getEinheit() {
		return einheit;
	}
	public int getAnzahl() {
		return anzahl;
	}

}
