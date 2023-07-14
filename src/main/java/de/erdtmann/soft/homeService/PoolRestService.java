package de.erdtmann.soft.homeService;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import de.erdtmann.soft.homeService.model.PoolKonfig;

@Path("/pool")
@Consumes({ "application/json" })
@Produces({ "application/json" })
public class PoolRestService {

	@Inject
	CoreService coreService;

	@GET
	@Path("/konfig")
	public PoolKonfig getPoolKonfig() {
		PoolKonfig pool = null;

		pool = coreService.getPoolDaten();

		return pool;
	}

	@POST
	@Path("/automatik/{wert}")
	public Response setAutomatik(@PathParam("wert") String wert) {
		if (coreService.setPoolAutomatik(wert) == 1) {
			return Response.status(Response.Status.OK).build();
		}else {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
		}
	}
	
	@POST
	@Path("/winter/{wert}")
	public Response setWinter(@PathParam("wert") String wert) {
		if (coreService.setPoolWinter(wert) == 1) {
			return Response.status(Response.Status.OK).build();
		}else {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
		}
	}
}
