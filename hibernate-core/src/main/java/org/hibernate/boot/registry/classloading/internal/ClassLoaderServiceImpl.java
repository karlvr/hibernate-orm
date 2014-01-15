package org.hibernate.boot.registry.classloading.internal;

import java.util.Collection;

import org.hibernate.internal.util.ClassLoaderHelper;

public class ClassLoaderServiceImpl extends org.hibernate.service.boot.classloading.internal.ClassLoaderServiceImpl {

	public ClassLoaderServiceImpl() {
		super();
	}

	public ClassLoaderServiceImpl(ClassLoader classLoader) {
		super(classLoader);
	}

	public ClassLoaderServiceImpl(Collection<ClassLoader> providedClassLoaders) {
		super(providedClassLoaders);
	}

	@Override
	protected ClassLoader locateTCCL() {
		try {
			return ClassLoaderHelper.getContextClassLoader();
		}
		catch (Exception e) {
			return null;
		}
	}

}
