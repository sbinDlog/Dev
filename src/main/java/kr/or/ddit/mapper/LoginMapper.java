package kr.or.ddit.mapper;


import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.web.bind.annotation.RequestParam;

import kr.or.ddit.vo.DDITMemberVO;
import kr.or.ddit.vo.MemberVO;

public interface LoginMapper {

	public DDITMemberVO loginCheck(DDITMemberVO member);

	public DDITMemberVO idCheck(String memId);

	public int signup(DDITMemberVO memberVO);

	public String idFind(DDITMemberVO memberVO);

	//public String pwFind(DDITMemberVO memberVO);

	public String pwFind(Map<String, String> map);

	//시큐리티
	public DDITMemberVO readByUserId(String username);

	public void signupAuth(int memNo);

	
	
}
