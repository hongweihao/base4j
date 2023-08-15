package io.github.hongweihao.base4j.model.result;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * <p>
 * Result
 * </p>
 *
 * @author Karl
 * @since 2023/8/2 9:15
 */
@ApiModel(value = "Result")
public class Result<T> {
    @ApiModelProperty(value = "HTTP 状态码")
    private Integer status;

    @ApiModelProperty(value = "错误码")
    private String code;

    @ApiModelProperty(value = "错误信息")
    private String message;

    @ApiModelProperty(value = "业务数据对象")
    private T data;

    public static Result<Object> ok() {
        return ok(null);
    }

    public static <T> Result<T> ok(T data) {
        return new Result<>(HttpStatus.OK, ErrorCodeBase.OK, null, data);
    }

    public static <T> Result<T> error(ErrorCode code, String message) {
        return result(HttpStatus.INTERNAL_SERVER_ERROR, code, message);
    }

    public static <T> Result<T> error(ErrorCode code, String message, T data) {
        return result(HttpStatus.INTERNAL_SERVER_ERROR, code, message, data);
    }

    public static <T> Result<T> bad(ErrorCode code, String message) {
        return result(HttpStatus.BAD_REQUEST, code, message);
    }

    public static <T> Result<T> bad(ErrorCode code, String message, T data) {
        return result(HttpStatus.BAD_REQUEST, code, message, data);
    }

    public static <T> Result<T> result(HttpStatus status, ErrorCode code, String message) {
        return result(status, code, message, null);
    }

    public static <T> Result<T> result(HttpStatus status, ErrorCode code, String message, T data) {
        return new Result<>(status, code, message, data);
    }

    public Result() {
    }

    public Result(HttpStatus status, ErrorCode code, String message, T data) {
        this.status = status.value();
        this.code = code.name();
        this.message = message;
        this.data = data;
    }

    public Result(HttpStatus status, ErrorCode code, String message) {
        this.status = status.value();
        this.code = code.name();
        this.message = message;
    }

    public Integer getStatus() {
        return status;
    }

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public T getData() {
        return data;
    }
}
