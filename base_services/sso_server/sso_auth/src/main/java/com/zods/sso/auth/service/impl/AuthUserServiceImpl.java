package com.zods.sso.auth.service.impl;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.zods.auth.model.AuthUser;
import com.zods.sso.auth.dao.AuthUserDao;
import com.zods.sso.auth.service.AuthUserService;
import com.zods.sso.auth.util.JsonUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
/**
 * @author jianglong
 * @version 1.0
 * @Description 授权用户实现类
 * @createDate 2022-06-08
 */
@Service
@Slf4j
public class AuthUserServiceImpl implements AuthUserService {

    @Autowired
    private AuthUserDao authUserDao;

    @Override
    public AuthUser findUserByUsername(String username) {
        AuthUser user = null;
        try {
            user = authUserDao.findUserByUsername(username);
        } catch (Exception e) {
            log.error("findUserByUsername failure.", e);
        }
        return user;
    }

    @Override
    public String findUserRoleNameByUserId(Long userId) {
        return authUserDao.findUserRoleNameByUserId(userId);
    }

    @Override
    public List<String> findUserPurviewCodeByUserId(Long userId) {
        List<String> purviewCodes = new ArrayList<>();
        try {
            List<String> purviewInfos = authUserDao.findUserPurviewCodeByUsername(userId);
            if (CollectionUtils.isNotEmpty(purviewInfos)) {
                purviewInfos.stream().forEach(purviewInfo -> {
                    List<String> codes = JsonUtils.jsonToList(purviewInfo, String.class);
                    purviewCodes.addAll(codes);
                });
            }
        } catch (Exception e) {
            log.error("findUserPurviewCodeByUserId failure.", e);
        }
        return purviewCodes;
    }

    @Override
    public AuthUser findUserByPhone(String phone) {
        AuthUser user = null;
        try {
            user = authUserDao.findUserByPhone(phone);
        } catch (Exception e) {
            log.error("findUserByPhone failure.", e);
        }
        return user;
    }
}
