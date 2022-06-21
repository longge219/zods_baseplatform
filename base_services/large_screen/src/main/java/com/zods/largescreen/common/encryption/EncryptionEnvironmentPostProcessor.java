package com.zods.largescreen.common.encryption;
import com.zods.largescreen.common.utils.JasyptUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.env.EnvironmentPostProcessor;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.PropertiesPropertySource;
import java.util.Properties;
/**
 * @author jianglong
 * @version 1.0
 * @Description
 * @createDate 2022-06-20
 */
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
