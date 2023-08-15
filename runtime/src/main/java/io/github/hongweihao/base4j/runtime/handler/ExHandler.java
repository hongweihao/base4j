package io.github.hongweihao.base4j.runtime.handler;

import io.github.hongweihao.base4j.model.exception.ServiceException;
import io.github.hongweihao.base4j.model.result.ErrorCodeBase;
import io.github.hongweihao.base4j.model.result.ErrorCodeRpc;
import io.github.hongweihao.base4j.model.result.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.stream.Collectors;


@RestControllerAdvice
public class ExHandler {

    private static final Logger log = LoggerFactory.getLogger(ExHandler.class);

    public ExHandler() {
    }

    /**
     * <p>
     * 业务异常处理
     * </p>
     *
     * @param e       e
     * @param request request
     * @return com.felo.usercore.common.result.Result<?>
     * @author Karl
     * @since 2023/8/2 11:03
     */
    @ExceptionHandler({ServiceException.class})
    public Result<Object> handleServiceException(ServiceException e, HttpServletRequest request) {
        log.error("ServiceException[{} => {}]", request.getRequestURI(), e.getMessage(), e);
        return Result.result(e.getStatus(), e.getCode(), e.getMessage());
    }

    public ResponseEntity<?>  handleServiceExceptionEntity(ServiceException e, HttpServletRequest request) {
        Result<Object> result = handleServiceException(e, request);
        return ResponseEntity.status(result.getStatus()).body(result);
    }


    /**
     * <p>
     * 参数解析失败
     * </p>
     *
     * @param ex      ex
     * @param request request
     * @return com.bench.common.model.JsonResult<java.lang.Object>
     * @author Karl
     * @since 2023/8/2 11:02
     */
    @ExceptionHandler({ServletRequestBindingException.class})
    public Result<Object> handleServletRequestBindingException(ServletRequestBindingException ex, HttpServletRequest request) {
        log.error("BindingException[{} => {}]", request.getRequestURI(), ex.getMessage(), ex);
        return Result.bad(ErrorCodeRpc.BIND_ERROR, ex.getMessage());
    }

    public ResponseEntity<?> handleServletRequestBindingExceptionEntity(ServletRequestBindingException ex, HttpServletRequest request) {
        Result<Object> result = handleServletRequestBindingException(ex, request);
        return ResponseEntity.status(result.getStatus()).body(result);
    }


    /**
     * <p>
     * http 方法名不匹配
     * </p>
     *
     * @param e       e
     * @param request request
     * @return com.bench.common.model.JsonResult<java.lang.Object>
     * @author Karl
     * @since 2023/8/2 11:05
     */
    @ExceptionHandler({HttpRequestMethodNotSupportedException.class})
    public Result<Object> handleHttpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException e, HttpServletRequest request) {
        log.warn("HttpRequestMethodNotSupportedException[{} => {}]", request.getRequestURI(), e.getMessage(), e);
        return Result.bad(ErrorCodeRpc.METHOD_NOT_SUPPORTED, e.getMessage());
    }

    public ResponseEntity<?>  handleHttpRequestMethodNotSupportedExceptionEntity(HttpRequestMethodNotSupportedException e, HttpServletRequest request) {
        Result<Object> result = handleHttpRequestMethodNotSupportedException(e, request);
        return ResponseEntity.status(result.getStatus()).body(result);
    }


    /**
     * <p>
     * validation 验证不通过
     * </p>
     *
     * @param e       e
     * @param request request
     * @return java.lang.Object
     * @author Karl
     * @since 2023/8/2 11:06
     */
    @ExceptionHandler({MethodArgumentNotValidException.class})
    public Result<Object> handleMethodArgumentNotValidException(MethodArgumentNotValidException e, HttpServletRequest request) {
        List<FieldError> fieldErrors = e.getBindingResult().getFieldErrors();
        String msg = fieldErrors.stream()
                .map(fieldError -> fieldError.getField() + ":" + fieldError.getDefaultMessage())
                .collect(Collectors.joining(";"));
        log.warn("handleMethodArgumentNotValidException:{} => {}", request.getRequestURI(), msg, e);
        return Result.bad(ErrorCodeRpc.ARGUMENT_NOT_VALID, msg);
    }

    public ResponseEntity<?> handleMethodArgumentNotValidExceptionEntity(MethodArgumentNotValidException e, HttpServletRequest request) {
        Result<Object> result = handleMethodArgumentNotValidException(e, request);
        return ResponseEntity.status(result.getStatus()).body(result);
    }

    /**
     * <p>
     * 请求参数格式转换异常
     * </p>
     *
     * @param e       e
     * @param request request
     * @return com.bench.common.model.JsonResult<java.lang.Object>
     * @author Karl
     * @since 2023/8/2 11:08
     */
    @ExceptionHandler({MethodArgumentTypeMismatchException.class})
    public Result<Object> handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException e, HttpServletRequest request) {
        log.error("handleMethodArgumentTypeMismatchException:{} => {}", request.getRequestURI(), e.getMessage(), e);
        return Result.bad(ErrorCodeRpc.ARGUMENT_TYPE_MISMATCH, e.getMessage());
    }
    public ResponseEntity<?> handleMethodArgumentTypeMismatchExceptionEntity(MethodArgumentTypeMismatchException e, HttpServletRequest request) {
        Result<Object> result = handleMethodArgumentTypeMismatchException(e, request);
        return ResponseEntity.status(result.getStatus()).body(result);
    }

    @ExceptionHandler({BindException.class})
    public Result<Object> handleBindException(BindException e, HttpServletRequest request) {
        List<FieldError> fieldErrors = e.getBindingResult().getFieldErrors();
        String msg = fieldErrors.stream()
                .map(fieldError -> fieldError.getField() + ":" + fieldError.getDefaultMessage())
                .collect(Collectors.joining(";"));
        log.error("handleBindException[{} -> {}]", request.getRequestURI(), msg, e);
        return Result.bad(ErrorCodeRpc.BIND_ERROR, msg);
    }

    public ResponseEntity<?> handleBindExceptionEntity(BindException e, HttpServletRequest request){
        Result<Object> result = handleBindException(e, request);
        return ResponseEntity.status(result.getStatus()).body(result);
    }

    @ExceptionHandler({IllegalStateException.class})
    public Result<Object> handleIllegalStateException(IllegalStateException e, HttpServletRequest request) {
        log.error("handleIllegalStateException[{} -> {}]", request.getRequestURI(), e.getMessage(), e);
        return Result.error(ErrorCodeRpc.ILLEGAL_STATE, e.getMessage());
    }

    public ResponseEntity<?> handleIllegalStateExceptionEntity(IllegalStateException e, HttpServletRequest request) {
        Result<Object> result = handleIllegalStateException(e, request);
        return ResponseEntity.status(result.getStatus()).body(result);
    }

    @ExceptionHandler({IllegalArgumentException.class})
    public Result<Object> handleIllegalArgumentException(IllegalArgumentException e, HttpServletRequest request) {
        log.error("handleIllegalArgumentException[{} -> {}]", request.getRequestURI(), e.getMessage(), e);
        return Result.error(ErrorCodeRpc.ILLEGAL_ARGUMENT, e.getMessage());
    }

    public ResponseEntity<?> handleIllegalArgumentExceptionEntity(IllegalArgumentException e, HttpServletRequest request) {
        Result<Object> result = handleIllegalArgumentException(e, request);
        return ResponseEntity.status(result.getStatus()).body(result);
    }

    @ExceptionHandler({DuplicateKeyException.class})
    public Result<Object> handleDuplicateKeyException(DuplicateKeyException e, HttpServletRequest request) {
        log.error("handleDuplicateKeyException[{} -> {}]", request.getRequestURI(), e.getMessage(), e);
        return Result.error(ErrorCodeRpc.DUPLICATE_KEY, e.getMessage());
    }

    public ResponseEntity<?> handleDuplicateKeyExceptionEntity(DuplicateKeyException e, HttpServletRequest request) {
        Result<Object> result = handleDuplicateKeyException(e, request);
        return ResponseEntity.status(result.getStatus()).body(result);
    }

    @ExceptionHandler({HttpMessageNotReadableException.class})
    public Result<Object> handleHttpMessageNotReadableException(HttpMessageNotReadableException e, HttpServletRequest request) {
        log.warn("handleHttpMessageNotReadableException[{} -> {}]", request.getRequestURI(), e.getMessage(), e);
        return Result.bad(ErrorCodeRpc.BODY_IS_MISS, e.getMessage());
    }

    public ResponseEntity<?> handleHttpMessageNotReadableExceptionEntity(HttpMessageNotReadableException e, HttpServletRequest request) {
        Result<Object> result = handleHttpMessageNotReadableException(e, request);
        return ResponseEntity.status(result.getStatus()).body(result);
    }

    @ExceptionHandler({Exception.class})
    public Result<Object> handleException(Exception e, HttpServletRequest request) {
        log.error("handleException[{} -> {}]", request.getRequestURI(), e.getMessage(), e);
        return Result.error(ErrorCodeBase.INTERNAL_SERVER_ERROR, e.getMessage());
    }

    public ResponseEntity<?> handleExceptionEntity(Exception e, HttpServletRequest request) {
        Result<Object> result = handleException(e, request);
        return ResponseEntity.status(result.getStatus()).body(result);
    }
}
