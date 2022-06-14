package com.zods.plugins.zods.holder;


import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

public class UserContext {
    private String uuid;
    private String username;
    private List<String> roles;
    private Set<String> Authorities;
    private List<String> menus;
    private String orgCode;
    private String sysCode;
    private Locale locale;
    private Integer type;
    private String tenantCode;
    private Map<String, Object> params = new HashMap();

    public UserContext() {
    }

    public String getUuid() {
        return this.uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public List<String> getRoles() {
        return this.roles;
    }

    public void setRoles(List<String> roles) {
        this.roles = roles;
    }

    public Set<String> getAuthorities() {
        return this.Authorities;
    }

    public void setAuthorities(Set<String> authorities) {
        this.Authorities = authorities;
    }

    public List<String> getMenus() {
        return this.menus;
    }

    public void setMenus(List<String> menus) {
        this.menus = menus;
    }

    public String getOrgCode() {
        return this.orgCode;
    }

    public void setOrgCode(String orgCode) {
        this.orgCode = orgCode;
    }

    public Map<String, Object> getParams() {
        return this.params;
    }

    public void setParams(Map<String, Object> params) {
        this.params = params;
    }

    public String getSysCode() {
        return this.sysCode;
    }

    public void setSysCode(String sysCode) {
        this.sysCode = sysCode;
    }

    public Locale getLocale() {
        return this.locale;
    }

    public void setLocale(Locale locale) {
        this.locale = locale;
    }

    public Integer getType() {
        return this.type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getTenantCode() {
        return this.tenantCode;
    }

    public void setTenantCode(String tenantCode) {
        this.tenantCode = tenantCode;
    }
}
