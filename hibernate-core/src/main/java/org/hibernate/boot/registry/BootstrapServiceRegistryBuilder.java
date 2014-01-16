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
import java.util.Set;

import org.hibernate.boot.registry.classloading.internal.ClassLoaderServiceImpl;
import org.hibernate.boot.registry.internal.BootstrapServiceRegistryImpl;
import org.hibernate.boot.registry.selector.internal.StrategySelectorBuilder;
import org.hibernate.integrator.internal.IntegratorServiceImpl;
import org.hibernate.integrator.spi.Integrator;
import org.hibernate.boot.registry.BootstrapServiceRegistry;
import org.hibernate.service.boot.AbstractBootstrapServiceRegistryBuilder;
import org.hibernate.service.boot.classloading.spi.ClassLoaderService;
import org.hibernate.service.boot.selector.StrategyRegistrationProvider;
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
public class BootstrapServiceRegistryBuilder extends org.hibernate.service.boot.AbstractBootstrapServiceRegistryBuilder {
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
	public BootstrapServiceRegistryBuilder with(ClassLoader classLoader) {
		return (BootstrapServiceRegistryBuilder) super.with(classLoader);
	}

	@Override
	public BootstrapServiceRegistryBuilder with(ClassLoaderService classLoaderService) {
		return (BootstrapServiceRegistryBuilder) super.with(classLoaderService);
	}

	@Override
	public <T> BootstrapServiceRegistryBuilder withStrategySelector(Class<T> strategy, String name, Class<? extends T> implementation) {
		return (BootstrapServiceRegistryBuilder) super.withStrategySelector(strategy, name, implementation);
	}

	@Override
	public BootstrapServiceRegistryBuilder withStrategySelectors(StrategyRegistrationProvider strategyRegistrationProvider) {
		return (BootstrapServiceRegistryBuilder) super.withStrategySelectors(strategyRegistrationProvider);
	}
	


	/**
	 * Applies the specified {@link ClassLoader} as the application class loader for the bootstrap registry.
	 *
	 * @param classLoader The class loader to use
	 *
	 * @return {@code this}, for method chaining
	 *
	 * @deprecated Use {@link #with(ClassLoader)} instead
	 */
	@Deprecated
	@SuppressWarnings( {"UnusedDeclaration"})
	public AbstractBootstrapServiceRegistryBuilder withApplicationClassLoader(ClassLoader classLoader) {
		return with( classLoader );
	}

	/**
	 * Applies the specified {@link ClassLoader} as the resource class loader for the bootstrap registry.
	 *
	 * @param classLoader The class loader to use
	 *
	 * @return {@code this}, for method chaining
	 *
	 * @deprecated Use {@link #with(ClassLoader)} instead
	 */
	@Deprecated
	@SuppressWarnings( {"UnusedDeclaration"})
	public AbstractBootstrapServiceRegistryBuilder withResourceClassLoader(ClassLoader classLoader) {
		return with( classLoader );
	}

	/**
	 * Applies the specified {@link ClassLoader} as the Hibernate class loader for the bootstrap registry.
	 *
	 * @param classLoader The class loader to use
	 *
	 * @return {@code this}, for method chaining
	 *
	 * @deprecated Use {@link #with(ClassLoader)} instead
	 */
	@Deprecated
	@SuppressWarnings( {"UnusedDeclaration"})
	public AbstractBootstrapServiceRegistryBuilder withHibernateClassLoader(ClassLoader classLoader) {
		return with( classLoader );
	}

	/**
	 * Applies the specified {@link ClassLoader} as the environment (or system) class loader for the bootstrap registry.
	 *
	 * @param classLoader The class loader to use
	 *
	 * @return {@code this}, for method chaining
	 *
	 * @deprecated Use {@link #with(ClassLoader)} instead
	 */
	@Deprecated
	@SuppressWarnings( {"UnusedDeclaration"})
	public AbstractBootstrapServiceRegistryBuilder withEnvironmentClassLoader(ClassLoader classLoader) {
		return with( classLoader );
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
	
	protected ClassLoaderServiceImpl defaultClassLoaderService(final Set<ClassLoader> classLoaders) {
		return new ClassLoaderServiceImpl( classLoaders );
	}
	
}
