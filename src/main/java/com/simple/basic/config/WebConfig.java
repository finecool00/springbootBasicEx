package com.simple.basic.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.simple.basic.controller.HomeController;

@Configuration //이 파일이 설정파일이라는 것을 알림
public class WebConfig implements WebMvcConfigurer { //자바 빈 설정을 위해 상속
	
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
	@Bean
	public TestBean test2() {
		TestBean b = new TestBean();
		return b;
	}

}
