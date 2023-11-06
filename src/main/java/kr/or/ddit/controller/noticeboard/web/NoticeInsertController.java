package kr.or.ddit.controller.noticeboard.web;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;
import javax.servlet.ServletContainerInitializer;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import kr.or.ddit.ServiceResult;
import kr.or.ddit.controller.noticeboard.service.INoticeService;
import kr.or.ddit.controller.noticeboard.service.NoticeServiceImpl;
import kr.or.ddit.vo.DDITMemberVO;
import kr.or.ddit.vo.NoticeVO;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("/notice")
public class NoticeInsertController {

	//definition안에 notice/* 로 시작하면서 모든 것 --->  notice/form 와 {1}이 연결되어 from.jsp를 찾음
	
	@Inject
	private INoticeService noticeService;
	
	@RequestMapping("/form.do")
	public String noticeForm() {
		return "notice/form";
	}
	
	@RequestMapping(value="/insert.do", method= RequestMethod.POST)
	public String noticeInsert(
			HttpServletRequest req, 
			RedirectAttributes ra,
			HttpSession session,
			NoticeVO noticeVO, Model model) {
		
		String goPage="";
		Map<String, String> errors = new HashMap<String, String>();
		if(StringUtils.isBlank(noticeVO.getBoTitle())) {
			errors.put("boTitle", "제목을 입력해주세요");
		}
		if(StringUtils.isBlank(noticeVO.getBoContent())) {
			errors.put("boContent", "내용을 입력해주세요");
		}
		if(errors.size() > 0) {	//에러발생
			model.addAttribute("errors", errors);
			model.addAttribute("noticeVO", noticeVO);
			goPage = "notice/form";
		}else {
			// 방법1 - 일반적인 session 처리 시, 사용
//			DDITMemberVO memberVO = (DDITMemberVO)session.getAttribute("SessionInfo");
			
			
//			if(memberVO != null) { 	
				// 로그인 후 등록된 세션 정보(로그인 한 회원 정보가 들어있음)
				// 회원 정보 중, id를 작성자로 셋팅
		
				//방법 2 - 스프링 시큐리티를 이용한 사용자명 처리
				User user = (User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
				//noticeVO.setBoWriter(memberVO.getMemId());
				noticeVO.setBoWriter(user.getUsername());
				
				ServiceResult result = noticeService.insertNotice(req, noticeVO);
				if(result.equals(ServiceResult.OK)) {
					goPage = "redirect:/notice/detail.do?boNo="+ noticeVO.getBoNo();
				}else {
					model.addAttribute("message", "서버 에러, 다시 시도해주세요");
					goPage ="notice/form";
				}
				
//			}else {		//로그인이 안되었다면
//				ra.addFlashAttribute("message", "로그인 후에 사용 가능합니다!");
//				goPage ="redirect:/notice/login.do";
//			}
		}
		return goPage;
	}
	
}
