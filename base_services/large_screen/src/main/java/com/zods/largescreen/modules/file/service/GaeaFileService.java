package com.zods.largescreen.modules.file.service;
import com.zods.largescreen.common.curd.service.GaeaBaseService;
import com.zods.largescreen.modules.file.controller.param.GaeaFileParam;
import com.zods.largescreen.modules.file.entity.GaeaFile;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
/**
 * @desc 文件管理-service
 * @author jianglong
 * @date 2022-06-23
 **/
public interface GaeaFileService extends GaeaBaseService<GaeaFileParam, GaeaFile> {

    /**
     * 文件上传
     * @param multipartFile  文件
     * @return GaeaFile 对象
     */
    GaeaFile upload(MultipartFile multipartFile);

    /**
     * 文件上传
     * @param file 二选一
     * @param customFileName 自定义文件名
     * @return GaeaFile 对象
     */
    GaeaFile upload(File file, String customFileName);

    /**
     * 文件上传
     * @param multipartFile  文件
     * @param file 文件
     * @param customFileName 自定义文件名，默认给null
     * @return GaeaFile 对象
     */
    GaeaFile upload(MultipartFile multipartFile, File file, String customFileName);

    /**
     * 根据fileId显示图片或者下载文件
     * @param request 请求request对象
     * @param response 返回response对象
     * @param fileId 文件ID
     * @return 返回下载文件流
     */
    ResponseEntity<byte[]> download(HttpServletRequest request, HttpServletResponse response, String fileId);
}
