<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>당신에게 다시가는 길 - 로그인</title>
<style>
    /* 전체 초기화 */
    html, body {
      margin: 0;
      padding: 0;
      box-sizing: border-box;
      font-family: 'Noto Sans KR', sans-serif;
      background-color: #f5f5f5;
    }

    *, *::before, *::after {
      box-sizing: inherit;
    }

    /* float 정리용 */
    .clearfix::after {
      content: "";
      display: block;
      clear: both;
    }

/* 전체 wrapper를 화면 가운데 정렬 */
.login-wrapper {
  display: flex;
  justify-content: center;
  align-items: center;
  height: calc(85vh - 170px); 
  background-color: #ffffff;
}

/* 로그인 박스 */
.login-container {
  background-color: #fff;
  padding: 40px 30px;
  border-radius: 12px;
  box-shadow: 0 2px 12px rgba(0, 0, 0, 0.1);
  width: 360px;
}
.login-container h2,
.login-btn,
.login-links {
  text-align: center;
}
/* 제목 */
.login-container h2 {
  margin-bottom: 30px;
  font-size: 24px;
  color: #333;
}

/* 입력창 */
.login-input {
  width: 100%;
  padding: 12px 14px;
  margin-bottom: 30px;
  border: 1px solid #ccc;
  border-radius: 8px;
  font-size: 15px;
  outline-color: #53D9C1;
  display: block;
  text-align: left; 
}

/* 로그인 버튼 */
.login-btn {
  width: 100%;
  padding: 12px;
  background-color: #53D9C1;
  border: none;
  border-radius: 8px;
  color: white;
  font-size: 16px;
  cursor: pointer;
  margin-top: 10px;
}

.login-btn:hover {
  background-color: #3bc0aa;
}

/* 링크 */
.login-links {
  margin-top: 20px;
  font-size: 14px;
  color: #555;
}

.login-links a {
  color: #2e7f90;
  text-decoration: none;
}

.login-links a:hover {
  text-decoration: underline;
}

/* 에러 메시지 */
.error-message {
  color: red;
  font-size: 14px;
  margin-top: 10px;
  min-height: 20px;
  text-align: center;
}

/* 잠금 해제 이메일 인증용 행 */
.unlock-row {
  display: flex;
  gap: 10px;
  margin-bottom: 15px;
}

/* 입력창: 버튼 옆에 붙게 좁게 */
.inline-input {
  flex: 1;
  margin-bottom: 0;
}

/* 버튼: 전체 너비 말고 높이만 맞춰서 입력창 옆에 */
.inline-btn {
  width: 120px;
  padding: 12px;
  margin-top: 0;
}

</style>
</head>
<body>
<div class="clearfix">
	<%@ include file="/WEB-INF/views/common/index/indexHeader.jsp" %>
</div>

<div class="login-wrapper">
  <div class="login-container">
    <h2>로그인</h2>

    <input type="text" id="id" name="id" placeholder="아이디 입력" class="login-input" />
    <input type="password" id="password" name="password" placeholder="비밀번호 입력" class="login-input" />
    
    <button id="loginBtn" class="login-btn">로그인</button>

    <div id="errorMessage" class="error-message"></div>

    <!-- 계정 잠금 해제 이메일 인증 영역 -->
    <div id="unlock-section" style="display:none; margin-top: 20px;">
      <!-- 이메일 입력 + 인증번호 받기 -->
      <div class="unlock-row">
        <input type="email" id="unlockEmail" class="login-input inline-input" placeholder="이메일 입력" />
        <button id="sendUnlockCodeBtn" class="login-btn inline-btn">인증번호 받기</button>
      </div>

      <!-- 인증번호 입력 + 인증하기 -->
      <div class="unlock-row">
        <input type="text" id="unlockCode" class="login-input inline-input" placeholder="인증번호 입력" />
        <button id="verifyUnlockBtn" class="login-btn inline-btn">인증하기</button>
      </div>

      <div id="unlockMessage" class="error-message" style="color: green;"></div>
    </div>

    <div class="login-links">
      <a href="${root}/signup">회원가입</a> |
      <a href="${root}/find">아이디/비밀번호 찾기</a><br>
    </div>
  </div>
</div>

<script>
  document.getElementById('loginBtn').addEventListener('click', async function () {
  // 이전 unlock 인증 UI 초기화
  document.getElementById('unlock-section').style.display = 'none';
  document.getElementById('unlockEmail').value = '';
  document.getElementById('unlockCode').value = '';
  document.getElementById('unlockMessage').innerText = '';

    const data = {
      id: document.getElementById('id').value,
      password: document.getElementById('password').value
    };

    try {
      const response = await fetch('${root}/api/auth/login', {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json'
        },
        body: JSON.stringify(data)
      });

      const result = await response.json();

      if (response.ok && (result.status === 'OK' || result.status === 200)) {
        alert('로그인 성공');
        window.location.href = '${root}/index';
      } else {
        const errorMsg = result.errorMsg || '로그인 실패';
        document.getElementById('errorMessage').innerText = errorMsg;

        // 에러 메시지에 '잠금'이라는 단어가 포함된 경우 unlock-section 보여주기
        if (errorMsg.includes('잠금')) {
          document.getElementById('unlock-section').style.display = 'block';
            // result에서 이메일 직접 가져오기
            const email = result.email || (result.data && result.data.email);
            if (email) {
              document.getElementById('unlockEmail').value = email;
            }
        }
      }
    } catch (error) {
      console.error('서버 오류:', error);
      document.getElementById('errorMessage').innerText = '서버 오류 발생';
    }
  });

  //로그인 lock 인증코드 전송 / 검증 버튼 이벤트 
  document.getElementById('sendUnlockCodeBtn').addEventListener('click', async function () {
  const email = document.getElementById('unlockEmail').value;

  try {
    const res = await fetch('${root}/api/email/unlock-request', {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json'
      },
      body: JSON.stringify({ email })
    });

    const result = await res.json();
    if (res.ok) {
      document.getElementById('unlockMessage').innerText = result.message || '인증코드가 발송되었습니다.';
    } else {
      document.getElementById('unlockMessage').innerText = result.errorMsg || '오류 발생';
    }
  } catch (err) {
    console.error('인증코드 요청 실패:', err);
    document.getElementById('unlockMessage').innerText = '서버 오류 발생';
  }
});

document.getElementById('verifyUnlockBtn').addEventListener('click', async function () {
  const code = document.getElementById('unlockCode').value;

  try {
    const res = await fetch('${root}/api/email/unlock-verify', {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json'
      },
      body: JSON.stringify({ code })
    });

    const result = await res.json();
    if (res.ok) {
      alert(result.message || '잠금이 해제되었습니다. 다시 로그인해 주세요.');
      location.reload(); // 로그인 다시 시도하게 새로고침
    } else {
      document.getElementById('unlockMessage').innerText = result.errorMsg || '인증 실패';
    }
  } catch (err) {
    console.error('인증 실패:', err);
    document.getElementById('unlockMessage').innerText = '서버 오류 발생';
  }
});  
</script>
<%@ include file="/WEB-INF/views/common/index/indexFooter.jsp" %>
</body>
</html>