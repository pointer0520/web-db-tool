package com.dashu.dbtool.common.response;

import com.dashu.dbtool.common.utils.DateTimeUtil;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 统一响应结果类
 * 封装所有API的响应数据
 * 使用泛型支持不同类型的数据返回
 * 遵循OOP封装原则和泛型编程
 *
 * @param <T> 数据类型
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CommonResult<T> {
    /**
     * 响应码
     */
    private Integer code;

    /**
     * 响应消息
     */
    private String message;

    /**
     * 响应数据
     */
    private T data;

    /**
     * 时间戳
     */
    private String timestamp;

    /**
     * 请求路径（可选）
     */
    private String path;

    /**
     * 私有构造函数
     * 强制使用静态工厂方法创建实例
     */
    private CommonResult() {
        this.timestamp = DateTimeUtil.formatDateTime(LocalDateTime.now());
    }

    /**
     * 私有构造函数
     *
     * @param code    响应码
     * @param message 响应消息
     * @param data    响应数据
     */
    private CommonResult(Integer code, String message, T data) {
        this();
        this.code = code;
        this.message = message;
        this.data = data;
    }

    /**
     * 成功响应（无数据）
     *
     * @param <T> 数据类型
     * @return 成功响应
     */
    public static <T> CommonResult<T> success() {
        return new CommonResult<>(ResponseCode.SUCCESS.getCode(),
                ResponseCode.SUCCESS.getMessage(),
                null);
    }

    /**
     * 成功响应（带数据）
     *
     * @param data 响应数据
     * @param <T>  数据类型
     * @return 成功响应
     */
    public static <T> CommonResult<T> success(T data) {
        return new CommonResult<>(ResponseCode.SUCCESS.getCode(),
                ResponseCode.SUCCESS.getMessage(),
                data);
    }

    /**
     * 成功响应（自定义消息）
     *
     * @param message 响应消息
     * @param <T>     数据类型
     * @return 成功响应
     */
    public static <T> CommonResult<T> success(String message) {
        return new CommonResult<>(ResponseCode.SUCCESS.getCode(),
                message,
                null);
    }

    /**
     * 成功响应（带数据和消息）
     *
     * @param message 响应消息
     * @param data    响应数据
     * @param <T>     数据类型
     * @return 成功响应
     */
    public static <T> CommonResult<T> success(String message, T data) {
        return new CommonResult<>(ResponseCode.SUCCESS.getCode(),
                message,
                data);
    }

    /**
     * 失败响应（默认错误）
     *
     * @param <T> 数据类型
     * @return 失败响应
     */
    public static <T> CommonResult<T> error() {
        return new CommonResult<>(ResponseCode.INTERNAL_SERVER_ERROR.getCode(),
                ResponseCode.INTERNAL_SERVER_ERROR.getMessage(),
                null);
    }

    /**
     * 失败响应（自定义消息）
     *
     * @param message 错误消息
     * @param <T>     数据类型
     * @return 失败响应
     */
    public static <T> CommonResult<T> error(String message) {
        return new CommonResult<>(ResponseCode.BUSINESS_ERROR.getCode(),
                message,
                null);
    }

    /**
     * 失败响应（指定响应码枚举）
     *
     * @param responseCode 响应码枚举
     * @param <T>          数据类型
     * @return 失败响应
     */
    public static <T> CommonResult<T> error(ResponseCode responseCode) {
        return new CommonResult<>(responseCode.getCode(),
                responseCode.getMessage(),
                null);
    }

    /**
     * 失败响应（指定响应码和消息）
     *
     * @param code    响应码
     * @param message 错误消息
     * @param <T>     数据类型
     * @return 失败响应
     */
    public static <T> CommonResult<T> error(Integer code, String message) {
        return new CommonResult<>(code, message, null);
    }

    /**
     * 失败响应（指定响应码枚举和自定义消息）
     *
     * @param responseCode 响应码枚举
     * @param message      自定义错误消息
     * @param <T>          数据类型
     * @return 失败响应
     */
    public static <T> CommonResult<T> error(ResponseCode responseCode, String message) {
        return new CommonResult<>(responseCode.getCode(), message, null);
    }

    /**
     * 根据条件返回成功或失败响应
     *
     * @param condition 条件
     * @param <T>       数据类型
     * @return 响应结果
     */
    public static <T> CommonResult<T> status(boolean condition) {
        return condition ? success() : error();
    }

    /**
     * 设置请求路径
     *
     * @param path 请求路径
     * @return this
     */
    public CommonResult<T> setPath(String path) {
        this.path = path;
        return this;
    }

    /**
     * 判断响应是否成功
     *
     * @return 是否成功
     */
    public boolean isSuccess() {
        return ResponseCode.SUCCESS.getCode().equals(this.code);
    }

    /**
     * 判断响应是否失败
     *
     * @return 是否失败
     */
    public boolean isError() {
        return !isSuccess();
    }
}
