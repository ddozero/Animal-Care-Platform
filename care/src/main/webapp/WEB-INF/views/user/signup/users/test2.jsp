<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>일반 사용자 회원가입 Form</title>
  <style>
    body {
      font-family: 'Noto Sans KR', sans-serif;
      background-color: #f9f9f9;
      padding: 40px;
    }
    .signup-container {
      background: white;
      max-width: 700px;
      margin: auto;
      padding: 30px 40px;
      border-radius: 10px;
      box-shadow: 0 2px 8px rgba(0, 0, 0, 0.1);
    }
    h2 {
      text-align: center;
      margin-bottom: 30px;
    }
    .form-group {
      margin-bottom: 20px;
    }
    .form-group label {
      font-weight: bold;
      display: block;
      margin-bottom: 5px;
    }
    .form-group input,
    .form-group select {
      width: 100%;
      padding: 10px;
      border: 1px solid #ccc;
      border-radius: 5px;
      font-size: 14px;
    }
    .form-group input[readonly] {
      background-color: #f1f1f1;
    }
    .form-group .inline {
      display: flex;
      gap: 10px;
    }
    .error {
      color: red;
      font-size: 12px;
      margin-top: 5px;
    }
    .success {
      color: green;
      font-size: 12px;
      margin-top: 5px;
    }
    .button-wrap {
      display: flex;
      justify-content: center;
      gap: 10px;
      margin-top: 30px;
    }
    button {
      padding: 12px 25px;
      border: none;
      border-radius: 5px;
      font-size: 16px;
      cursor: pointer;
    }
    .btn-primary {
      background-color: #28a745;
      color: white;
    }
    .btn-secondary {
      background-color: #6c757d;
      color: white;
    }
  </style>
</head>
<body>
<%@ include file="/WEB-INF/views/common/index/indexHeader.jsp" %>
<h2>일반 사용자 회원가입</h2>

  <div class="signup-container">
    <h2>회원가입</h2>

    <form id="signupForm">
      <div class="form-group">
        <label for="name">회원이름 <span style="color:red">*</span></label>
        <input type="text" id="name" name="name" required />
      </div>

      <div class="form-group">
        <label for="id">회원ID <span style="color:red">*</span></label>
        <input type="text" id="id" name="id" required />
        <div class="error" id="idError"></div>
      </div>

      <div class="form-group">
        <label for="password">비밀번호 <span style="color:red">*</span></label>
        <input type="password" id="password" name="password" required />
        <div class="error" id="passwordError"></div>
      </div>

      <div class="form-group">
        <label for="confirmPassword">비밀번호 확인 <span style="color:red">*</span></label>
        <input type="password" id="confirmPassword" name="confirmPassword" required />
        <div class="error" id="confirmPasswordError"></div>
      </div>

      <div class="form-group">
        <label for="userType">회원구분 <span style="color:red">*</span></label>
        <input type="text" id="userType" name="userType" value="개인회원" readonly />
      </div>

      <div class="form-group">
        <label for="tel">휴대전화 <span style="color:red">*</span></label>
        <div class="inline">
          <input type="text" name="tel1" maxlength="3" placeholder="010" required />
          <input type="text" name="tel2" maxlength="4" required />
          <input type="text" name="tel3" maxlength="4" required />
        </div>
      </div>

      <div class="form-group">
        <label for="zipCode">주소 <span style="color:red">*</span></label>
        <div class="inline">
          <input type="text" id="zipCode" name="zipCode" readonly required />
          <button type="button" onclick="execDaumPostcode()">우편번호 검색</button>
        </div>
        <input type="text" id="address" name="address" readonly required style="margin-top: 10px;" />
        <input type="text" id="addressDetail" name="addressDetail" placeholder="상세주소 입력 (선택)" style="margin-top: 10px;" />
      </div>

      <div class="form-group">
        <label>이메일 <span style="color:red">*</span></label>
        <div class="inline">
          <input type="text" name="emailId" required />
          <span>@</span>
          <select name="emailDomain" id="emailDomain">
            <option value="">선택</option>
            <option value="naver.com">naver.com</option>
            <option value="gmail.com">gmail.com</option>
            <option value="daum.net">daum.net</option>
            <option value="self">직접 입력</option>
          </select>
          <input type="text" name="customEmailDomain" id="customEmailDomain" placeholder="직접입력" style="display:none;" />
        </div>
      </div>

      <div class="button-wrap">
        <button type="submit" class="btn-primary">등록</button>
        <button type="reset" class="btn-secondary">취소</button>
      </div>
    </form>
  </div>

  <script>
    // 이메일 직접입력 보이기
    const emailDomain = document.getElementById("emailDomain");
    const customEmailDomain = document.getElementById("customEmailDomain");
    emailDomain.addEventListener("change", function () {
      if (this.value === "self") {
        customEmailDomain.style.display = "inline-block";
      } else {
        customEmailDomain.style.display = "none";
      }
    });
  </script>

<script src="https://t1.daumcdn.net/mapjsapi/bundle/postcode/prod/postcode.v2.js"></script>
<script>
function execDaumPostcode() {
	  var element_layer = document.getElementById('postcodeLayer');

	  new daum.Postcode({
	    oncomplete: function(data) {
	      document.getElementById('zipCode').value = data.zonecode;
	      document.getElementById('address').value = data.roadAddress || data.jibunAddress;

	      // 창 닫기
	      element_layer.style.display = 'none';

	      validateForm(); // 주소 들어온 뒤 유효성 다시 확인
	    },
	    width: '100%',
	    height: '100%'
	  }).embed(element_layer);

	  // 레이어 열기
	  element_layer.style.display = 'block';
	}
</script>

<script>
  // debounce: 입력 후 일정 시간 지난 뒤 실행되도록
  function debounce(fn, delay) {
    let timer = null;
    return function (...args) {
      clearTimeout(timer);
      timer = setTimeout(() => fn.apply(this, args), delay);
    };
  }
</script>

<script>
async function checkDuplicate(field, value) {
	  const url = "${root}/api/validate/" + field + "?" + field + "=" + encodeURIComponent(value);
	  const response = await fetch(url);
	  if (!response.ok) return { isDuplicate: false };
	  return await response.json();
	}
</script>

<script>
  const idInput = document.getElementById("id");
  const idError = document.getElementById("idError");

  const emailInput = document.getElementById("email");
  const emailError = document.getElementById("emailError");

  const nicknameInput = document.getElementById("nickname");
  const nicknameError = document.getElementById("nicknameError");

  // 중복 검사 후 메시지 표시
  async function validateField(input, errorEl, field) {
	  const value = input.value.trim();
	  const fieldName = field === 'id' ? '아이디' : field === 'email' ? '이메일' : '닉네임';

	  // 1. 빈 값 처리
	  if (value.length === 0) {
	    errorEl.innerText = "";
	    input.classList.remove("valid", "invalid");
	    input.style.border = "";
	    return;
	  }

	  // 2. 정규식 유효성 검사 (형식 안 맞으면 중복 검사 안 함)
	  let isValidFormat = false;

	  if (field === 'id') {
	    isValidFormat = /^[a-z0-9]{6,20}$/.test(value);
	    if (!isValidFormat) {
	      errorEl.innerText = "아이디는 영문 소문자와 숫자 조합으로 6~20자 이내여야 합니다.";
	      errorEl.style.color = "red";
	      input.style.border = "1px solid red";
	      return;
	    }
	  }

	  if (field === 'email') {
	    isValidFormat = /^[^\s@]+@[^\s@]+\.[^\s@]+$/.test(value);
	    if (!isValidFormat) {
	      errorEl.innerText = "올바른 이메일 형식이 아닙니다.";
	      errorEl.style.color = "red";
	      input.style.border = "1px solid red";
	      return;
	    }
	  }

	  if (field === 'nickname') {
	    isValidFormat = /^[a-zA-Z0-9가-힣]{2,20}$/.test(value);
	    if (!isValidFormat) {
	      errorEl.innerText = "닉네임은 특수문자 없이 2~20자 이내여야 합니다.";
	      errorEl.style.color = "red";
	      input.style.border = "1px solid red";
	      return;
	    }
	  }

	  // 3. 중복 검사
	  const result = await checkDuplicate(field, value);

	  if (result.isDuplicate) {
	    errorEl.innerText = "이미 사용 중인 " + fieldName + "입니다.";
	    errorEl.style.color = "red";
	    input.style.border = "1px solid red";
	  } else {
	    errorEl.innerText = "사용 가능한 " + fieldName + "입니다.";
	    errorEl.style.color = "green";
	    input.style.border = "1px solid green";
	  }

	  validateForm(); // 전체 유효성 검사 (추후 구현)
  }

  idInput.addEventListener("input", debounce(() => validateField(idInput, idError, "id"), 500));
  emailInput.addEventListener("input", debounce(() => validateField(emailInput, emailError, "email"), 500));
  nicknameInput.addEventListener("input", debounce(() => validateField(nicknameInput, nicknameError, "nickname"), 500));
</script>

<script>
  function validateForm() {
    // 일단 아무것도 안 해도 됨!
    // 나중에 전체 입력 유효성 확인할 때 구현하면 됨
  }
</script>

</body>
</html>