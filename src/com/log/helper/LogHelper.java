package com.log.helper;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;

import com.log.utils.Constants;

public class LogHelper {

    private static final ThreadLocal<Map<String, String>> LOG_PARAMETERS = new ThreadLocal<Map<String, String>>();
    private static final String[] logParameters = { Constants.LOGIN_ID_KEY, Constants.VIEW_NAME_KEY, Constants.ORDER_ID_KEY, Constants.PRODUCT_CODE_KEY };

    public static void addParameters(Map<String, String> parameters) {

        Set<String> paramsKey = parameters.keySet();
        for (String key : paramsKey) {
            addParameter(key, parameters.get(key));
        }

    }

    public static void addParameter(String key, String value) {

        if (StringUtils.isNotEmpty(value)) {
            Map<String, String> parameters = LOG_PARAMETERS.get();
            if (parameters == null) {
                parameters = new HashMap<String, String>();
            }
            parameters.put(key, value);
            LOG_PARAMETERS.set(parameters);
        }

    }

    public static String getParametersConcatenated() {
        String params = "";
        Map<String, String> parameters = LOG_PARAMETERS.get();
        if (parameters != null && !parameters.isEmpty()) {
            for (String key : logParameters) {
                if (StringUtils.isNotEmpty(parameters.get(key)))
                    params += "[" + key + "=" + parameters.get(key) + "] ";
            }
        }
        return params.trim();
    }

    public static String getParameter(String keyParam) {
        String paramValue = null;
        Map<String, String> parameters = LOG_PARAMETERS.get();
        if (parameters != null && !parameters.isEmpty()) {
            paramValue = parameters.get(keyParam);
        }
        return paramValue;
    }

    /**
     * This method should always be called to initialize the parameters map in the thread.
     */
    public static void startLogParamThread() {
        LOG_PARAMETERS.set(new HashMap<String, String>());
    }

    /**
     * Is necessary to called this method to clean up the ThreadLocals, memory leak avoidance.
     */
    public static void endLogParamThread() {
        LOG_PARAMETERS.set(null);
        LOG_PARAMETERS.remove();
    }
}
