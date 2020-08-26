package cn.tedu.store.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import cn.tedu.store.entity.Order;
import cn.tedu.store.service.ex.ServiceException;

@RunWith(SpringRunner.class)
@SpringBootTest
public class OrderServiceTests {

	@Autowired
	private IOrderService service;
	
	@Test
	public void create() {
		try {
			Integer aid = 11;
			Integer[] cids = { 4,5,6,15000 };
			Integer uid = 4;
			String username = "新年快乐";
			Order order = service.create(aid, cids, uid, username);
			System.err.println(order);
		} catch (ServiceException e) {
			System.err.println(e.getClass().getName());
			System.err.println(e.getMessage());
		}
	}
	
}








