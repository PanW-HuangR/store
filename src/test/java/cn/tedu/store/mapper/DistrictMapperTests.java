package cn.tedu.store.mapper;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import cn.tedu.store.entity.District;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DistrictMapperTests {
	@Autowired
	private DistrictMapper districtMapper;
	
	@Test
	public void findByParent() {
		String parent = "86";
		List<District> list = districtMapper.findByParent(parent);
		System.err.println("count="+list.size());
		for (District district : list) {
			System.err.println(district);
		}
	}
	
	@Test
	public void findNameByCode() {
		String code = "130229";
		String name = districtMapper.findNameByCode(code);
		System.err.println(name);
	}
}
