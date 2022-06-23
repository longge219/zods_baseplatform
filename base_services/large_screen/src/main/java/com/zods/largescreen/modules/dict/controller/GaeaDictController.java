package com.zods.largescreen.modules.dict.controller;
import com.zods.largescreen.common.bean.KeyValue;
import com.zods.largescreen.common.bean.ResponseBean;
import com.zods.largescreen.common.curd.controller.GaeaBaseController;
import com.zods.largescreen.common.curd.service.GaeaBaseService;
import com.zods.largescreen.modules.dict.controller.dto.GaeaDictDTO;
import com.zods.largescreen.modules.dict.controller.param.GaeaDictParam;
import com.zods.largescreen.modules.dict.dao.entity.GaeaDict;
import com.zods.largescreen.modules.dict.service.GaeaDictItemService;
import com.zods.largescreen.modules.dict.service.GaeaDictService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.web.bind.annotation.*;
import java.util.Collection;
import java.util.List;
import java.util.Locale;
import java.util.Map;
/**
 * @desc 数据字典-controller
 * @author jianglong
 * @date 2022-06-23
 **/
@RestController
@RequestMapping("/gaeaDict")
@Api(value = "/gaeaDict", tags = "")
public class GaeaDictController extends GaeaBaseController<GaeaDictParam, GaeaDict, GaeaDictDTO> {

    @Autowired
    private GaeaDictService gaeaDictService;

    @Autowired
    private GaeaDictItemService gaeaDictItemService;

    @Override
    public GaeaBaseService<GaeaDictParam, GaeaDict> getService() {
        return gaeaDictService;
    }

    @Override
    public GaeaDict getEntity() {
        return new GaeaDict();
    }

    @Override
    public GaeaDictDTO getDTO() {
        return new GaeaDictDTO();
    }


    /**刷新指定字典项*/
    @PostMapping("/freshDict")
    public ResponseBean refreshDict(@RequestBody List<String> dictCodes) {
        //刷新
        gaeaDictService.refreshCache(dictCodes);
        return responseSuccess();
    }

    /**下拉菜单*/
    @GetMapping("/select/{dictCode}")
    public ResponseBean select(@PathVariable("dictCode") String dictName){
        Locale locale = LocaleContextHolder.getLocale();
        //语言
        String language = locale.getLanguage();
        List<KeyValue> keyValues = gaeaDictService.select(dictName,language);
        return responseSuccessWithData(keyValues);
    }


    /**指定语言的字典项*/
    @GetMapping("/map/{dictCode}")
    public ResponseBean dictItemByLang(@PathVariable("dictCode") String dictCode){
        return responseSuccessWithData(gaeaDictItemService.getItemMap(dictCode));
    }

    /**
     * 下拉菜单
     * @return
     */
    @GetMapping("/selectAll/{project}")
    public ResponseBean selectTypecodes(@PathVariable String project){
        Locale locale = LocaleContextHolder.getLocale();
        //语言
        String language = locale.getLanguage();
        Collection<KeyValue> keyValues = gaeaDictService.selectTypeCode(project,language);
        return responseSuccessWithData(keyValues);
    }

    /**
     * 获取所有数据字典
     * @return
     */
    @GetMapping("/all")
    public ResponseBean all(){
        Locale locale = LocaleContextHolder.getLocale();
        //语言
        String language = locale.getLanguage();
        Map<String, List<KeyValue>> all = gaeaDictService.all(language);
        return responseSuccessWithData(all);
    }
}
