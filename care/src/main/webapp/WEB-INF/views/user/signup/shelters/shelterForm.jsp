<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>보호시설 회원가입 Form</title>
<style>
  input:focus {
    outline: none;
  }
</style>
</head>
<body>
<%@ include file="/WEB-INF/views/common/index/indexHeader.jsp" %>
<h2>보호시설 사용자 회원가입</h2>

<form id="signupForm">

	<!-- 영업구분 -->
	<div>
	  <label>영업구분</label>
	  <label><input type="radio" name="shelterTypeIdx" value="1" required /> 공공</label>
	  <label><input type="radio" name="shelterTypeIdx" value="2" /> 민간 (사업자유)</label>
	  <label><input type="radio" name="shelterTypeIdx" value="3" /> 민간 (사업자무)</label>
	</div>

  <!-- 이름 -->
  <div>
    <label for="name">이름</label>
    <input type="text" id="name" name="name" required />
    <span class="error" id="nameError"></span>
  </div>

  <!-- 아이디 -->
  <div>
    <label for="id">아이디</label>
    <input type="text" id="id" name="id" required autocomplete="username"/>
    <span class="error" id="idError" style="color:red; font-size:12px;"></span>
  </div>

  <!-- 비밀번호 -->
  <div>
    <label for="password">비밀번호</label>
    <input type="password" id="password" name="password" required autocomplete="new-password" />
    <span class="error" id="passwordError"></span>
  </div>

  <!-- 비밀번호 확인-->
  <div>
    <label for="password">비밀번호 확인</label>
    <input type="password" id="confirmPassword" name="confirmPassword" required autocomplete="new-password" />
    <span class="error" id="confirmPasswordError"></span>
  </div>

  <!-- 회원유형 -->
  <div>
    <input type="radio" name="signuptype" checked="checked" />
    <label>보호시설</label>
  </div>

  <!-- 닉네임 -->
  <div>
    <label for="nickname">닉네임</label>
    <input type="text" id="nickname" name="nickname" required />
    <span class="error" id="nicknameError" style="color:red; font-size:12px;"></span>
  </div>

  <!-- 생년월일 -->
  <div>
    <label for="birthDate">생년월일</label>
    <input type="date" id="birthDate" name="birthDate" required />
    <span class="error" id="birthDateError"></span>
  </div>

  <!-- 전화번호 -->
  <div>
    <label for="tel">전화번호</label>
    <div style="display: flex; gap: 4px;">
      <input type="text" id="tel1" name="tel1" value="010" readonly style="width: 50px;" />
      <input type="text" id="tel2" name="tel2" maxlength="4" required style="width: 60px;" />
      <input type="text" id="tel3" name="tel3" maxlength="4" required style="width: 60px;" />
    </div>
    <span class="error" id="telError"></span>
  </div>

  <!-- 이메일 -->
  <div>
    <label for="email">이메일</label>
    <input type="text" id="emailInput" name="email" required />
    <button type="button" id="sendCodeBtn" disabled>인증번호 전송</button>
    <span id="emailError" class="error"></span>
  </div>

  <!-- 인증번호 입력 -->
  <div id="codeSection" style="display:none;">
    <input type="text" id="emailCode" placeholder="인증번호 입력" />
    <button type="button" id="verifyCodeBtn">인증 확인</button>
    <span id="codeError" class="error"></span>
  </div>

  <!-- 성별 -->
  <div>
    <label>성별</label>
    <input type="radio" name="gender" value="M" required /> 남
    <input type="radio" name="gender" value="F" required /> 여
    <span class="error" id="genderError"></span>
  </div>

  <!-- 주소 -->
  <div>
    <label for="zipCode">우편번호</label>
    <input type="text" id="zipCode" name="zipCode" readonly required />
    <button type="button" onclick="execDaumPostcode()">우편번호 찾기</button>
    <span class="error" id="zipCodeError"></span>
  </div>

  <div>
    <label for="address">주소</label>
    <input type="text" id="address" name="address" readonly required />
    <span class="error" id="addressError"></span>
  </div>

  <div>
    <label for="addressDetail">상세주소</label>
    <input type="text" id="addressDetail" name="addressDetail" />
  </div>
  
	<!-- 보호소명, 연락처, 담당자명 등 -->
	<div>
	  <label for="shelterName">보호소 이름</label>
	  <input type="text" id="shelterName" name="shelterName" />
    <span id="shelterNameError" class="error"></span>

	</div>
	<div>
	  <label for="shelterTel">보호소 연락처</label>
	  <input type="text" id="shelterTel" name="shelterTel" required />
    <span class="error" id="shelterTelError"></span> 
	</div>
	<div>
	  <label for="shelterPersonName">담당자명</label>
	  <input type="text" id="shelterPersonName" name="shelterPersonName" required />
    <span class="error" id="shelterPersonNameError" style="color:red; font-size:12px;"></span>
	</div>
	<!-- 보호소 주소 (우편번호/주소는 기존 우편번호 api 재활용 가능) -->
	<div>
	  <label for="shelterZipCode">보호소 우편번호</label>
	  <input type="text" id="shelterZipCode" name="shelterZipCode" readonly required />
	  <button type="button" onclick="execDaumPostcodeForShelter()">우편번호 찾기</button>
	</div>
	<div>
	  <label for="shelterAddress">보호소 주소</label>
	  <input type="text" id="shelterAddress" name="shelterAddress" readonly required />
	</div>
	<div>
	  <label for="shelterAddressDetail">보호소 상세주소</label>
	  <input type="text" id="shelterAddressDetail" name="shelterAddressDetail" />
	</div>
	<div>
	  <label for="shelterDescription">보호소 소개</label>
	  <textarea id="shelterDescription" name="shelterDescription" rows="3"></textarea>
	</div>  
	
	<!-- 공공 (1): 이메일 + 사업자번호 -->
	<div id="publicFields" style="display:none;">
	  <label for="shelterEmail">보호소 이메일</label>
	  <input type="email" id="shelterEmail" name="shelterEmail" />
    <span class="error" id="shelterEmailError"></span>
	  <label for="shelterBusinessNumber">사업자등록번호</label>
	  <input type="text" id="shelterBusinessNumber1" name="shelterBusinessNumber" />
	</div>
	
	<!-- 민간 (2): 사업자번호 -->
	<div id="privateFields" style="display:none;">
	  <label for="shelterBusinessNumber">사업자등록번호</label>
	  <input type="text" id="shelterBusinessNumber2" name="shelterBusinessNumber" />
	</div>	
  <span id="shelterBusinessNumberError" class="error" style="color:red; font-size:12px;"></span>

	<!-- 민간 (2): 사업자등록증 파일 업로드 -->
	<div id="businessFileField" style="display: none;">
	  <label for="shelterBusinessFile">사업자등록증 첨부</label>
	  <input type="file" id="shelterBusinessFile" name="businessFile" accept=".jpg,.jpeg,.png,.pdf" />
	</div>
  

	<!-- 주소입력 폼 아무 데나 두면 됨 -->
  <div id="postcodeOverlay" style="display:none; position:fixed; top:0; left:0; width:100%; height:100%; background:rgba(0,0,0,0.3); z-index:99;"></div>
	<div id="postcodeLayer" style="display:none;position:fixed;width:400px;height:500px;top:20%;left:50%;transform:translateX(-50%);z-index:100;border:1px solid #ccc;background:#fff;"></div>

  <!-- 가입 버튼 -->
  <div>
    <button type="submit" id="submitBtn" disabled>가입하기</button>
  </div>

  <!-- 전화번호 병합용 -->
  <input type="hidden" id="tel" name="tel" />
  <!-- 이메일 병합용 -->
  <input type="hidden" id="email" name="email" />

  <form id="signupForm" onsubmit="return false;">

</form>

<script>
//선택한 shelterTypeIdx 에 따라 필드 표시
  window.onload = function () {
    var radios = document.getElementsByName("shelterTypeIdx");

    for (var i = 0; i < radios.length; i++) {
      radios[i].addEventListener("change", function () {
        var value = this.value;

        validateForm();

        document.getElementById("publicFields").style.display = "none";
        document.getElementById("privateFields").style.display = "none";
        document.getElementById("businessFileField").style.display = "none";

        if (value === "1") {
          // 공공
          document.getElementById("publicFields").style.display = "block";
        } else if (value === "2") {
          // 민간 사업자유
          document.getElementById("privateFields").style.display = "block";
          document.getElementById("businessFileField").style.display = "block";
        }
        // value === "3"이면 아무 것도 안 보여줌
      });
    }
  };
</script>

<!-- 1. 공통 유틸: debounce -->
<script>
  // 입력 지연 실행 (debounce)
  function debounce(fn, delay) {
    let timer = null;
    return function (...args) {
      clearTimeout(timer);
      timer = setTimeout(() => fn.apply(this, args), delay);
    };
  }
</script>

<!-- 2. Daum 주소 API -->
<script src="https://t1.daumcdn.net/mapjsapi/bundle/postcode/prod/postcode.v2.js"></script>
<script>
  // 주소 검색창 띄우기
  function execDaumPostcode() {
    var element_layer = document.getElementById('postcodeLayer');
    var overlay = document.getElementById('postcodeOverlay');

    new daum.Postcode({
      oncomplete: function(data) {
        document.getElementById('zipCode').value = data.zonecode;
        document.getElementById('address').value = data.roadAddress || data.jibunAddress;
        element_layer.style.display = 'none'; // 창 닫기
        overlay.style.display = 'none';
        validateForm(); // 주소 들어온 뒤 유효성 다시 확인
      },
      width: '100%',
      height: '100%'
    }).embed(element_layer);

    element_layer.style.display = 'block';
    overlay.style.display = 'block';
  }
  // 보호소 전용 주소 검색
  function execDaumPostcodeForShelter() {
    var element_layer = document.getElementById('postcodeLayer');
    var overlay = document.getElementById('postcodeOverlay');

    new daum.Postcode({
      oncomplete: function(data) {
        document.getElementById('shelterZipCode').value = data.zonecode;
        document.getElementById('shelterAddress').value = data.roadAddress || data.jibunAddress;
        element_layer.style.display = 'none'; // 창 닫기
        overlay.style.display = 'none';
        validateForm();
      },
      width: '100%',
      height: '100%'
    }).embed(element_layer);

    element_layer.style.display = 'block';
    overlay.style.display = 'block';
  }

  document.getElementById("postcodeOverlay").addEventListener("click", function() {
  document.getElementById("postcodeLayer").style.display = "none";
  this.style.display = "none"; // 오버레이 숨김
});
</script>

<!-- 3. 중복 검사 AJAX 통신 -->
<script>
  async function checkDuplicate(field, value) {
    const url = "${root}/api/validate/" + field + "?" + field + "=" + encodeURIComponent(value);
    const response = await fetch(url);
    if (!response.ok) return { isDuplicate: false };
    return await response.json();
  }
</script>

<!-- 4. 입력값 유효성 및 중복 검사 -->
<script>

  async function validateField(input, errorEl, field) {
    const value = input.value.trim();
    const fieldName = field === 'id' ? '아이디' : field === 'email' ? '이메일' : '닉네임';

    // 빈 값
    if (value.length === 0) {
      errorEl.innerText = "";
      input.classList.remove("valid", "invalid");
      input.style.border = "";
      return;
    }

    let isValidFormat = false;

    //아이디 형식 검사 (소문자 + 숫자 조합 필수)
    if (field === 'id') {
      const isLengthValid = /^[a-z0-9]{6,20}$/.test(value);
      const hasLetter = /[a-z]/.test(value);
      const hasNumber = /[0-9]/.test(value);

      if (!isLengthValid || !hasLetter || !hasNumber) {
        errorEl.innerText = "아이디는 영문 소문자와 숫자를 모두 포함하여 6~20자 이내여야 합니다.";
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

    // 중복 검사
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

    validateForm();
  }


</script>

<!-- 비밀번호 유효성 검사 전용-->
<script>

function validatePassword() {
  const value = passwordInput.value.trim();
  passwordError.innerText = "";
  passwordInput.style.border = "";

  if (value.length === 0) {
    // 비어있으면 무시
    return;
  }

  if (/\s/.test(value)) {
    passwordError.innerText = "비밀번호에 공백이 포함될 수 없습니다.";
    passwordError.style.color = "red";
    passwordInput.style.border = "1px solid red";
    return;
  }

  if (/[ㄱ-ㅎㅏ-ㅣ가-힣]/.test(value)) {
    console.log("❗️한글 감지됨:", value);
    console.log("현재 비밀번호 입력값:", value);
    console.log("한글 포함 여부:", /[ㄱ-ㅎㅏ-ㅣ가-힣]/.test(value));
    passwordError.innerText = "한글은 사용할 수 없습니다.";
    passwordError.style.color = "red";
    passwordInput.style.border = "1px solid red";
    return;
  }

  if (value.length < 9) {
    passwordError.innerText = "비밀번호는 9자 이상이어야 합니다.";
    passwordError.style.color = "red";
    passwordInput.style.border = "1px solid red";
    return;
  }

  if (value.length > 20) {
    passwordError.innerText = "비밀번호는 20자 이하여야 합니다.";
    passwordError.style.color = "red";
    passwordInput.style.border = "1px solid red";
    return;
  }

  if (!/[A-Za-z]/.test(value)) {
    passwordError.innerText = "영문자를 최소 1자 이상 포함해야 합니다.";
    passwordError.style.color = "red";
    passwordInput.style.border = "1px solid red";
    return;
  }

  if (!/\d/.test(value)) {
    passwordError.innerText = "숫자를 최소 1자 이상 포함해야 합니다.";
    passwordError.style.color = "red";
    passwordInput.style.border = "1px solid red";
    return;
  }

  if (!/[!@#$%^&*()_+=-]/.test(value)) {
    passwordError.innerText = "특수문자(!@#$%^&*()_+=-)를 최소 1자 이상 포함해야 합니다.";
    passwordError.style.color = "red";
    passwordInput.style.border = "1px solid red";
    return;
  }

  // 통과
  passwordError.innerText = "사용 가능한 비밀번호입니다.";
  passwordError.style.color = "green";
  passwordInput.style.border = "1px solid green";

  validateForm();
}
</script>

<!-- 비밀번호 일치 여부 확인-->
 <script>

  function validateConfirmPassword() {
    const passwordValue = passwordInput.value.trim();
    const confirmValue = confirmPasswordInput.value.trim();

    confirmPasswordError.innerText = "";
    confirmPasswordInput.style.border = "";

    // 공백 처리
    if (confirmValue.length === 0) {
      return; // 아직 입력 안 했으면 무시
    }

    if (passwordValue !== confirmValue) {
      confirmPasswordError.innerText = "비밀번호가 일치하지 않습니다.";
      confirmPasswordError.style.color = "red";
      confirmPasswordInput.style.border = "1px solid red";
    } else {
      confirmPasswordError.innerText = "비밀번호가 일치합니다.";
      confirmPasswordError.style.color = "green";
      confirmPasswordInput.style.border = "1px solid green";
    }

    validateForm(); // 전체 유효성 검사도 같이 갱신
  }
 </script>

<!-- 전화번호 유효성 검사 -->
<script>
  const tel2 = document.getElementById("tel2");
  const tel3 = document.getElementById("tel3");
  const telError = document.getElementById("telError");

  function validateTel() {
    const val2 = tel2.value.trim();
    const val3 = tel3.value.trim();
    const regex = /^[0-9]{3,4}$/;

    telError.innerText = "";
    tel2.style.border = "";
    tel3.style.border = "";

    if (!regex.test(val2) || !regex.test(val3)) {
      telError.innerText = "전화번호는 숫자 3~4자리씩 입력해야 합니다.";
      telError.style.color = "red";
      if (!regex.test(val2)) tel2.style.border = "1px solid red";
      if (!regex.test(val3)) tel3.style.border = "1px solid red";
      return false;
    }

    telError.innerText = "올바른 전화번호입니다.";
    telError.style.color = "green";
    tel2.style.border = "1px solid green";
    tel3.style.border = "1px solid green";
    return true;
  }

  tel2.addEventListener("input", debounce(validateTel, 300));
  tel3.addEventListener("input", debounce(validateTel, 300));
</script>

<!-- 이메일 유효성 검사 + 중복 검사 -->
<script>
const emailInput = document.getElementById("emailInput");
const emailError = document.getElementById("emailError");
const sendCodeBtn = document.getElementById("sendCodeBtn");

let emailVerified = false;

async function validateEmailForVerification() {
  const email = emailInput.value.trim();
  document.getElementById("email").value = email; // hidden input에도 세팅

  const isValidFormat = /^[^\s@]+@[^\s@]+\.[^\s@]+$/.test(email);

  if (!isValidFormat) {
    emailError.innerText = "올바른 이메일 형식이 아닙니다.";
    emailError.style.color = "red";
    sendCodeBtn.disabled = true;
    return;
  }

  const result = await checkDuplicate("email", email);
  if (result.isDuplicate) {
    emailError.innerText = "이미 사용 중인 이메일입니다.";
    emailError.style.color = "red";
    sendCodeBtn.disabled = true;
  } else {
    emailError.innerText = "사용 가능한 이메일입니다. 인증번호를 전송하세요.";
    emailError.style.color = "green";
    sendCodeBtn.disabled = false;
  }
}

emailInput.addEventListener("input", debounce(validateEmailForVerification, 500));
</script>

<!--이메일 인증번호 전송 / 확인-->
<script>
sendCodeBtn.addEventListener("click", async () => {
  const email = emailInput.value.trim();

  const res = await fetch("${root}/api/email/verify", {
    method: "POST",
    headers: { "Content-Type": "application/json" },
    body: JSON.stringify({ email }),
  });

  if (res.ok) {
    document.getElementById("codeSection").style.display = "block";
    emailError.innerText = "인증번호가 전송되었습니다. 메일을 확인하세요.";
    emailError.style.color = "green";
  } else {
    emailError.innerText = "인증번호 전송에 실패했습니다.";
    emailError.style.color = "red";
  }
});

document.getElementById("verifyCodeBtn").addEventListener("click", async () => {
  const code = document.getElementById("emailCode").value.trim();
  const email = emailInput.value.trim();

  const res = await fetch("${root}/api/email/verify/code", {
    method: "POST",
    headers: { "Content-Type": "application/json" },
    credentials: "include",
    body: JSON.stringify({ email, code })
  });

  const json = await res.json(); 

  if (res.ok && json.status === 200) {
    emailVerified = true;
    document.getElementById("codeError").innerText = "인증이 완료되었습니다.";
    document.getElementById("codeError").style.color = "green";

    validateForm();
  } else {
    document.getElementById("codeError").innerText = json.message || "인증 실패";
    document.getElementById("codeError").style.color = "red";
  }
});

</script>

<!--보호소 이름 유효성 검사-->
<script>
  function validateShelterName() {
    var input = document.getElementById("shelterName");
    var error = document.getElementById("shelterNameError");
    var value = input.value.trim();

    if (value.length === 0) {
      error.innerText = "보호소 이름은 필수입니다.";
      error.style.color = "red";
      input.style.border = "1px solid red";
      return false;
    }

    var regex = /^[가-힣a-zA-Z0-9\s]{2,50}$/;
    if (!regex.test(value)) {
      error.innerText = "보호소 이름은 특수문자 없이 2~50자 이내여야 합니다.";
      error.style.color = "red";
      input.style.border = "1px solid red";
      return false;
    }

    error.innerText = "올바른 보호소 이름입니다.";
    error.style.color = "green";
    input.style.border = "1px solid green";
    return true;
  }
</script>

<!-- 공공 보호소 전화번호 유효성 검사-->
<script>
  function validateShelterTel() {
  const input = document.getElementById("shelterTel");
  const value = input.value.trim();
  const error = document.getElementById("shelterTelError");

  const regex = /^\d{9,11}$/; // 숫자만 9~11자리 허용

  if (value.length === 0) {
    error.innerText = "보호소 연락처는 필수입니다.";
    error.style.color = "red";
    input.style.border = "1px solid red";
    return false;
  }

  if (!regex.test(value)) {
    error.innerText = "숫자만 입력하며, 9~11자리여야 합니다.";
    error.style.color = "red";
    input.style.border = "1px solid red";
    return false;
  }

  error.innerText = "올바른 보호소 연락처입니다.";
  error.style.color = "green";
  input.style.border = "1px solid green";
  return true;
}

</script>

<!--담당자명 유효성 검사-->
<script>
  function validateShelterPersonName() {
  const input = document.getElementById("shelterPersonName");
  const value = input.value.trim();
  const error = document.getElementById("shelterPersonNameError");

  if (!value) {
    error.textContent = "담당자명을 입력해주세요.";
    return false;
  } else {
    error.textContent = "";
    return true;
  }
}
</script>

<!--공공 보호소 이메일 유효성 검사 -->
<script>
  function validateShelterEmail() {
    const shelterType = document.querySelector('input[name="shelterTypeIdx"]:checked').value;
    var input = document.getElementById("shelterEmail");
    var error = document.getElementById("shelterEmailError");
    var value = input.value.trim();

    //  shelterType이 1 (공공)이 아닐 경우 — 검사 없이 에러 지우고 return
    if (shelterType !== "1") {
      error.innerText = "";
      input.style.border = "";
      return true;
    }

    if (value.length === 0) {
      error.innerText = "보호소 이메일은 필수입니다.";
      error.style.color = "red";
      input.style.border = "1px solid red";
      return false;
    }

    var regex = /^[A-Za-z0-9._%+-]+@([A-Za-z0-9.-]+\.)?(go\.kr|korea\.kr|or\.kr)$/;
    if (!regex.test(value)) {
      error.innerText = "공공 도메인 이메일만 입력 가능합니다.";
      error.style.color = "red";
      input.style.border = "1px solid red";
      return false;
    }

    error.innerText = "사용 가능한 이메일입니다.";
    error.style.color = "green";
    input.style.border = "1px solid green";
    return true;
  }
</script>

<!--사업자 번호 유효성 검사-->
<script>
function validateBusinessNumber() {
  const checked = document.querySelector('input[name="shelterTypeIdx"]:checked');
  if (!checked) return true; // 아직 shelterType 선택 안 했으면 무시

  const shelterType = checked.value;
  const error = document.getElementById("shelterBusinessNumberError");

  // 민간 사업자무(3번)이면 검사 건너뛰고 에러 초기화
  if (shelterType === "3") {
    error.innerText = "";
    document.getElementById("shelterBusinessNumber1")?.style?.removeProperty("border");
    document.getElementById("shelterBusinessNumber2")?.style?.removeProperty("border");
    return true;
  }

  const input = shelterType === "1"
    ? document.getElementById("shelterBusinessNumber1")
    : document.getElementById("shelterBusinessNumber2");

  if (!input) return false;

  const value = input.value.trim();

  if (value.length === 0) {
    error.innerText = "사업자등록번호는 필수입니다.";
    error.style.color = "red";
    input.style.border = "1px solid red";
    return false;
  }

  const regex = /^\d{10}$/;
  if (!regex.test(value)) {
    error.innerText = "사업자등록번호는 하이픈 없이 숫자 10자리여야 합니다.";
    error.style.color = "red";
    input.style.border = "1px solid red";
    return false;
  }

  error.innerText = "유효한 사업자등록번호입니다.";
  error.style.color = "green";
  input.style.border = "1px solid green";
  return true;
}

</script>


<!-- 5. 전체 폼 유효성 검사 -->
<script>
function validateForm() {
    // 모든 입력 유효성 검사 후 submitBtn.disabled = false 처리 

  const checked = document.querySelector('input[name="shelterTypeIdx"]:checked');
  if (!checked) {
    // 선택 안된 상태면 가입버튼 비활성화 + false 반환
    document.getElementById("submitBtn").disabled = true;
    return false;
  }

  const shelterType = checked.value;


  const submitBtn = document.getElementById("submitBtn");

  // 값 확인
  const idValue = idInput.value.trim();
  const pwValue = passwordInput.value.trim();
  const confirmValue = confirmPasswordInput.value.trim();
  const nicknameValue = nicknameInput.value.trim();
  const emailValue = emailInput.value.trim();
  const birthValue = document.getElementById("birthDate").value.trim();
  const genderChecked = document.querySelector('input[name="gender"]:checked');
  const zipCode = document.getElementById("zipCode").value.trim();
  const address = document.getElementById("address").value.trim();

    // 유효성 체크
  const idValid = /^[a-z0-9]{6,20}$/.test(idValue) && /[a-z]/.test(idValue) && /\d/.test(idValue);
  const passwordValid = pwValue.length >= 9 && pwValue.length <= 20;
  const confirmPasswordValid = pwValue === confirmValue;
  const nicknameValid = /^[a-zA-Z0-9가-힣]{2,20}$/.test(nicknameValue);
  const emailValid = /^[^\s@]+@[^\s@]+\.[^\s@]+$/.test(emailValue);
  const birthValid = birthValue.length === 10;
  const genderValid = genderChecked !== null && (genderChecked.value === "M" || genderChecked.value === "F");
  const addressValid = zipCode !== "" && address !== "";
  const telValid = validateTel();

  const shelterNameValid = validateShelterName();
  const shelterTelValid = validateShelterTel();
  const shelterPersonNameValid = validateShelterPersonName();
  const businessNumberValid = validateBusinessNumber();
  let shelterEmailValid = true;

  if (shelterType === "1") {
    shelterEmailValid = validateShelterEmail();
  }

  const allValid = idValid && passwordValid && confirmPasswordValid &&
  nicknameValid && emailValid && emailVerified &&
  birthValid && genderValid && addressValid && telValid &&
  shelterNameValid && shelterTelValid && businessNumberValid && shelterEmailValid;

  // 버튼 활성화
  submitBtn.disabled = !allValid;
  return allValid;
  }
</script>

<!--submit 시 전화번호 자동 병합 -->
<script>
  const signupForm = document.getElementById("signupForm");

  signupForm.addEventListener("submit", function(e) {
    const tel1 = document.getElementById("tel1").value.trim();
    const tel2 = document.getElementById("tel2").value.trim();
    const tel3 = document.getElementById("tel3").value.trim();

    const fullTel = tel1 + tel2 + tel3;
    document.getElementById("tel").value = fullTel;

    // ↓ 아래 조건들 만족하지 않으면 전송 막기 (validateForm 내부에서)
    if (!validateTel() || !validateForm()) {
      e.preventDefault(); // 제출 막기
    }
  });
</script>


<!-- 유효성 검사 이벤트 등록 -->
<script>
  const idInput = document.getElementById("id");
  const idError = document.getElementById("idError");
  
  const nicknameInput = document.getElementById("nickname");
  const nicknameError = document.getElementById("nicknameError");

  const passwordInput = document.getElementById("password");
  const passwordError = document.getElementById("passwordError");

  const confirmPasswordInput = document.getElementById("confirmPassword");
  const confirmPasswordError = document.getElementById("confirmPasswordError");

  const bn1 = document.getElementById("shelterBusinessNumber1");
  const bn2 = document.getElementById("shelterBusinessNumber2");

  idInput.addEventListener("input", debounce(() => validateField(idInput, idError, "id"), 500));
  nicknameInput.addEventListener("input", debounce(() => validateField(nicknameInput, nicknameError, "nickname"), 500));
  passwordInput.addEventListener("input", debounce(() => validatePassword(), 500));
  confirmPasswordInput.addEventListener("input", debounce(() => validateConfirmPassword(), 300));

  document.getElementById("name").addEventListener("input", debounce(validateForm, 300));
  document.getElementById("birthDate").addEventListener("change", validateForm);
  document.getElementsByName("gender").forEach(radio => radio.addEventListener("change", validateForm));
  document.getElementById("addressDetail").addEventListener("input", debounce(validateForm, 300));
  document.getElementById("shelterName").addEventListener("input", debounce(validateShelterName, 300));
  document.getElementById("shelterEmail").addEventListener("input", debounce(validateShelterEmail, 300));
  if (bn1) bn1.addEventListener("input", debounce(validateBusinessNumber, 300));
  if (bn2) bn2.addEventListener("input", debounce(validateBusinessNumber, 300));
  document.getElementById("shelterTel").addEventListener("input", debounce(validateShelterTel, 300));
  document.getElementById("shelterPersonName").addEventListener("input", validateForm);

</script>

<!-- 가입요청 -->
<script>
signupForm.addEventListener("submit", async function(e) {
  e.preventDefault(); // 기본 제출 막기

  // 유효성 검사 통과 안하면 중단
  if (!validateTel() || !validateForm()) return;

  // 전화번호 병합
  const tel1 = document.getElementById("tel1").value.trim();
  const tel2 = document.getElementById("tel2").value.trim();
  const tel3 = document.getElementById("tel3").value.trim();
  const tel = tel1 + tel2 + tel3;

  const payload = {
    id: document.getElementById("id").value.trim(),
    password: document.getElementById("password").value.trim(),
    name: document.getElementById("name").value.trim(),
    nickname: document.getElementById("nickname").value.trim(),
    email: document.getElementById("emailInput").value.trim(), // 실제 입력 필드 기준
    birthDate: document.getElementById("birthDate").value.trim(),
    gender: document.querySelector('input[name="gender"]:checked').value,
    tel: tel,
    zipCode: document.getElementById("zipCode").value.trim(),
    address: document.getElementById("address").value.trim(),
    addressDetail: document.getElementById("addressDetail").value.trim(),
    
    //보호소 관련
    shelterTypeIdx: document.querySelector('input[name="shelterTypeIdx"]:checked').value,
    shelterName: document.getElementById("shelterName").value.trim(),
    shelterTel: document.getElementById("shelterTel").value.trim(),
    shelterPersonName: document.getElementById("shelterPersonName").value.trim(),
    shelterZipCode: document.getElementById("shelterZipCode").value.trim(),
    shelterAddress: document.getElementById("shelterAddress").value.trim(),
    shelterAddressDetail: document.getElementById("shelterAddressDetail").value.trim(),
    shelterDescription: document.getElementById("shelterDescription").value.trim()
  };
  // 보호소 관련 데이터 추가
  const shelterType = document.querySelector('input[name="shelterTypeIdx"]:checked').value;
  payload.shelterTypeIdx = shelterType;

  if (shelterType === "1") {
    payload.shelterEmail = document.getElementById("shelterEmail").value.trim();
    payload.shelterBusinessNumber = document.getElementById("shelterBusinessNumber1").value.trim();
    payload.hasBusinessFile = 0;
  } else if (shelterType === "2") {
    payload.shelterBusinessNumber = document.getElementById("shelterBusinessNumber2").value.trim();
    const fileInput = document.getElementById("shelterBusinessFile");
    payload.hasBusinessFile = (fileInput && fileInput.files.length > 0) ? 1 : 0;
  } else {
    payload.hasBusinessFile = 0;
  }

  //회원 가입
  signupForm.addEventListener("submit", async function(e) {
  e.preventDefault(); // 기본 제출 막기

  if (!validateTel() || !validateForm()) return;

  const formData = new FormData();

  // 기본 텍스트 필드들
  formData.append("id", document.getElementById("id").value.trim());
  formData.append("password", document.getElementById("password").value.trim());
  formData.append("name", document.getElementById("name").value.trim());
  formData.append("nickname", document.getElementById("nickname").value.trim());
  formData.append("email", document.getElementById("emailInput").value.trim());
  formData.append("birthDate", document.getElementById("birthDate").value.trim());
  formData.append("gender", document.querySelector('input[name="gender"]:checked').value);

  const tel = document.getElementById("tel1").value.trim() + document.getElementById("tel2").value.trim() + document.getElementById("tel3").value.trim();
  formData.append("tel", tel);

  formData.append("zipCode", document.getElementById("zipCode").value.trim());
  formData.append("address", document.getElementById("address").value.trim());
  formData.append("addressDetail", document.getElementById("addressDetail").value.trim());

  // 보호소 공통 필드
  formData.append("shelterTypeIdx", document.querySelector('input[name="shelterTypeIdx"]:checked').value);
  formData.append("shelterName", document.getElementById("shelterName").value.trim());
  formData.append("shelterTel", document.getElementById("shelterTel").value.trim());
  formData.append("shelterPersonName", document.getElementById("shelterPersonName").value.trim());
  formData.append("shelterZipCode", document.getElementById("shelterZipCode").value.trim());
  formData.append("shelterAddress", document.getElementById("shelterAddress").value.trim());
  formData.append("shelterAddressDetail", document.getElementById("shelterAddressDetail").value.trim());
  formData.append("shelterDescription", document.getElementById("shelterDescription").value.trim());

  const shelterType = document.querySelector('input[name="shelterTypeIdx"]:checked').value;

  if (shelterType === "1") {
    formData.append("shelterEmail", document.getElementById("shelterEmail").value.trim());
    formData.append("shelterBusinessNumber", document.getElementById("shelterBusinessNumber1").value.trim());
  } else if (shelterType === "2") {
    formData.append("shelterBusinessNumber", document.getElementById("shelterBusinessNumber2").value.trim());
    
    const fileInput = document.getElementById("shelterBusinessFile");
    if (fileInput && fileInput.files.length > 0) {
      formData.append("businessFile", fileInput.files[0]);
    }
  }

  try {
    const res = await fetch("${root}/api/signup/shelter", {
      method: "POST",
      credentials: "include",
      body: formData //  multipart로 전송
    });

    const json = await res.json();

    if (res.ok && json.status === 201) {
      alert("회원가입이 완료되었습니다!");
      location.href = "${root}/login";
    } else {
      alert(json.message || "회원가입에 실패했습니다.");
    }
  } catch (err) {
    console.error("서버 오류:", err);
    alert("서버 통신 중 오류가 발생했습니다.");
  }
});
});

</script>
</body>
</html>