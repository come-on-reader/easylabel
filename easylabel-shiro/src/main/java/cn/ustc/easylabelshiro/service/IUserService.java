package cn.ustc.easylabelshiro.service;

import cn.ustc.easylabelshiro.common.Result;
import cn.ustc.easylabelshiro.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;
import jakarta.servlet.http.HttpServletResponse;

/**
 * <p>
 * 用户表 服务类
 * </p>
 *
 * @author reader
 * @since 2023-12-27
 */
public interface IUserService extends IService<User> {
    User getUser(String userName);
    Result register(User user);

    Result login(User user, HttpServletResponse response);
}
