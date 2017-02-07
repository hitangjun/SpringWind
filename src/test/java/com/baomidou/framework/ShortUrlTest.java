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

import com.baomidou.framework.common.ShortUrlHelper;

/**
 * <p>
 * URL 短地址生成测试
 * </p>
 * 
 * @author hubin
 * @Date 2016-04-16
 */
public class ShortUrlTest {

	public static void main( String[] args ) {
		/* 长连接 */
		String longUrl = "http://www.baomidou.com";
		String[] result = ShortUrlHelper.generate(longUrl, "Nvqqem");

		/* 打印出结果，得到 4组短链接字符串 */
		for ( int i = 0 ; i < result.length ; i++ ) {
			System.out.println("The string [" + i + "] is " + result[i]);
		}
	}

}
