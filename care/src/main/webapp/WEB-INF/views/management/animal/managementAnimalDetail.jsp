<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
    <!DOCTYPE html>
    <html>

    <head>
        <meta charset="UTF-8">
        <title>Insert title here</title>
        <script src="${pageContext.request.contextPath}/resources/web/common/commonUtils.js"></script>
        <script>
            const idx = location.pathname.split("/").pop();
            // 유기동물 상세 조회 함수 
            async function animalDetail() {
                const result = await API.get('/care/api/management/animals/' + idx);
                if (result.status != 200) {
                    location.href = '/care/management/animals';
                    return;
                }
                document.getElementById("body").style.display = "block";
                const animal = result.data;
                // 조회 데이터
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
                document.getElementById("description").innerText = animal.description;

                // 수정 폼 데이터
                document.getElementById("editIdx").textContent = animal.idx;
                document.getElementById("preview").src = '${pageContext.request.contextPath}' + animal.imagePath;
                document.getElementById("editName").value = animal.name;
                document.getElementById("editAdoptionStatusIdx").value = animal.adoptionStatusIdx;
                document.getElementById("editAnimalBreedIdx").value = animal.breedIdx;
                document.getElementById("editAnimalPersonalityIdx").value = animal.personalityIdx;
                document.getElementById("editGender").value = animal.gender;
                document.getElementById("editAge").value = animal.age;
                document.getElementById("editSize").value = animal.size;
                document.getElementById("editNeuter").value = animal.neuter;
                document.getElementById("editCreatedAt").textContent = animal.createdAt;
                document.getElementById("editDescription").value = animal.description;

            }
            // 수정 모드 함수
            async function viewUpdateFrom() {
                document.getElementById("animalInfo").style.display = "none";
                document.getElementById("updateForm").style.display = "block";
            }
            // 조회 모드 함수
            async function viewAnimalInfo() {
                document.getElementById("animalInfo").style.display = "block";
                document.getElementById("updateForm").style.display = "none";
            }
            // 미리보기 함수
            function previewImage(event) {
                const file = event.target.files[0];
                const preview = document.getElementById('preview');

                if (file && file.type.startsWith('image/')) {
                    const reader = new FileReader();
                    reader.onload = function (e) {
                        preview.src = e.target.result;
                        preview.style.display = 'block';
                    };
                    reader.readAsDataURL(file);
                } else {
                    preview.style.display = 'none';
                    preview.src = '';
                }
            }
            // 수정 실행 함수
            async function animalSubmit() {
                const form = getJsonFromForm("animalForm");
                const result = await API.put('/care/api/management/animals/' + idx, form);

                const uploadForm = getFormDataFromForm("animalImageForm");

                const fileInput = document.getElementById('image');
                if (fileInput.files && fileInput.files.length > 0) {
                    const uploadResult = await FileAPI.upload("/care/api/management/animals/upload/" + idx + "/update", uploadForm);
                    if (uploadResult.status != 201) {
                        alert("업로드 오류 : 다시 진행해주세요.");
                        return;
                    }
                }

                if (result.status === 200) {
                    alert(result.message);
                    location.href = '/care/management/animals/' + idx;
                } else {
                    return;
                }
            }

            async function animalDelete() {
                if (!confirm("정말 삭제하시겠습니까?")) {
                    return;
                }
                const result = await API.delete("/care/api/management/animals/" + idx);
                if (result.status === 200) {
                    alert(result.message);
                    location.href = "/care/management/animals";
                }
            }

        </script>
    </head>

    <body id="body" style="display: none;">
        <%@ include file="/WEB-INF/views/common/index/indexHeader.jsp" %>
            <h1>유기동물 관리</h1>
            <div>
                <input type="button" value="유기동물 관리" onclick="location.href='/care/management/animals'">
                <input type="button" value="유기동물 등록" onclick="location.href='/care/management/animals/form'">
                <input type="button" value="입양상담 관리">
            </div>
            <div id="animalInfo" class="animal-detail">
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
                    <div>
                        <input type="button" id="update" value="수정" onclick="viewUpdateFrom()">
                        <input type="button" id="delete" value="삭제" onclick="animalDelete()">
                    </div>
                    <hr>
                    <div><span>소개</span><br><span id="description"></span></div>
                </div>
            </div>
            <div id="updateForm" style="display: none;">
                <form id="animalImageForm" enctype="multipart/form-data">
                    <img id="preview" width="200" height="200" alt="미리보기 이미지">
                    <label for="image">사진 업로드:</label>
                    <input type="file" id="image" name="files" accept="image/*" onchange="previewImage(event)">
                    <br><br>
                </form>
                <form id="animalForm" onsubmit="; return false;">
                    <label for="editName">이름:</label>
                    <input type="text" id="editName" name="name" required><br>
                    <div><span>관리번호:</span> <span id="editIdx"></span></div>

                    <label for="editGender">성별:</label>
                    <select id="editGender" name="gender" required>
                        <option value="M">수컷</option>
                        <option value="F">암컷</option>
                    </select><br>

                    <label for="editAge">나이:</label>
                    <input type="number" id="editAge" name="age" min="0" required><br>

                    <label for="editSize">크기(kg):</label>
                    <input type="number" id="editSize" name="size" min="0" required><br>

                    <label for="editNeuter">중성화 여부:</label>
                    <select id="editNeuter" name="neuter" required>
                        <option value="1">예</option>
                        <option value="0">아니오</option>
                    </select><br>

                    <label for="editAnimalBreedIdx">품종:</label>
                    <select id="editAnimalBreedIdx" name="animalBreedIdx" required>
                        <optgroup label="개">
                            <option value="1">푸들</option>
                            <option value="2">말티즈</option>
                            <option value="3">포메라니안</option>
                            <option value="4">진돗개</option>
                            <option value="5">요크셔테리어</option>
                            <option value="6">비숑프리제</option>
                            <option value="7">치와와</option>
                            <option value="8">웰시코기</option>
                            <option value="9">믹스견</option>
                        </optgroup>
                        <optgroup label="고양이">
                            <option value="10">코리안숏헤어</option>
                            <option value="11">러시안블루</option>
                            <option value="12">스코티시폴드</option>
                            <option value="13">먼치킨</option>
                            <option value="14">페르시안</option>
                            <option value="15">랙돌</option>
                            <option value="16">노르웨이숲</option>
                            <option value="17">믹스묘</option>
                        </optgroup>
                        <optgroup label="기타">
                            <option value="18">햄스터</option>
                            <option value="19">토끼</option>
                            <option value="20">기니피그</option>
                            <option value="21">패럿</option>
                            <option value="22">고슴도치</option>
                            <option value="23">거북이</option>
                            <option value="24">도마뱀</option>
                            <option value="25">기타</option>
                        </optgroup>
                    </select><br>

                    <label for="editAnimalPersonalityIdx">성격:</label>
                    <select id="editAnimalPersonalityIdx" name="animalPersonalityIdx" required>
                        <option value="1">온순함</option>
                        <option value="2">활발함</option>
                        <option value="3">겁많음</option>
                        <option value="4">사나움</option>
                        <option value="5">낯가림</option>
                        <option value="6">애교많음</option>
                        <option value="7">호기심많음</option>
                        <option value="8">사람좋아함</option>
                        <option value="9">소심함</option>
                        <option value="10">지능높음</option>
                    </select><br>

                    <label for="editAdoptionStatusIdx">입양 상태:</label>
                    <select id="editAdoptionStatusIdx" name="adoptionStatusIdx" required>
                        <option value="1">입양가능</option>
                        <option value="2">입양대기</option>
                        <option value="3">입양완료</option>
                    </select><br>
                    <div><span>등록일:</span> <span id="editCreatedAt"></span></div>
                    <input type="submit" value="수정" onclick="animalSubmit()">
                    <input type="button" value="취소" onclick="viewAnimalInfo()">
                    <hr>
                    <label for="editDescription">소개:</label><br>
                    <textarea id="editDescription" name="description" rows="5" cols="50" required></textarea><br>
                </form>
            </div>
            <input type="button" value="목록" onclick="location.href='/care/management/animals'">
            <script>
                window.addEventListener("DOMContentLoaded", animalDetail);
            </script>
    </body>

    </html>