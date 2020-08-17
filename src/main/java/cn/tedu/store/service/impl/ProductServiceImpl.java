package cn.tedu.store.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.tedu.store.entity.Product;
import cn.tedu.store.mapper.ProductMapper;
import cn.tedu.store.service.IProductService;
import cn.tedu.store.service.ex.ProductNotFoundException;

/**
 * 处理商品数据的业务层实现类
 * @author dell
 *
 */
@Service
public class ProductServiceImpl implements IProductService {
	
	@Autowired
	private ProductMapper productMapper;

	@Override
	public List<Product> getHotList() {
		List<Product> list = findHotList();
		for (Product product : list) {
			product.setCategoryId(null);
			product.setItemType(null);
			product.setSellPoint(null);
			product.setNum(null);
			product.setStatus(null);
			product.setPriority(null);
			product.setCreatedTime(null);
			product.setCreatedUser(null);
			product.setModifiedTime(null);
			product.setModifiedUser(null);
		}
		return list;
	}
	
	@Override
	public Product getProductById(Integer id) {
		Product product = findById(id);
		if(product == null) {
			throw new ProductNotFoundException("获取商品数据失败！尝试访问的数据不存在！");
		}
		product.setCategoryId(null);
		product.setPriority(null);
		product.setCreatedTime(null);
		product.setCreatedUser(null);
		product.setModifiedTime(null);
		product.setModifiedUser(null);
		return product;
	}
	
	
	
	/**
	 * 查询热点商品的列表
	 * @return 商品列表
	 */
	private List<Product> findHotList(){
		return productMapper.findHotList();
	}
	
	/**
	 * 查询商品详情
	 * @return 商品信息
	 */
	Product findById(Integer id) {
		return productMapper.findById(id);
	}
	
}
