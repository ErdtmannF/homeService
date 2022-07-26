package de.erdtmann.soft.pvService.exceptions;

public class PvException extends Exception {

	private static final long serialVersionUID = 7645909342126646834L;

	public PvException() {}
	
	public PvException(String message) {
		super(message);
	}
}
