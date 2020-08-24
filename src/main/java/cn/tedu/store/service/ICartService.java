package cn.tedu.store.service;

import java.util.List;

import cn.tedu.store.vo.CartVO;

/**
 * 处理购物车数据的业务层接口
 * @author dell
 *
 */
public interface ICartService {
	
	/**
	 * 将商品添加到购物车
	 * @param uid 当前登录的用户的id
	 * @param username 当前登录的用户名
	 * @param pid 商品id
	 * @param amount 将商品添加到购物车中的数量
	 */
	void addToCart(Integer uid, String username, Integer pid, Integer amount);
	
	/**
	 * 查询某用户的购物车数据的列表
	 * @param uid 用户id
	 * @return 该用户的购物车数据的列表
	 */
	List<CartVO> getVOByUid(Integer uid);
	
}
