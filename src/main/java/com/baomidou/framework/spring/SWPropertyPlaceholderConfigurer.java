/**
 * Copyright (c) 2011-2014, jiazhang (676002187@qq.com).
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

import java.io.File;
import java.io.FilenameFilter;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;

/**
 * 通过java.exe运行时设置-Dsw.home=D:/development形式引入环境配目录，将会自动加载目录下面所有的*.properties文件. 主要分为三套properties配置文件:
 * <ol>
 * <li>开发---D:/development/jdbc.properties</li>
 * <li>测试---D:/test/jdbc.properties</li>
 * <li>生产---D:/online/jdbc.properties</li>
 * </ol>
 * 
 * <p>
 * --------------------   使用配置       -------------------------
 * <bean class="com.baomidou.framework.spring.SWPropertyPlaceholderConfigurer">
 *     <property name="swDeployConfigPathKey" value="sw.home" />
 * </bean>
 * -------------------------------------------------------
 * </p>
 * 
 * 目的：便于使用"统一war包+三套properties配置文件"来实现布署切换.
 * 
 * @author jiazhang
 * @Date 2016-05-16
 */
public class SWPropertyPlaceholderConfigurer extends PropertyPlaceholderConfigurer {
    private static final String PROPERTIES_SUFFX = ".properties";

    /**
     * 加载从Java.exe的命令行中以-Dsw.home=D:/development形式注入的设置目录.
     * 
     * 在spring中类似如下配置： <property name="swDeployConfigPathKey" value="sw.home"/>.
     * 
     * @param propertiesPath
     *            JVM参数-Dsw.home=D:/development的引导key, 即为sw.home
     */
    public void setSwDeployConfigPathKey(String propertiesPath) {
        String configFilePath = System.getProperty(propertiesPath);
        if (configFilePath == null)
            throw new IllegalArgumentException(propertiesPath + "对应的值为null, 可能是java.exe运行时, 没有设置配置文件的目录-D" + propertiesPath + "=D:/xxx");
        try {
            // 检查目录
            File file = new File(configFilePath);
            if (!file.exists() || !file.isDirectory())
                throw new IllegalArgumentException(propertiesPath + "对应的值为" + configFilePath + ", 它可能不存在，或者不是一个目录");
            // 找到其下所有属性文件
            File[] listPropertiesFiles = file.listFiles(new FilenameFilter() {
                @Override
                public boolean accept(File dir, String name) {
                    if (name.endsWith(PROPERTIES_SUFFX)) {
                        return true;
                    }
                    return false;
                }
            });
            // 构造UrlResource资源
            List<UrlResource> listResource = null;
            if (listPropertiesFiles != null && listPropertiesFiles.length > 0) {
                listResource = new ArrayList<UrlResource>();
                for (File propertiesFile : listPropertiesFiles) {
                    URI uri = propertiesFile.toURI();
                    UrlResource urlResource = new UrlResource(uri);
                    listResource.add(urlResource);
                }
            }
            // 设值
            if (listResource != null) {
                this.setLocations((Resource[]) listResource.toArray(new UrlResource[listResource.size()]));
            }
        } catch (Exception e) {
            throw new IllegalArgumentException(e);
        }
    }
}
