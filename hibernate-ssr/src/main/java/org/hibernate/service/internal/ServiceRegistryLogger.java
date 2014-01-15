package org.hibernate.service.internal;

import static org.jboss.logging.Logger.Level.INFO;
import static org.jboss.logging.Logger.Level.WARN;

import org.jboss.logging.BasicLogger;
import org.jboss.logging.annotations.LogMessage;
import org.jboss.logging.annotations.Message;

public interface ServiceRegistryLogger extends BasicLogger {

	@LogMessage(level = WARN)
	@Message(
			id = 450,
			value = "Encountered request for Service by non-primary service role [%s -> %s]; please update usage"
	)
	void alternateServiceRole(String requestedRole, String targetRole);

	@LogMessage(level = INFO)
	@Message(value = "Error stopping service [%s] : %s", id = 369)
	void unableToStopService(Class class1,
							 String string);
	
}
