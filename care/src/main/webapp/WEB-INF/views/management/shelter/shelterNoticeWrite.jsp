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
	vertical-align: top;
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

.btn-area input[type="button"] {
	background-color: #3ACDB2;
	color: #fff;
	border: none;
	padding: 10px 20px;
	margin-bottom : 20px;
	border-radius: 4px;
	font-size: 14px;
	cursor: pointer;
	width : 90px;
	transition: background-color 0.3s;
}

.btn-area input[type="button"]:hover {
	background-color: #34b3a1;
}

@media (max-width : 600px) {
	.board-content-table th, .board-content-table td {
		font-size: 14px;
		padding: 10px;
	}
	.btn-area {
		text-align: center;
		margin-top: 20px;
	}
	.btn-area input[type="button"] {
		width: 100%;
	}
}
</style>
</head>

<body>
	<%@ include file="/WEB-INF/views/common/index/indexHeader.jsp"%>
	<section class="board">
		<div class="header-title">보호시설 소식</div>
		<div class="title-detail">보호소에서 안내할 공지사항을 등록해보세요.</div>

		<div class="board-container">
			<table class="board-content-table">
				<tr>
					<th>NO</th>
					<td id="noticeIdx"></td>
					<th>작성일</th>
					<td id="noticeDate"></td>
				</tr>
				<tr>
					<th>작성자</th>
					<td id="noticeWriter">댕봉사</td>
					<th>조회수</th>
					<td id="noticeViews"></td>
				</tr>
				<tr>
					<th>제목</th>
					<td id="noticeTitle" colspan="3"></td>
				</tr>
				<tr>
					<td id="noticeContent" colspan="4">
						<div class="content-box" id="noticeContentBox"></div>
					</td>
				</tr>
			</table>

			<div class="btn-area">
				<input type="button" value="목록으로" id = "goListNotice">
			</div>
		</div>
	</section>