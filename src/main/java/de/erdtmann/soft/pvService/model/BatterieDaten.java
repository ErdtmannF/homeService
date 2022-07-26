package de.erdtmann.soft.pvService.model;

import java.io.Serializable;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;

public class BatterieDaten implements Serializable {

	private static final long serialVersionUID = -1691387580771890875L;

	private float ladeZyklen;
	private float ladeStrom;
	private float spannung;
	private float temperatur;
	private float ladeStand;
	private float leistung;
	private String icon = "battery";
	private String richtung = "left";
	private String farbe = "rot";
	private String ladung = "Wird entladen";

	DecimalFormat df;
	DecimalFormat dfNachkomma;
	DecimalFormatSymbols symbols;

	public static Builder builder() {
		return new Builder();
	}

	public BatterieDaten(Builder builder) {
		symbols = new DecimalFormatSymbols();
		symbols.setMinusSign(' ');
		symbols.setDecimalSeparator(',');
		df = new DecimalFormat("#", symbols);
		dfNachkomma = new DecimalFormat("####.##", symbols);
		
		setLadeZyklen(builder.ladeZyklen);
		setLadeStrom(builder.ladeStrom);
		setSpannung(builder.spannung);
		setTemperatur(builder.temperatur);
		setLadeStand(builder.ladeStand);
		setLeistung(builder.ladeStrom * builder.spannung);
		setBattIcon();
		setBattRichtung();
	}
	
	public float getLadeZyklen() {
		return ladeZyklen;
	}
	public String getLadeZyklenStr() {
		return df.format(ladeZyklen);
	}
	public float getLadeStrom() {
		return ladeStrom;
	}
	public String getLadeStromStr() {
		return df.format(ladeStrom);
	}
	public String getLadeStromNachkomma() {
		return dfNachkomma.format(ladeStrom);
	}
	public float getSpannung() {
		return spannung;
	}
	public String getSpannungStr() {
		return df.format(spannung);
	}
	public String getSpannungNachkomma() {
		return dfNachkomma.format(spannung);
	}
	public float getTemperatur() {
		return temperatur;
	}
	public float getLadeStand() {
		return ladeStand;
	}
	public String getLadeStandStr() {
		return df.format(ladeStand);
	}
	public float getLeistung() {
		return leistung;
	}
	public String getLeistungStr() {
		return df.format(leistung);
	}
	public String getLeistungNachkomma() {
		return dfNachkomma.format(leistung);
	}
	public String getIcon() {
		return icon;
	}
	public String getRichtung() {
		return richtung;
	}
	public String getFarbe() {
		return farbe;
	}
	public String getLadung() {
		return ladung;
	}
	private void setLadeZyklen(float ladeZyklen) {
		this.ladeZyklen = ladeZyklen;
	}
	private void setLadeStrom(float ladeStrom) {
		this.ladeStrom = ladeStrom;
	}
	private void setSpannung(float spannung) {
		this.spannung = spannung;
	}
	private void setTemperatur(float temperatur) {
		this.temperatur = temperatur;
	}
	private void setLadeStand(float ladeStand) {
		this.ladeStand = ladeStand;
	}
	private void setLeistung(float leistung) {
		this.leistung = leistung;
	}
	private void setBattIcon() {
		if (this.ladeStand <= 5) {
			this.icon = "battery-0";
		} else if (this.ladeStand <= 25) {
			this.icon = "battery-1";
		} else if (this.ladeStand <= 50) {
			this.icon = "battery-2";
		} else if (this.ladeStand <= 75) {
			this.icon = "battery-3";
		} else if (this.ladeStand <= 100) {
			this.icon = "battery-4";
		}
	}
	private void setBattRichtung() {
		if (this.leistung > 0) {
			this.richtung = "right";
			this.farbe = "rot";
			this.ladung = "Wird entladen";
		} else {
			this.richtung = "left";
			this.farbe = "gruen";
			this.ladung = "Wird geladen";
			
		}

	}

	public static final class Builder {
		private float ladeZyklen;
		private float ladeStrom;
		private float spannung;
		private float temperatur;
		private float ladeStand;

		private Builder() {
		}

		public Builder withLadeZyklen(float ladeZyklen) {
			this.ladeZyklen = ladeZyklen;
			return this;
		}
		
		public Builder withLadeStrom(float ladeStrom) {
			this.ladeStrom = ladeStrom;
			return this;
		}
		
		public Builder withSpannung(float spannung) {
			this.spannung = spannung;
			return this;
		}

		public Builder withTemperatur(float temperatur) {
			this.temperatur = temperatur;
			return this;
		}

		public Builder withLadeStand(float ladeStand) {
			this.ladeStand = ladeStand;
			return this;
		}
		
		public BatterieDaten build() {
			return new BatterieDaten(this);
		}
	}
}
