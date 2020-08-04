package cn.tedu.store.controller;

import javax.servlet.http.HttpSession;

abstract class BaseController {
	
	/**
	 * 响应正确时使用的状态码
	 */
	public static final Integer OK = 2000;
	
	/**
	 * 从Session中获取当前登录的用户的用户名
	 * @param session HttpSession对象
	 * @return 当前登录的用户的用户名
	 */
	public String getUsernameFromSession(HttpSession session) {
		String username = session.getAttribute("username").toString();
		return username;
	}

	/**
	 * 从Session中获取当前登录的用户的id
	 * @param session HttpSession对象
	 * @return 当前登录的用户的id
	 */
	public Integer getUidFromSession(HttpSession session) {
		Integer uid = Integer.valueOf(session.getAttribute("uid").toString());
		return uid;
	}
	
}
