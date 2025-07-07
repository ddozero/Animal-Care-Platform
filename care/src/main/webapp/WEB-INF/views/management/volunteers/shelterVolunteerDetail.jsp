<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
    <% int loginUserId=0; if (session.getAttribute("loginUser") !=null) {
        loginUserId=((com.animal.api.auth.model.response.LoginResponseDTO) session.getAttribute("loginUser")).getIdx();
        } %>
        <!DOCTYPE html>
        <html>

        <head>
            <meta charset="UTF-8">
            <title>봉사 상세</title>
            <script src="${pageContext.request.contextPath}/resources/web/common/commonUtils.js"></script>
            <style>
                body {
                    font-family: 'Pretendard', '맑은 고딕', sans-serif;
                    background-color: #fff;
                    padding: 40px 20px;
                }

                .volunteer-detail-wrapper {
                    max-width: 1000px;
                    margin: 0 auto;
                }

                h2 {
                    text-align: center;
                    margin: 50px 0 30px;
                    font-size: 28px;
                    color: #3ACDB2;
                }

                /* ─────── 2단 레이아웃 ─────── */
                .volunteer-detail {
                    display: flex;
                    gap: 32px;
                    margin-bottom: 32px;
                }

                #volunteer-image {
                    width: 420px;
                    height: 420px;
                    object-fit: cover;
                    border-radius: 12px;
                    background-color: #eee;
                    flex-shrink: 0;
                }

                .volunteer-info {
                    flex: 1;
                    display: grid;
                    grid-template-columns: 120px 1fr;
                    row-gap: 10px;
                    column-gap: 16px;
                    align-items: center;
                }

                .volunteer-info h2 {
                    grid-column: 1 / -1;
                    font-size: 26px;
                    margin: 0 0 16px;
                    color: #333;
                }

                .volunteer-info .label {
                    font-weight: 600;
                    color: #444;
                }

                .volunteer-info .btn-group {
                    grid-column: 1 / -1;
                    text-align: center;
                    margin-top: 16px;
                }

                .volunteer-info button {
                    padding: 10px 20px;
                    background-color: #3acdb2;
                    color: #fff;
                    border: none;
                    border-radius: 8px;
                    font-weight: bold;
                    font-size: 15px;
                    cursor: pointer;
                    margin: 0 6px;
                    transition: background 0.2s;
                }

                .volunteer-info button:hover {
                    background-color: #32b8a1;
                }

                /* ─────── 내용 ─────── */
                .volunteer-description {
                    max-width: 1000px;
                    margin: 0 auto;
                }

                .volunteer-description .label {
                    font-weight: 600;
                    color: #444;
                    margin-bottom: 8px;
                }

                pre#volunteer-content {
                    background-color: #f6f6f8;
                    padding: 16px;
                    border-radius: 12px;
                    white-space: pre-line;
                    font-size: 15px;
                    line-height: 1.6;
                    color: #333;
                }

                .btn-area-center {
                    display: flex;
                    justify-content: center;
                    margin-top: 40px;
                    margin-bottom: 60px;
                    /* footer와의 간격 확보 */
                }

                .btn-area-center .back-btn {
                    padding: 10px 24px;
                    background-color: #3acdb2;
                    color: #fff;
                    border: none;
                    border-radius: 8px;
                    font-size: 15px;
                    font-weight: bold;
                    cursor: pointer;
                    transition: all 0.2s;
                }

                .btn-area-center .back-btn:hover {
                    background-color: #32b8a1;
                }

                pre#volunteer-content {
                    background-color: #f6f6f8;
                    padding: 16px;
                    border-radius: 12px;
                    white-space: pre-line;
                    font-size: 15px;
                    line-height: 1.6;
                    color: #333;
                    font-family: 'Pretendard', '맑은 고딕', sans-serif;
                    /* ✅ 추가 */
                }
            </style>

            <script>

                async function volunteerDetail() {
                    const idx = location.pathname.split("/").pop();
                    const result = await API.get('/care/api/management/volunteers/' + idx);

                    if (result.status !== 200) {
                        alert(result.errorMsg || "상세 정보를 불러올 수 없습니다.");
                        location.href = "/care/management/volunteers";
                        return;
                    }

                    const data = result.data;

                    // 캐시 방지용 timestamp 추가
                    const timestamp = new Date().getTime();
                    document.getElementById("volunteer-image").src =
                        data.imagePath ? ('${pageContext.request.contextPath}' + data.imagePath + '?t=' + timestamp)
                            : '${pageContext.request.contextPath}/resources/images/no-image.png';

                    document.getElementById("volunteer-title").innerText = data.title;
                    document.getElementById("volunteer-date").innerText = data.volunteerDate;
                    document.getElementById("volunteer-time").innerText = data.time + "시간";
                    document.getElementById("volunteer-location").innerText = data.location;
                    document.getElementById("volunteer-capacity").innerText = data.capacity + "명";
                    document.getElementById("volunteer-applicants").innerText = data.applicants + "명";
                    document.getElementById("volunteer-ageTarget").innerText = data.ageTarget;
                    document.getElementById("volunteer-type").innerText = data.volunteerTypeName;
                    document.getElementById("volunteer-status").innerText = data.volunteerStatusName;
                    document.getElementById("volunteer-createdAt").innerText = data.createdAt;
                    document.getElementById("volunteer-content").innerText = data.content;
                }

                function goBack() {
                    // 목록으로 직접 이동
                    location.href = "/care/management/volunteers";
                }



                async function deleteVolunteer() {
                    const idx = location.pathname.split("/").pop();
                    if (!confirm("정말 삭제하시겠습니까?")) return;
                    const result = await API.delete('/care/api/management/volunteers/' + idx);
                    if (result.status === 200) {
                        alert("삭제되었습니다.");
                        location.href = "/care/management/volunteers";
                    } else {
                        alert(result.errorMsg || "삭제 실패");
                    }
                }
                function editVolunteer() {
                    const idx = location.pathname.split("/").pop();
                    location.href = "/care/management/volunteers/form?idx=" + idx;
                }
                function goToApplicationList() {
                    const idx = location.pathname.split("/").pop(); // volunteerIdx만 추출
                    location.href = "/care/management/volunteers/" + idx + "/applications";
                }

                function goBack() {
                    history.back(); // ✅ 이전 페이지로 이동
                }


            </script>
        </head>

        <body data-login-user-id="<%= loginUserId %>">
            <%@ include file="/WEB-INF/views/common/management/managementHeader.jsp" %>
                <h2>봉사 상세</h2>
                <div class="volunteer-detail-wrapper">
                    <div class="volunteer-detail">
                        <img id="volunteer-image" src="" alt="대표 이미지">
                        <div class="volunteer-info">
                            <h2 id="volunteer-title"></h2>
                            <div class="label">일시</div>
                            <div><span id="volunteer-date"></span> / <span id="volunteer-time"></span></div>
                            <div class="label">장소</div>
                            <div id="volunteer-location"></div>
                            <div class="label">모집인원</div>
                            <div id="volunteer-capacity"></div>
                            <div class="label">신청자 수</div>
                            <div id="volunteer-applicants"></div>
                            <div class="label">대상 연령</div>
                            <div id="volunteer-ageTarget"></div>
                            <div class="label">유형</div>
                            <div id="volunteer-type"></div>
                            <div class="label">상태</div>
                            <div id="volunteer-status"></div>
                            <div class="label">등록일</div>
                            <div id="volunteer-createdAt"></div>
                            <div class="btn-group">
                                <button onclick="editVolunteer()">수정</button>
                                <button onclick="deleteVolunteer()">삭제</button>
                                <button onclick="goToApplicationList()">신청자 목록</button>
                            </div>
                        </div>
                    </div>

                    <div class="volunteer-description">
                        <div class="label">내용</div>
                        <pre id="volunteer-content"></pre>
                    </div>
                </div> <!-- volunteer-description 끝 -->

                <div class="btn-area-center">
                    <button class="back-btn" onclick="goBack()">목록으로</button>
                </div>

                </div>



                <script>

                    window.addEventListener("DOMContentLoaded", function () {
                        volunteerDetail();

                    });
                </script>
                <%@ include file="/WEB-INF/views/common/index/indexFooter.jsp" %>
        </body>

        </html>