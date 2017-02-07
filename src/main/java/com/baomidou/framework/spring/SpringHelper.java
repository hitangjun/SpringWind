/**
 * Copyright (c) 2011-2014, hubin (jobob@qq.com).
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
package com.baomidou.framework.spring;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.baomidou.framework.spring.SpringContextHolder;

/**
 * <p>
 * spring 辅助类
 * </p>
 * @author   hubin
 * @date	 2016-06-28
 */
public class SpringHelper {

	/**
	 * 获取 HttpServletRequest
	 */
	public static HttpServletRequest getHttpServletRequest() {
		return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
	}


	/**
	 * <p>
	 * 从spring容器中获取 bean
	 * </p>
	 * <p>
	 *  使用的时候需要注入到 servlet-context.xml 如下设置：
	 *  <br>
	 *  <!-- 获取ApplicationContext上下文 -->
	 *	<bean id="springContextHolder" class="com.baomidou.framework.spring.SpringContextHolder" />
	 * </p>
	 * @param clazz
	 * 				bean class
	 * @return
	 */
	public static <T> T getBean( Class<T> clazz ) {
		return SpringContextHolder.getBean(clazz);
	}

}
