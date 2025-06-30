<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
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
</body>
</html>








