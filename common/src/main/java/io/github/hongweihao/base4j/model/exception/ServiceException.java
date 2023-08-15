package io.github.hongweihao.base4j.model.exception;


import io.github.hongweihao.base4j.model.result.ErrorCode;
import io.github.hongweihao.base4j.model.result.ErrorCodeBase;
import io.github.hongweihao.base4j.model.result.HttpStatus;

/**
 * <p>
 * BaseException
 * </p>
 *
 * @author Karl
 * @since 2023/8/1 14:27
 */
public class ServiceException extends RuntimeException {
    /**
     * 状态码，用于分割问题
     */
    private final HttpStatus status;

    /**
     * 错误码，用于标识业务问题
     */
    private final ErrorCode code;

    /**
     * 错误提示消息
     */
    private String message;


    public static void throwInternalServerEx() {
        throwInternalServerEx(HttpStatus.INTERNAL_SERVER_ERROR.message());
    }

    public static void throwInternalServerEx(String message) {
        throwEx(HttpStatus.INTERNAL_SERVER_ERROR, ErrorCodeBase.INTERNAL_SERVER_ERROR, message);
    }

    public static void throwBadRequestEx() {
        throwBadRequestEx(HttpStatus.INTERNAL_SERVER_ERROR.message());
    }

    public static void throwBadRequestEx(String message) {
        throwBadRequestEx(ErrorCodeBase.BAD_REQUEST, message);
    }

    public static void throwBadRequestEx(ErrorCode errorCode, String message) {
        throwEx(HttpStatus.BAD_REQUEST, errorCode, message);
    }

    public static void throwTooManyRequestEx() {
        throwBadRequestEx(HttpStatus.TOO_MANY_REQUESTS.message());
    }

    public static void throwTooManyRequestEx(String message) {
        throwEx(HttpStatus.TOO_MANY_REQUESTS, ErrorCodeBase.TOO_MANY_REQUESTS, message);
    }

    public static void throwNotFoundEx(String message) {
        throwBadRequestEx(ErrorCodeBase.NOT_FOUND, message);
    }

    public static void throwNotFoundEx(ErrorCode errorCode, String message) {
        throwEx(HttpStatus.NOT_FOUND, errorCode, message);
    }

    public static void throwEx(HttpStatus status, ErrorCode code, String message) {
        throw new ServiceException(status, code, message);
    }

    public static void throwEx(Integer status, ErrorCode code, String message) {
        throw new ServiceException(HttpStatus.valueOf(status), code, message);
    }

    public static void throwEx(Integer status, String code, String message) {
        throw new ServiceException(HttpStatus.valueOf(status), () -> code, message);
    }


    public ServiceException(HttpStatus status, ErrorCode code, String message) {
        super(message);
        this.status = status;
        this.code = code;
        this.message = message;
    }

    public ServiceException(HttpStatus status, ErrorCode code) {
        super(status.message());
        this.status = status;
        this.code = code;
    }

    public HttpStatus getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    public ErrorCode getCode() {
        return code;
    }
}
