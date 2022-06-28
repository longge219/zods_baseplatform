package com.zods.plugins.db.annotation.enabled;
import com.zods.plugins.db.annotation.condition.ConditionalOnComponent;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.ImportSelector;
import org.springframework.core.env.Environment;
import org.springframework.core.io.support.SpringFactoriesLoader;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.util.CollectionUtils;
import java.util.List;
import java.util.stream.Collectors;
/**
 * @author jianglong
 * @version 1.0
 * @Description
 * @createDate 2022-06-20
 */
@Slf4j
public class ConfigurationImportSelector implements ImportSelector, EnvironmentAware {

    private Environment environment;

    public ConfigurationImportSelector() {
    }

    public void setEnvironment(Environment environment) {
        this.environment = environment;
    }

    public String[] selectImports(AnnotationMetadata importingClassMetadata) {
        List<String> list = SpringFactoriesLoader.loadFactoryNames(EnabledConfiguration.class, ConfigurationImportSelector.class.getClassLoader());
        if (CollectionUtils.isEmpty(list)) {
            return new String[0];
        } else {
            List<String> importAutoConfigurations = (List)list.stream().filter((className) -> {
                try {
                    Class<?> gaeaExtensionClass = Class.forName(className);
                    return gaeaExtensionClass.isAnnotationPresent(ConditionalOnComponent.class);
                } catch (ClassNotFoundException var2) {
                    return false;
                }
            }).collect(Collectors.toList());
            log.info("装载的组件：{}", importAutoConfigurations);
            return (String[])importAutoConfigurations.toArray(new String[0]);
        }
    }
}
