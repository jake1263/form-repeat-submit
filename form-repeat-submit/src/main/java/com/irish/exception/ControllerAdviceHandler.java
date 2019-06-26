package com.irish.exception;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class ControllerAdviceHandler {
	
	@ResponseBody
	@ExceptionHandler(value={com.irish.exception.FormRepeatException.class})
	public String arithmeticExceptionHandler(Exception e){
	       return "您重复提交表单了!";
	}

}
