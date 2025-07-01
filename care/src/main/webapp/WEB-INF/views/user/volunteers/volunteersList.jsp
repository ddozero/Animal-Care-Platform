<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>봉사</title>

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
	
	<div class="board-container">
			<div class="header-title">봉사하기</div>
			<div class="title-detail">다양한 봉사활동에 참여해보세요.</div>

	<section class="search">
		<form id="searchForm" onsubmit="searchVolunteers(); return false;" style="margin-bottom: 20px;">
				<!-- 1) location -->
				<label for="location">지역:</label> 
				<select id="location" name="location">
					<option value="">전국</option>
					<option value="서울">서울</option>
					<option value="부산">부산</option>
					<option value="대구">대구</option>
					<option value="인천">인천</option>
					<option value="광주">광주</option>
					<option value="대전">대전</option>
					<option value="울산">울산</option>
					<option value="세종">세종</option>
					<option value="경기">경기</option>
					<option value="강원">강원</option>
					<option value="충북">충북</option>
					<option value="충남">충남</option>
					<option value="전북">전북</option>
					<option value="전남">전남</option>
					<option value="경북">경북</option>
					<option value="경남">경남</option>
					<option value="제주">제주</option>
				</select>

				<!-- 2) status -->
				<label for="status">모집상태:</label> 
				<select id="status" name="status">
					<option value="">전체</option>
					<option value="모집중">모집중</option>
					<option value="모집예정">모집예정</option>
					<option value="모집완료">모집완료</option>
				</select>

				<!-- 3) shelterType -->
				<label for="companyType">업체:</label> 
				<select id="companyType" name="companyType">
					<option value="">전체</option>
					<option value="공공">공공</option>
					<option value="민간">민간</option>
					<option value="사설">사설</option>
				</select>

				<!-- 4) type -->
				<label for="volunteerType">봉사 유형:</label> 
				<select id="volunteerType" name="volunteerType">
					<option value="">전체</option>
					<option value="산책봉사">산책봉사</option>
					<option value="청소봉사">청소봉사</option>
					<option value="기타">기타</option>
				</select>

				<!-- 5) time -->
				<label for="hours">인정시간:</label> <select id="hours" name="hours">
					<option value="">전체</option>
					<option value="1">1시간</option>
					<option value="2">2시간</option>
					<option value="3">3시간</option>
					<option value="4">4시간</option>
					<option value="5">5시간</option>
				</select>

				<!-- 6) shelter -->
				<label for="companyName">업체명:</label> 
				<input type="text" id="companyName" name="companyName" placeholder="업체명을 입력하세요">
				
				<!--7) volunteerDate -->
				<label for="volunteerDate">봉사일:</label>
				<input type="date" id="volunteerDate" name="volunteerDate">
				

				<button type="submit">검색</button>
			</form>
	</section>
	<section class="borad">
		<div class="board-container">

			<table class="board-table">
				<thead>
					<tr>
						<th>등록일</th>
						<th>제목</th>
						<th>보호시설명</th>
						<th>기관</th>
						<th>장소</th>
						<th>상태</th>
						<th>봉사시간</th>
						<th>봉사일</th>
					</tr>
				</thead>

			<tbody id="volunteerBody"></tbody>
			</table>
			<div class="paging"></div>
			<div id="volunteersListContainer"></div>
			<div id="pagingArea" class="paging"></div>
			<script>
	            window.addEventListener("DOMContentLoaded", function () {
	                volunteerList(1);
	            });
        	</script>
		</div>
	</section>
	</div>

	<script src="${pageContext.request.contextPath}/resources/web/common/commonUtils.js"></script>
	<script>
	async function volunteerList(cp) {
    const tbody = document.getElementById("volunteerBody");
    tbody.innerHTML = ""; 

    const form = document.getElementById("searchForm");
    const params = new URLSearchParams(new FormData(form));
    params.set("cp", cp);  // 현재 페이지 번호 세팅

    try {
        const result = await API.get("/care/api/volunteers?" + params.toString());

        if (result.status !== 200 || !Array.isArray(result.data)) {
            const row = document.createElement("tr");
            row.innerHTML = '<td colspan="8" style="text-align:center;">등록된 봉사활동이 없습니다.</td>';
            tbody.appendChild(row);
            return;
        }
        
        const volunteers = result.data;
        const pageInfo = result.pageInfo;


        for (const v of volunteers) {
            const row = document.createElement("tr");
            row.innerHTML =
                '<td>' + v.createdAt + '</td>' +
                '<td><a href="/care/volunteers/' + v.idx + '">' + v.title + '</a></td>' +
                '<td>' + v.shelter + '</td>' +
                '<td>' + v.shelterType + '</td>' +
                '<td>' + v.location + '</td>' +
                '<td>' + v.status + '</td>' +
                '<td>' + v.time + '시간</td>' +
                '<td>' + v.volunteerDate + '</td>';
            tbody.appendChild(row);
        }

     
        if (pageInfo) {
            makePaging(
                pageInfo.totalCnt,
                pageInfo.listSize,
                pageInfo.pageSize,
                pageInfo.cp,
                "pagingArea",      
                volunteerList    
            );
        }

    } catch (error) {
        console.error(error);
        alert("서버와 통신 중 오류가 발생했습니다.");
      }
    }
	  // 검색 버튼 누르면 1페이지부터 조회
	  function searchVolunteers() {
	    volunteerList(1);
	  }

</script>
</body>
</html>