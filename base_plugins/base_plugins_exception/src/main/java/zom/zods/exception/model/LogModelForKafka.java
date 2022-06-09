package zom.zods.exception.model;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * @author woody
 * @date 2022/1/5 13:38
 */
@Data
@Accessors(chain = true)
public class LogModelForKafka<T> implements Serializable {

    private String rowkey;
    private LogEsModel logEsModel;
    private T logInfo;
}
