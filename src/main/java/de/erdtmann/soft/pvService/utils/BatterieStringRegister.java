package de.erdtmann.soft.pvService.utils;

public enum BatterieStringRegister implements ModbusRegister {

	HERSTELLER(517, null, 8);
	
	private int addr;
	private String einheit;
	private int anzahl;
	
	BatterieStringRegister(int addr, String einheit, int anzahl){
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
