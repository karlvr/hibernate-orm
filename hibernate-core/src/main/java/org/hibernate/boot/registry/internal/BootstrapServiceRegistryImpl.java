/*
 * Hibernate, Relational Persistence for Idiomatic Java
 *
 * Copyright (c) 2012, Red Hat Inc. or third-party contributors as
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
package org.hibernate.boot.registry.internal;

import java.util.LinkedHashSet;

import org.hibernate.integrator.internal.IntegratorServiceImpl;
import org.hibernate.integrator.spi.Integrator;
import org.hibernate.integrator.spi.IntegratorService;
import org.hibernate.service.Service;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.service.boot.classloading.internal.ClassLoaderServiceImpl;
import org.hibernate.service.boot.classloading.spi.ClassLoaderService;
import org.hibernate.service.boot.selector.spi.StrategySelector;
import org.hibernate.service.spi.ServiceBinding;

/**
 * {@link ServiceRegistry} implementation containing specialized "bootstrap" services, specifically:<ul>
 *     <li>{@link ClassLoaderService}</li>
 *     <li>{@link IntegratorService}</li>
 *     <li>{@link StrategySelector}</li>
 * </ul>
 *
 * IMPL NOTE : Currently implements the deprecated {@link org.hibernate.service.BootstrapServiceRegistry} contract
 * so that the registry returned from the builder works on the deprecated sense.  Once
 * {@link org.hibernate.service.BootstrapServiceRegistry} goes away, this should be updated to instead implement
 * {@link org.hibernate.boot.registry.BootstrapServiceRegistry}.
 *
 * @author Steve Ebersole
 */
public class BootstrapServiceRegistryImpl extends org.hibernate.service.boot.internal.BootstrapServiceRegistryImpl {

	private static final LinkedHashSet<Integrator> NO_INTEGRATORS = new LinkedHashSet<Integrator>();

	private final ServiceBinding<IntegratorService> integratorServiceBinding;

	/**
	 * Constructs a BootstrapServiceRegistryImpl.
	 *
	 * Do not use directly generally speaking.  Use {@link org.hibernate.boot.registry.BootstrapServiceRegistryBuilder}
	 * instead.
	 *
	 * @see org.hibernate.boot.registry.BootstrapServiceRegistryBuilder
	 */
	public BootstrapServiceRegistryImpl() {
		this( new ClassLoaderServiceImpl(), NO_INTEGRATORS );
	}

	/**
	 * Constructs a BootstrapServiceRegistryImpl.
	 *
	 * Do not use directly generally speaking.  Use {@link org.hibernate.boot.registry.BootstrapServiceRegistryBuilder}
	 * instead.
	 *
	 * @param classLoaderService The ClassLoaderService to use
	 * @param providedIntegrators The group of explicitly provided integrators
	 *
	 * @see org.hibernate.boot.registry.BootstrapServiceRegistryBuilder
	 */
	public BootstrapServiceRegistryImpl(
			ClassLoaderService classLoaderService,
			LinkedHashSet<Integrator> providedIntegrators) {
		super(classLoaderService);

		this.integratorServiceBinding = new ServiceBinding<IntegratorService>(
				this,
				IntegratorService.class,
				new IntegratorServiceImpl( providedIntegrators, classLoaderService )
		);
	}


	/**
	 * Constructs a BootstrapServiceRegistryImpl.
	 *
	 * Do not use directly generally speaking.  Use {@link org.hibernate.boot.registry.BootstrapServiceRegistryBuilder}
	 * instead.
	 *
	 * @param classLoaderService The ClassLoaderService to use
	 * @param strategySelector The StrategySelector to use
	 * @param integratorService The IntegratorService to use
	 *
	 * @see org.hibernate.boot.registry.BootstrapServiceRegistryBuilder
	 */
	public BootstrapServiceRegistryImpl(
			ClassLoaderService classLoaderService,
			StrategySelector strategySelector,
			IntegratorService integratorService) {
		super(classLoaderService, strategySelector);

		this.integratorServiceBinding = new ServiceBinding<IntegratorService>(
				this,
				IntegratorService.class,
				integratorService
		);
	}


	@Override
	@SuppressWarnings( {"unchecked"})
	public <R extends Service> ServiceBinding<R> locateServiceBinding(Class<R> serviceRole) {
		ServiceBinding<R> binding = super.locateServiceBinding(serviceRole);
		if (binding != null) {
			return binding;
		}
		else if ( IntegratorService.class.equals( serviceRole ) ) {
			return (ServiceBinding<R>) integratorServiceBinding;
		}

		return null;
	}

	@Override
	public void destroy() {
		super.destroy();
		destroy( integratorServiceBinding );
	}
	
}
