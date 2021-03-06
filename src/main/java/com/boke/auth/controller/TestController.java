package com.boke.auth.controller;

import com.boke.auth.utils.DataResult;
import com.boke.auth.exception.BusinessException;
import com.boke.auth.exception.code.BaseResponseCode;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ClassName: TestController
 * TODO:类文件简单描述
 * @Author: as
 * @CreateDate: 2019/9/7 22:22
 * @UpdateUser: as
 * @UpdateDate: 2019/9/7 22:22
 * @Version: 0.0.1
 */
@RestController
@RequestMapping("/test")
public class TestController {
    @GetMapping("/index")
    @ApiOperation(value = "引导页接口")
    public String testResult(){
        return "Hello World";
    }

    @GetMapping("/home")
    @ApiOperation(value = "测试DataResult接口")
    public DataResult<String> getHome(){
        int i=1/0;
        DataResult<String> result=DataResult.success("哈哈哈哈测试成功 欢迎来test接口");
        return result;
    }
    @GetMapping("/test/business/error")
    @ApiOperation(value = "测试主动抛出业务异常接口")
    public DataResult<String> testBusinessError(@RequestParam String type){
        if(!type.equals("1")||type.equals("2")||type.equals("3")){
            throw new BusinessException(BaseResponseCode.DATA_ERROR);
        }
        DataResult<String> result=new DataResult(0,type);
        return result;
    }

}
