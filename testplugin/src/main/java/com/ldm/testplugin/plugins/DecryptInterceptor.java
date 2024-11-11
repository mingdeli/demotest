package com.ldm.testplugin.plugins;

import com.ldm.testplugin.annotation.EncryptFace;
import com.ldm.testplugin.annotation.EncryptField;
import com.ldm.testplugin.annotation.EncryptTable;
import com.ldm.testplugin.util.AesUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.cache.CacheKey;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.plugin.*;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ReflectionUtils;
import org.springframework.util.StringUtils;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Properties;

/**
 * 解密 *
 */
@Slf4j
@Component
@Intercepts({
        @Signature(type = Executor.class, method = "query", args = {MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class}),
        @Signature(type = Executor.class, method = "query", args = {MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class, CacheKey.class, BoundSql.class})
})
public class DecryptInterceptor implements Interceptor {

    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        Object proceed = invocation.proceed();
        if (proceed == null) {
            return null;
        }
        if (proceed instanceof List) {
            List<Object> list = (List) proceed;
            if(CollectionUtils.isEmpty(list)){
                return proceed;
            }
            Optional<Object> first = list.stream().filter(Objects::nonNull).findFirst();
            if(!first.isPresent()){
                return proceed;
            }
            if (first.get() instanceof EncryptFace || needToDecrypt(first.get())) {
                for (Object item : list) {
                    // 逐一解密
                    decryptField(item);
                }
            }
            return proceed;
        }
        if (proceed instanceof EncryptFace) {
            decryptField(proceed);
        }
        return proceed;
    }

    private boolean needToDecrypt(Object object) {
        if(Objects.isNull(object)){
            return false;
        }
        Class<?> objectClass = object.getClass();
        EncryptTable sensitiveData = AnnotationUtils.findAnnotation(objectClass, EncryptTable.class);
        return Objects.nonNull(sensitiveData);
    }

    /**
     * 通过查询注解@Encrypt或者getEncryptFields返回的字段,进行解密 *
     *
     * @param item
     */
    private void decryptField(Object item) {
        if(Objects.isNull(item)){
            return;
        }
        Class<?> aClass = item.getClass();

        String[] encryptFields = null;
        if (item instanceof EncryptFace) {
            encryptFields = ((EncryptFace) item).getEncryptFields();
        }
        if (encryptFields == null || encryptFields.length == 0) {
            Field[] declaredFields = aClass.getDeclaredFields();
            for (Field field : declaredFields) {
//                field.setAccessible(true); // Noncompliant
                ReflectionUtils.makeAccessible(field);
                EncryptField encryptField = field.getAnnotation(EncryptField.class);
                if (!Objects.isNull(encryptField)) {
                    try {
                        Optional<String> encrypt = Optional.ofNullable(field.get(item)).map(Object::toString);
                        if(encrypt.isPresent()) {
                            String decryptString = AesUtil.aesDecrypt(encrypt.get());
                            if (!StringUtils.isEmpty(decryptString)) {
                                field.set(item, decryptString); // Noncompliant
                            }
                        }
                    } catch (Exception e) {
                        log.info("Decryption interceptor Failed，decrypt field: {}", field.getName());
                    }
                }
            }
        } else {
            for (String fieldName : encryptFields) {
                Field field = null;
                try {
                    field = aClass.getDeclaredField(fieldName);
                    ReflectionUtils.makeAccessible(field);
//                    field.setAccessible(true);
                    String decryptString = AesUtil.aesDecrypt(field.get(item).toString());
                    field.set(item, decryptString);
                } catch (NoSuchFieldException | IllegalAccessException e) {
                    log.debug("Decryption interceptor Failed，decrypt field: {}", field);
                }
            }
        }

    }

    @Override
    public Object plugin(Object target) {
        return Plugin.wrap(target, this);
    }

    @Override
    public void setProperties(Properties properties) {
//        Interceptor.super.setProperties(properties);
    }
}
