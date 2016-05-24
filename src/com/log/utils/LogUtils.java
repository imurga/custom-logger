package com.log.utils;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.log.async.AsyncLog;
import com.log.helper.LogHelper;

@Component
@Scope("prototype")
public class LogUtils {

    private Logger LOGGER;
    private String className;

    @Autowired
    private AsyncLog asyncLoggingTask;

    private <T> LogUtils(Class<T> clazz) {
        className = clazz.getName();
        LOGGER = Logger.getLogger(clazz);
    }

    static public <T> LogUtils getLogger(Class<T> clazz) {
        return new LogUtils(clazz);
    }

    public void fatal(Object message) {
        String logParams = LogHelper.getParametersConcatenated();
        LOGGER.fatal(getLogMessage(logParams, removeBreakLinesInMessage(message.toString())));
        persistLog("FATAL", message.toString());
    }

    public void error(Object message) {
        String logParams = LogHelper.getParametersConcatenated();
        LOGGER.error(getLogMessage(logParams, removeBreakLinesInMessage(message.toString())));
        persistLog("ERROR", message.toString());
    }

    public void error(Object message, Throwable throwable) {
        String logParams = LogHelper.getParametersConcatenated();
        StackTraceElement[] stackTraceElement = throwable.getStackTrace();
        LOGGER.error(getLogMessage(logParams, removeBreakLinesInMessage(message.toString()) + " - " + concatenateStackTrace(stackTraceElement)), throwable);
        persistLog("ERROR", message.toString());
    }

    public void warn(Object message) {
        String logParams = LogHelper.getParametersConcatenated();
        LOGGER.warn(getLogMessage(logParams, removeBreakLinesInMessage(message.toString())));
    }

    public void warn(Object message, Throwable throwable) {
        String logParams = LogHelper.getParametersConcatenated();
        StackTraceElement[] stackTraceElement = throwable.getStackTrace();
        LOGGER.warn(getLogMessage(logParams, removeBreakLinesInMessage(message.toString()) + " - " + concatenateStackTrace(stackTraceElement)), throwable);
    }

    public void info(Object message) {
        String logParams = LogHelper.getParametersConcatenated();
        LOGGER.info(getLogMessage(logParams, removeBreakLinesInMessage(message.toString())));
        persistLog("INFO", message.toString());
    }

    public void debug(Object message) {
        String logParams = LogHelper.getParametersConcatenated();
        LOGGER.debug(getLogMessage(logParams, removeBreakLinesInMessage(message.toString())));
    }

    public void trace(Object message) {
        String logParams = LogHelper.getParametersConcatenated();
        LOGGER.trace(getLogMessage(logParams, removeBreakLinesInMessage(message.toString())));
    }

    private String concatenateStackTrace(StackTraceElement[] stackTraceElement) {
        String stackTraceConcatenated = "";
        for (StackTraceElement stackTraceElement2 : stackTraceElement) {
            stackTraceConcatenated += "\t" + removeBreakLinesInMessage(stackTraceElement2.toString());
        }
        return stackTraceConcatenated;
    }

    private String removeBreakLinesInMessage(String message) {
        return message.replaceAll("\n", "").replaceAll("\r", "");
    }

    private String getLogMessage(String parameters, String message) {
        Long threadId = Thread.currentThread().getId();
        String logMessage = "[Thread=" + threadId + "]";
        if (StringUtils.isNotEmpty(parameters)) {
            logMessage += parameters + " - " + message;
        } else {
            logMessage += message;
        }
        return logMessage;
    }

    private void persistLog(String logLevel, String logTrace) {
        // asyncLoggingTask could be null when the applicationcontext hasn't finished to load.
        if (asyncLoggingTask != null && StringUtils.isNotEmpty(LogHelper.getParameter("loginId"))) {
            // LogHelper.getParameter(Constants.LOGIN_ID_KEY)
            // LogHelper.getParameter(Constants.ORDER_ID_KEY)
            // LogHelper.getParameter(Constants.VIEW_NAME_KEY)
            // LogHelper.getParameter(Constants.PRODUCT_CODE_KEY)
            // LogHelper.getParameter(CoreConstants.SERVER_NAME_KEY)
            // LogHelper.getParameter(Constants.SESSION_ID_KEY)
            // LogHelper.getParameter(Constants.URL_KEY)
            // Thread.currentThread().getId()
            // logTrace
            // logLevel
            // persist log
        }
    }

}
