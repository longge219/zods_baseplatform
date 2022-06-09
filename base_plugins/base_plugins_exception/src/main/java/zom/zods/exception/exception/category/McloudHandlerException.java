package zom.zods.exception.exception.category;


import zom.zods.exception.enums.ExceptionEnums;
import zom.zods.exception.exception.BaseException;

/**
 * @author Wangchao
 * @version 1.0
 * @Description 系统业务处理异常
 * @createDate 2021/1/14 16:38
 */
public class McloudHandlerException extends BaseException {

    private static final long serialVersionUID = 7587499562534063205L;

    public McloudHandlerException(String message) {
        super(message);
    }

    public McloudHandlerException(String message, Throwable cause) {
        super(message, cause);
    }

    public McloudHandlerException(ExceptionEnums exceptionEnums) {
        super(exceptionEnums);
    }

    public McloudHandlerException(ExceptionEnums exceptionEnums, Throwable cause) {
        super(exceptionEnums, cause);
    }

    public McloudHandlerException(String message, int errorCode, Throwable cause) {
        super(message, errorCode, cause);
    }

    public McloudHandlerException(String message, int errorCode) {
        super(message, errorCode);
    }


}
