package com.rabbitframework.web.spring.aop;

import com.rabbitframework.commons.utils.StatusCode;
import com.rabbitframework.commons.utils.StringUtils;
import com.rabbitframework.web.DataJsonResponse;
import com.rabbitframework.web.annotations.FormValid;
import com.rabbitframework.web.utils.FieldError;
import com.rabbitframework.web.utils.ResponseUtils;
import com.rabbitframework.web.utils.ServletContextHelper;
import com.rabbitframework.web.utils.ValidationUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.aspectj.weaver.ast.Not;
import org.hibernate.validator.constraints.NotBlank;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.validation.constraints.Digits;
import javax.ws.rs.BeanParam;
import javax.ws.rs.FormParam;
import javax.ws.rs.QueryParam;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Aspect
public class FormSubmitValidInterceptor {
    private static final Logger logger = LoggerFactory.getLogger(FormSubmitValidInterceptor.class);
    private static final String pointCupExpression = "execution(@com.rabbitframework.web.annotations.FormValid * *(..))";

    @Pointcut(pointCupExpression)
    public void formAnnotatedMethod() {
    }

    @Around("formAnnotatedMethod()")
    public Object doInterceptor(ProceedingJoinPoint pjp) throws Throwable {
        Object[] args = pjp.getArgs();
        if (args.length == 0) {//参数为空则不拦截
            return pjp.proceed();
        }
        MethodSignature methodSignature = (MethodSignature) pjp.getSignature();
        Method method = methodSignature.getMethod();
        FormValid formValid = method.getAnnotation(FormValid.class);
        String fieldFilter = formValid.fieldFilter();
        Parameter[] parameters = method.getParameters();
        Object value = null;
        int parameterLength = parameters.length;
        Map<String, NotBlank> map = new HashMap<String, NotBlank>();
        for (int i = 0; i < parameterLength; i++) {
            Parameter parameter = parameters[i];
            Annotation[] annotations = parameter.getAnnotations();
            if (annotations.length == 0) {
                continue;
            }
            String name = getAnnotationName(parameter, annotations, map);
            if (StringUtils.isBlank(name)) {
                continue;
            }
            value = args[i];
            break;
        }
        
        List<FieldError> fieldErrors = ValidationUtils.validateEntity(value, fieldFilter);
        for (Map.Entry<String, NotBlank> entry : map.entrySet()) {
            String fieldName = entry.getKey();
            NotBlank notBlank = entry.getValue();
            FieldError fieldError = ValidationUtils.getFieldError(fieldName, notBlank.message());
            fieldErrors.add(fieldError);
        }
        if (fieldErrors.size() > 0) {
            DataJsonResponse result = new DataJsonResponse();
            result.setStatus(StatusCode.SC_VALID_ERROR);
            result.setMessage(ServletContextHelper.getMessage("fail"));
            result.setData(fieldErrors);
            return ResponseUtils.ok(result.toJson());
        }
        return pjp.proceed();
    }

    private String getAnnotationName(Parameter parameter, Annotation[] annotations, Map<String, NotBlank> map) {
        int annotationsLength = annotations.length;
        String name = null;
        NotBlank notBlank = parameter.getAnnotation(NotBlank.class);
        for (int i = 0; i < annotationsLength; i++) {
            Annotation annotation = annotations[i];
            if (annotation instanceof BeanParam) {
                name = parameter.getType().getSimpleName().toLowerCase(Locale.ENGLISH);
                break;
            }
            if (annotation instanceof FormParam) {
                FormParam formParam = (FormParam) annotation;
                if (notBlank != null) {
                    map.put(formParam.value(), notBlank);
                }
                break;
            }
            if (annotation instanceof QueryParam) {
                QueryParam queryParam = (QueryParam) annotation;
                if (notBlank != null) {
                    map.put(queryParam.value(), notBlank);
                }
                break;
            }
        }
        return name;
    }
}
