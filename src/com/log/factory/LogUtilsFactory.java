package com.log.factory;

import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import com.log.utils.LogUtils;

public class LogUtilsFactory implements ApplicationContextAware {

    private static ApplicationContext applicationContext;

    public void setApplicationContext(ApplicationContext applicationContext) {
        LogUtilsFactory.applicationContext = applicationContext;
    }

    public static <T> LogUtils getLogger(Class<T> clazz) {
        // applicationContext is null when the context hasn't finished to load.
        if (applicationContext != null) {
            return applicationContext.getBean(LogUtils.class, clazz);
        } else {
            return LogUtils.getLogger(clazz);
        }
    }

}
