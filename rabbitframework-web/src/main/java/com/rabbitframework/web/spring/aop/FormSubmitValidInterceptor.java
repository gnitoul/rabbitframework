package com.rabbitframework.web.spring.aop;

import com.rabbitframework.commons.utils.StringUtils;
import com.rabbitframework.web.DataJsonResponse;
import com.rabbitframework.web.annotations.FormValid;
import com.rabbitframework.web.utils.ResponseUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.ws.rs.BeanParam;
import javax.ws.rs.FormParam;
import javax.ws.rs.QueryParam;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

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
        Map<String, Object> paramsValue = new HashMap<String, Object>();
        Parameter[] parameters = method.getParameters();
        Object value = null;
        int parameterLength = parameters.length;
        for (int i = 0; i < parameterLength; i++) {
            Parameter parameter = parameters[i];
            Annotation[] annotations = parameter.getAnnotations();
            if (annotations.length == 0) {
                continue;
            }
            String name = getAnnotationName(parameter, annotations);
            if (StringUtils.isBlank(name)) {
                continue;
            }
            value = args[i];
            break;
        }
        
//        DataJsonResponse hibernateValidResult = hibernateValidate(args[0]);
//        if (null != hibernateValidResult) {
//            String result = hibernateValidResult.toJson();
//            return ResponseUtils.ok(result);
//        }
        return pjp.proceed();
    }

    private String getAnnotationName(Parameter parameter, Annotation[] annotations) {
        int annotationsLength = annotations.length;
        String name = null;
        for (int i = 0; i < annotationsLength; i++) {
            Annotation annotation = annotations[i];
            if (annotation instanceof BeanParam) {
                name = parameter.getType().getSimpleName().toLowerCase(Locale.ENGLISH);
                break;
            }
//            if (annotation instanceof FormParam) {
//                FormParam formParam = (FormParam) annotation;
//                name = formParam.value();
//                break;
//            }
//            if (annotation instanceof QueryParam) {
//                QueryParam formParam = (QueryParam) annotation;
//                name = formParam.value();
//                break;
//            }
        }
        return name;
    }

    /**
     * 调用工具类来验证参数信息
     *
     * @param object 待验证的参数对象
     * @return
     */
//    private DataJsonResponse hibernateValidate(Object object) {
//        return ValidationUtils.validateEntity(object);
//    }
}
