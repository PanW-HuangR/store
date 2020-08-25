package cn.tedu.store.service;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import cn.tedu.store.service.ex.ServiceException;
import cn.tedu.store.vo.CartVO;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CartServiceTests {
	
	@Autowired
	private ICartService iCartService;
	
	@Test
	public void addToCart() {
        try {
            Integer uid = 1;
            Integer pid = 10000002;
            Integer amount = 30;
            String username = "土豪";
            iCartService.addToCart(uid, username, pid, amount);
            System.err.println("OK.");
        } catch (ServiceException e) {
            System.err.println(e.getClass().getName());
            System.err.println(e.getMessage());
        }
    }
	
	@Test
	public void getVOByUid() {
	    Integer uid = 4;
	    List<CartVO> list = iCartService.getVOByUid(uid);
	    System.err.println("count=" + list.size());
	    for (CartVO item : list) {
	        System.err.println(item);
	    }
	}
	
	@Test
	public void getVOByCids() {
	    Integer[] cids = {1,2,3,4,13,14,15,16};
	    Integer uid = 4;
	    List<CartVO> list = iCartService.getVOByCids(cids, uid);
	    System.err.println("count=" + list.size());
	    for (CartVO item : list) {
	        System.err.println(item);
	    }
	}
	
}
