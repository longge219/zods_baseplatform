package com.zods.largescreen.common.curd.mapper.injected;
import com.baomidou.mybatisplus.core.injector.AbstractMethod;
import com.baomidou.mybatisplus.core.injector.DefaultSqlInjector;
import com.zods.largescreen.common.curd.mapper.methods.CustomInsertBatch;
import com.zods.largescreen.common.curd.mapper.methods.UpdateFieldsBatch;
import com.zods.largescreen.common.curd.mapper.methods.UpdateFieldsBatchById;
import com.zods.largescreen.common.curd.mapper.methods.UpdateFieldsById;
import java.util.List;
/**
 * @author jianglong
 * @version 1.0
 * @Description
 * @createDate 2022-06-20
 */
public class CustomSqlInjector extends DefaultSqlInjector {
    public CustomSqlInjector() {
    }

    public List<AbstractMethod> getMethodList(Class<?> mapperClass) {
        List<AbstractMethod> methodList = super.getMethodList(mapperClass);
        methodList.add(new CustomInsertBatch());
        methodList.add(new UpdateFieldsById());
        methodList.add(new UpdateFieldsBatch());
        methodList.add(new UpdateFieldsBatchById());
        return methodList;
    }
}