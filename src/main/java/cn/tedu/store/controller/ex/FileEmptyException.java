package cn.tedu.store.controller.ex;

/**
 * 上传的文件为空的异常，例如，没有在表单中选择上传的文件，或者上传的文件大小为0字节
 * @author dell
 *
 */
public class FileEmptyException extends FileUploadException{

	private static final long serialVersionUID = -2399138190233012109L;

	public FileEmptyException() {
		super();
	}

	public FileEmptyException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public FileEmptyException(String message, Throwable cause) {
		super(message, cause);
	}

	public FileEmptyException(String message) {
		super(message);
	}

	public FileEmptyException(Throwable cause) {
		super(cause);
	}
	
}
