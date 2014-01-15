package org.hibernate.boot.registry.classloading.internal;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.hibernate.cfg.AvailableSettings;
import org.hibernate.internal.CoreLogging;
import org.hibernate.internal.util.ClassLoaderHelper;
import org.jboss.logging.Logger;

public class ClassLoaderServiceImpl extends org.hibernate.service.boot.classloading.internal.ClassLoaderServiceImpl {

	private static final Logger log = CoreLogging.logger( ClassLoaderServiceImpl.class );
	
	public ClassLoaderServiceImpl() {
		super();
	}

	public ClassLoaderServiceImpl(ClassLoader classLoader) {
		super(classLoader);
	}

	public ClassLoaderServiceImpl(Collection<ClassLoader> providedClassLoaders) {
		super(providedClassLoaders);
	}
	
	/**
	 * No longer used/supported!
	 *
	 * @param configValues The config values
	 *
	 * @return The built service
	 *
	 * @deprecated No longer used/supported!
	 */
	@Deprecated
	@SuppressWarnings({"UnusedDeclaration", "unchecked", "deprecation"})
	public static ClassLoaderServiceImpl fromConfigSettings(Map configValues) {
		final List<ClassLoader> providedClassLoaders = new ArrayList<ClassLoader>();
 
		final Collection<ClassLoader> classLoaders = (Collection<ClassLoader>) configValues.get( AvailableSettings.CLASSLOADERS );
		if ( classLoaders != null ) {
			for ( ClassLoader classLoader : classLoaders ) {
				providedClassLoaders.add( classLoader );
			}
		}
 
		addIfSet( providedClassLoaders, AvailableSettings.APP_CLASSLOADER, configValues );
		addIfSet( providedClassLoaders, AvailableSettings.RESOURCES_CLASSLOADER, configValues );
		addIfSet( providedClassLoaders, AvailableSettings.HIBERNATE_CLASSLOADER, configValues );
		addIfSet( providedClassLoaders, AvailableSettings.ENVIRONMENT_CLASSLOADER, configValues );
 
		if ( providedClassLoaders.isEmpty() ) {
			log.debugf( "Incoming config yielded no classloaders; adding standard SE ones" );
			final ClassLoader tccl = staticLocateTCCL();
			if ( tccl != null ) {
				providedClassLoaders.add( tccl );
			}
			providedClassLoaders.add( ClassLoaderServiceImpl.class.getClassLoader() );
		}
 
		return new ClassLoaderServiceImpl( providedClassLoaders );
	}
 
	private static void addIfSet(List<ClassLoader> providedClassLoaders, String name, Map configVales) {
		final ClassLoader providedClassLoader = (ClassLoader) configVales.get( name );
		if ( providedClassLoader != null ) {
			providedClassLoaders.add( providedClassLoader );
		}
	}

	@Override
	protected ClassLoader locateTCCL() {
		return staticLocateTCCL();
	}

	private static ClassLoader staticLocateTCCL() {
		try {
			return ClassLoaderHelper.getContextClassLoader();
		}
		catch (Exception e) {
			return null;
		}
	}

}
