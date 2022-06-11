package com.zods.smart.iot.electronic.server.protocal;
import com.zods.smart.iot.electronic.server.reflect.SubAnnotation;
import lombok.Data;
/**
 * @Description:设备状态报文
 * @create Author:jianglong
 * @create Date:2022-06-11
 * @version V1.0
 */
@Data
public class EquipStatus extends PacketHead {

    //命令编码
    public int getCommandType() {
        return 0x02;
    }

    @SubAnnotation(len = "1", name = "", type = "byte",mark="")
    private byte zoneAlarm;//防区报警---布防(bit位(0-7) 0:正常 1:报警)

    @SubAnnotation(len = "1", name = "", type = "byte",mark="")
    private byte equipStatus;//设备状态(bit位(0-7)  布防状态,紧急求助,被撬,留守布防,交流状态,测试（检修）模式,直流欠压,备用)

    @SubAnnotation(len = "1", name = "", type = "byte",mark="")
    private byte deploymentStatus;//防区撤布防(bit位(0-7) 0:布防1:撤防)

    @SubAnnotation(len = "1", name = "", type = "byte",mark="byLeangth")
    private byte timingStatus;//防区实时状态--未布防(bit位(0-7) 0:正常 1:触发)
}
