package com.chenay.common.msg;

public enum CodeEntity {

    SUCCESS(1, "请求成功"),
    WARN(-1, "网络异常，请稍后重试"),
    ERROR(-1,"请求失败!"),
    NO_DATA(-2, "没有数据!"),

    ALERT(2000, "提示!"),
    CODE_SHORTAGE(2001,"暂存区缺货,是否直接将转移到暂存区?"),


    CODE_NEW_JWT(1005,"new jwt","jwt"),
    CODE_EXPIRED_JWT(1006,"expired jwt",""),
    CODE_EXPIRED_JWT1(1006,"expired jwt","1"),
    CODE_ERROR_JWT(1007,"error jwt",""),
    CODE_NO_PERMISSION(1008,"no permission",""),
    CODE_ERROR_REQUEST(1111,"error request","");

    private int code;
    private String msg;
    private String key;

    CodeEntity(int code, String msg) {
        this.msg = msg;
        this.code = code;
    }

    CodeEntity(int code, String msg, String key) {
        this(code, msg);
        this.key = key;
    }

    public int getCode() {
        return code;
    }
    public String getMsg() {
        return msg;
    }

    public String getKey() {
        return key;
    }
}
