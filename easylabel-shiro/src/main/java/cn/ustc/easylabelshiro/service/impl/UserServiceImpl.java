package cn.ustc.easylabelshiro.service.impl;

import cn.hutool.jwt.JWTUtil;
import cn.hutool.jwt.signers.JWTSigner;
import cn.hutool.jwt.signers.JWTSignerUtil;
import cn.ustc.easylabelshiro.common.Result;
import cn.ustc.easylabelshiro.config.JwtConfig;
import cn.ustc.easylabelshiro.entity.User;
import cn.ustc.easylabelshiro.mapper.UserMapper;
import cn.ustc.easylabelshiro.service.IUserService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * 用户表 服务实现类
 * </p>
 *
 * @author reader
 * @since 2023-12-27
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

    @Autowired
    private StringRedisTemplate redisTemplate;
    @Override
    public User getUser(String userName) {
        QueryWrapper<User> qw = new QueryWrapper<>();
        qw.eq("username", userName);
        return getOne(qw);
    }

    @Override
    public Result register(User user) {
        String username = user.getUsername();
        String password = user.getPassword();
        String account = user.getAccount();
        User dbUser = getUser(username);
        if (dbUser != null) {
            return Result.fail("用户名已被注册，请更换用户名！");
        }
        save(user);
        return Result.ok();
    }

    @Override
    public Result login(User user) {
        // 验证有没有本用户
        String username = user.getUsername();
        String password = user.getPassword();
        User dbUser = getUser(username);
        if (dbUser == null || !password.equals(dbUser.getPassword())) {
            return Result.fail("用户名或密码错误！");
        }
        // 生成JwtToken，传到redis
        // 设置头部
        Map<String, Object> headers = new HashMap<>();
        headers.put("type", JwtConfig.TYPE);
        // 设置载荷
        Map<String, Object> payload = new HashMap<>();
        payload.put("userName", username);
        long expiration = System.currentTimeMillis() + 3600 * 1000;
        payload.put("expiration", expiration);
        // 设置签名
        JWTSigner signer = JWTSignerUtil.createSigner(JwtConfig.ALGRITHM, JwtConfig.SECRET.getBytes());
        // 生成jwtToken
        String token = JWTUtil.createToken(headers, payload, signer);

        // 上传到redis
        redisTemplate.opsForValue().set(username, token);
        return Result.ok();
    }
}
