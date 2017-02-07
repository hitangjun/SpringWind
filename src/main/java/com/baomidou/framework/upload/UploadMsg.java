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

/**
 * <p>
 * 上传信息
 * </p>
 * 
 * @author hubin
 * @Date 2016-04-21
 */
public class UploadMsg {

	/**
	 * 是否成功
	 */
	private boolean success = false;

	/* 文件字节数 */
	private long size;

	/* 文件保存路径 */
	private String url;

	/**
	 * 提示信息
	 */
	private String msg = "upload failed.";


	public boolean isSuccess() {
		return success;
	}


	public void setSuccess( boolean success ) {
		this.success = success;
	}


	public long getSize() {
		return size;
	}


	public void setSize( long size ) {
		this.size = size;
	}


	public String getUrl() {
		return url;
	}


	public void setUrl( String url ) {
		this.url = url;
	}


	public String getMsg() {
		return msg;
	}


	public void setMsg( String msg ) {
		this.msg = msg;
	}

}
