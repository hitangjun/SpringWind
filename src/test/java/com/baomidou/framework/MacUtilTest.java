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
package com.baomidou.framework;

import com.baomidou.framework.common.util.MacUtil;

/**
 * <p>
 * MAC地址工具测试
 * </p>
 * 
 * @author hubin
 * @version 2016-04-16
 */
public class MacUtilTest {

	/**
	 * 测试用的main方法.
	 * 
	 * @param argc
	 *            运行参数.
	 */
	public static void main(String[] argc) {
		String os = MacUtil.getOSName();
		System.out.println("os: " + os);
		if (os.startsWith("windows")) {
			String mac = MacUtil.getWindowsMACAddress();
			System.out.println("mac: " + mac);
		} else if (os.startsWith("linux")) {
			String mac = MacUtil.getLinuxMACAddress();
			System.out.println("mac: " + mac);
		} else {
			String mac = MacUtil.getUnixMACAddress();
			System.out.println("mac: " + mac);
		}
	}
}
