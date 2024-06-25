package cn.lyy.dynamic.thread.pool.common;

import lombok.Getter;

/**
 * 错误码
 */
@Getter
public enum ErrorCode {
    SUCCESS(0, "成功", "请求成功"),
    PARAMS_ERROR(40000, "参数错误", "请求参数异常"),
    NULL_ERROR(40001,"请求数据为空",""),
    NO_LOGIN(40100,"未登录",""),
    NO_AUTH(40101,"无权限访问",""),
    SYSTEM_ERROR(50000, "系统内部异常", "");

    private final int code;
    private final String message;
    private final String desciption;

    ErrorCode(int code, String message, String desciption) {
        this.code = code;
        this.message = message;
        this.desciption = desciption;
    }

}
