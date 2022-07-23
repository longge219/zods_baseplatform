package com.zods.smart.iot.common.topic;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import java.util.Set;
/**
 * @author Jianglong
 * @version 1.0
 * @description: GUN-RFID消息主题
 * @date 2022-07-22
 */
@Data
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class GunRfidData {

    /**设备IP*/
    private String deviceIP;

    /**EPC*/
    private Set<String> epcs;

}
