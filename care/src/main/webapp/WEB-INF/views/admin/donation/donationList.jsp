<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
    <% request.setAttribute("currentPage", "donation_list" ); %>
        <!DOCTYPE html>
        <html>

        <head>
            <meta charset="UTF-8">
            <title>[관리자]당신에게 다시가는 길 - 지원사업</title>
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
                }

                .board-table {
                    width: 100%;
                    border-collapse: collapse;
                    font-size: 16px;
                    margin-top: 20px;
                }

                .board-table a {
                    text-decoration: none;
                    color: inherit;
                }

                .board-table th {
                    padding: 14px 10px;
                    border-bottom: 2px solid #DBDBDB;
                    text-align: center;
                    color: #333;
                    background-color: #fafafa;
                }

                .board-table td {
                    padding: 14px 10px;
                    border-bottom: 1px solid #eee;
                    text-align: center;
                    color: #333;
                }

                .board-table thead {
                    font-weight: bold;
                }

                .board-table tbody tr {
                    cursor: pointer;
                }

                .board-table tbody tr:hover {
                    background-color: #f5f5f5;
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

                #detailView {
                    display: none;
                    background-color: #f9f9f9;
                    border: 1px solid #eee;
                    border-radius: 8px;
                    padding: 25px 30px;
                    margin-bottom: 30px;
                    animation: fadeIn 0.5s ease-out;
                }

                @keyframes fadeIn {
                    from {
                        opacity: 0;
                        transform: translateY(-10px);
                    }

                    to {
                        opacity: 1;
                        transform: translateY(0);
                    }
                }

                .detail-header {
                    display: flex;
                    justify-content: space-between;
                    align-items: center;
                    border-bottom: 1px solid #ddd;
                    padding-bottom: 15px;
                    margin-bottom: 20px;
                }

                .detail-header-title {
                    font-size: 20px;
                    font-weight: bold;
                    color: #333;
                }

                .detail-close-btn {
                    background: #e0e0e0;
                    border: none;
                    padding: 8px 15px;
                    border-radius: 5px;
                    cursor: pointer;
                    font-size: 14px;
                    transition: background-color 0.2s;
                }

                .detail-close-btn:hover {
                    background: #ccc;
                }

                .detail-grid {
                    display: grid;
                    grid-template-columns: repeat(auto-fill, minmax(350px, 1fr));
                    gap: 15px 20px;
                }

                .detail-item {
                    display: flex;
                    font-size: 15px;
                }

                .detail-label {
                    font-weight: 600;
                    color: #555;
                    width: 120px;
                    flex-shrink: 0;
                }

                .detail-value {
                    color: #333;
                }

                .detail-footer {
                    text-align: right;
                    margin-top: 25px;
                    padding-top: 20px;
                    border-top: 1px solid #ddd;
                }

                .detail-footer button {
                    padding: 10px 20px;
                    border: none;
                    border-radius: 5px;
                    cursor: pointer;
                    font-size: 15px;
                    font-weight: 500;
                    margin-left: 10px;
                    transition: background-color 0.2s, transform 0.1s;
                }

                .btn-approve {
                    background-color: #3acdb2;
                    color: white;
                }

                .btn-approve:hover {
                    background-color: #31b8a0;
                }

                .btn-reject {
                    background-color: #f44336;
                    color: white;
                }

                .btn-reject:hover {
                    background-color: #d32f2f;
                }

                .modal-overlay {
                    position: fixed;
                    top: 0;
                    left: 0;
                    width: 100%;
                    height: 100%;
                    background-color: rgba(0, 0, 0, 0.6);
                    display: flex;
                    justify-content: center;
                    align-items: center;
                    z-index: 1000;
                    animation: fadeInModal 0.3s;
                }

                .modal-content {
                    background-color: #fff;
                    padding: 30px;
                    border-radius: 8px;
                    width: 90%;
                    max-width: 500px;
                    box-shadow: 0 4px 15px rgba(0, 0, 0, 0.2);
                }

                .modal-header {
                    padding-bottom: 15px;
                    margin-bottom: 20px;
                    border-bottom: 1px solid #eee;
                }

                .modal-title {
                    font-size: 18px;
                    font-weight: 600;
                }

                .modal-body input[type="text"],
                .modal-body textarea {
                    width: 100%;
                    padding: 10px;
                    border: 1px solid #ddd;
                    border-radius: 5px;
                    font-size: 15px;
                    margin-bottom: 15px;
                    box-sizing: border-box;
                }

                .modal-body textarea {
                    height: 150px;
                    resize: vertical;
                }

                .modal-footer {
                    text-align: right;
                    margin-top: 10px;
                    display: flex;
                    justify-content: flex-end;
                    align-items: center;
                }

                .modal-footer button {
                    padding: 10px 20px;
                    border: none;
                    border-radius: 5px;
                    cursor: pointer;
                    font-size: 15px;
                    margin-left: 10px;
                }

                .btn-close {
                    background-color: #888;
                    color: white;
                }

                .btn-send {
                    background-color: #3acdb2;
                    color: white;
                }

                @keyframes fadeInModal {
                    from {
                        opacity: 0;
                    }

                    to {
                        opacity: 1;
                    }
                }

                .spinner {
                    border: 4px solid rgba(0, 0, 0, 0.1);
                    width: 24px;
                    height: 24px;
                    border-radius: 50%;
                    border-left-color: #3acdb2;
                    animation: spin 1s ease infinite;
                }

                @keyframes spin {
                    0% {
                        transform: rotate(0deg);
                    }

                    100% {
                        transform: rotate(360deg);
                    }
                }

                @media (max-width : 600px) {

                    .board-table th,
                    .board-table td {
                        font-size: 12px;
                        padding: 10px 6px;
                    }

                    .detail-grid {
                        grid-template-columns: 1fr;
                    }
                }
            </style>
            <script src="${pageContext.request.contextPath}/resources/web/common/commonUtils.js"></script>
            <script>
                async function donationList(cp) {
                    const tbody = document.getElementById("donationBody");
                    tbody.innerHTML = "";

                    const result = await API.get("/care/api/admin/donations?cp=" + cp);

                    if (result.errorCode === 401 || result.errorCode === 403) {
                        location.href = "/care/admin/login";
                        return;
                    }

                    if (result.status !== 200) {
                        const row = document.createElement("tr");
                        row.innerHTML = '<td colspan="6" style="text-align:center;">목록을 불러오는 중 오류가 발생했습니다.</td>';
                        tbody.appendChild(row);
                        return;
                    }

                    document.getElementById("body").style.display = "block";
                    const donations = result.data;
                    const pageInfo = result.pageInfo;

                    if (!donations || donations.length === 0) {
                        const row = document.createElement("tr");
                        row.innerHTML = '<td colspan="6" style="text-align:center;">등록된 신청이 없습니다.</td>';
                        tbody.appendChild(row);
                        document.getElementById("pagingArea").innerHTML = "";
                        return;
                    }

                    for (const donation of donations) {

                        const row = document.createElement("tr");

                        row.setAttribute("onclick", "location.href='/care/admin/donations/" + donation.idx + "'");
                        row.innerHTML =
                            '<td>' + donation.idx + '</td>' +
                            '<td>' + donation.name + '</td>' +
                            '<td>' + donation.startDate + '</td>' +
                            '<td>' + donation.endDate + '</td>' +
                            '<td>' + donation.sponsor + '</td>' +
                            '<td>' + donation.status + '</td>';
                        tbody.appendChild(row);

                        // 페이징 함수 실행
                        makePaging(
                            pageInfo.totalCnt,
                            pageInfo.listSize,
                            pageInfo.pageSize,
                            pageInfo.cp,
                            "pagingArea", // 페이지 버튼이 들어갈 div id
                            donationList    // 페이지 번호 클릭 시 다시 donationList 호출
                        );
                    }
                }
            </script>
        </head>

        <body id="body" style="display: none;">
            <%@ include file="/WEB-INF/views/common/admin/adminHeader.jsp" %>
                <section class="board">
                    <div class="header-title">지원사업 관리</div>
                    <div class="board-container">
                        <table class="board-table">
                            <thead>
                                <tr>
                                    <th>NO</th>
                                    <th>지원사업명</th>
                                    <th>시작일</th>
                                    <th>마감일</th>
                                    <th>모금단체</th>
                                    <th>상태</th>
                                </tr>
                            </thead>
                            <tbody id="donationBody">
                            </tbody>
                        </table>
                        <div id="pagingArea" class="paging"></div>
                    </div>
                </section>
                <%@ include file="/WEB-INF/views/common/admin/adminSideBar.jsp" %>

                    <script>
                        window.addEventListener("DOMContentLoaded", function () {
                            donationList(1);
                        });
                    </script>
        </body>

        </html>