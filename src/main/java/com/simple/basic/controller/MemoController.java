package com.simple.basic.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.simple.basic.command.MemoVO;
import com.simple.basic.memo.service.MemoService;

@Controller
@RequestMapping("/memo")
public class MemoController {

	//메모서비스 인터페이스 선언 및 서비스와 연결
	@Autowired
	@Qualifier("memoService")
	private MemoService memoService;
	
	
	//목록 화면
	@GetMapping("/memoList")
	public String memoList(Model model) {
		
		List<MemoVO> list = memoService.getMemo();
		model.addAttribute("list", list);
		
		return "memo/memoList";
	}
	
	//글쓰기 화면
	@GetMapping("/memoWrite")
	public String memoWrite() {
		return "memo/memoWrite";
	}
	
	//글쓰기 등록
	@PostMapping("/memoForm")
	public String memoForm(@Valid @ModelAttribute("vo") MemoVO vo, Errors errors, Model model) {
		
		if(errors.hasErrors()) {
			
			List<FieldError> list = errors.getFieldErrors();
			
			for(FieldError err : list) {
//				if(err.isBindingFailure()) {
//					model.addAttribute("valid_" + err.getField(), "입력형식을 지켜주세요");
//				} else {
//					model.addAttribute("valid_" + err.getField(), err.getDefaultMessage());
//				}
					model.addAttribute("valid_" + err.getField(), err.getDefaultMessage());
					
			}
			return "memo/memoWrite";
		
		} else {
			memoService.memoRegist(vo);
		}
		return "redirect:/memo/memoList";
					
	}
	
	
	@GetMapping("/memoDelete")
	public String memoDelete(@RequestParam("mno") int mno) {
		
		memoService.memoDelete(mno);
		
		return "redirect:/memo/memoList";
	}
	
}
