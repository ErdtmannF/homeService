package de.erdtmann.soft.homeService;


import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import org.jboss.logging.Logger;

import de.erdtmann.soft.homeService.exceptions.PoolPiException;
import de.erdtmann.soft.homeService.utils.Heizung;
import de.erdtmann.soft.homeService.utils.Pumpe;


@ApplicationScoped
public class PoolPiService {

	Logger log = Logger.getLogger(PoolPiService.class);
	
//	@Inject
//	PumpenRepository pumpenRepo;
	
	@Inject
	PoolPiRestClient restClient;
	
//	PumpenZeitE pumpenZeitE;
	
	public boolean holePumpenSatus() throws PoolPiException {
		return (restClient.statusPumpe() == 1);
	}

	public boolean holeHeizungSatus() throws PoolPiException {
		return (restClient.statusHeizung() == 1);
	}

	public int schaltePumpe(Pumpe pumpe) throws PoolPiException {
		log.info("Pumpe: " + pumpe.name());
		
		int status = restClient.schaltePumpe(pumpe);
		
			if (status == 200) {
				if (pumpe.equals(Pumpe.AN)) {
					startePumpenLauf();
				} else if (pumpe.equals(Pumpe.AUS)) {
					beendePumpenLauf();
				}
			}
		return status;
	}	

	public int schalteHeizung(Heizung heizung) throws PoolPiException {
		log.info("heizung: " + heizung.name());
		
		int status = restClient.schalteHeizung(heizung);
		
		if (status == 200) {
			if (heizung.equals(Heizung.AN)) {
				starteHeizungLauf();
			} else if (heizung.equals(Heizung.AUS)) {
				beendeHeizungLauf();
			}
		}
		return status;
	}

	void startePumpenLauf() throws PoolPiException {
//		pumpenZeitE = neuePumpenZeit();
//		
//		pumpenRepo.speicherePumpenZeit(pumpenZeitE);
	}

	void starteHeizungLauf() {
		// Start Heizungslauf implementieren
	}

	void beendePumpenLauf() throws PoolPiException {
//		pumpenZeitE.setAusgeschaltet(LocalDateTime.now());
//		
//		pumpenRepo.speicherePumpenZeit(pumpenZeitE);
//		
//		int tage = berechneTage();
//		
//		
//		
//		if (tage == 0) {
//			LocalDateTime anfang = pumpenZeitE.getEingeschaltet();
//			LocalDateTime ende = pumpenZeitE.getAusgeschaltet();
//			
//			PumpenLaufzeitE pumpenLaufzeitE = pumpenRepo.sucheLaufzeitByDatum(anfang.toLocalDate());
//			
//			if (pumpenLaufzeitE  == null) {
//				pumpenLaufzeitE = erzeugeNeueLaufzeit(anfang.toLocalDate());	
//			}
//			
//			long dauer = berechneLaufZeit(anfang, ende);
//			
//			pumpenLaufzeitE.setLaufzeit(pumpenLaufzeitE.getLaufzeit() + dauer);
//			
//			pumpenRepo.speicherePumpenLaufzeit(pumpenLaufzeitE);
//			
//		} else if (tage > 0) {
//			PumpenLaufzeitE pumpenLaufzeitE = pumpenRepo.sucheLaufzeitByDatum(pumpenZeitE.getEingeschaltet().toLocalDate());
//			
//			if (pumpenLaufzeitE  == null) {
//				pumpenLaufzeitE = erzeugeNeueLaufzeit(pumpenZeitE.getEingeschaltet().toLocalDate());	
//			}
//			
//			long dauer = berechneLaufZeit(pumpenZeitE.getEingeschaltet(), berechneMitternacht(pumpenZeitE.getEingeschaltet().toLocalDate().plusDays(1)));
//			
//			pumpenLaufzeitE.setLaufzeit(pumpenLaufzeitE.getLaufzeit() + dauer);
//			
//			pumpenRepo.speicherePumpenLaufzeit(pumpenLaufzeitE);
//			
//			for (int i=1; i <= tage; i++) {
//				pumpenLaufzeitE = pumpenRepo.sucheLaufzeitByDatum(pumpenZeitE.getEingeschaltet().toLocalDate().plusDays(i));
//				
//				if (pumpenLaufzeitE  == null) {
//					pumpenLaufzeitE = erzeugeNeueLaufzeit(pumpenZeitE.getEingeschaltet().toLocalDate().plusDays(i));	
//				}
//				
//				LocalDateTime anfang = berechneMitternacht(pumpenZeitE.getEingeschaltet().toLocalDate().plusDays(i));
//				
//				if (anfang.toLocalDate().equals(pumpenZeitE.getAusgeschaltet().toLocalDate())) {
//					dauer = berechneLaufZeit(anfang, pumpenZeitE.getAusgeschaltet());
//					
//					pumpenLaufzeitE.setLaufzeit(pumpenLaufzeitE.getLaufzeit() + dauer);
//					
//					pumpenRepo.speicherePumpenLaufzeit(pumpenLaufzeitE);
//				} else {
//					dauer = berechneLaufZeit(anfang, berechneMitternacht(anfang.toLocalDate().plusDays(1)));
//					
//					pumpenLaufzeitE.setLaufzeit(pumpenLaufzeitE.getLaufzeit() + dauer);
//					
//					pumpenRepo.speicherePumpenLaufzeit(pumpenLaufzeitE);
//				}
//			}
//		}
	}

	void beendeHeizungLauf() {
		// Ende Heizungslauf implementieren
	}

	
//	private LocalDateTime berechneMitternacht(LocalDate anfang) {
//		
//		LocalTime mitternacht = LocalTime.of(00, 00);
//		
//		return LocalDateTime.of(anfang, mitternacht);
//	}
	
	
//	private PumpenLaufzeitE erzeugeNeueLaufzeit(LocalDate datum) {
//		return PumpenLaufzeitE.builder().withDatum(datum).withLaufzeit(0).build();	
//	}
//
//	PumpenZeitE neuePumpenZeit() {
//		return PumpenZeitE.builder().withEingeschaltet(LocalDateTime.now()).build();
//	}
	
//	int berechneTage() {
//		return Period.between(pumpenZeitE.getEingeschaltet().toLocalDate(), pumpenZeitE.getAusgeschaltet().toLocalDate()).getDays();
//	}
	
//	long berechneLaufZeit(LocalDateTime anfang, LocalDateTime ende) {
//		return Duration.between(anfang, ende).getSeconds();
//	}
}
