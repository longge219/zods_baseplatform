package com.zods.sso.auth.service.impl;
import com.zods.sso.auth.dao.AuthPermissionDao;
import com.zods.sso.auth.entity.AuthPermission;
import com.zods.sso.auth.service.AuthPermissionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
/**
 * @author jianglong
 * @version 1.0
 * @Description 授权用户权限实现类
 * @createDate 2022-06-08
 */
@Service
@Slf4j
public class AuthPermissionServiceImpl implements AuthPermissionService {

    @Autowired
    private AuthPermissionDao permissionDao;

    @Override
    public List<AuthPermission> findPermissionByUserId(Long userId) {
        List<AuthPermission> permissions = null;
        try {
            permissions = permissionDao.findPermissionByUserId(userId);
        } catch (Exception e) {
            log.error("findPermissionByUserId failure.", e);
        }
        return permissions;
    }
}
