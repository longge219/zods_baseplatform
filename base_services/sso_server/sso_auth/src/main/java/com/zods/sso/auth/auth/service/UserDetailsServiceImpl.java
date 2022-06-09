package com.zods.sso.auth.auth.service;
import com.zods.auth.model.AuthUser;
import com.zods.auth.model.McloudUser;
import com.zods.sso.auth.entity.AuthPermission;
import com.zods.sso.auth.service.AuthPermissionService;
import com.zods.sso.auth.service.AuthUserService;
import org.assertj.core.util.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import zom.zods.exception.exception.category.McloudHandlerException;
import java.util.List;
/**
 * @author Wangchao
 * @version 1.0
 * @Description 自定义用户认证与授权
 * @createDate 2020/09/09 14:45
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private AuthUserService authUserService;

    @Autowired
    private AuthPermissionService authPermissionService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // 1.查询用户信息
        AuthUser authUser = authUserService.findUserByUsername(username);
        if (authUser == null) {
            throw new McloudHandlerException("该用户：" + username + "不存在");
        }
        // 2.权限查询
        List<GrantedAuthority> grantedAuthorities = getAuthorities(authUser);
        // 3.认证工作交给框架完成
        return new McloudUser(authUser, grantedAuthorities);
    }

    /**
     * 根据手机号登录
     *
     * @param mobile
     * @return UserDetails
     * @throws UsernameNotFoundException
     */
    public UserDetails loadUserByMobile(String mobile) throws UsernameNotFoundException {
        // 1.查询用户信息
        AuthUser authUser = authUserService.findUserByPhone(mobile);
        if (authUser == null) {
            throw new McloudHandlerException("该用户：" + mobile + "不存在");
        }
        // 2.权限查询
        List<GrantedAuthority> grantedAuthorities = getAuthorities(authUser);
        // 3.认证工作交给框架完成
        return new McloudUser(authUser, grantedAuthorities);
    }

    /**
     * 权限查询
     *
     * @param authUser
     * @return
     */
    private List<GrantedAuthority> getAuthorities(AuthUser authUser) {
        List<GrantedAuthority> grantedAuthorities = Lists.newArrayList();
        if (authUser != null) {
            // 2.获取用户授权
            List<AuthPermission> authPermissions = authPermissionService.findPermissionByUserId(authUser.getId());
            // 3.声明用户授权
            if (null != authPermissions) {
                authPermissions.forEach(authPermission -> {
                    if (authPermission != null && authPermission.getEnname() != null) {
                        GrantedAuthority grantedAuthority = new SimpleGrantedAuthority(authPermission.getEnname());
                        grantedAuthorities.add(grantedAuthority);
                    }
                });
            }
        }
        return grantedAuthorities;
    }
}
