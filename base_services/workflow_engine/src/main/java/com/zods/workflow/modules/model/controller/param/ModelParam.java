package com.zods.workflow.modules.model.controller.param;
import com.zods.workflow.common.PageParam;
import lombok.Data;
import java.io.Serializable;
/**
 * @desc 流程图模型管理请求参数对象
 * @author jianglong
 * @date 2022-08-10
 **/
@Data
public class ModelParam  extends PageParam  implements Serializable {

    private  String modelId;

    private String  key;

    private String name;

    protected String category;

    protected Integer version;

    protected String description;

    private String json_xml;

    private String svg_xml;
}
