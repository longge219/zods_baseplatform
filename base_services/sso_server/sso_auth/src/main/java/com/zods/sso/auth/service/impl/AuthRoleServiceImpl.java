package com.zods.sso.auth.service.impl;
import com.zods.sso.auth.dao.AuthRoleDao;
import com.zods.sso.auth.service.AuthRoleService;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
/**
 * @author jianglong
 * @version 1.0
 * @Description 授权用户角色实现类
 * @createDate 2022-06-08
 */
@Service
public class AuthRoleServiceImpl implements AuthRoleService {

    @Resource
    private AuthRoleDao roleDao;

}
