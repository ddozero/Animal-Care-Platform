<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
    <% request.setAttribute("currentPage", "donation_form" ); %>
        <!DOCTYPE html>
        <html>

        <head>
            <meta charset="UTF-8">
            <title>지원사업 등록</title>
            <link rel="stylesheet"
                href="https://cdn.jsdelivr.net/gh/orioncactus/pretendard/dist/web/static/pretendard.css">
            <style>
                /* 기본 스타일 */
                body {
                    font-family: 'Pretendard', sans-serif;
                }

                .project-form-container {
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

                .header-buttons .submit-btn {
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

                .form-field {
                    display: block;
                    width: 100%;
                    padding: 8px 12px;
                    font-size: 1em;
                    border: 1px solid #ccc;
                    border-radius: 8px;
                    box-sizing: border-box;
                }

                .status-item .form-field {
                    font-size: 18px;
                    font-weight: 600;
                    text-align: center;
                }

                textarea.form-field {
                    resize: vertical;
                    min-height: 150px;
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

                .image-upload-container {
                    display: flex;
                    gap: 30px;
                }

                .image-upload-box {
                    flex: 1;
                    padding: 20px;
                    border: 2px dashed #3acdb2;
                    border-radius: 12px;
                    background-color: #f6fffd;
                    text-align: center;
                }

                .image-upload-box label {
                    font-weight: bold;
                    display: block;
                    margin-bottom: 10px;
                }

                .image-upload-box .image-preview {
                    width: 100%;
                    height: 200px;
                    background-color: #e9ecef;
                    border-radius: 8px;
                    margin-bottom: 10px;
                    background-size: cover;
                    background-position: center;
                    display: flex;
                    align-items: center;
                    justify-content: center;
                    color: #aaa;
                    font-size: 1.5rem;
                }

                .image-upload-box .form-text {
                    font-size: 13px;
                    color: #555;
                }
            </style>
        </head>

        <body id="body">
            <%@ include file="/WEB-INF/views/common/admin/adminHeader.jsp" %>
                <div class="project-form-container">
                    <div class="project-header">
                        <h1>지원사업 등록</h1>
                        <div class="header-buttons">
                            <button type="button" onclick="location.href='/care/admin/donations'">목록</button>
                            <button type="button" class="submit-btn" onclick="submitDonation()">등록</button>
                        </div>
                    </div>

                    <form id="donationForm" onsubmit="return false;">
                        <div class="status-bar">
                            <div class="status-item">
                                <div class="label">시작일</div><input type="date" name="startDate" class="form-field">
                            </div>
                            <div class="status-item">
                                <div class="label">마감일</div><input type="date" name="endDate" class="form-field">
                            </div>
                            <div class="status-item">
                                <div class="label">목표금액</div><input type="number" name="amount" class="form-field"
                                    placeholder="숫자만 입력">
                            </div>
                        </div>
                        <div class="detail-section">
                            <h2>지원사업 정보</h2>
                            <label for="nameInput" style="display:block; margin-bottom:5px;"><b>사업명</b></label>
                            <input type="text" id="nameInput" name="name" class="form-field"
                                style="margin-bottom: 15px;">

                            <label for="statusIdxInput" style="display:block; margin-bottom:5px;"><b>상태</b></label>
                            <select id="statusIdxInput" name="statusIdx" class="form-field"
                                style="margin-bottom: 15px;">
                                <option value="1">대기</option>
                                <option value="2" selected>진행중</option>
                                <option value="3">모집완료</option>
                                <option value="4">완료</option>
                            </select>

                            <label for="sponsorInput" style="display:block; margin-bottom:5px;"><b>후원단체</b></label>
                            <input type="text" id="sponsorInput" name="sponsor" class="form-field"
                                style="margin-bottom: 15px;">
                        </div>
                        <div class="detail-section">
                            <h2>기부소개 및 사용처</h2>
                            <label for="introductionInput" style="display:block; margin-bottom:5px;"><b>기부
                                    소개</b></label>
                            <textarea id="introductionInput" name="content" class="form-field" rows="8"
                                style="margin-bottom: 15px;"></textarea>

                            <label for="sponsorDetailInput" style="display:block; margin-bottom:5px;"><b>모금액
                                    사용처</b></label>
                            <textarea id="sponsorDetailInput" name="sponsorDetail" class="form-field"
                                rows="5"></textarea>
                        </div>
                    </form>
                    <form id="imageUploadForm" enctype="multipart/form-data">
                        <div class="detail-section">
                            <h2>이미지 등록</h2>
                            <div class="image-upload-container">
                                <div class="image-upload-box">
                                    <label for="mainImageFile">메인 이미지</label>
                                    <div id="mainImagePreview" class="image-preview">+</div>
                                    <input type="file" id="mainImageFile" name="files" accept="image/*"
                                        onchange="previewImage(this, 'mainImagePreview')">
                                    <p class="form-text">대표 이미지를 업로드하세요. (권장: 1136x350)</p>
                                </div>
                                <div class="image-upload-box">
                                    <label for="secondaryImageFile">소개 이미지</label>
                                    <div id="secondaryImagePreview" class="image-preview">+</div>
                                    <input type="file" id="secondaryImageFile" name="files" accept="image/*"
                                        onchange="previewImage(this, 'secondaryImagePreview')">
                                    <p class="form-text">소개 섹션에 표시될 이미지를 업로드하세요.</p>
                                </div>
                            </div>
                        </div>
                    </form>
                </div>
                <%@ include file="/WEB-INF/views/common/admin/adminSideBar.jsp" %>
                    <script src="${pageContext.request.contextPath}/resources/web/common/commonUtils.js"></script>
                    <script>
                        async function submitDonation() {
                            const formJson = getJsonFromForm("donationForm");

                            if (!formJson.name || !formJson.startDate || !formJson.endDate || !formJson.amount || !formJson.sponsor || !formJson.content) {
                                alert("필수 입력 항목을 모두 채워주세요.");
                                return;
                            }

                            const mainImageFile = document.getElementById('mainImageFile').files[0];
                            if (!mainImageFile) {
                                alert("메인 이미지는 필수입니다.");
                                return;
                            }

                            const textResult = await API.post('/care/api/admin/donations', formJson);

                            if (textResult.status !== 201) {
                                alert(textResult.message || '지원사업 등록에 실패했습니다.');
                                return;
                            }

                            const donationIdx = textResult.data;

                            const imageForm = document.getElementById('imageUploadForm');
                            const imageFormData = new FormData(imageForm);
                            const fileUploadResult = await FileAPI.upload('/care/api/admin/donations/upload/' + donationIdx, imageFormData);

                            if (fileUploadResult.status === 201) {
                                alert('지원사업이 성공적으로 등록되었습니다.');
                                location.href = '/care/admin/donations';
                            } else {
                                alert(fileUploadResult.message || '이미지 업로드에 실패했습니다. 등록된 지원사업을 확인해주세요.');
                            }
                        }

                        window.addEventListener("DOMContentLoaded", function () {
                            document.getElementById("body").style.display = "block";
                        });
                    </script>
        </body>

        </html>