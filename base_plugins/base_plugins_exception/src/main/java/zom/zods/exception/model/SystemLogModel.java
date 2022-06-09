package zom.zods.exception.model;

import lombok.Data;

import java.io.Serializable;

/**
 * @author woody
 * @date 2021/12/23 11:16
 */
@Data
public class SystemLogModel implements Serializable {
    /**
     * id
     */
    private String id;
    /**
     * 线程名称
     */
    private String threadName;
    /**
     * 日志级别
     */
    private String logLevel;
    /**
     * 方法名
     */
    private String methodName;
    /**
     * 行号
     */
    private int LineNumber;
    /**
     * 线程id
     */
    private String threadId;
    /**
     * 创建时间
     */
    private long createOn;
    /**
     * 名称
     */
    private String name;
    /**
     * 类名
     */
    private String className;
    /**
     * 消息
     */
    private String msg;
    /**
     * 上下文
     */
    private String context;
    /**
     * 异常栈
     */
    private String errorStack;
    /**
     * 服务名称
     */
    private String serviceName;
}
