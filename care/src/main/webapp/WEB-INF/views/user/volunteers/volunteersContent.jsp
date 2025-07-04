<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>봉사</title>

<style>
:root {
  --gray-2: #e0e0e0; /* 밝은 회색 */
  --text-main: #222; /* 이미 쓰고 있는 변수도 함께 정의 */
}



.container {
  max-width: 1200px;
  margin: 60px auto;
  padding: 50px;
  border: 1px solid var(--gray-2);
  border-radius: 8px;
  background: #fff;
}


.volunteer-detail-top {
  display: flex;
  gap: 32px;
  align-items: flex-start;
  margin-bottom: 40px;
}

.volunteer-image {
  margin-right : 30px;
  margin-top : 10px;
}

.volunteer-image img {
  width: 300px;
  height: 350px;
  object-fit: cover;
  border-radius: 12px;
  border: 1px solid #ddd;
}


.volunteer-button {
  grid-column: span 2;
  margin-top: 20px;
}

.volunteer-button input {
  background-color: #3ACDB2;
  color: #fff;
  padding: 10px 24px;
  border: none;
  border-radius: 8px;
  font-size: 15px;
  cursor: pointer;
  font-weight: bold;
}

.volunteer-button input:hover {
  background-color: #32b8a1;
}

.volunteer-description {
  background-color: #f9f9f9;
  padding: 30px;
  border-radius: 10px;
  border: 1px solid #e0e0e0;
}

.volunteer-description h3 {
  margin-bottom: 10px;
  font-size: 20px;
  font-weight: 600;
  color: #333;
}

.volunteer-description p {
  white-space: pre-wrap;
  line-height: 1.6;
  color: #444;
  font-size: 16px;
}

.volunteer-info {
  display: flex;
  flex-direction: column;
  gap: 5px;
  font-size: 17px;
  line-height: 1.8;
  max-width: 600px; 
}

.info-row {
  display: flex;
  gap: 10px;
  align-items: center;
  flex-wrap: wrap;
}

.info-row strong {
  min-width: 100px; 
  font-weight: bold;
  color: #333;
}

#title {
  font-size: 22px;
  font-weight: 700;
  color: #222;
  margin-bottom : 10px;
}

.btn-area {
	text-align: center;
	margin-top : 50px;
}

.btn-area input[type="button"] {
	background-color: #3ACDB2;
	color: #fff;
	border: none;
	padding: 10px 20px;
	margin-bottom : 20px;
	border-radius: 8px;
	font-size: 14px;
	cursor: pointer;
	width : 90px;
	transition: background-color 0.3s;
}

.btn-area input[type="button"]:hover {
	background-color: #34b3a1;
}

</style>

</head>
<body>
<%@ include file="/WEB-INF/views/common/index/indexHeader.jsp"%>
		
	<section class="container">
	  <div class="board-container">
	 <div class="volunteer-detail-top">
			  <div class="volunteer-image">
			    <img id="volunteerImage" src="" alt="봉사 이미지">
			  </div>
			
			  <div class="volunteer-info">
			    <div class="info-row"><span id="title"></span></div>
			    <div class="info-row"><strong>업체명</strong> <span id="shelter"></span></div>
			    <div class="info-row"><strong>상태</strong> <span id="status"></span></div>
			    <div class="info-row"><strong>봉사유형</strong> <span id="type"></span></div>
			    <div class="info-row"><strong>대상연령</strong> <span id="ageTarget"></span></div>
			    <div class="info-row"><strong>봉사장소</strong> <span id="location"></span></div>
			    <div class="info-row"><strong>봉사일</strong> <span id="volunteerDate"></span></div>
			    <div class="info-row"><strong>봉사시간</strong> <span id="time"></span></div>
			    <div class="info-row"><strong>모집인원</strong> <span id="capacity"></span></div>
			    <div class="info-row"><strong>신청인원</strong> <span id="applicants"></span></div>
			
			
			    <div class="volunteer-button">
			      <input type="button" id="volunteerSubmit" value="봉사 신청하기">
			    </div>
			  </div>
			</div>
	    <div class="volunteer-description">
	      <h3>봉사 소개</h3>
	      <p id="content"></p>
	    </div>
	    
	    <div class="btn-area">
				<input type="button" value="목록으로" id = "goListVolunteers">
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
		    
		    document.getElementById("goListVolunteers").addEventListener("click", function () {
				  const contextPath = "${pageContext.request.contextPath}"; // contextPath를 동적으로 설정
				  window.location.href = contextPath + "/volunteers"; // 이동할 페이지 경로
				});
			
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

	
<%@ include file="/WEB-INF/views/common/index/indexFooter.jsp" %>
</body>
</html>