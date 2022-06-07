package com.zods.drool.core;

import lombok.extern.slf4j.Slf4j;

/**
 * @author Wangchao
 * @version 1.0
 * @Description
 * @createDate 2021/5/17 15:03
 */
@Slf4j
public class RuleCache implements Runnable {

    private KieTemplate kieTemplate;

    public RuleCache(KieTemplate kieTemplate) {
        this.kieTemplate = kieTemplate;
    }

    @Override
    public void run() {
        log.debug("===>>开始更新规则文件");
        kieTemplate.doRead0();
    }
}
