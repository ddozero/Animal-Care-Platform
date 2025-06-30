<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
    <!DOCTYPE html>
    <html>

    <head>
        <meta charset="UTF-8">
        <title>Insert title here</title>
         <script src="${pageContext.request.contextPath}/resources/web/common/commonUtils.js"></script>
        <script>
            const path = location.pathname.split("/");
            const idx = path[3];

            // 유기동물 정보 조회 함수 
            async function animalInfo() {
                const result = await API.get('/care/api/animals/' + idx + '/adoption');
                if (result.status != 200) {
                    history.back();
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
                document.getElementById("createdAt").textContent = animal.createdAt;
                document.getElementById("shelterName").textContent = animal.shelterName;

            }
            // 입양 상담 신청 함수 
            async function submitAdoption() {
                const form = getJsonFromForm("adoptionForm");
                const result = await API.post('/care/api/animals/' + idx + '/adoption', form);

                if (result.status === 201) {
                    alert(result.message);
                    history.back();
                    return;
                }
            }
        </script>
    </head>

    <body id="body" style="display: none;">
        <%@ include file="/WEB-INF/views/common/index/indexHeader.jsp" %>
        <div class="animal-info">
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
                <div><span>등록일:</span> <span id="createdAt"></span></div>
                <div><span>보호소명:</span> <span id="shelterName"></span></div>
            </div>
            <hr>
        </div>
        <form id="adoptionForm" method="post" onsubmit="submitAdoption(); return false;">
            <div>
                <label>이름:</label>
                <input type="text" name="name" required>
            </div>

            <div>
                <label>이메일:</label>
                <input type="email" name="email" required>
            </div>

            <div>
                <label>전화번호:</label>
                <input type="tel" name="tel" required placeholder="01012345678">
            </div>

            <div>
                <label>우편번호:</label>
                <input type="number" name="zipCode" required>
            </div>

            <div>
                <label>주소:</label>
                <input type="text" name="address" required>
            </div>

            <div>
                <label>상세주소:</label>
                <input type="text" name="addressDetail" required>
            </div>

            <div>
                <label>반려동물 유무:</label>
                <select name="hasPet" required>
                    <option value="1">있음</option>
                    <option value="0">없음</option>
                </select>
            </div>

            <div>
                <label>상담 희망일시:</label>
                <input type="datetime-local" name="consultedAt" required>
            </div>

            <div>
                <label>상세 내용:</label>
                <textarea name="description" rows="4" required></textarea>
            </div>

            <div>
                <input type="submit" value="입양 상담 신청">
            </div>
        </form>

        <script>
            window.addEventListener("DOMContentLoaded", animalInfo);
        </script>
    </body>

    </html>