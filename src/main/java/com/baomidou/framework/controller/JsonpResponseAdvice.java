/**
 * Copyright (c) 2011-2014, dennisit.pu (dennisit@163.com)
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
package com.baomidou.framework.controller;

import org.springframework.http.MediaType;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.AbstractJsonpResponseBodyAdvice;

/**
 * <p>
 * 自动jsonp
 * </p>
 * 
 * @author dennisit.pu
 * @Date 2016-04-15
 */
@ControllerAdvice
public class JsonpResponseAdvice extends AbstractJsonpResponseBodyAdvice {

	public JsonpResponseAdvice() {
		super("callback");
	}


	@Override
	protected MediaType getContentType( MediaType contentType, ServerHttpRequest request,
			ServerHttpResponse response ) {
		return new MediaType("application", "javascript", contentType.getCharSet());
	}
}
