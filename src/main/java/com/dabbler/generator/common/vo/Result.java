package com.dabbler.generator.common.vo;

/**
 * 返回实体类封装
 * @param <T>
 */
public class Result<T> {
    private T data;
    private int code;
    private String message;

    public static Result build(){
        return new Result();
    }

    public Result<T> ok(){
        this.code = 200;
        return this;
    }
    public Result<T> ok(T data){
        this.data = data;
        this.code = 200;
        return this;
    }

    public Result<T> error(T data,int code,String message){
        this.data = data;
        this.code = code;
        this.message = message;
        return this;
    }

}
