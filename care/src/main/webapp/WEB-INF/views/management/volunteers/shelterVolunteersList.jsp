<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
    <% int loginUserId=0; if (session.getAttribute("loginUser") !=null) {
        loginUserId=((com.animal.api.auth.model.response.LoginResponseDTO) session.getAttribute("loginUser")).getIdx();
        } %>
        <!DOCTYPE html>
        <html>

        <head>
            <meta charset="UTF-8">
            <title>당신에게 다시가는 길 - 보호시설 봉사 목록</title>
            <script src="${pageContext.request.contextPath}/resources/web/common/commonUtils.js"></script>
            <style>
                body {
                    font-family: 'Pretendard', '맑은 고딕', sans-serif;
                    background-color: #fff;
                    padding: 20px;
                }

                .board-container {
                    max-width: 1200px;
                    margin: 0 auto;
                }

                h2 {
                    text-align: center;
                    margin: 50px 0 30px;
                    font-size: 28px;
                    color: #3ACDB2;
                }

                .board-table {
                    width: 100%;
                    border-collapse: collapse;
                    font-size: 16px;
                    background-color: #fff;
                    border-radius: 6px;
                    overflow: hidden;
                }

                .board-table th {
                    padding: 14px 10px;
                    border-bottom: 2px solid #DBDBDB;
                    text-align: center;
                    background-color: #f1f1f1;
                    color: #333;
                }

                .board-table td {
                    padding: 14px 10px;
                    border-bottom: 1px solid #eee;
                    text-align: center;
                    color: #333;
                }

                .board-table tbody tr:hover {
                    background-color: #fafafa;
                }

                .badge-type {
                    display: inline-block;
                    padding: 4px 10px;
                    font-size: 13px;
                    color: #555;
                    background-color: #eee;
                    border-radius: 20px;
                    font-weight: 500;
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

                .btn-area-right {
                    text-align: right;
                    margin: 10px 0 30px;
                }

                .btn-area-right button {
                    padding: 10px 20px;
                    background-color: #3ACDB2;
                    color: #fff;
                    border: none;
                    border-radius: 8px;
                    font-weight: bold;
                    cursor: pointer;
                }

                .btn-area-right button:hover {
                    background-color: #32b7a1;
                }
            </style>

            <script>
                async function volunteerList(cp) {
                    const tbody = document.getElementById("volunteerBody");
                    tbody.innerHTML = "";

                    try {
                        const result = await API.get("/care/api/management/volunteers?cp=" + cp);

                        if (result.status !== 200 || !Array.isArray(result.data) || result.data.length === 0) {
                            const row = document.createElement("tr");
                            row.innerHTML = '<td colspan="10" style="text-align:center;">등록된 봉사활동이 없습니다.</td>';
                            tbody.appendChild(row);
                            return;
                        }

                        const volunteers = result.data;
                        const pageInfo = result.pageInfo;

                        for (const v of volunteers) {
                            const row = document.createElement("tr");
                            row.innerHTML =
                                '<td>' + v.createdAt + '</td>' +
                                '<td><span class="badge-type">' + v.volunteerType + '</span></td>' +
                                '<td><a href="/care/management/volunteers/' + v.idx + '">' + v.title + '</a></td>' +
                                '<td>' + v.location + '</td>' +
                                '<td>' + v.capacity + '명</td>' +
                                '<td>' + (v.applicants || 0) + '명</td>' +
                                '<td>' + v.ageTarget + '</td>' +
                                '<td>' + v.volunteerStatus + '</td>' +
                                '<td>' + v.time + '시간</td>' +
                                '<td>' + v.volunteerDate + '</td>';
                            tbody.appendChild(row);
                        }

                        if (pageInfo) {
                            makePaging(
                                pageInfo.totalCnt,
                                pageInfo.listSize,
                                pageInfo.pageSize,
                                pageInfo.cp,
                                "pagingArea",
                                volunteerList
                            );
                        }

                    } catch (error) {
                        console.error(error);
                        alert("서버와 통신 중 오류가 발생했습니다.");
                    }
                }

                function goToVolunteerForm() {
                    const loginUserId = parseInt(document.body.dataset.loginUserId || "0");
                    if (!loginUserId) {
                        alert("로그인 후 이용해주세요.");
                        location.href = "/care/login";
                        return;
                    }
                    location.href = "${pageContext.request.contextPath}/management/volunteers/form";
                }
            </script>
        </head>

        <body data-login-user-id="<%= loginUserId %>">
            <%@ include file="/WEB-INF/views/common/management/managementHeader.jsp" %>

                <div class="board-container">
                    <h2>봉사 목록</h2>

                    <table class="board-table">
                        <thead>
                            <tr>
                                <th>등록일</th>
                                <th>유형</th>
                                <th>제목</th>
                                <th>장소</th>
                                <th>모집인원</th>
                                <th>신청자 수</th>
                                <th>대상 연령</th>
                                <th>상태</th>
                                <th>봉사시간</th>
                                <th>봉사일</th>
                            </tr>
                        </thead>
                        <tbody id="volunteerBody"></tbody>
                    </table>

                    <div id="pagingArea" class="paging"></div>

                    <div class="btn-area-right">
                        <button onclick="goToVolunteerForm()">봉사 등록</button>
                    </div>
                </div>

                <script>
                    window.addEventListener("DOMContentLoaded", function () {
                        volunteerList(1);
                    });
                </script>

                <%@ include file="/WEB-INF/views/common/index/indexFooter.jsp" %>
        </body>

        </html>