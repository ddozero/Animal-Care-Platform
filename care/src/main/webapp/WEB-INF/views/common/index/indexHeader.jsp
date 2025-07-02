<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page session="true" %>
<%@ page import="com.animal.api.auth.model.response.LoginResponseDTO" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>index header</title>

<c:set var="root" value="${pageContext.request.contextPath}" />
<c:set var="loginUser" value="${sessionScope.loginUser}" />


<style>
html, body {
  margin: 0;
  padding: 0;
}

/* 헤더 전체 박스 : 좌우 끝까지 늘어나게 */
#header-wrapper {
  width: 100%;
  background-color: white;
  border-bottom: 1px solid #ccc;
}

/* 상단 로고 + 유저 메뉴 */
#header-top {
  position: relative;
  height: 80px;
  color:black;
}

/* 가운데 로고 */
#logo {
  position: absolute;
  left: 50%;
  transform: translateX(-50%);
  font-size: 35px;
  font-weight: bold;
  top: 50%;
  transform: translate(-50%, -50%);
}

#logo a {
  text-decoration: none;
  color: black;
}

/* 우측 로그인/회원가입 */
#user-menu {
  position: absolute;
  top: 50%;
  right: 0;
  transform: translateY(-50%);
  font-size: 14px;
}

#user-menu a {
  text-decoration: none;
  color: #333;
  margin-left: 10px;
}

/* 하단 메뉴 라인 */
#menu-bar {
  display: flex;
  justify-content: center;
  gap: 40px;
  padding: 15px 0;
  background-color: #53D9C1;
  border-top: 1px solid #ddd;
  border-bottom: 1px solid #ddd;
  width: 100%;
}

#menu-bar a {
  text-decoration: none;
  color: white;
  font-weight: 600;
}

#menu-inner a:hover {
  text-decoration: underline;
}
</style>

</head>

<div id="header-wrapper">
	
	<!-- 상단 로고 + 유저 메뉴 -->
	<div id="header-top">
	  
	  <div id="logo">
	    <a href="${root}/index">같 이 살 개</a>
	  </div>
	
	  <div id="user-menu">
	    <c:choose>
	      <c:when test="${empty loginUser}">
	        <a href="${root}/login">로그인</a>|<a href="${root}/signup">회원가입</a>
	      </c:when>
	      <c:otherwise>
	        <span><strong>${loginUser.nickname}</strong>님</span>
	        <a href="javascript:void(0);" onclick="logout()">로그아웃</a>|<a href="${root}/mypage">마이페이지</a>
	        <c:if test="${loginUser.userTypeIdx == 2}">
	        |<a href="${root}/shelter/manage">보호시설 관리</a>
	        </c:if>
	      </c:otherwise>
	    </c:choose>
	  </div>
	  
	</div>

	<!-- 하단 메뉴 라인 -->
	<div id="menu-bar">
	  <a href="${root}/volunteers">봉사</a>
	  <a href="${root}/animals">입양</a>
	  <a href="${root}/donations">기부앤테이크</a>
	  <a href="${root}/community">커뮤니티</a>
	  <a href="${root}/shelters">보호시설</a>
	  <a href="${root}/support">고객지원</a>
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

      if (res.ok && ( result.status === 'OK' || result.status === 200 )) {
        alert('로그아웃 완료');
        location.href = '${root}/index';
      } else {
        alert(result.message || '로그아웃 실패');
      }
    } catch (err) {
      console.error('서버 오류: ',err);
      alert('서버 오류 발생');
    }
  }
</script>
</body>
</html>