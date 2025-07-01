<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>보호시설 약관동의</title>
  <style>
    body {
      margin: 0;
      padding: 0;
      background-color: #f8f9fa;
      font-family: 'Noto Sans KR', sans-serif;
    }

    .container {
      display: flex;
      justify-content: center;
      align-items: center;
      padding: 60px 20px;
    }

    .agree-box {
      background-color: #fff;
      border-radius: 12px;
      padding: 40px 30px;
      width: 600px;
      box-shadow: 0 2px 12px rgba(0, 0, 0, 0.1);
    }

    .agree-box h2 {
      text-align: center;
      font-size: 24px;
      margin-bottom: 30px;
      color: #333;
    }

    .terms-section {
      margin-bottom: 30px;
    }

    .terms-section h4 {
      font-size: 16px;
      margin-bottom: 8px;
      color: #007B9E;
    }

    .terms-content {
      border: 1px solid #ccc;
      padding: 12px;
      height: 150px;
      overflow-y: scroll;
      background-color: #f9f9f9;
      font-size: 14px;
      line-height: 1.6;
      color: #444;
    }

    .checkbox-wrap {
      margin-top: 10px;
      font-size: 14px;
    }

    .checkbox-wrap input[type="checkbox"] {
      margin-right: 6px;
    }

    .submit-btn {
      display: block;
      width: 100%;
      padding: 14px;
      background-color: #53D9C1;
      color: white;
      font-size: 16px;
      border: none;
      border-radius: 8px;
      cursor: pointer;
      transition: background-color 0.2s ease;
      margin-top: 10px;
    }

    .submit-btn:hover {
      background-color: #3bc0aa;
    }
  </style>
</head>
<body>
<%@ include file="/WEB-INF/views/common/index/indexHeader.jsp" %>

<div class="container">
  <form id="agreeForm" class="agree-box">
    <h2>보호시설 사용자 회원가입 약관 동의</h2>

    <!-- 이용약관 -->
    <div class="terms-section">
      <h4>[필수] 서비스 이용약관 동의</h4>
      <div class="terms-content">
		<p>
		제1조(목적)<br/>
		이 약관은 본 플랫폼(이하 "서비스")이 제공하는 모든 서비스의 이용과 관련하여, 이용자와 운영자 간의 권리, 의무 및 책임사항 등을 규정함을 목적으로 합니다.<br/><br/>
		
		제2조(이용자의 의무)<br/>
		이용자는 관련 법령, 본 약관, 이용안내 및 서비스와 관련하여 공지한 주의사항 등을 준수해야 하며, 다음 행위를 해서는 안 됩니다.<br/>
		- 타인의 정보 도용<br/>
		- 서비스 운영을 방해하는 행위<br/>
		- 불법적인 게시물 작성 및 등록<br/><br/>
		
		제3조(서비스 제공의 중단)<br/>
		서비스는 정기점검, 시스템 점검 또는 기타 운영상 필요한 경우 일시적으로 서비스 제공을 중단할 수 있습니다.
		</p>
      </div>
      <div class="checkbox-wrap">
        <input type="checkbox" id="terms" required />
        <label for="terms">위 내용을 모두 읽고 동의합니다.</label>
      </div>
    </div>

    <!-- 개인정보 수집 및 이용 동의 -->
    <div class="terms-section">
      <h4>[필수] 개인정보 수집 및 이용 동의</h4>
      <div class="terms-content">
		<p>
		1. 수집 항목<br/>
		- 필수: 이름, 생년월일, 아이디, 이메일, 휴대전화번호<br/>
		- 선택: 주소, 닉네임<br/><br/>
		
		2. 수집 목적<br/>
		- 회원 식별 및 본인 인증<br/>
		- 서비스 제공에 따른 민원 처리 및 안내<br/>
		- 유기동물 보호/입양 활동 관련 통계 분석<br/><br/>
		
		3. 보유 기간<br/>
		회원 탈퇴 시 즉시 파기되며, 관련 법령에 따라 보관이 필요한 경우 해당 기간 동안 보관됩니다.
		</p>
      </div>
      <div class="checkbox-wrap">
        <input type="checkbox" id="privacy" required />
        <label for="privacy">위 내용을 모두 읽고 동의합니다.</label>
      </div>
    </div>

    <button type="submit" class="submit-btn">다음 단계로</button>
  </form>
</div>

<%@ include file="/WEB-INF/views/common/index/indexFooter.jsp" %>

<script>
var root = "${root}";
console.log("root:", root);

  document.getElementById("agreeForm").addEventListener("submit", function(e){
    e.preventDefault();

    var termsCheked = document.getElementById("terms").checked;
    var privacyChecked = document.getElementById("privacy").checked;

    if(termsCheked && privacyChecked) {
      location.href = "${root}/shelterForm";
    } else {
      alert("모든 약관에 동의해야 가입을 진행할 수 있습니다.")
    }
  });
</script>
</body>
</html>