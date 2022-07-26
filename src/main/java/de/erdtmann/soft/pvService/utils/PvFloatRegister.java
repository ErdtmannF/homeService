package de.erdtmann.soft.pvService.utils;

public enum PvFloatRegister implements ModbusRegister {

	DC_A_1(258, "A", 2),
	DC_W_1(260, "W", 2),
	DC_V_1(266, "V", 2),
	DC_A_2(268, "A", 2),
	DC_W_2(270, "W", 2),
	DC_V_2(276, "V", 2),
	VERBRAUCH_FROM_PV(116, "W", 2),
	TOTAL_DC_LEISTUNG(100, "W", 2);

	private int addr;
	private String einheit;
	private int anzahl;
	
	PvFloatRegister(int addr, String einheit, int anzahl){
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
