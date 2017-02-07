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
package com.baomidou.framework;

import com.baomidou.framework.common.FileHashHelper;


/**
 * 文件 Hash 帮助类测试
 * <p>
 * @author   hubin
 * @Date	 2014-8-11
 */
public class FileHashTest {

	/**
	 * 测试
	 */
	public static void main( String[] args ) throws Exception {
		long start = System.currentTimeMillis();
		System.out.println("开始计算文件MD5值,请稍后...");
		System.out.println("MD5:" + FileHashHelper.getMD5Hash("E:/Office_Visio_Pro_2007.iso"));
		long end = System.currentTimeMillis();
		System.out.println("一共耗时:" + (end - start) + "毫秒");
	}
}
