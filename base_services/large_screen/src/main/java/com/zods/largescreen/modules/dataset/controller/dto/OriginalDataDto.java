package com.zods.largescreen.modules.dataset.controller.dto;

import com.alibaba.fastjson.JSONObject;
import lombok.Data;

import java.io.Serializable;
import java.util.List;
/**
 * @desc 源数据 dto
 * @author jianglong
 * @date 2022-06-22
 **/
@Data
public class OriginalDataDto implements Serializable {

    /**总数*/
    private long total;

    /**获取的数据详情*/
    private List<JSONObject> data;

    public OriginalDataDto(List<JSONObject> data) {
        this.data = data;
    }

    public OriginalDataDto(long total, List<JSONObject> data) {
        this.total = total;
        this.data = data;
    }

    public OriginalDataDto() {
    }
}
