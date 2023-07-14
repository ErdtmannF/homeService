package de.erdtmann.soft.homeService;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

import org.jboss.logging.Logger;

import de.erdtmann.soft.homeService.entities.KonfigurationE;
import de.erdtmann.soft.homeService.exceptions.PoolPiException;
import de.erdtmann.soft.homeService.exceptions.PvException;
import de.erdtmann.soft.homeService.model.PoolKonfig;
import de.erdtmann.soft.homeService.model.PvHome;
import de.erdtmann.soft.homeService.utils.Heizung;
import de.erdtmann.soft.homeService.utils.KonfigNames;
import de.erdtmann.soft.homeService.utils.Pumpe;

@RequestScoped
public class CoreService {

	Logger log = Logger.getLogger(CoreService.class);

	@Inject
	CoreRepository coreRepo;

	@Inject
	PoolPiService poolPi;

	@Inject
	PvRestClient pvRest;

	private Map<KonfigNames, KonfigurationE> konfiguration;

	private static final String FLAG_TRUE = "1";
	private static final String FLAG_FALSE = "0";
	private static final String WERT_EIN = "ein";
	private static final String WERT_AUS = "aus";
	private static final int SLEEP_TIME = 30000;

	
	public void ladeKonfiguration() {
		List<KonfigurationE> konfigDBWerte = coreRepo.ladeKonfiguration();

		if (konfiguration == null) {
			konfiguration = new EnumMap<>(KonfigNames.class);
		}

		konfiguration.clear();

		for (KonfigurationE konfigE : konfigDBWerte) {
			for (KonfigNames konfEnum : KonfigNames.values()) {
				if (konfigE.getRubrik().equals(konfEnum.getRubrik())
						&& konfigE.getKategorie().equals(konfEnum.getKategorie())) {
					konfiguration.put(konfEnum, konfigE);
				}
			}
		}
	}

	public void poolSteuerung() {
		ladeKonfiguration();
		
		if (isPoolAutomatikEin()) {
			try {
				// PV Daten holen
				PvHome pvDaten = pvRest.holePvDaten();

				log.info("PV Leistung: " + pvDaten.getLeistung());
				log.info("Batt Ladung: " + pvDaten.getBattLadung());
				
				boolean isPumpeAn = holePumpenSatus();
				boolean isHeizungAn = holeHeizungSatus();

				boolean isPvMin = isPvUeberMin(pvDaten);
				boolean isPvMax = isPvUeberMax(pvDaten);

				boolean isBattMin = isBattUeberMin(pvDaten);
				boolean isBattMax = isBattUeberMax(pvDaten);

				boolean schaltePumpeAn = false;
				boolean schalteHeizungAn = false;

				log.info("Pumpe an: " + isPumpeAn);
				log.info("Heizung an: " + isHeizungAn);
				log.info("PvMin: " + isPvMin);
				log.info("PvMax: " + isPvMax);
				log.info("BattMin: " + isBattMin);
				log.info("BattMax: " + isBattMax);

				if (isPvMin && isBattMin) {
					schaltePumpeAn = true;
					if (isPvMax && isBattMax) {
						schalteHeizungAn = true;
					}
				}

				if (schaltePumpeAn == isPumpeAn && schalteHeizungAn == isHeizungAn) {
					log.info("Keine Änderung.");
				} else {
					log.info("Zustand hat sich geändert.");
					// Heizung soll eingeschaltet werden
					if (schalteHeizungAn) {
						// Pumpe ausschalten wenn an
						isPumpeAn = schaltePumpeAus(isPumpeAn);
						// Heizung einschalten wenn aus
						isHeizungAn = schalteHeizungEin(isHeizungAn);
						// Pumpe einschalten wenn aus
						isPumpeAn = schaltePumpeEin(isPumpeAn);
						// Heizung soll ausgeschaltet werden
					} else {
						// Pumpe ausschalten wenn an
						isPumpeAn = schaltePumpeAus(isPumpeAn);
						// Heizung ausschalten wenn an
						isHeizungAn = schalteHeizungAus(isHeizungAn);
						// Pumpe soll eingeschaltet werden
						if (schaltePumpeAn) {
							// Pumpe einschalten wenn aus
							isPumpeAn = schaltePumpeEin(isPumpeAn);
						}
					}
				}
			} catch (Exception e) {
				log.error("Fehler in der PoolSteuerung");
				log.error(e.getMessage());
			}

		}
	}

	public PoolKonfig getPoolDaten() {
		ladeKonfiguration();
		
		return PoolKonfig.builder().withAutomatik(isPoolAutomatikEin()).withWinter(isPoolWinterEin())
				.withPvMin(holeMinPv()).withPvMax(holeMaxPv()).withBattMin(holeMinBatt()).withBattMax(holeMaxBatt())
				.withAnfang(holeAnfang()).withEnde(holeEnde()).build();
	}

	private boolean isPoolAutomatikEin() {
		return (konfiguration.get(KonfigNames.POOL_AUTOMATIK).getWert().equals(FLAG_TRUE));
	}

	private boolean isPoolWinterEin() {
		return (konfiguration.get(KonfigNames.POOL_WINTER).getWert().equals(FLAG_TRUE));
	}

	public int setPoolAutomatik(String wert) {
		switch (wert) {
		case WERT_EIN:
			return saveKonfigWert(FLAG_TRUE, KonfigNames.POOL_AUTOMATIK);
		case WERT_AUS:
			return saveKonfigWert(FLAG_FALSE, KonfigNames.POOL_AUTOMATIK);
		default:
			return 0;
		}
	}

	public int setPoolWinter(String wert) {
		switch (wert) {
		case WERT_EIN:
			return saveKonfigWert(FLAG_TRUE, KonfigNames.POOL_WINTER);
		case WERT_AUS:
			return saveKonfigWert(FLAG_FALSE, KonfigNames.POOL_WINTER);
		default:
			return 0;
		}
	}

	private int holeMinPv() {
		return Integer.parseInt(konfiguration.get(KonfigNames.POOL_PV_MIN).getWert());
	}

	private int holeMaxPv() {
		return Integer.parseInt(konfiguration.get(KonfigNames.POOL_PV_MAX).getWert());
	}

	private int holeMinBatt() {
		return Integer.parseInt(konfiguration.get(KonfigNames.POOL_BATT_MIN).getWert());
	}

	private int holeMaxBatt() {
		return Integer.parseInt(konfiguration.get(KonfigNames.POOL_BATT_MAX).getWert());
	}

	private LocalTime holeAnfang() {
		return (LocalTime.parse(konfiguration.get(KonfigNames.POOL_ZEIT_ANFANG).getWert()));
	}

	private LocalTime holeEnde() {
		return (LocalTime.parse(konfiguration.get(KonfigNames.POOL_ZEIT_ENDE).getWert()));
	}

	private int saveKonfigWert(String wert, KonfigNames konfig) {
		ladeKonfiguration();
		
		KonfigurationE konfigE = konfiguration.get(konfig);

		konfigE.setWert(wert);

		return coreRepo.saveKonfigurationsItem(konfigE);
	}

	private boolean holePumpenSatus() throws PoolPiException {
		return poolPi.holePumpenSatus();
	}

	private boolean holeHeizungSatus() throws PoolPiException {
		return poolPi.holeHeizungSatus();
	}

	private boolean schaltePumpeEin(boolean isPumpeAn) throws PoolPiException, InterruptedException {
		if (!isPumpeAn) {
			schaltePumpe(Pumpe.AN);
			Thread.sleep(1000);
		}
		return holePumpenSatus();
	}

	private boolean schaltePumpeAus(boolean isPumpeAn) throws PoolPiException, InterruptedException {
		if (isPumpeAn) {
			schaltePumpe(Pumpe.AUS);
			Thread.sleep(1000);
		}
		return holePumpenSatus();
	}

	private int schaltePumpe(Pumpe zustand) throws PoolPiException {
		return poolPi.schaltePumpe(zustand);
	}

	private boolean schalteHeizungAus(boolean isHeizungAn) throws PoolPiException, InterruptedException {
		if (isHeizungAn) {
			schalteHeizung(Heizung.AUS);
			Thread.sleep(SLEEP_TIME);
		}
		return holeHeizungSatus();
	}

	private boolean schalteHeizungEin(boolean isHeizungAn) throws PoolPiException, InterruptedException {
		if (!isHeizungAn) {
			schalteHeizung(Heizung.AN);
			Thread.sleep(SLEEP_TIME);
		}
		return holeHeizungSatus();
	}

	private int schalteHeizung(Heizung status) throws PoolPiException {
		return poolPi.schalteHeizung(status);
	}

	public boolean isPvUeberMin(PvHome pvDaten) throws PvException {
		return pvDaten.getLeistung() > Integer.parseInt(konfiguration.get(KonfigNames.POOL_PV_MIN).getWert());
	}

	public boolean isPvUeberMax(PvHome pvDaten) throws PvException {
		return pvDaten.getLeistung() > Integer.parseInt(konfiguration.get(KonfigNames.POOL_PV_MAX).getWert());
	}

	public boolean isBattUeberMin(PvHome pvDaten) throws PvException {
		return pvDaten.getBattLadung() > Integer.parseInt(konfiguration.get(KonfigNames.POOL_BATT_MIN).getWert());
	}

	public boolean isBattUeberMax(PvHome pvDaten) throws PvException {
		return pvDaten.getBattLadung() > Integer.parseInt(konfiguration.get(KonfigNames.POOL_BATT_MAX).getWert());
	}

//	public BatterieDaten ladeBatterie() throws PvException {
//		return pvService.ladeBatterie();
//	}
//
//	public PvDaten ladePv() throws PvException {
//		return pvService.ladePv();
//	}
//
//	public NetzDaten ladeNetz() throws PvException {
//		return pvService.ladeNetz();
//	}
//
//	public Verbrauch ladeVerbrauch() throws PvException {
//		return pvService.ladeVerbrauch();
//	}
//
//	public List<BattLadungE> ladeBattLadungTag(LocalDate datum) {
//		return pvService.ladeBattLadungTag(datum);
//	}
//
//	public List<LeistungE> ladeVerbrauchTagTyp(LocalDate datum, int i) {
//		return pvService.ladeVerbrauchTagTyp(datum, i);
//	}
//
//	public void speichereDaten() {
//		try {
//			pvService.speichereDaten();
//		} catch (PvException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//	
//	}

}
