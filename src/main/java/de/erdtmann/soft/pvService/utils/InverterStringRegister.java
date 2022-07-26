package de.erdtmann.soft.pvService.utils;

public enum InverterStringRegister implements ModbusRegister {

	NAME(768, null, 32),
	NR(800, null, 32),
	ARTIKEL_NR(6, null, 8),
	SERIEN_NR(14, null, 16);

	private int addr;
	private String einheit;
	private int anzahl;
	
	InverterStringRegister(int addr, String einheit, int anzahl){
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
