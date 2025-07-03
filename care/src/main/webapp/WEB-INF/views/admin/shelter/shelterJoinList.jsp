<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
    <% request.setAttribute("currentPage", "member_shelter" ); %>
        <!DOCTYPE html>
        <html>

        <head>
            <meta charset="UTF-8">
            <title>Shelter Requests</title>
             <link rel="stylesheet" href="https://cdn.jsdelivr.net/gh/orioncactus/pretendard/dist/web/static/pretendard.css">
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
        </head>

        <body id="body" style="display: none;">
            <%@ include file="/WEB-INF/views/common/admin/adminHeader.jsp" %>
                <section class="board">
                    <div class="header-title">보호시설 승인 관리</div>
                    <div class="board-container">
                        <div id="detailView">
                            <div id="detailContent" style="display:none;">
                                <div class="detail-header">
                                    <span id="detailHeaderTitle" class="detail-header-title"></span>
                                    <button class="detail-close-btn" onclick="hideDetails()">닫기</button>
                                </div>
                                <div class="detail-grid">
                                    <div class="detail-item"><span class="detail-label">신청번호</span><span id="detailIdx"
                                            class="detail-value"></span></div>
                                    <div class="detail-item"><span class="detail-label">담당자명</span><span
                                            id="detailPersonName" class="detail-value"></span></div>
                                    <div class="detail-item"><span class="detail-label">아이디</span><span id="detailId"
                                            class="detail-value"></span></div>
                                    <div class="detail-item"><span class="detail-label">이메일</span><span id="detailEmail"
                                            class="detail-value"></span></div>
                                    <div class="detail-item"><span class="detail-label">시설 종류</span><span
                                            id="detailShelterType" class="detail-value"></span></div>
                                    <div class="detail-item"><span class="detail-label">연락처</span><span
                                            id="detailShelterTel" class="detail-value"></span></div>
                                    <div class="detail-item"><span class="detail-label">사업자번호</span><span
                                            id="detailBusinessNumber" class="detail-value"></span></div>
                                    <div class="detail-item"><span class="detail-label">신청일</span><span
                                            id="detailCreatedAt" class="detail-value"></span></div>
                                    <div class="detail-item" style="grid-column: 1 / -1;"><span
                                            class="detail-label">주소</span><span id="detailAddress"
                                            class="detail-value"></span></div>
                                </div>
                                <div class="detail-footer">
                                    <button class="btn-reject" id="rejectionRequest">거절</button>
                                    <button class="btn-approve" id="updateRequest">승인</button>
                                </div>
                            </div>
                        </div>

                        <table class="board-table">
                            <thead>
                                <tr>
                                    <th>NO</th>
                                    <th>시설명</th>
                                    <th>담당자</th>
                                    <th>E-MAIL</th>
                                    <th>연락처</th>
                                    <th>신청일</th>
                                </tr>
                            </thead>
                            <tbody id="requestBody">
                            </tbody>
                        </table>
                        <div id="pagingArea" class="paging"></div>
                    </div>
                </section>
                <%@ include file="/WEB-INF/views/common/admin/adminSideBar.jsp" %>

                    <div id="rejectionModal" class="modal-overlay" style="display: none;">
                        <div class="modal-content">
                            <div class="modal-header">
                                <span class="modal-title">거절 사유 전송</span>
                            </div>
                            <div class="modal-body">
                                <form id="rejectionForm">
                                    <input name="to" type="hidden" id="rejectionEmail">
                                    <input name="subject" type="text" id="rejectionSubject" placeholder="제목을 입력하세요">
                                    <textarea name="text" id="rejectionBody" placeholder="거절 사유를 입력하세요"></textarea>
                                </form>
                            </div>
                            <div class="modal-footer">
                                <button class="btn-close" onclick="hideRejectionModal()">닫기</button>
                                <button class="btn-send" id="sendRejectionBt">전송</button>
                                <div class="spinner" id="rejectionSpinner" style="display: none;"></div>
                            </div>
                        </div>
                    </div>


                    <script>
                        const detailView = document.getElementById("detailView");
                        const detailContent = document.getElementById("detailContent");
                        let currentRequestIdx = null;

                        function hideDetails() {
                            detailView.style.display = "none";
                        }

                        async function showDetails(idx) {
                            detailView.style.display = "block";
                            detailContent.style.display = "none";
                            currentRequestIdx = idx;

                            const result = await API.get("/care/api/admin/shelters/requests/" + idx);

                            if (result.errorCode === 401 || result.errorCode === 403) {
                                location.href = "/care/admin/login";
                                return;
                            }

                            if (result.status !== 200) {
                                return;
                            }

                            const data = result.data;

                            document.getElementById("detailHeaderTitle").textContent = data.shelterName;
                            document.getElementById("detailIdx").textContent = data.idx;
                            document.getElementById("detailPersonName").textContent = data.shelterPersonName;
                            document.getElementById("detailId").textContent = data.id;
                            document.getElementById("detailEmail").textContent = data.email;
                            document.getElementById("detailShelterType").textContent = data.shelterType;
                            document.getElementById("detailShelterTel").textContent = data.shelterTel;
                            document.getElementById("detailBusinessNumber").textContent = data.shelterBusinessNumber;
                            document.getElementById("detailCreatedAt").textContent = data.createdAt;
                            document.getElementById("detailAddress").textContent = data.shelterAddress + ' ' + data.shelterAddressDetail + ' (' + data.shelterZipCode + ')';

                            document.getElementById("updateRequest").setAttribute("onclick", "updateShelterJoinRequest(" + data.idx + ")");
                            document.getElementById("rejectionRequest").setAttribute("onclick", "showRejectionModal()");
                            document.getElementById("rejectionEmail").value = data.email;
                            document.getElementById("sendRejectionBt").setAttribute("onclick", "sendRejectionEmail(" + data.idx + ")");

                            detailContent.style.display = "block";
                        }

                        async function requestList(cp) {
                            const tbody = document.getElementById("requestBody");
                            tbody.innerHTML = "";

                            const result = await API.get("/care/api/admin/shelters/requests?cp=" + cp);

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
                            const requests = result.data;
                            const pageInfo = result.pageInfo;

                            if (!requests || requests.length === 0) {
                                const row = document.createElement("tr");
                                row.innerHTML = '<td colspan="6" style="text-align:center;">등록된 신청이 없습니다.</td>';
                                tbody.appendChild(row);
                                document.getElementById("pagingArea").innerHTML = "";
                                return;
                            }

                            for (const request of requests) {
                                const row = document.createElement("tr");
                                row.setAttribute("onclick", "showDetails(" + request.idx + ")");
                                row.innerHTML =
                                    '<td>' + request.idx + '</td>' +
                                    '<td>' + request.shelterName + '</td>' +
                                    '<td>' + request.shelterPersonName + '</td>' +
                                    '<td>' + request.email + '</td>' +
                                    '<td>' + request.shelterTel + '</td>' +
                                    '<td>' + request.createdAt + '</td>';
                                tbody.appendChild(row);
                            }

                            makePaging(
                                pageInfo.totalCnt,
                                pageInfo.listSize,
                                pageInfo.pageSize,
                                pageInfo.cp,
                                "pagingArea",
                                requestList
                            );
                        }

                        async function updateShelterJoinRequest(idx) {
                            const result = await API.put("/care/api/admin/shelters/requests/" + idx, null);

                            if (result.status === 200) {
                                requestList(1);
                                hideDetails();
                            }
                        }

                        function showRejectionModal() {
                            document.getElementById("rejectionModal").style.display = "flex";
                        }

                        function hideRejectionModal() {
                            document.getElementById("rejectionModal").style.display = "none";
                            document.getElementById("rejectionSubject").value = "";
                            document.getElementById("rejectionBody").value = "";
                        }

                        async function sendRejectionEmail(idx) {
                            const sendButton = document.getElementById("sendRejectionBt");
                            const spinner = document.getElementById("rejectionSpinner");

                            sendButton.style.display = "none";
                            spinner.style.display = "block";

                            try {
                                const form = getJsonFromForm("rejectionForm");
                                const result = await API.post("/care/api/admin/shelters/requests/" + idx, form);

                                if (result.status === 200) {
                                    alert(result.message);
                                    hideRejectionModal();
                                }
                            } finally {
                                sendButton.style.display = "block";
                                spinner.style.display = "none";
                            }
                        }

                        window.addEventListener("DOMContentLoaded", function () {
                            requestList(1);
                        });
                    </script>
        </body>

        </html>