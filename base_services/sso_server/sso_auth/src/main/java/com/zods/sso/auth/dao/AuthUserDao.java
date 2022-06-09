package com.zods.sso.auth.dao;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zods.auth.model.AuthUser;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import java.util.List;

/**
 * @author Wangchao
 * @version 1.0
 * @Description
 * @createDate 2020/9/28 15:03
 */
public interface AuthUserDao extends BaseMapper<AuthUser> {

    AuthUser findUserByUsername(@Param("username") String username) throws Exception;

    AuthUser findUserByPhone(@Param("phone") String phone) throws Exception;

    /**
     * 查询账户数据权限
     *
     * @param userId
     * @return
     */
    @Select("SELECT ara.station_codes FROM auth_role_area ara LEFT JOIN auth_user_role aur on ara.role_id = aur.role_id LEFT JOIN auth_user au on au.id = aur.user_id " +
            "where au.id = #{userId} and au.is_enable  = 1")
    List<String> findUserPurviewCodeByUsername(@Param("userId") Long userId);

    @Select("SELECT ar.role_name FROM auth_user au LEFT JOIN auth_user_role aur on aur.user_id = au.id " +
            "LEFT JOIN auth_role ar on ar.id = aur.role_id where au.id = #{userId} and au.is_enable = 1")
    String findUserRoleNameByUserId(@Param("userId") Long userId);
}
