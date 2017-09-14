package com.lpnote.demo.web.support;

import java.util.HashMap;
import java.util.Map;

/**
 * 结果对象，用于WEB层封装RESTFUL结果返回
 * Created by luopeng on 2017/9/14.
 */
public class Result {

    public static final String EXCEPTION = "exception";
    public static final String FAIL = "failed";
    public static final String SUCCESS = "success";
    public static final String UNKNOWN = "unknown";

    private Object data;

    private String code;

    private String message;

    public Result() {
        super();
    }

    /**
     * 获取一个result实例
     *
     * @return 一个不携带任何信息的result实例
     */
    public static Result me() {
        return new Result();
    }

    public Result(String code, Map<String, Object> data) {
        this.code = code;
        this.data = data;
    }


    /**
     * 创建一个异常结果
     *
     * @return 一个异常结果实例, 不携带异常信息
     */
    public Result exception() {
        this.code = EXCEPTION;
        return this;
    }

    /**
     * 创建一个异常结果
     *
     * @param e 异常
     * @return 一个异常结果实例, 包含参数异常的信息
     */
    public Result exception(Exception e) {
        this.code = EXCEPTION;
        this.message = e.getMessage();
        return this;
    }

    /**
     * 创建一个异常结果
     *
     * @param msg 异常信息
     * @return 一个异常结果实例, 不携带异常信息
     */
    public Result exception(String msg) {
        this.code = EXCEPTION;
        this.message = msg;
        return this;
    }

    /**
     * 创建一个带失败信息的result
     *
     * @param message 失败原因
     * @return result实例
     */
    public Result fail(String message) {
        this.message = message;
        this.code = FAIL;
        return this;
    }

    /**
     * 创建一个成功结果
     *
     * @return result实例状态为成功无数据携带
     */
    public Result success() {
        this.code = SUCCESS;
        return this;
    }

    /**
     * 创建一个成功结果
     *
     * @param data 需要携带的数据
     * @return result实例状态为成功数据位传入参数
     */
    public Result success(Map<String, Object> data) {
        this.code = SUCCESS;
        this.data = data;
        return this;
    }

    public Result success(Object data) {
        this.data = data;
        this.code = SUCCESS;
        return this;
    }

    public Result message(String message) {
        this.message = message;
        return this;
    }

    public String getMessage() {
        return this.message;
    }

    /**
     * 添加更多的数据
     *
     * @param data 待添加的数据
     * @return 结果实例
     */
    public Result putData(String key, Object data) {
        if (this.data == null) {
            this.data = new HashMap<String, Object>();
        }
        if (this.data instanceof Map) {
            ((Map<String, Object>) this.data).put(key, data);
        } else {
            throw new RuntimeException("data is type of Map, can't be deal with Map class");
        }

        return this;
    }

    /**
     * 添加数据
     *
     * @param datas
     * @return
     */
    public Result putDatas(Map<String, Object> datas) {
        if (this.data == null) {
            this.data = new HashMap<String, Object>();
        }
        if (this.data instanceof Map) {
            ((Map<String, Object>) this.data).putAll(datas);
        } else {
            throw new RuntimeException("data is type of Map, can't be deal with Map class");
        }
        return this;
    }

    public Result data(Object data) {
        this.data = data;
        return this;
    }

    /**
     * 清空结果
     */
    public Result clear() {
        this.code = null;
        this.message = null;
        this.data = null;
        return this;
    }

    public Object getData() {
        return this.data;
    }


    public String getResultCode() {
        return code;
    }

    /**
     * 是否成功
     *
     * @return
     */
    public boolean isSuccess() {
        return SUCCESS.equals(code);
    }

    public Result resultCode(String code) {
        this.code = code;
        return this;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Result{");
        sb.append("data=").append(data);
        sb.append(", code='").append(code).append('\'');
        sb.append(", message='").append(message).append('\'');
        sb.append('}');
        return sb.toString();
    }

}
