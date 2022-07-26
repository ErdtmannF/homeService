package de.erdtmann.soft.pvService;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import de.erdtmann.soft.pvService.exceptions.PvException;
import de.erdtmann.soft.pvService.model.PvHome;
import de.erdtmann.soft.pvService.utils.BatterieFloatRegister;
import de.erdtmann.soft.pvService.utils.NetzFloatRegister;
import de.erdtmann.soft.pvService.utils.PvFloatRegister;

@ApplicationScoped
public class PvService {

	@Inject
	PvModbusClient pvModbusClient;
	
	DecimalFormat df;
	DecimalFormat dfNachkomma;
	DecimalFormatSymbols symbols;

	public PvHome ladePvHomeDaten() throws PvException {
		float leistung = 999;
		float battStand = 999;
		String battRichtung = "rechts";
		float battLeistung = 999;
		float home = 999;
		float netz = 999;
		String netzRichtung = "links";

		symbols = new DecimalFormatSymbols();
		symbols.setMinusSign(' ');
		symbols.setDecimalSeparator(',');
		df = new DecimalFormat("#", symbols);
		dfNachkomma = new DecimalFormat("####.##", symbols);

		
		if (pvModbusClient != null) {
			float pv1 = pvModbusClient.holeModbusRegisterFloat(PvFloatRegister.DC_W_1);
			float pv2 = pvModbusClient.holeModbusRegisterFloat(PvFloatRegister.DC_W_2);
			leistung = pv1 + pv2;
		
			battStand = pvModbusClient.holeModbusRegisterFloat(BatterieFloatRegister.BATT_STAND);
			
			float battStrom = pvModbusClient.holeModbusRegisterFloat(BatterieFloatRegister.BATT_STROM);
			float battSpannung = pvModbusClient.holeModbusRegisterFloat(BatterieFloatRegister.BATT_SPANNUNG);
			battLeistung = battStrom * battSpannung;
			if (battLeistung > 0) {
				battRichtung = "rechts";
			} else {
				battRichtung = "links";
			}

			float verbrauchBatt = pvModbusClient.holeModbusRegisterFloat(BatterieFloatRegister.VERBRAUCH_FROM_BAT);
			float verbrauchPv = pvModbusClient.holeModbusRegisterFloat(PvFloatRegister.VERBRAUCH_FROM_PV);
			float verbrauchGrid = pvModbusClient.holeModbusRegisterFloat(NetzFloatRegister.VERBRAUCH_FROM_GRID);
			home = verbrauchBatt + verbrauchGrid + verbrauchPv;
			
			netz = pvModbusClient.holeModbusRegisterFloat(NetzFloatRegister.GRID_LEISTUNG);	
			if (netz > 0) {
				netzRichtung = "links";
				
			} else {
				netzRichtung = "rechts";
			}
		}
		PvHome pvHome = new PvHome(df.format(leistung) + " W", 
									df.format(battStand) + " %", 
									battRichtung, 
									df.format(battLeistung) + " W", 
									df.format(home) + " W", 
									df.format(netz) + " W", 
									netzRichtung);
		return pvHome;
	}
	
}
