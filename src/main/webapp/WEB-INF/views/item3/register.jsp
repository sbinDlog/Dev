<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Item3 Register</title>
</head>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.0/jquery.min.js"></script>
<body>
	<h2>REGISTER3</h2>
	<form action="/item3/register" method="post" enctype="multipart/form-data" id="item">
		<table>
			<tr>
				<td>상품명</td>
				<td><input type="text" name="itemName" id="itemName"></td>
			</tr>
			<tr>
				<td>가격</td>
				<td><input type="text" name="price" id="price"></td>
			</tr>
			<tr>
				<td>파일</td>
				<td>
					<input type="file" id="inputFile"/>
					<div class="uploadedList"></div>
				</td>
			</tr>
			<tr>
				<td>개요</td>
				<td><textarea rows="10" cols="30" name="description"></textarea></td>
			</tr>
		</table>
		<div>
			<button type="submit">등록</button>
			<button type="button" onclick="javascript:location.href='/item3/list'">목록</button>
		</div>
	</form>
</body>
<script type="text/javascript">
$(function(){
	var inputFile = $("#inputFile");		//input type file element
	var item = $("#item");		 
	
	inputFile.on("change", function(event){
		console.log("change...!!!");
		
		var files = event.target.files;
		var file = files[0];
		
		console.log(file);
		
		var formData = new FormData();
		
		formData.append("file", file);
		
		
		/* 
			dataType이라는 속성이 부여되었다면, 서버에서 브라우저로 넘길 때 데이터 타입이다.  
			processData는 쿼리스트링을 구성하지 않으니까 비활성화 함
			contentType 기본 값은 json이니까 비활성화함 ---> 파일이면 multipart/form-data임
			success: function(data)에서 data는 savedName임
		*/
		$.ajax({
			type : "post",
			url : "/item3/uploadAjax",
			data : formData,
			dataType : "text",
			processData : false,
			contentType : false,
			success: function(data){
				console.log(data);
				
				var str = "";
				if(checkImageType(data)){	//이미지면 이미지 태그를 이용하여 출력
					str = "<div>";
					str += "	<a href='/item3/displayFile?fileName=" + data + "'>";	//data -> 원본파일명
					str += "		<img src='/item3/displayFile?fileName=" +getThumbnailName(data) +"'/>";	//s_가 붙은 썸네일 이미지 데이터야 함[100x100]
					str += "	</a>";
					str += "	<span>X</span>";
					str += "</div>";
					
				}else {				//파일이면 파일명에 대한 링크로만 출력
					str += "<div>";
					str += "	<a href='/item3/displayFile?fileName=" + data + "'>" + getOriginalName(data)+ "</a>";	//_를 기준으로 오른쪽의 원본파일명
					str += "	<span>X</span>";
					str += "</div>";
				}
				
				$(".uploadedList").append(str);
			}
		});
		
	});
	
	// 업로드한 이미지 'X' 클릭
	$(".uploadedList").on("click", "span", function(){
		$(this).parent("div").remove();	//span태그의 부모 div를 잡음
	});
	
	
	item.submit(function(){
		var that = $(this);		//form
		var str = "";
		$(".uploadedList a").each(function(index){
			var value = $(this).attr("href");
			value = value.substr(28);		// '?fileName=' 다음부터 나오는 값
			str += "<input type='hidden' name='files["+index+"]' value='"+value+"'>";
		});
		
		console.log("str :" + str);
		that.append(str);
		that.get(0).submit();		//form의 첫번째를 가져와서 submit() 처리
	});
	
	
	//임시 파일로 썸네일 이미지 만들기
	function getThumbnailName(fileName){
		var front = fileName.substr(0, 12);		// 2023/09/04 폴더
		var end = fileName.substr(12);			// 뒤 파일명
		
		console.log("front" + front);
		console.log("end" + end);
		
		return front +"s_" + end;	//원본파일명이 s_가 붙은 걸로 들어감
	}
	
	function getOriginalName(fileName){		
		if(checkImageType(fileName)){			// _를 기준으로 왼쪽
			return;
		}
		var idx = fileName.indexOf("_") + 1;	// _를 기준으로 오른쪽
		return fileName.substr(idx);
	}
	
	
	// 이미지 파일인지 확인
	function checkImageType(fileName){
		var pattern = /jpg|gif|png|jpeg/i;
		return fileName.match(pattern);		//패턴과 일치하면 true(이미지구나?)
	}
	
});
</script>
</html>