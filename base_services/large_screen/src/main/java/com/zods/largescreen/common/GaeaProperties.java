package com.zods.largescreen.common;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import java.util.List;
/**
 * @author jianglong
 * @version 1.0
 * @Description
 * @createDate 2022-06-20
 */
@ConfigurationProperties(prefix = "spring.gaea")
@RefreshScope
public class GaeaProperties {
    private String secret = "anji-plus";
    private Security security = new Security();
    private String subscribes;
    private List<String> requestInfoServiceIds;

    public GaeaProperties() {
    }

    public String getSecret() {
        return this.secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }

    public String getSubscribes() {
        return this.subscribes;
    }

    public void setSubscribes(String subscribes) {
        this.subscribes = subscribes;
    }

    public List<String> getRequestInfoServiceIds() {
        return this.requestInfoServiceIds;
    }

    public void setRequestInfoServiceIds(List<String> requestInfoServiceIds) {
        this.requestInfoServiceIds = requestInfoServiceIds;
    }

    public Security getSecurity() {
        return this.security;
    }

    public void setSecurity(Security security) {
        this.security = security;
    }

    public class Security {
        private String jwtSecret = "anji_plus_gaea_p@ss1234";
        private Long jwtTokenTimeout = 120L;
        private boolean scanAnnotation = false;
        private List<String> whileList;
        private boolean permitAll = false;

        public Security() {
        }

        public boolean isPermitAll() {
            return this.permitAll;
        }

        public boolean isScanAnnotation() {
            return this.scanAnnotation;
        }

        public void setScanAnnotation(boolean scanAnnotation) {
            this.scanAnnotation = scanAnnotation;
        }

        public void setPermitAll(boolean permitAll) {
            this.permitAll = permitAll;
        }

        public List<String> getWhileList() {
            return this.whileList;
        }

        public void setWhileList(List<String> whileList) {
            this.whileList = whileList;
        }

        public String getJwtSecret() {
            return this.jwtSecret;
        }

        public void setJwtSecret(String jwtSecret) {
            this.jwtSecret = jwtSecret;
        }

        public Long getJwtTokenTimeout() {
            return this.jwtTokenTimeout;
        }

        public void setJwtTokenTimeout(Long jwtTokenTimeout) {
            this.jwtTokenTimeout = jwtTokenTimeout;
        }
    }
}
