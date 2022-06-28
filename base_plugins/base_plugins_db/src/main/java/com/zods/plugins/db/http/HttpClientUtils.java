package com.zods.plugins.db.http;
import com.alibaba.fastjson.JSONObject;
import com.zods.plugins.db.http.ssl.SslSocketClient;
import okhttp3.OkHttpClient.Builder;
import okhttp3.*;
import java.io.IOException;
import java.time.Duration;
/**
 * @author jianglong
 * @version 1.0
 * @Description
 * @createDate 2022-06-20
 */
public class HttpClientUtils {
    private static final OkHttpClient httpClient = (new Builder()).sslSocketFactory(SslSocketClient.getSSLSocketFactory(), SslSocketClient.trustManager()).hostnameVerifier(SslSocketClient.getHostnameVerifier()).connectTimeout(Duration.ofSeconds(90L)).readTimeout(Duration.ofSeconds(90L)).build();
    private static final MediaType MEDIA_TYPE = MediaType.parse("application/json; charset=UTF-8");

    public HttpClientUtils() {
    }

    public static Response get(String url, Headers headers) throws IOException {
        Request request = (new Request.Builder()).url(url).headers(headers).get().build();
        return httpClient.newCall(request).execute();
    }

    public static void getAsyn(String url, Headers headers, Callback callback) {
        Request request = (new Request.Builder()).url(url).headers(headers).get().build();
        httpClient.newCall(request).enqueue(callback);
    }

    public static Response post(String url, Headers headers, Object body) throws IOException {
        RequestBody requestBody = RequestBody.create(MEDIA_TYPE, JSONObject.toJSONString(body));
        Request request = (new Request.Builder()).url(url).headers(headers).post(requestBody).build();
        return httpClient.newCall(request).execute();
    }

    public static void postAsyn(String url, Headers headers, Object body, Callback callback) {
        RequestBody requestBody = RequestBody.create(MEDIA_TYPE, JSONObject.toJSONString(body));
        Request request = (new Request.Builder()).url(url).headers(headers).post(requestBody).build();
        httpClient.newCall(request).enqueue(callback);
    }

    public static Response put(String url, Headers headers, Object body) throws IOException {
        RequestBody requestBody = RequestBody.create(MEDIA_TYPE, JSONObject.toJSONString(body));
        Request request = (new Request.Builder()).url(url).headers(headers).put(requestBody).build();
        return httpClient.newCall(request).execute();
    }

    public static void putAsyn(String url, Headers headers, Object body, Callback callback) {
        RequestBody requestBody = RequestBody.create(MEDIA_TYPE, JSONObject.toJSONString(body));
        Request request = (new Request.Builder()).url(url).headers(headers).put(requestBody).build();
        httpClient.newCall(request).enqueue(callback);
    }

    public static Response del(String url, Headers headers) throws IOException {
        Request request = (new Request.Builder()).url(url).headers(headers).delete().build();
        return httpClient.newCall(request).execute();
    }

    public static void delAsyn(String url, Headers headers, Callback callback) {
        Request request = (new Request.Builder()).url(url).headers(headers).delete().build();
        httpClient.newCall(request).enqueue(callback);
    }
}
