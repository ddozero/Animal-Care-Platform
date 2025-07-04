<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="UTF-8">

<title>봉사 리뷰 답글</title>

<style>
.popup-background {
	display: flex;
	justify-content: center;
	align-items: center;
	position: fixed;
	top: 0;
	left: 0;
	width: 100%;
	height: 100%;
	z-index: 9999;
}

.popup-box {
	width : 500px;
}
.popup-box h2 {
	margin-top: 0;
	margin-bottom: 20px;
	font-size: 23px;
	color: #3ACDB2;
	padding-bottom: 10px;
	text-align: center;
	border-bottom : 1px solid #eee;
}

.reply-form label {
	display: block;
	margin-bottom: 5px;
	font-weight: bold;
	color: #333;
}

.reply-form textarea {
	width: 100%;
	height: 120px;
	padding: 10px;
	border: 1px solid #ccc;
	border-radius: 4px;
	font-size: 14px;
	resize: vertical;
}

.reply-form button {
	width: 100%;
	padding: 12px;
	margin-top: 15px;
	background-color: #3a75ff;
	color: #fff;
	border: none;
	border-radius: 6px;
	font-size: 16px;
	cursor: pointer;
	transition: background-color 0.3s;
}

.reply-form button:hover {
	background-color: #2e5ed9;
}

.close-btn {
	position: absolute;
	top: 10px;
	right: 10px;
	background: #ff4d4d;
	color: #fff;
	border: none;
	padding: 6px 12px;
	border-radius: 4px;
	cursor: pointer;
}
</style>
</head>
<body>
	<div class="popup-background">
		<div class="popup-box">
			<button class="close-btn" onclick="closePopup()">닫기</button>
			<h2>리뷰 답글 남기기</h2>
			<form id="replyForm" class="reply-form">
				<textarea name="content" id="content" placeholder="답글을 입력해주세요"
					required></textarea>
				<button type="submit">등록</button>
				<input type="hidden" name="reviewIdx" id="reviewIdx"> <input
					type="hidden" name="volunteerIdx" id="volunteerIdx">
			</form>
		</div>
	</div>

	<script
		src="${pageContext.request.contextPath}/resources/web/common/commonUtils.js"></script>
	<script>
    document.addEventListener("DOMContentLoaded", function () {
      const form = document.getElementById("replyForm");

      form.addEventListener("submit", async function (e) {
        e.preventDefault();

        const reviewIdx = form.reviewIdx.value;
        const volunteerIdx = form.volunteerIdx.value;
        const content = form.content.value.trim();

        if (!content) {
          alert("답글 내용을 입력해주세요.");
          return;
        }

        const dto = {
          ref: reviewIdx,
          volunteerIdx: volunteerIdx,
          content: content
        };

        const result = await API.post("/care/api/management/shelter/volunteerReviews/reply", dto);

        if (result && result.status === 201) {
          alert(result.message);
          window.opener.location.reload();
          window.close();
        } else {
          alert("등록을 실패하였습니다. 관리자에게 문의바랍니다.");
        }
      });

      // URL 파라미터 세팅
      const urlParams = new URLSearchParams(window.location.search);
      const reviewIdx = urlParams.get("reviewIdx");
      const volunteerIdx = urlParams.get("volunteerIdx");

      if (reviewIdx) {
        document.getElementById("reviewIdx").value = reviewIdx;
      } else {
        alert("리뷰 번호가 없습니다.");
        window.close();
      }

      if (volunteerIdx) {
        document.getElementById("volunteerIdx").value = volunteerIdx;
      } else {
        alert("봉사활동 번호가 없습니다.");
        window.close();
      }
    });

    function closePopup() {
      window.close();
    }
  </script>
</body>
</html>
