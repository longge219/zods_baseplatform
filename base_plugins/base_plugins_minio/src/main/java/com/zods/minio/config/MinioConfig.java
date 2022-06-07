package com.zods.minio.config;//package com.medicine.minio.config;
//
//import com.medicine.minio.constant.Const;
//import com.medicine.minio.model.McloudMinioClient;
//import io.minio.MinioClient;
//import lombok.Data;
//import org.springframework.boot.context.properties.ConfigurationProperties;
//import org.springframework.context.annotation.Bean;
//import org.springframework.stereotype.Component;
//
//
///**
// * @author Wangchao
// * @version 1.0
// * @Description
// * @createDate 2021/2/27 17:45
// */
//@Data
//@Component
//@ConfigurationProperties(prefix = "minio")
//public class MinioConfig {
//
//    /**
//     * 读写分离-上传服务器
//     */
//    private String endpointWrite;
//
//    private String endpointRead;
//
//    private int port;
//
//    private String accessKey;
//
//    private String secretKey;
//
//    private Boolean secure;
//
//    private String bucketName;
//
//    /**
//     * 读写分离-下载服务器地址
//     */
//    private String oosUrlRead;
//
//    @Bean
//    public McloudMinioClient getMinioClient() {
//        initOssUrl();
//        MinioClient minioClient = null;
//        try {
//            minioClient = MinioClient.builder().endpoint(endpointWrite, port, secure)
//                    .credentials(accessKey, secretKey)
//                    .build();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return new McloudMinioClient(minioClient);
//    }
//
//    /**
//     * 初始化对象存储URL
//     */
//    private void initOssUrl() {
//        String suffix = endpointRead + ":" + port + Const.URI_DELIMITER;
//        oosUrlRead = secure ? Const.HTTPS + suffix : Const.HTTP + suffix;
//    }
//
//}
