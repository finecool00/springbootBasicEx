package com.simple.basic.memo.service;

import java.util.List;

import com.simple.basic.command.MemoVO;

public interface MemoService {

	public void memoRegist(MemoVO vo);
	public List<MemoVO> getMemo();
	public void memoDelete(int mno);
	
}
