package com.zods.largescreen.common.holder;
import org.springframework.util.Assert;
import java.util.Locale;
/**
 * @author jianglong
 * @version 1.0
 * @Description
 * @createDate 2022-06-20
 */
public class UserContentHolder {

    private static final ThreadLocal<UserContext> userContextThreadLocal = new InheritableThreadLocal();

    public UserContentHolder() {
    }

    public static void clearContext() {
        userContextThreadLocal.remove();
    }

    public static UserContext getContext() {
        UserContext ctx = (UserContext)userContextThreadLocal.get();
        if (ctx == null) {
            ctx = createEmptyContext();
            userContextThreadLocal.set(ctx);
        }

        return ctx;
    }

    public static void setContext(UserContext context) {
        Assert.notNull(context, "Only non-null SecurityContext instances are permitted");
        userContextThreadLocal.set(context);
    }

    public static String getUsername() {
        return getContext().getUsername();
    }

    public static Integer getUserType() {
        return getContext().getType();
    }

    public static String getOrgCode() {
        return getContext().getOrgCode();
    }

    public static String getTenantCode() {
        return getContext().getTenantCode();
    }

    public static String getUuid() {
        return getContext().getUuid();
    }

    public static UserContext createEmptyContext() {
        return new UserContext();
    }

    public static Object getParam(String key) {
        return getContext().getParams().get(key);
    }

    public static void putParam(String key, Object value) {
        getContext().getParams().put(key, value);
    }

    public static Locale getLocale() {
        return getContext().getLocale();
    }
}
