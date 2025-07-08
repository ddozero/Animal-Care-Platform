<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
    <% request.setAttribute("currentPage", "donation_list" ); %>
        <!DOCTYPE html>
        <html>

        <head>
            <meta charset="UTF-8">
            <title>[관리자]당신에게 다시가는 길 - 지원사업 상세정보</title>
            <link rel="stylesheet"
                href="https://cdn.jsdelivr.net/gh/orioncactus/pretendard/dist/web/static/pretendard.css">
            <style>
                /* 기본 스타일 */
                body {
                    font-family: 'Pretendard', sans-serif;
                }

                .project-detail-container {
                    max-width: 1200px;
                    margin: 40px auto;
                    background: #fff;
                    padding: 32px;
                    border-radius: 16px;
                    box-shadow: 0 4px 12px rgba(0, 0, 0, 0.08);
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

                .header-buttons .delete-btn {
                    background-color: #ffeef0;
                    color: #f53d4f;
                }

                .header-buttons .delete-btn:hover {
                    background-color: #ffdde2;
                }

                .header-buttons .save-btn {
                    background-color: #3acdb2;
                    color: white;
                    border-color: #3acdb2;
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
                    flex: 1;
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
                }

                .project-image .image-overlay {
                    position: absolute;
                    bottom: 0;
                    left: 0;
                    right: 0;
                    padding: 24px;
                    background: linear-gradient(to top, rgba(0, 0, 0, 0.8) 0%, rgba(0, 0, 0, 0) 100%);
                    color: #fff;
                }

                .project-image .project-summary .info-row {
                    display: flex;
                    align-items: center;
                    margin-bottom: 10px;
                    font-size: 1rem;
                }

                .project-image .project-summary .info-row:last-child {
                    margin-bottom: 0;
                }

                .project-image .project-summary .info-row .label {
                    font-weight: 400;
                    color: #e0e0e0;
                    width: 100px;
                    flex-shrink: 0;
                }

                .project-image .project-summary .project-name-view {
                    font-size: 1.5rem;
                    font-weight: bold;
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
                    position: relative;
                }

                .introduction-image-column img {
                    width: 100%;
                    height: 100%;
                    object-fit: cover;
                }

                .introduction-text-column {
                    flex-grow: 1;
                }

                .content-box {
                    background-color: #f8f9fa;
                    padding: 20px;
                    border-radius: 12px;
                    font-size: 15px;
                    line-height: 1.6;
                    white-space: pre-line;
                    min-height: 100px;
                }

                .edit-field {
                    display: block;
                    width: 100%;
                    padding: 8px 12px;
                    font-size: 1em;
                    border: 1px solid #ccc;
                    border-radius: 8px;
                    box-sizing: border-box;
                }

                .status-item .edit-field {
                    font-size: 18px;
                    font-weight: 600;
                    text-align: center;
                }

                textarea.edit-field {
                    resize: vertical;
                    min-height: 150px;
                }

                #imageUploadForm {
                    padding: 20px;
                    margin-top: 15px;
                    border: 2px dashed #3acdb2;
                    border-radius: 12px;
                    background-color: #f6fffd;
                }

                #imageUploadForm h3 {
                    margin-top: 0;
                    text-align: center;
                }

                #imageUploadForm label {
                    display: block;
                    margin: 10px 0 5px;
                    font-weight: bold;
                }

                #imageUploadForm .form-text {
                    font-size: 13px;
                    color: #555;
                    margin-bottom: 15px;
                }

                #imageUploadForm input[type="file"] {
                    margin-bottom: 10px;
                }

                /* 수정/뷰 모드 토글 스타일 */
                .edit-mode {
                    display: none;
                }

                .project-detail-container.is-editing .view-mode {
                    display: none;
                }

                .project-detail-container.is-editing .edit-mode {
                    display: block;
                }

                /* 후원자 목록 테이블 스타일 */
                .donator-table {
                    width: 100%;
                    border-collapse: collapse;
                    margin-top: 20px;
                    font-size: 15px;
                }

                .donator-table th,
                .donator-table td {
                    border-bottom: 1px solid #e9e9e9;
                    padding: 12px 8px;
                    text-align: center;
                }

                .donator-table th {
                    background-color: #f8f9fa;
                    font-weight: 600;
                    color: #333;
                }

                .donator-table td.name {
                    text-align: left;
                    padding-left: 20px;
                }

                .donator-table .no-data {
                    text-align: center;
                    padding: 40px;
                    color: #888;
                }

                /* 페이징 스타일 */
                .paging {
                    margin: 28px 0;
                    text-align: center;
                }

                .paging button {
                    border: 1px solid #ddd;
                    background: #fff;
                    padding: 6px 12px;
                    margin: 0 2px;
                    border-radius: 4px;
                    cursor: pointer;
                    transition: background .2s;
                }

                .paging button:hover {
                    background: #f0f0f0;
                }

                .paging button:disabled {
                    background: #3acdb2;
                    color: #fff;
                    border-color: #3acdb2;
                    cursor: not-allowed;
                }
            </style>
        </head>

        <body id="body" style="display: none;">
            <%@ include file="/WEB-INF/views/common/admin/adminHeader.jsp" %>
                <div id="donationDetailWrapper" class="project-detail-container">
                    <div class="project-header">
                        <h1>지원사업 상세정보</h1>
                        <div class="header-buttons">
                            <button type="button" onclick="location.href='/care/admin/donations'">목록</button>
                            <button type="button" class="edit-btn" onclick="toggleEditMode(true)">수정</button>
                            <button type="button" class="save-btn" style="display:none;"
                                onclick="submitUpdate()">저장</button>
                            <button type="button" class="cancel-btn" style="display:none;"
                                onclick="toggleEditMode(false)">취소</button>
                            <button type="button" class="delete-btn delete" onclick="deleteDonation()">삭제</button>
                        </div>
                    </div>

                    <div class="view-mode">
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
                            <img id="mainImage" src="" alt="메인 이미지">
                            <div class="image-overlay">
                                <div class="project-summary">
                                    <div class="info-row"><span class="label">사업명</span><span id="projectName"
                                            class="project-name-view"></span></div>
                                    <div class="info-row"><span class="label">No</span><span id="projectNo"></span>
                                    </div>
                                    <div class="info-row"><span class="label">상태</span><span id="status"></span></div>
                                    <div class="info-row"><span class="label">후원단체</span><span id="sponsor"></span>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="detail-section">
                            <h2>기부소개</h2>
                            <div class="introduction-layout">
                                <div id="secondaryImageColumn" class="introduction-image-column" style="display: none;">
                                    <img id="secondaryImage" src="" alt="소개 이미지">
                                </div>
                                <div class="introduction-text-column">
                                    <div id="introduction" class="content-box"></div>
                                </div>
                            </div>
                        </div>
                        <div class="detail-section">
                            <h2>모금액 사용처</h2>
                            <div id="sponsorDetail" class="content-box"></div>
                        </div>
                    </div>

                    <div class="edit-mode">
                        <form id="donationForm" onsubmit="return false;">
                            <div class="status-bar">
                                <div class="status-item">
                                    <div class="label">시작일</div><input type="date" name="startDate" class="edit-field">
                                </div>
                                <div class="status-item">
                                    <div class="label">마감일</div><input type="date" name="endDate" class="edit-field">
                                </div>
                                <div class="status-item">
                                    <div class="label">모금현황</div>
                                    <div class="value">-</div>
                                </div>
                                <div class="status-item">
                                    <div class="label">목표금액</div><input type="number" name="amount" class="edit-field">
                                </div>
                                <div class="status-item">
                                    <div class="label">달성률</div>
                                    <div class="value">-</div>
                                </div>
                            </div>
                            <div class="detail-section">
                                <h2>지원사업 정보 수정</h2>
                                <label for="nameInput" style="display:block; margin-bottom:5px;"><b>사업명</b></label>
                                <input type="text" id="nameInput" name="name" class="edit-field"
                                    style="margin-bottom: 15px;">

                                <label for="statusIdxInput" style="display:block; margin-bottom:5px;"><b>상태</b></label>
                                <select id="statusIdxInput" name="statusIdx" class="edit-field"
                                    style="margin-bottom: 15px;">
                                    <option value="1">대기</option>
                                    <option value="2">진행중</option>
                                    <option value="3">모집완료</option>
                                    <option value="4">완료</option>
                                </select>

                                <label for="sponsorInput" style="display:block; margin-bottom:5px;"><b>후원단체</b></label>
                                <input type="text" id="sponsorInput" name="sponsor" class="edit-field"
                                    style="margin-bottom: 15px;">
                            </div>
                            <div class="detail-section">
                                <h2>기부소개 및 사용처 수정</h2>
                                <label for="introductionInput" style="display:block; margin-bottom:5px;"><b>기부
                                        소개</b></label>
                                <textarea id="introductionInput" name="content" class="edit-field" rows="8"
                                    style="margin-bottom: 15px;"></textarea>

                                <label for="sponsorDetailInput" style="display:block; margin-bottom:5px;"><b>모금액
                                        사용처</b></label>
                                <textarea id="sponsorDetailInput" name="sponsorDetail" class="edit-field"
                                    rows="5"></textarea>
                            </div>
                        </form>
                        <form id="imageUploadForm" enctype="multipart/form-data" class="edit-mode">
                            <div class="image-upload-container">
                                <h3>이미지 수정</h3>
                                <p class="form-text">이미지를 변경하려면 새 파일을 선택하세요. 선택하지 않으면 기존 이미지가 유지됩니다.</p>
                                <label for="mainImageFile">메인 이미지</label>
                                <input type="file" id="mainImageFile" name="files" accept="image/*">

                                <label for="secondaryImageFile">소개 이미지</label>
                                <input type="file" id="secondaryImageFile" name="files" accept="image/*">
                            </div>
                        </form>
                    </div>

                    <!-- 후원자 목록 섹션 -->
                    <div class="detail-section">
                        <h2>후원자 리스트</h2>
                        <table class="donator-table">
                            <thead>
                                <tr>
                                    <th style="width: 10%;">NO</th>
                                    <th style="width: 30%;">기부일</th>
                                    <th style="width: 30%;">기부자</th>
                                    <th style="width: 30%;">후원금액</th>
                                </tr>
                            </thead>
                            <tbody id="donatorListBody">
                                <!-- 후원자 목록이 여기에 동적으로 추가됩니다. -->
                            </tbody>
                        </table>
                        <div id="donatorPagingArea" class="paging"></div>
                    </div>
                </div>
                <%@ include file="/WEB-INF/views/common/admin/adminSideBar.jsp" %>
                    <script src="${pageContext.request.contextPath}/resources/web/common/commonUtils.js"></script>
                    <script>
                        const idx = location.pathname.split("/").pop();
                        let originalData = {};

                        function populateFields(donation) {
                            const formatNumber = function (num) { return num ? num.toLocaleString('ko-KR') : '0'; };

                            // 뷰 모드 필드 채우기
                            document.getElementById('startDate').textContent = donation.startDate;
                            document.getElementById('endDate').textContent = donation.endDate;
                            document.getElementById('currentAmount').textContent = formatNumber(donation.amount) + '원';
                            document.getElementById('targetAmount').textContent = formatNumber(donation.completionAmount) + '원';
                            document.getElementById('completionRate').textContent = donation.completionRate + '%';
                            document.getElementById('projectName').textContent = donation.name;
                            document.getElementById('projectNo').textContent = donation.idx;
                            document.getElementById('status').textContent = donation.status;
                            document.getElementById('introduction').textContent = donation.content;
                            document.getElementById('sponsor').textContent = donation.sponsor;
                            document.getElementById('sponsorDetail').textContent = donation.sponsorDetail || '내용 없음';

                            const timestamp = new Date().getTime(); // Cache-busting
                            if (donation.imagePaths && donation.imagePaths.length > 0) {
                                document.getElementById('mainImage').src = '${pageContext.request.contextPath}' + donation.imagePaths[0] + '?v=' + timestamp;
                            } else {
                                document.getElementById('mainImage').src = 'https://placehold.co/1136x350/e5e5e5/999999?text=No+Image';
                            }
                            if (donation.imagePaths && donation.imagePaths.length > 1) {
                                document.getElementById('secondaryImageColumn').style.display = 'block';
                                document.getElementById('secondaryImage').src = '${pageContext.request.contextPath}' + donation.imagePaths[1] + '?v=' + timestamp;
                            } else {
                                document.getElementById('secondaryImageColumn').style.display = 'none';
                            }

                            // 수정 모드 필드 채우기
                            const form = document.getElementById('donationForm');
                            form.name.value = donation.name;
                            form.startDate.value = donation.startDate;
                            form.endDate.value = donation.endDate;
                            form.amount.value = donation.completionAmount;
                            form.content.value = donation.content;
                            form.sponsor.value = donation.sponsor;
                            form.sponsorDetail.value = donation.sponsorDetail || '';
                            form.statusIdx.value = donation.statusIdx;
                        }

                        function toggleEditMode(isEdit) {
                            const wrapper = document.getElementById('donationDetailWrapper');
                            const headerButtons = document.querySelector('.header-buttons');

                            if (isEdit) {
                                wrapper.classList.add('is-editing');
                            } else {
                                wrapper.classList.remove('is-editing');
                                populateFields(originalData);
                            }

                            headerButtons.querySelector('.edit-btn').style.display = isEdit ? 'none' : 'inline-block';
                            headerButtons.querySelector('.save-btn').style.display = isEdit ? 'inline-block' : 'none';
                            headerButtons.querySelector('.cancel-btn').style.display = isEdit ? 'inline-block' : 'none';
                            headerButtons.querySelector('.delete-btn').style.display = isEdit ? 'none' : 'inline-block';
                        }

                        async function donationDetail() {
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
                            originalData = result.data;
                            populateFields(originalData);
                            toggleEditMode(false);
                            loadDonators(1);
                        }

                        async function submitUpdate() {
                            const formJson = getJsonFromForm("donationForm");
                            const result = await API.put('/care/api/admin/donations/' + idx, formJson);

                            const imageForm = document.getElementById('imageUploadForm');
                            const mainImageFile = document.getElementById('mainImageFile').files[0];
                            const secondaryImageFile = document.getElementById('secondaryImageFile').files[0];
                            const hasFilesToUpload = mainImageFile || secondaryImageFile;

                            if (hasFilesToUpload) {
                                const imageFormData = new FormData(imageForm);
                                const fileUploadResult = await FileAPI.upload('/care/api/admin/donations/upload/' + idx, imageFormData);
                                if (fileUploadResult.status != 201) {
                                    alert("이미지 업로드 오류 : 다시 진행해주세요.");
                                    return;
                                }
                            }

                            if (result.status === 200) {
                                alert(result.message || '성공적으로 저장되었습니다.');
                                await donationDetail();
                            } else {
                                alert(result.message || '저장에 실패했습니다.');
                            }
                        }

                        async function deleteDonation() {
                            if (!confirm("정말 삭제하시겠습니까? 이 작업은 되돌릴 수 없습니다.")) return;

                            const result = await API.delete('/care/api/admin/donations/' + idx);
                            if (result.status === 200) {
                                alert(result.message || "지원사업이 삭제되었습니다.");
                                location.href = "/care/admin/donations";
                            } else {
                                alert(result.message || '삭제에 실패했습니다.');
                            }
                        }

                        async function loadDonators(cp) {
                            const listBody = document.getElementById('donatorListBody');
                            const pagingArea = document.getElementById('donatorPagingArea');

                            listBody.innerHTML="";

                            const result = await API.get('/care/api/admin/donations/' + idx + '/user?cp=' + cp);

                            if (result.status != 200) {
                                const row = document.createElement('tr');
                                row.innerHTML = '<td colspan="4" class="no-data">후원 내역이 없습니다.</td>';
                                listBody.appendChild(row);
                                return;
                            }

                            const donators = result.data;
                            const pageInfo = result.pageInfo;

                            for (const donator of donators) {
                                const row = document.createElement('tr');

                                row.innerHTML = '<td>' + donator.idx + '</td>' +
                                    '<td>' + donator.createdAt + '</td>' +
                                    '<td>' + donator.name + '</td>' +
                                    '<td>' + donator.donatedAmount.toLocaleString('ko-KR') + '원</td>';

                                listBody.appendChild(row);
                            }

                            if (pageInfo) {
                                makePaging(
                                    pageInfo.totalCnt,
                                    pageInfo.listSize,
                                    pageInfo.pageSize,
                                    pageInfo.cp,
                                    "donatorPagingArea",
                                    loadDonators);
                            }


                        }

                        window.addEventListener("DOMContentLoaded", donationDetail);
                    </script>
        </body>

        </html>