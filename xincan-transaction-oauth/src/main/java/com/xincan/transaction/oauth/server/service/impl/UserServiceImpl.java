package com.xincan.transaction.oauth.server.service.impl;

import cn.com.hatech.common.data.universal.AbstractService;
import com.xincan.transaction.oauth.config.redis.CustomRedisTokenStore;
import com.xincan.transaction.oauth.server.entity.OAuthParam;
import com.xincan.transaction.oauth.server.entity.User;
import com.xincan.transaction.oauth.server.mapper.user.IUserMapper;
import com.xincan.transaction.oauth.server.service.IUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xincan.transaction.oauth.server.utils.TokenUtils;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationDetails;
import org.springframework.security.oauth2.provider.token.DefaultAuthenticationKeyGenerator;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.security.Principal;
import java.util.Base64;
import java.util.List;
import java.util.concurrent.locks.Condition;

@Service
public class UserServiceImpl extends AbstractService<User> implements IUserService {

    /**
     * 注入
     */
    @Autowired
    private RestTemplate restTemplate;

    /**
     * 注入服务发现
     */
    @Autowired
    private DiscoveryClient discoveryClient;

    /**
     * 注入认证中心服务名
     */
    @Value("${spring.application.name}")
    private String applicationName;

    /**
     * 注入jedis连接
     */
    @Autowired
    private JedisConnectionFactory jedisConnectionFactory;

    /**
     * 注入redis token store
     */
    @Autowired
    private TokenStore tokenStore;

    /**
     * 发送登录请求到认证中心, 获取登录token
     * @param oAuthParam
     * @return
     */
    @Override
    public ResponseEntity<OAuth2AccessToken> userLogin(OAuthParam oAuthParam) {
        // 创建basic auth的header
        String userMsg = oAuthParam.getHeaderUserName() + ":" + oAuthParam.getHeaderPassword();
        String base64UserMsg = Base64.getEncoder().encodeToString(userMsg.getBytes());
        // 查询eureka注册服务
        List<ServiceInstance> serviceInstances = discoveryClient.getInstances(applicationName);
        if (serviceInstances==null || serviceInstances.size()<1) {
            throw new RuntimeException(applicationName+" 服务未在eureka上注册");
        }
        String uri = serviceInstances.get(0).getUri().toString();
        String url = uri+"/oauth/token";
        // 创建post请求body
        MultiValueMap<String, String> postBody= new LinkedMultiValueMap<>();
        postBody.add("grant_type",oAuthParam.getGrantType());
        postBody.add("scope",oAuthParam.getScope());
        postBody.add("username",oAuthParam.getUsername());
        postBody.add("password",oAuthParam.getPassword());
        // 创建post请求header
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        headers.add("Authorization", "Basic "+base64UserMsg);
        HttpEntity<MultiValueMap<String,String>> entity = new HttpEntity<>(postBody, headers);
        // 返回登录token
        return restTemplate.postForEntity(url, entity, OAuth2AccessToken.class);
    }

    /**
     * 用户退出登录
     * 删除redis中储存的access token
     */
    @Override
    public void userLogout(OAuth2Authentication oAuth2Authentication) throws Exception{
        CustomRedisTokenStore customRedisTokenStore = (CustomRedisTokenStore)tokenStore;
        //以下计算redis中的key
        // 已登录的用户名
        String user = oAuth2Authentication.getName();
        // token值
        String token = ((OAuth2AuthenticationDetails)oAuth2Authentication.getDetails()).getTokenValue();
        // 解析access token
        Claims claims = TokenUtils.decodeToken(oAuth2Authentication);
        // client id值
        String clientId = claims.get("client_id", String.class);
        // 删除redis 中的 access token
        customRedisTokenStore.removeAccessToken(token);
        // 删除用户登录信息
        RedisConnection redisConnection = null;
        try {
            redisConnection = jedisConnectionFactory.getConnection();
            String key = "uname_to_access:"+clientId+":"+user;
            redisConnection.del(key.getBytes());
        }
        finally {
            redisConnection.close();
        }
    }

}
