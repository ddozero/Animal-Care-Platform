<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<%@ include file="/WEB-INF/views/common/index/indexHeader.jsp" %>

<h2>회원가입 유형을 선택해주세요</h2>

<div style="display: flex; gap: 20px;">
  <button onclick="location.href='${root}/typeUser'">🙋 일반 사용자</button>
  <button onclick="location.href='${root}/typeShelter'">🏠 보호시설</button>
</div>

<%@ include file="/WEB-INF/views/common/index/indexFooter.jsp" %>
</body>
</html>