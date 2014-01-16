package org.hibernate.service.boot;

import org.hibernate.service.boot.classloading.spi.ClassLoaderService;
import org.hibernate.service.boot.selector.StrategyRegistrationProvider;
import org.hibernate.service.boot.selector.internal.DefaultStrategySelectorBuilder;

public class DefaultBootstrapServiceRegistryBuilder extends AbstractBootstrapServiceRegistryBuilder {

	public DefaultBootstrapServiceRegistryBuilder() {
		super(new DefaultStrategySelectorBuilder());
	}

	@Override
	public DefaultBootstrapServiceRegistryBuilder with(ClassLoader classLoader) {
		return (DefaultBootstrapServiceRegistryBuilder) super.with(classLoader);
	}

	@Override
	public DefaultBootstrapServiceRegistryBuilder with(ClassLoaderService classLoaderService) {
		return (DefaultBootstrapServiceRegistryBuilder) super.with(classLoaderService);
	}

	@Override
	public <T> DefaultBootstrapServiceRegistryBuilder withStrategySelector(Class<T> strategy, String name, Class<? extends T> implementation) {
		return (DefaultBootstrapServiceRegistryBuilder) super.withStrategySelector(strategy, name, implementation);
	}

	@Override
	public DefaultBootstrapServiceRegistryBuilder withStrategySelectors(StrategyRegistrationProvider strategyRegistrationProvider) {
		return (DefaultBootstrapServiceRegistryBuilder) super.withStrategySelectors(strategyRegistrationProvider);
	}

}
