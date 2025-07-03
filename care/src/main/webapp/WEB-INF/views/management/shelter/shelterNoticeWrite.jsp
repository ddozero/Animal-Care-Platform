<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>보호시설 공지사항</title>
<link rel="stylesheet"
	href="https://cdn.jsdelivr.net/gh/orioncactus/pretendard/dist/web/static/pretendard.css">

<style>
.board {
	font-family: 'Pretendard', sans-serif;
	background-color: #fff;
	padding: 60px 20px 40px;
}

.header-title {
	font-size: 28px;
  	font-weight: bold;
  	margin-bottom : 20px;
  	text-align: center;
  	color: #3ACDB2;
}

.title-detail {
    text-align: center;
    margin-bottom: 40px; 
    color: #666; 
    font-size: 16px; 
}

.board-container {
	max-width: 1200px;
	margin: 0 auto;
	background-color: #fff;
	padding: 20px;
	border: 1px solid #DBDBDB;
	border-radius: 6px;
} 

.board-content-table {
	width: 100%;
	border-collapse: collapse;
	font-size: 16px;
	margin-bottom: 30px;
	table-layout: fixed;
}

.board-content-table th {
	width: 20%;
	background-color: #f9f9f9;
	padding: 12px 10px;
	border-bottom: 1px solid #ddd;
	color: #333;
	text-align: center;
	width: 15%;
}

.board-content-table td {
	padding: 12px 10px;
	border-bottom: 1px solid #DBDBDB;
	color: #333;
	width: 35%;
}

#noticeContentBox {
	border-radius: 4px;
	min-height: 400px;
	padding: 20px;
	font-size: 16px;
	line-height: 1.6;
	white-space: pre-line;
	color: #444;
}

.btn-area {
	text-align: center;
}

.btn-area input[type="button"],
.btn-area input[type="submit"] {
	background-color: #3ACDB2;
	color: #fff;
	border: none;
	padding: 10px 20px;
	margin-bottom: 20px;
	border-radius: 4px;
	font-size: 14px;
	cursor: pointer;
	width: 90px;
	transition: background-color 0.3s;
}

.btn-area input[type="button"]:hover,
.btn-area input[type="submit"]:hover {
	background-color: #34b3a1;
}
@media (max-width : 600px) {
	.board-content-table th, .board-content-table td {
		font-size: 14px;
		padding: 10px;
	}
}

textarea {
	font-family: 'Pretendard', sans-serif;
	font-size: 16px;
	color: #333;
	line-height: 1.6;
	border: 1px solid #DBDBDB;
	border-radius: 4px;
	box-sizing: border-box;
	padding: 10px;
	resize: vertical;
	background-color: white;
	outline: none; 
}

textarea:hover,
textarea:focus {
	border: 1px solid #DBDBDB; 
	box-shadow: none; 
	outline: none;
}

</style>
</head>

<body>
	<%@ include file="/WEB-INF/views/common/index/indexHeader.jsp"%>
	<section class="board">
		<div class="header-title">보호시설 소식</div>
		<div class="title-detail">보호소에서 안내할 공지사항을 등록해보세요.</div>

		<div class="board-container">
			<form id = "noticeWriteForm">
			<table class="board-content-table">
				<tr>
					<th>작성자</th>
					<td colspan="3">
						<input type="text" name="writer" value="보호소 관리자" readonly style="width: 100%; border: none; background: transparent; color: #555; font-size:16px;">
					</td>
				</tr>
				<tr>
					<th>제목</th>
					<td colspan="3">
						<input type="text" name="title" style="border: none; outline: none; font-size:15px; width: 100%; padding: 8px;"  placeholder="제목을 입력하세요" required>
					</td>
				</tr>
				<tr>
					<th>내용</th>
					<td colspan="3">
						<textarea name="content" required placeholder="내용을 입력하세요" rows="15" style="width: 100%; padding: 10px; font-size: 15px; resize: vertical;"></textarea>
					</td>
				</tr>
			</table>

			<div class="btn-area">
				<input type="submit" value="등록하기">
				<input type="button" value="돌아가기" onclick="history.back()">
			</div>
		</div>
		</form>
	</section>
	
	
<script src="${pageContext.request.contextPath}/resources/web/common/commonUtils.js"></script>
<script>
document.addEventListener("DOMContentLoaded", function () {
  const form = document.getElementById("noticeWriteForm");

  form.addEventListener("submit", async function (e) {
    e.preventDefault(); // 기본 form 제출 막기

    const title = form.title.value.trim();
    const content = form.content.value.trim();

    if (!title || !content) {
      alert("제목과 내용을 모두 입력해주세요.");
      return;
    }

    const dto = {
      title: title,
      content: content
    };

    try {
      const result = await API.post("/care/api/management/shelter/boards/", dto); 
      if (result.status === 201) {
        alert("공지사항이 등록되었습니다.");
        location.href = "/care/management/shelters"; 
      } else {
        alert("등록에 실패했습니다.");
      }
    } catch (err) {
      console.error("등록 중 오류:", err);
      alert("서버 오류로 등록 실패");
    }
  });
});
</script>




</body>
</html>