<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>보호시설 약관동의</title>
</head>
<body>
<%@ include file="/WEB-INF/views/common/index/indexHeader.jsp" %>

<h2>보호시설 회원가입 약관 동의</h2>

<form id="agreeForm">
  <!-- 이용약관 -->
  <div style="margin-bottom: 20px;">
    <h4>[필수] 서비스 이용약관 동의</h4>
    <div style="border: 1px solid #ccc; padding: 10px; height: 150px; overflow-y: scroll; background: #f9f9f9;">
      <p>
        여기는 서비스 이용약관 내용입니다. 이 약관은 대한민국 법령에 따르며, 사용자는 본 약관에 따라 서비스를 이용해야 합니다. <br/>
        (약관 내용 계속...)<br/>
        약관 전문을 이 영역에 넣을 수 있습니다.
      </p>
    </div>
    <div style="margin-top: 8px;">
      <input type="checkbox" id="terms" required />
      <label for="terms">위 내용을 모두 읽고 동의합니다.</label>
    </div>
  </div>

  <!-- 개인정보 수집 및 이용 동의 -->
  <div style="margin-bottom: 20px;">
    <h4>[필수] 개인정보 수집 및 이용 동의</h4>
    <div style="border: 1px solid #ccc; padding: 10px; height: 150px; overflow-y: scroll; background: #f9f9f9;">
      <p>
        여기는 개인정보 수집 및 이용 동의서입니다. 이름, 생년월일, 이메일, 주소 등의 정보가 수집됩니다. <br/>
        (개인정보 수집 내용 계속...)<br/>
        해당 영역에 실제 내용을 넣을 수 있습니다.
      </p>
    </div>
    <div style="margin-top: 8px;">
      <input type="checkbox" id="privacy" required />
      <label for="privacy">위 내용을 모두 읽고 동의합니다.</label>
    </div>
  </div>

  <button type="submit">다음 단계로</button>
</form>

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
<%@ include file="/WEB-INF/views/common/index/indexFooter.jsp" %>
</body>
</html>