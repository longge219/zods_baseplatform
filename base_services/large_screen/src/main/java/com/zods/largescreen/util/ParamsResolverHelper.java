package com.zods.largescreen.util;
import org.springframework.util.PropertyPlaceholderHelper;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
/**
 * @author jianglong
 * @version 1.0
 * @Description 参数转换工具类
 * @createDate 2022-06-22
 */
public class ParamsResolverHelper {

    //参数开始符号
    private static String placeholderPrefix = "${";

    //参数结束符号
    private static String placeholderSuffix = "}";

    private static PropertyPlaceholderHelper helper = new PropertyPlaceholderHelper(placeholderPrefix, placeholderSuffix);

    /**
     * 参数填充
     * @param param 动态参数结合
     * @param con 查询语句或接口
     * @return 组装参数后的动态查询语句或接口字符串
     */
    public static String resolveParams(final Map<String, Object> param, String con) {
        con = helper.replacePlaceholders(con, (key -> param.get(key) + ""));
        return con;
    }

    private static Pattern key = Pattern.compile("\\$\\{(.*?)\\}");

    //通过key查找参数
    public static List<String> findParamKeys(String con) {
        Matcher m = key.matcher(con);
        List ret = new ArrayList();
        while (m.find()) {
            ret.add(m.group(1));
        }
        return ret;
    }

}
