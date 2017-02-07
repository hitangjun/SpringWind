/**
 * Copyright (c) 2011-2014, L.cm (596392912@qq.com).
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
package com.baomidou.framework.aop;

import com.baomidou.framework.annotations.DataSourceManage;
import com.baomidou.framework.datasource.DynamicDataSource;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;

/**
 * 多数据源切换Aop
 */
@Aspect
public class DataSourceAop {

    @Around("@annotation(dataSourceManage)")
    public Object doAround(ProceedingJoinPoint joinPoint, DataSourceManage dataSourceManage) throws Throwable {
        Object retVal = null;
        boolean selectedDataSource = false;
        try {
            if (null != dataSourceManage) {
                String dbName = dataSourceManage.name();
                int dbSize = dataSourceManage.dbSize();
                DynamicDataSource.use(dbName, dbSize);
                selectedDataSource = true;
            }
            retVal = joinPoint.proceed();
        } catch (Throwable e) {
            throw e;
        } finally {
            if (selectedDataSource) {
                DynamicDataSource.reset();
            }
        }
        return retVal;
    }

}