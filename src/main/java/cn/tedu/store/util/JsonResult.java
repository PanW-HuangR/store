package cn.tedu.store.util;

/**
 * 用于封装向客户端的响应结果的类
 * 
 * @param <T> 向客户端响应的数据的类型
 */
public class JsonResult<T> {

	private Integer state; // 状态
	private String message; // 错误信息
	private T data; // 数据

	public JsonResult(Integer state, T data) {
		this.state = state;
		this.data = data;
	}

	public JsonResult(Integer state) {
		this.state = state;
	}

	public JsonResult(Throwable ex) {
		this.message = ex.getMessage();
	}

	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}

}
