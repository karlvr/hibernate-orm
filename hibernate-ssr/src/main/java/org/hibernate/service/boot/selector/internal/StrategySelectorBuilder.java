package org.hibernate.service.boot.selector.internal;

import org.hibernate.service.boot.classloading.spi.ClassLoaderService;
import org.hibernate.service.boot.selector.StrategyRegistration;
import org.hibernate.service.boot.selector.spi.StrategySelector;

public interface StrategySelectorBuilder {

	StrategySelector buildSelector(ClassLoaderService classLoaderService);

	<T> void addExplicitStrategyRegistration(Class<T> strategy, Class<? extends T> implementation, String name);
	
	<T> void addExplicitStrategyRegistration(StrategyRegistration<T> strategyRegistration);

}
