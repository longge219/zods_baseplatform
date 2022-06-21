package com.zods.largescreen.modules.accessrole.dao;
import com.zods.largescreen.common.curd.mapper.GaeaBaseMapper;
import com.zods.largescreen.modules.accessrole.dao.entity.AccessRole;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import java.util.List;
/**
* AccessRole Mapper
* @author 木子李·De <lide1202@hotmail.com>
* @date 2019-02-17 08:50:14.136
**/
@Mapper
public interface AccessRoleMapper extends GaeaBaseMapper<AccessRole> {

    List<String> checkedAuthoritys(@Param("roleCode")String roleCode);
}