package cn.tedu.store.service;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import cn.tedu.store.entity.Product;
import cn.tedu.store.service.ex.ServiceException;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ProductServiceTests {
	
	@Autowired
	private IProductService iProductService;
	
	@Test
	public void getHotList() {
		List<Product> list = iProductService.getHotList();
		for (Product product : list) {
			System.err.println(product);
		}
	}
	
	@Test
	public void getProductById() {
		try {
			Integer id = 1000015;
			Product product = iProductService.getProductById(id);
			System.err.println(product);
		} catch (ServiceException e) {
			System.err.println(e.getClass().getName());
			System.err.println(e.getMessage());
			e.printStackTrace();
		}
		
	}
		
	
}
