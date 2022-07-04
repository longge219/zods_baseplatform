package com.zods.smart.iot.electronic.server.protocal;
import com.zods.smart.iot.common.reflect.SubAnnotation;
import lombok.Data;
/**
 * @desc 电子围栏布防撤防
 * @author jianglong
 * @date 2022-06-30
 **/
@Data
public class ElectronicSidedeFenseCmd extends ElectronicPacketHead{
    //命令编码
    public int getCommandType() {
        return 0x05;
    }

    @SubAnnotation(len = "1", name = "", type = "byte",mark="")
    private byte secretOne;//密码1+密码2（高4位是密码1，低4位是密码2）)

    @SubAnnotation(len = "1", name = "", type = "byte",mark="")
    private byte secretTwo;//密码3+密码4（高4位是密码3，低4位是密码4）)

    @SubAnnotation(len = "1", name = "", type = "byte",mark="")
    private byte secretThree;//密码5+密码6（高4位是密码5，低4位是密码6）如果4位密码，此字节为0xFF)

    @SubAnnotation(len = "1", name = "", type = "byte",mark="")
    private byte sidedeFenseCmd;//布防侧防动作)
}
