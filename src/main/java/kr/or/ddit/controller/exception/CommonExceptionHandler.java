package kr.or.ddit.controller.exception;

import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.NoHandlerFoundException;

import lombok.extern.slf4j.Slf4j;

@Slf4j
// 예외처리할 때 사용하는 클래스
//@ControllerAdvice
public class CommonExceptionHandler {

	
	// 수정버튼을 눌렀을 때 발생하는 오류는 Exception 계열의 오류가 아니라서 예외처리가 안됨
	@ExceptionHandler(Exception.class)
	public String handler(Exception e, Model model) {
		log.info("handler()실행...!");
		log.info("Exception Info :" + e.toString());
		
		model.addAttribute("exception", e);
		return "error/errorCommon";
	}
	
	// 404 에러 페이지 처리
	// web.xml에서 NoHandlerFoundException 처리 활성화 true 설정하고 왔나요??
	@ExceptionHandler(NoHandlerFoundException.class)
	@ResponseStatus(value = HttpStatus.NOT_FOUND)
	public String handel404(Exception e) {
		log.info("Exception : " + e);
		return "error/custom404";
		
	}
}


