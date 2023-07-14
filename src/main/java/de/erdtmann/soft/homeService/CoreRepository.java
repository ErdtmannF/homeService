package de.erdtmann.soft.homeService;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.jboss.logging.Logger;

import de.erdtmann.soft.homeService.entities.KonfigurationE;



@Stateless
public class CoreRepository {

	Logger log = Logger.getLogger(CoreRepository.class);
	
	@PersistenceContext
	EntityManager em;
	
	@SuppressWarnings("unchecked")
	public List<KonfigurationE> ladeKonfiguration() {
		Query query = em.createNamedQuery("Konfiguration.ladeKonfiguration", KonfigurationE.class);
		return query.getResultList();
	}
	
	public int saveKonfigurationsItem(KonfigurationE konfig) {
		try {
			em.merge(konfig);
			log.info("Wert: " + konfig.getWert() + " für " + konfig.getRubrik() + "." + konfig.getKategorie() + " gespeichert");
		} catch (Exception e) {
			log.error(e.getMessage());
			return 0;
		}
		return 1;
	}

}
