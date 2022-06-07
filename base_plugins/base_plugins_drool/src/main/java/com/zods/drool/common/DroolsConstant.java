package com.zods.drool.common;
/**
 * @author jianglong
 * @version 1.0
 * @Description
 * @createDate 2021/5/17 15:03
 */
public class DroolsConstant {

    /**
     * 规则文件后缀
     */
    public static final String SUFFIX_DRL = "drl";

    /**
     * 决策表后缀 excel 97-03版本的后缀 drools同样支持
     */
    public static final String SUFFIX_EXCEL = "xls";

    /**
     * 决策表后缀 excel 2007版本以后的后缀 drools同样支持
     */
    public static final String SUFFIX_EXCEL_2007 = "xlsx";

    /**
     * CSV后缀
     */
    public static final String SUFFIX_CSV = "csv";

    /**
     * 开启监听器的标识符
     */
    public static final String LISTENER_OPEN = "on";

    /**
     * 关闭监听器的标识符
     */
    public static final String LISTENER_CLOSE = "off";


    /**
     * 执行本地规则文件
     */
    public static final String EXECUTE_LOCAL = "local";

    /**
     * 执行远程规则文件
     */
    public static final String EXECUTE_REMOTE = "remote";
}
