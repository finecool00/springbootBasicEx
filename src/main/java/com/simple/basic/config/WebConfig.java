package com.simple.basic.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.simple.basic.util.interceptor.UserAuthHandler;

@Configuration //이 파일이 설정파일이라는 것을 알림
public class WebConfig implements WebMvcConfigurer { //자바 빈 설정을 위해 상속
	
	//인터셉터로 사용할 클래스를 bean으로 생성
	@Bean
	public UserAuthHandler userAuthHandler() {
		return new UserAuthHandler();
	}

	
	//스프링설정에 인터셉터를 추가
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		
		registry.addInterceptor(userAuthHandler())
//		 		.addPathPatterns("/user/mypage")
//		 		.addPathPatterns("/user/modify")
//		 		.addPathPatterns("/memo/**")
//		 		.addPathPatterns("/product/**")
		 		.addPathPatterns("/user/**") //user로 시작하는 모든 요청검사
		 		.excludePathPatterns("/user/login") //user/login제외
		 		.excludePathPatterns("/user/loginForm"); //로그인메서드 제외	
		
		//인터셉터는 여러개가 있을수 있는데, 추가하면 됨
		
	}
	
	
	
	
	
	
	
	
//	//IoC확인
//	@Autowired
//	ApplicationContext applicationContext;
//	
//	@Value("${spring.datasource.url}") //데이터베이스의 값을 읽어올 때 "({값or변수})"으로 표현함
//	private String url;
//	
//	
//	@Bean //해당 메서드를 빈으로 생성!
//	public void test() {
//		
//		TestBean test = applicationContext.getBean(TestBean.class);
//		System.out.println("TestBean이 빈으로 등록됨:" + test);
//		
//		//@Controller를 통해 빈 자동 설정된 것 확인 - 성공!
//		HomeController con = applicationContext.getBean(HomeController.class);
//		System.out.println("homeController가 빈으로 등록됨:" + con);
//		
//		//
//		int count = applicationContext.getBeanDefinitionCount();
//		System.out.println("IoC안에 있는 빈의개수:" + count);
//		
//		//
//		System.out.println("application프로퍼티 키값:" + url);
//	}
	
	
	//빈 생성의 2가지 전략
	//1. @Controller, @Service 등을 이용해서 빈으로 등록
	//2. 스프링 설정파일에 빈으로 등록
	
	//return 객체를 반환하는 모형을 만들면!! 빈으로 등록!
//	@Bean
//	public TestBean test2() {
//		TestBean b = new TestBean();
//		return b;
//	}

}
