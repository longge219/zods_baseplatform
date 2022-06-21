package com.zods.largescreen.common.i18;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.servlet.LocaleResolver;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Locale;
/**
 * @author jianglong
 * @version 1.0
 * @Description
 * @createDate 2022-06-20
 */
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
