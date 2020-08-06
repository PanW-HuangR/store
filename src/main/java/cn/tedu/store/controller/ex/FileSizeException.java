package cn.tedu.store.controller.ex;

/**
 * 上传文件不符合系统设置的大小范围异常
 * @author dell
 *
 */
public class FileSizeException extends FileUploadException{

	private static final long serialVersionUID = 3652563516851916279L;

	public FileSizeException() {
		super();
	}

	public FileSizeException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public FileSizeException(String message, Throwable cause) {
		super(message, cause);
	}

	public FileSizeException(String message) {
		super(message);
	}

	public FileSizeException(Throwable cause) {
		super(cause);
	}
	
}
