package cn.tedu.store.mapper;

import java.util.List;

import cn.tedu.store.entity.Product;

/**
 * 处理商品数据的持久层接口
 * @author dell
 *
 */
public interface ProductMapper {
	
	/**
	 * 查询热点商品的列表
	 * @return 商品列表
	 */
	List<Product> findHotList();
	
	/**
	 * 查询商品详情
	 * @return 商品信息
	 */
	Product findById(Integer id);
	
}
