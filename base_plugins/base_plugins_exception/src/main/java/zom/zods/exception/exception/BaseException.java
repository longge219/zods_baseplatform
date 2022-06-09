package zom.zods.exception.exception;
import zom.zods.exception.enums.ExceptionEnums;
/**
 * @author Wangchao
 * @version 1.0
 * @Description 系统异常基类
 * @createDate 2021/1/14 16:38
 */
public class BaseException extends RuntimeException {

    private static final long serialVersionUID = 9008026268539823228L;
    private int errorCode = 500;
    private ExceptionEnums exceptionEnums;

    /**
     * @param exceptionEnums 异常
     */
    public BaseException(final ExceptionEnums exceptionEnums) {
        super(exceptionEnums.getMessage());
        this.exceptionEnums = exceptionEnums;
        errorCode = exceptionEnums.getCode();
    }

    /**
     * @param exceptionEnums 异常
     * @param cause          异常
     */
    public BaseException(final ExceptionEnums exceptionEnums, final Throwable cause) {
        super(exceptionEnums.getMessage(), cause);
        this.exceptionEnums = exceptionEnums;
        errorCode = exceptionEnums.getCode();
    }

    public BaseException() {
        super();
    }

    public BaseException(String message) {
        super(message);
    }

    public BaseException(String message, Throwable cause) {
        super(message, cause);
    }

    public BaseException(String message, int errorCode) {
        super(message);
        this.errorCode = errorCode;
    }

    public BaseException(String message, int errorCode, Throwable cause) {
        super(message, cause);
        this.errorCode = errorCode;
    }

    public ExceptionEnums getExceptionEnums() {
        return exceptionEnums;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }
}
