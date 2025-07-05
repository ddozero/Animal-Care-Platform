<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
    <% int loginUserId=0; if (session.getAttribute("loginUser") !=null) {
        loginUserId=((com.animal.api.auth.model.response.LoginResponseDTO) session.getAttribute("loginUser")).getIdx();
        } %>
        <!DOCTYPE html>
        <html>

        <head>
            <meta charset="UTF-8">
            <title>자유게시판 상세</title>
            <script src="${pageContext.request.contextPath}/resources/web/common/commonUtils.js"></script>
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
                    const btnDiv = document.getElementById("boardActionButtons");
                    btnDiv.innerHTML =
                        '<button onclick="enterBoardEditMode()">수정</button>' +
                        '<button onclick="deleteBoard()">삭제</button>';

                    if (loginUserId > 0 && board.heart !== undefined) {
                        const heartButton = document.createElement("button");
                        heartButton.id = "heart-btn";
                        heartButton.innerText = board.heart ? "❤️ 좋아요 취소" : "🤍 좋아요";
                        heartButton.onclick = toggleHeart;
                        btnDiv.appendChild(heartButton);
                    }

                    if (loginUserId > 0) {
                        document.getElementById("replyArea").style.display = "block";
                    } else {
                        document.getElementById("replyArea").style.display = "none";
                    }

                }
                function goBoardReply() {
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
                        card.className = "boardComment";//카드 클래스이름 지정
                        card.dataset.userId = boardComment.userIdx;//댓글 전체 조회시 userIdx값 변수에 넣는 과정
                        card.dataset.commentId = boardComment.idx;//댓글 전체 조회시 댓글 idx값 변수에 넣는 과정
                        card.dataset.ref = boardComment.ref;
                        card.dataset.lev = boardComment.lev;
                        card.dataset.turn = boardComment.turn;
                        card.innerHTML =
                            '<div class="boardComment-nickname">' + boardComment.nickname + '</div>' +
                            '<div class="boardComment-content">' + boardComment.content + '</div>' +
                            '<div class="boardComment-createdAt">' + boardComment.createdAt + '</div>' +
                            '<div class="boardComment-buttons">' +
                            '<button onclick="enterEditMode(this)">수정</button>' +
                            '<button onclick="deleteComment(' + boardComment.idx + ')">삭제</button>' +
                            '<button onclick="openReplyInput(this)">답글</button>' +
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
                function enterEditMode(button) {//댓글 수정 모드
                    const commentDiv = button.closest(".boardComment");
                    const contentDiv = commentDiv.querySelector(".boardComment-content");
                    const originalContent = contentDiv.innerText;
                    const commentId = parseInt(commentDiv.dataset.commentId);
                    const commentUserId = parseInt(commentDiv.dataset.userId);
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

                    contentDiv.innerHTML = '<textarea class="edit-area" rows="3">' + originalContent + '</textarea>';
                    const buttonBox = commentDiv.querySelector(".boardComment-buttons");
                    buttonBox.innerHTML =
                        '<button onclick="submitEdit(this,' + commentId + ')">저장</button>' +
                        '<button onclick="cancelEdit(this, \'' + originalContent.replace(/'/g, "\\'") + '\')">취소</button>';
                }

                function cancelEdit(button, originalContent) {//수정 취소
                    const commentDiv = button.closest(".boardComment");
                    const contentDiv = commentDiv.querySelector(".boardComment-content");
                    contentDiv.innerText = originalContent;
                    const commentId = commentDiv.dataset.commentId;
                    const buttonBox = commentDiv.querySelector(".boardComment-buttons");
                    buttonBox.innerHTML =
                        '<button onclick="enterEditMode(this)">수정</button>' +
                        '<button onclick="deleteComment(' + commentId + ')">삭제</button>' +
                        '<button onclick="openReplyInput(this)">답글</button>';
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
                            '<button onclick="enterEditMode(this)">수정</button>' +
                            '<button onclick="deleteComment(' + commentIdx + ')">삭제</button>' +
                            '<button onclick="openReplyInput(this)">답글</button>';
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
                        '<button onclick="submitBoardEdit()">저장</button>' +
                        '<button onclick="cancelBoardEdit(\'' + currentTitle.replace(/'/g, "\\'") + '\', \'' + currentContent.replace(/'/g, "\\'") + '\')">취소</button>';
                }


                function cancelBoardEdit(originalTitle, originalContent) {
                    document.getElementById("board-title").innerText = originalTitle;
                    document.getElementById("board-content").innerText = originalContent;

                    const btnDiv = document.getElementById("boardActionButtons");
                    btnDiv.innerHTML =
                        '<button onclick="enterBoardEditMode()">수정</button>' +
                        '<button onclick="deleteBoard()">삭제</button>';
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


            </script>
        </head>

        <body data-login-user-id="<%= loginUserId %>">
            <%@ include file="/WEB-INF/views/common/index/indexHeader.jsp" %>
                <h2>자유게시판 상세</h2>
                <div>게시글 번호: <span id="board-idx"></span></div>
                <div>작성일자: <span id="board-createdAt"></span></div>
                <div>작성자: <span id="board-nickname"></span></div>
                <div>조회수: <span id="board-views"></span></div>
                <div>좋아요 수: <span id="board-likeCount"></span></div>
                <div>제목: <span id="board-title"></span></div>
                <div>내용: <span id="board-content"></span></div>
                <div>첨부파일:
                    <div id="board-files"></div>
                </div>
                <div id="boardActionButtons" style="margin-top: 20px;">
                    <button onclick="enterBoardEditMode()">수정</button>
                    <button onclick="deleteBoard()">삭제</button>
                </div>

                <div id="replyArea" style="margin-top: 10px;">
                    <button id="reply-btn" onclick="goBoardReply()">답글 달기</button>
                </div>


                <script>
                    window.addEventListener("DOMContentLoaded", function () {
                        boardDetail();
                        boardCommentList(1);
                    });

                </script>
                <hr>
                <!-- 댓글 작성 영역 -->
                <div id="commentWriteBox">
                    <textarea id="commentContent" placeholder="댓글을 입력하세요" rows="4" cols="50"></textarea><br />
                    <button onclick="submitComment()">등록</button>
                </div>
                <hr>
                <div id="boardCommentContainer"></div>
                <div id="pagingAreaComment" class="paging"></div>

                <%@ include file="/WEB-INF/views/common/index/indexFooter.jsp" %>
        </body>

        </html>