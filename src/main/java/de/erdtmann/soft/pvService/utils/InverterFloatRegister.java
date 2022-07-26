package de.erdtmann.soft.pvService.utils;

public enum InverterFloatRegister implements ModbusRegister {

	AC_A_1(154, "A", 2),
	AC_W_1(156, "W", 2),
	AC_V_1(158, "V", 2),
	AC_A_2(160, "A", 2),
	AC_W_2(162, "W", 2),
	AC_V_2(164, "V", 2),
	AC_A_3(166, "A", 2),
	AC_W_3(168, "W", 2),
	AC_V_3(170, "V", 2);
	
	private int addr;
	private String einheit;
	private int anzahl;
	
	InverterFloatRegister(int addr, String einheit, int anzahl){
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
