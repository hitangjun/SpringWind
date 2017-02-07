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
package com.baomidou.framework.velocity.directive;

import java.io.IOException;
import java.io.Writer;
import java.util.Date;

import org.apache.velocity.context.InternalContextAdapter;
import org.apache.velocity.exception.MethodInvocationException;
import org.apache.velocity.exception.ParseErrorException;
import org.apache.velocity.exception.ResourceNotFoundException;
import org.apache.velocity.runtime.parser.node.Node;
import org.apache.velocity.runtime.parser.node.SimpleNode;
import org.apache.velocity.tools.view.ViewToolContext;

import com.baomidou.framework.common.RelativeDateFormat;
import com.baomidou.framework.velocity.AbstractDirective;

/**
 * <p>
 * velocity 相对日期格式标签
 * </p>
 * <p>
 * 例如： #relativeDateFormat($!{testDate}) 【 输出：1小时前 】 
 * </p>
 * @author hubin
 * @Date 2016-05-05
 */
public class RelativeDateFormatDirective extends AbstractDirective {

	@Override
	public String getName() {
		return "relativeDateFormat";
	}

	@Override
	public int getType() {
		return LINE;
	}

	@Override
	protected boolean doRender(InternalContextAdapter internalContext, ViewToolContext context, Writer writer,
			Node node) throws IOException, ResourceNotFoundException, ParseErrorException, MethodInvocationException {
		SimpleNode sn = (SimpleNode) node.jjtGetChild(0);
		Date date = (Date) sn.value(internalContext);
		writer.write(RelativeDateFormat.format(date));
		return true;
	}

}
