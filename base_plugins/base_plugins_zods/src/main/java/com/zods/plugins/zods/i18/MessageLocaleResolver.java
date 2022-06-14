package com.zods.plugins.zods.i18;


import java.util.Locale;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.servlet.LocaleResolver;

public class MessageLocaleResolver implements LocaleResolver {
    private String localeHeader = "locale";

    public MessageLocaleResolver() {
    }

    public Locale resolveLocale(HttpServletRequest request) {
        String language = request.getHeader(this.localeHeader);
        if (StringUtils.isBlank(language)) {
            return Locale.SIMPLIFIED_CHINESE;
        } else {
            Locale locale = Locale.forLanguageTag(language);
            return locale;
        }
    }

    public void setLocale(HttpServletRequest request, HttpServletResponse response, Locale locale) {
    }

    public static void main(String[] args) {
    }
}
