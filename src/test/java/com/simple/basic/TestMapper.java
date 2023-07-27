package com.simple.basic;

import org.apache.ibatis.annotations.Mapper;

@Mapper //마이바티스로 사용할 인터페이스에 @Mapper 붙임
public interface TestMapper {

	String getTime();

}
