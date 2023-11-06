package kr.or.ddit.security;

import javax.inject.Inject;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import kr.or.ddit.mapper.LoginMapper;
import kr.or.ddit.mapper.MemberMapper;
import kr.or.ddit.vo.CustomUser;
import kr.or.ddit.vo.DDITMemberVO;
import kr.or.ddit.vo.MemberVO;
import lombok.extern.slf4j.Slf4j;


@Slf4j
public class CustomUserDetailsService implements UserDetailsService{

	@Inject
	private LoginMapper loginMapper;
	
	@Override							//username : 회원 아이디를 뜻함
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		log.info("loadUserByUsername() 실행..!");
		
		DDITMemberVO member;
		try {
			member = loginMapper.readByUserId(username);
			log.info("queried by member mapper : " + member);
			return member == null ? null : new CustomUser(member);
			// null이면 null을 반환하고 아니면  new CustomUser객체
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
