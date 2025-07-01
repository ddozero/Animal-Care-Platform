<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>봉사 신청하기</title>

<style>
.board {
	font-family: 'Pretendard', sans-serif;
	background-color: #fff;
	padding: 60px 20px 40px;
}

.header-title {
	font-size: 28px;
	font-weight: bold;
	text-align: center;
	color: #3ACDB2;
}

.board-container {
	max-width: 1200px;
	margin: 0 auto;
	background-color: #fff;
	padding: 20px;
	border: 1px solid #DBDBDB;
	border-radius: 6px;
} 

</style>

</head>
<body>
<%@ include file="/WEB-INF/views/common/index/indexHeader.jsp"%>
	<section class="board">
		<div class="header-title">봉사 신청하기</div>
		
		<div class="board-container">
			<div class = "volunteer-image">
				<img id = "volunteerImage" src = "" width = "200" height="200">
			</div>
			
			<div class = "volunteer-Content">
				<div><span>제목:</span><span id = "title"></span></div>
				<div><span>업체명:</span><span id = "shelter"></span></div>
				<div><span>상태:</span><span id = "status"></span></div>
				<div><span>봉사유형:</span><span id = "type"></span></div>
				<div><span>대상연령:</span><span id = "ageTarget"></span></div>
				<div><span>봉사장소:</span><span id = "location"></span></div>
				<div><span>봉사일:</span><span id = "volunteerDate"></span></div>
				<div><span>봉사시간:</span><span id = "time"></span></div>
				<div><span>모집인원:</span><span id = "capacity"></span></div>
				<div><span>신청인원:</span><span id = "applicants"></span></div>
				<div><input type = "button" id = "volunteerSubmit" value = "봉사 신청하기" ></div>
				<div><span>봉사 소개</span><span id = "content"></span></div>
			</div>
		</div>
	</section>

<script src="${pageContext.request.contextPath}/resources/web/common/commonUtils.js"></script>
	<script>
		async function VolunteerDetail() {
		  try {
			const idx = location.pathname.split("/").pop();
		    const result = await API.get('/care/api/volunteers/' + idx);
		    if (result.status !== 200 || !result.data) {
		    	location.href = '/care/volunteers';
		      return;
		    }
	
		    const v = result.data;
		
		    document.getElementById("volunteerImage").src = '${pageContext.request.contextPath}' + v.imagePath;
		
		    document.getElementById("title").textContent = v.title || "";
		    document.getElementById("shelter").textContent = v.shelter || "";
		    document.getElementById("status").textContent = v.status || "";
		    document.getElementById("type").textContent = v.type || "";
		    document.getElementById("ageTarget").textContent = v.ageTarget || "";
		    document.getElementById("location").textContent = v.location || "";
		    document.getElementById("volunteerDate").textContent = v.volunteerDate || "";
		    document.getElementById("time").textContent = (v.time || "") + "시간";
		    document.getElementById("capacity").textContent = (v.capacity || "") + "명";
		    document.getElementById("applicants").textContent = (v.applicants || "") + "명";
		    document.getElementById("content").textContent = v.content || "";
		    //document.getElementById("volunteerSubmit").setAttribute("onclick", "location.href='/care/volunteers/" + v.idx + "/submit'");
		
			
		  } catch (error) {
		    console.error(error);
		    alert("서버 통신 중 오류가 발생했습니다.");
		  }
		}
		
		document.addEventListener("DOMContentLoaded", function() {
			  VolunteerDetail();

			  document.getElementById("volunteerSubmit").addEventListener("click", function() {
			    var isLoggedIn = <%= (session.getAttribute("loginUser") != null) ? "true" : "false" %>;

			    if (!isLoggedIn) {
			      alert("로그인이 필요합니다.");
			    } else {
			      var pathArray = location.pathname.split("/");
			      var idx = pathArray[pathArray.length - 1];
			      location.href = '/care/volunteers/' + idx + '/submit'; 
			    }
			});
		});

	</script>

	

</body>
</html>