package com.zods.largescreen.common.annotation.condition;
import org.springframework.boot.autoconfigure.condition.ConditionMessage;
import org.springframework.boot.autoconfigure.condition.ConditionOutcome;
import org.springframework.boot.autoconfigure.condition.SpringBootCondition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.env.Environment;
import org.springframework.core.type.AnnotatedTypeMetadata;
import java.util.Map;
/**
 * @author jianglong
 * @version 1.0
 * @Description
 * @createDate 2022-06-20
 */
public class OnGaeaComponentCondition extends SpringBootCondition {
    private String prefix = "spring.gaea.subscribes.";

    public OnGaeaComponentCondition() {
    }

    public ConditionOutcome getMatchOutcome(ConditionContext context, AnnotatedTypeMetadata metadata) {
        Map<String, Object> annotationAttributes = metadata.getAnnotationAttributes(ConditionalOnGaeaComponent.class.getName());
        String componentName = String.valueOf(annotationAttributes.get("value"));
        ConditionOutcome endpointOutcome = this.getEndpointOutcome(context, componentName);
        return endpointOutcome != null ? endpointOutcome : ConditionOutcome.noMatch(ConditionMessage.of("gaea，not load ", new Object[0]));
    }

    protected ConditionOutcome getEndpointOutcome(ConditionContext context, String componentName) {
        Environment environment = context.getEnvironment();
        String enabledProperty = this.prefix + componentName + ".enabled";
        if (environment.containsProperty(enabledProperty)) {
            boolean match = (Boolean)environment.getProperty(enabledProperty, Boolean.class, true);
            return new ConditionOutcome(match, ConditionMessage.forCondition(ConditionalOnGaeaComponent.class, new Object[0]).because(this.prefix + componentName + ".enabled is " + match));
        } else {
            return null;
        }
    }
}
