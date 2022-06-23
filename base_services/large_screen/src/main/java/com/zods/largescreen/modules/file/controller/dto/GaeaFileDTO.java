package com.zods.largescreen.modules.file.controller.dto;
import com.zods.largescreen.common.curd.dto.GaeaBaseDTO;
import lombok.Data;
/**
 * @desc 文件-Dto
 * @author jianglong
 * @date 2022-06-23
 **/
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
