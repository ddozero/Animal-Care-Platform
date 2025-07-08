<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
	<!DOCTYPE html>
	<html>

	<head>
		<meta charset="UTF-8">
		<title>당신에게 다시가는 길 - 기부앤테이크 목록</title>
		<style>
			.banner-image {
				width: 300px;
				height: auto;
				display: block;
				/* 필요 시 가운데 정렬 대비 */
				margin: 0 auto;
				/* 가운데 정렬 */
			}
		</style>
		<script src="${pageContext.request.contextPath}/resources/web/common/commonUtils.js"></script>
		<style>
			body {
				font-family: 'Pretendard', '맑은 고딕', sans-serif;
				background-color: #fff;
				padding: 40px 20px;
			}

			h1 {
				text-align: center;
				font-size: 28px;
				color: #3ACDB2;
				margin-top: 40px;
				margin-bottom: 20px;
			}

			.banner-image {
				width: 320px;
				height: auto;
				display: block;
				margin: 0 auto 40px;
			}

			#donationListContainer {
				max-width: 1200px;
				margin: 0 auto;
				display: grid;
				grid-template-columns: repeat(5, 1fr);
				/* ✅ 5개씩 한 줄에 */
				gap: 24px;
			}


			#donationListContainer>div {
				background: #fff;
				border-radius: 12px;
				box-shadow: 0 2px 8px rgba(0, 0, 0, 0.05);
				overflow: hidden;
				transition: transform 0.2s;
			}

			#donationListContainer>div:hover {
				transform: translateY(-4px);
			}

			#donationListContainer img {
				width: 100%;
				aspect-ratio: 1 / 1;
				object-fit: cover;
				display: block;
			}

			#donationListContainer a {
				text-decoration: none;
				color: inherit;
				display: block;
			}

			.donation-name {
				font-weight: bold;
				font-size: 16px;
				padding: 12px 16px 4px 16px;
				overflow: hidden;
				text-overflow: ellipsis;
				white-space: nowrap;
			}

			.donation-sponsor,
			.donation-status,
			.donation-completionRate,
			.donation-completionAmount {
				font-size: 14px;
				color: #555;
				padding: 2px 16px;
			}

			.paging {
				margin: 40px 0;
				text-align: center;
			}

			.paging button {
				border: none;
				background: #fff;
				padding: 6px 12px;
				margin: 0 2px;
				border-radius: 4px;
				cursor: pointer;
				box-shadow: 0 1px 4px rgba(0, 0, 0, .08);
				transition: background .2s;
			}

			.paging button:hover {
				background: #3acdb2;
				color: #fff;
			}

			.progress-bar-container {
				margin: 8px 16px;
				background-color: #e0e0e0;
				border-radius: 8px;
				height: 8px;
				width: 90%;
				overflow: hidden;
			}

			.progress-bar-fill {
				height: 100%;
				background-color: #3acdb2;
				transition: width 0.3s ease-in-out;
			}

			.donation-progress-info {
				display: flex;
				justify-content: space-between;
				align-items: center;
				padding: 4px 16px 12px;
				font-size: 14px;
			}

			.donation-percentage {
				color: #3acdb2;
				font-weight: bold;
			}

			.donation-amount {
				color: #333;
				font-weight: 600;
			}

			.title-detail {
				text-align: center;
				margin-bottom: 40px;
				color: #666;
				font-size: 16px;
			}

			.header-title {
				font-size: 28px;
				font-weight: bold;
				margin-top: 50px;
				margin-bottom: 20px;
				text-align: center;
				color: #3ACDB2;
			}
		</style>

		<script>
			async function donationList(cp) {

				const container = document.getElementById("donationListContainer");
				container.innerHTML = "";


				const result = await API.get('/care/api/donations?cp=' + cp);
				if (result.status != 200) {
					history.back();
					return;
				}


				const donations = result.data;
				const pageInfo = result.pageInfo


				for (const donation of donations) {
					const card = document.createElement("div");
					card.innerHTML =
						'<a href="${pageContext.request.contextPath}/donations/' + donation.idx + '">' +
						'<img src="${pageContext.request.contextPath}' + donation.imagePath + '" alt="기부 이미지"/>' +
						'<div class="donation-name">' + donation.name + '</div>' +
						'<div class="donation-sponsor">' + donation.sponsor + '</div>' +
						'<div class="donation-status">' + donation.status + '</div>' +
						'<div class="progress-bar-container">' +
						'<div class="progress-bar-fill" style="width:' + donation.completionRate + '%;"></div>' +
						'</div>' +
						'<div class="donation-progress-info">' +
						'<span class="donation-percentage">' + donation.completionRate + '%</span>' +
						'<span class="donation-amount">' + donation.completionAmount.toLocaleString() + '원</span>' +
						'</div>' +
						'</a>';



					container.appendChild(card);

				}
				// 페이징 함수 실행
				makePaging(
					pageInfo.totalCnt,
					pageInfo.listSize,
					pageInfo.pageSize,
					pageInfo.cp,
					"pagingArea",
					donationList
				);
			}
		</script>

	</head>

	<body>
		<%@ include file="/WEB-INF/views/common/index/indexHeader.jsp" %>
			<div class="header-title">기부앤테이크</div>
			<div class="title-detail">당신의 따뜻한 마음이 유기동물에게 봄이 되어줍니다.</div>
			<div id="donationListContainer"></div>
			<div id="pagingArea" class="paging"></div>
			<script>
				window.addEventListener("DOMContentLoaded", function () {
					donationList(1);
				});

			</script>
			<%@ include file="/WEB-INF/views/common/index/indexFooter.jsp" %>
	</body>

	</html>