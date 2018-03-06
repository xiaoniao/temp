package com.kunyao.assistant.core.dto;

import java.util.Properties;

import com.kunyao.assistant.core.utils.PropertiesUtils;

public class ResultFactory {
	
	/**
	 * 初始化 错误信息的Properties文件
	 */
	public static Properties ERROR_PRO = null;
	static {
		ERROR_PRO = PropertiesUtils.loadPropertyInstance("/error.properties");
	}

	/**
	 * 创建 ErrorResult
	 * @param error
	 * @return
	 */
	public static Result createError(String error) {
		return new Result(error, false, getResultMsg(error));
	}
	
	/**
	 * 创建 SuccessResult
	 * @param error
	 * @return
	 */
	public static Result createSuccess() {
    	return new Result("0", true, getResultMsg("0"));
    }
	
	/**
	 * 创建 Success JsonResult
	 * @param data
	 * @return
	 */
	public static Result createJsonSuccess(Object data) {
		return new JsonResult<Object>("0", true, getResultMsg("0"), data);
	}
	
	/**
	 * 获取 错误编码所对应的错误信息
	 * @param error
	 * @return
	 */
	public static String getResultMsg(String error) {
		return ERROR_PRO.getProperty(error).split(";")[1];
	}
	
	/**
	 * 获取 错误编码所对应的错误信息
	 * @param error
	 * @return
	 */
	public static String getExceptionMsg(String error) {
		return ERROR_PRO.getProperty(error).split(";")[0];
	}
}
