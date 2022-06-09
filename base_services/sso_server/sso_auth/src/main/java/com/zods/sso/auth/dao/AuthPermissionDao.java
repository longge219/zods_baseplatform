package com.zods.sso.auth.dao;
import com.zods.sso.auth.entity.AuthPermission;
import io.lettuce.core.dynamic.annotation.Param;
import java.util.List;
/**
 * @author Wangchao
 * @version 1.0
 * @Description
 * @createDate 2020/9/28 15:12
 */
public interface AuthPermissionDao {

    List<AuthPermission> findPermissionByUserId(@Param("userId") Long userId) throws Exception;

}
