package com.dashu.dbtool.common.response;

import lombok.Getter;

/**
 * 响应状态码枚举
 * 定义系统中所有响应状态码
 * 遵循OOP枚举类型安全原则
 */
@Getter
public enum ResponseCode {
    /**
     * 成功
     */
    SUCCESS(200, "操作成功"),

    /**
     * 业务错误
     */
    BUSINESS_ERROR(400, "业务处理失败"),

    /**
     * 未授权
     */
    UNAUTHORIZED(401, "未授权，请先登录"),

    /**
     * 禁止访问
     */
    FORBIDDEN(403, "禁止访问，权限不足"),

    /**
     * 资源未找到
     */
    NOT_FOUND(404, "请求的资源不存在"),

    /**
     * 请求方法不支持
     */
    METHOD_NOT_ALLOWED(405, "请求方法不支持"),

    /**
     * 请求参数校验失败
     */
    VALIDATION_ERROR(422, "请求参数校验失败"),

    /**
     * 服务器内部错误
     */
    INTERNAL_SERVER_ERROR(500, "服务器内部错误"),

    /**
     * 服务不可用
     */
    SERVICE_UNAVAILABLE(503, "服务暂时不可用"),

    /**
     * 用户名或密码错误
     */
    LOGIN_FAILED(1001, "用户名或密码错误"),

    /**
     * 用户已存在
     */
    USER_EXISTS(1002, "用户已存在"),

    /**
     * 用户不存在
     */
    USER_NOT_FOUND(1003, "用户不存在"),

    /**
     * 用户已被禁用
     */
    USER_DISABLED(1004, "用户已被禁用"),

    /**
     * 验证码错误
     */
    VERIFICATION_CODE_ERROR(1005, "验证码错误"),

    /**
     * 验证码已过期
     */
    VERIFICATION_CODE_EXPIRED(1006, "验证码已过期"),

    /**
     * 文件上传失败
     */
    FILE_UPLOAD_FAILED(2001, "文件上传失败"),

    /**
     * 文件大小超限
     */
    FILE_SIZE_EXCEEDED(2002, "文件大小超过限制"),

    /**
     * 文件格式不支持
     */
    FILE_FORMAT_NOT_SUPPORTED(2003, "文件格式不支持"),

    /**
     * 数据库操作失败
     */
    DATABASE_ERROR(3001, "数据库操作失败"),

    /**
     * 外部服务调用失败
     */
    EXTERNAL_SERVICE_ERROR(4001, "外部服务调用失败");

    /**
     * 响应码
     */
    private final Integer code;

    /**
     * 响应消息
     */
    private final String message;

    /**
     * 构造函数
     *
     * @param code 响应码
     * @param message 响应消息
     */
    ResponseCode(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    /**
     * 根据响应码获取枚举
     *
     * @param code 响应码
     * @return 响应码枚举
     */
    public static ResponseCode fromCode(Integer code) {
        if (code == null) {
            return null;
        }
        for (ResponseCode responseCode : values()) {
            if (responseCode.getCode().equals(code)) {
                return responseCode;
            }
        }
        return INTERNAL_SERVER_ERROR;
    }

    /**
     * 判断是否成功
     *
     * @return 是否成功
     */
    public boolean isSuccess() {
        return this == SUCCESS;
    }
}
