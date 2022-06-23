package com.zods.largescreen.modules.file.entity;
import com.baomidou.mybatisplus.annotation.TableName;
import com.zods.largescreen.common.curd.entity.GaeaBaseEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import java.io.Serializable;
/**
 * @desc GaeaFile 文件实体类
 * @author jianglong
 * @date 2022-06-23
 **/
@TableName(keepGlobalPrefix=true, value = "large_scrren_file")
@Data
public class GaeaFile extends GaeaBaseEntity implements Serializable {

    @ApiModelProperty(value = "文件标识")
    private String fileId;

    @ApiModelProperty(value = "文件类型")
    private String fileType;

    @ApiModelProperty(value = "文件路径")
    private String filePath;

    @ApiModelProperty(value = "url路径")
    private String urlPath;

    @ApiModelProperty(value = "内容说明")
    private String fileInstruction;
}
