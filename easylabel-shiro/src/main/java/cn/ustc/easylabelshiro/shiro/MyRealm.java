package cn.ustc.easylabelshiro.shiro;

import cn.hutool.core.date.DateUtil;
import cn.hutool.jwt.JWT;
import cn.hutool.jwt.JWTUtil;
import cn.hutool.jwt.JWTValidator;
import cn.hutool.jwt.signers.JWTSignerUtil;
import cn.ustc.easylabelshiro.config.JwtConfig;
import cn.ustc.easylabelshiro.entity.User;
import cn.ustc.easylabelshiro.service.impl.UserServiceImpl;
import cn.ustc.easylabelshiro.utils.JwtUtil;
import io.jsonwebtoken.Claims;
import org.apache.shiro.ShiroException;
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

    /**
     * 限定这个realm只能处理jwtToken
     */
    @Override
    public boolean supports(AuthenticationToken token) {
        return token instanceof JwtToken;
    }

    /**
     * 认证
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        // 1.获取token
        String token = (String) authenticationToken.getCredentials();
        // 2.获取jwt中关于用户名
        String username = JwtUtil.getClaimsByToken(token).getSubject();
        // 3.查询用户
        User user = userService.getUser(username);
        if (user == null) {
            throw new ShiroException("用户不存在");
        }
        Claims claims = JwtUtil.getClaimsByToken(token);
        if (JwtUtil.isTokenExpired(claims.getExpiration())) {
            throw new ShiroException("token已过期，请重新登录");
        }
        return new SimpleAuthenticationInfo(user, token, getName());
    }



    /**
     * 授权
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        return null;
    }
}
