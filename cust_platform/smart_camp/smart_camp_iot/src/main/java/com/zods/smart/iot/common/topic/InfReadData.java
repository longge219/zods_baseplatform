package com.zods.smart.iot.common.topic;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * @author Jianglong
 * @version 1.0
 * @description: 红外事件消息主题
 * @date 2021年10月13日 16:40:00
 */
@Data
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class InfReadData {
    /**
     * 设备编码
     */
    private String deviceCode;

    /**
     * 状态(0:报警修复；1:报警)
     */
    private int status;

    /**
     * 识别时间
     */
    private String dateStr;
}
