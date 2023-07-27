package com.simple.basic.command;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
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
public class ValidVO {
	
	/*
	 * @NotNull = null 허용x, (String, Integer, Double등등 적용)
	 * @NotEmpty = null 허용x, 공백 허용x (String 적용)
	 * @NotBlank = null, 공백, 화이트스페이스 허용x(String 적용)
	 * 
	 * @Pattern = 정규표현식에 일치하지 않으면 err
	 */
	
	
	//기본타입 => 래퍼타입으로 작성할 것
	//예 : int형일 때는 값이 공백일 때, parseInt 시 에러 발생 가능
	//Integer라면 null값으로 인식함
	
	@NotEmpty(message = "이름은 필수입니다")
	private String name;
	
	@NotNull(message = "급여는 숫자로 입력하세요") //숫자 검사시 NotNull 사용 가능
	private Integer salary;
	
	@Pattern(message = "000-0000-0000형식이어야 합니다", regexp = "[0-9]{3}-[0-9]{4}-[0-9]{4}")
	private String phone;
	
	@NotBlank(message = "공백일 수 없습니다")
	@Email(message = "이메일 형식이어야 합니다") //이메일은 공백이 통과됨
	private String email;
	
}
