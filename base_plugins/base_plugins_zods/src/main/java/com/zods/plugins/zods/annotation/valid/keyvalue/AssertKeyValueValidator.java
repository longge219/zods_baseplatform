package com.zods.plugins.zods.annotation.valid.keyvalue;

import java.util.HashMap;
import java.util.Map;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import com.zods.plugins.zods.cache.CacheHelper;
import com.zods.plugins.zods.holder.UserContentHolder;
import com.zods.plugins.zods.utils.ApplicationContextUtils;
import com.zods.plugins.zods.utils.GaeaUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.i18n.LocaleContextHolder;

public class AssertKeyValueValidator implements ConstraintValidator<AssertKeyValue, Object> {
    private String dictCode;
    private String key;

    public AssertKeyValueValidator() {
    }

    public boolean isValid(Object value, ConstraintValidatorContext context) {
        CacheHelper cacheHelper = (CacheHelper) ApplicationContextUtils.getBean(CacheHelper.class);
        if (cacheHelper == null) {
            return true;
        } else if (value == null) {
            return true;
        } else {
            String locale = LocaleContextHolder.getLocale().getLanguage();
            new HashMap();
            Map map;
            if (StringUtils.isNotBlank(this.dictCode)) {
                String dictKey = "gaea:dict:prefix:" + locale + ":" + this.dictCode;
                map = cacheHelper.hashGet(dictKey);
            } else {
                if (!StringUtils.isNotBlank(this.key)) {
                    return true;
                }

                if (this.key.contains("${")) {
                    Map<String, Object> params = UserContentHolder.getContext().getParams();
                    this.key = GaeaUtils.replaceFormatString(this.key, params);
                }

                map = cacheHelper.hashGet(this.key);
            }

            if (String.valueOf(value).contains(",")) {
                String[] values = ((String)value).split(",");
                String[] var7 = values;
                int var8 = values.length;

                for(int var9 = 0; var9 < var8; ++var9) {
                    String v = var7[var9];
                    if (!map.containsKey(v)) {
                        return false;
                    }
                }

                return true;
            } else {
                return map.containsKey(String.valueOf(value));
            }
        }
    }

    public void initialize(AssertKeyValue assertKeyValue) {
        this.dictCode = assertKeyValue.dictCode();
        this.key = assertKeyValue.key();
    }
}
