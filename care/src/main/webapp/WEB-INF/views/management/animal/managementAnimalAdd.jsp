<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
    <!DOCTYPE html>
    <html>

    <head>
        <meta charset="UTF-8">
        <title>Insert title here</title>
        <style>
            .page-wrapper {
                max-width: 1200px;
                margin: 0 auto;
                padding: 0 20px;
            }

            .footer-wrapper {
                max-width: 1200px;
                margin: 0 auto;
                padding: 40px 20px 60px;
                text-align: center;
            }

            .info-form {
                flex: 1 1 0;
            }

            .form-wrapper {
                max-width: 850px;
                margin: 0 auto;
                padding: 30px;
                padding-bottom: 80px;
                font-family: 'Pretendard', sans-serif;
            }

            .shelter-box {
                border: 1px solid #ddd;
                border-radius: 12px;
                padding: 20px 30px;
                margin-bottom: 40px;
                background: #fafafa;
            }

            .shelter-box h2 {
                margin-bottom: 12px;
                font-size: 20px;
                font-weight: bold;
            }

            .animal-register-container {
                display: flex;
                gap: 40px;
                align-items: flex-start;
            }

            .image-upload {
                flex: 0 0 390px;
                display: flex;
                flex-direction: column;
                align-items: center;
            }

            .image-upload img {
                width: 100%;
                aspect-ratio: 1 / 1;
                object-fit: cover;
                border-radius: 12px;
                margin-bottom: 12px;
                background: #eaeaea;
                display: block;
            }

            .info-form {
                flex: 1;
                display: grid;
                grid-template-columns: 120px 1fr;
                gap: 12px 20px;
                align-items: center;
            }

            .info-form label {
                text-align: right;
                font-weight: 600;
            }

            .info-form input,
            .info-form select,
            .info-form textarea {
                padding: 8px 10px;
                border: 1px solid #ccc;
                border-radius: 6px;
                font-size: 14px;
                width: 100%;
                max-width: 500px;
                /* 추가 */
                box-sizing: border-box;
            }

            .info-form textarea {
                grid-column: span 2;
                resize: vertical;
                width: 100% !important;
                max-width: 100% !important;
                box-sizing: border-box;
            }

            .form-buttons {
                grid-column: span 2;
                text-align: center;
                margin-top: 20px;
            }

            .form-buttons input {
                padding: 10px 28px;
                margin: 0 10px;
                border-radius: 8px;
                border: none;
                font-weight: 600;
                cursor: pointer;
            }

            .form-buttons input[type="submit"] {
                background-color: #3acdb2;
                color: white;
            }

            .form-buttons input[type="reset"] {
                background-color: #ddd;
                color: black;
            }

            .form-buttons {
                grid-column: span 2;
                margin-top: 20px;
                display: flex;
                justify-content: center;
                align-items: center;
                gap: 12px;
                width: 100%;
            }

            .form-buttons input {
                padding: 8px 20px;
                font-size: 14px;
                border-radius: 6px;
                border: none;
                font-weight: 500;
                cursor: pointer;
            }

            .upload-label {
                display: block;
                width: 100%;
                max-width: 360px;
                aspect-ratio: 1/1;
                background: #eaeaea;
                border-radius: 12px;
                position: relative;
                overflow: hidden;
                cursor: pointer;
            }


            /* ‘+’ 아이콘 */
            .upload-label::before {
                content: '+';
                position: absolute;
                inset: 0;
                display: flex;
                align-items: center;
                justify-content: center;
                font-size: 68px;
                font-weight: 700;
                color: #9d9d9d;
                transition: color .2s, background .2s;
            }

            /* 호버 시 색상 변화 */
            .upload-label:hover::before {
                color: #3acdb2;
            }

            /* 이미지가 올라오면 ‘+’ 숨김 */
            .upload-label.has-image::before {
                display: none;
            }

            .upload-placeholder {
                width: 100%;
                height: 100%;
                object-fit: cover;
                display: none;
            }

            .upload-label.has-image .upload-placeholder {
                display: block;
            }

            .submenu-buttons {
                display: flex;
                justify-content: center;
                gap: 12px;
                margin-bottom: 30px;
                margin-top: 30px;
            }

            .submenu-buttons input[type="button"] {
                background-color: #f6f6f6;
                border: 1px solid #ccc;
                border-radius: 8px;
                padding: 10px 24px;
                font-size: 15px;
                font-weight: 600;
                color: #333;
                cursor: pointer;
                transition: all 0.2s ease;
            }

            .submenu-buttons input[type="button"]:hover {
                background-color: #3acdb2;
                color: #fff;
                border-color: #3acdb2;
            }

            .submenu-buttons input[type="button"].active {
                background-color: #3acdb2;
                color: #fff;
                border-color: #3acdb2;
                font-weight: bold;
            }
        </style>
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
                const label = document.getElementById('uploadLabel');

                if (file && file.type.startsWith('image/')) {
                    const reader = new FileReader();
                    reader.onload = function (e) {
                        preview.src = e.target.result;
                        label.classList.add('has-image');
                        preview.style.display = 'block'; // 명시적으로 표시
                    };
                    reader.readAsDataURL(file);
                } else {
                    preview.removeAttribute('src');
                    preview.style.display = 'none'; // 완전히 숨김
                    label.classList.remove('has-image');
                }
            }
        </script>
    </head>

    <body id="body" style="display: none;">
        <%@ include file="/WEB-INF/views/common/management/managementHeader.jsp" %>
            <div class="page-wrapper"></div>
            <div class="submenu-buttons">
                <input type="button" value="유기동물 조회" onclick="location.href='/care/management/animals'">
                <input type="button" value="유기동물 등록" class="active"
                    onclick="location.href='/care/management/animals/form'">
                <input type="button" value="입양상담 관리" onclick="location.href='/care/management/animals/adoptions'">
            </div>
            <div class="form-wrapper">
                <section class="shelter-box">
                    <h2 id="shelterName">보호소명</h2>
                    <div><span>주소: </span><span id="shelterAddress"></span> <span id="shelterAddressDetail"></span>
                    </div>
                    <div><span>보호소 유형: </span><span id="shelterType"></span></div>
                    <div><span>담당자: </span><span id="shelterPersonName"></span></div>
                    <div><span>연락처: </span><span id="shelterTel"></span></div>
                    <div><span>이메일: </span><span id="shelterEmail"></span></div>
                </section>

                <div class="animal-register-container">

                    <form id="animalImageForm" enctype="multipart/form-data" class="image-upload">
                        <label for="image" class="upload-label" id="uploadLabel">
                            <img id="preview" class="upload-placeholder" alt="">
                        </label>
                        <input type="file" id="image" name="files" accept="image/*" onchange="previewImage(event)"
                            style="display:none;">
                    </form>

                    <form id="animalForm" onsubmit="animalSubmit(); return false;" class="info-form">
                        <label for="name">이름 </label>
                        <input type="text" id="name" name="name" required>

                        <label for="adoptionStatusIdx">입양 상태 </label>
                        <select id="adoptionStatusIdx" name="adoptionStatusIdx" required>
                            <option value="1">입양가능</option>
                            <option value="2">입양대기</option>
                        </select>

                        <label for="animalBreedIdx">품종 </label>
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
                        </select>

                        <label for="animalPersonalityIdx">성격 </label>
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
                        </select>

                        <label for="gender">성별 </label>
                        <select id="gender" name="gender" required>
                            <option value="M">수컷</option>
                            <option value="F">암컷</option>
                        </select>

                        <label for="age">나이 </label>
                        <input type="number" id="age" name="age" min="0" required>

                        <label for="size">몸무게(kg) </label>
                        <input type="number" id="size" name="size" min="0" required>

                        <label for="neuter">중성화 여부 </label>
                        <select id="neuter" name="neuter" required>
                            <option value="1">예</option>
                            <option value="0">아니오</option>
                        </select>

                        <textarea id="description" name="description" rows="5" cols="50" required
                            placeholder="동물의 소개글을 입력해주세요."></textarea>

                        <div class="form-buttons">
                            <input type="submit" value="등록">
                            <input type="reset" value="초기화">
                        </div>
                    </form>
                </div>
            </div>
            <div class="footer-wrapper">
                <%@ include file="/WEB-INF/views/common/index/indexFooter.jsp" %>
            </div>
            <script>
                window.addEventListener("DOMContentLoaded", shelterDetail());
            </script>
    </body>

    </html>