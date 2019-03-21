package com.sandu.common.context;

/**
 * creator by bvvy
 * systemcontext info
 */
public class SystemContextHolder {

    private static ThreadLocal<SystemContext> systemContextThreadLocal = new ThreadLocal<>();

    public static SystemContext getSystemContext() {
        SystemContext systemContext = systemContextThreadLocal.get();
        if (systemContext == null) {
            systemContext = newSystemContext();
        }
        return systemContext;
    }

    public static void setSystemContext(SystemContext systemContext) {
        systemContextThreadLocal.set(systemContext);

    }
    public static void remove() {
        systemContextThreadLocal.remove();
    }

    private static SystemContext newSystemContext() {
        return new SystemContext();
    }

}
