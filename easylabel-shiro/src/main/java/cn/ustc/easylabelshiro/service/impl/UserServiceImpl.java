package cn.ustc.easylabelshiro.service.impl;

import cn.ustc.easylabelshiro.common.Result;
import cn.ustc.easylabelshiro.config.JwtConfig;
import cn.ustc.easylabelshiro.entity.User;
import cn.ustc.easylabelshiro.mapper.UserMapper;
import cn.ustc.easylabelshiro.service.IUserService;
import cn.ustc.easylabelshiro.utils.JwtUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletResponse;
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

    @Override
    public User getUser(String userName) {
        QueryWrapper<User> qw = new QueryWrapper<>();
        qw.eq("username", userName);
        return getOne(qw);
    }

    @Override
    public Result register(User user) {
        String username = user.getUsername();
        User dbUser = getUser(username);
        if (dbUser != null) {
            return Result.fail("用户名已被注册，请更换用户名！");
        }
        save(user);
        return Result.ok();
    }

    @Override
    public Result login(User user, HttpServletResponse response) {
        // 验证有没有本用户
        String username = user.getUsername();
        String password = user.getPassword();
        User dbUser = getUser(username);
        if (dbUser == null || !password.equals(dbUser.getPassword())) {
            return Result.fail("用户名或密码错误！");
        }
        // 生成JwtToken，传到redis
        String token = JwtUtil.generateToken(username);

        // todo 上传到redis

        // 返回，并设置response头部
        response.setHeader(JwtConfig.HEADER, token);
        response.setHeader("Access-control-Expost-Headers", JwtConfig.HEADER);
        Map<String, String> map = new HashMap<>();
        map.put("token", token);
        return Result.ok(map);
    }
}
