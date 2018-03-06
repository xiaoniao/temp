package com.kunyao.assistant.web.controller.cross;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.TypeMismatchException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import com.kunyao.assistant.core.constant.ResultCode;
import com.kunyao.assistant.core.dto.Result;
import com.kunyao.assistant.core.dto.ResultFactory;
import com.kunyao.assistant.core.exception.ServiceException;

/**
 * 统一处理异常信息
 */
@ControllerAdvice("com.kunyao.assistant.web.controller.cross")
public class ErrorController {
	private Logger log = LoggerFactory.getLogger(ErrorController.class);

	@ExceptionHandler
	@ResponseBody
	public Result exceptionHandler(Exception e) {
		log.info(e.getMessage());
		e.printStackTrace();
		if (e instanceof ServiceException) {
			return ResultFactory.createError(((ServiceException) e).getError());
		} else if (e instanceof MissingServletRequestParameterException) {
			return ResultFactory.createError(ResultCode.ABNORMAL_REQUEST);
		} else if (e instanceof TypeMismatchException) {
			return ResultFactory.createError(ResultCode.PARAMETER_TYPEMISMATCH);
		}
		return ResultFactory.createError(ResultCode.DB_ERROR);
	}
}
