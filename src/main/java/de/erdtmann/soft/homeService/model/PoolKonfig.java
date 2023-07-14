package de.erdtmann.soft.homeService.model;

import java.io.Serializable;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.time.LocalTime;

public class PoolKonfig implements Serializable {

	private static final long serialVersionUID = -5226816382430205430L;

	private boolean isAutomatik;
	private boolean isWinter;
	private int pvMin;
	private int pvMax;
	private int battMin;
	private int battMax;
	private LocalTime anfang;
	private LocalTime ende;
	
	DecimalFormat df;
	DecimalFormatSymbols symbols;

	public PoolKonfig() {}
	
	public static Builder builder() {
		return new Builder();
	}

	public PoolKonfig(Builder builder) {
		setAutomatik(builder.isAutomatik);
		setWinter(builder.isWinter);
		setAnfang(builder.anfang);
		setEnde(builder.ende);
		setPvMin(builder.pvMin);
		setPvMax(builder.pvMax);
		setBattMin(builder.battMin);
		setBattMax(builder.battMax);
	}

	public boolean isAutomatik() {
		return isAutomatik;
	}
	public void setAutomatik(boolean isAutomatik) {
		this.isAutomatik = isAutomatik;
	}

	public boolean isWinter() {
		return isWinter;
	}
	public void setWinter(boolean isWinter) {
		this.isWinter = isWinter;
	}

	public int getPvMin() {
		return pvMin;
	}
	public void setPvMin(int pvMin) {
		this.pvMin = pvMin;
	}

	public int getPvMax() {
		return pvMax;
	}
	public void setPvMax(int pvMax) {
		this.pvMax = pvMax;
	}

	public int getBattMin() {
		return battMin;
	}
	public void setBattMin(int battMin) {
		this.battMin = battMin;
	}

	public int getBattMax() {
		return battMax;
	}
	public void setBattMax(int battMax) {
		this.battMax = battMax;
	}

	public LocalTime getAnfang() {
		return anfang;
	}
	public void setAnfang(LocalTime anfang) {
		this.anfang = anfang;
	}

	public LocalTime getEnde() {
		return ende;
	}
	public void setEnde(LocalTime ende) {
		this.ende = ende;
	}

	public static final class Builder {
		private boolean isAutomatik;
		private boolean isWinter;
		private int pvMin;
		private int pvMax;
		private int battMin;
		private int battMax;
		private LocalTime anfang;
		private LocalTime ende;

		private Builder() {}
		
		public Builder withAutomatik(boolean automatik) {
			this.isAutomatik = automatik;
			return this;
		}
		public Builder withWinter(boolean winter) {
			this.isWinter = winter;
			return this;
		}
		public Builder withPvMin(int pvMin) {
			this.pvMin = pvMin;
			return this;
		}
		public Builder withPvMax(int pvMax) {
			this.pvMax = pvMax;
			return this;
		}
		public Builder withBattMin(int battMin) {
			this.battMin = battMin;
			return this;
		}
		public Builder withBattMax(int battMax) {
			this.battMax = battMax;
			return this;
		}
		public Builder withAnfang(LocalTime anfang) {
			this.anfang = anfang;
			return this;
		}
		public Builder withEnde(LocalTime ende) {
			this.ende = ende;
			return this;
		}
		
		public PoolKonfig build() {
			return new PoolKonfig(this);
		}
	}
}
