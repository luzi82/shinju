package com.luzi82.common;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

public interface Factory<I> {

	public I create();

	public static class C<I> implements Factory<I> {

		private final Constructor<I> mConstructor;

		public C(Constructor<I> aConstructor) {
			mConstructor = aConstructor;
		}

		public C(Class<I> aClass) {
			try {
				mConstructor = aClass.getConstructor();
			} catch (NoSuchMethodException e) {
				throw new Error(e);
			}
		}

		@Override
		public I create() {
			try {
				return mConstructor.newInstance();
			} catch (InstantiationException e) {
				throw new Error(e);
			} catch (IllegalAccessException e) {
				throw new Error(e);
			} catch (IllegalArgumentException e) {
				throw new Error(e);
			} catch (InvocationTargetException e) {
				throw new Error(e);
			}
		}

		public static <I> C<I> create(Class<I> aClass) {
			return new C<I>(aClass);
		}

	}

}