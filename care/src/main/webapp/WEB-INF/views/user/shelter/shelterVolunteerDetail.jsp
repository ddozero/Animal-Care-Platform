<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
    <!DOCTYPE html>
    <html>

    <head>
        <meta charset="UTF-8">
        <title>Insert title here</title>
        <style>
            /* ───────── 전체 래퍼 ───────── */
            .volunteer-detail {
                max-width: 1200px;
                margin: 40px auto;
                display: flex;
                gap: 32px;
                font-size: 15px;
                line-height: 1.55;
            }

            /* ───────── 좌측 사진 ───────── */
            .volunteer-image {
                width: 280px;
                aspect-ratio: 1 / 1;
                background: #e5e5e5;
                border-radius: 12px;
                overflow: hidden;
                flex-shrink: 0;
            }

            .volunteer-image img {
                width: 100%;
                height: 100%;
                object-fit: cover;
                display: block;
            }

            /* ───────── 우측 정보 영역 ───────── */
            .volunteer-info {
                flex: 1 1 0;
                display: grid;
                grid-template-columns: 200px 1fr;
                row-gap: 8px;
                column-gap: 12px;
            }

            .volunteer-info h2 {
                grid-column: 1 / 3;
                margin: 0 0 12px 0;
                font-size: 22px;
                font-weight: 700;
            }

            .volunteer-info div span:first-child {
                font-weight: 600;
                color: #444;
            }

            .volunteer-info .btn-group {
                grid-column: 1 / 3;
                display: flex;
                gap: 12px;
                margin-top: 18px;
            }

            .volunteer-info input[type="button"] {
                grid-column: 1 / 3;
                width: 150px;
                height: 40px;
                border: none;
                border-radius: 20px;
                background: #d9d9d9;
                cursor: pointer;
                font-weight: 600;
            }

            .volunteer-info input[type="button"]:hover {
                background: #c4c4c4;
            }

            /* ───────── 소개 ───────── */
            .description-wrapper {
                max-width: 1200px;
                margin: 20px auto 0 auto;
            }

            #volunteerContent {
                padding: 14px 16px;
                background: #f6f6f8;
                border-radius: 12px;
                width: 100%;
                line-height: 1.6;
                white-space: pre-line;
                box-sizing: border-box;
            }

            /* ───────── Divider ───────── */
            .animal-detail+hr {
                max-width: 1200px;
                margin: 40px auto;
                border: none;
                height: 1px;
                background: #e0e0e0;
            }

            /* ───────── 보호소 정보 ───────── */
            .shelter-wrapper {
                max-width: 1200px;
                margin: 30px auto;
                display: flex;
                gap: 32px;
                align-items: flex-start;
                background: #f6f6f8;
                border-radius: 16px;
                padding: 24px;
                box-sizing: border-box;
            }

            .shelter-info {
                flex: 1;
                font-size: 14px;
                line-height: 1.6;
            }

            .shelter-vertical>div {
                margin-bottom: 8px;
            }

            .shelter-vertical span:first-child {
                font-weight: 600;
                color: #444;
                display: inline-block;
                width: 80px;
            }

            .shelter-map {
                width: 240px;
                height: 180px;
                border-radius: 12px;
                overflow: hidden;
                flex-shrink: 0;
            }
        </style>
        <script src="${pageContext.request.contextPath}/resources/web/common/commonUtils.js"></script>
        <script>
            const path = location.pathname.split("/");
            const shelterIdx = path[3];
            const volunteerIdx = path[path.length - 1];

            // 보호시설 봉사 상세조회 함수
            async function shelterVolunteerDetail() {
                const result = await API.get('/care/api/shelters/' + shelterIdx + '/volunteers/' + volunteerIdx);
                if (result.status != 200) {
                    location.href = '/care/shelters/' + shelterIdx;
                    return;
                }
                document.getElementById("body").style.display = "block";

                const volunteer = result.data;

                document.getElementById("volunteerImage").src = '${pageContext.request.contextPath}' + volunteer.imagePath;
                document.getElementById("volunteerTitle").textContent = volunteer.title;
                document.getElementById("volunteerDate").textContent = volunteer.volunteerDate;
                document.getElementById("volunteerTime").textContent = volunteer.time;
                document.getElementById("volunteerLocation").textContent = volunteer.location;
                document.getElementById("volunteerCapacity").textContent = volunteer.capacity;
                document.getElementById("volunteerApplicants").textContent = volunteer.applicants;
                document.getElementById("volunteerAgeTarget").textContent = volunteer.ageTarget;
                document.getElementById("volunteerType").textContent = volunteer.type;
                document.getElementById("volunteerStatus").textContent = volunteer.status;
                document.getElementById("volunteerCreatedAt").textContent = volunteer.createdAt;
                document.getElementById("volunteerContent").innerHTML = volunteer.content;

                // 보호소 정보
                document.getElementById("shelterName").textContent = volunteer.shelter;
                document.getElementById("shelterType").textContent = volunteer.shelterType;
            }

        </script>
    </head>

    <body id="body" style="display: none;">
        <%@ include file="/WEB-INF/views/common/index/indexHeader.jsp" %>
            <div class="volunteer-detail">
                <div class="volunteer-image">
                    <img id="volunteerImage" src="" alt="봉사 이미지">
                </div>
                <div class="volunteer-info">
                    <h2 id="volunteerTitle">봉사 제목</h2>
                    <div><span>일시: </span><span id="volunteerDate"></span></div>
                    <div><span>봉사 시간: </span><span id="volunteerTime"></span>시간</div>
                    <div><span>모집 인원: </span><span id="volunteerCapacity"></span>명</div>
                    <div><span>신청 인원: </span><span id="volunteerApplicants"></span>명</div>
                    <div><span>연령 대상: </span><span id="volunteerAgeTarget"></span></div>
                    <div><span>봉사 유형: </span><span id="volunteerType"></span></div>
                    <div><span>상태: </span><span id="volunteerStatus"></span></div>
                    <div><span>등록일: </span><span id="volunteerCreatedAt"></span></div>
                    <div><span>장소: </span><span id="volunteerLocation"></span></div>
                    <div class="btn-group">
                        <input type="button" id="adoption" value="봉사 신청"
                            onclick="location.href='/care/volunteers/'+volunteerIdx+'/submit'">
                        <input type="button" value="목록" onclick="location.href='/care/shelters/'+shelterIdx">
                    </div>
                </div>
            </div>
            <div class="description-wrapper">
                <div id="volunteerContent"></div>
            </div>
            <div class="shelter-wrapper">
                <div class="shelter-info">
                    <h3>보호소 정보</h3>
                    <div class="shelter-vertical">
                        <div><span>보호소명: </span><span id="shelterName"></span></div>
                        <div><span>보호소 유형: </span><span id="shelterType"></span></div>
                    </div>
                </div>
            </div>

            <%@ include file="/WEB-INF/views/common/index/indexFooter.jsp" %>
                <script>
                    window.addEventListener("DOMContentLoaded", function () {
                        shelterVolunteerDetail();
                    });
                </script>
    </body>

    </html>