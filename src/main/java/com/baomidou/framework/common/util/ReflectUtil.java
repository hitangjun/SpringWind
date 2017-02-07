/**
 * Copyright (c) 2011-2014, liuchangng@qq.com.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package com.baomidou.framework.common.util;

import java.lang.reflect.Method;

/**
 * <p>
 * 反射工具类.
 * </p>
 * 
 * @author hubin
 * @Date 2016-04-16
 */
public class ReflectUtil {

	/**
	 * 根据对象，返回一个class对象，用于获取方法
	 */
	public static Class<?> getClass(Object obj) {
		try {
			return Class.forName(obj.getClass().getName());
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 根据对象，获取某个方法
	 * 
	 * @param obj
	 *            对象
	 * @param methodName
	 *            方法名
	 * @param parameterTypes
	 *            该方法需传的参数类型，如果不需传参，则不传
	 */
	public static Method getMethod(Object obj, String methodName, Class<?>... parameterTypes) {
		try {
			Method method = getClass(obj).getDeclaredMethod(methodName, parameterTypes);
			method.setAccessible(true);
			return method;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public static Method getMethod(Class<?> cls, String methodName, Class<?>... parameterTypes) {
		try {
			Method method = cls.getDeclaredMethod(methodName, parameterTypes);
			method.setAccessible(true);
			return method;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 直接传入对象、方法名、参数，即可使用该对象的隐藏方法
	 * 
	 * @param obj
	 * @param methodName
	 * @param parameter
	 */
	public static Object invoke(Object obj, String methodName, Object... parameter) {
		Class<?>[] parameterTypes = new Class<?>[parameter.length];
		for (int i = 0; i < parameterTypes.length; i++) {
			parameterTypes[i] = parameter[i].getClass();
		}
		try {
			return getMethod(obj, methodName, parameterTypes).invoke(obj, parameter);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 直接传入类名、方法名、参数，即可使用该对象的隐藏静态方法
	 * 
	 * @param cls
	 * @param methodName
	 * @param parameter
	 */
	public static Object invoke(Class<?> cls, String methodName, Object... parameter) {
		Class<?>[] parameterTypes = new Class<?>[parameter.length];
		for (int i = 0; i < parameterTypes.length; i++) {
			parameterTypes[i] = parameter[i].getClass();
		}
		try {
			return getMethod(cls, methodName, parameterTypes).invoke(null, parameter);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

}
