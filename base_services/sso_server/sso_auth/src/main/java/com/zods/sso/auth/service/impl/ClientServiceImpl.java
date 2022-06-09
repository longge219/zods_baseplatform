package com.zods.sso.auth.service.impl;
import com.zods.sso.auth.dao.ClientDao;
import com.zods.sso.auth.entity.OauthClientDetails;
import com.zods.sso.auth.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
/**
 * @author jianglong
 * @version 1.0
 * @Description
 * @createDate 2022-06-08
 */
@Service
public class ClientServiceImpl implements ClientService {

    @Autowired
    private ClientDao clientDao;

    @Override
    public OauthClientDetails findClientDetailsByClientId(String clientId) {
        return clientDao.findClientDetailsByClientId(clientId);
    }
}
