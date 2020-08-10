package cn.tedu.store.config;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import cn.tedu.store.interceptor.LoginInterceptor;


/**
 * 配置拦截器的类
 * @author 14526
 *
 */
@Configuration
public class InterceptorConfigurer implements WebMvcConfigurer {

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		HandlerInterceptor interceptor = new LoginInterceptor();
		
		List<String> patterns = new ArrayList<String>();
		patterns.add("/users/reg");
		patterns.add("/users/login");
		patterns.add("/web/register.html");
		patterns.add("/web/login.html");
		patterns.add("/bootstrap3/**");
		patterns.add("/css/**");
		patterns.add("/images/**");
		patterns.add("/js/**");
		patterns.add("/districts/**");
		
		//  addPathPatterns(/**): 拦截全部请求       excludePathPatterns(patterns)：排除集合里面的请求不拦截
		registry.addInterceptor(interceptor)
				.addPathPatterns("/**")
				.excludePathPatterns(patterns);
	}
	
}
