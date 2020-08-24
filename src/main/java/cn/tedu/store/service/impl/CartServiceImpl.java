package cn.tedu.store.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.tedu.store.entity.Cart;
import cn.tedu.store.entity.Product;
import cn.tedu.store.mapper.CartMapper;
import cn.tedu.store.service.ICartService;
import cn.tedu.store.service.IProductService;
import cn.tedu.store.service.ex.InsertException;
import cn.tedu.store.vo.CartVO;

/**
 * 处理购物车数据的业务层实现类
 * @author dell
 *
 */
@Service
public class CartServiceImpl implements ICartService {
	
	@Autowired
	private CartMapper cartMapper;
	@Autowired
	private IProductService iProductService;

	@Override
	public void addToCart(Integer uid, String username, Integer pid, Integer amount) {
		//创建当前时间对象
		Date now = new Date();
		//根据uid和pid查询购物车数据
		Cart result = findByUidAndPid(uid, pid);
		//判断查询结果是否为null
		if(result == null) {
			//是：表示该用户尚未将该商品添加到购物车，则需要插入购物车数据
			//调用iProductService.getById()方法查询商品数据
			Product product = iProductService.getProductById(pid);
			//创建Cart对象
			Cart cart = new Cart();
			//补全数据：uid，pid，num，price(从商品数据中获取)
			cart.setUid(uid);
			cart.setPid(pid);
			cart.setNum(amount);
			cart.setPrice(product.getPrice());
			//补全数据：4个日志
			cart.setCreatedTime(now);
			cart.setCreatedUser(username);
			cart.setModifiedTime(now);
			cart.setModifiedUser(username);
			//调用insert(Cart cart)执行插入数据
			insert(cart);
		}else {
			//否：表示该用户已经将商品添加到购物车，则需要修改购物车数据中商品的数量
			//从查询结构中获取cid
			Integer cid = result.getCid();
			//冲查询结构中获取num，与参数amount相加，得到新的数量
			Integer num = result.getNum() + amount;
			//调用updateNumByCid(cid, num, modifiedUser, modifiedTime)方法执行修改数量
			updateNumByCid(cid, num, username, now);
		}
	}
	
	@Override
	public List<CartVO> getVOByUid(Integer uid) {
		return findVOByUid(uid);
	}
	
	
	//-----------------------------------------------
	/**
	 * 插入购物车数据
	 * @param cart 购物车数据
	 * @return 受影响的行数
	 */
	private void insert(Cart cart) {
		Integer rows = cartMapper.insert(cart);
		if (rows != 1) {
			throw new InsertException(
				"创建购物车数据失败！插入购物车数据时出现未知错误，请联系系统管理员！");
		}
	}

	/**
	 * 修改购物车数据中商品的数量
	 * @param cid 购物车数据id
	 * @param num 新的数量
	 * @param modifiedUser 修改执行人
	 * @param modifiedTime 修改时间
	 * @return 受影响的行数
	 */
	private void updateNumByCid(Integer cid, Integer num, String modifiedUser, Date modifiedTime) {
		Integer rows = cartMapper.updateNumByCid(cid, num, modifiedUser, modifiedTime);
		if (rows != 1) {
			throw new InsertException(
				"更新商品数量失败！更新购物车数据时出现未知错误，请联系系统管理员！");
		}
	}

	/**
	 * 根据用户id与商品id查询购物车数据
	 * @param uid 用户id
	 * @param pid 商品id
	 * @return 匹配的购物车数据，如果没有匹配的数据，则返回null
	 */
	private Cart findByUidAndPid(Integer uid, Integer pid) {
		return cartMapper.findByUidAndPid(uid, pid);
	}
	
	/**
	 * 查询某用户的购物车数据的列表
	 * @param uid 用户id
	 * @return 该用户的购物车的数据列表
	 */
	private List<CartVO> findVOByUid(Integer uid) {
		return cartMapper.findVOByUid(uid);
	}
	
}
