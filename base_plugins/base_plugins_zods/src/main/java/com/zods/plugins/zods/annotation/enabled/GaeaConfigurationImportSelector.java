package com.zods.plugins.zods.annotation.enabled;

import java.util.List;
import java.util.stream.Collectors;

import com.zods.plugins.zods.annotation.condition.ConditionalOnGaeaComponent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.ImportSelector;
import org.springframework.core.env.Environment;
import org.springframework.core.io.support.SpringFactoriesLoader;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.util.CollectionUtils;

public class GaeaConfigurationImportSelector implements ImportSelector, EnvironmentAware {
    private Logger logger = LoggerFactory.getLogger(GaeaConfigurationImportSelector.class);
    private Environment environment;

    public GaeaConfigurationImportSelector() {
    }

    public void setEnvironment(Environment environment) {
        this.environment = environment;
    }

    public String[] selectImports(AnnotationMetadata importingClassMetadata) {
        List<String> list = SpringFactoriesLoader.loadFactoryNames(EnabledGaeaConfiguration.class, GaeaConfigurationImportSelector.class.getClassLoader());
        if (CollectionUtils.isEmpty(list)) {
            return new String[0];
        } else {
            List<String> importAutoConfigurations = (List)list.stream().filter((className) -> {
                try {
                    Class<?> gaeaExtensionClass = Class.forName(className);
                    return gaeaExtensionClass.isAnnotationPresent(ConditionalOnGaeaComponent.class);
                } catch (ClassNotFoundException var2) {
                    return false;
                }
            }).collect(Collectors.toList());
            this.logger.info("盖亚装载的组件：{}", importAutoConfigurations);
            return (String[])importAutoConfigurations.toArray(new String[0]);
        }
    }
}
