package com.baomidou.framework.oauth2.apis;

import com.github.scribejava.core.builder.api.DefaultApi20;
import com.github.scribejava.core.model.OAuthConfig;
import com.github.scribejava.core.model.OAuthConstants;
import com.github.scribejava.core.utils.OAuthEncoder;

/**
 * <p>
 * SinaWeibo 登录 API 类
 * </p>
 * @author hubin
 * @Date 2016-05-09
 */
public class SinaWeiboApi extends DefaultApi20 {

	private static final String AUTHORIZE_URL = "https://api.weibo.com/oauth2/authorize?client_id=%s&redirect_uri=%s&response_type=code";

	private static final String SCOPED_AUTHORIZE_URL = AUTHORIZE_URL + "&scope=%s";


	protected SinaWeiboApi() {

	}

	private static class InstanceHolder {
		private static final SinaWeiboApi INSTANCE = new SinaWeiboApi();
	}


	public static SinaWeiboApi instance() {
		return InstanceHolder.INSTANCE;
	}


	@Override
	public String getAccessTokenEndpoint() {
		return "https://api.weibo.com/oauth2/access_token?grant_type=" + OAuthConstants.AUTHORIZATION_CODE;
	}


	@Override
	public String getAuthorizationUrl( OAuthConfig config ) {
		// Append scope if present
		if ( config.hasScope() ) {
			return String.format(SCOPED_AUTHORIZE_URL, config.getApiKey(), OAuthEncoder.encode(config.getCallback()),
				OAuthEncoder.encode(config.getScope()));
		} else {
			return String.format(AUTHORIZE_URL, config.getApiKey(), OAuthEncoder.encode(config.getCallback()));
		}
	}
}
