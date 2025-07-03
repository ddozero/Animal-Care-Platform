<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
    <% request.setAttribute("currentPage", "donation_list" ); %>
        <!DOCTYPE html>
        <html>

        <head>
            <meta charset="UTF-8">
            <title>지원사업 상세정보</title>
            <link rel="stylesheet"
                href="https://cdn.jsdelivr.net/gh/orioncactus/pretendard/dist/web/static/pretendard.css">
            <style>
                .project-detail-container {
                    max-width: 1200px;
                    margin: 40px auto;
                    background: #fff;
                    padding: 32px;
                    border-radius: 16px;
                    box-shadow: 0 4px 12px rgba(0, 0, 0, 0.08);
                }

                .header-title {
                    font-size: 28px;
                    font-weight: bold;
                    margin-bottom: 40px;
                    text-align: left;
                }


                .project-header {
                    display: flex;
                    justify-content: space-between;
                    align-items: center;
                    border-bottom: 1px solid #e9e9e9;
                    padding-bottom: 20px;
                    margin-bottom: 20px;
                }

                .project-header h1 {
                    font-size: 28px;
                    font-weight: 700;
                    margin: 0;
                    color: #1a1a1a;
                }

                .header-buttons button {
                    background-color: #e9e9e9;
                    border: none;
                    padding: 8px 16px;
                    border-radius: 8px;
                    font-size: 14px;
                    font-weight: 600;
                    cursor: pointer;
                    margin-left: 8px;
                    transition: background-color 0.2s ease;
                }

                .header-buttons button:hover {
                    background-color: #dcdcdc;
                }

                .header-buttons button.delete {
                    background-color: #ffeef0;
                    color: #f53d4f;
                }

                .header-buttons button.delete:hover {
                    background-color: #ffdde2;
                }

                .status-bar {
                    display: flex;
                    justify-content: space-around;
                    background-color: #f8f9fa;
                    padding: 16px;
                    border-radius: 12px;
                    margin-bottom: 30px;
                }

                .status-item {
                    text-align: center;
                }

                .status-item .label {
                    font-size: 14px;
                    color: #666;
                    margin-bottom: 4px;
                }

                .status-item .value {
                    font-size: 18px;
                    font-weight: 600;
                    color: #1a1a1a;
                }

                .status-item .value.percentage {
                    color: #3ACDB2;
                }

                .project-image {
                    position: relative;
                    width: 100%;
                    max-width: 1136px;
                    height: 350px;
                    margin-bottom: 30px;
                    background-color: #e5e5e5;
                    border-radius: 12px;
                    overflow: hidden;
                }

                .project-image img {
                    width: 100%;
                    height: 100%;
                    object-fit: cover;
                    filter: brightness(0.6);
                    transition: transform 0.3s ease;
                }

                .project-image:hover img {
                    transform: scale(1.05);
                }

                .image-overlay {
                    position: absolute;
                    bottom: 0;
                    left: 0;
                    right: 0;
                    padding: 24px;
                    background: linear-gradient(to top, rgba(0, 0, 0, 0.8) 0%, rgba(0, 0, 0, 0) 100%);
                }

                .project-summary .info-row {
                    display: flex;
                    align-items: center;
                    margin-bottom: 10px;
                    font-size: 1rem;
                    color: #ffffff;
                }

                .project-summary .info-row:last-child {
                    margin-bottom: 0;
                }

                .project-summary .info-row .label {
                    font-weight: 400;
                    color: #e0e0e0;
                    width: 120px;
                    flex-shrink: 0;
                }

                #projectName {
                    font-size: 1.5rem;
                    font-weight: bold;
                }

                .project-summary #status {
                    font-weight: bold;
                    color: #3ACDB2;
                    background-color: rgba(255, 255, 255, 0.1);
                    padding: 4px 10px;
                    border-radius: 6px;
                }

                .project-summary #status.completed {
                    color: #a7ffeb;
                }

                .detail-section {
                    margin-bottom: 30px;
                }

                .detail-section h2 {
                    font-size: 20px;
                    font-weight: 600;
                    color: #333;
                    margin-bottom: 12px;
                    padding-bottom: 8px;
                    border-bottom: 2px solid #f0f2f5;
                }

                .introduction-layout {
                    display: flex;
                    gap: 24px;
                    align-items: flex-start;
                }

                .introduction-image-column {
                    width: 300px;
                    height: 300px;
                    flex-shrink: 0;
                    background-color: #f0f2f5;
                    border-radius: 12px;
                    overflow: hidden;
                }

                .introduction-image-column img {
                    width: 100%;
                    height: 100%;
                    object-fit: cover;
                }

                .introduction-text-column {
                    flex-grow: 1;
                    height: 300px;
                }

                .introduction-text-column .content-box {
                    height: 100%;
                }

                .content-box {
                    background-color: #f8f9fa;
                    padding: 20px;
                    border-radius: 12px;
                    font-size: 15px;
                    line-height: 1.6;
                    white-space: pre-line;
                }

                .usage-plan-grid {
                    display: grid;
                    grid-template-columns: repeat(auto-fill, minmax(300px, 1fr));
                    gap: 16px;
                }

                .usage-item {
                    background: #f8f9fa;
                    padding: 16px;
                    border-radius: 12px;
                    display: flex;
                    justify-content: space-between;
                    align-items: center;
                }

                .usage-item .label {
                    font-weight: 600;
                    color: #555;
                }

                .usage-item .value {
                    font-size: 16px;
                    font-weight: 500;
                    background-color: #e9ecef;
                    padding: 4px 10px;
                    border-radius: 6px;
                }
            </style>
            <script src="${pageContext.request.contextPath}/resources/web/common/commonUtils.js"></script>
            <script>
                const idx = location.pathname.split("/").pop();
                async function donationDetail() {
                    const formatNumber = (num) => num ? num.toLocaleString('ko-KR') : '0';
                    const result = await API.get('/care/api/admin/donations/' + idx);
                    if (result.errorCode === 401 || result.errorCode === 403) {
                        location.href = "/care/admin/login";
                        return;
                    }

                    if (result.status != 200) {
                        location.href = '/care/admin/donations';
                        return;
                    }
                    document.getElementById("body").style.display = "block";
                    const donation = result.data;

                    document.getElementById('startDate').textContent = donation.startDate;
                    document.getElementById('endDate').textContent = donation.endDate;
                    document.getElementById('currentAmount').textContent = formatNumber(donation.amount) + '원';
                    document.getElementById('targetAmount').textContent = formatNumber(donation.completionAmount) + '원';
                    document.getElementById('completionRate').textContent = donation.completionRate + '%';

                    document.getElementById('projectNo').textContent = donation.idx;
                    document.getElementById('projectName').textContent = donation.name;
                    const statusEl = document.getElementById('status');
                    statusEl.textContent = donation.status;
                    if (donation.status === "완료") {
                        statusEl.classList.add('completed');
                    }

                    const mainImage = document.getElementById('mainImage');
                    if (donation.imagePaths && donation.imagePaths.length > 0) {
                        mainImage.src = '${pageContext.request.contextPath}' + donation.imagePaths[0];
                    }

                    const secondaryImageColumn = document.getElementById('secondaryImageColumn');
                    const secondaryImage = document.getElementById('secondaryImage');
                    if (donation.imagePaths && donation.imagePaths.length > 1) {
                        secondaryImage.src = '${pageContext.request.contextPath}' + donation.imagePaths[1];
                        secondaryImageColumn.style.display = 'block';
                    }

                    document.getElementById('introduction').textContent = donation.content;
                    document.getElementById('sponsor').textContent = donation.sponsor;
                }

                async function deleteDonation() {
                    if (!confirm("정말 삭제하시겠습니까?")) {
                        return;
                    }

                    const result = await API.delete('/care/api/admin/donations/' + idx);
                    if (result.errorCode === 401 || result.errorCode === 403) {
                        location.href = "/care/admin/login";
                        return;
                    }

                    if (result.status === 200) {
                        alert(result.message);
                        location.href = "/care/admin/donations";
                    }
                }
            </script>
        </head>

        <body id="body" style="display: none;">
            <%@ include file="/WEB-INF/views/common/admin/adminHeader.jsp" %>
                <div class="header-title">지원사업 관리</div>
                <div class="project-detail-container">
                    <div class="project-header">
                        <h1>지원사업 상세정보</h1>
                        <div class="header-buttons">
                            <button type="button" onclick="location.href='/care/admin/donations'">목록</button>
                            <button type="button">수정</button>
                            <button type="button" class="delete" onclick="deleteDonation()">삭제</button>
                        </div>
                    </div>

                    <div class="status-bar">
                        <div class="status-item">
                            <div class="label">시작일</div>
                            <div id="startDate" class="value"></div>
                        </div>
                        <div class="status-item">
                            <div class="label">마감일</div>
                            <div id="endDate" class="value"></div>
                        </div>
                        <div class="status-item">
                            <div class="label">모금현황</div>
                            <div id="currentAmount" class="value"></div>
                        </div>
                        <div class="status-item">
                            <div class="label">목표금액</div>
                            <div id="targetAmount" class="value"></div>
                        </div>
                        <div class="status-item">
                            <div class="label">달성률</div>
                            <div id="completionRate" class="value percentage"></div>
                        </div>
                    </div>

                    <div class="project-image">
                        <img id="mainImage" src="" alt="Project Main Image">
                        <div class="image-overlay">
                            <div class="project-summary">
                                <div class="info-row">
                                    <span class="label">지원사업명</span>
                                    <span id="projectName"></span>
                                </div>
                                <div class="info-row">
                                    <span class="label">NO</span>
                                    <span id="projectNo"></span>
                                </div>
                                <div class="info-row">
                                    <span class="label">상태</span>
                                    <span id="status"></span>
                                </div>
                            </div>
                        </div>
                    </div>

                    <div class="detail-section">
                        <h2>기부소개</h2>
                        <div class="introduction-layout">
                            <div id="secondaryImageColumn" class="introduction-image-column" style="display: none;">
                                <img id="secondaryImage" src="" alt="Donation Content Image">
                            </div>
                            <div class="introduction-text-column">
                                <div id="introduction" class="content-box"></div>
                            </div>
                        </div>
                    </div>
                    <div class="detail-section">
                        <h2>사용계획</h2>
                        <div class="usage-plan-grid">
                            <div class="usage-item">
                                <span class="label">모금액 사용처</span>
                                <span id="usageTarget" class="value">서울유기견</span>
                            </div>
                            <div class="usage-item">
                                <span class="label">모금액 사용비</span>
                                <span id="usageAmount" class="value">50,000</span>
                            </div>
                            <div class="usage-item">
                                <span class="label">모금 단체</span>
                                <span id="sponsor" class="value"></span>
                            </div>
                        </div>
                    </div>
                </div>
                <%@ include file="/WEB-INF/views/common/admin/adminSideBar.jsp" %>
                    <script>
                        window.addEventListener("DOMContentLoaded", function () {
                            donationDetail();
                        });
                    </script>
        </body>

        </html>