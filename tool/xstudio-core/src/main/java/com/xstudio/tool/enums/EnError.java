package com.xstudio.tool.enums;

/**
 * @author Beeant on 2017/2/26.
 */
public enum EnError {
    DEFAULT(0,""),
    NO_MATCH(1,"没有匹配项"),
    INSERT_NONE(2, "插入失败"),
    DELETE_NONE(3, "删除失败"),
    UPDATE_NONE(4, "更新失败"),
    CONFLICT(5,"已存在"),
    MORE_THAN_ONE(6,"数据不唯一"),
    SQL_EXECUTE_ERROR(10,"SQL执行失败"),

    FORBIDDEN(403,"未授权操作"),
    UNAUTHORIZED(401, "未登录"),
    AccountExpired(401,"登录超时"),
    TIMEOUT(408,"调用超时"),

    EMPTY_PARAM(1000,"缺少必填参数"),
    SERVICE_INVALID(1001,"服务不可用");

    private Integer code = 0;

    private String description = "";

    /**
     * Getter for property 'code'.
     *
     * @return Value for property 'code'.
     */
    public Integer getCode() {
        return code;
    }

    /**
     * Getter for property 'description'.
     *
     * @return Value for property 'description'.
     */
    public String getDescription() {
        return description;
    }

    EnError(Integer code, String description) {
        this.code = code;
        this.description = description;
    }
}
