package de.erdtmann.soft.homeService;


import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.MediaType;

import org.jboss.logging.Logger;

import de.erdtmann.soft.homeService.exceptions.PvException;
import de.erdtmann.soft.homeService.model.PvHome;
import de.erdtmann.soft.homeService.utils.Constants;

@ApplicationScoped
public class PvRestClient {

	Logger log = Logger.getLogger(PvRestClient.class);

	public PvHome holePvDaten() throws PvException {

		String restCall = Constants.REST_PV_URL + Constants.REST_PFAD_PV ;

		log.info(restCall);

		Client restClient = ClientBuilder.newClient();

		return restClient.target(restCall).request(MediaType.APPLICATION_JSON).get(PvHome.class);
	}
}
