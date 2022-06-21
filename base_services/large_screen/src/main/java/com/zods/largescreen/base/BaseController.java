package com.zods.largescreen.base;
import com.zods.largescreen.common.curd.controller.GaeaBaseController;
import com.zods.largescreen.common.curd.dto.BaseDTO;
import com.zods.largescreen.common.curd.entity.BaseEntity;
import com.zods.largescreen.common.curd.params.PageParam;
import org.springframework.context.i18n.LocaleContextHolder;
/**
 * @author jianglong
 * @version 1.0
 * @Description
 * @createDate 2022-06-20
 */
public abstract class BaseController<P extends PageParam, T extends BaseEntity, D extends BaseDTO>
        extends GaeaBaseController<P,T,D> {
    /**
     * 获取当前语言类型
     * @return
     */
    public String getI18nLang(){
        return LocaleContextHolder.getLocale().getLanguage();
    }
}
