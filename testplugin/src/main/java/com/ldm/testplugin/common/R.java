package com.ldm.testplugin.common;

import lombok.AllArgsConstructor;
import lombok.Data;


@Data
@AllArgsConstructor
public class R<T> {
    private Integer code;
    private String msg;
    private T data;

    public static R ok(){
        return ok(Constant.OK, null);
    }

    public static R ok(Object data){
        return ok(Constant.OK, data);
    }

    public static R ok( String msg, Object obj){
        return new R(Constant.CODE_OK, msg, obj);
    }

    public static R fail(){
        return fail(Constant.FAIL, null);
    }
    public static R fail(String msg){
        return fail(Constant.FAIL, msg);
    }
    public static R fail(String msg, Object obj){
        return new R(Constant.CODE_FAIL, msg, obj);
    }
}
