package kr.or.ddit.vo;

import java.util.Date;

import org.hibernate.validator.constraints.NotBlank;

import lombok.Data;

@Data
public class Board {

	private int boardNo;
	@NotBlank //입력값 검증
	private String title;
	private String content;
	private String writer;
	private Date regDate;
}
