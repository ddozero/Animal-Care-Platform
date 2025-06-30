<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
    <!DOCTYPE html>
    <html>

    <head>
        <meta charset="UTF-8">
        <title>Insert title here</title>
        <script src="${pageContext.request.contextPath}/resources/web/common/commonUtils.js"></script>
        <script>
            const path = location.pathname.split("/");
            const shelterIdx = path[3];
            const volunteerIdx = path[path.length - 1];
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
                    <img id="volunteerImage" src="" width="200" height="200" alt="봉사 이미지">
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
                    <hr>
                    <div><span>내용:</span><br>
                        <div id="volunteerContent" style="white-space: pre-wrap;"></div>
                    </div>
                    <hr>
                    <div><span>보호소명: </span><span id="shelterName"></span></div>
                    <div><span>보호소 유형: </span><span id="shelterType"></span></div>
                </div>
            </div>
            <input type="button" value="목록" onclick="location.href='/care/shelters/'+shelterIdx">
            <script>
                window.addEventListener("DOMContentLoaded", function () {
                    shelterVolunteerDetail();
                });
            </script>
    </body>

    </html>