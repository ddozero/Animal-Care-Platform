<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="UTF-8">

<title>입양 리뷰 답글</title>

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
	width: 500px;
	padding: 20px;
	box-sizing: border-box; 
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

.reply-form {
  text-align: center;
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
  box-sizing: border-box;    
  padding: 10px;
  font-size: 16px;
  font-family: 'Pretendard', sans-serif;
  border: 1px solid #DBDBDB;
  border-radius: 4px;
  background-color: #fff;
  resize: vertical;
}

.bt-box {
  text-align: center;
}

.reply-btn {
  background: #3ACDB2;
  border: none;
  border-radius: 6px;
  padding: 8px 16px;
  cursor: pointer;
  transition: background 0.25s;
  margin-left: auto;
  color: #fff;
  margin-top : 30px;
}

.close-btn {
  background: #999;
  border: none;
  border-radius: 6px;
  padding: 8px 16px;
  cursor: pointer;
  transition: background 0.25s;
  margin-left: auto;
  color: #fff;
  margin-top : 30px;
}

.reply-button:hover {
  background: #ccc;
}

textarea:disabled {
  background-color: #fff; /* disabled 상태일 때 색상 변경 */
  border : none;
}

</style>
</head>
<body>
	<div class="popup-background">
		<div class="popup-box">
			<h2>리뷰 답글 남기기</h2>
			<form id="replyForm" class="reply-form">
				<textarea name="content" id="content" placeholder="답글을 입력해주세요" required></textarea>
				<div class="bt-box">
				<button type="submit" class="reply-btn">등록</button>
			    <button class="close-btn" onclick="closePopup()">닫기</button>
				<input type="hidden" name="reviewIdx" id="reviewIdx"> 
				<input type="hidden" name="animalIdx" id="animalIdx">
				</div>
			</form>
		</div>
	</div>

	<script src="${pageContext.request.contextPath}/resources/web/common/commonUtils.js"></script>
	<script>
	document.addEventListener("DOMContentLoaded", function () {
		  const form = document.getElementById("replyForm");

		  const urlParams = new URLSearchParams(window.location.search);
		  const reviewIdx = urlParams.get("reviewIdx");
		  const animalIdx = urlParams.get("animalIdx");

		  if (reviewIdx) {
		    document.getElementById("reviewIdx").value = reviewIdx;
		  } else {
		    alert("리뷰가 존재하지 않습니다.");
		    window.close();
		  }

		  if (animalIdx) {
		    document.getElementById("animalIdx").value = animalIdx;
		  } else {
		    alert("입양 동물이 없습니다.");
		    window.close();
		  }

		  form.addEventListener("submit", async function (e) {
		    e.preventDefault();

		    const reviewIdx = form.reviewIdx.value;
		    const animalIdx = form.animalIdx.value;
		    const content = form.content.value.trim();

		    if (!content) {
		      alert("답글 내용을 입력해주세요.");
		      return;
		    }

		    const dto = {
		      ref: reviewIdx,
		      animalIdx: animalIdx,
		      content: content
		    };

		    const result = await API.post("/care/api/management/shelter/adoptionReviews/reply", dto);

		    if (result && result.status === 201) {
		      alert(result.message);

		      if (window.opener) {
		        try {
		          const tabBtn = window.opener.document.querySelector(".tab-btn:nth-child(3)");
		          const subBtn = window.opener.document.querySelector(".sub-btn:nth-child(2)");

		          if (tabBtn && subBtn) {
		            window.opener.changeTab(tabBtn, "reviewSection");
		            window.opener.changeReviewTab(subBtn, "adoptionReview");
		          } else {
		            window.opener.location.reload();
		          }
		        } catch (e) {
		          console.error("탭 강제 전환 실패", e);
		          window.opener.location.reload();
		        }
		      }
		      window.close();
		    } else {
		      alert("등록을 실패하였습니다. 관리자에게 문의바랍니다.");
		    }
		  });
		});

		function closePopup() {
		  window.close();
		}

  </script>
</body>
</html>
