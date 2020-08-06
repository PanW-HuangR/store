package cn.tedu.store;

import javax.servlet.MultipartConfigElement;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.unit.DataSize;

@SpringBootApplication
@MapperScan("cn.tedu.store.mapper")
@Configuration
public class StoreApplication {

	public static void main(String[] args) {
		SpringApplication.run(StoreApplication.class, args);
	}
	
	@Bean
	public MultipartConfigElement getMultipartConfigElement() {
		MultipartConfigFactory factory = new MultipartConfigFactory();
		
		factory.setMaxFileSize(DataSize.ofMegabytes(100)); //最大文件大小100M
		factory.setMaxRequestSize(DataSize.ofMegabytes(100));//最大请求大小100M
		
		return factory.createMultipartConfig();
	}

}
