package com.zods.largescreen.common.i18;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;

public class MessageSourceHolder {
    @Autowired
    private MessageSource messageSource;

    public MessageSourceHolder() {
    }

    public String getMessage(String code) {
        return this.messageSource.getMessage(code, (Object[])null, LocaleContextHolder.getLocale());
    }

    public String getMessage(String code, Object[] args) {
        return this.messageSource.getMessage(code, args, LocaleContextHolder.getLocale());
    }
}
