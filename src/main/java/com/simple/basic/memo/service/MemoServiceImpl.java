package com.simple.basic.memo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.simple.basic.command.MemoVO;

@Service("memoService")
public class MemoServiceImpl implements MemoService {
	
	@Autowired
	private MemoMapper memoMapper;

	@Override
	public void memoRegist(MemoVO vo) {
	
		memoMapper.memoRegist(vo);
	}

	@Override
	public List<MemoVO> getMemo() {
		
		return memoMapper.getMemo();
	}

	@Override
	public void memoDelete(int mno) {
		
		memoMapper.memoDelete(mno);
		
	}
}
