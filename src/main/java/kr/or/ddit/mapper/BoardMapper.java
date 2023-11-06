package kr.or.ddit.mapper;

import java.util.List;

import kr.or.ddit.vo.Board;

// xml에 파일에서 namespace를 mapper 인터페이스 주소로 함 
// id는 메소드 명이 됨
public interface BoardMapper {
	
	public void create(Board board);

	public List<Board> list();

	public Board read(int boardNo);

	public void update(Board board);

	public void delete(int boardNo);

	public List<Board> search(Board board);

}
