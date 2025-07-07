<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core"  prefix="c" %>    
<%@ include file="/WEB-INF/views/common/index/indexHeader.jsp" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>당신에게 다시가는 길 - 아이디/비밀번호 찾기</title>
<link rel="stylesheet" type="text/css" href="${root}/resources/web/user/auth/find/finding.css">
</head>

<body>
<script src="https://www.google.com/recaptcha/api.js" async defer></script>

<div class="container">
  <div class="tabs">
    <div class="tab active" onclick="switchTab('id')">아이디 찾기</div>
    <div class="tab" onclick="switchTab('pw')">비밀번호 찾기</div>
  </div>

  <!-- 아이디 찾기 -->
  <div class="form-section active" id="section-id">
    <div class="form-group">
      <label for="findName">이름</label>
      <input type="text" id="findName" placeholder="이름 입력" />
    </div>
	<div class="form-group">
	  <label for="findEmail">이메일 주소</label>
	  <input type="email" id="findEmail" placeholder="example@domain.com" />
	  <button class="btn small-btn" id="sendCodeBtn" style="margin-top: 10px;">인증번호받기</button>
	</div>
    <div class="form-group">
      <label for="emailCode">인증번호 입력</label>
      <input type="text" id="emailCode" placeholder="인증번호 6자리 입력" />
    </div>
    <button class="btn" id="findIdNextBtn">다음</button>
  </div>

  <!-- 비밀번호 찾기 STEP1 -->
  <div class="form-section" id="section-pw">
    <div id="section-pw-step1" class="pw-steps active">
      <p style="font-size: 15px; text-align: center; color: #2e7f90;">* 비밀번호를 찾고자 하는 아이디를 입력해주세요 *</p>
      <div class="form-group">
        <label for="pwFindId">아이디</label>
        <input type="text" id="pwFindId" placeholder="아이디 입력" />
      </div>
      <button class="btn" id="pwStep1NextBtn">다음</button>
    </div>

    <!-- 비밀번호 찾기 STEP2 -->
    <div id="section-pw-step2" class="pw-steps" style="display: none;">
      <div class="form-group">
        <label for="pwFindName">이름</label>
        <input type="text" id="pwFindName" placeholder="이름 입력" />
      </div>
      <div class="form-group">
        <label for="pwFindEmail">이메일 주소</label>
        <input type="email" id="pwFindEmail" placeholder="example@domain.com" />
        <button class="btn small-btn" id="sendPwCodeBtn" style="margin-top: 10px;">인증번호받기</button>
      </div>
      <div class="form-group">
        <label for="pwEmailCode">인증번호 입력</label>
        <input type="text" id="pwEmailCode" placeholder="인증번호 6자리 입력" />
      </div>
      <input type="hidden" id="pwStep2UserId" />
      <button class="btn" id="pwStep2NextBtn">다음</button>
    </div>
  </div>

    <!-- 비밀번호 찾기 STEP3 -->
    <div id="section-pw-step3" class="pw-steps" style="display: none;">
      <div class="form-group">
        <label for="newPassword">새 비밀번호</label>
        <input type="password" id="newPassword" placeholder="새 비밀번호 입력" />
      </div>
      <div class="form-group">
        <label for="confirmPassword">비밀번호 확인</label>
        <input type="password" id="confirmPassword" placeholder="비밀번호 확인" />
      </div>
      <div class="form-group">
        <div class="captcha-wrapper">
          <div class="g-recaptcha" data-sitekey="${recaptchaSiteKey}"></div>
        </div>
      <button class="btn" id="resetPasswordBtn">비밀번호 재설정</button>
    </div>
  </div>
</div>

<script>
  function switchTab(type) {
    const idTab = document.querySelector('.tab:nth-child(1)');
    const pwTab = document.querySelector('.tab:nth-child(2)');
    const idSection = document.getElementById('section-id');
    const pwSection = document.getElementById('section-pw');

    if (type === 'id') {
      idTab.classList.add('active');
      pwTab.classList.remove('active');
      idSection.classList.add('active');
      pwSection.classList.remove('active');
    } else {
      idTab.classList.remove('active');
      pwTab.classList.add('active');
      idSection.classList.remove('active');
      pwSection.classList.add('active');
    }
  }

    // 페이지 로드 시 URL 쿼리에서 tab 값을 읽어서 탭 초기화
    window.addEventListener("DOMContentLoaded", () => {
    const params = new URLSearchParams(window.location.search);
    const tab = params.get("tab"); // tab=id or tab=pw
    if (tab === 'pw') switchTab('pw');
    else switchTab('id');
  });
</script>

<script>
//아이디찾기 이메일 인증번호 전송
document.getElementById("sendCodeBtn").addEventListener("click", async () => {
  const email = document.getElementById("findEmail").value.trim();

  if(!email || !/^[^\s@]+@[^\s@]+\.[^\s@]+$/.test(email)) {
    alert("올바은 이메일 주소를 입력해주세요.");
    return;
  }

  try {
    const res = await fetch(`${root}/api/email/find/send-code`, {
      method: "POST",
      headers: { "Content-Type": "application/json"},
      body: JSON.stringify({ email })
    });

    if(res.ok){
      alert("인증번호가 전송되었습니다,");
    } else {
      const json = await res.json();
      alert(json.errorMsg || "인증번호 전송 실패");
    }

  }catch (err) {
    console.error("에러:", err);
    alert("서버 오류가 발생했습니다.");
  }
});

  //아이디 찾기 검증 요청
  document.getElementById("findIdNextBtn").addEventListener("click", async () => {
    const name = document.getElementById("findName").value.trim();
    const email = document.getElementById("findEmail").value.trim();
    const code = document.getElementById("emailCode").value.trim();

    if (!name || !email || !code) {
      alert("이름, 이메일, 인증번호를 모두 입력해주세요.");
      return;
    }

  try {
    // 2. 이메일 인증 코드 확인 먼저
    const codeRes = await fetch(`${root}/api/email/find/check-code`, {
      method: "POST",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify({ email, code }),
    });

    if (!codeRes.ok) {
      const err = await codeRes.json();
      alert(err.errorMsg || "인증번호 확인 실패");
      return;
    }

    // 3. 인증 성공 후 아이디 찾기 요청
    const findRes = await fetch(`${root}/api/find/user/id`, {
      method: "POST",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify({ name, email }),
    });

    const json = await findRes.json();

    if (findRes.ok && json.status === 200) {
      const data = json.data;
      location.href = `${root}/find/id`;
    } else {
      alert(json.errorMsg || "아이디 찾기 실패");
    }

  } catch (err) {
    console.error(err);
    alert("서버 오류가 발생했습니다.");
  }
  });
</script>

<!-- 비밀번호 찾기 로직 시작-->
<script>
// 비밀번호 찾기 1단계 → 2단계 전환
const pwStep1Btn = document.getElementById("pwStep1NextBtn");
if (pwStep1Btn) {
  pwStep1Btn.addEventListener("click", async () => {
    const userId = document.getElementById("pwFindId").value.trim();
    if (!userId) return alert("아이디를 입력해주세요.");

    try {
      const res = await fetch(`${root}/api/find/user/password/init`, {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify({ userid: userId }),
      });
      const json = await res.json();

      if (res.ok && json.status === 200) {
        document.getElementById("section-pw-step1").style.display = "none";
        document.getElementById("section-pw-step2").style.display = "block";
        document.getElementById("pwStep2UserId").value = userId;
      } else {
        alert(json.errorMsg || "해당 아이디로 가입된 사용자가 없습니다.");
      }
    } catch (err) {
      console.error(err);
      alert("서버 오류가 발생했습니다.");
    }
  });
}

// 비밀번호 찾기 - 이메일 인증번호 전송
const sendPwCodeBtn = document.getElementById("sendPwCodeBtn");
if (sendPwCodeBtn) {
  sendPwCodeBtn.addEventListener("click", async () => {
    const email = document.getElementById("pwFindEmail").value.trim();
    if (!email || !/^[^\s@]+@[^\s@]+\.[^\s@]+$/.test(email)) {
      alert("올바른 이메일 주소를 입력해주세요.");
      return;
    }

    try {
      const res = await fetch(`${root}/api/email/find/send-code`, {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify({ email }),
        credentials: "include"
      });

      if (res.ok) {
        alert("인증번호가 전송되었습니다.");
      } else {
        const json = await res.json();
        alert(json.errorMsg || "인증번호 전송 실패");
      }
    } catch (err) {
      console.error(err);
      alert("서버 오류가 발생했습니다.");
    }
  });
}

// 비밀번호 찾기 2단계 → 3단계 전환
const pwStep2Btn = document.getElementById("pwStep2NextBtn");
if (pwStep2Btn) {
  pwStep2Btn.addEventListener("click", async () => {
    const userid = document.getElementById("pwStep2UserId").value.trim();
    const name = document.getElementById("pwFindName").value.trim();
    const email = document.getElementById("pwFindEmail").value.trim();
    const code = document.getElementById("pwEmailCode").value.trim();

    if (!userid || !name || !email || !code) {
      alert("모든 정보를 입력해주세요.");
      return;
    }

    try {
      //  사용자 정보 확인
      const verifyRes = await fetch('${root}/api/find/user/password/verify', {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify({ userid, name, email, code }),
      });

      const verifyJson = await verifyRes.json();

      if (verifyRes.ok && verifyJson.status === 200) {
        // 단계 전환: 모든 pw-steps 비활성화 후, step3만 활성화
        document.querySelectorAll(".pw-steps").forEach(el => {
          el.classList.remove("active");
          el.style.display = "none";
        });
        document.getElementById("section-pw-step3").classList.add("active");
        document.getElementById("section-pw-step3").style.display = "block";
      } else {
        alert(verifyJson.errorMsg || "회원 정보 확인 실패");
      }
    } catch (err) {
      console.error(err);
      alert("서버 오류가 발생했습니다.");
    }
  });
}
  //비밀번호 찾기 3단계 새 비밀번호 변경
  document.getElementById("resetPasswordBtn").addEventListener("click", async () => {
    const userid = document.getElementById("pwStep2UserId").value.trim();
    const newPassword = document.getElementById("newPassword").value.trim();
    const confirmPassword = document.getElementById("confirmPassword").value.trim();
    const captcha = grecaptcha.getResponse();
  
    if (!newPassword || !confirmPassword) {
      alert("비밀번호를 입력해주세요.");
      return;
    }
    if (newPassword.length < 9 || newPassword.length > 20) {
      alert("비밀번호는 9자 이상 20자 이하로 입력해주세요.");
      return;
    }
    if (newPassword !== confirmPassword) {
      alert("비밀번호 확인이 일치하지 않습니다.");
      return;
    }
    if (!captcha) {
      alert("캡차 인증을 완료해주세요.");
      return;
    }
  
    try {
      const res = await fetch(`${root}/api/find/user/password/reset`, {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify({ userid, newPassword, confirmPassword, captcha })
      });
  
      const json = await res.json();
      if (res.ok && json.status === 200) {
        alert("비밀번호가 성공적으로 변경되었습니다.");
        location.href = `${root}/login`;
      } else {
        alert(json.errorMsg || "비밀번호 변경 실패");
      }
    } catch (err) {
      console.error(err);
      alert("서버 오류가 발생했습니다.");
    }
  });
  </script>
<%@ include file="/WEB-INF/views/common/index/indexFooter.jsp" %>
</body>
</html>