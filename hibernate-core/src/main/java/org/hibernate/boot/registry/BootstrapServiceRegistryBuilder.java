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
package org.hibernate.boot.registry;

import java.util.LinkedHashSet;

import org.hibernate.boot.registry.internal.BootstrapServiceRegistryImpl;
import org.hibernate.boot.registry.selector.internal.StrategySelectorBuilder;
import org.hibernate.integrator.internal.IntegratorServiceImpl;
import org.hibernate.integrator.spi.Integrator;
import org.hibernate.boot.registry.BootstrapServiceRegistry;
import org.hibernate.service.boot.classloading.spi.ClassLoaderService;
import org.hibernate.service.boot.selector.spi.StrategySelector;

/**
 * Builder for {@link BootstrapServiceRegistry} instances.  Provides registry for services needed for
 * most operations.  This includes {@link Integrator} handling and ClassLoader handling.
 *
 * Additionally responsible for building and managing the {@link org.hibernate.boot.registry.selector.spi.StrategySelector}
 *
 * @author Steve Ebersole
 * @author Brett Meyer
 *
 * @see StandardServiceRegistryBuilder
 */
public class BootstrapServiceRegistryBuilder extends org.hibernate.service.boot.BootstrapServiceRegistryBuilder {
	private final LinkedHashSet<Integrator> providedIntegrators = new LinkedHashSet<Integrator>();
	
	public BootstrapServiceRegistryBuilder() {
		super(new StrategySelectorBuilder());
	}

	/**
	 * Add an {@link Integrator} to be applied to the bootstrap registry.
	 *
	 * @param integrator The integrator to add.
	 *
	 * @return {@code this}, for method chaining
	 */
	public BootstrapServiceRegistryBuilder with(Integrator integrator) {
		providedIntegrators.add( integrator );
		return this;
	}

	@Override
	public BootstrapServiceRegistry build() {
		return (BootstrapServiceRegistry) super.build();
	}

	@Override
	protected BootstrapServiceRegistry build(ClassLoaderService classLoaderService, StrategySelector strategySelector) {
		final IntegratorServiceImpl integratorService = new IntegratorServiceImpl(
				providedIntegrators,
				classLoaderService
		);
		
		return new BootstrapServiceRegistryImpl(
				classLoaderService,
				strategySelector,
				integratorService
		);
	}
}
