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
package com.baomidou.framework.velocity.tools;

import org.apache.velocity.tools.ConversionUtils;
import org.apache.velocity.tools.config.DefaultKey;
import org.apache.velocity.tools.generic.FormatConfig;

import com.baomidou.framework.common.RelativeDateFormat;

/**
 * <p>
 * velocity 相对日期格式工具类
 * <br>
 * 使用方法：$relativeDate.format($!{testDate})
 * </p>
 * <p>
 * 例如： #relativeDateFormat(传入格式日期) 【 输出：1小时前 】 
 * </p>
 * <p>
 * 另外一种选择使用标签：
 * com.baomidou.framework.velocity.directive.RelativeDateFormatDirective
 * </p>
 * @author hubin
 * @Date 2016-05-05
 */
@DefaultKey("relativeDate")
public class RelativeDateTool extends FormatConfig {

	/**
	 * <p>
	 * 日期格式为 xx 前（例如：1小时前）
	 * </p>
	 * 
	 * @param obj
	 *            待格式化日期对象
	 * @return
	 */
	public String format( Object obj ) {
		return RelativeDateFormat.format(ConversionUtils.toDate(obj));
	}

}
