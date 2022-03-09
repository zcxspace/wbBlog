package com.xhy.wblog.controller.interceptor;


import com.xhy.wblog.controller.result.PublicResult;
import com.xhy.wblog.system.exception.BusinessException;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

// IOC管理
// 网络层异常增强器
@Component
@ControllerAdvice
public class ProjectExceptionAdvice {



    // 关联异常信息类
    @ExceptionHandler(BusinessException.class)
    @ResponseBody
    public PublicResult doBusinessException(BusinessException e) {

        // 若出错了，那就返回这些信息。
        // 返回状态码， 和异常信息
        return new PublicResult(e.getCode(), e.getMessage());
    }

}
