package com.simple.basic;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.simple.basic.command.BuilderVO;
import com.simple.basic.command.BuilderVO2;

@SpringBootTest // 스프링 부트 테스트 클래스
public class JDBCTest {

	//빌더 패턴의 확인
	@Test
	public void testCode01() {
		//.out.println(1); //마이바티스 연결해줘야 값 받을 수 있음
		
//		Builder b = BuilderVO.builder();
//		b = b.age(10); //나 자신을반환하기 때문에 다시 builder타입으로 반환됨
//		b = b.name("홍길동");
//		BuilderVO vo = b.build();
		
		//vo는 setter가 없기 때문에 객체불변성을 보장합니다.
		BuilderVO vo = BuilderVO.builder().age(10).name("홍길동").build(); //세터로 값을 바꿔 저장할 수 없음(세터가 없기 때문)
		
		System.out.println(vo.toString());
		
		
		BuilderVO2 vo2 = BuilderVO2.builder().age(20).name("이순신").build();
		System.out.println(vo2.toString());
	}
	
	
}
