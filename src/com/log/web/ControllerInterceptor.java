package com.log.web;

import java.net.InetAddress;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.log.factory.LogUtilsFactory;
import com.log.helper.LogHelper;
import com.log.utils.Constants;
import com.log.utils.LogUtils;

public class ControllerInterceptor {

    private static final LogUtils LOGGER = LogUtilsFactory.getLogger(ControllerInterceptor.class);

    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        LogHelper.startLogParamThread();
        Map<String, String> parameters = new HashMap<String, String>();
        parameters.put(Constants.LOGIN_ID_KEY, getLoginId());
        
        String orderId = request.getParameter(Constants.ORDER_ID_KEY);
        parameters.put(Constants.ORDER_ID_KEY, orderId);
        
        String viewName = request.getParameter(Constants.VIEW_NAME_KEY);
        parameters.put(Constants.VIEW_NAME_KEY, viewName);
        
        String productCode = request.getParameter(Constants.PRODUCT_CODE_KEY);
        parameters.put(Constants.PRODUCT_CODE_KEY, productCode);
        
        String serverName = InetAddress.getLocalHost().getHostName();
        parameters.put(Constants.SERVER_NAME_KEY, serverName);
        
        String url = request.getServletPath();
        parameters.put(Constants.URL_KEY, url);
        
        LogHelper.addParameters(parameters);

        
        
        
        
        //Use the log
        LOGGER.debug("Debug example: ");
        LOGGER.trace("Trace removed: ");
        LOGGER.error("Error example: ");
        
        // if returned false, we need to make sure 'response' is sent
        return true;
    }

    private String getLoginId() {
        return null;
    }
}
