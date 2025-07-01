<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<style>
  * {
    box-sizing: border-box;
  }

  body {
    font-family: 'Noto Sans KR', sans-serif;
    margin: 0;
    background-color: #f8f9fa;
  }

  .container {
    display: flex;
    max-width: 1200px;
    margin: 0 auto;
    padding: 40px 20px;
  }

  /* 왼쪽 네비게이션 */
  .sidebar {
    width: 200px;
    margin-right: 40px;
  }

  .sidebar button {
    display: block;
    width: 100%;
    margin-bottom: 16px;
    padding: 12px 20px;
    background-color: #fff;
    border: 2px solid #53D9C1;
    color: #333;
    font-size: 16px;
    border-radius: 8px;
    cursor: pointer;
    transition: background-color 0.2s ease;
  }

  .sidebar button:hover {
    background-color: #53D9C1;
    color: #fff;
  }

  /* 우측 메인 */
  .main-content {
    flex: 1;
  }

  .user-greeting {
    display: flex;
    justify-content: space-between;
    align-items: center;
    margin-bottom: 30px;
  }

  .user-greeting h2 {
    font-size: 24px;
    color: #333;
  }

  .user-greeting .edit-btn {
    background-color: #53D9C1;
    color: #fff;
    border: none;
    padding: 8px 16px;
    border-radius: 6px;
    cursor: pointer;
  }

  /* 통계 카드 */
  .stats-box {
    background-color: #fff;
    border-radius: 12px;
    padding: 20px 24px;
    box-shadow: 0 2px 6px rgba(0, 0, 0, 0.05);
    margin-bottom: 30px;
    line-height: 1.8;
  }

  .stats-box p {
    margin: 0;
    font-size: 16px;
    color: #333;
  }

  /* 활동 히스토리 */
  .history-box {
    background-color: #fff;
    border-radius: 12px;
    padding: 20px 24px;
    box-shadow: 0 2px 6px rgba(0, 0, 0, 0.05);
  }

  .history-box h3 {
    margin-top: 0;
    font-size: 18px;
    color: #333;
    margin-bottom: 12px;
  }

  .history-box ul {
    list-style: none;
    padding: 0;
    margin: 0;
  }

  .history-box li {
    padding: 6px 0;
    font-size: 15px;
    color: #555;
  }
</style>
</head>
<body>
<%@ include file="/WEB-INF/views/common/index/indexHeader.jsp" %>

  <div class="container">
    <!-- 왼쪽 사이드 메뉴 -->
    <div class="sidebar">
      <button>봉사내역</button>
      <button>입양내역</button>
      <button>기부내역</button>
      <button>활동내역</button>
    </div>

    <!-- 오른쪽 콘텐츠 -->
    <div class="main-content">
      <!-- 사용자 인사말 -->
      <div class="user-greeting">
        <h2>사용자명님, 오늘도 따뜻한 하루 보내세요 ☀️</h2>
        <button class="edit-btn">내 정보 수정</button>
      </div>

      <!-- 통계 정보 -->
      <div class="stats-box">
        <p>총 봉사 횟수: 10회</p>
        <p>입양 완료: 2건</p>
        <p>누적 기부 포인트: 120,000P</p>
        <p>보유 포인트: 3000 P</p>
      </div>

      <!-- 최근 활동 히스토리 -->
      <div class="history-box">
        <h3>최근 활동 히스토리</h3>
        <ul>
          <li>2024-04-30 입양완료</li>
          <li>2024-04-25 보호소 봉사활동 참여</li>
          <li>2024-04-23 입양신청</li>
          <li>2024-02-11 기부 1500 포인트</li>
        </ul>
      </div>
    </div>
  </div>
<%@ include file="/WEB-INF/views/common/index/indexFooter.jsp" %>
</body>
</html>