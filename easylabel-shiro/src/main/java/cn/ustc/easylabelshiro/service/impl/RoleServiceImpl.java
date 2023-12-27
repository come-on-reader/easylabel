package cn.ustc.easylabelshiro.service.impl;

import cn.ustc.easylabelshiro.entity.Role;
import cn.ustc.easylabelshiro.mapper.RoleMapper;
import cn.ustc.easylabelshiro.service.IRoleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 角色表 服务实现类
 * </p>
 *
 * @author reader
 * @since 2023-12-27
 */
@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements IRoleService {

}
