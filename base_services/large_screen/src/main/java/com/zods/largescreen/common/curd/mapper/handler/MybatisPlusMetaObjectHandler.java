package com.zods.largescreen.common.curd.mapper.handler;
import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import org.apache.ibatis.reflection.MetaObject;
import java.util.Date;
/**
 * @author jianglong
 * @version 1.0
 * @Description
 * @createDate 2022-06-20
 */
public class MybatisPlusMetaObjectHandler implements MetaObjectHandler {
    public MybatisPlusMetaObjectHandler() {
    }
    public void insertFill(MetaObject metaObject) {
        Object createBy = this.getFieldValByName("createBy", metaObject);
        if (createBy == null) {
            this.setFieldValByName("createBy", createBy, metaObject);
        }
        this.setFieldValByName("createTime", new Date(), metaObject);
        Object updateBy = this.getFieldValByName("updateBy", metaObject);
        if (updateBy == null) {
            this.setFieldValByName("updateBy", updateBy, metaObject);
        }
        this.setFieldValByName("updateTime", new Date(), metaObject);
        this.setFieldValByName("version", 1, metaObject);
    }

    public void updateFill(MetaObject metaObject) {
        this.setFieldValByName("updateBy", this.getFieldValByName("updateBy",metaObject), metaObject);
        this.setFieldValByName("updateTime", new Date(), metaObject);
        this.setFieldValByName("version", this.getFieldValByName("version", metaObject), metaObject);
    }
}
