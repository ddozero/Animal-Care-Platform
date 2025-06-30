<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>로그인 페이지</title>
</head>
<body>
<%@ include file="/WEB-INF/views/common/index/indexHeader.jsp" %>

<h2>로그인</h2>

<!-- 아이디 입력 -->
<input type="text" name="id" id="id" placeholder="아이디 입력" /><br><br>

<!-- 비밀번호 입력 -->
<input type="password" name="password" id="password" placeholder="비밀번호 입력" /><br><br>

<!-- 로그인 버튼 -->
<button id="loginBtn">로그인</button>

<br><br>

<!-- 회원가입 / 아이디 비번 찾기 / 보호소 찾기 -->
<div>
  <a href="${root}/signup">회원가입</a> |
  <a href="${root}/find/idpw">아이디/비밀번호 찾기</a><br>
  • <a href="${root}/find/shelter/idpw">보호시설 아이디/비밀번호 찾기</a>
</div>

<!-- 에러 메시지 출력 -->
<div id="errorMessage" style="color: red; margin-top: 10px;"></div>

<script>
  document.getElementById('loginBtn').addEventListener('click', async function () {
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
        document.getElementById('errorMessage').innerText = result.message || '로그인 실패';
      }
    } catch (error) {
      console.error('서버 오류:', error);
      document.getElementById('errorMessage').innerText = '서버 오류 발생';
    }
  });
</script>
<%@ include file="/WEB-INF/views/common/index/indexFooter.jsp" %>
</body>
</html>