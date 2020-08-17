package cn.tedu.store.service;

import java.util.List;

import cn.tedu.store.entity.Product;

/**
 * 处理商品数据的业务层接口
 * @author dell
 *
 */
public interface IProductService {
	
	/**
	 * 查询热销商品列表
	 * @return 热销商品列表
	 */
	List<Product> getHotList();
	
	/**
	 * 查询商品详情
	 * @param id 商品id
	 * @return 商品信息
	 */
	Product getProductById(Integer id);
}
