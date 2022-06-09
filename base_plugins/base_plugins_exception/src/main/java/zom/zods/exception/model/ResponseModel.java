package zom.zods.exception.model;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import zom.zods.exception.enums.HandlerExceptionEnums;
import java.io.Serializable;
/**
 * @author Wangchao
 * @version 1.0
 * @Description 请求响应体
 * @createDate 2021/1/14 16:38
 */
@ApiModel(value = "请求响应体")
public class ResponseModel<T> implements Serializable {
    private static final long serialVersionUID = 3644322851500370651L;

    @ApiModelProperty(value = "响应状态")
    private Integer code = HandlerExceptionEnums.SUCCESS.code;

    @ApiModelProperty(value = "响应提示信息")
    private String message = HandlerExceptionEnums.SUCCESS.message;

    @ApiModelProperty(value = "响应结果")
    private T data;

    public ResponseModel(HandlerExceptionEnums exceptionEnums) {
        this.code = exceptionEnums.getCode();
        this.message = exceptionEnums.getMessage();
    }

    public ResponseModel(HandlerExceptionEnums exceptionEnums, T data) {
        this.code = exceptionEnums.getCode();
        this.message = exceptionEnums.getMessage();
        this.data = data;
    }

    public ResponseModel(Integer status, String message, T data) {
        this.code = status;
        this.message = message;
        this.data = data;
    }

    public ResponseModel(Integer status, String message) {
        this.code = status;
        this.message = message;
    }

    public ResponseModel(T data) {
        this.data = data;
    }

    public ResponseModel(String message) {
        this.message = message;
    }

    public ResponseModel(String message, T data) {
        this.message = message;
        this.data = data;
    }

    public ResponseModel() {
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
