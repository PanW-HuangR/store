package cn.tedu.store.controller;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import cn.tedu.store.controller.ex.FileEmptyException;
import cn.tedu.store.controller.ex.FileSizeException;
import cn.tedu.store.controller.ex.FileTypeException;
import cn.tedu.store.controller.ex.FileUploadIOException;
import cn.tedu.store.controller.ex.FileUploadStateException;
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
		//日志
		System.err.println("UserController.reg()");
		iUserService.reg(user);
		return new JsonResult<>(OK);
	}
	
	// http://localhost:80/users/login?username=root&password=1234
	@RequestMapping("login")
	public JsonResult<User> login(String username, String password, HttpSession session) {
		//日志
		System.err.println("UserController.login()");
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
		//日志
		System.err.println("UserController.changePassword()");
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
		//日志
		System.err.println("UserController.showInfo()");
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
		//日志
		System.err.println("UserController.changeInfo()");
		// 从session中获取uid和username
		Integer uid = getUidFromSession(session);
		String username = getUsernameFromSession(session);
		// 调用业务对象执行修改
		iUserService.changeInfo(uid, username, user);
		// 返回
		return new JsonResult<>(OK);
	}
	
	/**
	 * 允许上传的文件大小的上限值，以字节为单位
	 */
	public static final long AVATAR_MAX_SIZE = 100;
	
	/**
	 * 允许上传的文件类型的集合
	 */
	public static final List<String> AVATAR_TYPES = new ArrayList<String>();
	static {
		AVATAR_TYPES.add("image/jpeg");
		AVATAR_TYPES.add("image/png");
		AVATAR_TYPES.add("image/gif");
		AVATAR_TYPES.add("image/bmp");
	}
	
	@PostMapping("avatar/change")
	public JsonResult<String> changeAvatar(MultipartFile file,HttpSession session) {
		//日志
		System.err.println("UserController.changeAvatar()");
		
		//判断文件是否为空
		if(file.isEmpty()) {
			//为空，则抛出异常
			throw new FileEmptyException("上传失败！请选择您要上传的文件！");
		}
		
		//获取文件的大小
		long size = file.getSize();
		if(size > 1024*AVATAR_MAX_SIZE) {
			throw new FileSizeException("上传失败！上传的文件超出了"+ AVATAR_MAX_SIZE +"KB的限制！");
		}
		
		//获取文件的MIME类型
		String contentType = file.getContentType();
		//判断上传的文件类型是否符合：image/jpeg, image/png, image/gif, image/bmp
		if(!AVATAR_TYPES.contains(contentType)) {
			throw new FileTypeException("上传失败！仅允许上传一些类型的文件：" + AVATAR_TYPES);
		}
		
		
		
		//获取原始文件名(客户端设备中的文件名)
		String originalFilename = file.getOriginalFilename();
		System.err.println("\toriginalFilename="+originalFilename);
		//将文件上传到哪个文件夹
		String parent = session.getServletContext().getRealPath("upload");
		System.err.println("\tupload path=" + parent);
		File dir = new File(parent);
		if(!dir.exists()) {
			dir.mkdirs();
		}
		
		//保存上传的文件时使用的文件名
		String filename = "" + System.currentTimeMillis() + System.nanoTime();
		String suffix = "";
		int beginIndex = originalFilename.lastIndexOf(".");
		if(beginIndex >= 1) {
			suffix = originalFilename.substring(beginIndex);
		}
		String child = filename + suffix;
		
		//将客户端上传的文件保存到服务器端
		File dest = new File(parent,child);
		try {
			file.transferTo(dest);
		} catch (IllegalStateException e) {
			throw new FileUploadStateException("上传失败！您的文件的状态异常！");
		} catch (IOException e) {
			throw new FileUploadIOException("上传失败！读写文件时出现错误，请重新上传！");
		}
		
		//将保存的文件的路径记录到数据库中
		String avatar = "/upload/" + child;
		System.err.println("\tavatar path=" + avatar);
		// 从session中获取uid和username
		Integer uid = getUidFromSession(session);
		String username = getUsernameFromSession(session);
		iUserService.changeAvatar(uid, avatar, username);
		return new JsonResult<String>(OK, avatar);
	}
	
}
