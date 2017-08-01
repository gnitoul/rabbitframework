package com.rabbitframework.web.utils;

import com.rabbitframework.web.DataJsonResponse;
import org.apache.commons.collections.CollectionUtils;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.groups.Default;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;


/**
 * 校验工具类
 *
 * @author wdmcygah
 */
public class ValidationUtils {

    private static Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

    public static <T> DataJsonResponse validateEntity(T obj) {
        DataJsonResponse result = null;
        Set<ConstraintViolation<T>> set = validator.validate(obj, Default.class);
        if (CollectionUtils.isEmpty(set)) {
            return result;
        }
        result = new DataJsonResponse();
        result.setStatus(DataJsonResponse.SC_VALID_ERROR);
        result.setMessage(ServletContextHelper.getMessage("fail"));
        List<FieldError> fieldErrors = getFiledErrors(set);
        result.setData(fieldErrors);
        return result;
    }

    private static <T> List<FieldError> getFiledErrors(Set<ConstraintViolation<T>> set) {
        List<FieldError> fieldErrors = new ArrayList<FieldError>();
        for (ConstraintViolation<T> cv : set) {
            FieldError fieldError = new FieldError();
            String fieldName = cv.getPropertyPath().toString();
            String message = cv.getMessage();
            if (message.indexOf("{") != -1 && message.lastIndexOf("}") != -1) {
                message = message.substring(message.indexOf("{") + 1, message.lastIndexOf("}"));
            }

            fieldError.setFieldName(fieldName);
            fieldError.setErrorMessage(ServletContextHelper.getMessage(message));
            fieldErrors.add(fieldError);
        }
        return fieldErrors;
    }

    public static <T> DataJsonResponse validateProperty(T obj, String propertyName) {
        DataJsonResponse result = new DataJsonResponse();
        Set<ConstraintViolation<T>> set = validator.validateProperty(obj, propertyName, Default.class);
        if (CollectionUtils.isNotEmpty(set)) {
            result.setStatus(DataJsonResponse.SC_VALID_ERROR);
            StringBuffer errorMsg = new StringBuffer();
            for (ConstraintViolation<T> cv : set) {
                errorMsg.append("arg:");
                errorMsg.append(cv.getPropertyPath().toString());
                errorMsg.append("error:");
                errorMsg.append(cv.getMessage());
            }
            result.setMessage(errorMsg.toString());
        }
        return result;
    }

}
