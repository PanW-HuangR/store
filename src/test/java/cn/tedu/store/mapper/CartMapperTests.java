package cn.tedu.store.mapper;

import java.util.Date;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import cn.tedu.store.entity.Cart;
import cn.tedu.store.vo.CartVO;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CartMapperTests {
	
	@Autowired
	private CartMapper cartMapper;
	
	@Test
	public void insert() {
		Cart cart = new Cart();
		cart.setCreatedTime(new Date());
		cart.setUid(2);
		cart.setPid(10000001);
		cart.setNum(1);
		cart.setPrice(12l);
		int rows = cartMapper.insert(cart);
		System.err.println("rows="+rows);
	}
	
	@Test
    public void updateNumByCid() {
        Integer cid = 1;
        Integer num = 10;
        String modifiedUser = "默认管理员";
        Date modifiedTime = new Date();
        Integer rows = cartMapper.updateNumByCid(cid, num, modifiedUser, modifiedTime);
        System.err.println("rows=" + rows);
    }

    @Test
    public void findByUidAndPid() {
        Integer uid = 2;
        Integer pid = 10000001;
        Cart result = cartMapper.findByUidAndPid(uid, pid);
        System.err.println(result);
    }
	
    @Test
    public void findVOByUid() {
        Integer uid = 4;
        List<CartVO> list = cartMapper.findVOByUid(uid);
        System.err.println("count=" + list.size());
        for (CartVO item : list) {
            System.err.println(item);
        }
    }
    
    @Test
    public void findVOByCids() {
        Integer[] cids = { 1, 2, 4, 800, 900 };
        List<CartVO> list = cartMapper.findVOByCids(cids);
        System.err.println("count=" + list.size());
        for (CartVO item : list) {
            System.err.println(item);
        }
    }
    
}
