<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
    <!DOCTYPE html>
    <html>

    <head>
        <meta charset="UTF-8">
        <title>Insert title here</title>
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
                        if(imagePath===null){
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
                    <div><span>작성자: </span><span id="shelterName"></span></div>
                    <div><span>작성일: </span><span id="createdAt"></span></div>
                    <div><span>조회수: </span><span id="views"></span></div>
                </div>
                <hr>
                <div class="board-content" id="boardContent" style="white-space: pre-wrap;">
                </div>
                <div class="board-images" id="imageContainer">
                </div>
                <ul class="board-files" id="fileList">
                </ul>
            </div>
            <input type="button" value="목록" onclick="location.href='/care/shelters/'+shelterIdx">
            <script>
                window.addEventListener("DOMContentLoaded", function () {
                    shelterBoard();
                });
            </script>
    </body>

    </html>