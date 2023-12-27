package cn.ustc.easylabelshiro.service.impl;

import cn.ustc.easylabelshiro.entity.Permission;
import cn.ustc.easylabelshiro.mapper.PermissionMapper;
import cn.ustc.easylabelshiro.service.IPermissionService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 资源表 服务实现类
 * </p>
 *
 * @author reader
 * @since 2023-12-27
 */
@Service
public class PermissionServiceImpl extends ServiceImpl<PermissionMapper, Permission> implements IPermissionService {

}
