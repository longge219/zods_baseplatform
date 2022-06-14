package com.zods.plugins.zods.curd.mapper.injected;
import com.baomidou.mybatisplus.core.injector.AbstractMethod;
import com.baomidou.mybatisplus.core.injector.DefaultSqlInjector;
import com.zods.plugins.zods.curd.mapper.methods.CustomInsertBatch;
import com.zods.plugins.zods.curd.mapper.methods.UpdateFieldsBatch;
import com.zods.plugins.zods.curd.mapper.methods.UpdateFieldsBatchById;
import com.zods.plugins.zods.curd.mapper.methods.UpdateFieldsById;

import java.util.List;

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