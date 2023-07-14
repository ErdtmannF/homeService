package de.erdtmann.soft.homeService.timer;

import javax.ejb.Singleton;
import javax.inject.Inject;
import javax.ejb.Schedule;

import org.jboss.logging.Logger;

import de.erdtmann.soft.homeService.CoreService;

@Singleton
public class PoolTimer {

	Logger log = Logger.getLogger(PoolTimer.class);

	@Inject
	CoreService coreService;

//	@Schedule(second = "0", minute = "*/5", hour = "*", persistent = false)
//	public void alleFuenfMinuten() {
//			coreService.speichereTemperaturen();
//	}

	@Schedule(second = "0", minute = "*/15", hour = "*", persistent = false)
	public void alleZehnMinuten() {
		coreService.poolSteuerung();
	}
}
