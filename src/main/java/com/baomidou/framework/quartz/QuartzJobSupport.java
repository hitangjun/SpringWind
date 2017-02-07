/**
 * Copyright (c) 2011-2014, yyn_0210@sina.com.
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
package com.baomidou.framework.quartz;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.quartz.QuartzJobBean;

/**
 * <p>
 * QuartzJob 辅助类
 * </p>
 * 
 * @author yyn_0210@sina.com
 * @Date 2016-04-13
 */
public abstract class QuartzJobSupport extends QuartzJobBean {

	private ApplicationContext applicationContext;


	/**
	 * 从SchedulerFactoryBean注入的applicationContext.
	 */
	public void setApplicationContext( ApplicationContext applicationContext ) {
		this.applicationContext = applicationContext;
	}


	public <T> T getBean( String beanName, Class<T> clazz ) {
		return this.applicationContext.getBean(beanName, clazz);
	}


	@Override
	protected void executeInternal( JobExecutionContext context ) throws JobExecutionException {
		innerIter(context);
	}


	public abstract void innerIter( JobExecutionContext context );

}
