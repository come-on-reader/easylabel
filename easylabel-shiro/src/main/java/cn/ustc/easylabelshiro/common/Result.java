package cn.ustc.easylabelshiro.common;

import java.util.List;

public class Result {
    /**
     * 是否成功
     **/
    private Boolean isSuccess;
    /**
     * 错误信息
     **/
    private String errorMsg;
    /**
     * 请求状态 200-成功 400-失败
     **/
    private Integer status;
    /**
     * 当前时间戳
     **/
    private Long timestamp;
    /**
     * 返回结果
     **/
    private Object data;

    public Result(boolean b, Object o, int i, long l, Object o1) {
    }

    public static Result ok() {
        return new Result(true, null, 200, System.currentTimeMillis(),null);
    }

    public static Result ok(Object data) {
        return new Result(true, null, 200,System.currentTimeMillis(),data);
    }

    public static Result ok(List<?> data) {
        return new Result(true, null, 200,System.currentTimeMillis(),data);
    }

    public static Result fail(String errorMsg) {
        return new Result(false, errorMsg, 400,System.currentTimeMillis(),null);
    }
}