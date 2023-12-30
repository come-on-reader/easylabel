package cn.ustc.easylabelshiro.shiro;

import cn.ustc.easylabelshiro.utils.JwtUtil;
import org.apache.shiro.authc.AuthenticationToken;

/**
 * shiro不能识别字符串的token，需要对其进行封装
 * jwtToken ： head+payload+signature
 */
public class JwtToken implements AuthenticationToken {
    private String username;
    private String token;

    public JwtToken(String token) {
        this.token = token;
        this.username = JwtUtil.getClaimField(token, "username");
    }

    /**
     * 类似用户名
     * @return
     */
    @Override
    public Object getPrincipal() {
        return username;
    }

    /**
     * 类似密码
     * @return
     */
    @Override
    public Object getCredentials() {
        return token;
    }
}
