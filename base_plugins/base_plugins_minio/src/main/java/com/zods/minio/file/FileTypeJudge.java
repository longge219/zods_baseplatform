package com.zods.minio.file;
import java.io.IOException;
import java.io.InputStream;
/**
 * @author Wangchao
 * @version 1.0
 * @Description
 * @createDate 2021/6/3 14:07
 */
public final class FileTypeJudge {

    private FileTypeJudge() {
    }

    /**
     * 图片
     */
    private static final FileType[] PICS = {FileType.JPEG, FileType.PNG, FileType.GIF, FileType.TIFF, FileType.BMP, FileType.DWG, FileType.PSD};
    /**
     * 文档
     */
    private static final FileType[] DOCS = {FileType.RTF, FileType.XML, FileType.HTML, FileType.CSS, FileType.JS, FileType.EML, FileType.DBX, FileType.PST, FileType.XLS_DOC, FileType.XLSX_DOCX, FileType.VSD,
            FileType.MDB, FileType.WPS, FileType.WPD, FileType.EPS, FileType.PDF, FileType.QDF, FileType.PWL, FileType.JSP, FileType.JAVA, FileType.CLASS,
            FileType.JAR, FileType.MF, FileType.EXE, FileType.CHM};
    /**
     * 压缩文件
     */
    private static final FileType[] COMPRESSED_FILE = {FileType.ZIP, FileType.RAR};
    /**
     * 视频
     */
    private static final FileType[] VIDEOS = {FileType.AVI, FileType.RAM, FileType.RM, FileType.MPG, FileType.MOV, FileType.ASF, FileType.MP4, FileType.FLV, FileType.MID};
    /**
     * 种子
     */
    private static final FileType[] TOTTENTS = {FileType.TORRENT};
    /**
     * 音乐
     */
    private static final FileType[] AUDIOS = {FileType.WAV, FileType.MP3};

    /**
     * 将文件头转换成16进制字符串
     *
     * @param src
     * @return 16进制字符串
     */
    private static String bytesToHexString(byte[] src) {
        StringBuilder stringBuilder = new StringBuilder();
        if (src == null || src.length <= 0) {
            return null;
        }
        for (int i = 0; i < src.length; i++) {
            int v = src[i] & 0xFF;
            String hv = Integer.toHexString(v);
            if (hv.length() < 2) {
                stringBuilder.append(0);
            }
            stringBuilder.append(hv);
        }
        return stringBuilder.toString();
    }

    /**
     * 得到文件头
     *
     * @param is
     * @return 文件头
     * @throws IOException
     */
    private static String getFileContent(InputStream is) throws IOException {
        byte[] b = new byte[28];
        InputStream inputStream = null;

        try {
            is.read(b, 0, 28);
        } catch (IOException e) {
            e.printStackTrace();
            throw e;
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                    throw e;
                }
            }
        }
        return bytesToHexString(b);
    }

    /**
     * 判断文件类型
     *
     * @param is
     * @return 文件类型
     * @throws IOException
     */
    public static FileType getType(InputStream is) throws IOException {
        String fileHead = getFileContent(is);
        if (fileHead == null || fileHead.length() == 0) {
            return null;
        }
        fileHead = fileHead.toUpperCase();
        FileType[] fileTypes = FileType.values();

        for (FileType type : fileTypes) {
            if (fileHead.startsWith(type.getValue())) {
                return type;
            }
        }

        return null;
    }

    /**
     * 字典 1-图片 2-文档 3-视频 4-压缩文件,5-音乐,6-种子,7-其它
     *
     * @param value 表示文件类型
     * @return
     */
    public static Integer isFileType(FileType value) {
        //其他
        Integer type = 7;

        // 图片
        for (FileType fileType : PICS) {
            if (fileType.equals(value)) {
                type = 1;
            }
        }
        // 文档
        for (FileType fileType : DOCS) {
            if (fileType.equals(value)) {
                type = 2;
            }
        }
        // 视频
        for (FileType fileType : VIDEOS) {
            if (fileType.equals(value)) {
                type = 3;
            }
        }
        // 压缩文件
        for (FileType fileType : COMPRESSED_FILE) {
            if (fileType.equals(value)) {
                type = 4;
            }
        }
        // 音乐
        for (FileType fileType : AUDIOS) {
            if (fileType.equals(value)) {
                type = 5;
            }
        }
        // 种子
        for (FileType fileType : TOTTENTS) {
            if (fileType.equals(value)) {
                type = 6;
            }
        }

        return type;
    }

}
