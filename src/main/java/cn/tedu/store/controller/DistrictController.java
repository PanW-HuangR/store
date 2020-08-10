package cn.tedu.store.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cn.tedu.store.entity.District;
import cn.tedu.store.service.IDistrictService;
import cn.tedu.store.util.JsonResult;

/**
 * 处理省/市/区数据相关请求的控制器类
 * @author dell
 *
 */
@RestController
@RequestMapping("districts")
public class DistrictController extends BaseController {
	
	@Autowired
	private IDistrictService iDistrictService;
	
	//http://localhost:80/districts/?parent=86
	@GetMapping({"","/"})
	public JsonResult<List<District>> getByParent(String parent){
		//调用业务对象获取数据
		List<District> list = iDistrictService.getByParent(parent);
		//返回OK与数据
		return new JsonResult<List<District>>(OK, list);
	}
	
}
