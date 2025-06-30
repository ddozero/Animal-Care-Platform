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
            const animalIdx = path[path.length - 1];

            // 보호시설 유기동물 상세 조회 함수 
            async function animalDetail() {
                const idx = location.pathname.split("/").pop();
                const result = await API.get('/care/api/shelters/' + shelterIdx + '/animals/' + animalIdx);
                if (result.status != 200) {
                    location.href = '/care/shelters/' + shelterIdx;
                    return;
                }
                document.getElementById("body").style.display = "block";
                const animal = result.data;

                document.getElementById("animalImage").src = '${pageContext.request.contextPath}' + animal.imagePath;
                document.getElementById("animalName").textContent = animal.name;
                document.getElementById("idx").textContent = animal.idx;
                document.getElementById("gender").textContent = animal.gender === "M" ? "남" : "여";
                document.getElementById("age").textContent = animal.age;
                document.getElementById("size").textContent = animal.size === 1 ? "소형" : animal.size === 2 ? "중형" : "대형";
                document.getElementById("neuter").textContent = animal.neuter === 1 ? "O" : "X";
                document.getElementById("breed").textContent = animal.breed;
                document.getElementById("type").textContent = animal.type;
                document.getElementById("personality").textContent = animal.personality;
                document.getElementById("adoptionStatus").textContent = animal.adoptionStatus;
                document.getElementById("createdAt").textContent = animal.createdAt;
                document.getElementById("adoption").setAttribute("onclick", "location.href='/care/animals/" + animal.idx + "/adoption'");
                document.getElementById("description").innerText = animal.description;

                document.getElementById("shelterName").textContent = animal.shelterName;
                document.getElementById("shelterPersonName").textContent = animal.shelterPersonName;
                document.getElementById("shelterTel").textContent = animal.shelterTel;
                document.getElementById("shelterZipCode").textContent = animal.shelterZipCode;
                document.getElementById("shelterAddress").textContent = animal.shelterAddress;
                document.getElementById("shelterAddressDetail").textContent = animal.shelterAddressDetail;
            }
        </script>
    </head>

    <body id="body" style="display: none;">
        <%@ include file="/WEB-INF/views/common/index/indexHeader.jsp" %>
        <div class="animal-detail">
            <div class="animal-image">
                <img id="animalImage" src="" width="200" height="200">
            </div>
            <div class="animal-info">
                <h2 id="animalName">이름</h2>
                <div><span>관리번호:</span> <span id="idx"></span></div>
                <div><span>성별:</span> <span id="gender"></span></div>
                <div><span>나이:</span> <span id="age"></span>세</div>
                <div><span>크기:</span> <span id="size"></span></div>
                <div><span>중성화:</span> <span id="neuter"></span></div>
                <div><span>품종:</span> <span id="breed"></span></div>
                <div><span>종류:</span> <span id="type"></span></div>
                <div><span>성격:</span> <span id="personality"></span></div>
                <div><span>입양상태:</span> <span id="adoptionStatus"></span></div>
                <div><span>등록일:</span> <span id="createdAt"></span></div>
                <div><input type="button" id="adoption" value="입양상담신청"></div>
                <div><span>소개</span><br><span id="description"></span></div>
            </div>
            <hr>
            <h3>보호소 정보</h3>
            <div><span>보호소명:</span> <span id="shelterName"></span></div>
            <div><span>담당자:</span> <span id="shelterPersonName"></span></div>
            <div><span>연락처:</span> <span id="shelterTel"></span></div>
            <div><span>주소:</span>
                <span id="shelterZipCode"></span>
                <span id="shelterAddress"></span>
                <span id="shelterAddressDetail"></span>
            </div>
        </div>
        <input type="button" value="목록" onclick="location.href='/care/shelters/'+shelterIdx">
        <script>
            window.addEventListener("DOMContentLoaded", animalDetail);
        </script>
    </body>

    </html>