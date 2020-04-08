package org.su18.utils;

import sun.reflect.ReflectionFactory;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;


public class ReflectionUtil {

	public static Field getField(final Class<?> clazz, final String fieldName) throws Exception {
		try {
			Field field = clazz.getDeclaredField(fieldName);
			if (field != null)
				field.setAccessible(true);
			else if (clazz.getSuperclass() != null)
				field = getField(clazz.getSuperclass(), fieldName);

			return field;
		} catch (NoSuchFieldException e) {
			if (!clazz.getSuperclass().equals(Object.class)) {
				return getField(clazz.getSuperclass(), fieldName);
			}
			throw e;
		}
	}


	public static void setFieldValue(final Object obj, final String fieldName, final Object value) throws Exception {
		final Field field = getField(obj.getClass(), fieldName);
		field.set(obj, value);
	}
	

	public static <T> T createWithoutConstructor(Class<T> classToInstantiate)
			throws NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException {
		return createWithConstructor(classToInstantiate, Object.class, new Class[0], new Object[0]);
	}


	public static <T> T createWithConstructor(Class<T> classToInstantiate, Class<? super T> constructorClass, Class<?>[] consArgTypes,
	                                          Object[] consArgs) throws NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException {
		Constructor<? super T> objCons = constructorClass.getDeclaredConstructor(consArgTypes);
		objCons.setAccessible(true);
		Constructor<?> sc = ReflectionFactory.getReflectionFactory().newConstructorForSerialization(classToInstantiate, objCons);
		sc.setAccessible(true);
		return (T) sc.newInstance(consArgs);
	}

}

