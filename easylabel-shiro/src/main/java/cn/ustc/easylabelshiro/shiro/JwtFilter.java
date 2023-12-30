package cn.ustc.easylabelshiro.shiro;

import cn.hutool.json.JSONUtil;
import cn.ustc.easylabelshiro.common.Result;
import cn.ustc.easylabelshiro.config.JwtConfig;
import cn.ustc.easylabelshiro.utils.JwtUtil;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.web.filter.authc.BasicHttpAuthenticationFilter;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMethod;

import java.io.IOException;
import java.io.PrintWriter;

/**
 * 拦截请求，判断请求头中是否携带了token，如果携带了，就交给Realm处理
 */
@Component
@Slf4j
public class JwtFilter extends BasicHttpAuthenticationFilter {
    /**
     * 判断主体是否被允许进行HTTP请求的访问，需要将自定义一过滤器定义到shiro配置的过滤器链中
     * 访问是否被允许的判断逻辑：
     * 1.是否携带jwtToken
     * 2.jwtToken是否过期，是否有效
     */
    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) {
        /**
         * 1. 返回true，shiro就直接允许访问url
         * 2. 返回false，shiro才会根据onAccessDenied的方法的返回值决定是否允许访问url
         *  这里先让它始终返回false来使用onAccessDenied()方法
         */
        log.info("isAccessAllowed方法被调用");
        return false;
    }

    /**
     * 返回结果为true表明登录通过
     */
    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
        log.info("onAccessDenied方法被调用");
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        String token = httpServletRequest.getHeader(JwtConfig.HEADER);

        if (token == null) {
            return true;
        }
        JwtToken jwtToken = new JwtToken(token);
        try {
            // 进行登录处理，委托realm进行认证，调用AccountRealm进行的认证
            getSubject(httpServletRequest,response).login(jwtToken);
        } catch (Exception e) {
            log.error("Subject login error:", e);
            return false;
        }
        //如果走到这里，那么就返回true，代表登录成功
        return true;
    }

    /**
     * 登录失败要执行的方法
     */
    protected void onLoginFailure(ServletResponse response) {
        try {
            HttpServletResponse httpServletResponse = (HttpServletResponse) response;
            httpServletResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            httpServletResponse.getWriter().print("login error");
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }

}
