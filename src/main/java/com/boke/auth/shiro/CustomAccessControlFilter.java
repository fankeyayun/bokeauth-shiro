package com.boke.auth.shiro;

import com.alibaba.fastjson.JSON;
import com.boke.auth.utils.DataResult;
import com.boke.auth.constants.Constant;
import com.boke.auth.exception.BusinessException;
import com.boke.auth.exception.code.BaseResponseCode;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.web.filter.AccessControlFilter;
import org.springframework.util.StringUtils;


import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.OutputStream;

/**
 * @ClassName: CustomAccessControlFilter
 * TODO:类文件简单描述
 * @Author: as
 * @CreateDate: 2019/10/6 23:22
 * @UpdateUser: as
 * @UpdateDate: 2019/10/6 23:22
 * @Version: 0.0.1
 */
@Slf4j
public class CustomAccessControlFilter extends AccessControlFilter {
    /**
     * 是否允许访问
     * true：允许，交下一个Filter处理
     * false：回往下执行onAccessDenied
     * @Author:      as
     * @CreateDate:  2019/10/6 23:24
     * @UpdateUser:
     * @UpdateDate:  2019/10/6 23:24
     * @Version:     0.0.1
     * @param servletRequest
     * @param servletResponse
     * @param o
     * @return       boolean
     * @throws
     */
    @Override
    protected boolean isAccessAllowed(ServletRequest servletRequest, ServletResponse servletResponse, Object o) {
        return false;
    }
    /**
     * 表示访问拒绝时是否自己处理，
     * 如果返回true表示自己不处理且继续拦截器链执行，
     * 返回false表示自己已经处理了（比如重定向到另一个页面）。
     * @Author:      as
     * @CreateDate:  2019/10/6 23:25
     * @UpdateUser:
     * @UpdateDate:  2019/10/6 23:25
     * @Version:     0.0.1
     * @param servletRequest
     * @param servletResponse
     * @return       boolean
     * @throws
     */
    @Override
    protected boolean onAccessDenied(ServletRequest servletRequest, ServletResponse servletResponse)   {
        try {
            HttpServletRequest request= (HttpServletRequest) servletRequest;
            log.info(request.getMethod());
            log.info(request.getRequestURL().toString());
            String token=request.getHeader(Constant.ACCESS_TOKEN);
            if(StringUtils.isEmpty(token)){
                throw new BusinessException(BaseResponseCode.TOKEN_ERROR);
            }
            CustomPasswordToken customPasswordToken=new CustomPasswordToken(token);
            // 委托给Realm进行登录
            getSubject(servletRequest, servletResponse).login(customPasswordToken);
        } catch (Exception e) {

            if(e.getCause() instanceof BusinessException){
                BusinessException exception= (BusinessException) e.getCause();
                customRsponse(exception.getMessageCode(),exception.getDetailMessage(),servletResponse);
            }else {
                customRsponse(BaseResponseCode.TOKEN_ERROR.getCode(),BaseResponseCode.TOKEN_ERROR.getMsg(),servletResponse);
            }
            return false;
        }
        return true;
    }
    /**
     * 自定义错误响应
     * @Author:      as
     * @CreateDate:  2019/10/8 19:14
     * @UpdateUser:
     * @UpdateDate:  2019/10/8 19:14
     * @Version:     0.0.1
     * @param code
     * @param msg
     * @param response
     * @return       void
     * @throws
     */
    private void customRsponse(int code,String msg,ServletResponse response){
        // 自定义异常的类，用户返回给客户端相应的JSON格式的信息
        try {
            DataResult result = DataResult.getResult(code,msg);

            response.setContentType("application/json; charset=utf-8");
            response.setCharacterEncoding("UTF-8");

            String userJson = JSON.toJSONString(result);
            OutputStream out = response.getOutputStream();
            out.write(userJson.getBytes("UTF-8"));
            out.flush();
        } catch (IOException e) {
            log.error("eror={}",e);
        }
    }

}
