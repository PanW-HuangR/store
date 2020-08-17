package cn.tedu.store.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cn.tedu.store.entity.Product;
import cn.tedu.store.service.IProductService;
import cn.tedu.store.util.JsonResult;

/**
 * 处理商品数据相关请求的控制器类
 * @author dell
 *
 */
@RestController
@RequestMapping("products")
public class ProductController extends BaseController {
	
	@Autowired
	private IProductService iProductService;
	
	//http://localhost:80/products/hot
	@GetMapping("hot")
	public JsonResult<List<Product>> getHotList() {
		List<Product> data = iProductService.getHotList();
		return new JsonResult<List<Product>>(OK, data);
	}
	
	//http://localhost:80/products/10000002/details
	//请求路径：/products/{id}/details
	//请求参数：@PathVariable("id") Integer id
	//请求方式：GET
	//响应结果：JsonResult<Product>
	@GetMapping("{id}/details")
	public JsonResult<Product> getProductById(@PathVariable("id") Integer id) {
		Product product = iProductService.getProductById(id);
		return new JsonResult<Product>(OK, product);
	}
	
}
