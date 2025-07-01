<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
    <!DOCTYPE html>
    <html>

    <head>
        <meta charset="UTF-8">
        <title>Insert title here</title>
        <script src="${pageContext.request.contextPath}/resources/web/common/commonUtils.js"></script>
        <script>
            // 로그인한 보호시설 조회 함수
            async function shelterDetail() {
                const result = await API.get('/care/api/management/animals/shelter');
                if (result.errorCode === 401 || result.errorCode === 403) {
                    location.href = "/care/index";
                    return;
                }

                if (result.status != 200) {
                    location.href = '/care/management/animals';
                    return;
                }

                document.getElementById("body").style.display = "block";
                const shelter = result.data;

                document.getElementById("shelterName").textContent = shelter.shelterName;
                document.getElementById("shelterPersonName").textContent = shelter.shelterPersonName;
                document.getElementById("shelterTel").textContent = shelter.shelterTel;
                document.getElementById("shelterAddress").textContent = shelter.shelterAddress;
                document.getElementById("shelterAddressDetail").textContent = shelter.shelterAddressDetail;
                document.getElementById("shelterEmail").textContent = shelter.shelterEmail;
                document.getElementById("shelterType").textContent = shelter.shelterType;
            }

            // 유기동물 등록 함수
            async function animalSubmit() {
                const fileInput = document.getElementById('image');
                if (!fileInput.files || fileInput.files.length === 0) {
                    alert('사진업로드는 필수입니다.');
                    return;
                }
                const form = getJsonFromForm("animalForm");
                const result = await API.post('/care/api/management/animals', form);

                const uploadForm = getFormDataFromForm("animalImageForm");
                const uploadResult = await FileAPI.upload("/care/api/management/animals/upload/" + result.data.createdIdx + "/insert", uploadForm);
                if (uploadResult.status != 201) {
                    alert("업로드 오류 : 다시 진행해주세요.");
                    return;
                }

                if (result.status === 201) {
                    alert(result.message);
                    location.href = '/care/management/animals';
                } else {
                    return;
                }
            }

            // 이미지 미리보기 함수
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
        </script>
    </head>

    <body id="body" style="display: none;">
       <%@ include file="/WEB-INF/views/common/management/managementHeader.jsp" %>
            <h1>유기동물 등록</h1>
            <div>
                <input type="button" value="유기동물 관리" onclick="location.href='/care/management/animals'">
                <input type="button" value="유기동물 등록" onclick="location.href='/care/management/animals/form'">
                <input type="button" value="입양상담 관리" onclick="location.href='/care/management/animals/adoptions'">
            </div>
            <div class="shelter-info">
                <h2 id="shelterName">보호소명</h2>
                <div><span>주소: </span><span id="shelterAddress"></span> <span id="shelterAddressDetail"></span></div>
                <div><span>보호소 유형: </span><span id="shelterType"></span></div>
                <div><span>담당자: </span><span id="shelterPersonName"></span></div>
                <div><span>연락처: </span><span id="shelterTel"></span></div>
                <div><span>이메일: </span><span id="shelterEmail"></span></div>
            </div>
            <hr>
            <form id="animalImageForm" enctype="multipart/form-data">
                <img id="preview" width="200" height="200" style="display:none;" alt="미리보기 이미지">
                <label for="image">사진 업로드:</label>
                <input type="file" id="image" name="files" accept="image/*" onchange="previewImage(event)">
                <br><br>
            </form>
            <form id="animalForm" onsubmit="animalSubmit(); return false;">
                <label for="name">이름:</label>
                <input type="text" id="name" name="name" required><br>

                <label for="adoptionStatusIdx">입양 상태:</label>
                <select id="adoptionStatusIdx" name="adoptionStatusIdx" required>
                    <option value="1">입양가능</option>
                    <option value="2">입양대기</option>
                </select><br>

                <label for="animalBreedIdx">품종:</label>
                <select id="animalBreedIdx" name="animalBreedIdx" required>
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

                <label for="animalPersonalityIdx">성격:</label>
                <select id="animalPersonalityIdx" name="animalPersonalityIdx" required>
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

                <label for="gender">성별:</label>
                <select id="gender" name="gender" required>
                    <option value="M">수컷</option>
                    <option value="F">암컷</option>
                </select><br>

                <label for="age">나이:</label>
                <input type="number" id="age" name="age" min="0" required><br>

                <label for="size">몸무게(kg):</label>
                <input type="number" id="size" name="size" min="0" required><br>

                <label for="neuter">중성화 여부:</label>
                <select id="neuter" name="neuter" required>
                    <option value="1">예</option>
                    <option value="0">아니오</option>
                </select><br>

                <label for="description">설명:</label><br>
                <textarea id="description" name="description" rows="5" cols="50" required></textarea><br>

                <input type="submit" value="등록">
                <input type="reset" value="초기화">
            </form>
            <%@ include file="/WEB-INF/views/common/index/indexFooter.jsp" %>
            <script>
                window.addEventListener("DOMContentLoaded", shelterDetail());
            </script>
    </body>

    </html>