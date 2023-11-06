package kr.or.ddit.vo;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import lombok.Data;

@Data
public class NoticeVO {
	
	private int boNo;
	private String boTitle;
	private String boContent;
	private String boWriter;
	private String boDate;
	private int boHit;
	
	private Integer[] delNoticeNo;
	private MultipartFile[] boFile;
	private List<NoticeFileVO> noticeFileList;
	//private AlramVO alarmVO;
	
	
	public void setBoFile(MultipartFile[] boFile) {
		this.boFile = boFile;
		if(boFile != null) {
			List<NoticeFileVO> noticeFileList = new ArrayList<NoticeFileVO>();
			for(MultipartFile item : boFile) {
				if(StringUtils.isBlank(item.getOriginalFilename())) {
					continue;	//파일이 있으면 다음으로 건너뜀
				}
				// 하나의 파일을 noticeFileVO에 item으로 넣음  ---> list에도 추가
				NoticeFileVO noticeFileVO = new NoticeFileVO(item);
				noticeFileList.add(noticeFileVO);
			}
			this.noticeFileList = noticeFileList;
		}
	}
}
