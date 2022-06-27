package com.zods.largescreen.modules.file.controller;
import com.zods.largescreen.common.bean.ResponseBean;
import com.zods.largescreen.common.curd.controller.GaeaBaseController;
import com.zods.largescreen.common.curd.service.GaeaBaseService;
import com.zods.largescreen.modules.file.controller.dto.GaeaFileDTO;
import com.zods.largescreen.modules.file.controller.param.GaeaFileParam;
import com.zods.largescreen.modules.file.entity.GaeaFile;
import com.zods.largescreen.modules.file.service.GaeaFileService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
/**
 * @desc 文件管理-Controller
 * @author jianglong
 * @date 2022-06-23
 **/
@RestController
@RequestMapping("/file")
@Api(tags = "文件管理")
public class GaeaFileController extends GaeaBaseController<GaeaFileParam, GaeaFile, GaeaFileDTO> {
    @Autowired
    private GaeaFileService gaeaFileService;

    /**获取当前Controller数据库实体Entity*/
    @Override
    public GaeaFile getEntity() {
        return new GaeaFile();
    }

    /**获取当前Controller数据传输DTO*/
    @Override
    public GaeaFileDTO getDTO() {
        return new GaeaFileDTO();
    }

    /**获取实际服务类*/
    @Override
    public GaeaBaseService<GaeaFileParam, GaeaFile> getService() {
        return gaeaFileService;
    }

    @PostMapping("/upload")
    public ResponseBean upload(@RequestParam("file") MultipartFile file) {
        return ResponseBean.builder().message("success").data((gaeaFileService.upload(file))).build();
    }

    @GetMapping(value = "/download/{fileId}")
    public ResponseEntity<byte[]> download(HttpServletRequest request, HttpServletResponse response, @PathVariable("fileId") String fileId) {
        return gaeaFileService.download(request, response, fileId);
    }




}
