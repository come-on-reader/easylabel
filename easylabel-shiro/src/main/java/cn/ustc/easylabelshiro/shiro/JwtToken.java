package cn.ustc.easylabelshiro.shiro;

import org.apache.shiro.authc.AuthenticationToken;

/**
 * shiro不能识别字符串的token，需要对其进行封装
 * jwtToken ： head+payload+signature
 */
public class JwtToken implements AuthenticationToken {
    private String token;

    public JwtToken(String token) {
        this.token = token;
    }

    @Override
    public Object getPrincipal() {
        return token;
    }

    @Override
    public Object getCredentials() {
        return token;
    }
}
