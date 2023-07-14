package de.erdtmann.soft.homeService;

import javax.ws.rs.client.Client;

import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Response;

import org.jboss.logging.Logger;

import de.erdtmann.soft.homeService.exceptions.PoolPiException;
import de.erdtmann.soft.homeService.utils.Heizung;
import de.erdtmann.soft.homeService.utils.Constants;
import de.erdtmann.soft.homeService.utils.Pumpe;

@ApplicationScoped
public class PoolPiRestClient {

	Logger log = Logger.getLogger(PoolPiRestClient.class);

	public int schaltePumpe(Pumpe pumpe) throws PoolPiException {

		String restCall = Constants.REST_POOL_URL + Constants.REST_PFAD_PUMPE + pumpe.getRestPfad();

		log.info(restCall);

		Client restClient = ClientBuilder.newClient();
		Response response = restClient.target(restCall).request().post(Entity.text(""));

		if (response.getStatus() != 200) {
			throw new PoolPiException("Fehler beim Schalten der Pumpe, Response Staus: " + response.getStatus());
		}

		log.info("Pumpe Rest Call Response: " + response.getStatus());

		return response.getStatus();

	}

	public int statusPumpe() throws PoolPiException {
		
		String restCall = Constants.REST_POOL_URL + Constants.REST_PFAD_PUMPE + Constants.REST_PFAD_PUMPE_STATUS;

		log.info(restCall);

		try {
			Client restClient = ClientBuilder.newClient();
			return restClient.target(restCall).request().get(Integer.class);

		} catch (Exception e) {
			throw new PoolPiException("Fehler beim holen des Pumpenstatus: " + e.getMessage());
		}
	}

	public int schalteHeizung(Heizung heizung) throws PoolPiException {

		String restCall = Constants.REST_POOL_URL + Constants.REST_PFAD_HEIZUNG + heizung.getRestPfad();

		log.info(restCall);

		Client restClient = ClientBuilder.newClient();
		Response response = restClient.target(restCall).request().post(Entity.text(""));

		if (response.getStatus() != 200) {
			throw new PoolPiException("Fehler beim Schalten der Heizung: Response Staus: " + response.getStatus());
		}

		log.info("Heizung Rest Call Response: " + response.getStatus());

		return response.getStatus();
	}

	public int statusHeizung() throws PoolPiException {
		String restCall = Constants.REST_POOL_URL + Constants.REST_PFAD_HEIZUNG	+ Constants.REST_PFAD_HEIZUNG_STATUS;

		log.info(restCall);

		try {
			Client restClient = ClientBuilder.newClient();
			return restClient.target(restCall).request().get(Integer.class);
		} catch (Exception e) {
			throw new PoolPiException("Fehler beim holen des Heizungstatus: " + e.getMessage());
		}
	}

}
