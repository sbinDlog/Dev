<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<div class="login-box">
	<div class="card card-outline card-primary">
		<div class="card-header text-center">
			<p class="h4">
				<b>아이디찾기</b>
			</p>
		</div>
		<div class="card-body">
			<p class="login-box-msg">아이디 찾기는 이메일, 이름을 입력하여 찾을 수 있습니다.</p>
			<form action="" method="post">
				<div class="input-group mb-3">
					<input type="text" class="form-control" name="" id="memEmail" placeholder="이메일을 입력해주세요.">
				</div>
				<div class="input-group mb-3">
					<input type="text" class="form-control" name="" id="memName" placeholder="이름을 입력해주세요.">
				</div>
				<div class="input-group mb-3">
					<p>
						회원님의 아이디는 [<font id="id" class="h2 text-danger"></font>] 입니다.
					</p>
				</div>
				<div class="row">
					<div class="col-12">
						<button type="button" class="btn btn-primary btn-block" id="idFindBtn">아이디찾기</button>
					</div>
				</div>
			</form>
		</div>
	</div>
	<br />
	<div class="card card-outline card-primary">
		<div class="card-header text-center">
			<p href="" class="h4">
				<b>비밀번호찾기</b>
			</p>
		</div>
		<div class="card-body">
			<p class="login-box-msg">비밀번호 찾기는 아이디, 이메일, 이름을 입력하여 찾을 수 있습니다.</p>
			<form action="" method="post">
				<div class="input-group mb-3">
					<input type="text" class="form-control" id="memId" name="" placeholder="아이디를 입력해주세요.">
				</div>
				<div class="input-group mb-3">
					<input type="text" class="form-control" id="memEmail2" name="" placeholder="이메일을 입력해주세요.">
				</div>
				<div class="input-group mb-3">
					<input type="text" class="form-control" id="memName2" name="" placeholder="이름을 입력해주세요.">
				</div>
				<div class="input-group mb-3">
					<p>
						회원님의 비밀번호는 [<font color="red" class="h2" id="password"></font>] 입니다.
					</p>
				</div>
				<div class="row">
					<div class="col-12">
						<button type="button" class="btn btn-primary btn-block" id="pwFindBtn">비밀번호찾기</button>
					</div>
				</div>
			</form>
		</div>
	</div>
	<br/>
	<div class="card card-outline card-secondary">
		<div class="card-header text-center">
			<h4>MAIN MENU</h4>
			<button type="button" class="btn btn-secondary btn-block" id="loginBtn">로그인</button>
		</div>
	</div>
</div>
<script>
$(function(){
	var loginBtn = $("#loginBtn");
	var idFindBtn = $("#idFindBtn");
	var pwFindBtn = $("#pwFindBtn");
	var id = $("#id");
	var pw = $("#password");
	
	
	loginBtn.on("click", function(){
		location.href="/notice/login.do";
	});
	
	idFindBtn.on("click", function(){
		var name = $("#memName").val();
		console.log("name >>" +name);
		
		var email = $("#memEmail").val();
		console.log("email >>" +email);
		
		var data = {
			memName : name,
			memEmail : email
		};
		
		
		$.ajax({
			type : "post",
			url : "/notice/forget.do",
			data : JSON.stringify(data),
			contentType : "application/json; charset=utf-8",
			success : function(rst){
				if(rst == null || rst == ""){
					alert("존재하지 않는 회원입니다!");
				}else{
					console.log("결과: " + rst);
					id.text(rst);
				}
			},
				error : function(error){
					console.log("에러결과: " + error);
			}
		});
		
	});
	
	
	pwFindBtn.on("click", function(){
		var id = $("#memId").val();
		var name = $("#memName2").val();
		var email = $("#memEmail2").val();
		
		//controller에서 map으로 받기 때문에 key : value (memId : a001)로 담기게 된다.
		var data = {
			memId : id,
			memName : name,
			memEmail : email
		};
		
		$.ajax({
			type : "post",
			url : "/notice/pwforget.do",
			data : JSON.stringify(data),
			contentType : "application/json; charset=utf-8",
			success : function(res){
				if(res == null || res == ""){
					alert("존재하지 않는 회원입니다!");
				}else{
					console.log(res);
					pw.text(res).css("color", "red");
				}
			}
		});
		
	});
});	
</script>