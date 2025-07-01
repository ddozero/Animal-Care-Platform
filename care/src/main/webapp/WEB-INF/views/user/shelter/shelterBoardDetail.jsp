<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
    <!DOCTYPE html>
    <html>

    <head>
        <meta charset="UTF-8">
        <title>Insert title here</title>
        <link rel="stylesheet" href="https://cdn.jsdelivr.net/gh/orioncactus/pretendard/dist/web/static/pretendard.css">

        <style>
            :root {
                --brand: #3ACDB2;
                --gray-1: #f9f9f9;
                --gray-2: #DBDBDB;
                --text-main: #333;
            }

            * {
                box-sizing: border-box;
            }

            body {
                margin: 0;
                font-family: 'Pretendard', sans-serif;
                background: #fff;
                color: var(--text-main);
                line-height: 1.55;
            }

            /* ───────── 메인 래퍼 ───────── */
            .board-detail {
                max-width: 960px;
                margin: 60px auto;
                padding: 40px 32px 60px;
                border: 1px solid var(--gray-2);
                border-radius: 8px;
            }

            /* ───────── 헤더 ───────── */
            .board-header h2 {
                font-size: 26px;
                margin: 0 0 20px 0;
                word-break: break-all;
            }

            .board-header div {
                font-size: 15px;
                margin-bottom: 6px;
            }

            .board-header span:first-child {
                font-weight: 600;
            }

            hr {
                border: none;
                border-top: 1px solid var(--gray-2);
                margin: 24px 0;
            }

            /* ───────── 본문 ───────── */
            #boardContent {
                font-size: 16px;
                white-space: pre-line;
                margin-bottom: 32px;
            }

            /* ───────── 이미지 ───────── */
            #imageContainer {
                display: flex;
                flex-wrap: wrap;
                gap: 12px;
                margin-bottom: 32px;
            }

            #imageContainer img {
                width: 200px;
                aspect-ratio: 4/3;
                object-fit: cover;
                border-radius: 6px;
                border: 1px solid var(--gray-2);
            }

            /* ───────── 파일 리스트 ───────── */
            .board-files {
                list-style: none;
                padding: 0;
                margin: 0 0 32px 0;
                font-size: 15px;
            }

            .board-files li {
                margin-bottom: 6px;
            }

            .board-files a {
                text-decoration: none;
                color: var(--brand);
            }

            /* ───────── 버튼 ───────── */
            .btn-area {
                text-align: right;
            }

            .btn-area .btn {
                display: inline-block;
                padding: 10px 22px;
                font-size: 14px;
                background: var(--brand);
                color: #fff;
                border: none;
                border-radius: 4px;
                cursor: pointer;
                transition: background .25s;
            }

            .btn-area .btn:hover {
                background: #34b3a1;
            }

            /* ───────── 반응형 ───────── */
            @media (max-width: 600px) {
                .board-detail {
                    padding: 28px 20px 50px;
                }

                #imageContainer img {
                    width: 100%;
                }

                .btn-area {
                    text-align: center;
                }
            }
        </style>
        <script src="${pageContext.request.contextPath}/resources/web/common/commonUtils.js"></script>
        <script>
            const path = location.pathname.split("/");
            const shelterIdx = path[3];
            const boardIdx = path[path.length - 1];

            async function shelterBoard() {
                const result = await API.get('/care/api/shelters/' + shelterIdx + '/boards/' + boardIdx);
                if (result.status != 200) {
                    location.href = '/care/shelters/' + shelterIdx;
                    return;
                }
                document.getElementById("body").style.display = "block";
                const board = result.data;

                document.getElementById("boardTitle").textContent = board.title;
                document.getElementById("shelterName").textContent = board.shelterName;
                document.getElementById("createdAt").textContent = board.createdAt;
                document.getElementById("views").textContent = board.views;
                document.getElementById("boardContent").textContent = board.content;

                if (board.imagePaths != null) {
                    const images = document.getElementById("imageContainer");
                    for (const imagePath of board.imagePaths) {
                        if (imagePath === null) {
                            continue;
                        }
                        const img = document.createElement("img");
                        img.src = "${pageContext.request.contextPath}" + imagePath;
                        img.width = "200";
                        images.appendChild(img);
                    }
                }
                if (board.filePaths != null) {
                    const fileList = document.getElementById("fileList");
                    const fileText = document.createElement("div");
                    fileText.textContent = "첨부파일";
                    fileList.appendChild(fileText);
                    for (const filePath of board.filePaths) {
                        const li = document.createElement("li");
                        const file = document.createElement("a");
                        file.href = "${pageContext.request.contextPath}" + filePath;
                        file.textContent = filePath.substring(filePath.lastIndexOf("/") + 1);
                        li.appendChild(file);
                        fileList.appendChild(li);
                    }
                }

            }
        </script>
    </head>

    <body id="body" style="display: none;">
        <%@ include file="/WEB-INF/views/common/index/indexHeader.jsp" %>

            <div class="board-detail">
                <div class="board-header">
                    <h2 id="boardTitle">게시글 제목</h2>
                    <div><span>작성자&nbsp;|&nbsp;</span><span id="shelterName"></span></div>
                    <div><span>작성일&nbsp;|&nbsp;</span><span id="createdAt"></span></div>
                    <div><span>조회수&nbsp;|&nbsp;</span><span id="views"></span></div>
                </div>

                <hr>

                <div id="boardContent"></div>

                <div id="imageContainer"></div>

                <ul class="board-files" id="fileList"></ul>

                <div class="btn-area">
                    <button class="btn" onclick="location.href='/care/shelters/' + shelterIdx">목록</button>
                </div>
            </div>
            <%@ include file="/WEB-INF/views/common/index/indexFooter.jsp" %>
                <script>
                    window.addEventListener("DOMContentLoaded", function () {
                        shelterBoard();
                    });
                </script>
    </body>

    </html>