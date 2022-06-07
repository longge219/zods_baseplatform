package com.zods.minio.service;//package com.medicine.minio.service;
//
//import com.alibaba.fastjson.JSON;
//import com.alibaba.fastjson.JSONObject;
//import com.medicine.exception.category.HandlerException;
//import com.medicine.minio.constant.Const;
//import com.medicine.minio.model.McloudMinioClient;
//import com.medicine.minio.util.ImageUtil;
//import io.minio.*;
//import io.minio.errors.*;
//import io.minio.http.Method;
//import io.minio.messages.*;
//import lombok.AllArgsConstructor;
//import lombok.SneakyThrows;
//import lombok.extern.slf4j.Slf4j;
//import org.apache.commons.io.FilenameUtils;
//import org.apache.commons.lang3.StringUtils;
//import org.springframework.stereotype.Service;
//import org.springframework.util.CollectionUtils;
//import org.springframework.util.StreamUtils;
//import org.springframework.web.multipart.MultipartFile;
//
//import javax.imageio.ImageIO;
//import javax.servlet.ServletOutputStream;
//import javax.servlet.http.HttpServletResponse;
//import java.io.IOException;
//import java.io.InputStream;
//import java.nio.charset.StandardCharsets;
//import java.security.InvalidKeyException;
//import java.security.NoSuchAlgorithmException;
//import java.text.DecimalFormat;
//import java.time.LocalDate;
//import java.util.*;
//import java.util.concurrent.TimeUnit;
//import java.util.stream.Collectors;
//
///**
// * @author Wangchao
// * @version 1.0
// * @Description
// * @createDate 2021/2/27 09:32
// */
//@Slf4j
//@Service
//@AllArgsConstructor
//public class MinioService {
//
//    private final MinioConfig minioConfig;
//
//    private final McloudMinioClient minioClient;
//
//    private static final int DEFAULT_EXPIRY_TIME = (int) TimeUnit.DAYS.toSeconds(7L);
//
//    private static int RETRY_NUM = 3;
//
//    private final String FORMAT = "{'fileName':'%s','fileSize':'%s'}";
//
//    /**
//     * 检查存储桶是否存在
//     *
//     * @param bucketName 存储桶名称
//     * @return
//     */
//    @SneakyThrows
//    public boolean bucketExists(String bucketName) {
//        boolean flag = false;
//        BucketExistsArgs bArgs = BucketExistsArgs.builder().bucket(bucketName).build();
//        flag = minioClient.bucketExists(bArgs);
//        if (flag) {
//            return true;
//        }
//        return false;
//    }
//
//    /**
//     * 创建存储桶
//     *
//     * @param bucketName 存储桶名称
//     */
//    @SneakyThrows
//    public boolean makeBucket(String bucketName) {
//        boolean flag = bucketExists(bucketName);
//        if (!flag) {
//            minioClient.makeBucket(MakeBucketArgs.builder().bucket(bucketName).build());
//            return true;
//        } else {
//            return false;
//        }
//    }
//
//    /**
//     * 列出所有存储桶名称
//     *
//     * @return
//     */
//    @SneakyThrows
//    public List<String> listBucketNames() {
//        List<Bucket> bucketList = listBuckets();
//        List<String> bucketListName = new ArrayList<>();
//        for (Bucket bucket : bucketList) {
//            bucketListName.add(bucket.name());
//        }
//        return bucketListName;
//    }
//
//    /**
//     * 列出所有存储桶
//     *
//     * @return
//     */
//    @SneakyThrows
//    public List<Bucket> listBuckets() {
//        return minioClient.listBuckets();
//    }
//
//    /**
//     * 删除存储桶
//     *
//     * @param bucketName 存储桶名称
//     * @return
//     */
//    @SneakyThrows
//    public boolean removeBucket(String bucketName) {
//        boolean flag = bucketExists(bucketName);
//        if (flag) {
//            Iterable<Result<Item>> myObjects = listObjects(bucketName);
//            for (Result<Item> result : myObjects) {
//                Item item = result.get();
//                // 有对象文件，则删除失败
//                if (item.size() > 0) {
//                    return false;
//                }
//            }
//            // 删除存储桶，注意，只有存储桶为空时才能删除成功。
//            minioClient.removeBucket(RemoveBucketArgs.builder().bucket(bucketName).build());
//            flag = bucketExists(bucketName);
//            if (!flag) {
//                return true;
//            }
//
//        }
//        return false;
//    }
//
//    /**
//     * 列出存储桶中的所有对象名称
//     *
//     * @param bucketName 存储桶名称
//     * @return
//     */
//    @SneakyThrows
//    public List<String> listObjectNames(String bucketName) {
//        List<String> listObjectNames = new ArrayList<>();
//        boolean flag = bucketExists(bucketName);
//        if (flag) {
//            Iterable<Result<Item>> myObjects = listObjects(bucketName);
//            for (Result<Item> result : myObjects) {
//                Item item = result.get();
//                listObjectNames.add(item.objectName());
//            }
//        }
//        return listObjectNames;
//    }
//
//    /**
//     * 列出存储桶中的所有对象
//     *
//     * @param bucketName 存储桶名称
//     * @return
//     */
//    @SneakyThrows
//    public Iterable<Result<Item>> listObjects(String bucketName) {
//        //获取存储桶名
//        bucketName = StringUtils.isNotBlank(bucketName) ? bucketName : minioConfig.getBucketName();
//        boolean flag = bucketExists(bucketName);
//        if (flag) {
//            return minioClient.listObjects(ListObjectsArgs.builder().bucket(bucketName).build());
//        }
//        return null;
//    }
//
//    /**
//     * 获取路径下文件列表
//     *
//     * @param bucketName bucket名称
//     * @param prefix     文件名称
//     * @param recursive  是否递归查找，如果是false,就模拟文件夹结构查找
//     * @return 二进制流
//     */
//    public Iterable<Result<Item>> listObjects(String bucketName, String prefix, boolean recursive) {
//        return minioClient.listObjects(ListObjectsArgs.builder().bucket(bucketName).prefix(prefix).recursive(recursive).build());
//    }
//
//    /**
//     * 通过文件上传到对象
//     *
//     * @param bucketName 存储桶名称
//     * @param objectName 存储桶里的对象名称
//     * @param fileName   文件名(包含文件路径及其后缀)
//     * @return
//     */
//    @SneakyThrows
//    public boolean putObject(String bucketName, String objectName, String fileName) {
//        boolean flag = bucketExists(bucketName);
//        if (flag) {
//            UploadObjectArgs uploadArgs = UploadObjectArgs.builder().filename(fileName).bucket(bucketName).object(objectName).build();
//            ObjectWriteResponse response = minioClient.uploadObject(uploadArgs);
//            log.info("response:" + JSON.toJSON(response));
//            StatObjectResponse statObject = statObject(bucketName, objectName);
//            if (statObject != null && statObject.size() > 0) {
//                return true;
//            }
//        }
//        return false;
//    }
//
//
//    /**
//     * 上传文件
//     * 支持单文件，多文件
//     * 返回文件访问路径，多文件以分号‘；’分隔
//     *
//     * @param bucketName
//     * @param muFiles
//     * @return
//     */
//    public String uploadFiles(String bucketName, MultipartFile... muFiles) {
//        if (muFiles.length < 1) {
//            throw new RuntimeException("上传文件为空！");
//        }
//        StringBuilder str = new StringBuilder();
//        for (MultipartFile muFile : muFiles) {
//            str.append(uploadFile(muFile, bucketName));
//            str.append(";");
//        }
//        return str.deleteCharAt(str.length() - 1).toString();
//
//    }
//
//    /**
//     * 内部方法
//     * 上传文件
//     *
//     * @param muFile
//     * @return
//     */
//    private String uploadFile(MultipartFile muFile, String bucketName) {
//        String fileName = getFilePathName(muFile, true);
//        try {
//            if (!bucketExists(bucketName)) {
//                makeBucket(bucketName);
//            }
//            InputStream currentStream = getInputStream(muFile);
//            putObjectByInputStream(bucketName, currentStream, fileName, currentStream.available(), muFile.getContentType());
//        } catch (Exception e) {
//            log.error("文件上传失败", e);
//            throw new RuntimeException("文件上传失败");
//        }
//        return minioConfig.getOosUrlRead() + bucketName + fileName;
//    }
//
//    /**
//     * 获取文件流/如果是图片文件就进行压缩,加水印
//     *
//     * @param muFile
//     * @return
//     * @throws IOException
//     */
//    private InputStream getInputStream(MultipartFile muFile) throws IOException {
//        InputStream inputStream = muFile.getInputStream();
//        // 如果是图片文件就进行压缩
//        if (ImageUtil.isImage(muFile.getOriginalFilename())) {
//            inputStream = ImageUtil.getInputStream(
//                    ImageUtil.setWatermark(
//                            ImageUtil.compress(
//                                    ImageIO.read(inputStream))),
//                    FilenameUtils.getExtension(muFile.getOriginalFilename()));
//        }
//        return inputStream;
//    }
//
//    /**
//     * 获取文件名
//     *
//     * @param muFile   文件
//     * @param isRetain 是否保留源文件名
//     * @return 返回文件名，以当前年月日作为前缀路径
//     */
//    private String getFilePathName(MultipartFile muFile, boolean isRetain) {
//        String fileName = muFile.getOriginalFilename();
//        String name = fileName;
//        String prefix = "";
//        if (fileName.indexOf('.') != -1) {
//            name = FilenameUtils.getBaseName(fileName);
//            prefix = "." + FilenameUtils.getExtension(fileName);
//        }
//
//        LocalDate date = LocalDate.now();
//        StringBuilder filePathName = new StringBuilder("upload/");
//        filePathName.append(date.getYear());
//        filePathName.append(Const.URI_DELIMITER);
//        filePathName.append(date.getMonthValue());
//        filePathName.append(Const.URI_DELIMITER);
//        filePathName.append(date.getDayOfMonth());
//        filePathName.append(Const.URI_DELIMITER);
//        //添加随机后缀
//        Random r = new Random();
//        int pix = r.ints(1, (100 + 1)).findFirst().getAsInt();
//        filePathName.append(System.currentTimeMillis());
//        filePathName.append("" + pix);
//        //文件名超过32字符则截取
//        if (isRetain) {
//            filePathName.append("_");
//            if (name.length() >= 32) {
//                name = name.substring(0, 32);
//            }
//            filePathName.append(name);
//        }
//        filePathName.append(prefix);
//        return filePathName.toString();
//    }
//
//    /**
//     * 上传文件
//     *
//     * @param multipartFiles
//     * @return
//     */
//    public List<String> putObject(MultipartFile... multipartFiles) {
//        if (multipartFiles.length < 1) {
//            throw new RuntimeException("上传文件为空！");
//        }
//
//        try {
//            List<String> retVal = new LinkedList<>();
//            String[] folders = getDateFolder();
//            for (MultipartFile multipartFile : multipartFiles) {
//                // UUID重命名
//                String fileName = UUID.randomUUID().toString().replace("-", "") + "." + FilenameUtils.getExtension(multipartFile.getOriginalFilename());
//                // 年/月/日/file
//                String finalPath = new StringBuilder(String.join(Const.URI_DELIMITER, folders))
//                        .append(Const.URI_DELIMITER)
//                        .append(fileName).toString();
//
//                minioClient.putObject(PutObjectArgs.builder()
//                        .stream(multipartFile.getInputStream(), multipartFile.getSize(), -1)
//                        .object(finalPath)
//                        .contentType(multipartFile.getContentType())
//                        .bucket(minioConfig.getBucketName())
//                        .build());
//
//                retVal.add(gateway(finalPath));
//            }
//            return retVal;
//        } catch (Exception e) {
//            throw new RuntimeException(e);
//        }
//    }
//
//    /**
//     * 获取访问网关
//     *
//     * @param path
//     * @return
//     */
//    protected String gateway(String path) {
//        return minioConfig.getOosUrlRead() + path;
//    }
//
//    /**
//     * 获取年月日[2020, 09, 01]
//     *
//     * @return
//     */
//    protected String[] getDateFolder() {
//        String[] retVal = new String[3];
//
//        LocalDate localDate = LocalDate.now();
//        retVal[0] = localDate.getYear() + "";
//
//        int month = localDate.getMonthValue();
//        retVal[1] = month < 10 ? "0" + month : month + "";
//
//        int day = localDate.getDayOfMonth();
//        retVal[2] = day < 10 ? "0" + day : day + "";
//
//        return retVal;
//    }
//
//    /**
//     * 文件上传
//     *
//     * @param bucketName
//     * @param file
//     */
//    @SneakyThrows
//    public void putObject(String bucketName, MultipartFile file, String objectName) {
//        putObjectByInputStream(bucketName, file.getInputStream(), objectName, file.getSize(), file.getContentType());
//    }
//
//    /**
//     * 根据流上传文件
//     *
//     * @param bucketName
//     * @param stream
//     * @param objectName
//     * @param objectSize
//     * @param contentType
//     */
//    @SneakyThrows
//    private void putObjectByInputStream(String bucketName, InputStream stream, String objectName, long objectSize, String contentType) {
//        minioClient.putObject(PutObjectArgs.builder().
//                bucket(bucketName).object(objectName).
//                stream(stream, objectSize, -1).
//                contentType(contentType).build());
//    }
//
//    /**
//     * 文件对象上传
//     *
//     * @param objectName 服务器中对象名称(包含文件后缀)
//     * @param fileName   文件名(包含文件路径及其后缀)
//     * @return
//     */
//    public String uploadFileByPath(String bucketName, String objectName, String fileName) {
//        putObject(bucketName, objectName, fileName);
//        return minioConfig.getOosUrlRead() + minioConfig.getBucketName() + Const.URI_DELIMITER + objectName;
//    }
//
//    /**
//     * 以流的形式获取一个文件对象
//     *
//     * @param bucketName 存储桶名称
//     * @param objectName 存储桶里的对象名称
//     * @return
//     */
//    @SneakyThrows
//    public InputStream getObject(String bucketName, String objectName) {
//        boolean flag = bucketExists(bucketName);
//        if (flag) {
//            StatObjectResponse statObject = statObject(bucketName, objectName);
//            if (statObject != null && statObject.size() > 0) {
//                InputStream stream = minioClient.getObject(GetObjectArgs.builder().bucket(bucketName).object(objectName).build());
//                return stream;
//            }
//        }
//        return null;
//    }
//
//    /**
//     * 以流的形式获取一个文件对象（断点下载）
//     *
//     * @param bucketName 存储桶名称
//     * @param objectName 存储桶里的对象名称
//     * @param offset     起始字节的位置
//     * @param length     要读取的长度 (可选，如果无值则代表读到文件结尾)
//     * @return
//     */
//    @SneakyThrows
//    public InputStream getObject(String bucketName, String objectName, long offset, Long length) {
//        boolean flag = bucketExists(bucketName);
//        if (flag) {
//            StatObjectResponse statObject = statObject(bucketName, objectName);
//            if (statObject != null && statObject.size() > 0) {
//                InputStream stream = minioClient.getObject(GetObjectArgs.builder().bucket(bucketName).object(objectName).offset(offset).length(length).build());
//                return stream;
//            }
//        }
//        return null;
//    }
//
//    /**
//     * 下载并将文件保存到本地
//     *
//     * @param bucketName 存储桶名称
//     * @param objectName 存储桶里的对象名称
//     * @param fileName   File name
//     * @return
//     */
//    @SneakyThrows
//    public boolean getObject(String bucketName, String objectName, String fileName) {
//        boolean flag = bucketExists(bucketName);
//        if (flag) {
//            StatObjectResponse statObject = statObject(bucketName, objectName);
//            if (statObject != null && statObject.size() > 0) {
//                minioClient.getObject(GetObjectArgs.builder().bucket(bucketName).object(objectName).build());
//                return true;
//            }
//        }
//        return false;
//    }
//
//    /**
//     * 删除一个对象
//     *
//     * @param bucketName 存储桶名称
//     * @param objectName 存储桶里的对象名称
//     */
//    @SneakyThrows
//    public boolean removeObject(String bucketName, String objectName) {
//        boolean flag = bucketExists(bucketName);
//        if (flag) {
//            minioClient.removeObject(RemoveObjectArgs.builder().bucket(bucketName).object(objectName).build());
//            return true;
//        }
//        return false;
//    }
//
//    /**
//     * 删除指定桶的多个文件对象,返回删除错误的对象列表，全部删除成功，返回空列表
//     *
//     * @param bucketName  存储桶名称
//     * @param objectNames 含有要删除的多个object名称的迭代器对象
//     * @return
//     */
//    @SneakyThrows
//    public List<String> removeObject(String bucketName, List<String> objectNames) {
//        List<String> deleteErrorNames = new ArrayList<>();
//        boolean flag = bucketExists(bucketName);
//        if (flag) {
//            List<DeleteObject> deleteObjects = objectNames.stream().parallel().map(o -> {
//                DeleteObject deleteObject = new DeleteObject(o);
//                return deleteObject;
//            }).collect(Collectors.toList());
//            Iterable<Result<DeleteError>> results = minioClient.removeObjects(RemoveObjectsArgs.builder().bucket(bucketName).objects(deleteObjects).build());
//            for (Result<DeleteError> result : results) {
//                DeleteError error = result.get();
//                deleteErrorNames.add(error.objectName());
//            }
//        }
//        return deleteErrorNames;
//    }
//
//    @SneakyThrows
//    public List<String> presignedGetObject(String bucketName, Integer expires, String... objectNames) {
//        //获取存储桶名
//        bucketName = StringUtils.isNotBlank(bucketName) ? bucketName : minioConfig.getBucketName();
//        boolean flag = bucketExists(bucketName);
//        String finalBucketName = bucketName;
//        List<String> urls = Arrays.stream(objectNames).map(objectName -> {
//            String url = "";
//            if (flag) {
//                if (expires < 1 || expires > DEFAULT_EXPIRY_TIME) {
//                    throw new HandlerException(expires + ": expires must be in range of 1 to " + DEFAULT_EXPIRY_TIME);
//                }
//                try {
//                    url = minioClient.getPresignedObjectUrl(
//                            GetPresignedObjectUrlArgs.builder().
//                                    method(Method.GET).
//                                    bucket(finalBucketName).
//                                    object(objectName).
//                                    expiry(expires).
//                                    build());
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            }
//            return url;
//        }).collect(Collectors.toList());
//
//        return urls;
//    }
//
//    /**
//     * 生成一个给HTTP GET请求用的presigned URL。
//     * 浏览器/移动端的客户端可以用这个URL进行下载，即使其所在的存储桶是私有的。这个presigned URL可以设置一个失效时间，默认值是7天。
//     *
//     * @param json
//     * @return
//     */
//    @SneakyThrows
//    public String preSignedGetObject(String json) throws Exception {
//        JSONObject jsonObject = JSON.parseObject(json);
//        String bucketName = jsonObject.getString("bucketName");
//        Integer expires = jsonObject.getInteger("expires");
//        List<String> objectNames = jsonObject.getJSONArray("objectNames").toJavaList(String.class);
//
//        // 1.校验参数合法性
//        String bucket = StringUtils.isNotBlank(bucketName) ? bucketName : minioConfig.getBucketName();
//        if (StringUtils.isEmpty(bucket)) {
//            throw new HandlerException("bucketName为空,请传入bucketName");
//        }
//        if (expires != null && (expires <= 0 || expires > DEFAULT_EXPIRY_TIME)) {
//            throw new HandlerException(expires + ": expires must be in range of 1 to " + DEFAULT_EXPIRY_TIME);
//        }
//        if (!bucketExists(bucketName)) {
//            throw new HandlerException("该bucketName[" + bucketName + "]不存在,请重新选择");
//        }
//        if(CollectionUtils.isEmpty(objectNames)){
//            throw new HandlerException("objectNames不能为空,请重新选择");
//        }
//
//        // 2.获取预览地址
//        JSONObject object = new JSONObject();
//        objectNames.forEach(objectName -> {
//            try {
//                String url = minioClient.getPresignedObjectUrl(
//                        GetPresignedObjectUrlArgs.builder().
//                                method(Method.GET).
//                                bucket(bucket).
//                                object(objectName).
//                                expiry(expires == null ? DEFAULT_EXPIRY_TIME : expires).
//                                build());
//                object.put(objectName, url);
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        });
//
//        return object.toJSONString();
//    }
//
//    /**
//     * 生成一个给HTTP PUT请求用的presigned URL。
//     * 浏览器/移动端的客户端可以用这个URL进行上传，即使其所在的存储桶是私有的。这个presigned URL可以设置一个失效时间，默认值是7天。
//     *
//     * @param bucketName 存储桶名称
//     * @param objectName 存储桶里的对象名称
//     * @param expires    失效时间（以秒为单位），默认是7天，不得大于七天
//     * @return
//     */
//    @SneakyThrows
//    public String presignedPutObject(String bucketName, String objectName, Integer expires) {
//        boolean flag = bucketExists(bucketName);
//        String url = "";
//        if (flag) {
//            if (expires < 1 || expires > DEFAULT_EXPIRY_TIME) {
//                throw new HandlerException(expires + ": expires must be in range of 1 to " + DEFAULT_EXPIRY_TIME);
//            }
//            url = minioClient.getPresignedObjectUrl(
//                    GetPresignedObjectUrlArgs.builder()
//                            .method(Method.PUT)
//                            .bucket(bucketName)
//                            .object(objectName)
//                            .expiry(expires)
//                            .build());
//        }
//        return url;
//    }
//
//    /**
//     * 获取对象的元数据
//     *
//     * @param bucketName 存储桶名称
//     * @param objectName 存储桶里的对象名称
//     * @return
//     */
//    public StatObjectResponse statObject(String bucketName, String objectName) throws IOException, InvalidKeyException, InvalidResponseException, InsufficientDataException, NoSuchAlgorithmException, ServerException, InternalException, XmlParserException, ErrorResponseException {
//        boolean flag = bucketExists(bucketName);
//        if (flag) {
//            return minioClient.statObject(StatObjectArgs.builder().bucket(bucketName).object(objectName).build());
//        }
//        return null;
//    }
//
//    /**
//     * 文件访问路径
//     *
//     * @param bucketName 存储桶名称
//     * @param objectName 存储桶里的对象名称
//     * @return
//     */
//    @SneakyThrows
//    public String getObjectUrl(String bucketName, String objectName) {
//        boolean flag = bucketExists(bucketName);
//        String url = "";
//        if (flag) {
//            url = minioClient.getPresignedObjectUrl(GetPresignedObjectUrlArgs.builder().expiry(30).bucket(bucketName).method(Method.GET).object(objectName).build());
//        }
//        return url;
//    }
//
//    /**
//     * 获取存储桶策略
//     *
//     * @param bucketName 存储桶名称
//     * @return
//     */
//    @SneakyThrows
//    public String getBucketPolicy(String bucketName) {
//        return minioClient.getBucketPolicy(GetBucketPolicyArgs.builder().bucket(bucketName).build());
//    }
//
//    /**
//     * 文件下载
//     *
//     * @param bucketName   存储桶名称
//     * @param objectName   文件名称
//     * @param originalName 文件原始名
//     * @param response     响应
//     */
//    public void downloadFile(String bucketName, String objectName, String originalName, HttpServletResponse response) {
//        try {
//            GetObjectArgs args = GetObjectArgs.builder().bucket(bucketName).object(objectName).build();
//            InputStream file = minioClient.getObject(args);
//            String filename = new String(objectName.getBytes("ISO8859-1"), StandardCharsets.UTF_8);
//            if (StringUtils.isNotEmpty(originalName)) {
//                filename = originalName;
//            }
//            response.setHeader("Content-Disposition", "attachment;filename=" + filename);
//            ServletOutputStream servletOutputStream = response.getOutputStream();
//            int len;
//            byte[] buffer = new byte[1024];
//            while ((len = file.read(buffer)) > 0) {
//                servletOutputStream.write(buffer, 0, len);
//            }
//            servletOutputStream.flush();
//            file.close();
//            servletOutputStream.close();
//        } catch (ErrorResponseException e) {
//            e.printStackTrace();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//
//    /**
//     * 文件下载字节数组
//     *
//     * @param bucketName   存储桶名称
//     * @param objectName   文件名称
//     */
//    public byte[] downloadFileBytes(String bucketName, String objectName) {
//        try {
//            GetObjectArgs args = GetObjectArgs.builder().bucket(bucketName).object(objectName).build();
//            InputStream file = minioClient.getObject(args);
//            byte[] bytes = StreamUtils.copyToByteArray(file);
//            file.close();
//            return bytes;
//        } catch (ErrorResponseException e) {
//            e.printStackTrace();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return null;
//    }
//
//
//    /**
//     * 文件下载
//     *
//     * @param filename
//     * @param bucketName
//     * @param objectName
//     */
//    @SneakyThrows
//    public void downloadFile(String filename, String bucketName, String objectName) {
//        minioClient.downloadObject(DownloadObjectArgs.builder().filename(filename).bucket(bucketName).object(objectName).build());
//    }
//
//    /**
//     * 初始化获取分片上传URLS
//     *
//     * @param bucketName
//     * @param objectName
//     * @param totalPart
//     * @return
//     */
//    public Map<String, Object> initMultiPartUpload(String bucketName, String objectName, int totalPart) {
//        Map<String, Object> result = new HashMap<>();
//        try {
//            String uploadId = minioClient.initMultiPartUpload(bucketName, null, objectName, null, null);
//
//            result.put("uploadId", uploadId);
//            List<String> partList = new ArrayList<>();
//            Map<String, String> reqParams = new HashMap<>();
//            // 暂时放置于此:reqParams.put("response-content-type", "application/json");
//            reqParams.put("uploadId", uploadId);
//            for (int i = 1; i <= totalPart; i++) {
//                reqParams.put("partNumber", String.valueOf(i));
//                String uploadUrl = minioClient.getPresignedObjectUrl(
//                        GetPresignedObjectUrlArgs.builder()
//                                .method(Method.PUT)
//                                .bucket(bucketName)
//                                .object(objectName)
//                                .expiry(1, TimeUnit.DAYS)
//                                .extraQueryParams(reqParams)
//                                .build());
//                partList.add(uploadUrl);
//            }
//            result.put("uploadUrls", partList);
//        } catch (Exception e) {
//            log.error("initMultiPartUpload error: {}", e.getMessage(), e);
//            return null;
//        }
//
//        return result;
//    }
//
//    /**
//     * 合并分段上传
//     *
//     * @param bucketName
//     * @param objectName
//     * @param uploadId
//     * @return
//     */
//    public boolean mergeMultipartUpload(String bucketName, String objectName, String uploadId) {
//        try {
//            // S3 API的限制: 每次list multipart uploads请求可返回的multipart uploads最大数量
//            ListPartsResponse partResult = minioClient.listMultipart(bucketName, null, objectName, 1000, 0, uploadId, null, null);
//            int partNumber = 1;
//            Part[] parts = new Part[1000];
//            for (Part part : partResult.result().partList()) {
//                parts[partNumber - 1] = new Part(partNumber, part.etag());
//                partNumber++;
//            }
//            minioClient.mergeMultipartUpload(bucketName, null, objectName, uploadId, parts, null, null);
//        } catch (Exception e) {
//            log.error("mergeMultipartUpload error: {}", e.getMessage(), e);
//            return false;
//        }
//
//        return true;
//    }
//
//    /**
//     * 预览文件
//     *
//     * @param bucketName
//     * @param expires
//     */
//    @SneakyThrows
//    public List<Object> privewList(String bucketName, int expires) {
//        return getItems(bucketName, expires, listObjects(bucketName).iterator(), new ArrayList<>());
//    }
//
//    @SneakyThrows
//    private List<Object> getItems(String bucketName, int expires, Iterator<Result<Item>> iterator, List<Object> items) {
//        while (iterator.hasNext()) {
//            //返回迭代中的下一个元素。
//            Item item = iterator.next().get();
//            if (StringUtils.isNotBlank(item.objectName()) && item.objectName().endsWith(Const.URI_DELIMITER)) {
//                Iterator<Result<Item>> iteratorItems = listObjects(bucketName, item.objectName(), false).iterator();
//                getItems(bucketName, expires, iteratorItems, items);
//                continue;
//            }
//            //生成一个给HTTP GET请求用的presigned URL
//            String filePath = presignedGetObject(bucketName, expires, item.objectName()).get(0);
//            //封装信息
//            items.add(JSON.parse(String.format(FORMAT, filePath, formatFileSize(item.size()))));
//        }
//
//        return items;
//    }
//
//    /**
//     * 显示文件大小信息单位
//     *
//     * @param fileS
//     * @return
//     */
//    private String formatFileSize(long fileS) {
//        DecimalFormat df = new DecimalFormat("#.00");
//        String fileSizeString = "";
//        String wrongSize = "0B";
//        if (fileS == 0) {
//            return wrongSize;
//        }
//        if (fileS < 1024) {
//            fileSizeString = df.format((double) fileS) + " B";
//        } else if (fileS < 1048576) {
//            fileSizeString = df.format((double) fileS / 1024) + " KB";
//        } else if (fileS < 1073741824) {
//            fileSizeString = df.format((double) fileS / 1048576) + " MB";
//        } else {
//            fileSizeString = df.format((double) fileS / 1073741824) + " GB";
//        }
//        return fileSizeString;
//    }
//
//}
