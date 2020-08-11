package cn.tedu.store.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cn.tedu.store.entity.Address;
import cn.tedu.store.service.IAddressService;
import cn.tedu.store.util.JsonResult;

/**
 * 处理收货地址数据相关请求的控制器类
 * @author dell
 *
 */
@RestController
@RequestMapping("addresses")
public class AddressController extends BaseController {
	
	@Autowired
	private IAddressService iAddressService;
	
	//http://localhost:80/addresses/addnew?receiver="李陆行"&phone="16589452652"
	@RequestMapping("addnew")
	public JsonResult<Void> addnew(Address address,HttpSession session){
		Integer uid = getUidFromSession(session);
		String username = getUsernameFromSession(session);
		iAddressService.addNew(uid, username, address);
		return new JsonResult<Void>(OK);
	}
	
	//http://localhost:80/addresses
	@GetMapping("")
	public JsonResult<List<Address>> getByUid(HttpSession session){
		Integer uid = getUidFromSession(session);
		List<Address> list = iAddressService.getByUid(uid);
		return new JsonResult<List<Address>>(OK, list);
	}
	
	//RestFull网址请求设计风格
	//http://localhost:80/addresses/21/set_default
	@RequestMapping("{aid}/set_default")
	public JsonResult<Void> setDefault(@PathVariable("aid") Integer aid,HttpSession session){
		Integer uid = getUidFromSession(session);
		String username = getUsernameFromSession(session);
		iAddressService.setDefault(aid, uid, username);
		return new JsonResult<Void>(OK);
	}
	
}
