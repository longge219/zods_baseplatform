package com.zods.sso.auth.dao;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zods.sso.auth.entity.OauthClientDetails;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
/**
 * @author Wangchao
 * @version 1.0
 * @Description
 * @createDate 2021/3/18 17:41
 */
public interface ClientDao extends BaseMapper<OauthClientDetails> {

    /**
     * 根据客户端ID查询客户端信息
     *
     * @param clientId
     * @return
     */
    @Select("select * from oauth_client_details where client_id=#{clientId}")
    OauthClientDetails findClientDetailsByClientId(@Param("clientId") String clientId);
}
