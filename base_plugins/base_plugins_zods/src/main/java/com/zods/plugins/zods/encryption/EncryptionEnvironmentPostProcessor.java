package com.zods.plugins.zods.encryption;
import java.util.Properties;
import com.zods.plugins.zods.utils.JasyptUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.env.EnvironmentPostProcessor;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.PropertiesPropertySource;

public class EncryptionEnvironmentPostProcessor implements EnvironmentPostProcessor {
    private String username = "spring.datasource.username";
    private String password = "spring.datasource.password";

    public EncryptionEnvironmentPostProcessor() {
    }

    public void postProcessEnvironment(ConfigurableEnvironment environment, SpringApplication application) {
        String secret = "anji-plus";
        String propertyUsername = environment.getProperty(this.username);
        String propertyPassword = environment.getProperty(this.password);
        if (!StringUtils.isBlank(propertyUsername) && !StringUtils.isBlank(propertyPassword)) {
            Properties properties = new Properties();
            properties.put(this.username, JasyptUtils.decrypt(propertyUsername, secret));
            properties.put(this.password, JasyptUtils.decrypt(propertyPassword, secret));
            environment.getPropertySources().addFirst(new PropertiesPropertySource("gaeaProperties", properties));
        }
    }
}
