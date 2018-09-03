package com.psys.hobb.common.sys.util.constant;

/**
 * 编  号：
 * 名  称：SysExceptionEnum
 * 描  述：系统异常枚举类
 * 完成日期：2018/5/5 13:09
 * 编码作者：SHJ
 */
public enum SysExceptionEnum {

    SUCCESS(0, "成功"), PART_SUCCESS(1, "批量处理，部分成功，部分失败！"),
    ERR400(400, "请求参数错误！"), ERR401(401, "请求未授权！"),
    ERR500(500, "服务器处理异常！"), ERRUNKNOWN(-1, "未知异常！");

    private int code;

    private String msg;

    private SysExceptionEnum(int code, String msg){
        this.code = code;
        this.msg = msg;
    }

    public static SysExceptionEnum getByCode(int code){
        for (SysExceptionEnum senum : values()){
            if(code == senum.getCode()){
                return senum;
            }
        }
        return ERRUNKNOWN;
    }

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}
