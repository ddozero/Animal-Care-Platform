<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>index footer</title>

<c:set var="root" value="${pageContext.request.contextPath}" />

<style>
#footer-wrapper {
  background-color: #ffffff;
  border-top: 1px solid #ddd;
  padding: 15px 0;
  font-size: 14px;
  color: #555;
}

.footer-inner {
  width: 1200px;
  margin: 0 auto;
  text-align: center;
}

.footer-partners {
  margin-bottom: 10px;
}

.footer-partners span {
  display: block;
  font-weight: bold;
  margin-bottom: 10px;
}

.partner-logos img {
  height: 40px;
  margin: 0 10px;
  vertical-align: middle;
}

.footer-links {
  margin-bottom: 10px;
}

.footer-links a {
  color: #333;
  text-decoration: none;
  margin: 0 5px;
}

.footer-links a:hover {
  text-decoration: underline;
}

.footer-contact p {
  margin: 3px 0;
}

.footer-bottom {
  margin-top: 10px;
  display: flex;
  justify-content: center;
  align-items: center;
  gap: 10px;
}

.footer-bottom img {
  height: 30px;
}

</style>

</head>
<body>

<div id="footer-wrapper">
  <div class="footer-inner">

    <!-- 관련기관 로고 영역 -->
    <div class="footer-partners">
      <span>관련기관</span>
      <div class="partner-logos">
        <img src="${root}/resources/web/images/partner1.png" alt="기관1" />
        <img src="${root}/resources/web/images/partner2.png" alt="기관2" />
        <img src="${root}/resources/web/images/partner3.png" alt="기관3" />
        <img src="${root}/resources/web/images/partner4.png" alt="기관4" />
      </div>
    </div>

    <!-- 링크 메뉴 -->
    <div class="footer-links">
      <a href="#">시스템소개</a> |
      <a href="#">저작권정책</a> |
      <a href="#">이용안내</a> |
      <a href="#">개인정보처리방침</a>
    </div>

    <!-- 주소 및 연락처 -->
    <div class="footer-contact">
      <p>경상북도 김천시 혁신로 177 (골목동)</p>
      <p>전화문의: <a href="tel:054-810-8626">054-810-8626</a></p>
    </div>

    <!-- 카피라이트 + 인증 -->
    <div class="footer-bottom">
      <p>Copyright © 2025 같이 살개. All Rights Reserved.</p>
      <img src="${root}/resources/web/images/wa.png" alt="웹접근성 인증마크" />
    </div>

  </div>
</div>

</body>
</html>