package cn.tedu.store.controller;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import cn.tedu.store.controller.ex.FileEmptyException;
import cn.tedu.store.controller.ex.FileSizeException;
import cn.tedu.store.controller.ex.FileTypeException;
import cn.tedu.store.controller.ex.FileUploadException;
import cn.tedu.store.controller.ex.FileUploadIOException;
import cn.tedu.store.controller.ex.FileUploadStateException;
import cn.tedu.store.service.ex.AccessDeniedException;
import cn.tedu.store.service.ex.AddressCountLimitException;
import cn.tedu.store.service.ex.AddressNotFoundException;
import cn.tedu.store.service.ex.InsertException;
import cn.tedu.store.service.ex.PasswordNotMatchException;
import cn.tedu.store.service.ex.ServiceException;
import cn.tedu.store.service.ex.UpdateException;
import cn.tedu.store.service.ex.UserNotFoundException;
import cn.tedu.store.service.ex.UsernameDuplicateException;
import cn.tedu.store.util.JsonResult;

@RestControllerAdvice//全局
public class ExceptionHandlerController {
	
	@ExceptionHandler({ServiceException.class,FileUploadException.class})
	public JsonResult<Void> handleException(Throwable ex){
		JsonResult<Void> jsonResult = new JsonResult<Void>(ex);
		if (ex instanceof UsernameDuplicateException) {
			jsonResult.setState(4000);
		} else if (ex instanceof UserNotFoundException) {
			jsonResult.setState(4001);
		} else if (ex instanceof PasswordNotMatchException) {
			jsonResult.setState(4002);
		}  else if (ex instanceof AddressCountLimitException) {
			jsonResult.setState(4003);
		}  else if (ex instanceof AddressNotFoundException) {
			jsonResult.setState(4004);
		}  else if (ex instanceof AccessDeniedException) {
			jsonResult.setState(4005);
		}  else if (ex instanceof InsertException) {
			jsonResult.setState(5000);
		}  else if (ex instanceof UpdateException) {
			jsonResult.setState(6000);
		}  else if (ex instanceof FileEmptyException) {
			jsonResult.setState(6001);
		}  else if (ex instanceof FileSizeException) {
			jsonResult.setState(6002);
		}  else if (ex instanceof FileTypeException) {
			jsonResult.setState(6003);
		}  else if (ex instanceof FileUploadIOException) {
			jsonResult.setState(6004);
		}  else if (ex instanceof FileUploadStateException) {
			jsonResult.setState(6005);
		}

		return jsonResult;
	}
}
