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

import java.io.File;
import java.util.HashMap;

/**
 * <p>
 * Cos上传文件信息
 * </p>
 * 
 * @author hubin
 * @Date 2016-04-21
 */
public class UploadFile {

	/* 名称 */
	private String filename;

	/* 后缀名 */
	private String suffix;

	/* 上传文件目录 */
	private String dir;

	/* 上传名称 */
	private String original;

	/* 上传类型 */
	private String type;

	/* 文件字节数 */
	private long size;

	/* 文件上传码 */
	private UploadCode uploadCode = UploadCode.NORMAL;

	/* 上传携带参数 */
	private HashMap<String, String> paramParts;


	/**
	 * 是否上传成功
	 */
	public boolean isSuccess() {
		if ( UploadCode.NORMAL == getUploadCode() ) {
			return true;
		}
		return false;
	}


	/**
	 * 文件路径
	 */
	public String getFileUrl() {
		if ( dir == null || filename == null ) {
			return null;
		} else {
			return dir + File.separator + filename;
		}
	}


	/**
	 * 上传文件
	 */
	public File getFile() {
		if ( dir == null || filename == null ) {
			return null;
		} else {
			return new File(dir + File.separator + filename);
		}
	}


	/**
	 * 删除上传文件
	 */
	public boolean delFile() {
		File file = getFile();
		if ( file != null ) {
			return file.delete();
		}
		return false;
	}


	public String getContentType() {
		return type;
	}


	public String getFilesystemName() {
		return filename;
	}


	public String getOriginalFileName() {
		return original;
	}


	public String getFilename() {
		return filename;
	}


	public void setFilename( String filename ) {
		this.filename = filename;
	}


	public String getSuffix() {
		return suffix;
	}


	public void setSuffix( String suffix ) {
		this.suffix = suffix;
	}


	public String getDir() {
		return dir;
	}


	public void setDir( String dir ) {
		this.dir = dir;
	}


	public String getOriginal() {
		return original;
	}


	public void setOriginal( String original ) {
		this.original = original;
	}


	public String getType() {
		return type;
	}


	public void setType( String type ) {
		this.type = type;
	}


	public long getSize() {
		return size;
	}


	public void setSize( long size ) {
		this.size = size;
	}


	public UploadCode getUploadCode() {
		return uploadCode;
	}


	public void setUploadCode( UploadCode uploadCode ) {
		this.uploadCode = uploadCode;
	}


	public HashMap<String, String> getParamParts() {
		return paramParts;
	}


	public void setParamParts( HashMap<String, String> paramParts ) {
		this.paramParts = paramParts;
	}

}
