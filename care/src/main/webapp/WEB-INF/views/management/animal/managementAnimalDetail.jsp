<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
    <!DOCTYPE html>
    <html>

    <head>
        <meta charset="UTF-8">
        <title>유기동물 상세 정보</title>
        <style>
            /* ───────── 전체 래퍼 ───────── */
            .animal-detail-wrapper {
                max-width: 1200px;
                margin: 40px auto;
            }

            .animal-detail {
                display: flex;
                gap: 32px;
                font-size: 15px;
                line-height: 1.55;
            }

            /* ───────── 좌측 사진 ───────── */
            .animal-image {
                width: 420px;
                /* 고정 너비 */
                height: 420px;
                /* ↲ 고정 높이까지 지정  */
                position: relative;
                background: #e5e5e5;
                border-radius: 12px;
                overflow: hidden;
                flex-shrink: 0;
            }

            /* 1:1 비율 유지용 가짜 박스 */
            .animal-image::before {
                content: "";
                display: block;
                padding-top: 100%;
            }

            /* 실 이미지(뷰·프리뷰 공통) */
            .animal-image img {
                position: absolute;
                inset: 0;
                /* top:0; right:0; bottom:0; left:0; */
                width: 100%;
                height: 100%;
                object-fit: cover;
            }

            /* 편집 오버레이 */
            .animal-image .image-edit-overlay {
                position: absolute;
                inset: 0;
                background-color: rgba(0, 0, 0, 0.5);
                color: #fff;
                justify-content: center;
                align-items: center;
                cursor: pointer;
                font-weight: 600;
                opacity: 0;
                transition: opacity 0.2s;
                display: flex;
                /* flex 정중앙 */
            }

            .is-editing .animal-image:hover .image-edit-overlay {
                opacity: 1;
            }

            #image-upload {
                display: none;
            }

            /* ───────── 우측 정보 영역 ───────── */
            .animal-info {
                flex: 1 1 0;
                display: grid;
                grid-template-columns: 120px 1fr;
                row-gap: 8px;
                column-gap: 12px;
                align-items: center;
            }

            .animal-info h2,
            .animal-info .full-span {
                grid-column: 1 / 3;
            }

            .animal-info h2 {
                margin: 0 0 12px;
                font-size: 22px;
                font-weight: 700;
            }

            .animal-info .label {
                font-weight: 600;
                color: #444;
            }

            .animal-info .btn-group {
                display: flex;
                gap: 12px;
                margin-top: 18px;
            }

            .animal-info input[type="button"],
            .animal-info input[type="submit"] {
                width: 150px;
                height: 40px;
                border: none;
                border-radius: 20px;
                background: #d9d9d9;
                cursor: pointer;
                font-weight: 600;
            }

            .animal-info input[type="button"]:hover,
            .animal-info input[type="submit"]:hover {
                background: #c4c4c4;
            }

            /* Form elements */
            .animal-info input[type="text"],
            .animal-info input[type="number"],
            .animal-info select {
                width: 100%;
                padding: 6px 10px;
                border: 1px solid #ccc;
                border-radius: 6px;
                font-size: 15px;
            }

            /* ───────── 소개 ───────── */
            .description-wrapper {
                max-width: 1200px;
                margin: 20px auto 0;
            }

            .description-wrapper .label {
                font-weight: 600;
                color: #444;
                margin-bottom: 6px;
            }

            #description-view,
            #description-edit {
                padding: 14px 16px;
                background: #f6f6f8;
                border-radius: 12px;
                width: 100%;
                line-height: 1.6;
                white-space: pre-line;
                box-sizing: border-box;
                min-height: 100px;
            }

            #description-edit {
                border: 1px solid #ccc;
                resize: vertical;
            }

            /* ───────── 메뉴 버튼 ───────── */
            .submenu-buttons {
                display: flex;
                justify-content: center;
                gap: 12px;
                margin: 30px 0;
            }

            .submenu-buttons input[type="button"] {
                background: #f6f6f6;
                border: 1px solid #ccc;
                border-radius: 8px;
                padding: 10px 24px;
                font-size: 15px;
                font-weight: 600;
                color: #333;
                cursor: pointer;
                transition: all 0.2s;
            }

            .submenu-buttons input[type="button"]:hover {
                background: #3acdb2;
                color: #fff;
                border-color: #3acdb2;
            }

            .submenu-buttons input[type="button"].active {
                background: #3acdb2;
                color: #fff;
                border-color: #3acdb2;
                font-weight: 700;
            }

            /* ───────── 조회/수정 모드 토글 ───────── */
            .edit-mode {
                display: none;
            }

            /* 기본 숨김  */
            .view-mode {}

            /* 기본 노출  */

            /* 버튼 노출 제어 */
            .animal-detail-wrapper .btn-group.view-mode,
            .animal-detail-wrapper .btn-group.edit-mode {
                display: none;
            }

            .animal-detail-wrapper:not(.is-editing) .btn-group.view-mode {
                display: flex;
            }

            .animal-detail-wrapper.is-editing .btn-group.edit-mode {
                display: flex;
            }

            /* 수정 모드일 때 필드 표시 전환 */
            .animal-detail-wrapper.is-editing .view-mode {
                display: none;
            }

            .animal-detail-wrapper.is-editing .edit-mode {
                display: block;
            }

            .animal-detail-wrapper.is-editing .image-edit-overlay.edit-mode {
                display: flex;
            }

            /* 오버레이 기본 숨김 처리 */
            .image-edit-overlay.edit-mode {
                display: none;
            }

            /* 수정 모드일 때만 표시 */
            .animal-detail-wrapper.is-editing .image-edit-overlay.edit-mode {
                display: flex;
            }
        </style>

        <script src="${pageContext.request.contextPath}/resources/web/common/commonUtils.js"></script>
        <script>
            const idx = location.pathname.split("/").pop();

            // 유기동물 상세 조회 함수
            async function animalDetail() {
                try {
                    const result = await API.get('/care/api/management/animals/' + idx);
                    if (result.errorCode === 401 || result.errorCode === 403) {
                        location.href = "/care/index";
                        return;
                    }

                    if (result.status != 200) {
                        location.href = '/care/management/animals';
                        return;
                    }
                    document.getElementById("body").style.display = "block";
                    const animal = result.data;
                    populateFields(animal);
                } catch (error) {
                    alert("데이터를 불러오는 중 오류가 발생했습니다.");
                    console.error(error);
                }
            }

            function populateFields(animal) {
                const imagePath = '${pageContext.request.contextPath}' + animal.imagePath;

                // 이미지
                document.getElementById("animalImage").src = imagePath;
                document.getElementById("preview").src = imagePath;

                // 이름
                document.getElementById("animalName").textContent = animal.name;
                document.getElementById("editName").value = animal.name;

                // 관리번호, 등록일
                document.getElementById("idx").textContent = animal.idx;
                document.getElementById("createdAt").textContent = animal.createdAt;

                // 성별
                document.getElementById("gender-view").textContent = animal.gender === "M" ? "남" : "여";
                document.getElementById("editGender").value = animal.gender;

                // 나이
                document.getElementById("age-view").textContent = animal.age + "세";
                document.getElementById("editAge").value = animal.age;

                // 크기
                document.getElementById("size-view").textContent = animal.size + "kg";
                document.getElementById("editSize").value = animal.size;

                // 중성화
                document.getElementById("neuter-view").textContent = animal.neuter === 1 ? "O" : "X";
                document.getElementById("editNeuter").value = animal.neuter;

                // 품종
                document.getElementById("breed-view").textContent = animal.breed;
                document.getElementById("editAnimalBreedIdx").value = animal.breedIdx;

                // 종류
                document.getElementById("type-view").textContent = animal.type;

                // 성격
                document.getElementById("personality-view").textContent = animal.personality;
                document.getElementById("editAnimalPersonalityIdx").value = animal.personalityIdx;

                // 입양상태
                document.getElementById("adoptionStatus-view").textContent = animal.adoptionStatus;
                document.getElementById("editAdoptionStatusIdx").value = animal.adoptionStatusIdx;

                // 소개
                document.getElementById("description-view").innerText = animal.description;
                document.getElementById("description-edit").value = animal.description;
            }

            // 수정 모드 진입
            function viewUpdateFrom() {
                document.getElementById("animalDetailWrapper").classList.add("is-editing");
            }

            // 조회 모드 복귀
            function viewAnimalInfo() {
                document.getElementById("animalDetailWrapper").classList.remove("is-editing");
                // 취소 시, 원래 이미지로 되돌리기 위해 데이터를 다시 로드
                animalDetail();
            }

            // 이미지 미리보기
            function previewImage(event) {
                const file = event.target.files[0];
                const preview = document.getElementById('preview');
                if (file) {
                    preview.src = URL.createObjectURL(file);
                }
            }

            // 수정 제출
            async function animalSubmit() {
                const form = getJsonFromForm("animalForm");
                const result = await API.put('/care/api/management/animals/' + idx, form);

                const fileInput = document.getElementById('image-upload');
                if (fileInput.files && fileInput.files.length > 0) {
                    const uploadForm = new FormData();
                    uploadForm.append('files', fileInput.files[0]);
                    const uploadResult = await FileAPI.upload("/care/api/management/animals/upload/" + idx + "/update", uploadForm);
                    if (uploadResult.status != 201) {
                        alert("업로드 오류 : 다시 진행해주세요.");
                        return;
                    }
                }

                if (result.status === 200) {
                    alert(result.message);
                    // 수정 후 데이터 다시 로드하고 조회 모드로 전환
                    await animalDetail();
                    viewAnimalInfo();
                } else {
                    alert(result.message || "수정에 실패했습니다.");
                }
            }

            // 삭제
            async function animalDelete() {
                if (!confirm("정말 삭제하시겠습니까?")) {
                    return;
                }
                const result = await API.delete("/care/api/management/animals/" + idx);
                if (result.status === 200) {
                    alert(result.message);
                    location.href = "/care/management/animals";
                } else {
                    alert(result.message || "삭제에 실패했습니다.");
                }
            }
        </script>
    </head>

    <body id="body" style="display: none;">
        <%@ include file="/WEB-INF/views/common/management/managementHeader.jsp" %>
            <div class="submenu-buttons">
                <input type="button" value="유기동물 조회" class="active" onclick="location.href='/care/management/animals'">
                <input type="button" value="유기동물 등록" onclick="location.href='/care/management/animals/form'">
                <input type="button" value="입양상담 관리" onclick="location.href='/care/management/animals/adoptions'">
            </div>

            <form id="animalForm" onsubmit="animalSubmit(); return false;">
                <div id="animalDetailWrapper" class="animal-detail-wrapper">
                    <div class="animal-detail">
                        <div class="animal-image">
                            <img id="animalImage" src="" class="view-mode">
                            <img id="preview" src="" class="edit-mode">
                            <label for="image-upload" class="image-edit-overlay edit-mode">사진 변경</label>
                            <input type="file" id="image-upload" name="files" accept="image/*"
                                onchange="previewImage(event)">
                        </div>

                        <div class="animal-info">
                            <h2 id="animalName" class="view-mode full-span"></h2>
                            <input type="text" id="editName" name="name" required class="edit-mode full-span">

                            <div class="label">관리번호</div>
                            <div><span id="idx"></span></div>

                            <div class="label">성별</div>
                            <div>
                                <span id="gender-view" class="view-mode"></span>
                                <select id="editGender" name="gender" required class="edit-mode">
                                    <option value="M">수컷</option>
                                    <option value="F">암컷</option>
                                </select>
                            </div>

                            <div class="label">나이</div>
                            <div>
                                <span id="age-view" class="view-mode"></span>
                                <input type="number" id="editAge" name="age" min="0" required class="edit-mode">
                            </div>

                            <div class="label">크기</div>
                            <div>
                                <span id="size-view" class="view-mode"></span>
                                <select id="editSize" name="size" required class="edit-mode">
                                    <option value="1">소형</option>
                                    <option value="2">중형</option>
                                    <option value="3">대형</option>
                                </select>
                            </div>

                            <div class="label">중성화</div>
                            <div>
                                <span id="neuter-view" class="view-mode"></span>
                                <select id="editNeuter" name="neuter" required class="edit-mode">
                                    <option value="1">O</option>
                                    <option value="0">X</option>
                                </select>
                            </div>

                            <div class="label">품종</div>
                            <div>
                                <span id="breed-view" class="view-mode"></span>
                                <select id="editAnimalBreedIdx" name="animalBreedIdx" required class="edit-mode">
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
                            </div>

                            <div class="label">종류</div>
                            <div><span id="type-view"></span></div>

                            <div class="label">성격</div>
                            <div>
                                <span id="personality-view" class="view-mode"></span>
                                <select id="editAnimalPersonalityIdx" name="animalPersonalityIdx" required
                                    class="edit-mode">
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
                            </div>

                            <div class="label">입양상태</div>
                            <div>
                                <span id="adoptionStatus-view" class="view-mode"></span>
                                <select id="editAdoptionStatusIdx" name="adoptionStatusIdx" required class="edit-mode">
                                    <option value="1">입양가능</option>
                                    <option value="2">입양대기</option>
                                    <option value="3">입양완료</option>
                                </select>
                            </div>

                            <div class="label">등록일</div>
                            <div><span id="createdAt"></span></div>

                            <div class="btn-group full-span view-mode">
                                <input type="button" value="수정" onclick="viewUpdateFrom()">
                                <input type="button" value="삭제" onclick="animalDelete()">
                            </div>
                            <div class="btn-group full-span edit-mode">
                                <input type="submit" value="저장">
                                <input type="button" value="취소" onclick="viewAnimalInfo()">
                            </div>
                        </div>
                    </div>

                    <div class="description-wrapper">
                        <div class="label">소개</div>
                        <div id="description-view" class="view-mode"></div>
                        <textarea id="description-edit" name="description" class="edit-mode"></textarea>
                    </div>
                </div>
            </form>

            <div class="submenu-buttons" style="margin-top: 0;">
                <input type="button" value="목록" onclick="location.href='/care/management/animals'">
            </div>

            <%@ include file="/WEB-INF/views/common/index/indexFooter.jsp" %>
                <script>
                    window.addEventListener("DOMContentLoaded", animalDetail);
                </script>
    </body>

    </html>