package com.simple.basic.controller;


import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.simple.basic.command.UploadVO;

@Controller
@RequestMapping("/upload")
public class UploadController {
	
	@Value("${project.upload.path}")
	private String uploadPath;
	
	//폴더생성함수
	public String makeFolder() {
		//오늘날짜 구하기(원하는 형식으로 formatting~!)
		String path = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));
		
		//오늘날짜로 된 날짜폴더를 만든다
		File file = new File(uploadPath + "/" + path);
		
		if(file.exists() == false) { //존재하면 true, 존재하지 않으면 false
			file.mkdirs(); //mkdirs() : 폴더만드는 메서드
		}
		
		return path; //날짜폴더명 반환.. 파일경로 만들 때 필요함
	}
	
	//화면 구성
	@GetMapping("/upload")
	public String upload() {
		return "upload/upload";
	}
	
	//파일데이터는 MultipartFile객체로 받습니다.
	@PostMapping("/upload_ok")
	public String upload_ok(@RequestParam("file") MultipartFile file) {
		
		//step1. 파일 이름을 받습니다
		String originName = file.getOriginalFilename();
		
		//step2. 브라우저별로 파일의 경로가 다를 수 있기 때문에 \\ 기준으로 파일명만 잘라서 다시 저장
		String fileName = originName.substring(originName.lastIndexOf("\\") + 1);
		
		//step3. 파일 사이즈
		long size = file.getSize();
		
		//step4. 동일한 파일을 재업로드시 기존파일 덮어버리기 때문에, 난수이름으로 파일명을 바꿔서 올림..이건 그 난수번호!
		String uuid = UUID.randomUUID().toString();
		
		//step5. 날짜별로 폴더생성(새 폴더 만들고 폴더명(filePath) 얻음)
		String filePath = makeFolder();
		
		
		//step6. 세이브할 경로 조합
		String savePath = uploadPath + "/" + filePath + "/" + uuid + "_" + fileName;
		
//		System.out.println(originName);
//		System.out.println(size);
		//데이터베이스 추후에 저장할 것임 
		System.out.println("실제파일명: " + fileName);
		System.out.println("난수값: " + uuid);
		System.out.println("날짜폴더경로: " + filePath);
		System.out.println("세이브할 경로:" + savePath);
		
		//step7. 파일 업로드(세이브 경로의 파일을 만든 후 transferTo()를 통해 변환!)
		try {
			File saveFile = new File(savePath);
			file.transferTo(saveFile); //파일 업로드 진행(에러날 수 있으므로 try~catch구문 사용하자)
		} catch (Exception e) {
			System.out.println("파일업로드 중 에러발생");
			e.printStackTrace();
		}
		
		return "upload/upload_ok";
	}
	
	
	//복수태그를 사용한 다중파일 업로드 - List(MultipartFile)
	@PostMapping("/upload_ok2")
	public String upload_ok2(@RequestParam("file") List<MultipartFile> list) {
		
		//파일을 올리지 않는 태그에는 빈 file객체가 올라가는데 이를 제외한 새로운 리스트를 만들어 빈 객체 생성을 방지함
		//=> filter를 통해 빈 file객체는 제외, 새로운 리스트 생성(파일 업로드한 것만! 리스트 만들어서 덮는다고 생각!?!)
		list = list.stream().filter((f) -> f.isEmpty() == false).collect(Collectors.toList());
		
		for(MultipartFile file : list) { //리스트 요소들을 파일형식으로 꺼내서 업로드!
			
			//System.out.println(file.isEmpty()); //이걸로 파일이 비었는지 여부 확인
			
			//파일 이름을 받습니다
			String originName = file.getOriginalFilename();
			
			//브라우저별로 파일의 경로가 다를 수 있기 때문에 \\ 기준으로 파일명만 잘라서 다시 저장
			String fileName = originName.substring(originName.lastIndexOf("\\") + 1);
			
			//파일 사이즈
			long size = file.getSize();
			
			//동일한 파일을 재업로드시 기존파일 덮어버리기 때문에, 난수이름으로 파일명을 바꿔서 올림
			String uuid = UUID.randomUUID().toString();
			
			//날짜별로 폴더생성
			String filePath = makeFolder();
			
			
			//세이브할 경로
			String savePath = uploadPath + "/" + filePath + "/" + uuid + "_" + fileName;
			
//			System.out.println(originName);
//			System.out.println(size);
			//데이터베이스 추후에 저장할 것임 
			System.out.println("실제파일명: " + fileName);
			System.out.println("난수값: " + uuid);
			System.out.println("날짜폴더경로: " + filePath);
			System.out.println("세이브할 경로:" + savePath);
			
			try {
				File saveFile = new File(savePath);
				file.transferTo(saveFile); //파일 업로드 진행(에러날 수 있으므로 try~catch구문 사용하자)
			} catch (Exception e) {
				System.out.println("파일업로드 중 에러발생");
				e.printStackTrace();
			}
			
		}
		return "upload/upload_ok";
	}
	
	
	//multiple 옵션을 사용한 다중파일 업로드
	@PostMapping("/upload_ok3")
	public String upload_ok3(MultipartHttpServletRequest files) {
		
		List<MultipartFile> list = files.getFiles("file"); //클라이언트 input name값 (클라이언트에서 요청한 파일들)
		
		for(MultipartFile file : list) {
			
			//System.out.println(file.isEmpty()); //이걸로 파일이 비었는지 아닌지 걸러냄
			
			//파일 이름을 받습니다
			String originName = file.getOriginalFilename();
			
			//브라우저별로 파일의 경로가 다를 수 있기 때문에 \\ 기준으로 파일명만 잘라서 다시 저장
			String fileName = originName.substring(originName.lastIndexOf("\\") + 1);
			
			//파일 사이즈
			long size = file.getSize();
			
			//동일한 파일을 재업로드시 기존파일 덮어버리기 때문에, 난수이름으로 파일명을 바꿔서 올림
			String uuid = UUID.randomUUID().toString();
			
			//날짜별로 폴더생성
			String filePath = makeFolder();
			
			
			//세이브할 경로
			String savePath = uploadPath + "/" + filePath + "/" + uuid + "_" + fileName;
			
//			System.out.println(originName);
//			System.out.println(size);
			//데이터베이스 추후에 저장할 것임 
			System.out.println("실제파일명: " + fileName);
			System.out.println("난수값: " + uuid);
			System.out.println("날짜폴더경로: " + filePath);
			System.out.println("세이브할 경로:" + savePath);
			
			try {
				File saveFile = new File(savePath);
				file.transferTo(saveFile); //파일 업로드 진행(에러날 수 있으므로 try~catch구문 사용하자)
			} catch (Exception e) {
				System.out.println("파일업로드 중 에러발생");
				e.printStackTrace();
			}
		
		}
		return "upload/upload_ok";
			
	}
	
	
	
	//비동기방식으로 받기
	@PostMapping("/upload_ok4")
	public @ResponseBody ResponseEntity<String> upload_ok4(UploadVO vo) {
		
		MultipartFile file = vo.getFile(); //getter
		
		//파일 이름을 받습니다
		String originName = file.getOriginalFilename();
		
		//브라우저별로 파일의 경로가 다를 수 있기 때문에 \\ 기준으로 파일명만 잘라서 다시 저장
		String fileName = originName.substring(originName.lastIndexOf("\\") + 1);
		
		//파일 사이즈
		long size = file.getSize();
		
		//동일한 파일을 재업로드시 기존파일 덮어버리기 때문에, 난수이름으로 파일명을 바꿔서 올림
		String uuid = UUID.randomUUID().toString();
		
		//날짜별로 폴더생성
		String filePath = makeFolder();
		
		
		//세이브할 경로
		String savePath = uploadPath + "/" + filePath + "/" + uuid + "_" + fileName;
		
//		System.out.println(originName);
//		System.out.println(size);
		//데이터베이스 추후에 저장할 것임 
		System.out.println("실제파일명: " + fileName);
		System.out.println("난수값: " + uuid);
		System.out.println("날짜폴더경로: " + filePath);
		System.out.println("세이브할 경로:" + savePath);
		
		try {
			File saveFile = new File(savePath);
			file.transferTo(saveFile); //파일 업로드 진행(에러날 수 있으므로 try~catch구문 사용하자)
		} catch (Exception e) {
			System.out.println("파일업로드 중 에러발생");
			e.printStackTrace();
		}
		
		
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	
	//이미지 띄워주기
	
	@GetMapping("/display")
	public @ResponseBody byte[] display(@RequestParam("filename") String filename,
										@RequestParam("filepath") String filepath,
										@RequestParam("uuid") String uuid) {
		
		//파일을 읽어올 경로
		String path = uploadPath + "/" + filepath + "/" + uuid + "_" + filename;
		
		byte[] data = null;
		
		try {
			data = FileCopyUtils.copyToByteArray(new File(path)); //이미지 경로를 바이트배열로 구함
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return data;
	}
	
}
