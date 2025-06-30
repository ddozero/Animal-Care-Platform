<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>고객지원</title>
<link rel="stylesheet"
	href="https://cdn.jsdelivr.net/gh/orioncactus/pretendard/dist/web/static/pretendard.css">

<style>
.board {
	font-family: 'Pretendard', sans-serif;
	background-color: #fff;
	padding: 60px 20px 40px;
	margin-top: 50px;
}

.header-title {
	font-size: 24px;
	font-weight: bold;
	margin-top: 50px;
	margin-bottom: 20px;
	text-align: center;
	color: #333;
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

.board-table th:nth-child(2), .board-table td:nth-child(2) {
	text-align: left;
}

.board-table thead {
	font-weight: bold;
}

.board-table tbody tr:hover {
	background-color: #fafafa;
}

.write-bt {
	max-width: 900px;
	margin: 20px auto 0;
	text-align: right;
}

.write-bt input[type="button"] {
	background-color: #000;
	color: #fff;
	padding: 10px 20px;
	border: none;
	font-size: 14px;
	border-radius: 4px;
	cursor: pointer;
	transition: background-color 0.2s ease;
}

.write-bt input[type="button"]:hover {
	background-color: #444;
}

.paging {
	text-align: center;
	margin-top: 30px;
	font-size: 14px;
}

@media ( max-width : 600px) {
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

<script src="${pageContext.request.contextPath}/resources/web/common/commonUtils.js"></script>
<script>
async function noticeList(cp) {
    const tbody = document.getElementById("noticeBody");
    tbody.innerHTML = ""; // 초기화

    const form = document.getElementById("searchForm");
    const params = new URLSearchParams(new FormData(form));
    params.set("cp", cp); // 페이지 번호

    const result = await API.get("/care/api/support?" + params.toString());

    if (result.status !== 200) {
      alert("공지사항 목록을 불러오는 데 실패했습니다. (" + result.message + ")");
      return;
    }

    const notices = result.data;
    const pageInfo = result.pageInfo;

    if (!notices || notices.length === 0) {
      const row = document.createElement("tr");
      row.innerHTML = '<td colspan="4" style="text-align:center;">등록된 게시글이 없습니다.</td>';
      tbody.appendChild(row);
      return;
    }

    for (const notice of notices) {
      const row = document.createElement("tr");
      row.innerHTML =
        '<td>' + notice.idx + '</td>' +
        '<td><a href="/notice/' + notice.idx + '">' + notice.title + '</a></td>' +
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

  // 검색 시 실행되는 함수
  function searchNotice() {
    noticeList(1);
  }

  // 페이지 로드 시 실행
  document.addEventListener("DOMContentLoaded", () => {
    const searchForm = document.getElementById("searchForm");
    searchForm.addEventListener("submit", function (event) {
      event.preventDefault();
      noticeList(1);
    });

    noticeList(1);
  });
</script>

</head>

<body>
	<%@ include file="/WEB-INF/views/common/index/indexHeader.jsp"%>
	<section class="borad">
		<div>
			<div class="header-title">고객지원</div>
			<div class="banner-notice">
				<img src="#">
			</div>

			<form id="searchForm">
				<div class="search-bar">
					<select name="type">
						<option value="">전체</option>
						<option value="title">제목</option>
						<option value="content">내용</option>
					</select> <input type="text" name="keyword" placeholder="검색어 입력"> <input
						type="submit" value="검색">
				</div>
			</form>
			<div class="board-container">
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
			</div>
			<div class="paging"></div>
			<div class="write-bt">
				<input type="button" value="글쓰기">
			</div>
		</div>
		<div id="noticeListContainer"></div>
		<div id="pagingArea" class="paging"></div>
	</section>


</body>



</html>