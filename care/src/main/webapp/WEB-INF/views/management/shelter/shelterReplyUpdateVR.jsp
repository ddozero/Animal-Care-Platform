<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="UTF-8">
<title>답글 수정</title>
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
      <h2>리뷰 답글 수정</h2>
      <form id="editForm" class="reply-form">
        <textarea id="content" required></textarea>
        <div class="bt-box">
          <button type="submit" class="reply-btn">수정하기</button>
          <button type="button" class="close-btn" onclick="window.close()">닫기</button>
          <input type="hidden" id="reviewIdx">
        </div>
      </form>
    </div>
  </div>

<script src="${pageContext.request.contextPath}/resources/web/common/commonUtils.js"></script>
<script>
document.addEventListener("DOMContentLoaded", async () => {
  const urlParams = new URLSearchParams(window.location.search);
  const reviewIdx = urlParams.get("reviewIdx");
  
  if (!reviewIdx) {
    alert("리뷰가 존재하지 않습니다.");
    window.close();
    return;
  }
  
  document.getElementById("editForm").addEventListener("submit", async (e) => {
    e.preventDefault();
    
    const content = document.getElementById("content").value.trim();
    if (!content) {
      alert("수정 내용을 입력해주세요.");
      return;
    }
    
    const dto = { content: content };
    
    try {
      const result = await API.put( "/care/api/management/shelter/volunteerReviews/reply/update/" + reviewIdx, dto);
      if (result.status === 200) {
        alert(result.message);
        // 부모창 새로고침
        if (window.opener) {
          try {
            const tabBtn = window.opener.document.querySelector(".tab-btn:nth-child(3)");
            const subBtn = window.opener.document.querySelector(".sub-btn:nth-child(1)");
            if (tabBtn && subBtn) {
              window.opener.changeTab(tabBtn, "reviewSection");
              window.opener.changeReviewTab(subBtn, "volunteerReview");
            } else {
              window.opener.location.reload();
            }
          } catch(e) {
            console.error("탭 전환 실패", e);
            window.opener.location.reload();
          }
        }
        window.close();
      } else {
        alert("수정 실패: " + result.message);
      }
    } catch(e) {
      console.error(e);
      alert("수정 중 오류가 발생했습니다. 관리자에게 문의바랍니다.");
    }
  });
});
</script>
</body>
</html>
