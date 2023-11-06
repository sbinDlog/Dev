package kr.or.ddit.controller.noticeboard.service;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import kr.or.ddit.ServiceResult;
import kr.or.ddit.controller.noticeboard.web.TelegramSendController;
import kr.or.ddit.mapper.LoginMapper;
import kr.or.ddit.mapper.NoticeMapper;
import kr.or.ddit.mapper.ProfileMapper;
import kr.or.ddit.vo.DDITMemberVO;
import kr.or.ddit.vo.MemberVO;
import kr.or.ddit.vo.NoticeFileVO;
import kr.or.ddit.vo.NoticeVO;
import kr.or.ddit.vo.PaginationInfoVO;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class NoticeServiceImpl implements INoticeService {

	@Inject
	private LoginMapper loginMapper;
	
	@Inject
	private NoticeMapper noticeMapper;

	@Inject
	private ProfileMapper profileMapper;
	
	@Inject
	private PasswordEncoder pe;
	
	private TelegramSendController tst = new TelegramSendController();
	
	
	@Override
	public DDITMemberVO loginCheck(DDITMemberVO member) {
		return loginMapper.loginCheck(member);
	}

	@Override
	public ServiceResult idCheck(String memId) {
		ServiceResult result = null;
		
		DDITMemberVO member = loginMapper.idCheck(memId);
		if(member != null) {
			result = ServiceResult.EXIST;
		}else {
			result = ServiceResult.NOTEXIST;
		}
		return result;
	}

	@Override
	public ServiceResult signup(HttpServletRequest req, DDITMemberVO memberVO) {
		ServiceResult result =null;
		
		String uploadPath = req.getServletContext().getRealPath("/resources/profile");
		File file = new File(uploadPath);
		
		if(!file.exists()) {
			file.mkdirs();
		}
		
		String proFileImg = "";		//회원 정보에 추가될 프로필 이미지 경로
		try {
			MultipartFile profileImgFile = memberVO.getImgFile();
			if(profileImgFile.getOriginalFilename() != null &&
					!profileImgFile.getOriginalFilename().equals("")) {
				
				String fileName = UUID.randomUUID().toString();		//UUID 파일명 생성 
				fileName += "_" + profileImgFile.getOriginalFilename();		//UUID_원본파일명
				uploadPath += "/" + fileName;		//최종 업로드하기 위한 파일 경로
				profileImgFile.transferTo(new File(uploadPath));  	// 해당 위치 경로에 파일 복사
				proFileImg = "/resources/profile/" + fileName;		// 파일 복사가 일어난 파일의 위치로 접근하기 위한 URI 설정
			}
				memberVO.setMemProfileImg(proFileImg);	
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		memberVO.setMemPw(pe.encode(memberVO.getMemPw()));
		
		int status = loginMapper.signup(memberVO);
		if(status > 0) {
			loginMapper.signupAuth(memberVO.getMemNo());
			result = ServiceResult.OK;
		}else {
			result = ServiceResult.FAILED;
		}
		return result;
	}

	
	@Override
	public int selectNoticeCount(PaginationInfoVO<NoticeVO> pagingVO) {
 		return noticeMapper.selectNoticeCount(pagingVO);
	}

	@Override
	public List<NoticeVO> selectNoticeList(PaginationInfoVO<NoticeVO> pagingVO) {
 		return noticeMapper.selectNoticeList(pagingVO);
	}

	@Override
	public ServiceResult insertNotice(HttpServletRequest req, NoticeVO noticeVO) {
 		
		ServiceResult result = null;
		int status = noticeMapper.insertNotice(noticeVO);
		
//		AlramVO alarmVO = new AlramVO();
//		alarmVO.setBoNo(noticeVO.getBoNo());
//		log.info("noticeVO.getBoNo() : "+ noticeVO.getBoNo());
//		//alarmVO : AlramVO(aNo=0, boNo=47)
//		log.info("alarmVO : " + alarmVO);
//		alarmMapper.insertAlram(alarmVO);
		
		if(status > 0 ) {	// 게시물이 등록되었다면!
			List<NoticeFileVO> noticeFileList = noticeVO.getNoticeFileList();
			try {
				// 공지사항 파일 업로드 처리 함수
				// 공통적인 소스가 반복되므로 함수로 모듈화를 진행하여 사용한다.
				// 여러 컨트롤러 메소드에서 사용될 수 있으므로
				noticeFileUpload(noticeFileList, noticeVO.getBoNo(), req);
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			// 게시글이 등록되면 알람도 같이 등록이 되어야 함!!
			
//			try {
//				tst.sendGet("뚭", noticeVO.getBoTitle());
//			} catch (IOException e) {
//				e.printStackTrace();
//			}
			

			result = ServiceResult.OK;
		}else {
			result = ServiceResult.FAILED;
		}
		return result;
	}

	@Override
	public NoticeVO selectNotice(int boNo) {
 		noticeMapper.incrementHit(boNo);
		return noticeMapper.selectNotice(boNo);
	}

	@Override
	public ServiceResult updateNotice(HttpServletRequest req, NoticeVO noticeVO) {
		ServiceResult result = null;
		int status = noticeMapper.updateNotice(noticeVO);
		
		if(status > 0) {
			List<NoticeFileVO> noticeFileList = noticeVO.getNoticeFileList();
			
			try {
				// 공지사항 파일 업로드
				noticeFileUpload(noticeFileList, noticeVO.getBoNo(), req);
				
				// 기존에 등록 되어 있는 파일 목록들 중, 수정하기 위해서 x버튼을 눌러 삭제 처리로 넘겨준 파일 번호들
				Integer[] delNoticeNo = noticeVO.getDelNoticeNo();
				if(delNoticeNo != null) {
					for(int i=0; i < delNoticeNo.length; i++) {
						NoticeFileVO noticeFileVO = noticeMapper.selectNoticeFile(delNoticeNo[i]);
						noticeMapper.deleteNoticeFile(delNoticeNo[i]);	//파일번호에 해당하는 파일 데이터를 삭제
						File file = new File(noticeFileVO.getFileSavepath());
						file.delete();  	// 기존 파일이 업로드되어 있던 경로에 파일 삭제
					}
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
			result = ServiceResult.OK;
		}else {
			result = ServiceResult.FAILED;
		}
		return result;
	}

	@Override
	public ServiceResult deleteNotice(HttpServletRequest req, int boNo) {
		ServiceResult result = null;
		// 공지사항 파일 데이터를 삭제하기 위한 준비
		NoticeVO notice = noticeMapper.selectNotice(boNo);		// 게시글 번호에 해당하는 공지사항 게시글 정보 가져오기
		noticeMapper.deleteNoticeFileByNo(boNo);				// 게시글 번호에 해당하는 파일 데이터 삭제
		
		int status = noticeMapper.deleteNotice(boNo);
		
		if(status > 0) {
			List<NoticeFileVO> noticeFileList = notice.getNoticeFileList();		//공지사항 게시글 정보에서 파일 목록 가져오기
			try {
				if(noticeFileList != null && noticeFileList.size() > 0 ) {
					// [0] = D:\99.Class\02.SPRING2\workspace_spring2\.matadata\...\
					// [1] = aardfa-adfa4df-asdfa323sf-afdf56_원본파일명.jpg
					String[] filePath = noticeFileList.get(0).getFileSavepath().split("/");
					
					String path = filePath[0];
					deleteFoler(req, path);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			result = ServiceResult.OK;
		}else {
			result = ServiceResult.FAILED;
		}
		return result;
	}


	@Override
	public String idFind(DDITMemberVO memberVO) {
		String memId = loginMapper.idFind(memberVO);
 		return memId;
	}


//	@Override
//	public String pwFind(DDITMemberVO memberVO) {
//		String memPw = loginMapper.pwFind(memberVO);
//		return memPw;
//	}

	@Override
	public String pwFind(Map<String, String> map) {
		String memPw = loginMapper.pwFind(map); 
		return memPw;
	}

	@Override
	public DDITMemberVO selectMember(String memId) {
 		return profileMapper.selectMember(memId);
	}

	@Override
	public ServiceResult profileUpdate(HttpServletRequest req, DDITMemberVO memberVO) {
		ServiceResult result = null;
 		
		String uploadPath = req.getServletContext().getRealPath("/resources/profile");
		File file = new File(uploadPath);
		
		if(!file.exists()) {
			file.mkdirs();
		}
		
		String proFileImg = "";		//회원 정보에 추가될 프로필 이미지 경로
		try {
			MultipartFile profileImgFile = memberVO.getImgFile();
			if(profileImgFile.getOriginalFilename() != null &&
					!profileImgFile.getOriginalFilename().equals("")) {
				
				String fileName = UUID.randomUUID().toString();		//UUID 파일명 생성 
				fileName += "_" + profileImgFile.getOriginalFilename();		//UUID_원본파일명
				uploadPath += "/" + fileName;						//최종 업로드하기 위한 파일 경로
				profileImgFile.transferTo(new File(uploadPath));  	// 해당 위치 경로에 파일 복사
				proFileImg = "/resources/profile/" + fileName;		// 파일 복사가 일어난 파일의 위치로 접근하기 위한 URI 설정
			}
				memberVO.setMemProfileImg(proFileImg);	
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		int status = profileMapper.profileUpdate(memberVO);
		if(status > 0) {
			result = ServiceResult.OK;
		}else {
			result = ServiceResult.FAILED;
		}
		return result;
	}

	private void noticeFileUpload(List<NoticeFileVO> noticeFileList,
								int boNo, HttpServletRequest req) throws IllegalStateException, IOException {
		
		// 공지사항 게시판에서 등록된 파일은 기본  '/resources/notice/' 경로로 설정
		String savePath = "/resources/notice/";
		
		// 넘겨받은 파일 데이터가 존재할 때
		if(noticeFileList != null && noticeFileList.size() > 0) {
			for(NoticeFileVO noticeFileVO : noticeFileList) {
				// 파일 업로드 처리 시작
				String saveName = UUID.randomUUID().toString(); 	//UUID의 랜덤 파일명 생성
				saveName = saveName + "_" + noticeFileVO.getFileName().replace(" ", "_");
//				String endFileName = noticeFileVO.getFileName().split("\\")[1];		//디버깅 및 확장자 추출 참고
				
				// .../resources/notice/15 경로
				String saveLocate = req.getServletContext().getRealPath(savePath + boNo);
				File file = new File(saveLocate);
				if(!file.exists()) {	// 업로드를 하기 위한 폴더 구조가 존재하지 않을 때
					file.mkdirs();		// 폴더생성
				}
				
				// /resources/notice/15/UUID_원본파일명
				saveLocate += "/" +saveName;
				noticeFileVO.setBoNo(boNo);						//게시글 번호 설정
				noticeFileVO.setFileSavepath(saveLocate);		//파일 업로드 경로 설정
				noticeMapper.insertNoticeFile(noticeFileVO);	// 공지사항 게시글 파일 데이터 추가
				
				File saveFile = new File(saveLocate);
				noticeFileVO.getItem().transferTo(saveFile); 	//파일 복사
			}
		}
	}
	
	
	private void deleteFoler(HttpServletRequest req, String path) {
		// UUID_원본파일명 전 폴더경로를 folder 파일 객체로 잡아준다.
		File folder = new File(path);
		
		try {
			if(folder.exists()) {	//경로가 존재한다면
				File[] folderList = folder.listFiles();	//폴더 안에 있는 파일들의 목록을 가져온다.
				
				for(int i=0; i < folderList.length; i++ ) {
					if(folderList[i].isFile()) {	// 폴더 안에 있는 파일이 파일일 때
						folderList[i].delete();		// 폴더 안에 파일을 차례대로 삭제
					}else {
						// 폴더 안에 있는 파일이 폴더일때 재귀함수 호출(폴더안으로 들어가서 다시 이행) 
						deleteFoler(req, folderList[i].getPath());
					}
				}
				folder.delete();		//폴더 삭제
			}
		} catch (Exception e) {
			e.printStackTrace();
 		}
	}
	

	@Override
	public NoticeFileVO noticeDownload(int fileNo) {
 		NoticeFileVO noticeFileVO = noticeMapper.noticeDownload(fileNo);
		if(noticeFileVO == null) {
			throw new RuntimeException();
		}
		noticeMapper.incrementNoticeDownloadCount(fileNo); //다운로드 횟수 증가
 		
 		return noticeFileVO;
	}
}
