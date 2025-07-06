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


body {
  font-family: 'Pretendard', sans-serif;
  background: #fff;
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
  width: 270px;
  height: 300px;
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

.tab-btn:hover { 
  background: var(--gray-1); 
}

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
  .content { 
    flex-direction: column; 
  }
}

.board {
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

.edit-button, #saveButton {
  background: #ddd;
  border: none;
  border-radius: 6px;
  padding: 8px 16px;
  cursor: pointer;
  transition: background 0.25s;
  margin-left: auto; /* 오른쪽 끝에 배치 */
  color: var(--text-main);
}

.edit-button:hover, #saveButton:hover {
  background: #ccc;
}

#shelterName {
  font-size: 28px;    
  font-weight: 700;    
  display: block;      
  margin-bottom: 40px; 
  margin-top: 10px;
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

/* Paging */
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

/** 보호소 정보 **/
input:disabled {
  all: unset;
  border: none;
  background-color: transparent;
  color: #333;
}

input:enabled {
  border: 1px solid #DBDBDB !important;
  background-color: #fff !important;
}

label {
  font-weight: 700;  
  display: inline-block;
  margin-right : 8px;
}

.info-section div {
  margin-bottom: 12px; 
}

input {
  width: 100%;  
  padding: 10px;
  margin-top: 4px;  
  border-radius: 4px;
  background-color: #fff;
  font-size: 16px; /* 기본 폰트 크기 설정 */
}

input[readonly] {
  border: none !important; 
  background-color: transparent !important; 
  color: #333; 
}

input[readonly]:focus {
  outline: none;                       
  border: 1px solid #DBDBDB !important; 
}

/* 보호시설 소개 */
textarea {
  width: 100%;  
  padding: 10px;
  font-size: 16px;
  font-family: 'Pretendard', sans-serif;
  border-radius: 4px;
  border: 1px solid #DBDBDB;
  background-color: #fff;
  resize: vertical;  
}

textarea:disabled {
  background-color: #fff; /* disabled 상태일 때 색상 변경 */
  border : none;
}


/*리뷰 답글 */

.review-card {
  margin-top: 20px;
  margin-bottom: 10px;
}

.review-card::after {
  content: "";
  display: block;
  clear: both;
}

.reply-card {
  margin-top: 10px;
  margin-bottom: 10px;
  background: #fafafa;
}

.reply-form {
    margin-top: 10px;
    padding-top: 10px;
    display: flex;              
    flex-direction: column;      
    align-items: flex-end;       
}

.reply-input {
    width: 100%;
    height: 60px;
    padding: 10px;
    margin-bottom: 10px;
    border-radius: 4px;
    border: 1px solid #ccc;
    resize: none;
}

.reply-submit {
    background-color: #007bff;
    color: white;
    border: none;
    border-radius: 4px;
    cursor: pointer;
    margin-left: auto;
    margin-bottom:10px;
}

.reply-submit:hover {
    background-color: #0056b3;
}

/* action-text에 스타일 적용 */
.action-text {
    display: flex;
    justify-content: flex-end; 
    margin-top: 5px;
    font-size: 0.9em;
    color: #555;
}

/* 수정/삭제 텍스트 스타일 */
.edit-text, .delete-text {
    display: inline-block;
    background-color: #aaa;    
    color: #fff;                
    font-size: 12px;            
    padding: 4px 8px;           
    border-radius: 8px;       
    cursor: pointer;
    margin-left: 6px;
    text-decoration: none;
    transition: background 0.2s;
}
.edit-text:hover, .delete-text:hover {
    background-color: #888;
}



</style>
</head>
<body>
 <%@ include file="/WEB-INF/views/common/management/managementHeader.jsp" %>

<section class="container">

  <div class="content">
   <div>
 	 <div class="photo-box">
	    <img id="shelterImage" src="" alt="보호소 사진">
	  </div>
	  <button class="upload-button" id="uploadButton">사진 업로드</button>
	  <input type="file" id="uploadInput" accept="image/*" style="display:none;" />
	</div>
     <div class="info-section">
           <div>
		      <input type="text" id="shelterName" value="" disabled />
		    </div>
		    <div>
		      <label for="personNameInput" class="label">담당자</label>
		      <input type="text" id="personName" value="" disabled />
		    </div>
		    <div>
		      <label for="typeInput" class="label">분류</label>
		      <input type="text" id="type" value="" disabled />
		    </div>
		    <div>
		      <label for="businessNumberInput" class="label">사업자번호</label>
		      <input type="text" id="businessNumber" value="" disabled/>
		    </div>
			<div>
			  <label for="addressInput" class="label">주소</label>
			  <span>(</span>
			  <input type="text" id="zipCode" value="shelter.zipCode" disabled style="width: 13%; padding: 0; margin: 0; text-align:center;"/>
			  <span>)</span>
			  <input type="text" id="address" value="shelter.address" disabled style="padding: 0; margin: 0;"/>
			</div>
			<div>
			  <label for="addressInput" class="label">상세주소</label>
			  <input type="text" id="addressDetail" value="shelter.addressDetail" disabled style="width: 10%; max-width: 200px; padding: 0; margin: 0;"/>
			</div>
		    <div>
		      <label for="telInput" class="label">연락처</label>
		      <input type="text" id="tel" value="" disabled />
		    </div>
		    <div>
		      <label for="emailInput" class="label">이메일</label>
		      <input type="email" id="email" value="" disabled />
		    </div>
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
	    	<button class="edit-button" id="editButton" onclick="enableEdit()">수정하기</button>
	    	<button id="saveButton" onclick="saveChanges()" style="display:none;">저장하기</button>
  		</div>
  			<div>
		       <textarea id="description" rows="4" disabled></textarea>
		    </div>
	</div>
	
	  <div id="noticeSection" style="display:none; text-align:right; margin-bottom:13px;" class="notice-box">
		    <button class="edit-button" id="addButton" onclick="addNotice()">등록하기</button>
		    <div id="noticeContent"></div>
	</div>
	  
	
	 <div id="reviewSection" style="display:none;" class="review-box">
		<div class="sub-tabs">
		   <button class="sub-btn active" onclick="changeReviewTab(this, 'volunteerReview')">봉사리뷰</button>
		   <button class="sub-btn" onclick="changeReviewTab(this, 'adoptionReview')">입양리뷰</button>
		</div>
		
		<div id="volunteerReview" class="review-content">
			<button class="edit-button" id="addButton" onclick="addNotice()">등록하기</button>
		
		</div>
		
		
		<div id="adoptionReview" class="review-content" style="display:none;"></div>
	</div>
	
	<div id="noticePaging" class="paging"></div>
	<div id="reviewPagingVol" class="paging"></div>
	<div id="reviewPagingAod" class="paging"></div>

</section>

<script src="${pageContext.request.contextPath}/resources/web/common/commonUtils.js"></script>
<script>

// 보호소 정보 불러오기
var shelterIdx; 

async function ShelterInfo() {
    try {
        const result = await API.get("/care/api/management/shelter");

        if (!result || result.status !== 200 || !result.data) {
            alert(result && result.message ? result.message : "존재하지 않는 보호소입니다.");
            return;
        }

        var shelter = result.data;
        
        shelterIdx = shelter.idx;
        console.log("shelterIdx:", shelterIdx);

        // 이미지 처리
        var img = document.getElementById("shelterImage");

        if (shelter.imagePaths && shelter.imagePaths.length > 0 && shelter.imagePaths[0]) {
            img.src = "${pageContext.request.contextPath}" + shelter.imagePaths[0] + "?" + new Date().getTime();
        }


        // 정보 입력
        document.getElementById("shelterName").value = shelter.shelterName;
        document.getElementById("personName").value = shelter.personName;
        document.getElementById("type").value = shelter.type;
        document.getElementById("businessNumber").value = shelter.businessNumber;
        document.getElementById("zipCode").value = shelter.zipCode;
        document.getElementById("address").value = shelter.address;
        document.getElementById("addressDetail").value = shelter.addressDetail;
        document.getElementById("tel").value = shelter.tel;
        document.getElementById("email").value = shelter.email;
        document.getElementById("description").value = shelter.description;
        
        document.getElementById("noticePaging").style.display = "none";
        document.getElementById("reviewPagingVol").style.display = "none";
        document.getElementById("reviewPagingAod").style.display = "none";

    } catch (error) {
        console.error("보호소 정보를 불러오는 중 오류가 발생했습니다: ", error);
        alert("보호소 정보를 불러오는 데 실패했습니다.");
    }
}


//보호시설 수정
function enableEdit() {
		  document.getElementById("shelterName").disabled = false;
		  document.getElementById("personName").disabled = false;
		  document.getElementById("tel").disabled = false;
		  document.getElementById("email").disabled = false;
		  document.getElementById("description").disabled = false;

		  document.getElementById("saveButton").style.display = "inline-block"; // 수정 완료 후 저장하기 버튼 보이기
		  document.getElementById("editButton").style.display = "none"; // 수정하기 버튼 숨기기
		}

async function saveChanges() {
	  // dto 생성
	  var dto = {
	    shelterName: document.getElementById("shelterName").value,
	    personName: document.getElementById("personName").value,
	    type: document.getElementById("type").value,
	    businessNumber: document.getElementById("businessNumber").value,
	    zipCode: document.getElementById("zipCode").value,  // 우편번호
	    address: document.getElementById("address").value,  // 주소
	    addressDetail: document.getElementById("addressDetail").value,  // 상세주소
	    tel: document.getElementById("tel").value,
	    email: document.getElementById("email").value,
	    description: document.getElementById("description").value,
	  };

	  try {
	    const result = await API.put("/care/api/management/shelter", dto);

	    if (result.status === 200) {
	      alert("정보가 수정되었습니다.");
	      ShelterInfo();  // 수정된 정보 다시 조회하여 화면 갱신
	    } else {
	      alert("수정에 실패했습니다.");
	    }

	    // 입력 필드 비활성화 및 버튼 상태 업데이트
	    document.getElementById("shelterName").disabled = true;
	    document.getElementById("personName").disabled = true;
	    document.getElementById("type").disabled = true;
	    document.getElementById("businessNumber").disabled = true;
	    document.getElementById("zipCode").readonly = true;
	    document.getElementById("address").readonly = true;
	    document.getElementById("addressDetail").readonly = true;
	    document.getElementById("tel").disabled = true;
	    document.getElementById("email").disabled = true;
	    document.getElementById("description").disabled = true;
	    document.getElementById("saveButton").style.display = "none";
	    document.getElementById("editButton").style.display = "inline-block";

	  } catch (err) {
	    console.error("Error while saving changes:", err);
	    alert("수정 중 오류가 발생했습니다.");
	  }
	}
	

	window.addEventListener("DOMContentLoaded", function() {
	  ShelterInfo();
	});
	

//보호시설 사진 업로드
document.getElementById('uploadButton').addEventListener('click', function() {
    if (!shelterIdx) {
        alert('보호소 정보를 먼저 불러온 뒤 시도해주세요.');
        return;
    }
    document.getElementById('uploadInput').click();
});

document.getElementById('uploadInput').addEventListener('change', async function(event) {
    if (!shelterIdx) {
        alert('보호소 정보를 먼저 불러온 뒤 시도해주세요.');
        return;
    }

    const file = event.target.files[0];
    if (!file) return;

    const formData = new FormData();
    formData.append('files', file);

    try {
        const uploadRes = await fetch('/care/api/management/shelter/upload/' + shelterIdx, {
            method: 'POST',
            body: formData
        });

        if (!uploadRes.ok) {
            alert('사진 업로드에 실패했습니다. 관리자에게 문의하세요.');
            return;
        }

        alert('사진이 업로드되었습니다.');
        ShelterInfo();
    } catch (err) {
        console.error(err);
        alert('사진 업로드 중 오류가 발생했습니다.');
    }
});




//공지사항
async function loadNotice(cp) {
    const wrapper = document.getElementById("noticeContent");
    wrapper.innerHTML = ""; // 초기화

    const pagingBox = document.getElementById("noticePaging");
    pagingBox.innerHTML = "";

    try {
        const result = await API.get('/care/api/management/shelter/boards?cp=' + cp);
        
        if (result.status !== 200) {
            alert('공지사항 목록을 불러오는 데 실패했습니다. (' + result.message + ')');
            return;
        }

        const notices = result.data;
        const pageInfo = result.pageInfo;

        let html = '<table class="board-table"><thead><tr>';
        html += '<th>NO</th><th>제목</th><th>작성일</th><th>조회수</th></tr></thead><tbody>';

        if (!notices || notices.length === 0) {
            html += '<tr><td colspan="4" style="text-align:center;">등록된 게시글이 없습니다.</td></tr>';
        } else {
            for (var i = 0; i < notices.length; i++) {
                html += '<tr>';
                html += '<td>' + notices[i].idx + '</td>';
                html += '<td><a href="/care/management/shelters/boards/' + notices[i].idx + '">' + notices[i].title + '</a></td>';
                html += '<td>' + notices[i].createdAt + '</td>';
                html += '<td>' + notices[i].views + '</td>';
                html += '</tr>';
            }
        }

        html += '</tbody></table>';

        document.getElementById('noticeContent').innerHTML = html;

        // 페이징 처리
        makePaging(
            pageInfo.totalCnt,
            pageInfo.listSize,
            pageInfo.pageSize,
            pageInfo.cp,
            'noticePaging',
            loadNotice
        );
    } catch (error) {
        console.error('Error loading notice:', error);
    }
}

// 페이지 로드 시 공지사항 목록을 첫 번째 페이지로 불러오기
document.addEventListener('DOMContentLoaded', function () {
	  ShelterInfo();
	  loadNotice(1);  // 공지사항 첫 페이지도 미리 로드

	  const urlParams = new URLSearchParams(window.location.search);
	  const activeTab = urlParams.get('tab');

	  if (activeTab === 'notice') {
	    const noticeBtn = document.querySelector('.tab-btn:nth-child(2)');
	    changeTab(noticeBtn, 'noticeSection');
	  }
});

function changeTab(button, sectionId) {
    var tabs = document.getElementsByClassName("tab-btn");
    for (var i = 0; i < tabs.length; i++) {
        tabs[i].classList.remove("active");
    }
    button.classList.add("active");

    var sections = ["infoSection", "noticeSection", "reviewSection"];
    for (var j = 0; j < sections.length; j++) {
        document.getElementById(sections[j]).style.display = (sections[j] === sectionId) ? "block" : "none";
    }
    
    document.getElementById("noticePaging").style.display = (sectionId === "noticeSection") ? "block" : "none";
    document.getElementById("reviewPagingVol").style.display = (sectionId === "reviewSection") ? "block" : "none";
    document.getElementById("reviewPagingAod").style.display = (sectionId === "reviewSection") ? "block" : "none";

    if (sectionId === "noticeSection") {
        loadNotice(1); // 첫 페이지로 로드
    }
    if (sectionId === "reviewSection") {
        changeReviewTab(document.querySelector(".sub-tabs .sub-btn.active"), "volunteerReview");
    }
}


/// 공지사항 글쓰기

function addNotice() {
    location.href = "/care/management/shelters/boards/write";
}


//보호소 리뷰 관리

///봉사리뷰
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
        card.id = `review-${review.reviewIdx}`;

        let cardContent = '';

        // 원본 리뷰일 경우 (turn === 0)
        if (review.turn === 0) {
            card.className = "review-card"; // 원본 리뷰용 클래스 설정
            card.style.position = "relative";  
            
            cardContent += 
                '<div class="image-wrapper" style="width: 150px; height: 180px; overflow: hidden; margin-right: 20px; margin-bottom:10px; float: left;">' +
                '<img src="${pageContext.request.contextPath}' + review.imagePath + '" style="width: 100%; height: 100%; object-fit: cover;">' +
                '</div>' +
                '<span class="nickname" style="font-weight: bold;">' + review.nickName + '</span> · ' +
                '<span class="created-at" style="font-size: 0.9em; color: #777;">' + review.createdAt + '</span>';
        } else {
            card.className = "reply-card"; // 답글용 클래스 설정
            cardContent += 
                '<span class="shelterName" style="font-weight: bold;">' + 
                (review.shelterName ? review.shelterName : '정보 없음') + 
                '</span> · ' + 
                '<span class="created-at" style="font-size: 0.9em; color: #777;">' + review.createdAt + '</span>';
        }

        // 공통 내용 추가
        cardContent += 
            '<div class="content" style="white-space:pre-wrap;">' + review.content + '</div>';

        card.innerHTML = cardContent;

        // 카드 스타일

        card.style.padding = "10px";
        
     // 답글 버튼 
        if (review.turn === 0) {
        	const replyButton = document.createElement("button");
            replyButton.innerHTML = "답글 남기기";
            replyButton.className = "edit-button";
            replyButton.style.position = "absolute";
            replyButton.style.top = "10px";
            replyButton.style.right = "10px";
            replyButton.onclick = () => openPopup(review.reviewIdx);
            card.appendChild(replyButton);
        }
     	//답글 등록
        function openPopup(reviewIdx) {
            const url = '/care/management/shelters/volunteerReview/reply?reviewIdx=' + reviewIdx + '&volunteerIdx=' + review.volunteerIdx;
            const width = 600;
            const height = 400;
            const left = (window.innerWidth - width) / 2;
            const top = (window.innerHeight - height) / 2;

            var windowOptions = "width=" + width + ",height=" + height + ",top=" + top + ",left=" + left + ",resizable=yes";
            window.open(url, "blank", windowOptions);
        }

        // 답글일 경우 수정/삭제 버튼 추가
        if (review.turn !== 0) {
            const actionText = document.createElement("div");
            actionText.className = "action-text";
            actionText.innerHTML = 
                '<span class="edit-text" onclick="editReviewPopupVR(' + review.reviewIdx + ')">수정</span>' +
                '<span class="delete-text" onclick="deleteReviewVR(' + review.reviewIdx + ')">삭제</span>';

            actionText.style.position = "absolute";  
            actionText.style.right = "10px";  
            actionText.style.top = "5px";  
            actionText.style.fontSize = "0.9em"; 
            actionText.style.color = "#555"; 
            card.style.position = "relative";
            card.appendChild(actionText);  // 수정/삭제 텍스트를 추가
  
            // 답글에 margin-top과 border 추가
          
            card.style.marginLeft = (review.turn * 8) + "px"; // turn 값 × 들여쓰기
            card.style.borderBottom = "1px solid #ccc";  
            card.style.borderLeft = "3px solid #ccc";    
            card.style.clear = "both"; 
        }
 
        wrapper.appendChild(card);
    }

    makePaging(
        pageInfo.totalCnt,
        pageInfo.listSize,
        pageInfo.pageSize,
        pageInfo.cp,
        "reviewPagingVol", 
        loadVolunteerReview
    );
}


//봉사 리뷰 답글 수정 
function editReviewPopupVR(reviewIdx) {
			const url = '/care/management/shelters/volunteerReview/reply/update?reviewIdx=' + reviewIdx;
			const width = 600;
            const height = 400;
            const left = (window.innerWidth - width) / 2;
            const top = (window.innerHeight - height) / 2;

            var windowOptions = "width=" + width + ",height=" + height + ",top=" + top + ",left=" + left + ",resizable=yes";
            window.open(url, "replyUpdateVR", windowOptions);
}
	
//봉사 리뷰 답글 삭제
async function deleteReviewVR(reviewIdx) {
	if (!confirm("정말 삭제하시겠습니까?")) {
	      return;
	  }
	 const result = await API.delete('/care/api/management/shelter/volunteerReviews/reply/' + reviewIdx);
	  if (result.status === 200) {
	        alert(result.message);
	        loadVolunteerReview();  // 삭제 후 목록 새로고침
	  } else {
	        alert("삭제에 실패하였습니다. 관리자에게 문의하세요");
	   }
}
	


///입양리뷰
async function loadAdoptionReview(cp = 1) {
    const wrapper = document.getElementById("adoptionReview");
    wrapper.innerHTML = "";

    const pagingBox = document.getElementById("reviewPagingAod");
    pagingBox.innerHTML = ""; // 초기화

    const result = await API.get('/care/api/management/shelter/reviews/adoption?cp=' + cp);
    
    if (result.status !== 200) {
        alert("리뷰 데이터를 불러올 수 없습니다.");
        return;
    }

    const reviews = result.data;
    const pageInfo = result.pageInfo;

    for (const review of reviews) {
        const card = document.createElement("div");
        card.id = 'review-' + review.reviewIdx;

        let cardContent = '';

        // 원본 리뷰일 경우 (turn === 0)
        if (review.turn === 0) {
            card.className = "review-card"; // 원본 리뷰용 클래스 설정
            card.style.position = "relative";  
            
            cardContent += 
                '<div class="image-wrapper" style="width: 150px; height: 180px; overflow: hidden; margin-right: 20px; margin-bottom:10px; float: left;">' +
                '<img src="${pageContext.request.contextPath}' + review.imagePath + '" style="width: 100%; height: 100%; object-fit: cover;">' +
                '</div>' +
                '<span class="nickname" style="font-weight: bold;">' + review.nickName + '</span> · ' +
                '<span class="created-at" style="font-size: 0.9em; color: #777;">' + review.createdAt + '</span>';
        } else {
            card.className = "reply-card"; // 답글용 클래스 설정
            cardContent += 
                '<span class="shelterName" style="font-weight: bold;">' + 
                (review.shelterName ? review.shelterName : '정보 없음') + 
                '</span> · ' + 
                '<span class="created-at" style="font-size: 0.9em; color: #777;">' + review.createdAt + '</span>';
        }

        // 공통 내용 추가
        cardContent += 
            '<div class="content" style="white-space:pre-wrap;">' + review.content + '</div>';

        card.innerHTML = cardContent;
        card.style.padding = "10px";

        // 원본 리뷰 답글 버튼
        if (review.turn === 0) {
            const replyButton = document.createElement("button");
            replyButton.innerHTML = "답글 남기기";
            replyButton.className = "edit-button";
            replyButton.style.position = "absolute";
            replyButton.style.top = "10px";
            replyButton.style.right = "10px";
            replyButton.onclick = function() { openPopup(review.reviewIdx); };
            card.appendChild(replyButton);
        }

        function openPopup(reviewIdx) {
            const url = '/care/management/shelters/adoptionReview/reply?reviewIdx=' + reviewIdx + '&animalIdx=' + review.animalIdx;
            const width = 600;
            const height = 400;
            const left = (window.innerWidth - width) / 2;
            const top = (window.innerHeight - height) / 2;
            window.open(url, '리뷰 답글 남기기', 'width=' + width + ',height=' + height + ',top=' + top + ',left=' + left + ',resizable=yes');
        }

        // 답글이면 수정/삭제
        if (review.turn !== 0) {
            const actionText = document.createElement("div");
            actionText.className = "action-text";
            actionText.innerHTML =
                '<span class="edit-text" onclick="editReviewPopupAR(' + review.reviewIdx + ')">수정</span>' +
                '<span class="delete-text" onclick="deleteReviewAR(' + review.reviewIdx + ')">삭제</span>';
            actionText.style.position = "absolute";
            actionText.style.right = "10px";
            actionText.style.top = "5px";
            card.style.position = "relative";
            card.appendChild(actionText);

            card.style.marginLeft = (review.turn * 8) + "px";
            card.style.borderBottom = "1px solid #ccc";
            card.style.borderLeft = "3px solid #ccc";
            card.style.clear = "both";
        }

        wrapper.appendChild(card);
    }

    makePaging(
        pageInfo.totalCnt,
        pageInfo.listSize,
        pageInfo.pageSize,
        pageInfo.cp,
        "reviewPagingAod",
        loadAdoptionReview
    );
}

	//입양 답글 수정 
	function editReviewPopupAR(reviewIdx) {
			const url = '/care/management/shelters/adoptionReview/reply/update?reviewIdx=' + reviewIdx;
			const width = 600;
	        const height = 400;
	        const left = (window.innerWidth - width) / 2;
	        const top = (window.innerHeight - height) / 2;
	
	        var windowOptions = "width=" + width + ",height=" + height + ",top=" + top + ",left=" + left + ",resizable=yes";
	        window.open(url, "replyUpdateAR", windowOptions);
	 }
	
	
	//입양 답글 삭제
	async function deleteReviewAR(reviewIdx) {
	    if (!confirm("정말 삭제하시겠습니까?")) return;

	    const result = await API.delete('/care/api/management/shelter/adoptionReviews/reply/' + reviewIdx);
	    if (result.status === 200) {
	        alert(result.message);
	        loadAdoptionReview();  // 삭제 후 목록 새로고침
	    } else {
	        alert("삭제에 실패하였습니다. 관리자에게 문의하세요");
	    }
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