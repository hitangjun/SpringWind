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

import com.baomidou.framework.common.Base58Helper;
import com.baomidou.mybatisplus.toolkit.IdWorker;

/**
 * <p>
 * Base58 压缩算法测试类
 * </p>
 * 
 * @author hubin
 * @Date 2016-04-21
 */
public class Base58Test {

	public static void main( String[] args ) {
		long id = IdWorker.getId();
		System.out.println(id);
		String code = (Base58Helper.encode(id));
		System.out.println(code);
		System.out.println(Base58Helper.decode(code));
		System.out.println(" Base58 UUID = " + Base58Helper.compressedUUID());
	}

}
