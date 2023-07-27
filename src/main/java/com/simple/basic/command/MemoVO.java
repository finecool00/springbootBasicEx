package com.simple.basic.command;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MemoVO {
	
	private Integer mno;
	
	@Pattern(message="5글자 이상 작성하세요", regexp="[a-zA-z0-9가-힣]{5,}")
	private String memo;
	
	@Pattern(message="형식(예:010-1234-5678)에 맞춰 작성하세요", regexp="[0-9]{3}-[0-9]{4}-[0-9]{4}")
	private String phone;
	
	@Pattern(message="숫자 4자리 비밀번호를 입력하세요", regexp="[0-9]{4}")
	private String pw;
	
	private String secret;
	
}
