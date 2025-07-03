<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <% request.setAttribute("currentPage", "donation_form" ); %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" href="https://cdn.jsdelivr.net/gh/orioncactus/pretendard/dist/web/static/pretendard.css">
 <script src="${pageContext.request.contextPath}/resources/web/common/commonUtils.js"></script>
</head>
<body>
 <%@ include file="/WEB-INF/views/common/admin/adminHeader.jsp" %>

   <%@ include file="/WEB-INF/views/common/admin/adminSideBar.jsp" %>
</body>
</html>