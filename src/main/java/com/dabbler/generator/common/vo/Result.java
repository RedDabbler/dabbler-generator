package com.dabbler.generator.common.vo;

import java.io.Serializable;
import java.util.List;

/**
 * 返回实体类封装
 * @param <T>
 */
public class Result<T> implements Serializable {
    private T data;
    private int code;
    private String message;
    private Page page;

    public static Result build(){
        return new Result();
    }

    public Result<T> ok(){
        this.code = 200;
        return this;
    }

    public Result<T> error(){
        this.code = 500;
        this.message = "系统异常";
        return this;
    }

    public Result<T> ok(T data){
        this.data = data;
        this.code = 200;
        return this;
    }

    public Page getPage() {
        return page;
    }

    public Result<T> error(T data, int code, String message){
        this.data = data;
        this.code = code;
        this.message = message;
        return this;
    }

    public Result page(List<T> data){
        page = new Page();
        page.setData(data);
        return this;
    }





}
