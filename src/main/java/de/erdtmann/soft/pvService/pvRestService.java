package de.erdtmann.soft.pvService;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import de.erdtmann.soft.pvService.exceptions.PvException;
import de.erdtmann.soft.pvService.model.PvHome;

@Path("/pv")
@Consumes({ "application/json" })
@Produces({ "application/json" })
public class pvRestService {

	@Inject
	PvService pvService;
	
	@GET
	@Path("/pvHome")
	public PvHome getHomeDaten() {
		PvHome pv = null;
		try {
			pv =  pvService.ladePvHomeDaten();
		} catch (PvException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
			return pv;	
	}
	
}
