package com.rabbitframework.web.exceptions;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;

/**
 * 统一异常处理
 *
 * @author justin.liang
 */
//@Provider
public class ExceptionMapperSupport implements ExceptionMapper<Exception> {
    private static final Logger logger = LoggerFactory.getLogger(ExceptionMapperSupport.class);
    @Context
    private HttpServletRequest request;

    @Override
    public Response toResponse(Exception e) {
        logger.error(e.getMessage(), e);
        return null;
//        int httpStatus = HttpServletResponse.SC_OK;
//        DataJsonResponse dataJsonResponse = new DataJsonResponse();
//        Exception currException = e;
//        if (e instanceof WebApplicationException) {
//            WebApplicationException webException = ((WebApplicationException) e);
//            Response response = webException.getResponse();
//            int status = response.getStatus();
//            String message = webException.getMessage();
//            PrintWriter printWriter = null;
//            try {
//                Writer writer = new StringWriter();
//                printWriter = new PrintWriter(writer);
//                webException.printStackTrace(printWriter);
//                message = writer.toString();
//            } finally {
//                IOUtils.closeQuietly(printWriter);
//            }
//            dataJsonResponse.setStatus(FAIL);
//            dataJsonResponse.setMessage(message);
//            ResponseUtils.getResponse(status, JsonUtils.toJsonString(dataJsonResponse));
//        }
//
//        if (!(e instanceof TwdrpException)) {
//            currException = new UnknowException(e.getMessage(), e);
//        }
//
//        TwdrpException twdrpException = (TwdrpException) currException;
//        String message = ServletContextHelper.getMessage(twdrpException.getMessage());
//        dataJsonResponse.setMessage(message);
//        int status = twdrpException.getStatus();
//        int resultStatus = status;
//        switch (status) {
//            case FAIL:
//                resultStatus = DataJsonResponse.FAIL;
//                break;
//            case SC_VALID_ERROR:
//                resultStatus = DataJsonResponse.SC_VALID_ERROR;
//                break;
//            case SC_CACHE_ERROR:
//                resultStatus = SC_CACHE_ERROR;
//                break;
//            case SC_INTERNAL_SERVER_ERROR:
//                httpStatus = HttpServletResponse.SC_INTERNAL_SERVER_ERROR;
//                break;
//            case SC_UNAUTHORIZED:
//            case SC_PROXY_AUTHENTICATION_REQUIRED:
//                httpStatus = HttpServletResponse.SC_UNAUTHORIZED;
//                break;
//        }
//        dataJsonResponse.setStatus(resultStatus);
//        dataJsonResponse.setMessage(message);
//        return ResponseUtils.getResponse(httpStatus, dataJsonResponse.toJson());
    }
}