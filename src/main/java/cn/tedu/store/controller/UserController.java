package cn.tedu.store.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cn.tedu.store.entity.User;
import cn.tedu.store.service.IUserService;
import cn.tedu.store.util.JsonResult;

@RestController
@RequestMapping("users")
public class UserController extends BaseController{
	
	@Autowired
	private IUserService iUserService;
	
	// http://localhost:80/users/reg?username=controller&password=1234&gender=0&phone=13100131001&email=controller@qq.com
	@RequestMapping("reg")
	public JsonResult<Void> reg(User user) {
		iUserService.reg(user);
		return new JsonResult<>(OK);
	}
	
	// http://localhost:80/users/login?username=root&password=1234
	@RequestMapping("login")
	public JsonResult<User> login(String username, String password, HttpSession session) {
		User data = iUserService.login(username, password);
		session.setAttribute("uid", data.getUid());
		session.setAttribute("username", data.getUsername());
		return new JsonResult<>(OK, data);
	}
	
	// http://localhost:80/users/password/change?oldPassword=1234&newPassword=123
	//请求路径：/users/password/change
	//请求参数：String oldPassword, String newPassword, HttpSession session
	//请求方式：POST
	//响应结果：JsonResult<Void>
	@RequestMapping("password/change")
	public JsonResult<Void> changePassword(String oldPassword, String newPassword, HttpSession session) {
		//从参数session中取出登录时存入的uid和username
		Integer uid = Integer.valueOf(session.getAttribute("uid").toString());
		String username = session.getAttribute("username").toString();
		//调用业务对象的方法执行修改密码
		iUserService.changePassword(uid, oldPassword, newPassword, username);
		//返回
		return new JsonResult<>(OK);
	}
	
	// http://localhost:80/users/info/show
	//请求路径：/users/info/show
	//请求参数：HttpSession session
	//请求方式：GET
	//响应结果：JsonResult<User>
	@RequestMapping("info/show")
	public JsonResult<User> showInfo(HttpSession session) {
		//从参数session中取出登录时存入的uid
		Integer uid = Integer.valueOf(session.getAttribute("uid").toString());
		//调用业务对象的showInfo()方法查询用户数据
		User data = iUserService.showInfo(uid);
		//返回
		return new JsonResult<>(OK,data);
	}
	
	// http://localhost:80/users/info/change?phone=13800138888&email=root@tedu.cn&gender=0
	//请求路径：/users/info/change
	//请求参数：User user, HttpSession session
	//请求方式：POST
	//响应结果：JsonResult<Void>
	@RequestMapping("info/change")
	public JsonResult<Void> changeInfo(User user, HttpSession session) {
		// 从session中获取uid和username
		Integer uid = getUidFromSession(session);
		String username = getUsernameFromSession(session);
		// 调用业务对象执行修改
		iUserService.changeInfo(uid, username, user);
		// 返回
		return new JsonResult<>(OK);
	}
	
}
