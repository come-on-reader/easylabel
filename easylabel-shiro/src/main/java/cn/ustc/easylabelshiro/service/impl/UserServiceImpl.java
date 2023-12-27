package cn.ustc.easylabelshiro.service.impl;

import cn.ustc.easylabelshiro.entity.User;
import cn.ustc.easylabelshiro.mapper.UserMapper;
import cn.ustc.easylabelshiro.service.IUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

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

}
