package com.zods.sso.auth.service;
import com.zods.auth.model.AuthUser;
import java.util.List;
/**
 * @author jianglong
 * @version 1.0
 * @Description 授权用户
 * @createDate 2022-06-08
 */
public interface AuthUserService {

    AuthUser findUserByUsername(String username);

    String findUserRoleNameByUserId(Long userId);

    List<String> findUserPurviewCodeByUserId(Long userId);

    AuthUser findUserByPhone(String phone);
}
