package kr.or.ddit.controller.noticeboard.view;

import java.io.File;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.servlet.view.AbstractView;

// AbstractView 클래스를 상속받아 renderMergedOutputModel 메소드를 재정의하여 사용하면
// 해당 클래스가 View의 역할을 하는 페이지의 형태가 된다.
public class NoticeDownloadView extends AbstractView{

	
	// 이 클래스가 뷰의 역할을 할 수 있게 도와줌   ---> 클래스가 아니고 뷰로(view)
	@Override
	protected void renderMergedOutputModel(Map<String, Object> model, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
 		Map<String, Object> noticeFileMap = (Map<String, Object>) model.get("noticeFileMap");
 	
 		File saveFile = new File(noticeFileMap.get("fileSavepath").toString());
 		String fileName = noticeFileMap.get("fileName").toString();
 		// User-Agent 를 꺼내는 이유? 
 		// 너가 사용하는 브라우저가 무엇인지 알 수 있음 <다운로드를 진행할 때 fileName이 한글이나 영어로 되어있을때
 		// 브라우저별로 인코딩이 달라서 인코딩 설정을 해주기 위함>
 		
 		
 		// 요청 Header 정보들 중, User-Agent 영역 안에 여러 키워드 정보들을 가지고
 		// 특정 키워드가 포함되어 있는지를 체크해서 파일며의 출력 인코딩 부분을 설정한다.
 		// 사용 브라우저 또는 현상에 따라 발생하는 알고리즘이므로, 내가 사용하게 되는 브라우저의 버전이나 얻어온 header 정보들의
 		// 값에 따라 차이가 발생할 수 있다.
 		String agent = request.getHeader("User-Agent");
 		if(StringUtils.containsIgnoreCase(agent, "msie") || 
 				StringUtils.containsIgnoreCase(agent, "trident")) { 	//IE, Chrome일 때는
 			fileName = URLEncoder.encode(fileName, "UTF-8");
 		
 		} else {	//firefox, chrome
 			fileName = new String(fileName.getBytes(), "ISO-8859-1");
 		}
 		
 		response.setHeader("Content-Disposition", "attachment;filename=\"" + fileName + "\"");
 		response.setHeader("Content-Length", noticeFileMap.get("fileSize").toString());
 		
 		// try with resource 
 		// () 안에 명시한 객체는 finally로 최종 열린 객체에 대한 close()를 처리하지 않아도 자동 close()가 이루어진다.
 		try(
 			OutputStream os = response.getOutputStream();
 		){
 			FileUtils.copyFile(saveFile, os);
 		}
 		
 		
 		
	}

}