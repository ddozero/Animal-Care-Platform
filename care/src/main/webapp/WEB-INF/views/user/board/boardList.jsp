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
            <style>
                /* Pretendard 폰트 적용 */
                body {
                    font-family: 'Pretendard', sans-serif;
                    background-color: #fff;
                    padding: 40px 20px;
                }

                /* 제목 및 설명 */
                .header-title {
                    font-size: 28px;
                    font-weight: bold;
                    margin-top: 50px;
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

                /* 게시글 테이블 스타일 */
                .board-container {
                    max-width: 1200px;
                    /* ✅ 공지사항과 동일하게 설정 */
                    margin: 0 auto;
                }

                #boardListContainer>div {
                    display: grid;
                    grid-template-columns: 60px 1fr 140px 100px 60px 60px;
                    align-items: center;
                    border-bottom: 1px solid #eee;
                    padding: 14px 10px;
                    font-size: 15px;
                    color: #333;
                    transition: background-color 0.2s;
                }

                #boardListContainer>div:hover {
                    background-color: #f9f9f9;
                }

                #boardListContainer .board-title {
                    font-weight: bold;
                    color: #333;
                    overflow: hidden;
                    text-overflow: ellipsis;
                    white-space: nowrap;
                }

                #boardListContainer a {
                    text-decoration: none;
                    color: inherit;
                }

                #boardListContainer .board-idx,
                #boardListContainer .donation-createdAt,
                #boardListContainer .donation-nickname,
                #boardListContainer .donation-views,
                #boardListContainer .donation-likeCount {
                    text-align: center;
                    font-size: 14px;
                }

                /* 검색 영역 스타일 */
                #searchKey,
                #searchWord,
                #searchWord+button {
                    font-size: 14px;
                    padding: 8px 12px;
                    border-radius: 4px;
                    border: 1px solid #ccc;
                    outline: none;
                    box-sizing: border-box;
                }

                #searchKey {
                    width: 80px;
                    background: white;
                }

                #searchWord {
                    width: 160px;
                    margin-left: 4px;
                    margin-right: 4px;
                }

                #searchWord+button {
                    background-color: #d1d1d1;
                    border: none;
                    cursor: pointer;
                    font-weight: bold;
                    transition: background-color 0.3s;
                }

                #searchWord+button:hover {
                    background-color: #aaa;
                }

                /* 페이징 스타일 */
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

                /* 글쓰기 버튼 */
                button[onclick*="goToBoardForm"] {
                    background-color: #3ACDB2;
                    color: white;
                    border: none;
                    padding: 8px 16px;
                    font-size: 14px;
                    border-radius: 6px;
                    cursor: pointer;
                    transition: background 0.2s;
                }

                button[onclick*="goToBoardForm"]:hover {
                    background-color: #2fb3a0;
                }

                .board-table {
                    width: 100%;
                    min-width: 700px;
                    border-collapse: collapse;
                    font-size: 15px;
                }

                .board-table th,
                .board-table td {
                    padding: 14px 10px;
                    border-bottom: 1px solid #eee;
                    text-align: center;
                    color: #333;
                }

                .board-table td:nth-child(2) {
                    text-align: left;
                }

                .board-table thead {
                    font-weight: bold;
                    border-bottom: 2px solid #DBDBDB;
                }

                .board-table tbody tr:hover {
                    background-color: #f9f9f9;
                }

                .board-table a {
                    color: #333;
                    text-decoration: none;
                }

                #searchForm {
                    max-width: 1200px;
                    margin: 0 auto;
                    padding-bottom: 20px;
                    box-sizing: border-box;
                }
            </style>

            <script>
                async function boardList(cp, searchKey = '', searchWord = '') {
                    const container = document.getElementById("boardListContainer");
                    container.innerHTML = "";

                    // 검색 파라미터 추가
                    let url = '/care/api/boards?cp=' + cp;
                    if (searchKey && searchWord) {
                        url += '&type=' + encodeURIComponent(searchKey) + '&keyword=' + encodeURIComponent(searchWord);

                    }

                    const originalAlert = window.alert;
                    window.alert = function () { };

                    let result;
                    try {
                        result = await API.get(url);
                    } catch (err) {
                        console.warn("게시글 조회 실패:", err.message);
                    }

                    window.alert = originalAlert;

                    if (!result || !result.data || result.data.length === 0) {
                        container.innerHTML = "<tr><td colspan='6' style='text-align:center; color:#999;'>게시글이 존재하지 않습니다.</td></tr>";
                        document.getElementById("pagingArea").innerHTML = "";
                        return;
                    }

                    const boards = result.data;
                    const pageInfo = result.pageInfo;

                    for (const board of boards) {
                        const card = document.createElement("tr");
                        const indent = board.lev > 0 ? '&nbsp;&nbsp;&nbsp;└ ' : '';
                        card.innerHTML =
                            '<td>' + board.idx + '</td>' +
                            '<td><a href="${pageContext.request.contextPath}/boards/' + board.idx + '">' + indent + board.title + '</a></td>' +
                            '<td>' + board.createdAt + '</td>' +
                            '<td>' + board.nickname + '</td>' +
                            '<td>' + board.views + '</td>' +
                            '<td>' + board.likeCount + '</td>';
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
                        boardList(1);  // 검색어 없으면 전체 목록 보여주기
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
                <div class="board-container">
                    <div class="header-title">커뮤니티</div>
                    <div class="title-detail">자유롭게 소통해보세요</div>

                    <!-- 검색 영역 -->
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

                    <!-- 게시판 테이블 구조 -->
                    <table class="board-table">
                        <thead>
                            <tr>
                                <th>NO</th>
                                <th>제목</th>
                                <th>작성일</th>
                                <th>작성자</th>
                                <th>조회수</th>
                                <th>좋아요</th>
                            </tr>
                        </thead>
                        <tbody id="boardListContainer">
                            <!-- JavaScript에서 tr 요소로 채워짐 -->
                        </tbody>
                    </table>

                    <div id="pagingArea" class="paging"></div>

                    <!-- 글쓰기 버튼 -->
                    <div style="text-align: right; margin: 10px;">
                        <button onclick="goToBoardForm()">글쓰기</button>
                    </div>
                </div>


                <script>
                    window.addEventListener("DOMContentLoaded", function () {
                        boardList(1);
                    });

                </script>


                <%@ include file="/WEB-INF/views/common/index/indexFooter.jsp" %>

        </body>

        </html>