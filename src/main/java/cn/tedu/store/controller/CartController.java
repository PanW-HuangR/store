package cn.tedu.store.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cn.tedu.store.service.ICartService;
import cn.tedu.store.util.JsonResult;
import cn.tedu.store.vo.CartVO;

/**
 * 处理购物车数据相关请求的控制器类
 * @author dell
 * 
 *
 */
@RestController
@RequestMapping("carts")
public class CartController extends BaseController {
	
	@Autowired
	private ICartService iCartService;
	
	/**
	 * 请求路径：/carts/add_to_cart
	 * 请求参数：Integer pid, Integer num, HttpSession session
	 * 请求方式：POST
	 * 响应结果：JsonResult<Void>
	 */
	// http://localhost:80/carts/add_to_cart?pid=10000005&amount=3
	@PostMapping("add_to_cart")
	public JsonResult<Void> addToCart(Integer pid, Integer amount, HttpSession session) {
	        Integer uid = getUidFromSession(session);
	        String username = getUsernameFromSession(session);
	        iCartService.addToCart(uid, username, pid, amount);
	        return new JsonResult<>(OK);
	}
	
	/**
	 * 请求路径：/carts/
	 * 请求参数：HttpSession session
	 * 请求方式：GET
	 * 响应结果：JsonResult<List<CartVO>>
	 */
	// http://localhost:80/carts/
	@GetMapping("")
	public JsonResult<List<CartVO>> getVOByUid(HttpSession session) {
	    Integer uid = getUidFromSession(session);
	    List<CartVO> data = iCartService.getVOByUid(uid);
	    return new JsonResult<>(OK, data);
	}
	
}
