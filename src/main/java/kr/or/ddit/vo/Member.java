package kr.or.ddit.vo;

import java.util.Date;
import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.Past;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;

@Data
public class Member {

	// 문자열이 null이 아니고 trim한 길이가 0보다 크다는 것을 검사
	@NotBlank(message = "태원이가 알립니다! 입력이 누락되었수!")
	private String userId = "a001";
	
	// 문자열이 null이 아니고 trim한 길이가 0보다 크다는 것을 검사
	@NotBlank
	@Size(max=3)
	private String userName = "hongkd";
	
	// 문자열이 null이 아니고 trim한 길이가 0보다 크다는 것을 검사
	@NotBlank
	private String password = "1234";
	private int coin=100;
	
	//과거 날짜인지를 검사한다.
	@Past
	@DateTimeFormat(pattern= "yyyy-MM-dd")
	private Date dateOfBirth;
	
	@Email
	private String email;
	private String gender;
	private String hobby;
	private String[] hobbyArray;
	private List<String> hobbyList;
	private boolean foreigner;
	private String developer;
	private String nationality;
	
	
	// 중첩된 자바빈즈의 입력값 검증을 지정한다.
	@Valid
	private Address address;
	//자바빈즈 컬렉션의 입력값 검증을 지정한다.
	@Valid
	private List<Card> cardList;
	
	private String cars;
	private String[] carArray;
	private List<String> carList;
	
	private String introduction;
	private String birthDay;
}
