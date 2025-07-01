<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<script>
async function donationList(){
	try{
		const  = document.getElement
			
		
		const res = await fetch("/care/api/donations") //서버에 get방식으로 데이터 요청
		console.log(res.status);//http 상태코드를 담음
		console.log(res.ok) //res.status가 200~299면 true 반환
		const date = await res.json()//요청 결과를
	}
			
		
}

</script>
</head>
<body>
	<div class="donation-list-container">
		<h2>기부앤테이크</h2>

		<div class="donation-banner">
			<!-- 배너 이미지 또는 안내 문구 자리 -->
		</div>

		<div class="donation-cards">

			<!-- 반복될 기부 카드 -->
			<div class="donation-card">
				<div class="thumbnail">
					<img src="#" alt="기부 이미지" />
				</div>
				<div class="info">
					<p class="title">강아지를 도와주세요</p>
					<p class="sponsor">모금단체: 서울보호소</p>
					<p class="progress">현재 모금률: 75%</p>
					<p class="amount">현재 모금액: 1,200,000원</p>
				</div>
			</div>
			<!-- 반복 끝 -->

		</div>

		<div class="pagination">
			<span>&lt;</span> <span class="page">1</span> <span class="page">2</span>
			<span class="page">3</span> <span>&gt;</span>
		</div>
	</div>



</body>
</html>