package com.Bin.quartz.utils;

/**
 * 响应码
 *
 * @author Bin
 */
public enum ResponseMessageCodeEnum {

    /**
     * 操作成功
     */
    SUCCESS("0"),
    /**
     * 操作错误
     */
    ERROR("-1"),
    /**
     * 操作异常
     */
    EXCEPTION("-2"),
    /**
     * 参数验证错误
     */
    VALID_ERROR("1000"),
    /**
     * 重定向登录
     */
    RE_LOGIN("999");

    private String code;

    ResponseMessageCodeEnum(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
