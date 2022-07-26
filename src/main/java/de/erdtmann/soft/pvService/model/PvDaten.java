package de.erdtmann.soft.pvService.model;

import java.io.Serializable;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;

public class PvDaten implements Serializable {

	private static final long serialVersionUID = -5226816382430205430L;

	private Phase dcString1;
	private Phase dcString2;
	private float gesamtLeistung;
	
	DecimalFormat df;
	DecimalFormatSymbols symbols;

	public static Builder builder() {
		return new Builder();
	}

	public PvDaten(Builder builder) {
		symbols = new DecimalFormatSymbols();
		symbols.setMinusSign(' ');
		df = new DecimalFormat("#", symbols);

		setGesamtLeistung(builder.gesamtLeistung);
		setDcString1(builder.dcString1);
		setDcString2(builder.dcString2);
	}
	
	public float getGesamtLeistung() {
		return gesamtLeistung;
	}
	public String getGesamtLeistungStr() {
		if (gesamtLeistung < 0) {
			return "0";
		} else {
			return df.format(gesamtLeistung);
		}
	}
	
	public Phase getDcString1() {
		return dcString1;
	}
	public Phase getDcString2() {
		return dcString2;
	}

	private void setGesamtLeistung(float gesamtLeistung) {
		this.gesamtLeistung = gesamtLeistung;
	}

	private void setDcString1(Phase dcString1) {
		this.dcString1 = dcString1;
	}
	
	private void setDcString2(Phase dcString2) {
		this.dcString2 = dcString2;
	}

	public static final class Builder {
		private float gesamtLeistung;
		private Phase dcString1;
		private Phase dcString2;

		private Builder() {	}

		public Builder withGesamtLeistung(float gesamtLeistung) {
			this.gesamtLeistung = gesamtLeistung;
			return this;
		}
		
		public Builder withDcString1(Phase dcString1) {
			this.dcString1 = dcString1;
			return this;
		}
		public Builder withDcString2(Phase dcString2) {
			this.dcString2 = dcString2;
			return this;
		}
		
		public PvDaten build() {
			return new PvDaten(this);
		}
	}
}
