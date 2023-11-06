package kr.or.ddit.controller;

import java.util.Date;

import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import kr.or.ddit.vo.Board;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("/board")
public class BoardController {

	//Slf4j의 log가 뜨지 않을 경우
	//private static final Logger logger = LoggerFactory.getLogger(BoardController.class);
	
	
	/*
	 * 1. 요청 경로 매핑
	 * 
	 * @RequestMapping(value="/board/register")
	 * 
	 * 해당 경로로 요청시, 페이지는 404 Not found이지만 요청 결과가 콘솔창에 출력되는 걸 확인할 수 있다.
	 * 
	 * - 요청 경로는 반드시 설정해야 하는 필수 정보
	 * - 속성이 하나일 때는 속성명을 생략할 수 있다.
	 * - 컨트롤러의 클래스 레벨과 메서드 레벨로 지정할 수 있다.
	 * - 클래스 레벨로 요청 경로를 지정하면 메서드 레벨에서 지정한 경로의 기본경로로 취급된다.
	 * - 클래스 레벨의 요청 경로에 메서드 레벨의 요청 경로를 덧붙인 형태가 최종 경로가 된다. 
	 * 
	 * 3. HTTP 메서드 매핑
	 * @RequestMapping(value="/board/register", method=RequestMethod.GET)
	 * 
	 * Http 메소드 매핑으로 넘어오면서 void타입의 메소드가 String으로 리턴 타입을 변경해서 테스트
	 */
	
	//value[생략가능]를 쓰는 이유는 다른 속성을 쓸 때, 어떤 속성에 어떤 값인지 알아보기 위해
	// /register 경로에 GET 방식
	@RequestMapping(value = "/register", method = RequestMethod.GET)
	public String registerForm() {
		log.info("registerForm()실행...!");
		return "success";
	}
	
	// /register 경로에 POST 방식
	@RequestMapping(value="register", method = RequestMethod.POST)
	public String register() {
		log.info("register()실행...!");
		return "success";
	}
	
	
	// /modify 경로  GET 방식
	@RequestMapping(value = "/modify", method = RequestMethod.GET)
	public String modifyForm() {
		log.info("modifyForm()실행 ...!");
		return "success";
	}
	
	// /modify 경로에 POST 방식
	@RequestMapping(value = "/modify", method = RequestMethod.POST)
	public String modify() {
		log.info("modify()실행 ...!");
		return "success";
	}
	
	// /remove 경로 POST 방식
	@RequestMapping(value = "/remove", method = RequestMethod.POST)
	public String remove() {
		log.info("remove()실행 ...!");
		return "success";
	}
	

	// /list 경로 GET 방식
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public String list() {
		log.info("list()실행 ...!");
		//return "success";
		return "board/list";
	}
	
	//1번
//	@RequestMapping(value = "/list")
//	public void list() {
//		log.info("list()실행...!");
//	}
	
	
	
	/*
	 * 2. 경로 패턴 매핑
	 * @RequestMapping(value =/board/read/{boardNo}")
	 * 
	 * 해당 경로로 요청시, board 폴더에 read.jsp를 찾아서 페이지를 출력해준다.
	 * 경로로 요청한 boardNo값을 콘솔 창에서 확인할 수 있다.
	 */
	
	//경로에 변수가 있을 시 @PathVariable 안붙이면 500에러
	@RequestMapping(value ="/read/{boardNo}")
	public String read(@PathVariable int boardNo) {
		log.info("read()실행!");
		log.info("boardNo :"+ boardNo);
		return "board/read";
	}

	/*
	 * 4. Param 매핑
	 * @RequestMapping(value="/board/get", method=RequestMethod.GET, params="register")
	 */
	
	// ###  jsp에서의 name값과 params와 일치했을 때 들어올 수 있음
	
	
	// /board/get 경로, GET방식, "register" 요청 파라미터
	@RequestMapping(value = "/get", method= RequestMethod.GET, params = "register")
	public String registerFormParamsGet() {
		log.info("registerFormParamsGet() 실행!!");
		return "board/register";
	}
	
	// /board/post 경로, POST방식, "register" 요청 파라미터
	@RequestMapping(value = "/post", method = RequestMethod.POST, params = "register")
	public String registerFormParamsPost() {
		log.info("registerFormParamsPost()실행");
		return "board/list";
	}
	
	
	// /board/get 경로, GET방식, "modify"요청 파라미터
	@RequestMapping(value="/get", method = RequestMethod.GET, params = "modify")
	public String modifyFormParamsGet() {
		log.info("modifyFormParamsGet() 실행");
		return "board/modify";
	}
	
	// /board/post 경로, POST방식, "modify"요청 파라미터
	@RequestMapping(value="/post", method = RequestMethod.POST, params = "modify")
	public String modifyFormParamsPost() {
		log.info("modifyFormParamsPost() 실행");
		return "board/list";
	}
	
	
	// /board/get 경로, GET방식, "remove" 요청 파라미터
	@RequestMapping(value="/get", method = RequestMethod.GET, params= "remove")
	public String removeFormParamsGet() {
		log.info("removeFormParamsGet() 실행");
		return "board/remove";
	}
	
	// /board/post 경로, POST방식, "remove" 요청 파라미터
	@RequestMapping(value="/post", method = RequestMethod.POST, params= "remove")
	public String removeFormParamsPost() {
		log.info("removeFormParamsPost() 실행");
		return "board/list";
	}
	
	
	
	// /board/list 경로, GET방식, "list" 요청 파라미터
	@RequestMapping(value="/get", method = RequestMethod.GET, params = "list")
	public String listParamsGet() {
		log.info("listParamsGet() 실행");
		return "board/list";
	}
	
	// /board/read 경로, GET방식, "read" 요청 파라미터
	@RequestMapping(value="/get", method = RequestMethod.GET, params = "read")
	public String readParamsGet() {
		log.info("readParamsGet() 실행");
		return "board/read";
	}
	
	/*
	 * 5. Headers 매핑
	 * @RequestMapping(value="/board/{boardNo}", method= RequestMethod.PUT, header="X-HTTP-Method-Override="PUT""
	 */
	
	
	@RequestMapping(value="/{boardNo}", method = RequestMethod.PUT)
	//컨드롤변수명 : boardNo - jsp에서 네임값 : @PathVariable("boardNo")
	public ResponseEntity<String> modify(@PathVariable("boardNo") int boardNo, Board board){
		log.info("modify() 실행");
		ResponseEntity<String> entity = new ResponseEntity<String>("SUCCESS", HttpStatus.OK);
		return entity;
	}
	
	@RequestMapping(value ="/{boardNo}", method= RequestMethod.PUT, headers= "X-HTTP-Method-Override=PUT")
	//@RequestBody 요청본문 안의 내용을 자동 바인딩 되도록 사용함
	public ResponseEntity<String> modifyByHeader(@PathVariable("boardNo") int boardNo, @RequestBody Board board){
		log.info("modifyByHeader() 실행");
		
		log.info("board.boardNo : " + board.getBoardNo());
		log.info("board.title : " + board.getTitle());
		log.info("board.content : " + board.getContent());
		log.info("board.writer : " + board.getWriter());
		
		ResponseEntity<String> entity = new ResponseEntity<String>("SUCCESS", HttpStatus.OK);
		return entity;
	}
	
	/*
	 * 6. Content Type 매핑
	 * @RequestMapping(value="/{boardNo}", method= RequestMethod.PUT, consumes="application/json")
	 * jsp에서 데이터를 비동기로 보낼때, 컨텐트타입(application/json)이면서 메소드는 post인것을 찾아감, 컨슘을 설정하지 않아도 디폴트로 제이슨으로 자동설정됨
	 */
	
	@RequestMapping(value="/{boardNo}", method = RequestMethod.POST)
	public ResponseEntity<String> modifyContentType(@PathVariable("boardNo") int boardNo, @RequestBody Board board){
		log.info("modifyContentType() 실행!!");
		ResponseEntity<String> entity = new ResponseEntity<String>("SUCCESS", HttpStatus.OK);
		return entity;
	}
	
	// consumes 속성 값에 "application/json" 미디어 타입을 지정한다.
	// consumes 속성은 클라이언트에서 보내온 요청중 contentType 설정과 일치할 때 컨트롤러 메소드가 실행될 수 있는 도착지가 된다.
	// 응답으로 내보낼때 값 타입<제너릭><String> -->> "SUCCESS"와 200코드[상태코드]
	// 에러가 나면 200코드와 SUCCESS가 나오지 않음.
	// 데이터와 상태코드값을 같이 쓰기 위해 리턴값을 ResponseEntity로 두었음
	@RequestMapping(value="/{boardNo}", method = RequestMethod.PUT, consumes = "application/json")
	public ResponseEntity<String> modifyByJson(@PathVariable("boardNo") int boardNo, @RequestBody Board board){
		log.info("modifyByJson() 실행");
		ResponseEntity<String> entity = new ResponseEntity<String>("SUCCESS", HttpStatus.OK);
		return entity;
	}

	
	//consumes 속성 값에 "application/xml" 미디어 타입을 지정한다.
	// '/'가 꼭 필요함.
	@RequestMapping(value="/{boardNo}", method = RequestMethod.PUT, consumes = "application/xml")
	public ResponseEntity<String> modifyByXml(@PathVariable("boardNo") int boardNo, @RequestBody Board board){
		log.info("modifyByXml() 실행");
		log.info("boardNo : " + boardNo);
		log.info("board : " + board);
		
		
		ResponseEntity<String> entity = new ResponseEntity<String>("SUCCESS", HttpStatus.OK);
		return entity;
	}
	
	/*
	 * 7. Accept 매핑 사용
	 * produces 속성값을 지정하지 않으면 기본값인 "application/json" 미디어 타입으로 지정된다.
	 */

	@RequestMapping(value="/{boardNo}", method = RequestMethod.GET)
	public ResponseEntity<Board> readToAccept(@PathVariable("boardNo") int boardNo){
		log.info("read() 실행");
		log.info("boardNo : " + boardNo);
		
		Board board = new Board();
		board.setTitle("제목1");
		board.setContent("내용1");
		board.setWriter("작성자1");
		board.setRegDate(new Date());
		
		ResponseEntity<Board> entity = new ResponseEntity<Board>(board, HttpStatus.OK);
		return entity;
	}
	
	@RequestMapping(value="/{boardNo}", method = RequestMethod.GET, produces= "application/json")
	public ResponseEntity<Board> readToJson(@PathVariable("boardNo") int boardNo){
		log.info("readToJson() 실행");
		
		String addStr = "_json";
		Board board = new Board();
		board.setTitle("제목" + addStr);
		board.setContent("내용" + addStr);
		board.setWriter("작성자" + addStr);
		board.setRegDate(new Date());
		
		ResponseEntity<Board> entity = new ResponseEntity<Board>(board, HttpStatus.OK);
		return entity;
	}
	
	@RequestMapping(value="/{boardNo}", method = RequestMethod.GET, produces= "application/xml")
	public ResponseEntity<Board> readToXml(@PathVariable("boardNo") int boardNo){
		log.info("readToXml() 실행");
		
		String addStr = "_xml";
		Board board = new Board();
		board.setTitle("제목" + addStr);
		board.setContent("내용" + addStr);
		board.setWriter("작성자" + addStr);
		board.setRegDate(new Date());
		
		ResponseEntity<Board> entity = new ResponseEntity<Board>(board, HttpStatus.OK);
		return entity;
	}
	
	
	@RequestMapping(value="/search", method= RequestMethod.GET)
	public String boardSearch(String keyword, Model model) {
		model.addAttribute("keyword", keyword);
		return "board/search";
		
	}
}
