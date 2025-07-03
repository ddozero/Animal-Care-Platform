<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>답글 폼</title>

    <style>
        /* 팝업 배경 */
        .popup-background {
            display: block;
            position: fixed;
            top: 0;
            left: 0;
            width: 100%;
            height: 100%;
            background-color: rgba(0, 0, 0, 0.5); /* 반투명 배경 */
            z-index: 999;
            justify-content: center;
            align-items: center;
        }

        /* 팝업 박스 스타일 */
        .popup-box {
            background-color: white;
            padding: 20px;
            width: 400px;
            border-radius: 8px;
            box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1);
        }

        /* 닫기 버튼 */
        .close-btn {
            background-color: #ff4d4d;
            color: white;
            border: none;
            padding: 10px;
            cursor: pointer;
            font-size: 16px;
            border-radius: 4px;
            float: right;
        }

        /* 답글 폼 스타일 */
        .reply-form textarea {
            width: 100%;
            height: 100px;
            padding: 10px;
            margin: 10px 0;
            border-radius: 4px;
            border: 1px solid #ccc;
            font-size: 14px;
        }

        .reply-form button {
            width: 100%;
            padding: 10px;
            background-color: #4CAF50;
            color: white;
            border: none;
            border-radius: 4px;
            font-size: 16px;
        }
    </style>
</head>
<body>

<!-- 팝업 배경 -->
<div id="popupBackground" class="popup-background">
    <div class="popup-box">
        <button class="close-btn" onclick="closePopup()">닫기</button>
        <h2>답글을 입력하세요</h2>

        <!-- 답글 폼 -->
        <form id="replyForm" class="reply-form" method="POST" action="/submitReply">
            <input type="hidden" name="reviewIdx" id="reviewIdx"> <!-- reviewIdx 동적으로 변경 -->
            <textarea name="content" id="content" placeholder="답글을 입력하세요" required></textarea>
            <button type="submit">답글 남기기</button>
        </form>
    </div>
</div>

<script>
// URL에서 쿼리 파라미터로 reviewIdx를 가져오는 함수
function getReviewIdxFromUrl() {
    const urlParams = new URLSearchParams(window.location.search);
    return urlParams.get('reviewIdx');  // reviewIdx를 반환
}

// 팝업을 열 때 reviewIdx를 동적으로 설정
function openPopup() {
    const reviewIdx = getReviewIdxFromUrl(); // URL에서 reviewIdx 값 가져오기
    if (reviewIdx) {
        document.getElementById("reviewIdx").value = reviewIdx;  // 폼에 reviewIdx 설정
        document.getElementById("popupBackground").style.display = "flex";  // 팝업 보이기
    } else {
        alert("Review ID가 없습니다.");
    }
}

// 팝업을 닫는 함수
function closePopup() {
    document.getElementById("popupBackground").style.display = "none";
}

// 페이지 로드 시 팝업을 열기 위해 호출 (예시)
// openPopup();  // 실제 호출은 `reviewIdx`가 있는 링크에서 팝업을 열도록 설정
</script>

</body>
</html>
