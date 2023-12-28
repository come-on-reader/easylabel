package cn.ustc.easylabelshiro.shiro;

import cn.hutool.core.date.DateUtil;
import cn.hutool.jwt.JWT;
import cn.hutool.jwt.JWTUtil;
import cn.hutool.jwt.JWTValidator;
import cn.hutool.jwt.signers.JWTSignerUtil;
import cn.ustc.easylabelshiro.config.JwtConfig;
import cn.ustc.easylabelshiro.service.impl.UserServiceImpl;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

@Component
public class MyRealm extends AuthorizingRealm {

    @Autowired
    private UserServiceImpl userService;

    @Autowired
    private StringRedisTemplate redisTemplate;
    /**
     * 限定这个realm只能处理jwtToken
     * @param token
     * @return
     */
    @Override
    public boolean supports(AuthenticationToken token) {
        return token instanceof JwtToken;
    }

    /**
     * 认证
     * @param authenticationToken
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        // 1.获取token
        String token = (String) authenticationToken.getCredentials();
        JWT jwt = JWTUtil.parseToken(token);
        // 2.校验token：未校验通过或者已过期
        // 2.1 校验token的signature，看内容是否被篡改
        JWTValidator.of(token).validateAlgorithm(JWTSignerUtil.hs256(JwtConfig.ALGRITHM.getBytes()));
        // 2.2 校验token的有效期
        JWTValidator.of(token).validateDate(DateUtil.date());
        // 2.3 校验token中的账户是否存在，秘钥是否正确
        String userName = (String) jwt.getPayload("userName");
        String jwtToken = (String) jwt.getPayload("jwtToken");

        if (userName == null) {
            throw new AuthenticationException("Username or password error");
        }
        // 拿着userName对应的token去redis中寻找
        String redisToken = redisTemplate.opsForValue().get(userName);
        // 对比redisToken和jwtToken
        if (!jwtToken.isEmpty() && !jwtToken.equals(redisToken)) {
            throw new AuthenticationException("Username or password error");
        }
        
        return new SimpleAuthenticationInfo(token, token, "my_realm");
    }



    /**
     * 授权
     * @param principalCollection
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        return null;
    }
}
