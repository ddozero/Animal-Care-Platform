<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>보호시설 관리</title>
<style>
:root {
  --brand: #3ACDB2;
  --gray-1: #f9f9f9;
  --gray-2: #DBDBDB;
  --gray-3: #eee;
  --text-main: #333;
}
* { box-sizing: border-box; }
body {
  margin: 0;
  font-family: 'Pretendard', sans-serif;
  background: #fff;
  color: var(--text-main);
  line-height: 1.55;
}
.container {
  max-width: 1200px;
  margin: 60px auto;
  padding: 40px 20px;
  border: 1px solid var(--gray-2);
  border-radius: 8px;
  background: #fff;
}

.content {
  display: flex;
  gap: 40px;
  flex-wrap: wrap;
  align-items: flex-start;
}
.photo-box {
  width: 250px;
  height: 280px;
  background: var(--gray-3);
  border-radius: 8px;
  overflow: hidden;
  display: flex;
  align-items: center;
  justify-content: center;
}
.photo-box img {
  width: 100%;
  height: 100%;
  object-fit: cover;
}
.upload-button {
  margin-top: 8px;
  background: #eee;
  border: none;
  border-radius: 6px;
  padding: 8px 16px;
  cursor: pointer;
}
.upload-button:hover { 
  background: #ddd; 
}

.info-section div {
  margin-bottom: 12px;
  font-size: 16px;
  margin-left : 20px;
}
.info-section span.label {
  display: inline-block;
  min-width: 100px;
  font-weight: 600;
}
.tabs {
  display: flex;
  justify-content: center;
  gap: 8px;
  margin: 30px 0;
}
.tab-btn {
  border: 1px solid var(--gray-2);
  border-radius: 8px;
  padding: 10px 20px;
  background: #fff;
  cursor: pointer;
  font-weight: 600;
  transition: 0.3s;
}
.tab-btn:hover { background: var(--gray-1); }
.tab-btn.active {
  background: var(--brand);
  border-color: var(--brand);
  color: #fff;
}
.bottom-box {
  margin-top: 40px;
  padding: 20px;
  border-radius: 12px;
  background: var(--gray-1);
  min-height: 150px;
}
.bottom-box span {
  display: block;
  font-size: 16px;
}
@media(max-width: 768px) {
  .content { flex-direction: column; }
  .tabs { flex-direction: column; gap: 12px; }
  .edit-button { width: 100%; }
}

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
  margin-bottom: 20px;
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
.board-table tbody tr:hover {
  background-color: #fafafa;
}

.info-box-header {
  display: flex;
  align-items: center;
  margin-bottom: 10px;
}

.edit-button {
  background: #ddd;
  border: none;
  border-radius: 6px;
  padding: 8px 16px;
  cursor: pointer;
  transition: background 0.25s;
  margin-left: auto; /* 오른쪽 끝에 배치 */
  color: var(--text-main);
}
.edit-button:hover {
  background: #ccc;
}

#shelterName {
  font-size: 25px;    
  font-weight: 700;    
  display: block;      
  margin-bottom: 20px; 
}

/* ===== 서브 탭 (봉사리뷰 / 입양리뷰) ===== */
.sub-tabs {
    display: flex;
    justify-content: center;
    width: 100%;
    gap: 12px;
    margin-top: 16px;
    margin-bottom: 20px;
}

.sub-btn {
    appearance: none;
    border: none;
    background: transparent;
    padding: 6px 14px;
    font-size: 14px;
    font-weight: 600;
    color: #666;
    cursor: pointer;
    border-bottom: 2px solid transparent;
    transition: color .2s, border-color .2s;
}

.sub-btn:hover {
    color: #3acdb2;
}

.sub-btn.active {
    color: #3acdb2;
    border-color: #3acdb2;
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


</style>
</head>
<body>
<%@ include file="/WEB-INF/views/common/index/indexHeader.jsp"%>

<section class="container">

  <div class="content">
    <div>
      <div class="photo-box">
        <img id="shelterImage" src="" alt="보호소 사진">
      </div>
      <button class="upload-button">사진업로드</button>
    </div>
    <div class="info-section">
      <div><span id="shelterName"></span></div>
      <div><span class="label">담당자</span> <span id="personName"></span></div>
      <div><span class="label">분류</span> <span id="type"></span></div>
      <div><span class="label">사업자번호</span> <span id="businessNumber"></span></div>
      <div><span class="label">주소</span> <span id="address"></span></div>
      <div><span class="label">연락처</span> <span id="tel"></span></div>
      <div><span class="label">이메일</span> <span id="email"></span></div>
    </div>
  </div>

  <!-- 탭 버튼 -->
  <div class="tabs">
    <button class="tab-btn active" onclick="changeTab(this, 'infoSection')">정보관리</button>
    <button class="tab-btn" onclick="changeTab(this, 'noticeSection')">보호시설 공지사항</button>
    <button class="tab-btn" onclick="changeTab(this, 'reviewSection')">리뷰관리</button>
  </div>

  <!-- 탭 컨텐츠 -->
	<div id="infoSection" style="display:block;" class="bottom-box info-box">
  		<div class="info-box-header">
    		<strong>보호시설 소개</strong>
    	<button class="edit-button" id="editButton">수정하기</button>
  		</div>
  		<span id="description"></span>
	</div>
	
	  <div id="noticeSection" style="display:none;" class="notice-box">
	    <div id="noticeContent"></div>
	  </div>
	  
	
	 <div id="reviewSection" style="display:none;" class="review-box">
		<div class="sub-tabs">
		   <button class="sub-btn active" onclick="changeReviewTab(this, 'volunteerReview')">봉사리뷰</button>
		   <button class="sub-btn" onclick="changeReviewTab(this, 'adoptionReview')">입양리뷰</button>
		</div>
		<div id="volunteerReview" class="review-content"></div>
		<div id="adoptionReview" class="review-content" style="display:none;"></div>
	</div>
	
	<div id="noticePaging" class="paging"></div>
	<div id="reviewPagingVol" class="paging"></div>
	<div id="reviewPagingAod" class="paging"></div>

</section>

<script src="${pageContext.request.contextPath}/resources/web/common/commonUtils.js"></script>
<script>

// 보호소 정보 불러오기
function ShelterInfo() {
  var xhr = new XMLHttpRequest();
  xhr.open("GET", "/care/api/management/shelter", true);
  xhr.onreadystatechange = function() {
    if (xhr.readyState === 4) {
      var result = JSON.parse(xhr.responseText);
      if (!result || result.status !== 200 || !result.data) {
        alert(result && result.message ? result.message : "존재하지 않는 보호소입니다.");
        return;
      }
      var shelter = result.data;

      var img = document.getElementById("shelterImage");
      if (shelter.imagePaths && shelter.imagePaths.length > 0 && shelter.imagePaths[0]) {
          img.src = "${pageContext.request.contextPath}" + shelter.imagePaths[0];
        } else {
          img.src = "/resources/images/no-image.png";
        }

      document.getElementById("shelterName").textContent = shelter.shelterName;
      document.getElementById("personName").textContent = shelter.personName;
      document.getElementById("type").textContent = shelter.type;
      document.getElementById("businessNumber").textContent = shelter.businessNumber;
      document.getElementById("address").textContent = shelter.address;
      document.getElementById("tel").textContent = shelter.tel;
      document.getElementById("email").textContent = shelter.email;
      document.getElementById("description").textContent = shelter.description;
    }
  };
  xhr.send();
}

//공지사항
function loadNotice() {
	  const wrapper = document.getElementById("noticeContent");
	  wrapper.innerHTML = "";
	  
	  const pagingBox = document.getElementById("noticePaging");
	  pagingBox.innerHTML = ""; 
	    
	  var xhr = new XMLHttpRequest();
	  xhr.open("GET", "/care/api/management/shelter/boards", true);
	  xhr.onreadystatechange = function() {
	    if (xhr.readyState === 4) {
	      var result = JSON.parse(xhr.responseText);
	      if (result.status !== 200) {
	        alert("공지사항 불러오기 실패: " + result.message);
	        return;
	      }
	      var notices = result.data;
	      var pageInfo = result.pageInfo;

	      var html = '<table class="board-table"><thead><tr>';
	      html += '<th>NO</th><th>제목</th><th>작성일</th><th>조회수</th></tr></thead><tbody>';

	      if (!notices || notices.length === 0) {
	        html += '<tr><td colspan="4" style="text-align:center;">등록된 게시글이 없습니다.</td></tr>';
	      } else {
	        for (var i=0; i<notices.length; i++) {
	          html += '<tr>';
	          html += '<td>' + notices[i].idx + '</td>';
	          html += '<td><a href="/care/shelter/boards/' + notices[i].idx + '">' + notices[i].title + '</a></td>';
	          html += '<td>' + notices[i].createdAt + '</td>';
	          html += '<td>' + notices[i].views + '</td>';
	          html += '</tr>';
	        }
	      }
	      html += '</tbody></table>';

	      document.getElementById("noticeContent").innerHTML = html;

          // **페이징**
          

          makePaging(
              pageInfo.totalCnt,
              pageInfo.listSize,
              pageInfo.pageSize,
              pageInfo.cp,
              "noticePaging",
              loadNotice
          );
      }
  };
	  xhr.send();
}
	
	


function changeTab(button, sectionId) {
	  var tabs = document.getElementsByClassName("tab-btn");
	  for (var i=0; i<tabs.length; i++) {
	    tabs[i].classList.remove("active");
	  }
	  button.classList.add("active");

	  var sections = ["infoSection", "noticeSection", "reviewSection"];
	  for (var j=0; j<sections.length; j++) {
	    document.getElementById(sections[j]).style.display = (sections[j] === sectionId) ? "block" : "none";
	  }
	  
	  document.getElementById("noticePaging").style.display = (sectionId === "noticeSection") ? "block" : "none";
	  document.getElementById("reviewPagingVol").style.display = (sectionId === "reviewSection") ? "block" : "none";
	  document.getElementById("reviewPagingAod").style.display = (sectionId === "reviewSection") ? "block" : "none";

	  if(sectionId === "noticeSection") {
	    loadNotice();
	  }
	  if(sectionId === "reviewSection") {
	    changeReviewTab(
	      document.querySelector(".sub-tabs .sub-btn.active"),
	      "volunteerReview"
	    );
	  }
	}



window.addEventListener("DOMContentLoaded", function() {
  ShelterInfo();
});

//보호소 리뷰 관리
async function loadVolunteerReview(cp = 1) {
    const wrapper = document.getElementById("volunteerReview");
    wrapper.innerHTML = "";

 	const pagingBox = document.getElementById("reviewPagingVol");
    pagingBox.innerHTML = ""; // 초기화
    
    const result = await API.get('/care/api/management/shelter/reviews/volunteer?cp=' + cp);
    if (result.status != 200) {
        alert("리뷰 데이터를 불러올 수 없습니다.");
        return;
    }

    const reviews = result.data;
    const pageInfo = result.pageInfo;

    for (const review of reviews) {
        const card = document.createElement("div");
        card.className = "review-card";
        card.innerHTML =
            '<img src="${pageContext.request.contextPath}' + review.imagePath + '" ' +
            'alt="' + review.nickName + '" width="120" height="120">' +
            '<div class="content" style="white-space:pre-wrap;">' + review.content + '</div>' +
            '<div class="meta">' + review.nickName + ' · ' + review.createdAt + '</div>';
        card.addEventListener("click", () => card.classList.toggle("expanded"));

        if (review.turn != 0) {
            card.style.marginLeft = (review.turn * 30) + "px"; // turn 값 × 30px 만큼 들여쓰기
            card.style.backgroundColor = "#f9f9f9";             // 연한 배경
            card.style.borderLeft = "3px solid #ccc";          // 구분선
        }
        wrapper.appendChild(card);
    }


    // paging 
  
    
    makePaging(
            pageInfo.totalCnt,
            pageInfo.listSize,
            pageInfo.pageSize,
            pageInfo.cp,
            "reviewPagingVol", 
            loadVolunteerReview
    );
}


async function loadAdoptionReview(cp = 1) {
    const wrapper = document.getElementById("adoptionReview");
    wrapper.innerHTML = "";
    
    // paging
    const pagingBox = document.getElementById("reviewPagingAod");
    pagingBox.innerHTML = ""; // 초기화

    const result = await API.get('/care/api/management/shelter/reviews/adoption?cp=' + cp);
    if (result.status != 200) {
        alert("리뷰 데이터를 불러올 수 없습니다.");
        return;
    }

    const reviews = result.data;
    const pageInfo = result.pageInfo;

    for (const review of reviews) {
        const card = document.createElement("div");
        card.className = "review-card";
        card.innerHTML =
            '<img src="${pageContext.request.contextPath}' + review.imagePath + '" ' +
            'alt="' + review.nickName + '" width="120" height="120">' +
            '<div class="content" style="white-space:pre-wrap;">' + review.content + '</div>' +
            '<div class="meta">' + review.nickName + ' · ' + review.createdAt + '</div>';
        card.addEventListener("click", () => card.classList.toggle("expanded"));

        if (review.turn != 0) {
            card.style.marginLeft = (review.turn * 30) + "px"; // turn 값 × 30px 만큼 들여쓰기
            card.style.backgroundColor = "#f9f9f9";             // 연한 배경
            card.style.borderLeft = "3px solid #ccc";          // 구분선
        }
        wrapper.appendChild(card);
    }

    makePaging(
            pageInfo.totalCnt,
            pageInfo.listSize,
            pageInfo.pageSize,
            pageInfo.cp,
            "reviewPagingAod", // 페이지 버튼이 들어갈 div id
            loadAdoptionReview
    );
}


function changeReviewTab(button, sectionId) {
	  document.querySelectorAll('.sub-btn').forEach(b => b.classList.remove('active'));
	  button.classList.add('active');

	  document.getElementById('volunteerReview').style.display = 'none';
	  document.getElementById('adoptionReview').style.display = 'none';

	  document.getElementById(sectionId).style.display = 'block';
	  
	  document.getElementById("reviewPagingVol").style.display = (sectionId === "volunteerReview") ? "block" : "none";
	  document.getElementById("reviewPagingAod").style.display = (sectionId === "adoptionReview") ? "block" : "none";

	  // 선택된 탭에 맞는 ajax 실행
	  if (sectionId === 'volunteerReview') {
	    loadVolunteerReview();
	  } else if (sectionId === 'adoptionReview') {
	    loadAdoptionReview();
	  }
	}




</script>
</body>
</html>
