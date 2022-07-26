package de.erdtmann.soft.pvService;

import java.io.IOException;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;

import org.jboss.logging.Logger;

import de.erdtmann.soft.pvService.exceptions.PvException;
import de.erdtmann.soft.pvService.utils.ModbusRegister;
import de.erdtmann.soft.pvService.utils.PvConstants;
import de.re.easymodbus.exceptions.ModbusException;
import de.re.easymodbus.modbusclient.ModbusClient;

@ApplicationScoped
public class PvModbusClient {

	Logger log = Logger.getLogger(PvModbusClient.class);

	private ModbusClient modbus;

	@PostConstruct
	private void init() {

		// Modbus initialisieren
		modbus = new ModbusClient(PvConstants.INVERTER_URL, PvConstants.INVERTER_PORT);
		// UnitID dem Client mitteilen
		modbus.setUnitIdentifier((byte) PvConstants.INVERTER_UNIT_ID);
	}

	private int[] holeHoldingRegister(ModbusRegister register) throws IOException, ModbusException {

		return modbus.ReadHoldingRegisters(register.getAddr(), register.getAnzahl());
	}

	public String holeModbusRegisterString(ModbusRegister register) throws PvException {

		String wert = null;

		try {
			if (!modbus.isConnected()) {
				// Verbindung aufbauen

				modbus.Connect();
			}

			wert = ModbusClient.ConvertRegistersToString(holeHoldingRegister(register), 0, register.getAnzahl());

		} catch (Exception e) {
			throw new PvException("Fehler beim Abruf des Modbus String: " + e.getMessage());
		} finally {
			try {
				modbus.Disconnect();
			} catch (IOException e) {
				throw new PvException("Fehler beim Schlieﬂen der Modbus-Verbindung: " + e.getMessage());
			}
		}

		return wert;
	}

	public Float holeModbusRegisterFloat(ModbusRegister register) throws PvException {
		Float wert = null;
		
		try {
			if (!modbus.isConnected()) {
				modbus.Connect();
			}
			wert = ModbusClient.ConvertRegistersToFloat(holeHoldingRegister(register));

		} catch (Exception e) {
			throw new PvException("Fehler beim Abruf des Modbus Float: " + e.getMessage());
		} finally {
			try {
				modbus.Disconnect();
			} catch (Exception e) {
				throw new PvException("Fehler beim Schlieﬂen der Modbus-Verbindung: " + e.getMessage());
			}
		}

		return wert;
	}

}
