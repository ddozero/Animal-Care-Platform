<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
    <% int loginUserId=0; if (session.getAttribute("loginUser") !=null) {
        loginUserId=((com.animal.api.auth.model.response.LoginResponseDTO) session.getAttribute("loginUser")).getIdx();
        } %>
        <!DOCTYPE html>
        <html>

        <head>
            <meta charset="UTF-8">
            <title>당신에게 다시가는 길 - 자유게시판 상세</title>
            <script src="${pageContext.request.contextPath}/resources/web/common/commonUtils.js"></script>
            <link rel="stylesheet"
                href="https://cdn.jsdelivr.net/gh/orioncactus/pretendard/dist/web/static/pretendard.css">

            <style>
                .board {
                    font-family: 'Pretendard', sans-serif;
                    background-color: #fff;
                    padding: 60px 20px 40px;
                }

                .header-title {
                    font-size: 28px;
                    font-weight: bold;
                    margin-bottom: 20px;
                    text-align: center;
                    color: #3ACDB2;
                }

                .title-detail {
                    text-align: center;
                    margin-bottom: 40px;
                    color: #666;
                    font-size: 16px;
                }

                .board-container {
                    max-width: 1200px;
                    margin: 0 auto;
                    background-color: #fff;
                    padding: 20px;
                    border: 1px solid #DBDBDB;
                    border-radius: 6px;
                }

                .board-content-table {
                    width: 100%;
                    border-collapse: collapse;
                    font-size: 16px;
                    margin-bottom: 30px;
                    table-layout: fixed;
                }

                .board-content-table th {
                    background-color: #f9f9f9;
                    padding: 12px 10px;
                    border-bottom: 1px solid #ddd;
                    color: #333;
                    vertical-align: top;
                    text-align: center;
                    width: 15%;
                }

                .board-content-table td {
                    padding: 12px 10px;
                    border-bottom: 1px solid #DBDBDB;
                    color: #333;
                    width: 35%;
                }

                .content-box {
                    border-radius: 4px;
                    min-height: 400px;
                    padding: 20px;
                    font-size: 16px;
                    line-height: 1.6;
                    white-space: pre-line;
                    color: #444;
                }

                .boardComment {
                    padding: 10px;
                    border-bottom: 1px solid #eee;
                    margin-bottom: 10px;
                }

                .boardComment-nickname {
                    font-weight: bold;
                    margin-bottom: 6px;
                }

                .boardComment-content {
                    margin-bottom: 6px;
                }

                .boardComment-createdAt {
                    font-size: 12px;
                    color: #999;
                    margin-bottom: 8px;
                }

                .boardComment-buttons button.button-primary {
                    background-color: #3ACDB2;
                    color: white;
                }

                .boardComment-buttons button.button-secondary {
                    background-color: #e0e0e0;
                    color: #333;
                }


                .boardComment-buttons button:hover {
                    background-color: #bbb;
                }

                /* 공통 버튼 스타일 */
                .button-primary {
                    background-color: #3ACDB2 !important;
                    color: white !important;
                    border: none !important;
                    padding: 8px 16px !important;
                    font-size: 14px !important;
                    border-radius: 6px !important;
                    cursor: pointer !important;
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

                #heart-btn,
                #reply-btn {
                    background-color: #3ACDB2;
                    color: white;
                    border: none;
                    padding: 8px 16px;
                    font-size: 14px;
                    border-radius: 6px;
                    cursor: pointer;
                    transition: background 0.2s;
                }

                #heart-btn:hover,
                #reply-btn:hover {
                    background-color: #2fb3a0;
                }



                #buttonLineWrapper button,
                #buttonLineWrapper div>button {
                    margin: 0;
                    /* 버튼끼리 간격은 gap으로 처리 */
                }

                .reply-like-wrapper {
                    display: flex;
                    justify-content: center;
                    margin-top: 20px;
                    margin-bottom: 30px;
                    /* ✅ 아래 간격 추가 */
                }

                #buttonLineWrapper {
                    display: flex;
                    justify-content: space-between;
                    width: 100%;
                    max-width: 100%;
                    padding: 0 20px;
                    /* 좌우 여백 */
                    box-sizing: border-box;
                }

                .button-left,
                .button-center,
                .button-right {
                    display: flex;
                    align-items: center;
                }

                .button-center {
                    flex: 1;
                    justify-content: center;
                    gap: 10px;
                }

                .button-left,
                .button-right {
                    flex: 1;
                }

                .button-left {
                    justify-content: flex-start;
                }

                .button-right {
                    justify-content: flex-end;
                }

                .board-container+.board-container {
                    margin-top: 40px;
                    /* 댓글 작성 박스 위쪽에 여백 */
                }

                .paging {
                    margin: 28px 0;
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
            </style>

            <script>
                async function boardDetail() {
                    const boardIdx = location.pathname.split("/").pop();
                    const loginUserId = parseInt(document.body.dataset.loginUserId || "0");
                    const apiUrl = loginUserId > 0
                        ? '/care/api/boards/' + boardIdx + '/auth'
                        : '/care/api/boards/' + boardIdx;

                    const result = await API.get(apiUrl);
                    const board = result.data;

                    document.getElementById("board-idx").innerText = board.idx;
                    document.getElementById("board-createdAt").innerText = board.createdAt;
                    document.getElementById("board-nickname").innerText = board.nickname;
                    document.getElementById("board-nickname").dataset.userId = board.userIdx;
                    document.getElementById("board-views").innerText = board.views;
                    document.getElementById("board-likeCount").innerText = board.likeCount;
                    document.getElementById("board-title").innerText = board.title;
                    document.getElementById("board-content").innerText = board.content;

                    // 파일 초기화
                    const filesContainer = document.getElementById("board-files");
                    filesContainer.innerHTML = "";
                    if (board.filePaths && board.filePaths.length > 0) {
                        board.filePaths.forEach(function (filePath) {
                            const link = document.createElement("a");
                            const fileName = filePath.split('/').pop();
                            link.href = '${pageContext.request.contextPath}' + filePath;
                            link.innerText = fileName;
                            link.setAttribute("download", fileName);
                            link.style.display = "block";
                            filesContainer.appendChild(link);
                        });
                    } else {
                        filesContainer.innerText = "첨부파일 없음";
                    }

                    // 버튼 초기화
                    document.getElementById("boardActionButtons").innerHTML =
                        '<button class="button-primary" onclick="enterBoardEditMode()">수정</button>' +
                        '<button class="button-secondary" onclick="deleteBoard()">삭제</button>';


                    if (loginUserId > 0 && board.heart !== undefined) {
                        const heartButton = document.createElement("button");
                        heartButton.id = "heart-btn";
                        heartButton.innerText = board.heart ? "❤️ 좋아요 취소" : "🤍 좋아요";
                        heartButton.onclick = toggleHeart;
                        document.getElementById("heartButtonContainer").innerHTML = "";
                        document.getElementById("heartButtonContainer").appendChild(heartButton);

                    }

                    const replyArea = document.getElementById("replyArea");
                    if (replyArea) {
                        if (loginUserId > 0) {
                            replyArea.style.display = "block";
                        } else {
                            replyArea.style.display = "none";
                        }
                    }


                }
                function goBoardReply() {
                    const loginUserId = parseInt(document.body.dataset.loginUserId || "0");
                    if (!loginUserId) {
                        alert("로그인 후 이용해주세요.");
                        location.href = "/care/login";
                        return;
                    }

                    const boardIdx = location.pathname.split("/").pop();
                    location.href = "/care/boards/form?origin=" + boardIdx;
                }



                async function toggleHeart() {
                    const boardIdx = location.pathname.split("/").pop();
                    const heartBtn = document.getElementById("heart-btn");

                    // 현재 상태 텍스트로 판단 (❤️이면 취소)
                    const isLiked = heartBtn.innerText.includes("❤️");

                    try {
                        let result;
                        if (isLiked) {
                            // 좋아요 취소
                            result = await API.delete('/care/api/boards/' + boardIdx + '/hearts');
                        } else {
                            // 좋아요 등록
                            result = await API.post('/care/api/boards/' + boardIdx + '/hearts');
                        }

                        if (result.status === 200 || result.status === 201) {
                            alert(result.message || "처리 성공");
                            await boardDetail(); // 좋아요 상태 반영을 위해 재렌더링
                        } else {
                            alert(result.message || "처리에 실패했습니다.");
                        }
                    } catch (err) {
                        console.error("좋아요 토글 실패", err);
                        alert("서버 오류로 좋아요 처리가 실패했습니다.");
                    }
                }





                async function boardCommentList(cp) {//게시판 댓글 조회

                    const container = document.getElementById("boardCommentContainer");
                    container.innerHTML = "";
                    const idx = location.pathname.split("/").pop();
                    const result = await API.get('/care/api/boards/' + idx + '/comments?cp=' + cp)



                    const boardComments = result.data;
                    const pageInfo = result.pageInfo

                    for (const boardComment of boardComments) {
                        const card = document.createElement("div");
                        card.className = "boardComment";
                        card.dataset.userId = boardComment.userIdx;
                        card.dataset.commentId = boardComment.idx;
                        card.dataset.ref = boardComment.ref;
                        card.dataset.lev = boardComment.lev;
                        card.dataset.turn = boardComment.turn;
                        card.style.marginLeft = boardComment.lev > 0 ? "30px" : "0px";

                        if (!card.dataset.userId) {
                            card.dataset.userId = "0"; // 로그인 유저 검증이 항상 되도록 기본값 지정
                        }

                        // ✅ 조건 강화
                        const isDeleted =
                            !boardComment.nickname &&
                            (!boardComment.content || boardComment.content.trim() === "" || boardComment.content.includes("삭제된 댓글"));

                        card.innerHTML =
                            '<div class="boardComment-nickname">' + (isDeleted ? '' : boardComment.nickname) + '</div>' +
                            '<div class="boardComment-content">' + (isDeleted ? '삭제된 댓글입니다.' : boardComment.content) + '</div>' +
                            '<div class="boardComment-createdAt">' + (isDeleted ? '' : boardComment.createdAt) + '</div>' +
                            (isDeleted ? '' :
                                '<div class="boardComment-buttons">' +
                                '<button class="button-primary" onclick="enterEditMode(this)">수정</button>' +
                                '<button class="button-secondary" onclick="deleteComment(' + boardComment.idx + ')">삭제</button>' +
                                '<button class="button-secondary" onclick="openReplyInput(this)">답글</button>' +
                                '</div>'
                            );

                        container.appendChild(card);
                    }



                    // 페이징 함수 실행
                    makePaging(
                        pageInfo.totalCnt,
                        pageInfo.listSize,
                        pageInfo.pageSize,
                        pageInfo.cp,
                        "pagingAreaComment",
                        boardCommentList
                    );

                }
                function openReplyInput(button) { //대댓글 모드
                    const loginUserId = parseInt(document.body.dataset.loginUserId || "0"); //로그인했을때의 userIdx값

                    if (!loginUserId) {
                        alert("로그인 후 이용해주세요.");
                        location.href = "/care/login";
                        return;
                    }
                    const commentDiv = button.closest(".boardComment");//댓글 정보
                    const replyBox = document.createElement("div");
                    replyBox.className = "replyBox";
                    replyBox.innerHTML =
                        '<textarea class="replyContent" rows="2" cols="50" placeholder="답글을 입력하세요"></textarea>' +
                        '<button onclick="submitBoardReply(this)">등록</button>';
                    commentDiv.appendChild(replyBox);
                    button.disabled = true;
                }

                async function submitBoardReply(button) {//대댓글 등록
                    const loginUserId = parseInt(document.body.dataset.loginUserId || "0"); //로그인했을때의 userIdx값

                    if (!loginUserId) {
                        alert("로그인 후 이용해주세요.");
                        location.href = "/care/login";
                        return;
                    }
                    const replyBox = button.closest(".replyBox");
                    const content = replyBox.querySelector(".replyContent").value.trim();
                    if (!content) {
                        alert("내용을 입력해주세요");
                        return;
                    }
                    const boardIdx = location.pathname.split("/").pop();
                    const boardComment = button.closest(".boardComment");
                    const boardCommentIdx = boardComment.dataset.commentId;
                    const ref = boardComment.dataset.ref;
                    const turn = boardComment.dataset.turn + 1;
                    const body = {
                        userIdx: loginUserId,
                        boardIdx: boardIdx,
                        content: content,
                        ref: ref,
                        turn: turn
                    };

                    const result = await API.post('/care/api/boards/' + boardIdx + '/comments/' + boardCommentIdx + '/replies', body);

                    if (result.status === 200) {
                        alert("답글 등록 성공");
                        boardCommentList(1);
                    } else if (result.errorCode === 401) {
                        location.href = "/care/login";
                    } else if (result.errorCode === 404) {
                        location.reload(true);
                    } else if (result.errorCode === 400) {
                        location.reload(true);
                    } else {
                        location.reload(true);
                    }
                }


                async function submitComment() {//댓글 등록

                    const content = document.getElementById("commentContent").value.trim();
                    if (!content) {
                        alert("댓글 내용을 입력해주세요.");
                        return;
                    }
                    const loginUserId = parseInt(document.body.dataset.loginUserId || "0"); //로그인했을때의 userIdx값

                    if (!loginUserId) {
                        alert("로그인 후 이용해주세요.");
                        location.href = "/care/login";
                        return;
                    }

                    const boardIdx = location.pathname.split("/").pop();
                    const body = {
                        userIdx: loginUserId,
                        boardIdx: boardIdx,
                        content: content
                    };
                    const result = await API.post('/care/api/boards/' + boardIdx + '/comments', body);

                    if (result.status === 200) {
                        alert("댓글 등록 성공");
                        document.getElementById("commentContent").value = "";
                        boardCommentList(1);
                    } else if (result.errorCode === 401) {
                        location.href = "/care/login";
                    } else {
                        alert("댓글 등록 실패");
                    }
                }
                function enterBoardEditMode() {
                    const loginUserId = parseInt(document.body.dataset.loginUserId || "0");
                    const writerUserId = parseInt(document.getElementById("board-nickname").dataset.userId || "0");

                    if (loginUserId !== writerUserId) {
                        alert("본인이 작성한 글만 수정할 수 있습니다.");
                        return;
                    }

                    const titleEl = document.getElementById("board-title");
                    const contentEl = document.getElementById("board-content");
                    const filesEl = document.getElementById("board-files"); // 기존 파일 영역

                    const currentTitle = titleEl.innerText;
                    const currentContent = contentEl.innerText;

                    titleEl.innerHTML = '<input type="text" id="edit-title" value="' + currentTitle + '" style="width: 80%;" />';

                    // ✅ 기존 파일 영역 제거 또는 숨김
                    filesEl.innerHTML = '<span style="color:#999;">(기존 첨부파일은 보존되며, 새 파일로 대체됨)</span>';

                    // ✅ 파일 첨부 input 추가
                    const fileInputHtml = '<input type="file" id="edit-files" name="files" multiple style="margin-top:10px;" />';
                    contentEl.innerHTML =
                        '<textarea id="edit-content" rows="10" style="width: 90%;">' + currentContent + '</textarea>' +
                        fileInputHtml;

                    const btnDiv = document.getElementById("boardActionButtons");
                    btnDiv.innerHTML =
                        '<button class="button-primary" onclick="submitBoardEdit()">저장</button>' +
                        '<button class="button-secondary" onclick="cancelBoardEdit(\'' + currentTitle.replace(/'/g, "\\'") + '\', \'' + currentContent.replace(/'/g, "\\'") + '\')">취소</button>';
                }


                function cancelEdit(button, originalContent) {//수정 취소
                    const commentDiv = button.closest(".boardComment");
                    const contentDiv = commentDiv.querySelector(".boardComment-content");
                    contentDiv.innerText = originalContent;
                    const commentId = commentDiv.dataset.commentId;
                    const buttonBox = commentDiv.querySelector(".boardComment-buttons");
                    buttonBox.innerHTML =
                        '<button class="button-primary" onclick="enterEditMode(this)">수정</button>' +
                        '<button class="button-secondary" onclick="deleteComment(' + commentId + ')">삭제</button>' +
                        '<button class="button-secondary" onclick="openReplyInput(this)">답글</button>';

                }

                async function submitEdit(button, commentIdx) {//수정 등록
                    const commentDiv = button.closest(".boardComment");
                    const textarea = commentDiv.querySelector("textarea.edit-area");
                    const newContent = textarea.value.trim();
                    const boardIdx = location.pathname.split("/").pop();
                    const loginUserId = parseInt(document.body.dataset.loginUserId || "0"); //로그인했을때의 userIdx값
                    if (!newContent) {
                        alert("수정할 내용을 입력하세요.");
                        return;
                    }

                    const body = {
                        idx: commentIdx,
                        userIdx: loginUserId,
                        content: newContent,
                        boardIdx: boardIdx
                    };

                    const result = await API.put('/care/api/boards/' + boardIdx + '/comments/' + commentIdx, body);

                    if (result.status === 200) {
                        const contentDiv = commentDiv.querySelector(".boardComment-content");
                        contentDiv.innerText = newContent;
                        const buttonBox = commentDiv.querySelector(".boardComment-buttons");
                        buttonBox.innerHTML =
                            '<button class="button-primary" onclick="enterEditMode(this)">수정</button>' +
                            '<button class="button-secondary" onclick="deleteComment(' + commentIdx + ')">삭제</button>' +
                            '<button class="button-secondary" onclick="openReplyInput(this)">답글</button>';

                    } else if (result.errorCode === 401) {
                        location.href = "/care/login";
                    } else {
                        location.reload(true);
                    }
                }

                async function deleteComment(commentId) {//댓글 삭제
                    const loginUserId = parseInt(document.body.dataset.loginUserId || "0");
                    if (!loginUserId) {
                        alert("로그인 후 이용해주세요.");
                        location.href = "/care/login";
                        return;
                    }
                    const confirmed = confirm("정말로 이 댓글을 삭제하시겠습니까?");
                    if (!confirmed) return;

                    const boardIdx = location.pathname.split("/").pop();
                    const result = await API.delete('/care/api/boards/' + boardIdx + '/comments/' + commentId);

                    if (result.status === 200) {
                        alert("댓글이 삭제되었습니다.");
                        boardCommentList(1);
                    } else {
                        location.reload(true);
                    }
                }
                function enterBoardEditMode() {
                    const loginUserId = parseInt(document.body.dataset.loginUserId || "0");
                    const writerUserId = parseInt(document.getElementById("board-nickname").dataset.userId || "0");

                    if (loginUserId !== writerUserId) {
                        alert("본인이 작성한 글만 수정할 수 있습니다.");
                        return;
                    }

                    const titleEl = document.getElementById("board-title");
                    const contentEl = document.getElementById("board-content");

                    const currentTitle = titleEl.innerText;
                    const currentContent = contentEl.innerText;

                    titleEl.innerHTML = '<input type="text" id="edit-title" value="' + currentTitle + '" style="width: 80%;" />';

                    // 🔽 파일 첨부 input 추가
                    const fileInputHtml = '<input type="file" id="edit-files" name="files" multiple style="margin-top:10px;" />';
                    contentEl.innerHTML =
                        '<textarea id="edit-content" rows="10" style="width: 90%;">' + currentContent + '</textarea>' +
                        fileInputHtml;

                    const btnDiv = document.getElementById("boardActionButtons");
                    btnDiv.innerHTML =
                        '<button class="button-primary" onclick="submitBoardEdit()">저장</button>' +
                        '<button class="button-secondary" onclick="cancelBoardEdit(\'' + currentTitle.replace(/'/g, "\\'") + '\', \'' + currentContent.replace(/'/g, "\\'") + '\')">취소</button>';
                }


                function cancelBoardEdit(originalTitle, originalContent) {
                    document.getElementById("board-title").innerText = originalTitle;
                    document.getElementById("board-content").innerText = originalContent;

                    const btnDiv = document.getElementById("boardActionButtons");
                    btnDiv.innerHTML =
                        '<button class="button-primary" onclick="enterBoardEditMode()">수정</button>' +
                        '<button class="button-secondary" onclick="deleteBoard()">삭제</button>';

                }

                async function submitBoardEdit() {
                    const loginUserId = parseInt(document.body.dataset.loginUserId || "0");
                    const writerUserId = parseInt(document.getElementById("board-nickname").dataset.userId || "0");

                    if (loginUserId !== writerUserId) {
                        alert("본인이 작성한 글만 수정할 수 있습니다.");
                        return;
                    }

                    const boardIdx = location.pathname.split("/").pop();
                    const newTitle = document.getElementById("edit-title").value.trim();
                    const newContent = document.getElementById("edit-content").value.trim();

                    if (!newTitle || !newContent) {
                        alert("제목과 내용을 모두 입력해주세요.");
                        return;
                    }

                    const body = {
                        idx: parseInt(boardIdx),
                        userIdx: loginUserId,
                        title: newTitle,
                        content: newContent
                    };

                    const result = await API.put('/care/api/boards/' + boardIdx, body);

                    if (result.status === 200) {
                        // 파일 선택되어 있으면 업로드 진행
                        const fileInput = document.getElementById("edit-files");
                        if (fileInput && fileInput.files.length > 0) {
                            const formData = new FormData();
                            for (let i = 0; i < fileInput.files.length; i++) {
                                formData.append("files", fileInput.files[i]);
                            }

                            const uploadResult = await FileAPI.upload('/care/api/boards/upload/' + boardIdx, formData);
                            if (uploadResult.status === 201) {
                                alert("게시글과 파일 수정 완료");
                            } else {
                                alert("게시글은 수정되었지만 파일 업로드에 실패했습니다.");
                            }
                        } else {
                            alert("수정 완료");
                        }

                        boardDetail();
                    } else if (result.errorCode === 401) {
                        location.href = "/care/login";
                    } else {
                        alert("수정 실패");
                    }
                }


                async function deleteBoard() {
                    const loginUserId = parseInt(document.body.dataset.loginUserId || "0");
                    const writerUserId = parseInt(document.getElementById("board-nickname").dataset.userId || "0");

                    if (loginUserId !== writerUserId) {
                        alert("본인이 작성한 글만 삭제할 수 있습니다.");
                        return;
                    }

                    const confirmed = confirm("정말로 게시글을 삭제하시겠습니까?");
                    if (!confirmed) return;

                    const boardIdx = location.pathname.split("/").pop();
                    const result = await API.delete('/care/api/boards/' + boardIdx);

                    if (result.status === 200) {
                        alert("삭제 완료");
                        location.href = "/care/boards";
                    } else {
                        alert("삭제 실패");
                    }
                }
                function enterEditMode(button) {
                    const commentDiv = button.closest(".boardComment");
                    const contentDiv = commentDiv.querySelector(".boardComment-content");
                    const originalContent = contentDiv.innerText;
                    const commentId = parseInt(commentDiv.dataset.commentId);
                    const commentUserId = parseInt(commentDiv.dataset.userId || "0");
                    const loginUserId = parseInt(document.body.dataset.loginUserId || "0");

                    if (!loginUserId) {
                        alert("로그인 후 이용해주세요.");
                        location.href = "/care/login";
                        return;
                    }

                    if (commentUserId !== loginUserId) {
                        alert("본인이 작성한 댓글만 수정할 수 있습니다.");
                        return;
                    }

                    contentDiv.innerHTML = '<textarea class="edit-area" rows="3" style="width:100%;">' + originalContent + '</textarea>';

                    const buttonBox = commentDiv.querySelector(".boardComment-buttons");
                    buttonBox.innerHTML =
                        '<button class="button-primary" onclick="submitEdit(this, ' + commentId + ')">저장</button>' +
                        '<button class="button-secondary" onclick="cancelEdit(this, \'' + originalContent.replace(/'/g, "\\'") + '\')">취소</button>';
                }


            </script>
        </head>

        <body data-login-user-id="<%= loginUserId %>">
            <%@ include file="/WEB-INF/views/common/index/indexHeader.jsp" %>
                <section class="board">
                    <div class="header-title">자유게시판</div>
                    <div class="title-detail">자유롭게 소통해보세요</div>

                    <div class="board-container">
                        <table class="board-content-table">
                            <tr>
                                <th>NO</th>
                                <td id="board-idx"></td>
                                <th>작성일</th>
                                <td id="board-createdAt"></td>
                            </tr>
                            <tr>
                                <th>작성자</th>
                                <td colspan="3" id="board-nickname" data-user-id=""></td>
                            </tr>
                            <tr>
                                <th>조회수</th>
                                <td id="board-views"></td>
                                <th>좋아요</th>
                                <td id="board-likeCount"></td>
                            </tr>
                            <tr>
                                <th>제목</th>
                                <td colspan="3" id="board-title"></td>
                            </tr>
                            <tr>
                                <td colspan="4">
                                    <div id="board-content" class="content-box"></div>
                                </td>
                            </tr>
                            <tr>
                                <th>첨부파일</th>
                                <td colspan="3">
                                    <div id="board-files"></div>
                                </td>
                            </tr>


                        </table>




                        <div class="reply-like-wrapper">
                            <div id="buttonLineWrapper">
                                <div class="button-left">
                                    <button id="reply-btn" onclick="goBoardReply()">답글 달기</button>
                                </div>
                                <div class="button-center" id="boardActionButtons">
                                    <!-- 수정/삭제 버튼이 JS로 삽입됩니다 -->
                                </div>
                                <div class="button-right" id="heartButtonContainer">
                                    <!-- 좋아요 버튼이 JS로 삽입됩니다 -->
                                </div>
                            </div>
                        </div>


                    </div>

                    <!-- 댓글 작성 영역 -->
                    <div class="board-container">
                        <div class="header-title" style="font-size: 20px; margin-bottom: 10px;">댓글 작성</div>
                        <div id="commentWriteBox" style="margin-bottom: 20px;">
                            <textarea id="commentContent" rows="4" cols="50" placeholder="댓글을 입력하세요"
                                style="width: 100%; padding: 10px; border: 1px solid #ccc; border-radius: 4px;"></textarea>
                            <div style="text-align: left; margin-top: 8px;">
                                <button class="button-primary" onclick="submitComment()">등록</button>
                            </div>


                        </div>

                        <!-- 댓글 리스트 -->
                        <div id="boardCommentContainer"></div>

                        <!-- 페이징 -->
                        <div id="pagingAreaComment" class="paging"></div>
                    </div>
                </section>
                <script>
                    window.addEventListener("DOMContentLoaded", function () {
                        boardDetail();
                        boardCommentList(1);
                    });

                </script>
                <%@ include file="/WEB-INF/views/common/index/indexFooter.jsp" %>
        </body>

        </html>