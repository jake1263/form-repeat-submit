package com.irish.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.irish.aop.Token;


@Controller
public class URLController {

	
	/**
	 * 获取token,并将token保存在session中
	 * @return
	 */
    @Token(save = true)
    @RequestMapping("/queryToken")
    @ResponseBody
    public String getToken(HttpServletRequest request, HttpServletResponse response){
        return (String) request.getSession().getAttribute("token");
    }

    /**
                * 提交表单的地址，在AOP中检查表单是否重复提交，将token删除
     * @param request
     * @param response
     * @return
     */
    @Token(remove = true)
    @RequestMapping("/submitFrom")
    @ResponseBody
    public String removeToken(HttpServletRequest request, HttpServletResponse response){
        return "success";
    }
}