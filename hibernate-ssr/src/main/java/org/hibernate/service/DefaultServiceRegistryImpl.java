/*
 * Hibernate, Relational Persistence for Idiomatic Java
 *
 * Copyright (c) 2011, Red Hat Inc. or third-party contributors as
 * indicated by the @author tags or express copyright attribution
 * statements applied by the authors.  All third-party contributions are
 * distributed under license by Red Hat Inc.
 *
 * This copyrighted material is made available to anyone wishing to use, modify,
 * copy, or redistribute it subject to the terms and conditions of the GNU
 * Lesser General Public License, as published by the Free Software Foundation.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY
 * or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU Lesser General Public License
 * for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this distribution; if not, write to:
 * Free Software Foundation, Inc.
 * 51 Franklin Street, Fifth Floor
 * Boston, MA  02110-1301  USA
 */
package org.hibernate.service;

import java.util.List;
import java.util.Map;

import org.hibernate.service.internal.AbstractServiceRegistryImpl;
import org.hibernate.service.internal.ProvidedService;
import org.hibernate.service.spi.Configurable;
import org.hibernate.service.spi.ServiceBinding;
import org.hibernate.service.spi.ServiceInitiator;
import org.hibernate.service.spi.ServiceRegistryImplementor;

/**
 * Specialization of the {@link org.hibernate.service.ServiceRegistry} contract.
 * 
 * @author Karl von Randow
 */
public class DefaultServiceRegistryImpl extends AbstractServiceRegistryImpl {

	private Map<?, ?> configurationValues;

	public DefaultServiceRegistryImpl(ServiceRegistryImplementor parentRegistry, List<DefaultServiceInitiator<? extends Service>> serviceInitiators,
			List<ProvidedService<? extends Service>> providedServices, Map<?, ?> configurationValues) {
		super(parentRegistry);
		this.configurationValues = configurationValues;

		// process initiators
		for (ServiceInitiator<? extends Service> initiator : serviceInitiators) {
			createServiceBinding(initiator);
		}

		// then, explicitly provided service instances
		for (ProvidedService<? extends Service> providedService : providedServices) {
			createServiceBinding(providedService);
		}
	}

	@Override
	public <R extends Service> R initiateService(ServiceInitiator<R> serviceInitiator) {
		return ((DefaultServiceInitiator<R>) serviceInitiator).initiateService(configurationValues, this);
	}

	@Override
	public <R extends Service> void configureService(ServiceBinding<R> binding) {
		if (Configurable.class.isInstance(binding.getService())) {
			((Configurable) binding.getService()).configure(configurationValues);
		}
	}

	@Override
	public void destroy() {
		super.destroy();
		if (getParentServiceRegistry() != null) {
			((ServiceRegistryImplementor) getParentServiceRegistry()).destroy();
		}
	}

}
