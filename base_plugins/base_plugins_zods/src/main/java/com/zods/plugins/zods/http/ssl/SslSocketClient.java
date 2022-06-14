package com.zods.plugins.zods.http.ssl;


import java.security.SecureRandom;
import java.security.cert.X509Certificate;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.KeyManager;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

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
