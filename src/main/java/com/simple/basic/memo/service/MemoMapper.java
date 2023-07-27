package com.simple.basic.memo.service;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.simple.basic.command.MemoVO;

@Mapper
public interface MemoMapper {

	public void memoRegist(MemoVO vo);
	public List<MemoVO> getMemo();
	public void memoDelete(int mno);
}
