package com.simple.basic.controller;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.simple.basic.command.SimpleVO;

@RestController //@ResponseBody + @Controller
public class RestBasicController {

	@GetMapping("/basic")
	public String basic() { 
		return "<h3>hello world</h3>";
	}
	
	//데이터를 보내는 방법 -> @ResponseBody = 자바의 객체를 JSON형식으로 자동 변환
	@GetMapping("/getObject")
	public SimpleVO getObject() {
		
		SimpleVO vo = new SimpleVO(1, "홍", "길동", LocalDateTime.now());
		
		return vo;
	}
	
	@GetMapping("/getMap")
	public Map<String, Object> getMap() {
		
		Map<String, Object> map = new HashMap<>();
		map.put("name", "홍길동");
		
		return map;
	}
	
	@GetMapping("/getList")
	public List<SimpleVO> getList() {
		
		List<SimpleVO> list = new ArrayList<>();
		list.add(new SimpleVO(1, "홍", "길동", LocalDateTime.now()));
		list.add(new SimpleVO(2, "이", "길동", LocalDateTime.now()));
		
		return list;
	}
	
	
	//데이터를 받는 방법
	//get방식 => 쿼리스트링 or 쿼리파라미터 이용해서 주소에 담아서 넘김
	//post방식 => 폼형식 or JSON을 이용해서 body에 담아서 넘김
	@GetMapping("/getData")
	public SimpleVO getData(SimpleVO vo) {
		
		System.out.println(vo.toString());
		
		return new SimpleVO(2, "이", "길동", LocalDateTime.now());
	}
	
	
	//http://localhost:8181/getData2?sno=1&first=홍길동
	//sno, first는 필수값이 됩니다!!
	@GetMapping("/getData2")
	public SimpleVO getData2(@RequestParam("sno") int sno,
							 @RequestParam("first") String first) {
		
		System.out.println(sno);
		System.out.println(first);
		
		return new SimpleVO(2, "이", "길동", LocalDateTime.now());
	}

	//http://localhost:8181/getData3/1/홍길동
	@GetMapping("/getData3/{sno}/{first}")
	public SimpleVO getData3(@PathVariable("sno") int sno,
							 @PathVariable("first") String first) {
		
		System.out.println(sno);
		System.out.println(first);
		
		return new SimpleVO(2, "이", "길동", LocalDateTime.now());
	}
	
	////////////////////////////////////////////////////////////////////
	//post방식의 데이터 받기
	
	//보내는 입장에서 폼형식의 데이터를 써줘야함(map으로는 값이 안 받아짐...)
	@PostMapping("/getForm")
	public SimpleVO getForm(SimpleVO vo) {
		
		System.out.println(vo.toString());
		
		return new SimpleVO(2, "이", "길동", LocalDateTime.now());
	}
	
	//보내는 입장에서 JSON형식의 데이터를 써줘야함(@RequestBody를 붙여서 JSON형식을 자동변환 해줘야함)
	//{"sno":"1", "first":"홍길동", "last":"이순신"}
	@PostMapping("/getJSON")
	public SimpleVO getJSON(@RequestBody SimpleVO vo) {
		
		System.out.println(vo.toString());
		
		return new SimpleVO(2, "이", "길동", LocalDateTime.now());
	}
	//JSON은 맵으로도 데이터 받을 수 있음!
	@PostMapping("/getJSON2")
	public SimpleVO getJSON2(@RequestBody Map<String, Object> map) {
		
		System.out.println(map.toString());
		
		return new SimpleVO(2, "이", "길동", LocalDateTime.now());
	}
	
	/////////////////////////////////////////////////////////////////////////////
	//consumer = 반드시 oo타입으로 보내라!!고 명시
	//producer = 내가 oo타입으로 줄게!라고 명시(default = JSON), xml파일을 사용하려면 xml데이터바인딩 라이브러리 필요
	
	//보내는 타입은 text로 줄게(producer), 너는 json데이터로 보내줘(consumer)!
	@PostMapping(value = "/getResult", produces="text/plain", consumes="application/json")
	public String getResult(@RequestBody String str) {
		
		System.out.println(str);
		
		return "<h3>문자열</h3>";
	}
	
	
	//응답문서 직접 작성하기(중요!) = ResponseEntity<보낼데이터 타입>
	@PostMapping("/createResponse")
	public ResponseEntity<SimpleVO> createResponse() {
		
		//0번째
		SimpleVO vo = new SimpleVO(1, "홍", "길동", LocalDateTime.now());
		
		//1st
		//ResponseEntity<SimpleVO> result = new ResponseEntity<>(vo, HttpStatus.OK); //데이터, 상태코드
		
		//2nd
		//헤더에 대한 내용 정의
		HttpHeaders header = new HttpHeaders();
		header.add("Authorization", "JSON WEB TOKEN~~");
		header.add("Content-type","application/json");
		header.add("Access-Control-Allow-origin", "*");
		
		ResponseEntity<SimpleVO> result = new ResponseEntity<SimpleVO>(vo, header, HttpStatus.OK); //데이터, 헤더, 상태코드
		
		return result;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////
	/*
	 클라이언트 fetch
	 요청주소 : /api/v1/getData
	 메서드 : get
	 클라이언트에서 보낼데이터는 : num, name
	 서버에서 보낼 데이터는 : SimpleVO
	 받을 수 있는 restAPI 생성
	*/
	@CrossOrigin("*")
	@GetMapping("/api/v1/getData")
	public ResponseEntity<SimpleVO> getData(@RequestParam("num") int num,
											@RequestParam("name") String name) {
		
		System.out.println("num : " + num); //데이터베이스로~!!
		System.out.println("name : " + name);
		
		return new ResponseEntity<>(new SimpleVO(1, "홍길동", "아자아자아자자", LocalDateTime.now()), HttpStatus.OK);
	}
	
	
	/*
	 클라이언트 fetch
	 요청주소 : /api/v1/getInfo
	 클라이언트에서 보낼데이터는 : num, name
	 서버에서 보낼데이터는 : 리스트<SimpleVO>
	 받을 수 있는 restAPI 생성
	 ResponseEntity로 응답
	*/
	
//	@PostMapping("/api/v1/getInfo")
//	public ResponseEntity<List<SimpleVO>> getInfo() {
//		
//		List<SimpleVO> list = new ArrayList<>();
//		list.add(new SimpleVO(1, "홍", "성공", LocalDateTime.now()));
//		list.add(new SimpleVO(2, "홍", "달성", LocalDateTime.now()));
//		
//		HttpHeaders header = new HttpHeaders();
//		header.add("Authorization", "JSON WEB TOKEN..!");
//		header.add("Content-type", "application/json");
//		header.add("Access-Control-Allow-origin", "*");
//		
//		ResponseEntity<List<SimpleVO>> result = new ResponseEntity<>(list, header, HttpStatus.OK);
//		
//		return result;
//	}
	
	//@CrossOrigin("http://localhost:3000")
	@CrossOrigin("*")
	@PostMapping("/api/v1/getInfo")
	public ResponseEntity<List<SimpleVO>> getInfo(@RequestBody Map<String, Object> map) {
		
		System.out.println(map.toString());
		
		List<SimpleVO> list = new ArrayList<>();
		list.add(new SimpleVO(1, "홍", "성공", LocalDateTime.now()));
		list.add(new SimpleVO(2, "홍", "달성", LocalDateTime.now()));
		
		
		ResponseEntity<List<SimpleVO>> result = new ResponseEntity<>(list, HttpStatus.OK);
		
		return result;
	}
}
