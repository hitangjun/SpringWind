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
package com.baomidou.framework.upload;

import java.io.FileInputStream;
import java.io.IOException;

/**
 * <p>
 * 根据文件头处理工具类
 * </p>
 * 
 * @author hubin
 * @Date 2016-04-21
 */
public class UploadFileHeader {

	/** 
	 * <p>
	 * 上传文件的文件头  byte[] 转 String
	 * </p>
	 * 
	 * @param bytes
	 * 				字节数组
	 * @return 
	 */
	public static String bytesToHexString( byte[] bytes ) {
		StringBuilder stringBuilder = new StringBuilder();
		if ( null == bytes || bytes.length <= 0 ) {
			return null;
		}
		for ( int i = 0 ; i < bytes.length ; i++ ) {
			int v = bytes[i] & 0xFF;
			String hv = Integer.toHexString(v);
			if ( hv.length() < 2 ) {
				stringBuilder.append(0);
			}
			stringBuilder.append(hv);
		}
		return stringBuilder.toString();
	}


	/**
	 * <p>
	 * 获取文件类型
	 * </p>
	 * 
	 * @param filePath
	 * 				文件路径
	 * @return 文件头信息
	 */
	public static String getThreeBytes( String filePath ) {
		FileInputStream is = null;
		String value = null;
		try {
			is = new FileInputStream(filePath);
			byte[] b = new byte[3];
			is.read(b, 0, b.length);
			value = bytesToHexString(b);
		} catch (Exception e) {
		} finally {
			if (null != is) {
				try {
					is.close();
				} catch (IOException e) {
				}
			}
		}
		return value;
	}

}
