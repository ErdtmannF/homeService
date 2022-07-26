package de.erdtmann.soft.pvService.model;

import java.io.Serializable;

public class Phase implements Serializable {

	private static final long serialVersionUID = 6637483444576793641L;

	private float strom;
	private float spannung;
	private float leistung;
	
	public static Builder builder() {
		return new Builder();
	}

	public Phase(Builder builder) {
		setStrom(builder.strom);
		setSpannung(builder.spannung);
		setLeistung(builder.leistung);
	}

	public float getStrom() {
		return strom;
	}
	public void setStrom(float strom) {
		this.strom = strom;
	}
	public float getSpannung() {
		return spannung;
	}
	public void setSpannung(float spannung) {
		this.spannung = spannung;
	}
	public float getLeistung() {
		return leistung;
	}
	public void setLeistung(float leistung) {
		this.leistung = leistung;
	}
	
	public static final class Builder {
		private float strom;
		private float spannung;
		private float leistung;

		private Builder() {	}

		public Builder withStrom(float strom) {
			this.strom = strom;
			return this;
		}
		public Builder withSpannung(float spannung) {
			this.spannung = spannung;
			return this;
		}
		public Builder withLeistung(float leistung) {
			this.leistung = leistung;
			return this;
		}
		
		public Phase build() {
			return new Phase(this);
		}
	}

}
