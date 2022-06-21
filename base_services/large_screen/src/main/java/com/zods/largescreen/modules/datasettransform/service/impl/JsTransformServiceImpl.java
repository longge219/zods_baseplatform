package com.zods.largescreen.modules.datasettransform.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.zods.largescreen.code.ResponseCode;
import com.zods.largescreen.common.exception.BusinessExceptionBuilder;
import com.zods.largescreen.modules.datasettransform.controller.dto.DataSetTransformDto;
import com.zods.largescreen.modules.datasettransform.service.TransformStrategy;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import java.util.List;

/**
 * Created by raodeming on 2021/3/23.
 */
@Component
@Slf4j
public class JsTransformServiceImpl implements TransformStrategy {

    private ScriptEngine engine;
    {
        ScriptEngineManager manager = new ScriptEngineManager();
        engine = manager.getEngineByName("JavaScript");
    }

    /**
     * 数据清洗转换 类型
     *
     * @return
     */
    @Override
    public String type() {
        return "js";
    }

    /***
     * 清洗转换算法接口
     * @param def
     * @param data
     * @return
     */
    @Override
    public List<JSONObject> transform(DataSetTransformDto def, List<JSONObject> data) {
        return getValueFromJs(def,data);
    }

    private List<JSONObject> getValueFromJs(DataSetTransformDto def, List<JSONObject> data) {
        String js = def.getTransformScript();
        try {
            engine.eval(js);
            if(engine instanceof Invocable){
                Invocable invocable = (Invocable) engine;
                return (List<JSONObject>) invocable.invokeFunction("dataTransform", data);
            }

        } catch (Exception ex) {
            throw BusinessExceptionBuilder.build(ResponseCode.EXECUTE_JS_ERROR, ex.getMessage());
        }
        return null;
    }
}
