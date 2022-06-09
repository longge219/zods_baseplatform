package zom.zods.exception.model;

import lombok.Data;

import java.io.Serializable;

/**
 * @author woody
 * @date 2022/1/5 13:47
 */
@Data
public class LogEsModel implements Serializable {

    private String app_name;
    private String threadName;
    private String ip;
    private String log_level;
    private String url;
    private String log_type;
    private long create_time;
    private String message;
}
