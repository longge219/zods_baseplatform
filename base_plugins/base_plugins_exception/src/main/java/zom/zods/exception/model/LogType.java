package zom.zods.exception.model;

/**
 * @author woody
 * @date 2021/12/22 16:08
 */
public enum LogType {
    VISIT_LOG("访问日志","visitLog"),
    SYSTEM_LOG("系统日志","sysLog");

    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    private String value;

    LogType(String name, String value){
        this.name = name;
        this.value = value;
    }

    public static LogType getByType(String type) {
        for (LogType logType : values()) {
            if (logType.getValue().equals(type)) {
                //获取指定的枚举
                return logType;
            }
        }
        return null;
    }
}
