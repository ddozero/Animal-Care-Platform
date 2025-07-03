<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
    <% request.setAttribute("currentPage", "notice_form" ); %>
        <!DOCTYPE html>
        <html>

        <head>
            <meta charset="UTF-8">
            <title>Insert title here</title>
        </head>
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

            #addNoticeTitle input[type="text"],
            #addNoticeContent textarea {
                width: 100%;
                padding: 8px;
                font-size: 16px;
                border: 1px solid #ccc;
                border-radius: 4px;
                box-sizing: border-box;
            }

            #addNoticeContent textarea {
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
        <script src="${pageContext.request.contextPath}/resources/web/common/commonUtils.js"></script>
        <script>
            async function addNotice() {
                const form = getJsonFromForm("addForm");
                const result = await API.post('/care/api/admin/boards/notices', form);

                if (result.errorCode === 401 || result.errorCode === 403) {
                    location.href = "/care/admin/login";
                    return;
                }

                if (result.status === 201) {
                    alert(result.message);
                    location.href = '/care/admin/notices';
                    return;
                }
            }
        </script>

        <body>
            <%@ include file="/WEB-INF/views/common/admin/adminHeader.jsp" %>
                <section class="board">
                    <div class="header-title">공지사항 등록</div>
                    <form id="addForm">
                        <div class="board-container">
                            <table class="board-content-table">
                                <tr>
                                    <th>작성자</th>
                                    <td>댕봉사</td>
                                </tr>
                                <tr>
                                    <th>제목</th>
                                    <td id="addNoticeTitle" colspan="3">
                                        <input type="text" name="title" required>
                                    </td>
                                </tr>
                                <tr>
                                    <td id="addNoticeContent" colspan="4">
                                        <textarea name="content" required></textarea>
                                    </td>
                                </tr>
                            </table>
                        </div>
                        <br>
                        <div class="btn-area">
                            <input type="button" value="목록으로" onclick="location.href = '/care/admin/notices'">
                            <input type="button" value="등록" onclick="addNotice()">
                        </div>
                    </form>

                </section>
                <%@ include file="/WEB-INF/views/common/admin/adminSideBar.jsp" %>
                    <%@ include file="/WEB-INF/views/common/index/indexFooter.jsp" %>
        </body>

        </html>