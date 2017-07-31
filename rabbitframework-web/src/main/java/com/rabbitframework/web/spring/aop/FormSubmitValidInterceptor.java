package com.rabbitframework.web.spring.aop;

import com.rabbitframework.web.DataJsonResponse;
import com.rabbitframework.web.utils.ResponseUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
public class FormSubmitValidInterceptor {
    private static final Logger logger = LoggerFactory.getLogger(FormSubmitValidInterceptor.class);
    private static final String pointCupExpression = "execution(@FormValid * *(..))";

    @Pointcut(pointCupExpression)
    public void formAnnotatedMethod() {
    }

    @Around("formAnnotatedMethod()")
    public Object doInterceptor(ProceedingJoinPoint pjp) throws Throwable {
        Object[] args = pjp.getArgs();

//        if (args.length == 0) {//参数为空则不拦截
//            return pjp.proceed();
//        }
//        DataJsonResponse mustResult = mustValidate(pjp, args[0]);
//        //必填参数为空则返回;
//        if (null != mustResult) {
//            String result = mustResult.toJson();
//            logger.warn("valid:" + result);
//            return ResponseUtils.ok(result);
//        }
//
//        DataJsonResponse hibernateValidResult = hibernateValidate(args[0]);
//        if (null != hibernateValidResult) {
//            String result = hibernateValidResult.toJson();
//            logger.warn("valid:" + result);
//            return ResponseUtils.ok(result);
//        }


        return pjp.proceed();

    }

    /**
     * 验证参数是否包含了必须的值,作为hibernate validate的补充
     *
     * @param pjp
     * @param object
     * @return
     */
    private DataJsonResponse mustValidate(ProceedingJoinPoint pjp, Object object) {
        DataJsonResponse response = null;

//        if (object == null) {
//            return response;
//        }
//
//        Method method = AopUtils.getMethod(pjp);
//        String mustParametersValue = null;
//        if (method != null && method.isAnnotationPresent(MustParameters.class)) {
//            MustParameters mustParameters = method.getAnnotation(MustParameters.class);
//            mustParametersValue = mustParameters.value();
//        }
//
//        if (StringUtils.isBlank(mustParametersValue)) {
//            return response;
//        }
//
//        Map<String, String> data = new HashMap<String, String>();
//        //获取注解中所有要求不为空的项
//        String[] mastArgs = mustParametersValue.split(",");
//        for (String string : mastArgs) {
//            Field field = ReflectionUtils.findField(object.getClass(), string);
//            if (field == null) {
//                continue;
//            }
//            String fieldName = field.getName();
//            field.setAccessible(true);
//            Object fieldVal = ReflectionUtils.getField(field, object);
//            //如果该字段为空则直接返回false
//            if (null == fieldVal) {
//                data.put(fieldName, ServletContextHelper.getMessage(fieldName + ".null"));
//            }
//        }
//
//        if (data.size() > 0) {
//            response = new DataJsonResponse();
//            response.setData(data);
//            response.setStatus(DataJsonResponse.SC_VALID_ERROR);
//            response.setMessage(ServletContextHelper.getMessage("fail"));
//        }

        return response;
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
