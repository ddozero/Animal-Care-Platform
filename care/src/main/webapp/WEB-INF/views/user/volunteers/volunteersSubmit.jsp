<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>봉사신청</title>

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
	<div class="board-container">
		<div class="header-title">봉사 신청</div>
		<form id="volunteerApplyForm" method="post">
			<table class="board-table">
				<tbody>
					<tr>
						<th>이름</th>
						<td><input type="text" name="name" placeholder="신청인의 이름을 작성해주세요." required></td>
					</tr>
					<tr>
						<th>이메일</th>
						<td><input type="email" name="email" placeholder="이메일을 입력해주세요." required></td>
					</tr>
					<tr>
						<th>전화번호</th>
						<td><input type="tel" name="tel" placeholder="전화번호를 입력해주세요." required></td>
					</tr>
					<tr>
						<th>생년월일</th>
						<td><input type="date" name="birthDate" placeholder="생년월일을 입력해주세요." required></td>
					</tr>
					<tr>
						<th>남 인원수</th>
						<td><input type="number" name="male" min="0" placeholder="남 인원수를 입력해주세요."></td>
					</tr>
					<tr>
						<th>여 인원수</th>
						<td><input type="number" name="female" min="0" placeholder="여 인원수를 입력해주세요."></td>
					</tr>
					<tr>
						<th>신청 유형</th>
						<td><select name="type" required>
								<option value="">선택</option>
								<option value="S">개인</option>
								<option value="G">단체</option>
						</select></td>
					</tr>
					<tr>
						<th>단체명</th>
						<td><input type="text" name="groupName" placeholder="봉사 단체명을 입력해주세요."></td>
					</tr>
					<tr>
						<th>단체 연락처</th>
						<td><input type="text" name="groupTel" placeholder="연락처를 입력해주세요."></td>
					</tr>
					<tr>
						<th>신청 내용</th>
						<td><textarea name="description" rows="5" style="width: 100%;"></textarea></td>
					</tr>
				</tbody>
			</table>

			<div style="text-align: center; margin-top: 20px;">
				<button type="submit">신청하기</button>
			</div>
		</form>
	</div>


<script src="${pageContext.request.contextPath}/resources/web/common/commonUtils.js"></script>
	<script>
		  const path = location.pathname.split("/");
		  const idx = path[3];
		
		  document.getElementById("volunteerApplyForm").addEventListener("submit", async function(event) {
			  event.preventDefault();
			  
		    const formData = getJsonFromForm("volunteerApplyForm");
		    if (formData.type === 'G') {
		    	  if (!formData.groupName) {
		    	    alert("단체 신청 시 단체명을 입력해야 합니다.");
		    	    return;
		    	  }
		    	  if (!formData.groupTel) {
		    	    alert("단체 신청 시 단체 연락처를 입력해야 합니다.");
		    	    return;
		    	  }
		    }

		    const result = await API.post('/care/api/volunteers/' + idx + '/submit', formData);
		
		      if (result.status === 200) {
		        alert(result.message || "봉사 신청이 완료되었습니다.");
		        history.back(); 
		        return;
		      }
		  }); 

	</script>



</body>
</html>