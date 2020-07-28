package cn.tedu.store.controller;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import cn.tedu.store.service.ex.InsertException;
import cn.tedu.store.service.ex.PasswordNotMatchException;
import cn.tedu.store.service.ex.ServiceException;
import cn.tedu.store.service.ex.UserNotFoundException;
import cn.tedu.store.service.ex.UsernameDuplicateException;
import cn.tedu.store.util.JsonResult;

@RestControllerAdvice//全局
public class ExceptionHandlerController {
	@ExceptionHandler(ServiceException.class)
	public JsonResult<Void> handleException(Throwable ex){
		JsonResult<Void> jsonResult = new JsonResult<Void>(ex);
		if (ex instanceof UsernameDuplicateException) {
			jsonResult.setState(4000);
		} else if (ex instanceof UserNotFoundException) {
			jsonResult.setState(4001);
		} else if (ex instanceof PasswordNotMatchException) {
			jsonResult.setState(4002);
		} else if (ex instanceof InsertException) {
			jsonResult.setState(5000);
		}

		return jsonResult;
	}
}
