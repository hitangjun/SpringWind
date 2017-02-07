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
package com.baomidou.framework.oauth2.apis;

import com.github.scribejava.core.builder.api.DefaultApi20;
import com.github.scribejava.core.extractors.OAuth2AccessTokenExtractor;
import com.github.scribejava.core.extractors.TokenExtractor;
import com.github.scribejava.core.model.OAuth2AccessToken;
import com.github.scribejava.core.model.OAuthConfig;
import com.github.scribejava.core.model.OAuthConstants;
import com.github.scribejava.core.model.Verb;
import com.github.scribejava.core.utils.OAuthEncoder;
import com.github.scribejava.core.utils.Preconditions;

/**
 * <p>
 * GitHub 登录 API 类
 * </p>
 * @author hubin
 * @Date 2016-05-09
 */
public class GitHubApi extends DefaultApi20 {

	private static final String AUTHORIZE_URL = "https://github.com/login/oauth/authorize?client_id=%s&redirect_uri=%s";


	protected GitHubApi() {

	}

	private static class InstanceHolder {

		private static final GitHubApi INSTANCE = new GitHubApi();
	}


	public static GitHubApi instance() {
		return InstanceHolder.INSTANCE;
	}


	@Override
	public Verb getAccessTokenVerb() {
		return Verb.GET;
	}


	@Override
	public String getAccessTokenEndpoint() {
		return "https://github.com/login/oauth/access_token";
	}


	@Override
	public String getAuthorizationUrl( OAuthConfig config ) {
		Preconditions.checkValidUrl(config.getCallback(),
			"Must provide a valid url as callback. GitHub does not support OOB");
		final StringBuilder sb = new StringBuilder(
				String.format(AUTHORIZE_URL, config.getApiKey(), OAuthEncoder.encode(config.getCallback())));
		if ( config.hasScope() ) {
			sb.append('&').append(OAuthConstants.SCOPE).append('=').append(OAuthEncoder.encode(config.getScope()));
		}
		final String state = config.getState();
		if ( state != null ) {
			sb.append('&').append(OAuthConstants.STATE).append('=').append(OAuthEncoder.encode(state));
		}
		return sb.toString();
	}


	@Override
	public TokenExtractor<OAuth2AccessToken> getAccessTokenExtractor() {
		return OAuth2AccessTokenExtractor.instance();
	}
}
