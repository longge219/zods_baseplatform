package com.zods.auth.model;
import lombok.Setter;
import org.springframework.beans.BeanUtils;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import java.io.Serializable;
import java.util.Collection;
import java.util.List;
/**
 * @author Wangchao
 * @version 1.0
 * @Description
 * @createDate 2021/2/4 16:31
 */
public class McloudUser extends AuthUser implements UserDetails, Serializable {

    @Setter
    private Boolean accountNonExpired = true;
    @Setter
    private Boolean accountNonLocked = true;
    @Setter
    private Boolean credentialsNonExpired = true;
    @Setter
    private Boolean enabled = true;

    private Collection<? extends GrantedAuthority> authorities;


    public McloudUser(AuthUser user, List<GrantedAuthority> authorities) {
        this.authorities = authorities;
        if (user != null) {
            BeanUtils.copyProperties(user, this);
        }
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return super.getPassword();
    }

    @Override
    public String getUsername() {
        return super.getUsername();
    }


    @Override
    public boolean isAccountNonExpired() {
        return this.accountNonExpired;
    }

    @Override
    public boolean isAccountNonLocked() {
        return this.accountNonLocked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return this.credentialsNonExpired;
    }

    @Override
    public boolean isEnabled() {
        return this.enabled;
    }

}
