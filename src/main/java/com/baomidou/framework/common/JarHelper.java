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
package com.baomidou.framework.common;

import org.apache.commons.lang.StringUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

/**
 * <p>
 * Jar 辅助工具类
 * </p>
 * 
 * @author hubin
 * @Date 2016-04-09
 */
public class JarHelper {

	public static List<String> listFiles(JarFile jarFile, String endsWith) {
		if (jarFile == null || StringUtils.isEmpty(endsWith)) {
			return null;
		}
		List<String> files = new ArrayList<String>();
		Enumeration<JarEntry> entries = jarFile.entries();
		while (entries.hasMoreElements()) {
			JarEntry entry = entries.nextElement();
			String name = entry.getName();
			if (name.endsWith(endsWith)) {
				files.add(name);
			}
		}
		return files;
	}

	public static List<String> readLines(JarFile jarFile, String fileName) throws IOException {
		if (jarFile == null || StringUtils.isEmpty(fileName)) {
			return null;
		}
		List<String> lines = new ArrayList<String>();
		JarEntry entry = jarFile.getJarEntry(fileName);
		InputStream inputStream = jarFile.getInputStream(entry);
		InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
		BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
		String line;
		while ((line = bufferedReader.readLine()) != null) {
			lines.add(line);
		}
		bufferedReader.close();
		inputStreamReader.close();
		return lines;
	}

}
