<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>당신에게 다시가는 길 - 봉사신청</title>

<style>
:root {
  --gray-2: #e0e0e0;
  --text-main: #222;
  --primary-color: #3ACDB2;
}

body {
  background-color: #f9f9f9;
}

.container {
  max-width: 600px;
  margin: 40px auto;
  padding: 40px;
  border: 1px solid var(--gray-2);
  border-radius: 12px;
  background-color: #fff;
  box-shadow: 0 4px 10px rgba(0,0,0,0.05);
}

.header-title {
  font-size: 28px;
  font-weight: bold;
  margin-bottom: 10px;
  text-align: center;
  color: var(--primary-color);
}

.title-detail {
  text-align: center;
  margin-bottom: 30px;
  font-size: 15px;
  color: #666;
}

form {
  display: flex;
  flex-direction: column;
}

form > div {
  margin-bottom: 16px;
}

form label {
  font-weight: bold;
  margin-bottom: 8px;
  display: inline-block;
}

form input,
form select,
form textarea {
  width: 100%;
  padding: 12px;
  border: 1px solid var(--gray-2);
  border-radius: 8px;
  font-size: 14px;
  box-sizing: border-box;
}

form textarea {
  font-family: 'Pretendard', sans-serif;
  font-size: 15px;
  resize: vertical;
  min-height: 120px;
}

button[type="submit"]:hover,
.btn-area:hover {
  background-color: #32b8a1;
}

.button-wrapper {
  display: flex;
  justify-content: center;
  gap: 16px;   
  margin-top: 30px;
}

.button-wrapper button {
  width: 120px;       /* ← 너비 줄이기 */
  height: 42px;       /* ← 높이 살짝 줄이기 */
  padding: 10px 16px; /* ← 내부 여백 조정 */
  font-size: 14px;    /* ← 글씨 크기 줄이기 */
  border-radius: 8px;
  background-color: var(--primary-color);
  color: #fff;
  font-weight: bold;
  border: none;
  cursor: pointer;
  transition: background-color 0.2s;
}


</style>

</head>
<body>
<%@ include file="/WEB-INF/views/common/index/indexHeader.jsp"%>

<div class="container">
  <div class="header-title">봉사신청</div>
  <div class="title-detail">다양한 봉사활동에 참여해보세요.</div>

  <form id="volunteerApplyForm" method="post">
    <div>
      <label>이름</label>
      <input type="text" name="name" placeholder="신청인의 이름을 작성해주세요." required>
    </div>
    <div>
      <label>이메일</label>
      <input type="email" name="email" placeholder="이메일을 입력해주세요." required>
    </div>
    <div>
      <label>전화번호</label>
      <input type="tel" name="tel" placeholder="전화번호를 입력해주세요." required>
    </div>
    <div>
      <label>생년월일</label>
      <input type="date" name="birthDate" required>
    </div>
    <div>
      <label>남 인원수</label>
      <input type="number" name="male" min="0" placeholder="남 인원수를 입력해주세요.">
    </div>
    <div>
      <label>여 인원수</label>
      <input type="number" name="female" min="0" placeholder="여 인원수를 입력해주세요.">
    </div>
    <div>
      <label>신청 유형</label>
      <select name="type" required>
        <option value="">선택</option>
        <option value="S">개인</option>
        <option value="G">단체</option>
      </select>
    </div>
    <div>
      <label>단체명</label>
      <input type="text" name="groupName" placeholder="봉사 단체명을 입력해주세요.">
    </div>
    <div>
      <label>단체 연락처</label>
      <input type="text" name="groupTel" placeholder="연락처를 입력해주세요.">
    </div>
    <div>
      <label>신청 내용</label>
      <textarea name="description" placeholder="신청하고 싶은 내용을 자유롭게 작성해주세요."></textarea>
    </div>
    <div class="button-wrapper">
	  <button type="submit">신청하기</button>
	  <button type="button" class="btn-area" onclick="history.back()">돌아가기</button>
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
      alert(result.message || "봉사 신청이 완료되었습니다. 담당자가 확인하여 연락드리겠습니다.");
      history.back(); 
    }
  }); 
</script>

<%@ include file="/WEB-INF/views/common/index/indexFooter.jsp" %>
</body>
</html>
