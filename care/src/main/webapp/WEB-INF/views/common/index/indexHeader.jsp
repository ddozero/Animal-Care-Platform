<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ page session="true" %>
    <%@ page import="com.animal.api.auth.model.response.LoginResponseDTO" %>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<c:set var="root" value="${pageContext.request.contextPath}" />

<%
	LoginResponseDTO loginUser = (LoginResponseDTO) session.getAttribute("loginUser");
%>
</head>
<body>

<div class="header" style="padding: 15px; border-bottom: 1px solid #ccc; display: flex; justify-content: space-between; align-items: center;">
  <div class="logo">
    <a href="${root}/index.do" style="text-decoration: none; font-weight: bold; font-size: 22px; color: #333;">
      댕봉사 플랫폼
    </a>
  </div>
  
  <div class="user-menu">
    <% if (loginUser == null) { %>
      <!-- 로그인 안 한 상태 -->
      <a href="${root}/login.do">로그인</a> |
      <a href="${root}/signup.do">회원가입</a>
    <% } else { %>
      <!-- 로그인 한 상태 -->
      <span><strong><%= loginUser.getNickname() %></strong>님 로그인 중</span> |
      <a href="javascript:void(0);" onclick="logout()">로그아웃</a>
    <% } %>
  </div>
</div>

<script>
  async function logout() {
    if (!confirm("로그아웃 하시겠습니까?")) return;

    try {
      const res = await fetch('${root}/api/auth/logout', {
        method: 'POST'
      });

      const result = await res.json();

      if (res.ok && result.status === 'OK') {
        alert('로그아웃 완료');
        location.href = '${root}/user/login/login.jsp';
      } else {
        alert(result.message || '로그아웃 실패');
      }
    } catch (err) {
      console.error(err);
      alert('서버 오류 발생');
    }
  }
</script>
</body>
</html>