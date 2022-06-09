package com.zods.auth.common.wrapper;

import com.alibaba.ttl.TransmittableThreadLocal;

import javax.servlet.ReadListener;
import javax.servlet.ServletInputStream;
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.io.*;
import java.nio.charset.StandardCharsets;

/**
 * @author Wangchao
 * @version 1.0
 * @Description 是否需要解析Body
 * @createDate 2020/12/28 09:36
 */
public class HttpAuthRequestWrapper extends HttpServletRequestWrapper {

    public static final ThreadLocal<String> transmittableThreadLocal = new TransmittableThreadLocal<>();

    /**
     * 请求体
     */
    private String mBody;

    /**
     * 是否需要解析Body
     */
    private boolean parseBody;

    /**
     * 是否是单点登录
     */
    private boolean isSSO;

    /**
     * 是否为失败的请求
     */
    private boolean isFailedRequest = false;

    /**
     * Constructs a request object wrapping the given request.
     *
     * @param request The request to wrap
     * @throws IllegalArgumentException if the request is null
     */
    public HttpAuthRequestWrapper(HttpServletRequest request, boolean parseBody) {
        super(request);
        this.parseBody = parseBody;
        // 将body数据存储起来
        if (parseBody) {
            mBody = getBody(request);
            transmittableThreadLocal.set(mBody);
        }
    }

    /**
     * Constructs a request object wrapping the given request.
     *
     * @param request The request to wrap
     * @throws IllegalArgumentException if the request is null
     */
    public HttpAuthRequestWrapper(HttpServletRequest request, boolean parseBody, boolean isSSO) {
        super(request);
        this.parseBody = parseBody;
        this.isSSO = isSSO;
        // 将body数据存储起来
        if (parseBody) {
            mBody = getBody(request);
            transmittableThreadLocal.set(mBody);
        }
    }

    /**
     * 获取请求体
     *
     * @param request 请求
     * @return 请求体
     */
    private String getBody(HttpServletRequest request) {
        return getBodyString(request);
    }

    @Override
    public BufferedReader getReader() throws IOException {
        if (!parseBody) {
            return super.getReader();
        }
        return new BufferedReader(new InputStreamReader(getInputStream()));
    }

    /**
     * 将取出来的字符串，再次转换成流，
     * 然后把它放入到新request对象中
     *
     * @return
     * @throws IOException
     */
    @Override
    public ServletInputStream getInputStream() throws IOException {
        if (!parseBody) {
            return super.getInputStream();
        }
        // 创建字节数组输入流
        final ByteArrayInputStream bais = new ByteArrayInputStream(mBody.getBytes(StandardCharsets.UTF_8));

        return new ServletInputStream() {
            @Override
            public boolean isFinished() {
                return false;
            }

            @Override
            public boolean isReady() {
                return false;
            }

            @Override
            public void setReadListener(ReadListener readListener) {

            }

            @Override
            public int read() throws IOException {
                return bais.read();
            }
        };
    }

    /**
     * 获取请求Body
     *
     * @param request
     * @return
     */
    private String getBodyString(ServletRequest request) {
        StringBuilder sb = new StringBuilder();
        InputStream inputStream = null;
        BufferedReader reader = null;
        try {
            inputStream = request.getInputStream();
            reader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8));
            String line = "";
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return sb.toString();
    }


    /**
     * 获取请求体
     *
     * @return 请求体
     */
    public String getBody() {
        return mBody;
    }


    /**
     * 设置body消息
     *
     * @param body
     */
    public void setBody(String body) {
        this.mBody = body;
    }

    /**
     * 获取当前请求结果
     *
     * @return
     */
    public boolean isFailedRequest() {
        return isFailedRequest;
    }

    /**
     * 设置当前请求结果
     *
     * @param failedRequest
     */
    public void setFailedRequest(boolean failedRequest) {
        isFailedRequest = failedRequest;
    }

    public boolean isSSO() {
        return isSSO;
    }

    public void setSSO(boolean SSO) {
        isSSO = SSO;
    }
}
