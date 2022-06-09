package zom.zods.exception.util;
import zom.zods.exception.enums.HandlerExceptionEnums;
import zom.zods.exception.model.ResponseModel;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @author Wangchao
 * @version 1.0
 * @Description 请求响应工具类
 * @createDate 2021/1/14 16:38
 */
public class ResponseUtil {
    /**
     * 响应json格式字符串
     *
     * @param request  {HttpServletRequest}
     * @param response {HttpServletResponse}
     * @param model    {ResponseModel}
     */
    public static void responseJson(HttpServletRequest request, HttpServletResponse response, ResponseModel model) {
        response.setCharacterEncoding("utf-8");
        response.setContentType("application/json; charset=utf-8");
        try (PrintWriter out = response.getWriter()) {
            out.append(FastJsonUtil.toJSONString(model));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 响应json格式字符串
     *
     * @param response
     * @param model
     */
    public static void responseJson(HttpServletResponse response, ResponseModel model) {
        response.setCharacterEncoding("utf-8");
        response.setContentType("application/json; charset=utf-8");
        try (PrintWriter out = response.getWriter()) {
            out.append(FastJsonUtil.toJSONString(model));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static ResponseModel success() {
        return new ResponseModel(HandlerExceptionEnums.SUCCESS);
    }

    public static ResponseModel success(Object data) {
        return new ResponseModel(data);
    }

    public static ResponseModel fail() {
        return new ResponseModel(HandlerExceptionEnums.BUSSINESS_EXCETION);
    }

    public static ResponseModel fail(int code, String msg) {
        return new ResponseModel(code, msg, (Object) null);
    }


}
