package cn.ustc.easylabelshiro.controller;

import cn.ustc.easylabelshiro.common.Result;
import cn.ustc.easylabelshiro.entity.User;
import cn.ustc.easylabelshiro.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
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
    @Autowired
    private UserServiceImpl userService;
    @PostMapping("/register")
    public Result register(@RequestBody User user) {
        return userService.register(user);
    }

    @PostMapping("/login")
    public Result login(@RequestBody User user) {
        return userService.login(user);
    }
}
