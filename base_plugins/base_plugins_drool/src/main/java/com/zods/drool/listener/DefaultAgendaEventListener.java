package com.zods.drool.listener;

import lombok.extern.slf4j.Slf4j;
import org.kie.api.event.rule.*;

/**
 * @author Wangchao
 * @version 1.0
 * @Description
 * @createDate 2021/5/17 15:03
 */
@Slf4j
public class DefaultAgendaEventListener implements AgendaEventListener {

    @Override
    public void matchCreated(MatchCreatedEvent event) {
        log.debug("===>>匹配的规则：{}", event.getMatch().getRule());
    }

    @Override
    public void matchCancelled(MatchCancelledEvent event) {

    }

    @Override
    public void beforeMatchFired(BeforeMatchFiredEvent event) {
        log.debug("===>>开始执行Java代码块，匹配规则：{}，评估对象：{}",
                event.getMatch().getRule(), event.getMatch().getFactHandles());
    }

    @Override
    public void afterMatchFired(AfterMatchFiredEvent event) {
        log.debug("===>>结束执行Java代码块，匹配规则：{}，评估对象：{}",
                event.getMatch().getRule(), event.getMatch().getFactHandles());
    }

    @Override
    public void agendaGroupPopped(AgendaGroupPoppedEvent event) {

    }

    @Override
    public void agendaGroupPushed(AgendaGroupPushedEvent event) {

    }

    @Override
    public void beforeRuleFlowGroupActivated(RuleFlowGroupActivatedEvent event) {

    }

    @Override
    public void afterRuleFlowGroupActivated(RuleFlowGroupActivatedEvent event) {

    }

    @Override
    public void beforeRuleFlowGroupDeactivated(RuleFlowGroupDeactivatedEvent event) {

    }

    @Override
    public void afterRuleFlowGroupDeactivated(RuleFlowGroupDeactivatedEvent event) {

    }
}
