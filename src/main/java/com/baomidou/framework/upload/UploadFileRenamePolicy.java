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

import com.baomidou.framework.common.Base58Helper;
import com.baomidou.framework.upload.multipart.FileRenamePolicy;

/**  
 * <p>
 * 自定义COS命名策略文件
 * </p>
 * 
 * @author hubin
 * @Date 2016-04-21
 */
public class UploadFileRenamePolicy implements FileRenamePolicy {

	/* 后缀名 */
	private String suffix;


	/**
	 * 文件重命名
	 */
	public File rename( File file ) {
		StringBuffer pathName = new StringBuffer();
		pathName.append(Base58Helper.compressedUUID());
		pathName.append(getSuffix());
		file = new File(file.getParent(), pathName.toString());
		return file;
	}


	public String getSuffix() {
		return suffix;
	}


	public void setSuffix( String suffix ) {
		this.suffix = suffix;
	}
}
