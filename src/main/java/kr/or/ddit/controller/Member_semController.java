//package kr.or.ddit.controller;
//
//import java.util.ArrayList;
//import java.util.Date;
//import java.util.List;
//
//import org.springframework.format.annotation.DateTimeFormat;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.validation.Errors;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestMethod;
//import org.springframework.web.bind.annotation.RequestParam;
//
//import kr.or.ddit.vo.Address;
//import kr.or.ddit.vo.Card;
//import kr.or.ddit.vo.Member;
//import lombok.extern.slf4j.Slf4j;
//
//@Slf4j
//@Controller
//public class Member_semController {
//	
//	/*
//	 * 폼 데이터 양식을 활용한 전체 문제
//	 * - 아래 작성한 메소드가 시작페이지
//	 */
//	@RequestMapping(value="/registerAllForm", method = RequestMethod.GET)
//	public String registerAllForm() {
//		log.info("registerAllForm() 실행...!");
//		return "member/registerAllForm";
//	}
//	
//	// 회원가입 기능을 요청하는 처리부
//	@RequestMapping(value="/registerUser", method=RequestMethod.POST)
//	public String registerUser(Member member, Model model) {
//		// 문제 결과 ) 결과 출력하기 위한 로직 구성
//		String gender = "";
//		if(member.getGender().equals("male")) 
//			gender = "남자";
//		else if(member.getGender().equals("female"))
//			gender = "여자";
//		else
//			gender = "기타";
//		
//		String developer = "일반";
//		if(member.getDeveloper().equals("Y"))
//			developer = "개발자";
//		
//		String nationality = "";
//		if(member.getNationality().equals("Korea"))
//			nationality = "대한민국";
//		if(member.getNationality().equals("Germany"))
//			nationality = "독일";
//		if(member.getNationality().equals("Austrailia"))
//			nationality = "호주";
//		if(member.getNationality().equals("Canada"))
//			nationality = "캐나다";
//		
//		String[] carArray = member.getCarArray();
//		String cars = "";
//		if(carArray != null && carArray.length > 0) {
//			for(String car : carArray) {
//				if(car.equals("volvo"))
//					cars += car.toUpperCase() + " ";
//				if(car.equals("jeep"))
//					cars += car.toUpperCase() + " ";
//				if(car.equals("bmw"))
//					cars += car.toUpperCase() + " ";
//				if(car.equals("audi"))
//					cars += car.toUpperCase() + " ";
//			}
//		}else {
//			cars = "소유차량 없음";
//		}
//		
//		String[] hobbyArray = member.getHobbyArray();
//		String hobby = "";
//		if(hobbyArray != null && hobbyArray.length > 0) {
//			for(String hobbys : hobbyArray) {
//				if(hobbys.equals("Sports"))
//					hobby += "운동 ";
//				if(hobbys.equals("Music"))
//					hobby += "음악감상";
//				if(hobbys.equals("Movie"))
//					hobby += "영화시청 ";
//			}
//		}else {
//			hobby = "취미없음";
//		}
//		
//		// 회원 정보에 새롭게 추가
//		member.setGender(gender);
//		member.setDeveloper(developer);
//		member.setNationality(nationality);
//		member.setCars(cars);
//		member.setHobby(hobby);
//		model.addAttribute("member", member);
//
//		return "member/registerAllResult";
//	}
//}
//
//
