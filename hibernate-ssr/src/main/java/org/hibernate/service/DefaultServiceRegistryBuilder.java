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

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.service.internal.ProvidedService;
import org.hibernate.service.spi.ServiceRegistryImplementor;

/**
 * Builder for {@link DefaultServiceRegistryImpl} instances.
 * 
 * @author Karl von Randow
 */
public class DefaultServiceRegistryBuilder implements ServiceRegistryBuilder {

	private ServiceRegistryImplementor parentRegistry;
	private List<DefaultServiceInitiator<? extends Service>> serviceInitiators = new ArrayList<DefaultServiceInitiator<? extends Service>>();
	private List<ProvidedService<? extends Service>> providedServices = new ArrayList<ProvidedService<? extends Service>>();
	private Map<Object, Object> settings = new HashMap<Object, Object>();
	
	@Override
	public ServiceRegistry build() {
		return new DefaultServiceRegistryImpl(parentRegistry, serviceInitiators, providedServices, settings);
	}
	
	public DefaultServiceRegistryBuilder setParentRegistry(ServiceRegistry parentRegistry) {
		this.parentRegistry = (ServiceRegistryImplementor) parentRegistry;
		return this;
	}
	
	public DefaultServiceRegistryBuilder addInitiator(DefaultServiceInitiator<? extends Service> serviceInitiator) {
		this.serviceInitiators.add(serviceInitiator);
		return this;
	}

	public DefaultServiceRegistryBuilder addInitiators(Collection<DefaultServiceInitiator<? extends Service>> serviceInitiators) {
		for (DefaultServiceInitiator<? extends Service> serviceInitiator : serviceInitiators) {
			addInitiator(serviceInitiator);
		}
		return this;
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public DefaultServiceRegistryBuilder addService(final Class<? extends Service> serviceClass, final Service service) {
		this.providedServices.add(new ProvidedService(serviceClass, service));
		return this;
	}

	public void addSettings(Map<?, ?> settings) {
		this.settings.putAll(settings);
	}
	
}
