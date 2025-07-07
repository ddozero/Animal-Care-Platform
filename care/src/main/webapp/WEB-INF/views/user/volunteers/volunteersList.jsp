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
    margin-bottom:30px;
}

.paging button:hover {
   background: #3acdb2;
   color: #fff;
}


.search {
  background-color: #f7f7f9;
  padding: 24px;
  border-radius: 16px;
  margin-bottom: 40px;
}

#searchForm {
  display: flex;
  flex-wrap: wrap;
  gap: 16px 24px;
}

.search-row {
  display: flex;
  flex-wrap: wrap;
  gap: 16px 24px;
  width: 100%;
}

.search-group {
  display: flex;
  flex-direction: column;
  flex: 1 1 160px;
  min-width: 140px;
}

.search-group label {
  font-size: 15px;
  margin-bottom: 6px;
  color: #333;
  font-weight: bold;
}

.search-group input,
.search-group select {
  padding: 8px 12px;
  border: 1px solid #ddd;
  border-radius: 8px;
  font-size: 14px;
  background-color: #fff;
}

#searchForm button[type="submit"] {
  padding: 10px 20px;
  background-color: #3acdb2;
  color: #fff;
  border: none;
  border-radius: 8px;
  font-weight: bold;
  cursor: pointer;
  align-self: flex-end;
}

#searchForm button[type="submit"]:hover {
  background-color: #32b8a1;
}

@media (max-width: 768px) {
  .search-row {
    flex-direction: column;
  }
  .search-group {
    width: 100%;
  }

}

.badge-type {
  display: inline-block;
  padding: 4px 10px;
  font-size: 13px;
  color: #555;
  background-color: #eee;
  border-radius: 20px;
  font-weight: 500;
   margin: 0 auto;
   text-align: center;
}

.reset-link {
    font-size: 14px;
    color: #666;
    text-decoration: underline;
    cursor: pointer;
}

.btn-area {
  display: flex;
  justify-content: flex-end;
  align-items: center;
  gap: 12px;
  margin-top: 8px;
}

.filter-buttons {
  text-align: left !important; /* 기존 right 무효화 */
  margin: 10px 0;
}

.date-btn {
  padding: 10px 20px;
  background-color: #3acdb2;
  color: #fff;
  border: none;
  border-radius: 8px;
  font-weight: bold;
  cursor: pointer;
}

.date-btn:hover {
  background-color: #32b7a1;
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
    
	    <div class="search-row">
	      <div class="search-group">
	        <label for="location">지역</label>
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
	      </div>
	
	      <div class="search-group">
	        <label for="status">모집상태</label>
	        <select id="status" name="status">
	          <option value="">전체</option>
	          <option value="모집중">모집중</option>
	          <option value="모집예정">모집예정</option>
	          <option value="모집완료">모집완료</option>
	          <option value="종료">종료</option>
	        </select>
	      </div>
	
	      <div class="search-group">
	        <label for="companyType">업체</label>
	        <select id="companyType" name="shelterType">
	          <option value="">전체</option>
	          <option value="공공">공공</option>
	          <option value="민간">민간</option>
	          <option value="사설">사설</option>
	        </select>
	      </div>
	
	      <div class="search-group">
	        <label for="volunteerType">봉사 유형</label>
	        <select id="volunteerType" name="type">
	          <option value="">전체</option>
	          <option value="산책봉사">산책봉사</option>
	          <option value="청소봉사">청소봉사</option>
	          <option value="미용봉사">미용봉사</option>
	          <option value="기타">기타</option>
	        </select>
	      </div>
	
	      <div class="search-group">
	        <label for="hours">인정시간</label>
	        <select id="hours" name="time">
	          <option value="">전체</option>
	          <option value="1">1시간</option>
	          <option value="2">2시간</option>
	          <option value="3">3시간</option>
	          <option value="4">4시간</option>
	          <option value="5">5시간</option>
	          <option value="6plus">6시간이상</option>
	        </select>
	      </div>
	    </div>
	
	   <div class="search-row">
	      <div class="search-group">
	        <label for="companyName">업체명</label>
	        <input type="text" id="companyName" name="shelter" placeholder="업체명을 입력하세요">
	      </div>
	
	      <div class="search-group">
	        <label for="volunteerDate">봉사일</label>
	        <input type="date" id="volunteerDate" name="volunteerDate">
	      </div>
	      
		 <div class="btn-area">
		      <div class="search-group-bt">
		      	<a class="reset-link" onclick="resetSearchForm()">검색 설정 초기화</a>
		        <button type="submit">검색</button>
		      </div>
	    </div>
	   </div>
  </form>
  
</section>

	<section class="borad">

		<div class="board-container">
			<div class="filter-buttons" style="text-align: right; margin: 10px 0;">
				<button type="button" class="date-btn">일정별 조회</button>
			</div>

			<table class="board-table">
				<thead>
					<tr>
						<th>등록일</th>
						<th>유형</th>
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

        if (result.status !== 200 || !Array.isArray(result.data) || result.data.length === 0) {
            const row = document.createElement("tr");
            row.innerHTML = '<td colspan="9" style="text-align:center;">등록된 봉사활동이 없습니다.</td>';
            tbody.appendChild(row);
            return;
        }
        
        const volunteers = result.data;
        const pageInfo = result.pageInfo;


        for (const v of volunteers) {
        	
        	
            const row = document.createElement("tr");
            row.innerHTML =
                '<td>' + v.createdAt + '</td>' +
                '<td><span class="badge-type">' + v.type + '</span></td>' +
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
	  
	  function resetSearchForm() {
		  document.getElementById('searchForm').reset();
		  volunteerList(1); // 초기화 후 봉사목록 1페이지 호출
		}

</script>

<script>
	  const contextPath = "${pageContext.request.contextPath}";
	
	  document.querySelector(".date-btn").addEventListener("click", function () {
	    location.href = contextPath + "/volunteers/calendar";
	  });
</script>

<%@ include file="/WEB-INF/views/common/index/indexFooter.jsp" %>
</body>
</html>