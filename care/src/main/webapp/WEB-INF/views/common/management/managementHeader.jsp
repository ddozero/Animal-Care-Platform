<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page session="true" %>
<%@ page import="com.animal.api.auth.model.response.LoginResponseDTO" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="root" value="${pageContext.request.contextPath}" />
<c:set var="loginUser" value="${sessionScope.loginUser}" />
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>index header</title>
<!-- Pretendard 폰트 -->
<link href="https://cdn.jsdelivr.net/gh/orioncactus/pretendard/dist/web/static/pretendard.css" rel="stylesheet" />


<style>
	html, body {
	  margin: 0;
	  padding: 0;
	}
	
	#header-wrapper {
	  width: 100%;
	  background-color: white;
	  border-bottom: 2px solid #53D9C1;
	  box-shadow: 0 2px 5px rgba(0,0,0,0.05);
	}
	
	#header-top {
	  display: flex;
	  align-items: center;
	  justify-content: space-between;
	  max-width: 1200px;
	  margin: 0 auto;
	  padding: 20px 30px 10px;
	}
	
	#logo a {
	  font-size: 30px;
	  font-weight: bold;
	  text-decoration: none;
	  color: #fff;
	}
	
	#menu-bar {
	  display: flex;
	  gap: 30px;
	}
	
	#menu-bar a {
	  text-decoration: none;
	  color: #333;
	  font-weight: 600;
	}
	
	#menu-bar a:hover {
	  color: #53D9C1;
	}
	
	/* 로그인/회원가입 or 닉네임/마이페이지 영역 */
	#user-info-bar {
	  text-align: right;
	  font-size: 14px;
	  max-width: 1200px;
	  margin: 0 auto;
	  padding: 0 30px 10px;
	  color: #333;
	}
	
	#user-info-bar a {
	  margin-left: 10px;
	  text-decoration: none;
	  color: #333;
	}
	
	#user-info-bar a:hover {
	  color: #53D9C1;
	}
</style>

</head>
<body>

<div id="header-wrapper">

    <!-- 상단: 로고 + 메뉴 -->
    <div id="header-top">
        <div id="logo">
        <a href="${root}/index"  style="display: flex; align-items: center;" >
		  <img src="${root}/resources/web/images/indexheader/logo.png" alt="로고" style="height: 50px; vertical-align: middle; transform: scale(1.5); transform-origin: left center; margin-right: 20px;">
	    <div style="display: flex; flex-direction: column; line-height: 1.2; font-family: 'Pretendard';">
	      <span style="font-size: 14px; color: #666; letter-spacing: 0.5px;">당신에게</span>
	      <span style="font-size: 34px; font-weight: 800; color: #111; letter-spacing: -1px;">다시 가는 길</span>
	    </div>
		</a>

        </div>
        <div id="menu-bar">
            <a href="${root}/management/shelters">보호시설 관리</a>
            <a href="${root}/management/animals">유기동물 관리</a>
            <a href="${root}/management/volunteers">봉사 관리</a>
            <a href="${root}/management/dashboards">통계</a>
        </div>
    </div>
    
    <!-- 하단: 로그인/회원가입 또는 사용자 정보 -->
    <div id="user-info-bar">
        <c:choose>
        <c:when test="${empty loginUser}">
            <a href="${root}/login">로그인&ensp;</a>
            <span>|</span>
            <a href="${root}/signup">회원가입</a>
        </c:when>
        <c:otherwise>
            <span><strong>${loginUser.nickname}</strong> 님</span>
            <a href="javascript:void(0);" onclick="logout()">로그아웃&ensp;</a>
            <span>|</span>
            <a href="${root}/mypage">마이페이지</a>
            <c:if test="${loginUser.userTypeIdx == 2}">
            <span>|</span>
            <a href="${root}/management/shelters">보호시설 관리</a>
            </c:if>
        </c:otherwise>
        </c:choose>
    </div>
    
</div>

<script>
  async function logout() {
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