<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
    <% request.setAttribute("currentPage", "notices" ); %>
        <!DOCTYPE html>
        <html>

        <head>
            <meta charset="UTF-8">
            <title>공지사항 관리</title>
            <link rel="stylesheet"
                href="https://cdn.jsdelivr.net/gh/orioncactus/pretendard/dist/web/static/pretendard.css">
            <style>
                .board {
                    font-family: 'Pretendard', sans-serif;
                    padding: 0px 0px 40px;
                }

                .header-title {
                    font-size: 28px;
                    font-weight: bold;
                    margin-bottom: 40px;
                    text-align: left;
                }

                .board-container {
                    max-width: 1200px;
                    margin: 0 auto;
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

                #noticeContentBox {
                    border-radius: 4px;
                    min-height: 400px;
                    padding: 20px;
                    font-size: 16px;
                    line-height: 1.6;
                    white-space: pre-line;
                    color: #444;
                }

                #editForm {
                    display: none;
                }

                #editNoticeTitle input[type="text"],
                #editNoticeContent textarea {
                    width: 100%;
                    padding: 8px;
                    font-size: 16px;
                    border: 1px solid #ccc;
                    border-radius: 4px;
                    box-sizing: border-box;
                }

                #editNoticeContent textarea {
                    min-height: 400px;
                    resize: vertical;
                    line-height: 1.6;
                    font-family: 'Pretendard', sans-serif;
                }

                .btn-area {
                    text-align: center;
                }

                .btn-area input[type="button"] {
                    background-color: #3ACDB2;
                    color: #fff;
                    border: none;
                    padding: 10px 20px;
                    margin: 0 5px;
                    border-radius: 4px;
                    font-size: 14px;
                    cursor: pointer;
                    width: 90px;
                    transition: background-color 0.3s;
                }

                .btn-area input[type="button"]:hover {
                    background-color: #34b3a1;
                }

                @media (max-width : 600px) {

                    .board-content-table th,
                    .board-content-table td {
                        font-size: 14px;
                        padding: 10px;
                    }

                    .btn-area {
                        margin-top: 20px;
                    }

                    .btn-area input[type="button"] {
                        width: calc(33% - 10px);
                    }
                }
            </style>
        </head>

        <body>
            <%@ include file="/WEB-INF/views/common/admin/adminHeader.jsp" %>
                <section class="board">
                    <div class="header-title">공지사항 관리</div>

                    <div id="viewContent">
                        <div class="board-container">
                            <table class="board-content-table">
                                <tr>
                                    <th>NO</th>
                                    <td id="noticeIdx"></td>
                                    <th>작성일</th>
                                    <td id="noticeDate"></td>
                                </tr>
                                <tr>
                                    <th>작성자</th>
                                    <td id="noticeWriter">댕봉사</td>
                                    <th>조회수</th>
                                    <td id="noticeViews"></td>
                                </tr>
                                <tr>
                                    <th>제목</th>
                                    <td id="noticeTitle" colspan="3"></td>
                                </tr>
                                <tr>
                                    <td colspan="4">
                                        <div class="content-box" id="noticeContentBox"></div>
                                    </td>
                                </tr>
                            </table>
                        </div>
                        <br>
                        <div class="btn-area">
                            <input type="button" value="목록으로" id="goListNotice">
                            <input type="button" value="수정" onclick="noticeUpdate()">
                            <input type="button" value="삭제" onclick="noticeDelete()">
                        </div>
                    </div>

                    <form id="editForm">
                        <div class="board-container">
                            <table class="board-content-table">
                                <tr>
                                    <th>NO</th>
                                    <td id="editNoticeIdx"></td>
                                    <th>작성일</th>
                                    <td id="editNoticeDate"></td>
                                </tr>
                                <tr>
                                    <th>작성자</th>
                                    <td>댕봉사</td>
                                    <th>조회수</th>
                                    <td id="editNoticeViews"></td>
                                </tr>
                                <tr>
                                    <th>제목</th>
                                    <td id="editNoticeTitle" colspan="3">
                                        <input type="text" name="title" required>
                                    </td>
                                </tr>
                                <tr>
                                    <td id="editNoticeContent" colspan="4">
                                        <textarea name="content" required></textarea>
                                    </td>
                                </tr>
                            </table>
                        </div>
                        <br>
                        <div class="btn-area">
                            <input type="button" value="목록으로" id="goListNoticeFromEdit">
                            <input type="button" value="수정완료" onclick="submitUpdate()">
                            <input type="button" value="취소" onclick="cancelUpdate()">
                        </div>
                    </form>

                </section>

                <script src="${pageContext.request.contextPath}/resources/web/common/commonUtils.js"></script>
                <script>
                    const pathParts = window.location.pathname.split('/');
                    const idx = pathParts[pathParts.length - 1];

                    const viewContentDiv = document.getElementById('viewContent');
                    const editForm = document.getElementById('editForm');

                    const editNoticeIdx = document.getElementById('editNoticeIdx');
                    const editNoticeDate = document.getElementById('editNoticeDate');
                    const editNoticeViews = document.getElementById('editNoticeViews');
                    const editTitleInput = editForm.querySelector('input[name="title"]');
                    const editContentTextarea = editForm.querySelector('textarea[name="content"]');

                    async function noticeDetail() {
                        if (!idx) {
                            alert("잘못된 접근입니다.");
                            window.location.href = "/care/admin/notices";
                            return;
                        }

                        const result = await API.get("/care/api/admin/boards/notices/" + idx);

                        if (result.errorCode === 401 || result.errorCode === 403) {
                            location.href = "/care/admin/login";
                            return;
                        }

                        if (result.status !== 200) {
                            alert("게시물을 불러올 수 없습니다.");
                            window.location.href = "/care/admin/notices";
                            return;
                        }

                        const notice = result.data;

                        document.getElementById("noticeIdx").innerHTML = notice.idx;
                        document.getElementById("noticeTitle").innerHTML = notice.title;
                        document.getElementById("noticeDate").innerHTML = notice.createdAt;
                        document.getElementById("noticeWriter").innerHTML = "댕봉사";
                        document.getElementById("noticeViews").innerHTML = notice.views;
                        document.getElementById("noticeContentBox").innerHTML = notice.content;
                    }

                    function noticeUpdate() {
                        editNoticeIdx.innerHTML = document.getElementById("noticeIdx").innerHTML;
                        editNoticeDate.innerHTML = document.getElementById("noticeDate").innerHTML;
                        editNoticeViews.innerHTML = document.getElementById("noticeViews").innerHTML;
                        editTitleInput.value = document.getElementById("noticeTitle").innerHTML;
                        editContentTextarea.value = document.getElementById("noticeContentBox").innerText;

                        viewContentDiv.style.display = 'none';
                        editForm.style.display = 'block';
                    }

                    function cancelUpdate() {
                        if (confirm("수정을 취소하시겠습니까? 변경사항이 저장되지 않습니다.")) {
                            viewContentDiv.style.display = 'block';
                            editForm.style.display = 'none';
                        }
                    }

                    async function submitUpdate() {
                        const updatedData = {
                            title: editTitleInput.value,
                            content: editContentTextarea.value
                        };

                        if (!updatedData.title || !updatedData.content) {
                            alert("제목과 내용을 모두 입력해주세요.");
                            return;
                        }

                        if (!confirm("이대로 수정하시겠습니까?")) {
                            return;
                        }

                        const result = await API.put("/care/api/admin/boards/notices/" + idx, updatedData);
                        if (result.status === 200) {
                            alert("수정이 완료되었습니다.");
                            window.location.reload();
                        } else {
                            alert(result.message || "수정에 실패했습니다.");
                        }
                    }

                    async function noticeDelete() {
                        if (!confirm("정말 삭제하시겠습니까?")) {
                            return;
                        }
                        const result = await API.delete("/care/api/admin/boards/notices/" + idx);
                        if (result.status === 200) {
                            alert(result.message);
                            location.href = "/care/admin/notices";
                        } else {
                            alert(result.message || "삭제에 실패했습니다.");
                        }
                    }

                    noticeDetail();

                    document.getElementById("goListNotice").addEventListener("click", function () {
                        window.location.href = "/care/admin/notices";
                    });
                    document.getElementById("goListNoticeFromEdit").addEventListener("click", function () {
                        window.location.href = "/care/admin/notices";
                    });
                </script>
                <%@ include file="/WEB-INF/views/common/admin/adminSideBar.jsp" %>
        </body>

        </html>