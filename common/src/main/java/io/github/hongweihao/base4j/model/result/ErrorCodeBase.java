package io.github.hongweihao.base4j.model.result;

/**
 * <p>
 * ErrorCodeCommon
 * </p>
 *
 * @author Karl
 * @since 2023/8/2 10:38
 */
public enum ErrorCodeBase implements ErrorCode {
    OK,
    INTERNAL_SERVER_ERROR,
    BAD_REQUEST,
    NOT_FOUND,
    TOO_MANY_REQUESTS, // 请求过于频繁/加锁失败

    ;

}
