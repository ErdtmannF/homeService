package de.erdtmann.soft.homeService.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedNativeQuery;
import javax.persistence.Table;


@NamedNativeQuery(
		name="Konfiguration.ladeKonfiguration",
		query="select id, wert, rubrik, kategorie from KONFIGURATION k",
		resultClass=KonfigurationE.class)


@Entity
@Table(name = "KONFIGURATION")
public class KonfigurationE {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private String wert;
	private String rubrik;
	private String kategorie;
	
	protected KonfigurationE() { }
	
	private KonfigurationE(Builder builder) {
		setWert(builder.wert);
		setRubrik(builder.rubrik);
		setKategorie(builder.kategorie);
	}
	
	public static Builder builder() {
		return new Builder();
	}
	
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getWert() {
		return wert;
	}

	public void setWert(String wert) {
		this.wert = wert;
	}

	public String getRubrik() {
		return rubrik;
	}

	public void setRubrik(String rubrik) {
		this.rubrik = rubrik;
	}

	public String getKategorie() {
		return kategorie;
	}

	public void setKategorie(String kategorie) {
		this.kategorie = kategorie;
	}


	public static final class Builder {
		private String wert;
		private String rubrik;
		private String kategorie;

		private Builder() {
		}

		public Builder withWert(String wert) {
			this.wert = wert;
			return this;
		}
		
		public Builder withRubrik(String rubrik) {
			this.rubrik = rubrik;
			return this;
		}

		public Builder withKategorie(String kategorie) {
			this.kategorie = kategorie;
			return this;
		}

		public KonfigurationE build() {
			return new KonfigurationE(this);
		}
	}
}
