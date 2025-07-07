<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
    <% int loginUserId=0; if (session.getAttribute("loginUser") !=null) {
        loginUserId=((com.animal.api.auth.model.response.LoginResponseDTO) session.getAttribute("loginUser")).getIdx();
        } %>
        <!DOCTYPE html>
        <html>


        <head>
            <meta charset="UTF-8">
            <title>당신에게 다시가는 길 - 기부앤테이크 상세</title>
            <script src="${pageContext.request.contextPath}/resources/web/common/commonUtils.js"></script>
            <style>
                /* 대표 이미지 컨테이너 추가 */
                #donation-image-wrapper {
                    position: relative;
                    width: 100%;
                    max-height: 280px;
                    overflow: hidden;
                    border-radius: 12px;
                    margin-bottom: 24px;
                }

                #donation-image {
                    width: 100%;
                    height: auto;
                    object-fit: cover;
                    display: block;
                }

                /* 텍스트 오버레이 */
                #donation-image-wrapper .overlay-text {
                    position: absolute;
                    bottom: 24px;
                    left: 0;
                    width: 100%;
                    color: white;
                    padding: 0 16px;
                    box-sizing: border-box;
                    text-align: center;
                }

                .overlay-text h2 {
                    font-size: 20px;
                    font-weight: bold;
                    margin-bottom: 8px;
                }

                .overlay-text .progress-bar-container {
                    width: 100%;
                    height: 6px;
                    background: rgba(255, 255, 255, 0.3);
                    border-radius: 4px;
                    margin: 8px auto 4px auto;
                    overflow: hidden;
                }

                .overlay-text .progress-bar-fill {
                    height: 100%;
                    background: #3ACDB2;
                    transition: width 0.3s ease-in-out;
                }

                /* 하단 텍스트 (좌우 배치) */
                .overlay-text .progress-info {
                    display: flex;
                    justify-content: space-between;
                    font-size: 14px;
                    padding: 0 4px;
                    color: #A0F0DF;
                }

                #donationUserListContainer>div {
                    padding: 10px 0;
                    border-bottom: 1px solid #eee;
                    font-size: 14px;
                    color: #333;
                }



                #donation-image2 {
                    width: 100%;
                    max-height: 400px;
                    object-fit: cover;
                    border-radius: 12px;
                    margin-bottom: 24px;
                }

                .button-primary {
                    background-color: #3ACDB2;
                    color: white;
                    border: none;
                    padding: 8px 16px;
                    font-size: 14px;
                    border-radius: 6px;
                    cursor: pointer;
                    transition: background 0.2s;
                }

                .button-primary:hover {
                    background-color: #2fb3a0;
                }

                .button-secondary {
                    background-color: #e0e0e0;
                    color: #333;
                    border: none;
                    padding: 8px 16px;
                    font-size: 14px;
                    border-radius: 6px;
                    cursor: pointer;
                    margin-left: 6px;
                    transition: background 0.2s;
                }

                .button-secondary:hover {
                    background-color: #ccc;
                }

                .section {
                    background: #f9f9f9;
                    padding: 20px 16px;
                    border-radius: 12px;
                    margin-bottom: 24px;
                    box-shadow: 0 1px 4px rgba(0, 0, 0, 0.05);
                }

                .section h3 {
                    font-size: 18px;
                    margin-bottom: 12px;
                    color: #333;
                    font-weight: bold;
                }

                .container {
                    max-width: 960px;
                    /* 또는 1000px, 90%, 적절히 조정 가능 */
                    margin: 0 auto;
                    padding: 16px;
                    box-sizing: border-box;
                }

                .row {
                    display: flex;
                    flex-wrap: wrap;
                    gap: 16px;
                    margin-bottom: 8px;
                }

                .row>div {
                    flex: 1;
                    min-width: 200px;
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
                    font-size: 14px;
                }

                .paging button:hover {
                    background: #3acdb2;
                    color: #fff;
                }

                /* 댓글 수정/삭제 버튼 위치 조정 */
                .donationComment-buttons {
                    margin-top: 8px;
                    display: flex;
                    gap: 6px;
                }

                .donation-amount-green {
                    color: #3ACDB2;
                    font-weight: bold;
                }

                .donationComment {
                    padding: 12px 0;
                    border-bottom: 1px solid #eee;
                    font-size: 14px;
                    color: #333;
                }

                .donationComment-nickname {
                    font-weight: bold;
                    color: #222;
                    margin-bottom: 4px;
                }

                .donationComment-content {
                    margin: 4px 0;
                    font-size: 14px;
                    color: #444;
                    line-height: 1.5;
                }

                .donationComment-createdAt {
                    font-size: 12px;
                    color: #888;
                }
            </style>

            <script>
                async function donationDetail() {//기부 상세 조회
                    const idx = location.pathname.split("/").pop();
                    const result = await API.get('/care/api/donations/' + idx);


                    if (result.status === 200) {// 조회 성공시

                    } else if (result.errorCode === 404) {//데이터가 존재 하지 않음
                        history.back();
                        return;
                    } else {//잘못된 요청
                        history.back();
                        return;
                    }

                    const donation = result.data;

                    document.getElementById("donation-image").src = '${pageContext.request.contextPath}' + donation.imagePaths[0];
                    document.getElementById("donation-name").innerText = donation.name;
                    document.getElementById("donation-completionRate").innerText = donation.completionRate + "%";
                    document.getElementById("donation-completionAmount").innerText = donation.completionAmount.toLocaleString() + "원";
                    document.getElementById("donation-image2").src = '${pageContext.request.contextPath}' + donation.imagePaths[1];
                    document.getElementById("donation-startDate").innerText = donation.startDate;
                    document.getElementById("donation-endDate").innerText = donation.endDate;
                    document.getElementById("donation-status").innerText = donation.status;
                    document.getElementById("donation-amount").innerText = donation.amount.toLocaleString() + "원";
                    document.getElementById("donation-sponsor").innerText = donation.sponsor;
                    document.getElementById("donation-sponsorDetail").innerText = donation.sponsorDetail;
                    document.getElementById("donation-content").innerText = donation.content;

                    document.getElementById("progressBarFill").style.width = donation.completionRate + "%";


                }

                async function donationUserList(cp) {//기부 내역 조회

                    const container = document.getElementById("donationUserListContainer");
                    container.innerHTML = "";
                    const idx = location.pathname.split("/").pop();
                    const result = await API.get('/care/api/donations/' + idx + '/userLists?cp=' + cp)

                    if (result.status === 200) {// 조회 성공시
                    } else if (result.errorCode === 404) {//데이터가 존재 하지 않음
                    } else {//잘못된 요청
                    }

                    const donationUsers = result.data;
                    const pageInfo = result.pageInfo
                    for (const donationUser of donationUsers) {
                        const card = document.createElement("div");
                        card.innerHTML =
                            '<span>' + donationUser.nickname + '님 </span>' +
                            '<span class="donation-amount-green">' + donationUser.donatedAmount.toLocaleString() + '원</span>' +
                            ' 참여';
                        container.appendChild(card);
                    }





                    // 페이징 함수 실행
                    makePaging(
                        pageInfo.totalCnt,
                        pageInfo.listSize,
                        pageInfo.pageSize,
                        pageInfo.cp,
                        "pagingAreaUser",
                        donationUserList
                    );

                }


                async function donationCommentList(cp) {//기부 댓글 조회

                    const container = document.getElementById("donationCommentContainer");
                    container.innerHTML = "";
                    const idx = location.pathname.split("/").pop();
                    const result = await API.get('/care/api/donations/' + idx + '/comments?cp=' + cp)

                    if (result.status === 200) {// 조회 성공시
                    } else if (result.errorCode === 404) {//데이터가 존재 하지 않음
                    } else {//잘못된 요청
                    }

                    const donationComments = result.data;
                    const pageInfo = result.pageInfo

                    for (const donationComment of donationComments) {
                        const card = document.createElement("div");
                        card.className = "donationComment";//카드 클래스이름 지정
                        card.dataset.userId = donationComment.userIdx;//댓글 전체 조회시 userIdx값 변수에 넣는 과정
                        card.dataset.commentId = donationComment.idx;//댓글 전체 조회시 댓글 idx값 변수에 넣는 과정
                        card.innerHTML =
                            '<div class="donationComment-nickname">' + donationComment.nickname + '</div>' +
                            '<div class="donationComment-content">' + donationComment.content + '</div>' +
                            '<div class="donationComment-createdAt">' + donationComment.createdAt + '</div>' +
                            '<div class="donationComment-buttons">' +
                            '<button class="button-primary" onclick="enterEditMode(this)">수정</button>' +
                            '<button class="button-secondary" onclick="deleteComment(' + donationComment.idx + ')">삭제</button>' +
                            '</div>';
                        container.appendChild(card);
                    }


                    // 페이징 함수 실행
                    makePaging(
                        pageInfo.totalCnt,
                        pageInfo.listSize,
                        pageInfo.pageSize,
                        pageInfo.cp,
                        "pagingAreaComment",
                        donationCommentList
                    );

                }

                async function submitComment() {//응원 댓글 등록
                    const content = document.getElementById("commentContent").value.trim();
                    if (!content) {
                        alert("댓글 내용을 입력해주세요.");
                        return;
                    }

                    const idx = location.pathname.split("/").pop();

                    const body = {
                        donationIdx: idx,
                        content: content
                    };

                    const result = await API.post('/care/api/donations/' + idx + '/comments', body);

                    if (result.status === 201) {
                        alert("댓글 등록 성공");
                        document.getElementById("commentContent").value = "";
                        donationCommentList(1);
                        return;
                    } else if (result.errorCode === 401) {
                        location.href = "/care/login";
                        return;
                    } else {
                        location.reload(true);
                        return;
                    }
                }

                function enterEditMode(button) {//응원 댓글 수정 모드
                    const commentDiv = button.closest(".donationComment"); //하나의 댓글 정보를 변수에 담고
                    const contentDiv = commentDiv.querySelector(".donationComment-content");//하나의 댓글의 원문 내용을 변수에 담고
                    const originalContent = contentDiv.innerText;//원문 내용을 다시 변수에 담음
                    const commentId = parseInt(commentDiv.dataset.commentId);//댓글 idx를 변수에 담음
                    const commentUserId = parseInt(commentDiv.dataset.userId);//댓글의 userIdx를 변수에 담음

                    const loginUserId = parseInt(document.body.dataset.loginUserId || "0"); //로그인했을때의 userIdx값

                    if (!loginUserId) {
                        alert("로그인 후 이용해주세요.");
                        location.href = "/care/login";
                        return;
                    }

                    if (commentUserId !== loginUserId) {
                        alert("본인이 작성한 댓글만 수정할 수 있습니다.");
                        return;
                    }
                    const escapedContent = originalContent
                        .replace(/\\/g, '\\\\')
                        .replace(/'/g, '\\\'')
                        .replace(/"/g, '\\"')
                        .replace(/\n/g, '\\n');



                    contentDiv.innerHTML = '<textarea class="edit-area" rows="3">' + originalContent + '</textarea>';//원문 댓글이 출력되는것을 textare로 바꿈

                    const buttonBox = commentDiv.querySelector(".donationComment-buttons");//버튼 들을 변수에 담음


                    buttonBox.innerHTML =
                        '<button class="button-primary" onclick="submitEdit(this,' + commentId + ')">저장</button>' +
                        '<button class="button-secondary" onclick="cancelEdit(this, \'' + escapedContent + '\')">취소</button>';



                }
                function cancelEdit(button, originalContent) {
                    const commentDiv = button.closest(".donationComment"); // 댓글 블록
                    const contentDiv = commentDiv.querySelector(".donationComment-content"); // 댓글 내용 영역

                    // 원래 텍스트로 되돌리기 (중첩 div 제거)
                    contentDiv.innerText = originalContent;

                    // 버튼 영역 복원
                    const commentId = commentDiv.dataset.commentId;

                    const buttonBox = commentDiv.querySelector(".donationComment-buttons");
                    buttonBox.innerHTML =
                        '<button class="button-primary" onclick="enterEditMode(this)">수정</button>' +
                        '<button class="button-secondary" onclick="deleteComment(' + commentId + ')">삭제</button>';

                }


                async function submitEdit(button, commentIdx) {
                    const commentDiv = button.closest(".donationComment");//하나의 수정된 댓글 정보를 변수에 담고
                    const textarea = commentDiv.querySelector("textarea.edit-area");//수정한 댓글을 변수에 담고
                    const newContent = textarea.value.trim();//가공해서 수정댓글을 변수에 넣음
                    const donationIdx = location.pathname.split("/").pop();//url로 부터 기부 번호 변수에 담고
                    const commentId = parseInt(commentDiv.dataset.commentId);//댓글 idx를 변수에 담음
                    const commentUserId = parseInt(commentDiv.dataset.userId);//댓글의 userIdx를 변수에 담음
                    if (!newContent) {
                        alert("수정할 내용을 입력하세요.");
                        return;
                    }

                    const body = {
                        idx: commentId,
                        content: newContent,
                        donationIdx: donationIdx
                    };

                    const result = await API.put('/care/api/donations/' + donationIdx + '/comments/' + commentId, body);

                    if (result.status === 200) {
                        const contentDiv = commentDiv.querySelector(".donationComment-content");
                        contentDiv.innerText = newContent;

                        const buttonBox = commentDiv.querySelector(".donationComment-buttons");
                        buttonBox.innerHTML =
                            '<button class="button-primary" onclick="enterEditMode(this)">수정</button>' +
                            '<button class="button-secondary" onclick="deleteComment(' + commentIdx + ')">삭제</button>';

                    } else if (result.errorCode === 401) {
                        location.href = "/care/login";
                        return;
                    } else {
                        location.reload(true);
                        return;
                    }
                }

                async function deleteComment(commentId) {//응원 댓글 삭제
                    const loginUserId = parseInt(document.body.dataset.loginUserId || "0");




                    if (!loginUserId) {
                        alert("로그인 후 이용해주세요.");
                        location.href = "/care/login";
                        return;
                    }



                    const confirmed = confirm("정말로 이 댓글을 삭제하시겠습니까?");
                    if (!confirmed) return;

                    const donationIdx = location.pathname.split("/").pop();



                    const result = await API.delete('/care/api/donations/' + donationIdx + '/comments/' + commentId);

                    if (result.errorCode === 400) {
                        return;
                    } else if (result.status === 200) {
                        alert("댓글이 삭제되었습니다.");
                        donationCommentList(1); // 댓글 목록 갱신
                        return;
                    } else {
                        return;
                    }
                }

                // 기부 팝업 열기
                async function openDonatePopup() {
                    const userId = parseInt(document.body.dataset.loginUserId || "0");
                    const donationIdx = location.pathname.split("/").pop();

                    if (!userId) {
                        alert("로그인이 필요합니다.");
                        location.href = "/care/login";
                        return;
                    }

                    // 회원 보유 포인트 조회
                    const result = await API.get('/care/api/donations/' + donationIdx + '/users/' + userId);
                    if (result.status === 200) {
                        document.getElementById("userPointText").innerText = result.data + "";
                        document.getElementById("donationPopup").style.display = "block";
                    } else {
                        alert("포인트 조회에 실패했습니다.");
                    }
                }

                // 기부 팝업 닫기
                function closeDonatePopup() {
                    document.getElementById("donationInput").value = "";
                    document.getElementById("donationPopup").style.display = "none";
                }

                // 기부하기 실행
                async function submitDonation() {
                    const userId = parseInt(document.body.dataset.loginUserId || "0");
                    const donationIdx = location.pathname.split("/").pop();
                    const donatedAmount = parseInt(document.getElementById("donationInput").value);

                    if (!donatedAmount || donatedAmount <= 0) {
                        alert("기부할 포인트를 올바르게 입력해주세요.");
                        return;
                    }

                    const body = {
                        userIdx: userId,
                        donationIdx: donationIdx,
                        donatedAmount: donatedAmount
                    };

                    const result = await API.post('/care/api/donations/' + donationIdx + '/users', body);
                    if (result.status === 201) {
                        alert("기부가 성공적으로 완료되었습니다.");
                        closeDonatePopup();
                        donationDetail();
                        donationUserList(1);
                    } else if (result.status === 400) {
                        alert(result.message || "기부에 실패했습니다.");
                    } else if (result.status === 401) {
                        alert("로그인이 필요합니다.");
                        location.href = "/care/login";
                    }
                }


            </script>


        </head>

        <body data-login-user-id="<%= loginUserId %>">
            <%@ include file="/WEB-INF/views/common/index/indexHeader.jsp" %>
                <div class="container">
                    <!-- 대표 이미지 + 진행률 -->
                    <div id="donation-image-wrapper">
                        <img id="donation-image" src="" alt="기부 대표이미지">
                        <div class="overlay-text">
                            <h2 id="donation-name"></h2>
                            <div class="progress-bar-container">
                                <div class="progress-bar-fill" id="progressBarFill" style="width: 0%;"></div>
                            </div>
                            <div class="progress-info">
                                <span id="donation-completionRate"></span>
                                <span id="donation-completionAmount"></span>
                            </div>
                        </div>
                    </div>

                    <!-- 기부 소개 -->
                    <div class="section">
                        <h3>기부소개</h3>
                        <img id="donation-image2" src="" alt="기부 상세이미지">
                        <div id="donation-content"></div>
                    </div>

                    <!-- 사용계획 -->
                    <!-- 사용계획 -->
                    <div class="section">
                        <h3>이렇게 사용됩니다</h3>

                        <div class="row">
                            <div>모금 시작일: <span id="donation-startDate"></span></div>
                            <div>모금 마감일: <span id="donation-endDate"></span></div>
                        </div>

                        <div class="row">
                            <div>모금 상태: <span id="donation-status"></span></div>
                            <div>모금액: <span id="donation-amount"></span></div>
                        </div>

                        <div class="row">
                            <div>모금처: <span id="donation-sponsor"></span></div>
                            <div>상세내용: <span id="donation-sponsorDetail"></span></div>
                        </div>
                        <!-- ✅ 카드 스타일 제거하고 일반 div로 변경 -->
                        <div style="margin-top: 16px;">
                            <button class="button-primary" onclick="openDonatePopup()">기부하기</button>
                        </div>

                    </div>




                    <!-- 기부 참여 내역 -->
                    <div class="section">
                        <h3>기부 참여 내역</h3>
                        <div id="donationUserListContainer"></div>
                        <div id="pagingAreaUser" class="paging"></div>
                    </div>

                    <!-- 댓글 영역 -->
                    <div class="section">
                        <h3>댓글</h3>
                        <div id="commentWriteBox" style="margin-bottom:12px;">
                            <textarea id="commentContent" placeholder="댓글을 입력하세요" rows="4" cols="50"></textarea><br />
                            <button class="button-primary" onclick="submitComment()">등록</button>
                        </div>
                        <div id="donationCommentContainer"></div>
                        <div id="pagingAreaComment" class="paging"></div>
                    </div>
                </div>
                <%@ include file="/WEB-INF/views/common/index/indexFooter.jsp" %>

                    <!-- 기부 팝업 -->
                    <div id="donationPopup" style="display:none; position:fixed; left:50%; top:50%; transform:translate(-50%, -50%);
        background:white; border:1px solid #ccc; padding:20px; z-index:9999;">
                        <div>보유 포인트: <span id="userPointText"></span> P</div>
                        <div>
                            기부할 포인트:
                            <input type="number" id="donationInput" min="1" placeholder="포인트 입력" />
                        </div>
                        <div style="margin-top:10px;">
                            <button class="button-primary" onclick="submitDonation()">기부하기</button>
                            <button class="button-secondary" onclick="closeDonatePopup()">닫기</button>
                        </div>
                    </div>

                    <script>
                        window.addEventListener("DOMContentLoaded", function () {
                            donationDetail();
                            donationCommentList(1);
                            donationUserList(1);
                        });
                    </script>
        </body>

        </html>