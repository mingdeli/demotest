package com.ldm.testplugin.plugins;

import com.baomidou.mybatisplus.core.conditions.AbstractWrapper;
import com.baomidou.mybatisplus.core.conditions.update.Update;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.ldm.testplugin.annotation.EncryptFace;
import com.ldm.testplugin.annotation.EncryptField;
import com.ldm.testplugin.annotation.EncryptTable;
import com.ldm.testplugin.util.AesUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlCommandType;
import org.apache.ibatis.plugin.*;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.stereotype.Component;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 拦截器加密数据 *
 */
@Slf4j
@Component
@Intercepts({@Signature(method = "update", type = Executor.class, args = {MappedStatement.class, Object.class})})
public class EncryptInterceptor implements Interceptor {

    /**
     * 变量占位符正则
     */
    private static final Pattern PARAM_PAIRS_RE = Pattern.compile("#\\{ew\\.paramNameValuePairs\\.(" + Constants.WRAPPER_PARAM + "\\d+)\\}");

    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        Object[] args = invocation.getArgs();
        SqlCommandType sqlCommandType = null;
        for (Object obj : args) {
            if(obj == null){
                continue;
            }
            if (obj instanceof MappedStatement) {
                MappedStatement ms = (MappedStatement) obj;
                sqlCommandType = ms.getSqlCommandType();
                continue;
            }
            boolean exist = SqlCommandType.INSERT == sqlCommandType || SqlCommandType.UPDATE == sqlCommandType;
            if (obj instanceof EncryptFace) {
                if (exist) {
                    encryptField(obj);
                }
            } else if (obj instanceof Map) {
                Map paramMap = (Map) obj;
                Object param;
                // 通过MybatisPlus自带API修改，通过mapper.xml中自定义API修改
                if (paramMap.containsKey(Constants.ENTITY) && null != (param = paramMap.get(Constants.ENTITY))) {
                    if (exist && (needToDecrypt(param.getClass()) || param instanceof EncryptFace)) {
                        encryptField(param);
                    }
                }
                // 通过UpdateWrapper、LambdaUpdateWrapper修改数据库时
                if (paramMap.containsKey(Constants.WRAPPER) && null != (param = paramMap.get(Constants.WRAPPER))) {
                    if (param instanceof Update && param instanceof AbstractWrapper) {
                        Class<?> entityClass = ((MappedStatement) args[0]).getParameterMap().getType();
                        if (needToDecrypt(entityClass)) {
                            encryptWrapper(entityClass, param);
                        }
                    }
                }
            } else {
                if (exist && needToDecrypt(obj.getClass())) {
                    encryptField(obj);
                }
            }
        }
        return invocation.proceed();
    }

    private void encryptField(Object obj) {
        if (obj == null) {
            return;
        }
        if (obj instanceof EncryptFace) {
            String[] encryptFields = ((EncryptFace) obj).getEncryptFields();
            if (encryptFields.length == 0) {
                setFieldByAnnotation(obj);
            } else {
                Class<?> aClass = obj.getClass();
                for (String fieldName : encryptFields) {
                    Field field = null;
                    try {
                        field = aClass.getDeclaredField(fieldName);
                        ReflectionUtils.makeAccessible(field);
//                        field.setAccessible(true);
                        String encryptString = AesUtil.aesEncrypt(field.get(obj).toString());
                        field.set(obj, encryptString);
                    } catch (NoSuchFieldException | IllegalAccessException e) {
                        log.debug("Encryption interceptor Failed，encrypt field: {}", field);
                    }
                }
            }
        } else {
            setFieldByAnnotation(obj);
        }
    }

    private void setFieldByAnnotation(Object obj) {
        Class<?> aClass = obj.getClass();
        Field[] declaredFields = aClass.getDeclaredFields();
        for (Field field : declaredFields) {
//            field.setAccessible(true);
            ReflectionUtils.makeAccessible(field);
            EncryptField encryptField = field.getAnnotation(EncryptField.class);
            if (!Objects.isNull(encryptField)) {
                try {
                    Optional<String> encrypt = Optional.ofNullable(field.get(obj)).map(Object::toString);
                    if(encrypt.isPresent()) {
                        String encryptString = AesUtil.aesEncrypt(encrypt.get());
                        if (!StringUtils.isEmpty(encryptString)) {
                            field.set(obj, encryptString);
                        }
                    }
                } catch (Exception e) {
                    log.debug("Encryption interceptor Failed，encrypt field: {}", field.getName());
                }
            }
        }
    }

    /**
     * 校验该实例的类是否被@EncryptedTable所注解
     */
    private boolean needToDecrypt(Class<?> objectClass) {
        EncryptTable sensitiveData = AnnotationUtils.findAnnotation(objectClass, EncryptTable.class);
        return Objects.nonNull(sensitiveData);
    }

    private void encryptWrapper(Class<?> entityClass, Object ewParam) {
        if (entityClass == null || ewParam == null) {
            return;
        }
        AbstractWrapper updateWrapper = (AbstractWrapper) ewParam;
        String sqlSet = updateWrapper.getSqlSet();
        if (StringUtils.isBlank(sqlSet)) {
            return;
        }
        String[] elArr = sqlSet.split(",");
        Map<String, String> propMap = new HashMap<>(elArr.length);
        Arrays.stream(elArr).forEach(el -> {
            String[] elPart = el.split("=");
            propMap.put(toCamelCase(elPart[0]), elPart[1]);
        });

        //取出parameterType的类
        Field[] declaredFields = entityClass.getDeclaredFields();
        for (Field field : declaredFields) {
            //取出所有被EncryptedColumn注解的字段
            EncryptField encryptField = field.getAnnotation(EncryptField.class);
            if (Objects.isNull(encryptField)) {
                continue;
            }
            String el = propMap.get(field.getName());
            if(StringUtils.isEmpty(el)){
                continue;
            }
            Matcher matcher = PARAM_PAIRS_RE.matcher(el);
            if (matcher.matches()) {
                String valueKey = matcher.group(1);
                Object value = updateWrapper.getParamNameValuePairs().get(valueKey);
                updateWrapper.getParamNameValuePairs().put(valueKey, AesUtil.aesEncrypt(value.toString()));
            }
        }

        Method[] declaredMethods = entityClass.getDeclaredMethods();
        for (Method method : declaredMethods) {
            EncryptField encryptField = method.getAnnotation(EncryptField.class);
            if (Objects.isNull(encryptField)) {
                continue;
            }
            String el = propMap.get(method.getName());
            if(StringUtils.isEmpty(el)){
                continue;
            }
            Matcher matcher = PARAM_PAIRS_RE.matcher(el);
            if (matcher.matches()) {
                String valueKey = matcher.group(1);
                Object value = updateWrapper.getParamNameValuePairs().get(valueKey);
                updateWrapper.getParamNameValuePairs().put(valueKey, AesUtil.aesEncrypt(value.toString()));
            }
        }
    }

    public static String toCamelCase(String str) {
        String[] words = str.split("_");
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < words.length; i++) {
            if (i == 0) {
                result.append(words[0]);
            } else {
                String word = words[i];
                result.append(Optional.ofNullable(word).map(s -> Character.toUpperCase(s.charAt(0)) + s.substring(1)).orElse(""));
            }
        }
        return result.toString();
    }

    @Override
    public Object plugin(Object target) {
        if (target instanceof Executor) {
            return Plugin.wrap(target, this);
        }
        return target;
    }

    @Override
    public void setProperties(Properties properties) {
//            Interceptor.super.setProperties(properties);
    }
}
