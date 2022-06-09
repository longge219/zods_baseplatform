package com.zods.sso.auth.service;
import com.zods.sso.auth.entity.OauthClientDetails;
/**
 * @author jianglong
 * @version 1.0
 * @Description
 * @createDate 2022-06-08
 */
public interface ClientService {

    /**
     * 根据客户端ID查询客户端信息
     *
     * @param clientId
     * @return
     */
    OauthClientDetails findClientDetailsByClientId(String clientId);

}
