<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>보호시설 공지사항</title>
</head>
<style>
.board {
	font-family: 'Pretendard', sans-serif;
	background-color: #fff;
	padding: 60px 20px 40px;
	margin-top: 50px;
}

.header-title {
	font-size: 28px;
  	font-weight: bold;
  	margin-top: 50px;
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
}

.board-table {
	width: 100%;
	border-collapse: collapse;
	font-size: 16px;
}

.board-table a {
	text-decoration: none;
	color: inherit;
	cursor: pointer;
}

.board-table th {
	padding: 14px 10px;
	border-bottom: 2px solid #DBDBDB;
	text-align: center;
	color: #333;
}

.board-table td {
	padding: 14px 10px;
	border-bottom: 1px solid #eee;
	text-align: center;
	color: #333;
}

.board-table td:nth-child(2) {
	text-align: left;
}

.board-table thead {
	font-weight: bold;
}

.board-table tbody tr:hover {
	background-color: #fafafa;
}

#searchForm {
  max-width: 1200px; 
  margin: 0 auto;
  padding-bottom: 20px;
  box-sizing: border-box;
}

.search-bar {
    display: flex;
    gap: 5px;
    align-items: center;
    max-width: 280px; 
    box-sizing: border-box;
}

.search-bar select,
.search-bar input[type="text"] {
    border: 1px solid #ccc;
    border-radius: 4px;
    padding: 8px 12px;
    font-size: 14px;
    outline: none;
    box-sizing: border-box;
}

.search-bar select {
    width: 80px;
    cursor: pointer;
    background-color: white;
}

.search-bar input[type="text"] {
    flex-grow: 1;
    color: #999;
}

.search-bar input[type="submit"] {
    background-color: #d1d1d1;
    border: none;
    border-radius: 4px;
    font-weight: bold;
    color: #333;
    padding-left : 20px;
    padding-right : 20px;
    cursor: pointer;
    transition: background-color 0.3s;
    height: 35px;
}

.search-bar input[type="submit"]:hover {
    background-color: #aaa;
 }


.paging {
      margin: 28px 0;
      text-align: center;
}

.paging button {
    border: none;
    background: #fff;
    padding: 6px 12px;
    margin: 0 2px;
    border-radius: 4px;
    cursor: pointer;
    box-shadow: 0 1px 4px rgba(0, 0, 0, .08);
    transition: background .2s;
}

.paging button:hover {
   background: #3acdb2;
   color: #fff;
}

@media (max-width : 600px) {
	.search-bar {
		flex-direction: column;
		gap: 10px;
	}
	.board-table th, .board-table td {
		font-size: 12px;
		padding: 10px 6px;
	}
	.write-bt input[type="button"] {
		width: 100%;
	}
}
</style>
</head>
<body>
<%@ include file="/WEB-INF/views/common/index/indexHeader.jsp"%>
	<section class="borad">
	

		<div class="board-container">
			<div class="header-title">보호시설 소식</div>
			<div class="title-detail">보호소에서 안내할 공지사항을 등록해보세요.</div>
			
		<!-- 상단 메뉴 -->
		<div class="nav">
			<button id="infoBtn" onclick="location.href='/care/shelter/manage'">정보관리</button>
			<button id="noticeBtn" onclick="location.href='/care/shelter/board'">공지사항</button>
			<button id="reviewBtn" onclick="location.href='/care/shelter/review'">리뷰관리</button>
		</div>
			

			<table class="board-table">
				<thead>
					<tr>
						<th>NO</th>
						<th>제목</th>
						<th>작성일</th>
						<th>조회수</th>
					</tr>
				</thead>

				<tbody id="noticeBody">
					<tr>
						<td colspan="5" style="text-align: center;">등록된 게시글이 없습니다.</td>
					</tr>
					<tr>
						<td></td>
						<td></td>
						<td></td>
						<td></td>
					</tr>
				</tbody>
			</table>
			<div class="write-bt">
  				<input type="button" value="글쓰기" onclick="location.href='/care/shelter/board/write'">
			</div>
			<div class="paging"></div>

			<div id="noticeListContainer"></div>
			<div id="pagingArea" class="paging"></div>
		</div>
	</section>
	
<script src="${pageContext.request.contextPath}/resources/web/common/commonUtils.js"></script>
<script>
async function noticeList(cp) {
    const tbody = document.getElementById("noticeBody");
    tbody.innerHTML = ""; // 초기화
    
    const params = new URLSearchParams();
    params.set("cp", cp); // 페이지 번호

    const result = await API.get("/care/api/management/shelter/boards?" + params.toString());

    if (result.status !== 200) {
      alert("공지사항 목록을 불러오는 데 실패했습니다. (" + result.message + ")");
      return;
    }

    const notices = result.data;
    const pageInfo = result.pageInfo;

    if (!notices || notices.length === 0) {
      const row = document.createElement("tr");
      row.innerHTML = '<td colspan="5" style="text-align:center;">등록된 게시글이 없습니다.</td>';
      tbody.appendChild(row);
      return;
    }

    for (const notice of notices) {
      const row = document.createElement("tr");
      row.innerHTML =
        '<td>' + notice.idx + '</td>' +
        '<td><a href="support/' + notice.idx + '">' + notice.title + '</a></td>' +
        '<td>' + notice.createdAt + '</td>' +
        '<td>' + notice.views + '</td>';
      tbody.appendChild(row);
    }

    // 페이징 함수 호출
    makePaging(
      pageInfo.totalCnt,
      pageInfo.listSize,
      pageInfo.pageSize,
      pageInfo.cp,
      "pagingArea",
      noticeList
    );
  }
  
	document.addEventListener("DOMContentLoaded", function() {
	  noticeList(1);
	});


</script>	


</body>
</html>