<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
	<!DOCTYPE html>
	<html>

	<head>
		<meta charset="UTF-8">
		<title>기부</title>
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
						'<div class="donation-completionRate">' + donation.completionRate + '</div>' +
						'<div class="donation-completionAmount">' + donation.completionAmount + '</div>' +
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
			<h1>기부앤테이크</h1>
			<img src="${pageContext.request.contextPath}/resources/web/images/donationBanner.png" class="banner-image">
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