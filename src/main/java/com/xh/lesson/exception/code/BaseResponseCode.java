package com.xh.lesson.exception.code;

/**
 * @ClassName: BaseResponseCode
 * TODO:异常信息枚举类
 * @Author: as
 * @CreateDate: 2019/10/4 22:57
 * @UpdateUser: as
 * @UpdateDate: 2019/10/4 22:57
 * @Version: 0.0.1
 */
public enum BaseResponseCode implements ResponseCodeInterface {
    /**
     * 这个要和前段约定好
     * 引导用户去登录界面的
     * code=401001 用户主动退出
     * code=401002 token 过期刷新token
     */
    SUCCESS(0,"操作成功"),
    SYSTEM_BUSY(500001, "系统繁忙，请稍候再试"),
    OPERATION_ERRO(500002,"操作失败"),

    TOKEN_PARSE_ERROR(401001, "解析应用授权信息失败"),
    TOKEN_ERROR(401001, "授权信息信息无效"),
    TOKEN_PAST_DUE(401002, "授权信息已过期，请刷新token"),
    DATA_ERROR(401003,"传入数据异常"),
    NOT_ACCOUNT(401004, "该用户不存在,请先注册"),
    USER_LOCK(401005, "该用户已被锁定，请联系运营人员"),
    PASSWORD_ERROR(401006,"用户名或密码错误"),
    METHODARGUMENTNOTVALIDEXCEPTION(401007, "方法参数校验异常"),
    UNAUTHORIZED_ERROR(401008, "沒有权限访问请联系管理员"),
    ROLE_PERMISSION_RELATION(401009, "该菜单权限存在关联，不允许删除"),

    ;

    /**
     * 错误码
     */
    private final int code;
    /**
     * 错误消息
     */
    private final String msg;

    BaseResponseCode(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    @Override
    public int getCode() {
        return code;
    }

    @Override
    public String getMsg() {
        return msg;
    }
}
