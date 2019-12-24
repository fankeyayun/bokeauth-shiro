package com.boke.auth.exception.handler;

import com.boke.auth.utils.DataResult;
import com.boke.auth.exception.BusinessException;
import com.boke.auth.exception.code.BaseResponseCode;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.UnauthorizedException;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

/**
 * @ClassName: RestExceptionHandler
 * controller 层全局异常统一处理类
 * @Author: as
 * @CreateDate: 2019/10/4 15:55
 * @UpdateUser: as
 * @UpdateDate: 2019/10/4 15:55
 * @Version: 0.0.1
 */
@RestControllerAdvice
@Slf4j
public class RestExceptionHandler {

    /**
     * 系统繁忙，请稍候再试"
     * @Author:      as
     * @CreateDate:  2019/10/4 23:25
     * @UpdateUser:
     * @UpdateDate:  2019/10/4 23:25
     * @Version:     0.0.1
     * @param e
     * @return       com.xh.lesson.utils.DataResult<T>
     * @throws
     */
    @ExceptionHandler(Exception.class)
    public <T> DataResult<T> handleException(Exception e){
        log.error("Exception,exception:{}", e);
        return DataResult.getResult(BaseResponseCode.SYSTEM_BUSY);
    }

    /**
     * 自定义全局异常处理
     * @Author:      as
     * @CreateDate:  2019/10/4 16:39
     * @UpdateUser:
     * @UpdateDate:  2019/10/4 16:39
     * @Version:     0.0.1
     * @param e
     * @return       com.xh.lesson.utils.DataResult<T>
     * @throws
     */
    @ExceptionHandler(value = BusinessException.class)
    <T> DataResult<T> businessExceptionHandler(BusinessException e) {
        log.error("BusinessException,exception:{}", e);
        return new DataResult<>(e.getMessageCode(),e.getDetailMessage());
    }
    /**
     * 没有权限 抛出异常
     * @Author:      as
     * @CreateDate:  2019/10/23 15:19
     * @UpdateUser:
     * @UpdateDate:  2019/10/23 15:19
     * @Version:     0.0.1
     * @param e
     * @return       com.xh.lesson.utils.DataResult<T>
     * @throws
     */
    @ExceptionHandler(value = UnauthorizedException.class)
    <T> DataResult<T> unauthorizedException(UnauthorizedException e) {

        log.error("BusinessException,exception:{}", e);
        return new DataResult<>(BaseResponseCode.UNAUTHORIZED_ERROR);
    }
    /**
     * 处理validation 框架异常
     * @Author:      as
     * @CreateDate:  2019/10/5 16:01
     * @UpdateUser:
     * @UpdateDate:  2019/10/5 16:01
     * @Version:     0.0.1
     * @param e
     * @return       com.hth.cloud.common.base.HgResult<T>
     * @throws
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    <T> DataResult<T> methodArgumentNotValidExceptionHandler(MethodArgumentNotValidException e) {
        log.error("methodArgumentNotValidExceptionHandler bindingResult.allErrors():{},exception:{}", e.getBindingResult().getAllErrors(), e);
        List<ObjectError> errors = e.getBindingResult().getAllErrors();
        return createValidExceptionResp(errors);
    }
    private <T> DataResult<T> createValidExceptionResp(List<ObjectError> errors) {
        String[] msgs = new String[errors.size()];
        int i = 0;
        for (ObjectError error : errors) {
            msgs[i] = error.getDefaultMessage();
            log.info("msg={}",msgs[i]);
            i++;
        }
        return DataResult.getResult(BaseResponseCode.METHODARGUMENTNOTVALIDEXCEPTION.getCode(), msgs[0]);
    }

}
