package com.rabbitframework.web;

import com.rabbitframework.commons.utils.JsonUtils;
import com.rabbitframework.commons.utils.StringUtils;
import com.rabbitframework.web.resources.RabbitContextResource;
import com.rabbitframework.web.utils.ResponseUtils;
import com.rabbitframework.web.utils.ServletContextHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.Response;

/**
 * rest抽象接口类
 *
 * @author: justin.liang
 */
public abstract class AbstractContextResource
        extends RabbitContextResource {
    private static final Logger logger = LoggerFactory.getLogger(AbstractContextResource.class);

    public String getMessage(String messageKey) {
        return ServletContextHelper.getMessage(messageKey);
    }

    public String getMessage(String messageKey, Object... args) {
        return ServletContextHelper.getMessage(messageKey, args);
    }

    public String getMessage(HttpServletRequest request, String messageKey) {
        return ServletContextHelper.getMessage(messageKey, request.getLocale());
    }

    public Response getSimpleResponse(boolean result) {
        return getSimpleResponse(result, null);
    }

    public Response getSimpleResponse(boolean result, Object data) {
        return getSimpleResponse(result, data, false);
    }

    public Response getSimpleResponse(boolean result, Object data, boolean dateNotNull) {
        DataJsonResponse dataJsonResponse = new DataJsonResponse();
        if (data != null) {
            dataJsonResponse.setData(data);
        }
        dataJsonResponse.setStatus(DataJsonResponse.FAIL);
        dataJsonResponse.setMessage(getMessage("fail"));
        if (result) {
            dataJsonResponse.setStatus(DataJsonResponse.SC_OK);
            dataJsonResponse.setMessage(getMessage("success"));
        }
        String dataJson = dataJsonResponse.toJson();
        if (dateNotNull) {
            dataJson = dataJsonResponse.toJsonNoNull();
        }
        logger.debug(getClass().getName() + "=>" + dataJson);
        return ResponseUtils.ok(dataJson);
    }


    public Response getSimpleResponse(boolean result, String key, Object data) {
        DataJsonResponse dataJsonResponse = new DataJsonResponse();
        if (result) {
            dataJsonResponse.setStatus(DataJsonResponse.SC_OK);
            dataJsonResponse.setMessage(getMessage("success"));
            if (data != null) {
                dataJsonResponse.setData(key, data);
            }
            String dataJson = dataJsonResponse.toJsonNoNull();
            logger.debug(getClass().getName() + "=>" + dataJson);
            return ResponseUtils.ok(dataJson);
        }
        dataJsonResponse.setStatus(DataJsonResponse.FAIL);
        dataJsonResponse.setMessage(getMessage("fail"));
        String dataJson = dataJsonResponse.toJsonNoNull();
        logger.debug(getClass().getName() + "=>" + dataJson);
        return ResponseUtils.ok(dataJson);

    }

    /**
     * 根据参数返回结果集
     *
     * @param result：是否成功s
     * @param data:返回数据
     * @param dateNotNull  数据是否去掉空值
     * @return
     */
    public Response getResponse(boolean result, Object data, boolean dateNotNull) {
        DataJsonResponse dataJsonResponse = new DataJsonResponse();
        dataJsonResponse.setStatus(DataJsonResponse.FAIL);
        dataJsonResponse.setMessage(getMessage("fail"));
        if (result) {
            dataJsonResponse.setStatus(DataJsonResponse.SC_OK);
            dataJsonResponse.setMessage(getMessage("success"));
        }
        if (null != data) {
            dataJsonResponse.setData(data);
        }
        String dataJson = dataJsonResponse.toJson();
        if (dateNotNull) {
            dataJson = dataJsonResponse.toJsonNoNull();
        }
        logger.debug(getClass().getName() + "-getResponse() =>" + dataJson);
        return ResponseUtils.ok(dataJson);
    }

    public Response getResponse(boolean status, String message, Object data) {
        int statusInt = status ? DataJsonResponse.SC_OK : DataJsonResponse.FAIL;
        return getResponse(statusInt, message, data);
    }

    public Response getResponse(int status, String message, Object data) {
        return getResponse(status, message, data, false);
    }

    /**
     * 获取返回信息
     *
     * @param status      返回状态
     * @param message     返回消息
     * @param data        接收数据
     * @param dataNotNull 是否去掉为空数据
     * @return
     */
    public Response getResponse(int status, String message, Object data, boolean dataNotNull) {
        DataJsonResponse dataJsonResponse = new DataJsonResponse();
        if (data != null) {
            dataJsonResponse.setData(data);
        }
        dataJsonResponse.setStatus(status);
        dataJsonResponse.setMessage(message);
        String value = dataJsonResponse.toJson();
        if (dataNotNull) {
            value = dataJsonResponse.toJsonNoNull();
        }
        logger.debug(getClass().getName() + "=>" + value);
        return ResponseUtils.ok(value);
    }

    /**
     * 1:app,2:PC,3:wx
     *
     * @param request
     * @return
     */
    public Integer getOperatorSource(HttpServletRequest request) {
        RequestHeaderInfo requestHeaderInfo = getHeaderInfo(request);
        //如果为空默认返回
        if (null == requestHeaderInfo) {
            return 2; //PC端
        }
        return requestHeaderInfo.getOperationSource();
    }

    public RequestHeaderInfo getHeaderInfo(HttpServletRequest request) {
        String requestHeaderInfoJson = request.getHeader("twdrpUserAgent");
        if (StringUtils.isBlank(requestHeaderInfoJson)) {
            return null;
        }
        RequestHeaderInfo requestHeaderInfo = JsonUtils.getObject(requestHeaderInfoJson, RequestHeaderInfo.class);
        return requestHeaderInfo;
    }

    /**
     * 参数验证结果
     *
     * @param result
     * @param messageKey
     * @return
     */
    public Response getArgumentResponse(boolean result, String messageKey) {
        DataJsonResponse dataJsonResponse = new DataJsonResponse();
        if (result) {
            dataJsonResponse.setStatus(DataJsonResponse.SC_OK);
            dataJsonResponse.setMessage(getMessage("success"));
            return ResponseUtils.ok(dataJsonResponse.toJsonNoNull());
        }
        dataJsonResponse.setStatus(DataJsonResponse.SC_VALID_ERROR);
        dataJsonResponse.setMessage(getMessage(messageKey));
        return ResponseUtils.ok(dataJsonResponse.toJsonNoNull());
    }

}
