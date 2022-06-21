package com.zods.largescreen.modules.file.controller.dto;
import com.zods.largescreen.common.curd.dto.GaeaBaseDTO;
import lombok.Data;

/**
 * (GaeaFile)实体类
 *
 * @author peiyanni
 * @since 2021-02-18 14:48:27
 */
@Data
public class GaeaFileDTO extends GaeaBaseDTO {

    /** 文件标识 */
    private String fileId;

    /** 文件类型 */
    private String fileType;

    /** 文件路径 */
    private String filePath;

    /** url路径 */
    private String urlPath;

    /** 内容说明 */
    private String fileInstruction;
}
