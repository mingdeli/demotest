package com.ldm.testplugin.annotation;

/**
 * 标记需要加密的实体，并返回需要加密字段 *
 */
public interface EncryptFace {

    /**
     * 返回需要加密字段 *
     * @return
     */
    default String[] getEncryptFields() {
        return new String[0];
    }
}
