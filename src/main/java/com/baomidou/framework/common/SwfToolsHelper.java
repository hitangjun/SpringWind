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
package com.baomidou.framework.common;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.baomidou.kisso.common.util.EnvUtil;

/**
 * 
 * PDF 转 SWF 辅助类
 * 
 * <p>
 * SwfTools 转换工具
 * 官网： http://www.swftools.org/download.html
 * </p>
 * <p>
 * Windows 下载地址： http://www.swftools.org/swftools-2013-04-09-1007.exe
 * </p>
 * 
 * @author hubin
 * @Date 2016-04-19
 */
public class SwfToolsHelper {
	
	protected static Logger logger = LoggerFactory.getLogger(SwfToolsHelper.class);

	private static final String CMD_WIN = "C:/Program Files (x86)/SWFTools/pdf2swf.exe -s flashversion=9 %s -o %s";

	private static final String CMD_LINUX = "pdf2swf -s languagedir=/usr/local/xpdf-chinese-simplified -T 9 -s poly2bitmap -s zoom=150 -s flashversion=9 %s -o %s";


	/**
	 * 
	 * PDF 转 SWF
	 *  
	 * @param sourcePath 
	 * 				源pdf文件
	 * @param destPath
	 * 				目标路径
	 * @param fileName
	 * 				swf文件名如：xxx.swf
	 * @return
	 * @throws IOException
	 */
	public static int convertPDF2SWF( String sourcePath, String destFile ) throws IOException {
		/**
		 * 源文件不存在则返回  
		 */
		File source = new File(sourcePath);
		if ( !source.exists() ) {
			return -1;
		}

		/**
		 * 调用pdf2swf命令进行转换  
		 */
		String execCmd = String.format(EnvUtil.isLinux() ? CMD_LINUX : CMD_WIN, sourcePath, destFile);
		logger.debug("exec pdf2swf: %s", execCmd);
		Process pro = Runtime.getRuntime().exec(execCmd);

		BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(pro.getInputStream()));
		while ( bufferedReader.readLine() != null )
			;

		try {
			pro.waitFor();
		} catch ( InterruptedException e ) {
			logger.error("SwfToolsHelper.convertPDF2SWF error.", e);
			return -1;
		}
		return pro.exitValue();
	}

}
