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

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.baomidou.kisso.common.encrypt.Byte2Hex;

/**
 * <p>
 * 文件 Hash 帮助类
 * </p>
 * 
 * @author hubin
 * @Date 2016-04-16
 */
public class FileHashHelper {

	private final static Logger logger = LoggerFactory.getLogger(FileHashHelper.class);

	/**
	 * 获取文件文件MD5 Hash值
	 * <p>
	 * 
	 * @param pathName
	 *            文件绝对地址
	 * @return
	 * @throws NoSuchAlgorithmException
	 * @throws IOException
	 */
	public static String getMD5Hash(String fileName) throws NoSuchAlgorithmException, IOException {
		return getHash(fileName, "MD5");
	}

	/**
	 * 获取文件文件MD5 Hash值
	 * <p>
	 * 
	 * @param InputStream
	 *            文件输入流
	 * @return
	 * @throws IOException
	 * @throws NoSuchAlgorithmException
	 */
	public static String getMD5Hash(InputStream ins) throws NoSuchAlgorithmException, IOException {
		return getHash(ins, "MD5");
	}

	/**
	 * 获取文件文件Hash值
	 * <p>
	 * 
	 * @param pathName
	 *            文件绝对地址
	 * @param hashType
	 *            MessageDigest 加密算法
	 * @return
	 * @throws IOException
	 * @throws NoSuchAlgorithmException
	 */
	public static String getHash(String pathName, String hashType) throws IOException, NoSuchAlgorithmException {
		File f = new File(pathName);
		logger.debug(" -------------------------------------------------------------------------------");
		logger.debug("|当前文件名称:" + f.getName());
		logger.debug("|当前文件大小:" + (f.length() / 1024 / 1024) + "MB");
		logger.debug("|当前文件路径[绝对]:" + f.getAbsolutePath());
		logger.debug("|当前文件路径[---]:" + f.getCanonicalPath());
		logger.debug("|当前文件最后一次被修改时间[---]:" + f.lastModified());
		logger.debug(" -------------------------------------------------------------------------------");
		return getHash(new FileInputStream(f), hashType);
	}

	/**
	 * 获取文件文件Hash值
	 * <p>
	 * 
	 * @param InputStream
	 *            文件输入流
	 * @param hashType
	 *            MessageDigest 加密算法
	 * @return
	 * @throws IOException
	 * @throws NoSuchAlgorithmException
	 */
	public static String getHash(InputStream ins, String hashType) throws IOException, NoSuchAlgorithmException {
		if (ins == null) {
			// 输入流为空返回 null
			return null;
		}
		byte[] buffer = new byte[8192];
		MessageDigest md5 = MessageDigest.getInstance(hashType);
		try {
			int len;
			while ((len = ins.read(buffer)) != -1) {
				md5.update(buffer, 0, len);
			}
		} catch (Exception e) {
			logger.error("getHash error: ", e);
		} finally {
			ins.close();
		}
		return Byte2Hex.byte2Hex(md5.digest());
	}

}
