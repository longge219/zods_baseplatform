package com.zods.largescreen.common.http.ssl;
import javax.net.ssl.*;
import java.security.SecureRandom;
import java.security.cert.X509Certificate;
/**
 * @author jianglong
 * @version 1.0
 * @Description
 * @createDate 2022-06-20
 */
public class SslSocketClient {
    public SslSocketClient() {
    }

    public static SSLSocketFactory getSSLSocketFactory() {
        try {
            SSLContext sslContext = SSLContext.getInstance("SSL");
            sslContext.init((KeyManager[])null, getTrustManager(), new SecureRandom());
            return sslContext.getSocketFactory();
        } catch (Exception var1) {
            throw new RuntimeException(var1);
        }
    }

    public static X509TrustManager trustManager() {
        return new X509TrustManager() {
            public void checkClientTrusted(X509Certificate[] chain, String authType) {
            }

            public void checkServerTrusted(X509Certificate[] chain, String authType) {
            }

            public X509Certificate[] getAcceptedIssuers() {
                return new X509Certificate[0];
            }
        };
    }

    private static TrustManager[] getTrustManager() {
        TrustManager[] trustAllCerts = new TrustManager[]{trustManager()};
        return trustAllCerts;
    }

    public static HostnameVerifier getHostnameVerifier() {
        HostnameVerifier hostnameVerifier = (s, sslSession) -> {
            return true;
        };
        return hostnameVerifier;
    }
}
