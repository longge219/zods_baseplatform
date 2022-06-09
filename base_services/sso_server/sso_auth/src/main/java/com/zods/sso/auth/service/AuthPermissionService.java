package com.zods.sso.auth.service;
import com.zods.sso.auth.entity.AuthPermission;
import java.util.List;
/**
 * @author Wangchao
 * @version 1.0
 * @Description 授权用户权限
 * @createDate 2020/09/09 14:45
 */
public interface AuthPermissionService {
    List<AuthPermission> findPermissionByUserId(Long userId);
}
