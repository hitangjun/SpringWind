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

import com.baomidou.framework.common.IEnum;

/**
 * <p>
 * 上传编码
 * </p>
 * 
 * @author hubin
 * @Date 2016-04-21
 */
public enum UploadCode implements IEnum {

	NORMAL(0, "upload success."), ILLEGAL_EXT(1, "upload illegal file ext."), EXCEPTION(3, "upload exception.");

	private final int key;

	private final String desc;


	private UploadCode( int key, String desc ) {
		this.key = key;
		this.desc = desc;
	}


	@Override
	public int key() {
		return key;
	}


	@Override
	public String desc() {
		return desc;
	}

}
