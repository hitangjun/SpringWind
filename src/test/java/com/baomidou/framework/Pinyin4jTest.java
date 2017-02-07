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
package com.baomidou.framework;

import com.baomidou.framework.common.Pinyin4jHelper;

/**
 * <p>
 * Pinyin4jHelper 测试
 * </p>
 * 
 * @author liuchangng@qq.com
 * @Date 2016-04-12
 */
public class Pinyin4jTest {

	public static void main( String[] args ) {
		String str = "春天的风";
		System.out.println("需要转换的字符串: " + str);

		//只取首字母
		System.out.println("所有首字母:" + Pinyin4jHelper.converterToAllFirstSpell(str));
		System.out.println("第一个结果首字母:" + Pinyin4jHelper.converterToFirstSpell(str));

		//全拼
		System.out.println("所有全拼:" + Pinyin4jHelper.converterToAllSpell(str));
		System.out.println("第一个结果全拼:" + Pinyin4jHelper.converterToSpell(str));
	}

}
