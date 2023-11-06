package kr.or.ddit.controller.validation;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import kr.or.ddit.vo.Member;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("/validation")
public class ValidationController {
	/*
	 * 9장 입력 유효성 검증
	 * 
	 * 1. 입력값 검증
	 * - 스프링 MVC는 Bean Validation 기능을 이용해 요청 파라미터 값이 바인딩 된 도메인 클래스(또는 커맨드 클래스)의 입력값 검증을 한다.
	 * 
	 * 
	 * 	# 의존 관계 정의
	 * 	- 입력값 검증을 위한 의존 라이브러리를 추가한다.
	 * 	- pom.xml에서  hibernate-validator 추가
	 * 
	 * 	# 입력값 검증 활성화
	 * 	
	 * 	- Member 클래스로 넘어가 userId, userName에 규칙을 활성화한다.
	 * 	- 입력값 검증을 하기 위해서는 메서드 매개변수에 도메인 클래스를 정의하고 @Validated를 지정한다.
	 * 	- 입력값 검증 대상의 도메인 클래스 직후에 BindingResult를 정의한다.
	 *  - BindingResult에는 요청 데이터의 바인딩 오류와 입력값 검증 오류 정보가 저장된다.
	 *  
	 *  # 환경 설정 순서
	 *  
	 *  	1. 입력값 검증을 위한 의존 라이브러리를 추가한다.
	 *  	2. 입력값 검증 활성화
	 *  		> 활성화를 할 도메인 클래스에다가 @Validation 어노테이션을 지정ㅎ낟.
	 *  	3. 도메인 클래스 내 필도에다가 검증을 위한 어노테이션들로 데이터 검증을 활성화한다.(@NotBlank, @Size 등등)
	 *  	4. 에러를 받을 BindingResult를 설정한다.
	 */
	
	// Validation 테스트할 폼 페이지 컨트롤러 메소드
	@RequestMapping(value="/registerValidationForm01", method=RequestMethod.GET)
	public String registerValidationForm01(Model model) {
		model.addAttribute("member", new Member());
		return "validation/registerValidationForm01";
	}
	
	// @Validated가 붙으면 매개변수 Member에 대해 입력값 검증을 함[데이터누락 @NotBlank @Size(max=3)사이즈가 3보다 크면 에러]
	// BindingResult에 에러정보를 던짐, if문으로 에러정보가 있다면 jsp에서<form:errors>가 작용함
	// validation 처리
	@RequestMapping(value="/result", method=RequestMethod.POST)
	public String registerValidatedForm01Process(@Validated Member member, BindingResult result) {
		log.info("registerValidatedForm01Process() 실행");
		
		if(result.hasErrors()) {
			return "validation/registerValidationForm01";
		}
		
		log.info("userId : "+ member.getUserId());
		log.info("password: "+ member.getPassword());
		log.info("userName : "+ member.getUserName());
		log.info("email : "+ member.getEmail());
		log.info("gender : "+ member.getGender());
		
		return "validation/success"; 
	}
	
	/*
	 * 2. 입력값 검증 결과
	 * - 입력값 검증 대상의 도메인 클래스 직후에 BindingResult를 정의한다.
	 * 
	 * 		# BindingResult에는 요청 데이터의 바인딩 에러와 입력값 검증 에러 정보가 저장된다.
	 * 		1) 에러 정보 확인을 위한 BindingResult 메소드
	 * 
	 * 			hasErrors()
	 * 			- 에러가 발생한 경우 true를 반환한다.
	 * 			hasGlobalErrors()
	 * 			- 객체 레벨의 에러가 발생한 경우 true를 반환한다.
	 * 			hasFieldErrors()
	 * 			- 필드 레벨의 에러가 발생한 경우 true를 반환한다.
	 * 			hasFieldErrors(String)
	 * 			- 인수에 저장한 필드에서 에러가 발생한 경우 true를 반환한다.
	 * 
	 */
	
	// 1) 입력값 검증 대상의 도메인 클래스 직후에 BindingResult를 정의하여 에러정보를 확인할 수 있다.(확인은 처리 메서드에서)
	@RequestMapping(value="/registerValidationForm02", method=RequestMethod.GET)
	public String registerValidationForm02(Model model) {
		model.addAttribute("member", new Member());
		return "validation/registerValidationForm02";
	}
	
	//결과
	@RequestMapping(value="/result2", method= RequestMethod.POST)
	public String registerValidationForm02Process(@Validated Member member, BindingResult result) {
		log.info("registerValidationForm02Process() 실행...!");
		
		//입력값 검증 에러가 발생한 경우 true를 반환합니다.
		log.info("result.hasError() : " + result.hasErrors());
		
		if(result.hasErrors()) {
			List<ObjectError> allErrors = result.getAllErrors();
			List<ObjectError> globalErrors = result.getGlobalErrors();
			List<FieldError> fieldErrors = result.getFieldErrors();
			
			log.info("allErrors.size() : " +allErrors.size());
			log.info("globalErrors.size() : " +globalErrors.size());
			log.info("fieldErrors.size() : " +fieldErrors.size());
			
			for(int i=0; i< allErrors.size(); i++) {
				ObjectError objError = allErrors.get(i);
				log.info("allError :" + objError);
			}
			
			for(int i=0; i< globalErrors.size(); i++) {
				ObjectError objError = globalErrors.get(i);
				log.info("globalErrors :" + objError);
			}
			
			for(int i=0; i< fieldErrors.size(); i++) {
				ObjectError fieldError = fieldErrors.get(i);
				log.info("fieldError :" + fieldError);
				log.info("fieldErrors.getDefaultMessage(): " + fieldError.getDefaultMessage());
			}
			return "validation/registerValidationForm02";
		}
		
		log.info("userId : " + member.getUserId());
		log.info("userName : " + member.getUserName());
		log.info("Email: " + member.getEmail());
		log.info("gender : " + member.getGender());
		
		return "validation/success";
	}
	
	/*
	 * 3. 입력값 검증 규칙 
	 * - 입력값 검증 규칙은 Bean Validation이 제공하는 제약 어노테이션으로 설정한다.
	 * 
	 * 	검사 규칙은 크게 다음 세가지로 분류할 수 있다.
	 * 	- Bean Validation 표준 제약 어노테이션
	 * 	- 서드 파티에서 구현한 제약 어노테이션(서드 파티란, 제 3자에서 만든)
	 * 	- 직접 구현한 제약 어노테이션
	 * 
	 *  1) Member 클래스에서 테스트 위한 어노테이션으로 설정(아래 명시되어 있는 내용들을 가지고 테스트 해보세요!)
	 *  
	 *   	@NotNull					: 빈 값이 아닌지를 검사한다.
	 *   	@Null						: null인지를 검사한다.
	 *   	@NotBlank					: 문자열이 null이 아니고 trim한 길이가 0보다 크다는 것을 검사한다.
	 *   	@NotEmpty					: 문자열이 null이거나 비어있는지 검사한다.
	 *   	@Size						: 글자수나 컬렉션의 요소 개수를 검사한다.
	 *   		> @Size(max=, min)		 
	 * 		@Max(value=)				: value보다 작거나 같은걸 검사한다.
	 * 		@Nin(value=)				: value보다 크거나 같은걸 검사한다.
	 * 		@Email						: 이메일 주소 형식인지를 검사한다.
	 * 		@Past						: 과거 날짜인지를 검사한다.
	 * 		@Future						: 미래 날짜인지를 검사한다. 
	 * 		@Pattern(regexp=)			: CharSequence는 지정된 정규식과 일치해야 하고, 정규식은 Java 정규식 규칙을 따른다.
	 * 		@Positive					: 양수여야 합니다.(0은 에러)
	 * 		@PositiveOrZero				: 양수 또는 0이어야 합니다.
	 * 		@Length						: 문자열의 길이가 min과 max 사이인지 확인합니다.
	 * 
	 * 		그 외에도 정말 많은 어노테이션이 존재하지만, 모든 어노테이션을 다루기가 어려우므로
	 * 		관심있는 분들은 공식 문서를 참고바랍니다.!
	 * 
	 * 	[테스트 시나리오]
	 * - Memeber 클래스의 검증 활성화 추가
	 * 		> userId, password, userName, email, dateOfBirth
	 * - /validation/registerValidationForm02 URL로 요청해서 테스트를 진행한다!
	 * 
	 */
	
	/*
	 * 4. 중첩된 자바빈즈 입력값 검증
	 * - 중첩된 자바빈즈와 자바빈즈의 컬렉션에서 정의한 프로퍼티에 대해 입력값 검증을 할 때는 @Valid를 지정한다.
	 * 
	 * 	1) 중첩된 자바빈즈 클래스를 정의하고 @Valid를 지정한다.
	 * 		- Member 클래스 내 Address address 필드에  @Valid 어노테이션 지정
	 * 		- Member 클래스 내 List<Card> cardList 필드에   @Valid 어노테이션 지정
	 * 
	 * 	2) Address 클래스에도 Validation을 설정한다.
	 * 		> 입력값 규칙 활성화 
	 *	
	 *	3) Card 클래스에도  Validation을 설정한다.
	 *		> 입력값 규칙 활성화 
	 *
	 */
	
	@RequestMapping(value="/registerValidationForm03", method=RequestMethod.GET)
	public String registerValidationForm03(Model model) {
		log.info("registerValidationForm03() 실행!!");
		model.addAttribute("member", new Member());
		return "validation/registerValidationForm03";
	}
	
	//결과
	@RequestMapping(value="/result3", method=RequestMethod.POST)
	public String registerFormValidationResult3(@Validated Member member, BindingResult result) {
		log.info("registerFormValidationResult3() 실행..!");
		
		if(result.hasErrors()) {
			return "validation/registerValidationForm03";
		}
		
		return "validation/success";
	}
}
