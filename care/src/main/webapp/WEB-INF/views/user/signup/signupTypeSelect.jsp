<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>회원가입 유형 선택</title>
  <style>
    body {
      margin: 0;
      padding: 0;
      font-family: 'Noto Sans KR', sans-serif;
      background-color: #ffffff;
    }

    .container {
      display: flex;
      flex-direction: column;
      align-items: center;
      padding: 30px 30px;
    }

    h2 {
      margin-bottom: 40px;
      font-size: 26px;
      color: #333;
    }

    .card-wrapper {
      display: flex;
      gap: 40px;
      justify-content: center;
      flex-wrap: wrap;
    }

    .card {
      background-color: white;
      border: 2px solid #ccc;
      border-radius: 12px;
      padding: 30px 20px;
      width: 300px;
      text-align: center;
      box-shadow: 0 2px 6px rgba(0,0,0,0.08);
      transition: all 0.25s ease-in-out;
    }

    .card:hover {
      transform: translateY(-10px);
      box-shadow: 0 8px 20px rgba(0,0,0,0.15);
    }

    .card img {
      width: 100px;
      margin-bottom: 20px;
    }

    .card h3 {
      font-size: 20px;
      margin-bottom: 12px;
      color: #007B9E;
    }

    .card p {
      font-size: 14px;
      color: #555;
      margin-bottom: 20px;
      line-height: 1.5;
    }

    .card .warn {
      color: red;
      font-size: 13px;
      margin-top: -10px;
      margin-bottom: 20px;
    }

    .card button {
      background-color: #007B9E;
      color: white;
      border: none;
      border-radius: 8px;
      padding: 12px 20px;
      font-size: 15px;
      cursor: pointer;
      transition: background-color 0.2s ease;
    }

    .card button:hover {
      background-color: #005f7a;
    }
  </style>
</head>
<body>
<%@ include file="/WEB-INF/views/common/index/indexHeader.jsp" %>

<div class="container">
  <h2>회원가입 유형을 선택해주세요</h2>

  <div class="card-wrapper">
    <!-- 일반 회원가입 -->
    <div class="card" style="border-color: #007B9E;">
      <img src="${root}/resources/web/images/signup/signup_user.png" alt="일반회원 이미지" />
      <h3>일반 사용자 회원가입</h3>
      <p>입양 신청, 봉사활동, 기부에 참여하며<br>유기동물과 함께 따뜻한 변화를 만들어갈 수 있습니다.</p>
      <br/>
      <button onclick="location.href='${root}/typeUser'">일반 회원가입</button>
    </div>

    <!-- 보호시설 회원가입 -->
    <div class="card" style="border-color: green;">
      <img src="${root}/resources/web/images/signup/signup_shelter.png" alt="보호시설 이미지" />
      <h3 style="color: green;">보호시설 회원가입</h3>
      <p>보호동물 등록부터 입양 신청 관리, 봉사자 모집까지 보호소 운영에 필요한 서비스를 제공합니다.</p>
      <div class="warn">※ 영업자 회원가입은 관리자 승인 이후 로그인 할 수 있습니다.</div>
      <button onclick="location.href='${root}/typeShelter'">영업자 회원가입</button>
    </div>
  </div>
</div>

<%@ include file="/WEB-INF/views/common/index/indexFooter.jsp" %>
</body>
</html>