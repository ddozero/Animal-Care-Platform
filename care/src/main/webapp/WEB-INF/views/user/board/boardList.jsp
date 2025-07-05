<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
    <% int loginUserId=0; if (session.getAttribute("loginUser") !=null) {
        loginUserId=((com.animal.api.auth.model.response.LoginResponseDTO) session.getAttribute("loginUser")).getIdx();
        } %>
        <!DOCTYPE html>
        <html>

        <head>
            <meta charset="UTF-8">
            <title>커뮤니티</title>
            <script src="${pageContext.request.contextPath}/resources/web/common/commonUtils.js"></script>
            <script>
                async function boardList(cp, searchKey = '', searchWord = '') {
                    const container = document.getElementById("boardListContainer");
                    container.innerHTML = "";

                    // 검색 파라미터 추가
                    let url = '/care/api/boards?cp=' + cp;
                    if (searchKey && searchWord) {
                        url += '&searchKey=' + encodeURIComponent(searchKey) + '&searchWord=' + encodeURIComponent(searchWord);
                    }

                    const result = await API.get(url);
                    if (result.status !== 200) {
                        history.back();
                        return;
                    }

                    const boards = result.data;
                    const pageInfo = result.pageInfo;

                    for (const board of boards) {
                        const card = document.createElement("div");
                        card.innerHTML =
                            '<div class="board-idx">' + board.idx + '</div>' +
                            '<a href="${pageContext.request.contextPath}/boards/' + board.idx + '">' +
                            '<div class="board-title">' + board.title + '</div>' +
                            '</a>' +
                            '<div class="donation-createdAt">' + board.createdAt + '</div>' +
                            '<div class="donation-nickname">' + board.nickname + '</div>' +
                            '<div class="donation-views">' + board.views + '</div>' +
                            '<div class="donation-likeCount">' + board.likeCount + '</div>';
                        container.appendChild(card);
                    }

                    // 페이징도 검색 조건 유지
                    makePaging(
                        pageInfo.totalCnt,
                        pageInfo.listSize,
                        pageInfo.pageSize,
                        pageInfo.cp,
                        "pagingArea",
                        function (page) {
                            boardList(page, searchKey, searchWord);
                        }
                    );
                }

                function searchBoardList() {
                    const searchKey = document.getElementById("searchKey").value;
                    const searchWord = document.getElementById("searchWord").value.trim();

                    if (!searchWord) {
                        alert("검색어를 입력해주세요.");
                        return;
                    }

                    boardList(1, searchKey, searchWord);
                }


                function goToBoardForm() {
                    const loginUserId = parseInt(document.body.dataset.loginUserId || "0");
                    if (!loginUserId) {
                        alert("로그인 후 이용해주세요.");
                        location.href = "/care/login";
                        return;
                    }
                    location.href = "${pageContext.request.contextPath}/boards/form";
                }
            </script>
        </head>

        <body data-login-user-id="<%= loginUserId %>">
            <%@ include file="/WEB-INF/views/common/index/indexHeader.jsp" %>
                <!-- 🔍 검색 영역 -->

                <div style="margin-bottom: 10px;">
                    <select id="searchKey">
                        <option value="all">전체</option>
                        <option value="title">제목</option>
                        <option value="content">내용</option>
                        <option value="nickname">작성자</option>
                    </select>
                    <input type="text" id="searchWord" placeholder="검색어 입력" />
                    <button onclick="searchBoardList()">검색</button>
                </div>


                <div id="boardListContainer"></div>
                <div id="pagingArea" class="paging"></div>
                <!-- 글쓰기 버튼은 항상 보이되, 로그인 상태에 따라 동작 제어 -->
                <div style="text-align: right; margin: 10px;">
                    <button onclick="goToBoardForm()">글쓰기</button>
                </div>

                <script>
                    window.addEventListener("DOMContentLoaded", function () {
                        boardList(1);
                    });

                </script>


                <%@ include file="/WEB-INF/views/common/index/indexFooter.jsp" %>

        </body>

        </html>