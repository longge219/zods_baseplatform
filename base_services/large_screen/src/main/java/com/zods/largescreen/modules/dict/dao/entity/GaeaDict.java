package com.zods.largescreen.modules.dict.dao.entity;
import com.baomidou.mybatisplus.annotation.TableName;
import com.zods.largescreen.common.annotation.Unique;
import com.zods.largescreen.common.code.ResponseCode;
import com.zods.largescreen.common.curd.entity.GaeaBaseEntity;
import java.io.Serializable;
/**
 * @desc GaeaDict 字典实体类
 * @author jianglong
 * @date 2022-06-23
 **/
@TableName(keepGlobalPrefix=true, value = "large_scrren_dict")
public class GaeaDict extends GaeaBaseEntity implements Serializable {

    /**字典名称*/
    private String dictName;

    /**字典编码*/
    @Unique(code = ResponseCode.DICCODE_ISEXIST)
    private String dictCode;

    /**字典描述*/
    private String remark;

    public String getDictName() {
        return dictName;
    }

    public void setDictName(String dictName) {
        this.dictName = dictName;
    }

    public String getDictCode() {
        return dictCode;
    }

    public void setDictCode(String dictCode) {
        this.dictCode = dictCode;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
