package cn.tedu.store.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
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
	
}