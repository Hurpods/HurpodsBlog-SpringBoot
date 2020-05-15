package com.hurpods.springboot.hurpodsblog.result;

import org.springframework.stereotype.Component;

@Component
public class ResultFactory {
    public static Result buildSuccessResult(String message, Object data) {
        return new Result(ResultCode.SUCCESS.getCode(), message, data);
    }

    public static Result buildSuccessResult(Object data) {
        return new Result(ResultCode.SUCCESS.getCode(), ResultCode.SUCCESS.getMessage(), data);
    }

    public static Result buildFailureResult(ResultCode resultCode) {
        return new Result(resultCode.getCode(), resultCode.getMessage(), null);
    }

    public static Result buildFailureResult(String message) {
        return new Result(ResultCode.ERROR.getCode(), message, null);
    }

    public static Result buildCustomFailureResult(ResultCode resultCode, String message) {
        return new Result(resultCode.getCode(), message, null);
    }
}
