package cn.ustc.easylabelshiro.controller;

import cn.ustc.easylabelshiro.common.Result;
import cn.ustc.easylabelshiro.entity.User;
import cn.ustc.easylabelshiro.service.impl.UserServiceImpl;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * <p>
 * 用户表 前端控制器
 * </p>
 *
 * @author reader
 * @since 2023-12-27
 */
@Controller
@RequestMapping("/easylabelshiro/user")
public class UserController {
    @Resource
    private UserServiceImpl userService;
    @PostMapping("/register")
    public Result register(@RequestBody User user) {
        return userService.register(user);
    }

    @PostMapping("/login")
    public Result login(@RequestBody User user, HttpServletResponse response) {
        return userService.login(user, response);
    }

    @RequiresAuthentication
    @GetMapping("/logout")
    public Result logout() {
        // 退出登录
        SecurityUtils.getSubject().logout();
        return Result.ok();
    }
}
