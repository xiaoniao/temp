package com.kunyao.assistant.web.controller.member;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import com.kunyao.assistant.core.constant.ResultCode;
import com.kunyao.assistant.core.dto.Result;
import com.kunyao.assistant.core.dto.ResultFactory;
import com.kunyao.assistant.core.exception.ResourceNotFoundException;
import com.kunyao.assistant.core.exception.ServiceException;

/**
 * 统一处理异常信息
 */
@ControllerAdvice("com.kunyao.assistant.web.controller.member")
public class MemberErrorController {

	@ExceptionHandler
	@ResponseBody
	public Result exceptionHandler(Exception e) throws Exception{
		e.printStackTrace();
		if (e instanceof ServiceException) {
			return ResultFactory.createError(((ServiceException) e).getError());
		} else if (e instanceof ResourceNotFoundException) {
			throw e;
		}
		return ResultFactory.createError(ResultCode.DB_ERROR);
	}
}
