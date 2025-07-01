<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>

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
		<div class="header-title">봉사 신청</div>
		
		<div class="board-container">
			<table id = "volunteerContent">
				<tbody></tbody>
			</table>
			<form>
				<input type="submit" value="신청하기">
			</form>
	
			<div>상세 내용</div>
			<div class = "contentbox"></div>
		</div>
	</section>
	
<script>
    const idx = ${idx};  // 예: 29
  </script>

<script src="${pageContext.request.contextPath}/resources/web/common/commonUtils.js"></script>
<script>
	  async function VolunteerDetail() {
      const tbody = document.querySelector("#volunteerContent tbody");
      tbody.innerHTML = "";

      try {
        const result = await API.get("/care/api/volunteers/" + idx);

        if (result.status !== 200 || !result.data) {
          const row = document.createElement("tr");
          row.innerHTML = '<td colspan="2" style="text-align:center;">해당 봉사 정보를 불러올 수 없습니다.</td>';
          tbody.appendChild(row);
          return;
        }

        const v = result.data;

        const rows = [
          ['제목', v.title],
          ['업체명', v.shelter],
          ['봉사유형', v.type],
          ['봉사장소', v.location],
          ['대상연령', v.ageTarget],
          ['봉사일', v.volunteerDate],
          ['봉사시간', v.time + "시간"],
          ['모집인원', v.capacity + "명"],
          ['신청인원', v.applicants + "명"],
          ['상태', v.status],
          ['등록일', v.createdAt],
          ['내용', v.content],
        ];

        for (const [label, value] of rows) {
          const row = document.createElement("tr");
          row.innerHTML = '<th>' + label + '</th><td>' + value + '</td>';
          tbody.appendChild(row);
        }

      } catch (error) {
        console.error(error);
        alert("서버와 통신 중 오류가 발생했습니다.");
      }
    }

    // 페이지 로드 시 실행
    document.addEventListener("DOMContentLoaded", VolunteerDetail);
 </script>
	

</body>
</html>