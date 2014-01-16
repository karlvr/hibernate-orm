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
package org.hibernate.service.boot;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.hibernate.service.boot.classloading.internal.ClassLoaderServiceImpl;
import org.hibernate.service.boot.classloading.spi.ClassLoaderService;
import org.hibernate.service.boot.internal.BootstrapServiceRegistryImpl;
import org.hibernate.service.boot.selector.StrategyRegistration;
import org.hibernate.service.boot.selector.StrategyRegistrationProvider;
import org.hibernate.service.boot.selector.internal.StrategySelectorBuilder;
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
public abstract class AbstractBootstrapServiceRegistryBuilder {
	private List<ClassLoader> providedClassLoaders;
	private ClassLoaderService providedClassLoaderService;
	private StrategySelectorBuilder strategySelectorBuilder;
	
	public AbstractBootstrapServiceRegistryBuilder(StrategySelectorBuilder strategySelectorBuilder) {
		super();
		this.strategySelectorBuilder = strategySelectorBuilder;
	}

	/**
	 * Adds a provided {@link ClassLoader} for use in class-loading and resource-lookup.
	 *
	 * @param classLoader The class loader to use
	 *
	 * @return {@code this}, for method chaining
	 */
	public AbstractBootstrapServiceRegistryBuilder with(ClassLoader classLoader) {
		if ( providedClassLoaders == null ) {
			providedClassLoaders = new ArrayList<ClassLoader>();
		}
		providedClassLoaders.add( classLoader );
		return this;
	}

	/**
	 * Adds a provided {@link ClassLoaderService} for use in class-loading and resource-lookup.
	 *
	 * @param classLoaderService The class loader service to use
	 *
	 * @return {@code this}, for method chaining
	 */
	public AbstractBootstrapServiceRegistryBuilder with(ClassLoaderService classLoaderService) {
		providedClassLoaderService = classLoaderService;
		return this;
	}

	/**
	 * Applies a named strategy implementation to the bootstrap registry.
	 *
	 * @param strategy The strategy
	 * @param name The registered name
	 * @param implementation The strategy implementation Class
	 * @param <T> Defines the strategy type and makes sure that the strategy and implementation are of
	 * compatible types.
	 *
	 * @return {@code this}, for method chaining
	 *
	 * @see org.hibernate.boot.registry.selector.spi.StrategySelector#registerStrategyImplementor(Class, String, Class)
	 */
	@SuppressWarnings( {"UnusedDeclaration"})
	public <T> AbstractBootstrapServiceRegistryBuilder withStrategySelector(Class<T> strategy, String name, Class<? extends T> implementation) {
		this.strategySelectorBuilder.addExplicitStrategyRegistration( strategy, implementation, name );
		return this;
	}

	/**
	 * Applies one or more strategy selectors announced as available by the passed announcer.
	 *
	 * @param strategyRegistrationProvider An provider for one or more available selectors
	 *
	 * @return {@code this}, for method chaining
	 *
	 * @see org.hibernate.boot.registry.selector.spi.StrategySelector#registerStrategyImplementor(Class, String, Class)
	 */
	@SuppressWarnings( {"UnusedDeclaration"})
	public AbstractBootstrapServiceRegistryBuilder withStrategySelectors(StrategyRegistrationProvider strategyRegistrationProvider) {
		for ( StrategyRegistration strategyRegistration : strategyRegistrationProvider.getStrategyRegistrations() ) {
			this.strategySelectorBuilder.addExplicitStrategyRegistration( strategyRegistration );
		}
		return this;
	}

	/**
	 * Build the bootstrap registry.
	 *
	 * @return The built bootstrap registry
	 */
	public BootstrapServiceRegistry build() {
		final ClassLoaderService classLoaderService;
		if ( providedClassLoaderService == null ) {
			// Use a set.  As an example, in JPA, OsgiClassLoader may be in both
			// the providedClassLoaders and the overridenClassLoader.
			final Set<ClassLoader> classLoaders = new HashSet<ClassLoader>();

			if ( providedClassLoaders != null )  {
				classLoaders.addAll( providedClassLoaders );
			}
			
			classLoaderService = defaultClassLoaderService(classLoaders);
		}
		else {
			classLoaderService = providedClassLoaderService;
		}

		return build(classLoaderService, strategySelectorBuilder.buildSelector( classLoaderService ));
	}

	protected ClassLoaderServiceImpl defaultClassLoaderService(final Set<ClassLoader> classLoaders) {
		return new ClassLoaderServiceImpl( classLoaders );
	}

	protected BootstrapServiceRegistry build(ClassLoaderService classLoaderService, StrategySelector strategySelector) {
		return new BootstrapServiceRegistryImpl(
				classLoaderService,
				strategySelector
		);
	}
}
