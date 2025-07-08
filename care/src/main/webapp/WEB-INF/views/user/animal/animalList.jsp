<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
    <!DOCTYPE html>
    <html>

    <head>
        <meta charset="UTF-8">
        <title>당신에게 다시가는 길 - 유기동물 목록</title>
        <style>
            .container {
                max-width: 1200px;
                margin: 0 auto;
                padding: 20px;
            }

            .header-title {
                font-size: 28px;
                font-weight: bold;
                margin-top: 50px;
                margin-bottom: 20px;
                text-align: center;
                color: #3ACDB2;
            }

            .title-detail {
                text-align: center;
                margin-bottom: 40px;
                color: #666;
                font-size: 16px;
            }

            #searchForm {
                display: grid;
                grid-template-columns: repeat(6, 1fr);
                gap: 16px 20px;
                background: #f6f6f8;
                padding: 26px 28px;
                border-radius: 14px;
                font-size: 15px;
                align-items: end;
                transition: max-height .3s ease;
                overflow: hidden;
            }

            .form-item {
                display: flex;
                flex-direction: column;
                font-weight: 600;
                color: #333;
            }

            #searchForm select,
            #searchForm input[type="text"],
            #searchForm input[type="number"] {
                width: 100%;
                margin-top: 6px;
                height: 44px;
                padding: 0 12px;
                border: 1px solid #ccc;
                border-radius: 8px;
                font-size: 15px;
                background: #fff;
                box-sizing: border-box;
            }

            .name-field {
                grid-column: 3 / 6;
            }

            .btn-area {
                grid-column: 6 / 7;
                display: flex;
                justify-content: flex-end;
                align-items: flex-end;
                gap: 12px;
            }

            #searchForm input[type="submit"] {
                background: #3acdb2;
                color: #fff;
                border: none;
                padding: 0 34px;
                height: 44px;
                border-radius: 8px;
                font-size: 16px;
                font-weight: 600;
                cursor: pointer;
                transition: background 0.2s;
            }

            #searchForm input[type="submit"]:hover {
                background: #2ea992;
            }

            .reset-link {
                font-size: 14px;
                color: #666;
                text-decoration: underline;
                cursor: pointer;
            }

            #animalListContainer {
                margin-top: 18px;
                display: grid;
                grid-template-columns: repeat(5, 1fr);
                gap: 24px;
            }

            #animalListContainer>div {
                position: relative;
            }

            .badge {
                position: absolute;
                top: 12px;
                left: 12px;
                background-color: #3acdb2;
                color: white;
                padding: 4px 10px;
                border-radius: 8px;
                font-size: 12px;
                font-weight: bold;
                z-index: 1;
                box-shadow: 0 2px 6px rgba(0, 0, 0, 0.1);
            }

            #animalListContainer>div {
                background: #ffffff;
                border-radius: 12px;
                overflow: hidden;
                box-shadow: 0 2px 8px rgba(0, 0, 0, 0.06);
                transition: transform 0.2s;
                text-align: left;
                display: flex;
                flex-direction: column;
            }

            #animalListContainer>div:hover {
                transform: translateY(-4px);
            }

            #animalListContainer img {
                width: 100%;
                aspect-ratio: 1 / 1;
                object-fit: cover;
                display: block;
                border-radius: 0;
            }

            .animal-name {
                font-weight: bold;
                font-size: 15px;
                padding: 10px 12px 4px 12px;
                white-space: nowrap;
                overflow: hidden;
                text-overflow: ellipsis;
            }

            .animal-info {
                color: #555;
                font-size: 13px;
                line-height: 1.4;
                padding: 0 12px 12px 12px;
                white-space: pre-line;
            }

            #toggleBtn {
                background: #3acdb2;
                color: #fff;
                font-size: 15px;
                font-weight: 600;
                border: none;
                padding: 10px 28px;
                border-radius: 8px;
                cursor: pointer;
                transition: background 0.2s;

                display: block;
                /* 중앙 정렬을 위한 블록 요소 */
                margin: 0 auto 20px;
                /* 가운데 정렬 + 아래 여백 */
            }

            #toggleBtn:hover {
                background: #2ea992;
            }

            .paging {
                margin: 28px 0;
                text-align: center;
            }

            .paging button {
                border: none;
                background: #fff;
                padding: 6px 12px;
                margin: 0 2px;
                border-radius: 4px;
                cursor: pointer;
                box-shadow: 0 1px 4px rgba(0, 0, 0, .08);
                transition: background .2s;
            }

            .paging button:hover {
                background: #3acdb2;
                color: #fff;
            }
        </style>
        <script src="${pageContext.request.contextPath}/resources/web/common/commonUtils.js"></script>
        <script>
            // 리스트 조회 함수
            async function animalList(cp) {
                const container = document.getElementById("animalListContainer");
                container.innerHTML = ""; // 초기화

                const form = document.getElementById("searchForm");
                const params = new URLSearchParams(new FormData(form));
                params.set("cp", cp);

                const result = await API.get('/care/api/animals?' + params.toString());
                if (result.status != 200) {
                    location.reload(true);
                    return;
                }
                document.getElementById("body").style.display = "block";
                const animals = result.data;
                const pageInfo = result.pageInfo;

                for (const animal of animals) {
                    const card = document.createElement("div");
                    card.innerHTML =
                        '<div class="badge">' + animal.adoptionStatus + '</div>' +
                        '<a href="${pageContext.request.contextPath}/animals/' + animal.idx + '">' +
                        '<img src="${pageContext.request.contextPath}' + animal.imagePath + '" alt="' + animal.name + '" />' +
                        '</a>' +
                        '<div class="animal-name">' + animal.name + '</div>' +
                        '<div class="animal-info">' +
                        animal.type + ' / ' + animal.breed + '<br>' +
                        (animal.gender === 'F' ? '암컷' : '수컷') + '(중성화 ' + (animal.neuter === 1 ? 'O' : 'X') + ')<br>' +
                        animal.age + '세 / ' + animal.size + 'kg' +
                        '</div>';
                    container.appendChild(card);
                }

                // 페이징 함수 실행
                makePaging(
                    pageInfo.totalCnt,
                    pageInfo.listSize,
                    pageInfo.pageSize,
                    pageInfo.cp,
                    "pagingArea", // 페이지 버튼이 들어갈 div id
                    animalList    // 페이지 번호 클릭 시 다시 animalList 호출
                );
            }
            function searchAnimals() {
                animalList(1); // 검색 시 1페이지로 리셋해서 호출
            }

            // 종류에 따른 품종 변화
            const breedsByType = {
                '개': ['푸들', '말티즈', '포메라니안', '진돗개', '요크셔테리어', '비숑프리제', '치와와', '웰시코기', '믹스견'],
                '고양이': ['코리안숏헤어', '러시안블루', '스코티시폴드', '먼치킨', '페르시안', '랙돌', '노르웨이숲', '믹스묘'],
                '기타': ['햄스터', '토끼', '기니피그', '패럿', '고슴도치', '거북이', '도마뱀', '기타']
            };

            function changeBreedOptions() {
                const typeSelect = document.getElementById('typeSelect');
                const breedSelect = document.getElementById('breedSelect');
                const selectedType = typeSelect.value;

                // 품종 select 초기화
                breedSelect.innerHTML = '<option value="">전체</option>';

                if (selectedType && breedsByType[selectedType]) {
                    for (const breed of breedsByType[selectedType]) {
                        const option = document.createElement('option');
                        option.value = breed;
                        option.textContent = breed;
                        breedSelect.appendChild(option);
                    }
                }
            }

            /* 토글 함수 */
            function viewSearch() {
                const toggleBtn = document.getElementById('toggleBtn');
                const searchForm = document.getElementById('searchForm');

                const isHidden = searchForm.style.display === 'none' || searchForm.style.display === '';
                searchForm.style.display = isHidden ? 'grid' : 'none';     // form 보여주기/숨기기
                toggleBtn.value = isHidden ? '검색 조건 닫기' : '검색 조건 열기'; // 버튼 글자 바꾸기
            }
        </script>
    </head>

    <body id="body" style="display: none;">
        <%@ include file="/WEB-INF/views/common/index/indexHeader.jsp" %>
            <div class="header-title">입양동물</div>
            <div class="title-detail">전국의 보호시설에 등록된 유기동물을 입양하세요!</div>
            <div class="container">
                <input type="button" id="toggleBtn" value="검색 조건 열기" onclick="viewSearch()">
                <form id="searchForm" onsubmit="searchAnimals(); return false;" style="display:none;">
                    <div class="form-item"><label>성별</label><select name="gender">
                            <option value="">전체</option>
                            <option value="M">남</option>
                            <option value="F">여</option>
                        </select></div>
                    <div class="form-item"><label>중성화</label><select name="neuter">
                            <option value="0">전체</option>
                            <option value="1">중성화 O</option>
                            <option value="2">중성화 X</option>
                        </select></div>
                    <div class="form-item"><label>나이 (세)</label><input type="number" name="age" min="0"></div>
                    <div class="form-item"><label>크기</label><select name="size">
                            <option value="0">전체</option>
                            <option value="1">소형</option>
                            <option value="2">중형</option>
                            <option value="3">대형</option>
                        </select></div>
                    <div class="form-item"><label>입양상태</label><select name="adoptionStatus">
                            <option value="">전체</option>
                            <option value="입양가능">입양가능</option>
                            <option value="입양대기">입양대기</option>
                            <option value="입양완료">입양완료</option>
                        </select></div>
                    <div class="form-item"><label>종류</label><select id="typeSelect" name="type"
                            onchange="changeBreedOptions()">
                            <option value="">전체</option>
                            <option value="개">개</option>
                            <option value="고양이">고양이</option>
                            <option value="기타">기타</option>
                        </select></div>
                    <div class="form-item"><label>품종</label><select id="breedSelect" name="breed">
                            <option value="">전체</option>
                        </select></div>
                    <div class="form-item"><label>성격</label><select name="personality">
                            <option value="">전체</option>
                            <option value="온순함">온순함</option>
                            <option value="활발함">활발함</option>
                            <option value="겁많음">겁많음</option>
                            <option value="사나움">사나움</option>
                            <option value="낯가림">낯가림</option>
                            <option value="애교많음">애교많음</option>
                            <option value="호기심많음">호기심많음</option>
                            <option value="사람좋아함">사람좋아함</option>
                            <option value="소심함">소심함</option>
                            <option value="지능높음">지능높음</option>
                        </select></div>
                    <div class="form-item name-field"><label>이름</label><input type="text" name="name"></div>
                    <div class="btn-area">
                        <a class="reset-link"
                            onclick="document.getElementById('searchForm').reset();changeBreedOptions();">검색 설정
                            초기화</a>
                        <input type="submit" value="검색">
                    </div>
                </form>
                <div id="animalListContainer"></div>
                <div id="pagingArea" class="paging"></div>
            </div>
            <%@ include file="/WEB-INF/views/common/index/indexFooter.jsp" %>
                <script>
                    window.addEventListener("DOMContentLoaded", function () {
                        animalList(1);
                        changeBreedOptions();
                    });
                </script>
    </body>

    </html>