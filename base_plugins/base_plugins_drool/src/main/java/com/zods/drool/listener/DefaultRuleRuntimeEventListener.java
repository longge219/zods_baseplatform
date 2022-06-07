package com.zods.drool.listener;
import lombok.extern.slf4j.Slf4j;
import org.kie.api.event.rule.ObjectDeletedEvent;
import org.kie.api.event.rule.ObjectInsertedEvent;
import org.kie.api.event.rule.ObjectUpdatedEvent;
import org.kie.api.event.rule.RuleRuntimeEventListener;
/**
 * @author Wangchao
 * @version 1.0
 * @Description
 * @createDate 2021/5/17 15:03
 */
@Slf4j
public class DefaultRuleRuntimeEventListener implements RuleRuntimeEventListener {

    @Override
    public void objectInserted(ObjectInsertedEvent event) {
        log.debug("===>>插入对象：{}；操作规则：{}", event.getFactHandle(), event.getRule());
    }

    @Override
    public void objectUpdated(ObjectUpdatedEvent event) {
        log.debug("===>>更新对象：{}；操作规则：{}", event.getFactHandle(), event.getRule());
    }

    @Override
    public void objectDeleted(ObjectDeletedEvent event) {
        log.debug("===>>删除对象：{}；操作规则：{}", event.getFactHandle(), event.getRule());
    }
}
