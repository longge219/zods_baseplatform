package com.zods.plugins.zods.config;


import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import java.util.Date;

import com.zods.plugins.zods.holder.UserContentHolder;
import org.apache.ibatis.reflection.MetaObject;

public class MybatisPlusMetaObjectHandler implements MetaObjectHandler {
    public MybatisPlusMetaObjectHandler() {
    }

    public void insertFill(MetaObject metaObject) {
        String username = UserContentHolder.getContext().getUsername();
        Object createBy = this.getFieldValByName("createBy", metaObject);
        if (createBy == null) {
            this.setFieldValByName("createBy", username, metaObject);
        }

        this.setFieldValByName("createTime", new Date(), metaObject);
        Object updateBy = this.getFieldValByName("updateBy", metaObject);
        if (updateBy == null) {
            this.setFieldValByName("updateBy", username, metaObject);
        }

        this.setFieldValByName("updateTime", new Date(), metaObject);
        this.setFieldValByName("version", 1, metaObject);
    }

    public void updateFill(MetaObject metaObject) {
        String username = UserContentHolder.getContext().getUsername();
        this.setFieldValByName("updateBy", username, metaObject);
        this.setFieldValByName("updateTime", new Date(), metaObject);
        this.setFieldValByName("version", this.getFieldValByName("version", metaObject), metaObject);
    }
}
