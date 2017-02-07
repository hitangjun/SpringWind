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
package com.baomidou.framework.velocity;

import com.baomidou.kisso.common.util.EnvUtil;

/**
 * <p>
 * 运行环境<br>
 * online 线上 ， dev 开发 ， test 测试
 * </p>
 * @author hubin
 * @Date 2016-04-19
 */
public class RunEnvironment {

	private static final String ONLINE = "online";

	private static final String DEV = "dev";

	private static final String TEST = "test";

	/**
	 * 运行环境配置变量名
	 */
	private String configEnv = "spring_runmode";

	private static String RUN_MODE = null;


	/**
	 * 获取当前运行模式，默认 DEV 开发模式。
	 * <p>
	 * 首先环境变量中获取，变量名：spring_runmode 变量值：dev <br>
	 * 如果不存在 JVM -D选项 参数中获取，例如：-Dspring_runmode=dev <br>
	 * </p>
	 */
	public String getRunMode() {
		if ( RUN_MODE == null ) {
			/* 环境变量获取配置 */
			String mode = System.getenv(getConfigEnv());
			if ( mode == null || "".equals(mode) ) {
				mode = System.getProperty(getConfigEnv());
			}
			if ( mode != null ) {
				if ( ONLINE.equals(mode) ) {
					mode = ONLINE;
				} else if ( DEV.equals(mode) ) {
					mode = DEV;
				} else if ( TEST.equals(mode) ) {
					mode = TEST;
				}
			} else {
				/* 默认 Windows 认为是开发环境 */
				if ( EnvUtil.isLinux() ) {
					mode = ONLINE;
				} else {
					mode = DEV;
				}
			}
			System.err.println("-Dspring_runmode=" + mode);
			RUN_MODE = mode;
		}
		return RUN_MODE;
	}


	public String getConfigEnv() {
		return configEnv;
	}


	public void setConfigEnv( String configEnv ) {
		this.configEnv = configEnv;
	}

}
