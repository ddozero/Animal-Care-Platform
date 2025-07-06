<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
    <% int loginUserId=0; if (session.getAttribute("loginUser") !=null) {
        loginUserId=((com.animal.api.auth.model.response.LoginResponseDTO) session.getAttribute("loginUser")).getIdx();
        } %>
        <!DOCTYPE html>
        <html>

        <head>
            <meta charset="UTF-8">
            <title>게시글 작성</title>
            <script src="${pageContext.request.contextPath}/resources/web/common/commonUtils.js"></script>
            <style>
                body {
                    font-family: 'Pretendard', sans-serif;
                    background-color: #fff;
                    padding: 40px 20px;
                }

                .header-title {
                    font-size: 28px;
                    font-weight: bold;
                    margin-top: 50px;
                    margin-bottom: 20px;
                    text-align: center;
                    color: #3ACDB2;
                }

                .form-container {
                    max-width: 800px;
                    margin: 0 auto;
                    background-color: #f9f9f9;
                    padding: 30px;
                    border-radius: 10px;
                    box-shadow: 0 0 10px rgba(0, 0, 0, 0.05);
                }

                .form-container label {
                    font-weight: bold;
                    display: block;
                    margin-top: 15px;
                    margin-bottom: 5px;
                }

                .form-container input[type="text"],
                .form-container textarea,
                .form-container input[type="file"] {
                    width: 100%;
                    padding: 10px;
                    border: 1px solid #ccc;
                    border-radius: 6px;
                    font-size: 14px;
                    box-sizing: border-box;
                }

                .form-container textarea {
                    resize: vertical;
                }

                .form-container button {
                    background-color: #3ACDB2;
                    color: white;
                    border: none;
                    padding: 10px 18px;
                    font-size: 14px;
                    border-radius: 6px;
                    cursor: pointer;
                    margin-right: 10px;
                    transition: background 0.2s;
                }

                .form-container button:hover {
                    background-color: #2fb3a0;
                }
            </style>

            <script>
                // boardForm.jsp에 추가
                async function submitBoard() {
                    const loginUserId = parseInt(document.body.dataset.loginUserId || "0");
                    if (!loginUserId) {
                        alert("로그인 후 작성 가능합니다.");
                        location.href = "/care/login";
                        return;
                    }

                    const urlParams = new URLSearchParams(window.location.search);
                    const originIdx = urlParams.get("origin");  // 원본글 idx

                    const jsonData = {
                        userIdx: loginUserId,
                        title: document.getElementById("title").value.trim(),
                        content: document.getElementById("content").value.trim()
                    };

                    if (!jsonData.title || !jsonData.content) {
                        alert("제목과 내용을 모두 입력해주세요.");
                        return;
                    }

                    let writeResult;
                    if (originIdx) {
                        // 답글 등록
                        writeResult = await API.post("/care/api/boards/" + originIdx + "/reply", jsonData);
                    } else {
                        // 일반 등록
                        writeResult = await API.post("/care/api/boards", jsonData);
                    }

                    if (writeResult && writeResult.status === 201) {
                        const createdIdx = writeResult.data.createdIdx;
                        const files = document.getElementById("files").files;

                        if (files.length > 0) {
                            const formData = new FormData();
                            for (let file of files) {
                                formData.append("files", file);
                            }
                            const uploadResult = await FileAPI.upload("/care/api/boards/upload/" + createdIdx, formData);
                            if (uploadResult.status !== 201) {
                                alert("파일 업로드 실패 (본문은 저장됨)");
                            }
                        }

                        alert("글이 성공적으로 등록되었습니다.");
                        location.href = "/care/boards";
                    } else {
                        alert(writeResult.message || "글 등록 실패");
                    }
                }

            </script>
        </head>

        <body data-login-user-id="<%= loginUserId %>">
            <%@ include file="/WEB-INF/views/common/index/indexHeader.jsp" %>

                <div class="header-title">게시글 작성</div>

                <div class="form-container">
                    <form id="boardForm" enctype="multipart/form-data">
                        <div>
                            <label for="title">제목</label>
                            <input type="text" id="title" required />
                        </div>
                        <div>
                            <label for="content">내용</label>
                            <textarea id="content" rows="10" required></textarea>
                        </div>
                        <div>
                            <label for="files">첨부파일</label>
                            <input type="file" id="files" multiple />
                        </div>
                        <div style="margin-top: 20px;">
                            <button type="button" onclick="submitBoard()">등록</button>
                            <button type="reset">초기화</button>
                        </div>
                    </form>
                </div>
                <script>
                    window.addEventListener("DOMContentLoaded", () => {
                        const urlParams = new URLSearchParams(window.location.search);
                        const originIdx = urlParams.get("origin");
                        if (originIdx) {
                            document.getElementById("title").value = "[Re] ";
                        }
                    });
                </script>


                <%@ include file="/WEB-INF/views/common/index/indexFooter.jsp" %>
        </body>

        </html>