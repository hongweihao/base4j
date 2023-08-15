package io.github.hongweihao.base4j.model.result;

/**
 * <p>
 * ErrorCodeCommon
 * </p>
 *
 * @author Karl
 * @since 2023/8/2 10:38
 */
public enum ErrorCodeRpc implements ErrorCode {
    ILLEGAL_ARGUMENT, // 参数不合法
    SYSTEM_TIMEOUT, // 接口超时
    //DATABASE_ERROR, // 数据库处理失败
    //CACHE_ERROR, // redis缓存处理失败
    //BINDING_ERROR, // 资源绑定失败
    METHOD_NOT_SUPPORTED, // 请求方法不支持
    ARGUMENT_NOT_VALID, // 参数校验通不过
    ARGUMENT_TYPE_MISMATCH, // 请求参数格式转换异常
    BIND_ERROR, // 参数类型不匹配
    ILLEGAL_STATE, // 非法状态
    DUPLICATE_KEY, // 数据已存在
    BODY_IS_MISS, // 读取body信息失败
}
