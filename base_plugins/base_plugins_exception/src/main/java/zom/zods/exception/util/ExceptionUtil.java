package zom.zods.exception.util;
import zom.zods.exception.enums.HandlerExceptionEnums;
import zom.zods.exception.exception.category.McloudHandlerException;
/**
 * @author Wangchao
 * @version 1.0
 * @Description
 * @createDate 2020/12/29 09:20
 */
public class ExceptionUtil {

    /**
     * 获取嵌套异常中的最终信息
     *
     * @param e
     * @return
     */
    public static String getRealMessage(Throwable e) {
        // 如果e不为空，则去掉外层的异常包装
        while (e != null) {
            Throwable cause = e.getCause();
            if (cause == null) {
                return e.getMessage();
            }
            e = cause;
        }
        return "";
    }

    /**
     * SQL结果异常处理
     *
     * @param msg
     * @param result
     */
    public static void throwSqlException(String msg, int result) {
        if (result != 1) {
            throw new McloudHandlerException(msg, HandlerExceptionEnums.SQL_ERROR.code);
        }
    }

    /**
     * 非空异常处理
     *
     * @param msg
     * @param o
     */
    public static void throwNotBlankException(String msg, Object o) {
        if (null != o) {
            throw new McloudHandlerException(msg, HandlerExceptionEnums.BUSSINESS_EXCETION.code);
        }
    }

    /**
     * 空异常处理
     *
     * @param msg
     * @param o
     */
    public static void throwBlankException(String msg, Object o) {
        if (null == o) {
            throw new McloudHandlerException(msg, HandlerExceptionEnums.BUSSINESS_EXCETION.code);
        }
    }

    /**
     * 空异常处理
     *
     * @param msg
     */
    public static void throwParamException(String msg) {
        throw new McloudHandlerException(msg, HandlerExceptionEnums.BAD_REQUEST.code);
    }

    public static void throwExceptionLog(String msg, Object o) {
//        LogModelForKafka<SystemLogModel> logModelForKafka = new LogModelForKafka();
//        LogEsModel logEsModel = new LogEsModel();
//        logEsModel.setApp_name(event.getLoggerName());
//        logEsModel.setLog_level(event.getLevel().levelStr);
//        logEsModel.setMessage(event.getMessage());
//        SystemLogModel sysLog = new SystemLogModel();
//        sysLog.setThreadName(event.getThreadName());
//        sysLog.setLogLevel(event.getLevel().levelStr);
//        sysLog.setMethodName(event.getLoggerName());
//        Thread thread = Thread.currentThread();
//        sysLog.setLineNumber(Integer.parseInt(convert(event)));
//        sysLog.setThreadId(String.valueOf(thread.getId()));
//        sysLog.setName(event.getLoggerName());
//        sysLog.setCreateOn(System.currentTimeMillis());
//        logEsModel.setCreate_time(sysLog.getCreateOn());
//        sysLog.setMsg(event.getMessage());
//        sysLog.setServiceName("platform-http-server");
//        sysLog.setContext(event.getFormattedMessage());
//        if (event.getThrowableProxy() == null) {
//            sysLog.setMsg(event.getFormattedMessage());
//        } else {
//            ExtendedThrowableProxyConverter throwableConverter = new ExtendedThrowableProxyConverter();
//            throwableConverter.start();
//            sysLog.setMsg(event.getFormattedMessage() + throwableConverter.convert(event));
//            throwableConverter.stop();
//        }
//        logModelForKafka.setLogInfo(sysLog);
//        logEsModel.setLog_type(LogType.SYSTEM_LOG.getValue());
//        logModelForKafka.setLogEsModel(logEsModel);
//        KafkaProducerService kafkaProducerService = SpringContextUtil.getBean(KafkaProducerService.class);
//        kafkaProducerService.sendMessage(TopicConst.LOG, logModelForKafka);
    }
}