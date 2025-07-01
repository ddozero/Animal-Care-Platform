<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>아이디 찾기 결과 화면</title>
  <style>
    .result-container {
      max-width: 500px;
      margin: 80px auto;
      padding: 30px;
      border: 1px solid #ddd;
      border-radius: 12px;
      box-shadow: 0 0 10px rgba(0,0,0,0.05);
      background: #fff;
      text-align: center;
    }

    .result-container h2 {
      margin-bottom: 20px;
      color: #2E7D32;
    }

    .found-id {
      font-size: 18px;
      font-weight: bold;
      color: #333;
      margin: 20px 0;
    }

    .buttons {
      display: flex;
      justify-content: center;
      gap: 15px;
      margin-top: 30px;
    }

    .buttons a {
      display: inline-block;
      padding: 12px 20px;
      background-color: #4CAF50;
      color: white;
      text-decoration: none;
      border-radius: 6px;
      font-weight: bold;
      transition: background 0.2s ease;
    }

    .buttons a:hover {
      background-color: #388E3C;
    }

  </style>
</head>
<body>
<%@ include file="/WEB-INF/views/common/index/indexHeader.jsp" %>

<div class="result-container">
  <h2>🔍 아이디 찾기 결과</h2>

	<c:set var="result" value="${sessionScope.findUserResult}" />
	<c:remove var="findUserResult" scope="session"/>

  <div class="found-id">
    <c:choose>
      <c:when test="${not empty result}">
        <p>회원님의 아이디는 <strong>${result.userid}</strong></p>
        <p>가입일: ${result.createdAt}</p>
      </c:when>
      <c:otherwise>
        <p>일치하는 아이디를 찾을 수 없습니다.</p>
      </c:otherwise>
    </c:choose>
  </div>

  <div class="buttons">
    <a href="${root}/login">로그인</a>
    <a href="${root}/find?tab=pw">비밀번호 찾기</a>
  </div>
</div>

<%@ include file="/WEB-INF/views/common/index/indexFooter.jsp" %>
</body>
</html>