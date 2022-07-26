package de.erdtmann.soft.pvService.utils;

public enum BatterieFloatRegister implements ModbusRegister {

	BATT_ZYKLUS(194, "", 2),
	BATT_STROM(200, "A", 2),
	BATT_SPANNUNG(216, "V", 2),
	BATT_TEMP(214, "°C", 2),
	BATT_STAND(210, "%", 2),
	VERBRAUCH_FROM_BAT(106, "W", 2);

	private int addr;
	private String einheit;
	private int anzahl;
	
	private BatterieFloatRegister(int addr, String einheit, int anzahl) {
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
