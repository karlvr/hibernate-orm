package org.hibernate.service.boot.selector.internal;

import org.hibernate.service.boot.selector.StrategyRegistrationProvider;

public class DefaultStrategySelectorBuilder extends AbstractStrategySelectorBuilder {

	public DefaultStrategySelectorBuilder() {
		super(StrategyRegistrationProvider.class);
	}

	@Override
	protected void addBaseline(StrategySelectorImpl strategySelector) {

	}

}
