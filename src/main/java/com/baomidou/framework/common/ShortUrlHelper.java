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

import com.baomidou.kisso.common.encrypt.MD5;

/**
 * <p>
 * URL 短地址生成工具类
 * </p>
 * 
 * @author hubin
 * @Date 2016-04-16
 */
public class ShortUrlHelper {

	/**
	 * <p>
	 * 生成短地址算法
	 * </p>
	 * 
	 * @param url
	 * 			加密地址
	 * @param salt
	 * 			加密盐值
	 * @return
	 */
	public static String[] generate( String url, String salt ) {
		/*
		 * 要使用生成 URL 的字符
		 */
		String[] chars = new String[ ] { "a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p",
				"q", "r", "s", "t", "u", "v", "w", "x", "y", "z", "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "A",
				"B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V",
				"W", "X", "Y", "Z" };
		/*
		 * 对传入网址进行 MD5 加密
		 */
		String sMD5EncryptResult = (MD5.toMD5(salt + url));
		String hex = sMD5EncryptResult;
		String[] resUrl = new String[4];
		/*
		 * 得到 4组短链接字符串
		 */
		for ( int i = 0 ; i < 4 ; i++ ) {
			/*
			 * 把加密字符按照 8 位一组 16 进制与 0x3FFFFFFF 进行位与运算
			 */
			String sTempSubString = hex.substring(i * 8, i * 8 + 8);
			/*
			 * 这里需要使用 long 型来转换，因为 Inteper .parseInt() 只能处理 31 位 , 
			 * 首位为符号位 , 如果不用 long ，则会越界
			 */
			long lHexLong = 0x3FFFFFFF & Long.parseLong(sTempSubString, 16);
			String outChars = "";
			/*
			 * 循环获得每组6位的字符串
			 */
			for ( int j = 0 ; j < 6 ; j++ ) {
				/*
				 * 把得到的值与 0x0000003D 进行位与运算，取得字符数组 chars 索引
				 * (具体需要看chars数组的长度   以防下标溢出，注意起点为0)
				 */
				long index = 0x0000003D & lHexLong;
				/* 把取得的字符相加 */
				outChars += chars[(int) index];
				/* 每次循环按位右移 5 位 */
				lHexLong = lHexLong >> 5;
			}
			/* 把字符串存入对应索引的输出数组 */
			resUrl[i] = outChars;
		}
		return resUrl;
	}

}
