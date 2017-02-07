/**
 * Copyright (c) 2011-2020, hubin (jobob@qq.com).
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

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.core.type.classreading.CachingMetadataReaderFactory;
import org.springframework.core.type.classreading.MetadataReader;
import org.springframework.core.type.classreading.MetadataReaderFactory;
import org.springframework.util.ClassUtils;

/**
 * <p>
 * spring 扫描辅助类
 * </p>
 * 
 * @author hubin
 * @date 2016-08-12
 */
public class SpringScanPackage {

	/**
	 * 扫描的 class 集合
	 */
	public static Map<String, Set<String>> SCAN_CLASS_MAP = new ConcurrentHashMap<String, Set<String>>();

	/**
	 * 扫描信息
	 */
	private ScanInfo[] scanInfos;

	public SpringScanPackage() {

	}

	public void scanPackage() {
		if (null != scanInfos && scanInfos.length >= 1) {
			for (ScanInfo scanInfo : scanInfos) {
				Set<String> clazzSet = findPackageClass(scanInfo.getScanPackages());
				SCAN_CLASS_MAP.put(scanInfo.getScanFlag(), clazzSet);
			}
		}
	}

	public ScanInfo[] getScanInfos() {
		return scanInfos;
	}

	public void setScanInfos(ScanInfo[] scanInfos) {
		this.scanInfos = scanInfos;
	}

	/**
	 * <p>
	 * 根据扫描包的路径,查询下面的所有类
	 * </p>
	 *
	 * @param scanPackages
	 *            扫描的package路径
	 * @return
	 */
	public static Set<String> findPackageClass(String scanPackages) {
		Set<String> clazzSet = new HashSet<String>();
		ResourcePatternResolver resolver = (ResourcePatternResolver) new PathMatchingResourcePatternResolver();
		MetadataReaderFactory metadataReaderFactory = new CachingMetadataReaderFactory(resolver);
		String pkg = ResourcePatternResolver.CLASSPATH_ALL_URL_PREFIX
				+ ClassUtils.convertClassNameToResourcePath(scanPackages) + "/**/*.class";
		/*
		 * 将加载多个绝对匹配的所有Resource
		 * 将首先通过ClassLoader.getResource("META-INF")加载非模式路径部分，然后进行遍历模式匹配，排除重复包路径
		 */
		try {
			Resource[] resources = resolver.getResources(pkg);
			if (resources != null && resources.length > 0) {
				for (Resource resource : resources) {
					if (resource.isReadable()) {
						MetadataReader metadataReader = metadataReaderFactory.getMetadataReader(resource);
						if (metadataReader != null) {
							clazzSet.add(metadataReader.getClassMetadata().getClassName());
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return clazzSet;
	}

}

class ScanInfo {
	/**
	 * 扫描功能标记
	 */
	private String scanFlag;

	/**
	 * 扫描包路径
	 */
	private String scanPackages;

	public String getScanFlag() {
		return scanFlag;
	}

	public void setScanFlag(String scanFlag) {
		this.scanFlag = scanFlag;
	}

	public String getScanPackages() {
		return scanPackages;
	}

	public void setScanPackages(String scanPackages) {
		this.scanPackages = scanPackages;
	}
}
