<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="/WEB-INF/views/common/index/indexHeader.jsp" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>commons Login page</title>
</head>
<body>
<form id="loginForm">
	<input type="text" name="id" placeholder="아이디" />
	<input type="password" name="password" placeholder="비밀번호" />
	<button type="submit">로그인</button>
</form>

<div id="errorMessage" style="color: red;"></div>

<script>
console.log("ROOT 경로는: '${root}'");

	document.getElementById('loginForm').addEventListener('submit', async function (e){
		e.preventDefault();

		const data = {
			id: document.querySelector('input[name="id"]').value,
			password: document.querySelector('input[name="password"]').value
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

			if (response.ok && result.status === 'OK') {
				alert('로그인 성공');
				location.href = '${root}/index.do';
			} else {
				document.getElementById('errorMessage').innerText = result.message || '로그인 실패';
			}
		} catch (error) {
			console.error('서버 오류: ', error);
			document.getElementById('errorMessage').innerText = '서버 오류 발생';
		}
	});
</script>

</body>
</html>