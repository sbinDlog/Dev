package kr.or.ddit.controller;

import java.io.Reader;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import kr.or.ddit.vo.Member;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class ModelAttributeMemberController {

	/*
	 * 3. @ModelAttribute 어노테이션
	 */
	// 테스트를 위한 페이지
	@RequestMapping(value="/modelattribute/registerForm", method= RequestMethod.GET)
	public String registerForm() {
		log.info("register()실행...!");
		return "member/registerModelAttributeForm";
	}
	
	//jsp에 값이 안넘어감
	@RequestMapping(value= "/modelattribute/register01", method= RequestMethod.POST)
	public String register01(String userId) {
		log.info("register01()실행..!");
		log.info("userId :" + userId);
		return "result";
	}
	
	// 2) 기본 자료형인 매개변수에 @ModelAttribute 어노테이션을 지정하여 데이터를 전달한다.
	@RequestMapping(value= "/modelattribute/register02", method= RequestMethod.POST)
	public String register02(@ModelAttribute("userId") String userId) {
		log.info("register02()실행..!");
		log.info("userId :" + userId);
		return "result";
	}
	
	//3) 기본 자료형인 매개변수가 여러 개인 경우에 각각의 매개변수에 @ModelAttribute 어노테이션을 지정하여 데이터를 전달한다.
	@RequestMapping(value= "/modelattribute/register03", method= RequestMethod.POST)
	public String register03(@ModelAttribute("userId") String userId, 
							@ModelAttribute("password") String password) {
		log.info("register03()실행...!");
		log.info("userId : " +userId);
		log.info("password : " +password);
		return "result";
	}
	
	//4) 자바빈즈 규칙에 맞는 객체는 매개변수로 선언하면 기본적으로 전달된다.
	@RequestMapping(value= "/modelattribute/register04", method= RequestMethod.POST)
	public String register04(Member member) { //key : (memeber 소문자).address.userId
		log.info("register04()실행...!");	
		log.info("userId : " +member.getUserId());
		log.info("password : " +member.getPassword());
		log.info("postCode : " +member.getAddress().getPostCode());
		log.info("location : " +member.getAddress().getLocation());
		return "result";
	}
	
}
