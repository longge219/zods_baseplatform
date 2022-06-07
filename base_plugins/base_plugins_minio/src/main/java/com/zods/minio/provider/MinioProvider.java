package com.zods.minio.provider;//package com.medicine.minio.provider;
//
//import com.medicine.minio.service.MinioService;
//import lombok.AllArgsConstructor;
//import org.springframework.stereotype.Component;
//import org.springframework.web.multipart.MultipartFile;
//
//import javax.servlet.http.HttpServletResponse;
//import java.util.List;
//
///**
// * @author Wangchao
// * @version 1.0
// * @Description
// * @createDate 2021/2/27 09:38
// */
//@AllArgsConstructor
//@Component
//public class MinioProvider {
//
//    private final MinioService minioService;
//
//    public String uploadOssByPath(String bucketName, String objectName, String fileName) {
//        return minioService.uploadFileByPath(bucketName, objectName, fileName);
//    }
//
//    public void uploadOss(String bucketName, MultipartFile file, String objectName) {
//        minioService.putObject(bucketName, file, objectName);
//    }
//
//    public boolean deleteOss(String bucketName, String objectName) {
//        return minioService.removeObject(bucketName, objectName);
//    }
//
//    public void downloadOssByPath(String bucketName, String filename, String objectName) {
//        minioService.downloadFile(filename, bucketName, objectName);
//    }
//
//    public void downloadOss(String bucketName, String objectName, HttpServletResponse response) {
//        minioService.downloadFile(bucketName, objectName, objectName, response);
//    }
//
//    /**
//     * 下载文件对象字节数组
//     *
//     * @param bucketName
//     * @param objectName
//     */
//    public byte[] downloadOssBytes(String bucketName, String objectName) {
//        return minioService.downloadFileBytes(bucketName, objectName);
//    }
//
//    /**
//     * 生成单个可以预览的文件链接
//     *
//     * @return
//     */
//    public String getPreviewFile(String json) throws Exception {
//        return minioService.preSignedGetObject(json);
//    }
//
//    /**
//     * 生成当前存储桶内所有可预览的文件链接
//     *
//     * @param bucketName
//     * @param expires
//     * @return
//     */
//    public List<Object> getPreviewList(String bucketName, Integer expires) {
//        return minioService.privewList(bucketName, expires);
//    }
//
//    /**
//     * 上传附件
//     *
//     * @param bucketName
//     * @param files
//     * @return
//     */
//    public String uploadFiles(String bucketName, MultipartFile... files) {
//        return minioService.uploadFiles(bucketName, files);
//    }
//
//}
