package com.zods.largescreen.modules.accessuser.dao;
import com.zods.largescreen.common.curd.mapper.GaeaBaseMapper;
import com.zods.largescreen.modules.accessuser.dao.entity.AccessUser;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
/**
* AccessUser Mapper
* @author 木子李·De <lide1202@hotmail.com>
* @date 2019-02-17 08:50:11.902
**/
@Mapper
public interface AccessUserMapper extends GaeaBaseMapper<AccessUser> {

    /** 查询用户所拥有的所有角色下的权限
     * @param loginName
     * @return
     */
    List<String> queryAuthoritiesByLoginName(@Param("loginName")String loginName);
}