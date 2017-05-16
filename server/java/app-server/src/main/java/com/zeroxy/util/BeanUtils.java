package com.zeroxy.util;

import java.beans.PropertyDescriptor;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.FatalBeanException;
import org.springframework.util.Assert;
import org.springframework.util.ClassUtils;

/**
 * Bean工具类。用于直接操作类、对象的属性或方法。
 */
public class BeanUtils {
	public static void copyProperties(Object source,Object target,Boolean ignoreNullProperties,String... ignoreProperties){
		
		Assert.notNull(source, "Source must not be null");
		Assert.notNull(target, "Target must not be null");

		Class<?> actualEditable = target.getClass();
		
		PropertyDescriptor[] targetPds = org.springframework.beans.BeanUtils.getPropertyDescriptors(actualEditable);
		List<String> ignoreList = (ignoreProperties != null) ? Arrays.asList(ignoreProperties) : null;

		for (PropertyDescriptor targetPd : targetPds) {
			Method writeMethod = targetPd.getWriteMethod();
			if (writeMethod != null && (ignoreProperties == null || (!ignoreList.contains(targetPd.getName())))) {
				PropertyDescriptor sourcePd = org.springframework.beans.BeanUtils.getPropertyDescriptor(source.getClass(), targetPd.getName());
				if (sourcePd != null) {
					Method readMethod = sourcePd.getReadMethod();
					if (readMethod != null &&
							ClassUtils.isAssignable(writeMethod.getParameterTypes()[0], readMethod.getReturnType())) {
						try {
							if (!Modifier.isPublic(readMethod.getDeclaringClass().getModifiers())) {
								readMethod.setAccessible(true);
							}
							Object value = readMethod.invoke(source);
							if (!Modifier.isPublic(writeMethod.getDeclaringClass().getModifiers())) {
								writeMethod.setAccessible(true);
							}
							if(value != null || !ignoreNullProperties){
								writeMethod.invoke(target, value);
							}
						}
						catch (Throwable ex) {
							throw new FatalBeanException(
									"Could not copy property '" + targetPd.getName() + "' from source to target", ex);
						}
					}
				}
			}
		}
	}

	public static void copyProperties(Object source,Object target) {
		copyProperties(source, target,true,(String[]) null);	
	}
	
	
	/**
	 * 获取类中注有指定标注的属性集合。
	 * 
	 * @param targetClass
	 *            类
	 * @param annotationClassOnField
	 *            标注
	 * @return 返回注有指定标注的属性集合。
	 */
	public static List<Field> findField(Class<?> targetClass,
			Class<? extends Annotation> annotationClassOnField) {
		Assert.notNull(targetClass);
		Assert.notNull(annotationClassOnField);
		List<Field> fields = new ArrayList<Field>();
		for (Field field : getAllDeclaredField(targetClass)) {
			if (field.isAnnotationPresent(annotationClassOnField)) {
				fields.add(field);
			}
		}
		return fields;
	}
	
	
	/**
	 * 获取对象中指定属性的值。
	 * 
	 * @param target
	 *            对象
	 * @param field
	 *            属性
	 * @return 返回对象中指定属性的值。
	 */
	public static Object getField(Object target, Field field) {
		try {
			boolean accessible = field.isAccessible();
			field.setAccessible(true);
			Object result = field.get(target);
			field.setAccessible(accessible);
			return processHibernateLazyField(result);
		} catch (Exception e) {
			throw new IllegalStateException("获取对象的属性[" + field.getName()
					+ "]值失败", e);
		}
	}
	

	/**
	 * 获取对象中指定属性的值，支持多层级。
	 * 
	 * @param target
	 *            对象
	 * @param fieldName
	 *            属性名
	 * @return 返回对象中指定属性的值。
	 */
	public static Object getField(Object target, String fieldName) {
		Assert.notNull(target);
		if (fieldName.contains(".")) {
			return getNestedField(target, fieldName);
		} else {
			return getDirectField(target, fieldName);
		}
	}
	
	/**
	 * 处理Hibernate懒加载属性。
	 * 
	 * @param fieldValue
	 *            属性值
	 * @return 如果是Hibernate懒加载属性则执行代理方法返回实际的属性对象，否则直接返回。
	 */
	private static Object processHibernateLazyField(Object fieldValue) {
		try {
			Class<?> hibernateProxyClass = Class
					.forName("org.hibernate.proxy.HibernateProxy");
			if (hibernateProxyClass.isAssignableFrom(fieldValue.getClass())) {
				Method method = fieldValue.getClass().getMethod(
						"getHibernateLazyInitializer");
				Object lazyInitializer = method.invoke(fieldValue);
				method = lazyInitializer.getClass().getMethod(
						"getImplementation");
				return method.invoke(lazyInitializer);
			} else {
				return fieldValue;
			}
		} catch (Exception e) {
			return fieldValue;
		}
	}
	
	/**
	 * 获取对象中指定单层级属性的值。
	 * 
	 * @param target
	 *            对象
	 * @param fieldName
	 *            属性名
	 * @return 返回对象中指定属性的值。
	 */
	private static Object getDirectField(Object target, String fieldName) {
		Assert.notNull(target);
		return getField(target, findDirectField(target.getClass(), fieldName));
	}

	/**
	 * 获取对象中指定多层级属性的值。
	 * 
	 * @param target
	 *            对象
	 * @param fieldName
	 *            属性名
	 * @return 返回对象中指定属性的值。
	 */
	private static Object getNestedField(Object target, String fieldName) {
		Assert.notNull(target);
		String[] nestedFieldNames = fieldName.split("\\.");
		for (String nestedFieldName : nestedFieldNames) {
			target = getDirectField(target, nestedFieldName);
		}
		return target;
	}
	
	/**
	 * 获取类中指定名称的单层级属性。
	 * 
	 * @param targetClass
	 *            类
	 * @param fieldName
	 *            属性名
	 * @return 返回对应的属性，如果没找到返回null。
	 */
	private static Field findDirectField(Class<?> targetClass, String fieldName) {
		Assert.notNull(targetClass);
		for (Field field : getAllDeclaredField(targetClass)) {
			if (fieldName.equals(field.getName())) {
				return field;
			}
		}
		return null;
	}
	
	/**
	 * 递归获取类的Field，直到其父类为Object类。子类的Field排列在父类之前。
	 * 
	 * @param targetClass
	 *            类
	 * @param excludeFieldNames
	 *            排除的属性名称
	 * @return 返回Field列表。
	 */
	public static List<Field> getAllDeclaredField(Class<?> targetClass,
			String... excludeFieldNames) {
		List<Field> fields = new ArrayList<Field>();
		for (Field field : targetClass.getDeclaredFields()) {
			if (Arrays.asList(excludeFieldNames).contains(field.getName())) {
				continue;
			}
			fields.add(field);
		}
		Class<?> parentClass = targetClass.getSuperclass();
		if (parentClass != Object.class) {
			fields.addAll(getAllDeclaredField(parentClass, excludeFieldNames));
		}
		return fields;
	}
}
