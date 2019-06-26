package com.irish.aop;

import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

import com.irish.exception.FormRepeatException;


@Aspect
@Component
public class TokenAspect {

    @SuppressWarnings("unused")
	@Before("within(@org.springframework.stereotype.Controller *) && @annotation(token)")
    public void testToken(final JoinPoint joinPoint, Token token){
        try {
            if (token != null) {
                //获取 joinPoint 的全部参数
                Object[] args = joinPoint.getArgs();
                HttpServletRequest request = null;
                HttpServletResponse response = null;
                for (int i = 0; i < args.length; i++) {
                    //获得参数中的 request && response
                    if (args[i] instanceof HttpServletRequest) {
                        request = (HttpServletRequest) args[i];
                    }
                    if (args[i] instanceof HttpServletResponse) {
                        response = (HttpServletResponse) args[i];
                    }
                }

                boolean needSaveSession = token.save();
                if (needSaveSession){
                    String uuid = UUID.randomUUID().toString();
                    request.getSession().setAttribute( "token" , uuid);
                    System.out.println("进入表单页面，Token值为："+uuid);
                }

                boolean needRemoveSession = token.remove();
                if (needRemoveSession) {
                    if (isRepeatSubmit(request)) {
                    	System.out.println("表单重复提交");
                        throw new FormRepeatException("表单重复提交");
                    }
                    request.getSession(false).removeAttribute( "token" );
                }
            }

        } catch (FormRepeatException e){
            throw e;
        } catch (Exception e){
        	e.printStackTrace();
        	throw e;
        }
    }

    private boolean isRepeatSubmit(HttpServletRequest request) throws FormRepeatException {
        String serverToken = (String) request.getSession( false ).getAttribute( "token" );
        if (serverToken == null ) {
            return true;
        }
        String clinetToken = request.getParameter( "token" );
        if (clinetToken == null || clinetToken.equals("")) {
            return true;
        }
        if (!serverToken.equals(clinetToken)) {
            return true ;
        }
        System.out.println("校验是否重复提交：表单页面Token值为："+clinetToken + ",Session中的Token值为:"+serverToken);
        return false ;
    }
}
