<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>보호시설 관리</title>
<style>
.container {
	padding: 20px;
	font-family: sans-serif;
}

.nav {
	display: flex;
	gap: 10px;
	margin-bottom: 30px;
}

.nav button {
	padding: 10px 20px;
	background-color: #eee;
	border: none;
	border-radius: 10px;
	cursor: pointer;
}

.content {
	display: flex;
	gap: 40px;
	align-items: flex-start;
}

.photo-box {
	width: 200px;
	height: 200px;
	background-color: #eee;
}

.edit-button {
  padding: 8px 16px;
  background-color: #ccc;
  border: none;
  border-radius: 8px;
  cursor: pointer;
  margin-bottom: 10px;
}

.info-section {
	flex: 1;
}

.title {
	font-size: 20px;
	font-weight: bold;
	margin-bottom: 20px;
}

.info-row {
	display: flex;
	margin-bottom: 10px;
}

.info-label {
	width: 100px;
	font-weight: bold;
}

.info-value {
	flex: 1;
}

.upload-button, .complete-button {
	padding: 8px 16px;
	background-color: #eee;
	border: none;
	border-radius: 10px;
	cursor: pointer;
	margin-top: 10px;
}

.bottom-box {
	margin-top: 40px;
	height: 400px;
	background-color: #eee;
	border-radius: 20px;
}

.container {
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
	<section class = "container">
		<div>
	   		<button class="edit-button" id="editButton">수정하기</button>
	  	</div>
		<div class="content">
				<div>
					<div class="photo-box">
						<img id = "shelterImage" src = "">
					</div>
					<button class="upload-button">사진업로드</button>
				</div>
			<div class="shelter-info">
				  <div><span id="shelterName"></span></div>
				  <div><span>담당관리자</span> <span id="personName"></span></div>
				  <div><span>분류</span> <span id="type"></span></div>
				  <div><span>사업자번호</span> <span id="businessNumber"></span></div>
				  <div>
				    <span>사업자등록증</span>
				    <!-- 사업자등록증 불러오기 -->
				    <a href="#" id="businessFile" target="_blank"> </a>
				
				    <!-- 사업자등록증 업로드하기 -->
				   <form id="bizUploadForm" enctype="multipart/form-data" method="post">
					    <label for="bizUpload" class="biz-btn">파일 선택</label>
					    <input type="file" id="bizUpload" name="files" style="position:absolute;width:1px;height:1px;opacity:0;" required>
					    <button type="submit">업로드하기</button>
					</form>
				  </div>
				  <div><span>주소</span><span id="address"></span></div>
				  <div><span>연락처</span><span id="tel"></span></div>
				  <div><span>이메일</span><span id="email"></span></div>
				</div>
		</div>
			
			<!-- 상단 메뉴 -->
			<div class="nav">
				<button id="infoBtn" onclick="location.href='/care/shelter/manage'">정보관리</button>
				<button id="noticeBtn" onclick="location.href='/care/shelter/notice'">공지사항</button>
				<button id="reviewBtn" onclick="location.href='/care/shelter/review'">리뷰관리</button>
			</div>
			
			<div class="bottom-box">
	  			<div><span></span><span id="description"></span></div>
			</div>
	</section>
	
<script src="${pageContext.request.contextPath}/resources/web/common/commonUtils.js"></script>
	<script>
		async function ShelterInfo() {
			const idx = location.pathname.split("/").pop();
			
			const result = await API.get('/care/api/management/shelter');
	
			if (!result || result.status !== 200 || !result.data) {
				alert(result?.message || '존재하지 않는 보호소입니다.');
				return;
			}
	
			const shelters = result.data;
	
			// 이미지 처리
			if (shelters.imagePaths && shelters.imagePaths.length > 0) {
				document.getElementById("shelterImage").src = '${pageContext.request.contextPath}' + shelters.imagePaths[0];
			} else {
				document.getElementById("shelterImage").src = '/resources/images/no-image.png';
			}
	
			document.getElementById('shelterName').innerText = shelters.shelterName;
			document.getElementById('personName').innerText = shelters.personName;
			document.getElementById('type').innerText = shelters.type;
			document.getElementById('businessNumber').innerText = shelters.businessNumber;
	
			// 사업자등록증 링크
			const bizLink = document.getElementById('businessFile');
			bizLink.href = '${pageContext.request.contextPath}/resources/' + shelters.filePaths;
			bizLink.innerText = shelters.businessFile;
	
			document.getElementById('tel').innerText = shelters.tel;
			document.getElementById('email').innerText = shelters.email;
			document.getElementById('description').innerText = shelters.description;
			
			//사업자 등록증 업로드
			const form = document.getElementById('bizUploadForm');
				form.action = '/care/api/management/shelter/upload/' + idx;

				form.addEventListener('submit', function(event) {
				event.preventDefault();

				const formData = getFormDataFromForm('bizUploadForm');

				FileAPI.upload(form.action, formData)
					.then(function(data) {
					const bizLink = document.getElementById('shelterBusinessFile');
					bizLink.href = '${pageContext.request.contextPath}/resources/management/' + idx + '/files/' + data.fileName;
					bizLink.innerText = data.fileName;
					bizLink.target = '_blank';

					alert('업로드 성공!');
					})
					.catch(function(error) {
					alert('업로드 실패: ' + error.message);
					console.error(error);
					});
    		});
		}

		window.addEventListener("DOMContentLoaded", ShelterInfo);
	</script>
</body>
</html>