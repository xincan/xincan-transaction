package com.xincan.transaction.oauth.server.service;

import cn.com.hatech.common.data.universal.IBaseService;
import com.xincan.transaction.oauth.server.entity.OAuthParam;
import com.xincan.transaction.oauth.server.entity.User;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;

public interface IUserService extends IBaseService<User> {

    ResponseEntity<OAuth2AccessToken> userLogin(OAuthParam oAuthParam);

    void userLogout(OAuth2Authentication oAuth2Authentication) throws Exception;

}
