<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
    <!DOCTYPE html>
    <html>

    <head>
        <meta charset="UTF-8">
        <title>Insert title here</title>
        <style>
            /* ───────── 전체 래퍼 ───────── */
            .shelter-detail {
                max-width: 1200px;
                margin: 40px auto;
                display: flex;
                gap: 32px;
                font-size: 15px;
                line-height: 1.55;
            }

            /* ───────── 좌측 사진 ───────── */
            .shelter-image {
                width: 280px;
                aspect-ratio: 1 / 1;
                background: #e5e5e5;
                border-radius: 12px;
                overflow: hidden;
                flex-shrink: 0;
            }

            .shelter-image img {
                width: 100%;
                height: 100%;
                object-fit: cover;
                display: block;
            }

            /* ───────── 우측 정보 영역 ───────── */
            .shelter-info {
                flex: 1 1 0;
                display: grid;
                grid-template-columns: 400px 1fr;
                row-gap: 8px;
                column-gap: 12px;
            }

            .shelter-info h2 {
                grid-column: 1 / 3;
                margin: 0 0 12px 0;
                font-size: 22px;
                font-weight: 700;
            }

            .shelter-info div span:first-child {
                font-weight: 600;
                color: #444;
            }

            .shelter-info input[type="button"] {
                grid-column: 1 / 3;
                margin-top: 25px;
                width: 150px;
                height: 40px;
                border: none;
                border-radius: 20px;
                background: #d9d9d9;
                cursor: pointer;
                font-weight: 600;
            }

            .shelter-info input[type="button"]:hover {
                background: #c4c4c4;
            }

            /* ───────── 소개 ───────── */
            .description-wrapper {
                max-width: 1200px;
                margin: 20px auto 50px auto;
            }

            .description-wrapper .label {
                font-weight: 600;
                color: #444;
                margin-bottom: 6px;
            }

            #shelterDescription {
                padding: 14px 16px;
                background: #f6f6f8;
                border-radius: 12px;
                width: 100%;
                line-height: 1.6;
                box-sizing: border-box;
            }

            /* ───────── Divider ───────── */
            .shelter-detail+hr {
                max-width: 850px;
                margin: 40px auto;
                border: none;
                height: 1px;
                background: #e0e0e0;
            }

            .board-container {
                max-width: 1200px;
                margin: 0 auto;
            }

            .board-table {
                width: 100%;
                border-collapse: collapse;
                font-size: 16px;
            }

            .board-table a {
                text-decoration: none;
                color: inherit;
                cursor: pointer;
            }

            .board-table th {
                padding: 14px 10px;
                border-bottom: 2px solid #DBDBDB;
                text-align: center;
                color: #333;
            }

            .board-table td {
                padding: 14px 10px;
                border-bottom: 1px solid #eee;
                text-align: center;
                color: #333;
            }

            .board-table tbody tr:hover {
                background-color: #fafafa;
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


            /* ---------- 동물 검색 · 카드 ---------- */
            #animalContainer {
                max-width: 1200px;
                /* 원하는 가로 제한 */
                margin: 0 auto;
                /* 가운데 정렬 */
            }

            #search {
                margin-top: 18px;
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
                display: block;
                margin: 0 auto 22px;
            }

            #toggleBtn:hover {
                background: #2ea992;
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
                max-width: 1200px;
                margin: 20px auto 0 auto;
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
                grid-column: 3/6;
            }

            .btn-area {
                grid-column: 6/7;
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
                background: #fff;
                border-radius: 12px;
                overflow: hidden;
                box-shadow: 0 2px 8px rgba(0, 0, 0, .06);
                transition: transform .2s;
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

            .badge {
                position: absolute;
                top: 12px;
                left: 12px;
                background: #3acdb2;
                color: #fff;
                padding: 4px 10px;
                border-radius: 8px;
                font-size: 12px;
                font-weight: 700;
                z-index: 1;
            }

            .animal-name {
                font-weight: 700;
                font-size: 15px;
                padding: 10px 12px 4px;
                white-space: nowrap;
                overflow: hidden;
                text-overflow: ellipsis;
            }

            .animal-info {
                color: #555;
                font-size: 13px;
                line-height: 1.4;
                padding: 0 12px 12px;
                white-space: pre-line;
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

            #reviewContainer {
                max-width: 1200px;
                /* 원하는 가로 제한 */
                margin: 0 auto;
                /* 가운데 정렬 */
            }

            .review-card {
                display: flex;
                gap: 14px;
                cursor: pointer;
                padding: 14px 10px;
                border-bottom: 1px solid #eee;
                transition: .3s;
            }

            .review-card img {
                flex-shrink: 0;
                width: 120px;
                height: 120px;
                object-fit: cover;
                border-radius: 8px;
                transition: .3s;
            }

            .review-card .content {
                flex: 1;
                display: -webkit-box;
                -webkit-box-orient: vertical;
                -webkit-line-clamp: 3;
                overflow: hidden;
                white-space: pre-wrap;
            }

            .review-card .meta {
                font-size: 13px;
                color: #666;
                margin-top: 6px;
            }

            .review-card.expanded img {
                width: 240px;
                height: 240px;
            }

            .review-card.expanded .content {
                -webkit-line-clamp: unset;
            }

            /* ===== 메인 탭 (‘봉사’ ~ ‘후기’) ===== */
            /* 메인 탭 버튼 묶음 */
            .tabs {
                display: flex;
                justify-content: center;
                /* 중앙 정렬 */
                gap: 8px;
                /* 버튼 간 간격 */
                margin: 24px 0;
            }

            /* 서브 탭(봉사리뷰/입양리뷰) 묶음 */
            .sub-tabs {
                display: flex;
                justify-content: center;
                width: 100%;
                gap: 12px;
                margin-top: 16px;
                margin-bottom: 20px;
            }

            .sub-tabs.hidden {
                /* 숨길 때만 이 클래스 붙임 */
                display: none;
            }

            .tab-btn {
                appearance: none;
                /* 플랫폼 기본 스타일 제거 */
                border: 1px solid #ccc;
                background: #fff;
                padding: 8px 22px;
                margin-right: 6px;
                border-radius: 8px;
                font-size: 15px;
                font-weight: 600;
                cursor: pointer;
                transition: background .2s, color .2s, border-color .2s;
            }

            .tab-btn:hover {
                background: #f6f6f8;
            }

            .tab-btn.active {
                background: #3acdb2;
                border-color: #3acdb2;
                color: #fff;
            }

            /* ===== 서브 탭 (‘봉사리뷰’ / ‘입양리뷰’) ===== */

            .sub-btn {
                appearance: none;
                border: none;
                background: transparent;
                padding: 6px 14px;
                font-size: 14px;
                font-weight: 600;
                color: #666;
                cursor: pointer;
                border-bottom: 2px solid transparent;
                transition: color .2s, border-color .2s;
            }

            .sub-btn:hover {
                color: #3acdb2;
            }

            .sub-btn.active {
                color: #3acdb2;
                border-color: #3acdb2;
            }

            .shelter-map-wrapper {
                max-width: 1200px;
                margin: 10px auto 32px;
            }

            .shelter-map-box {
                padding: 14px;
                background: #f6f6f8;
                border-radius: 12px;
                display: flex;
                gap: 14px;
                align-items: center;
            }

            .shelter-map-text {
                flex-shrink: 0;
            }

            .shelter-map-text strong {
                font-size: 15px;
                color: #333;
            }

            .shelter-map-text p {
                margin-top: 4px;
                font-size: 13px;
                color: #666;
                line-height: 1.4;
            }

            #map {
                flex: 1;
                height: 220px;
                border-radius: 12px;
                background: #ddd;
            }
        </style>
        <script src="${pageContext.request.contextPath}/resources/web/common/commonUtils.js"></script>
        <script
            src="//dapi.kakao.com/v2/maps/sdk.js?appkey=89af9a1fd3e16580bde0d540156f0001&autoload=false&libraries=services"></script>

        <script>
            const idx = location.pathname.split("/").pop();

            // 보호시설의 상세 정보 조회 함수
            async function shelterDetail() {
                const result = await API.get('/care/api/shelters/' + idx);
                if (result.errorCode) {
                    location.href = '/care/shelters';
                    return;
                }

                sheltevolunteer(1);
                document.getElementById("body").style.display = "block";
                const shelter = result.data;

                document.getElementById("shelterImage").src = '${pageContext.request.contextPath}' + shelter.imagePath;
                document.getElementById("shelterName").textContent = shelter.shelterName;
                document.getElementById("shelterPersonName").textContent = shelter.shelterPersonName;
                document.getElementById("shelterTel").textContent = shelter.shelterTel;
                document.getElementById("shelterAddress").textContent = shelter.shelterAddress;
                document.getElementById("shelterAddressDetail").textContent = shelter.shelterAddressDetail;
                document.getElementById("shelterEmail").textContent = shelter.shelterEmail;
                document.getElementById("shelterDescription").textContent = shelter.shelterDescription;
                document.getElementById("animalCount").textContent = shelter.animalCount != null ? shelter.animalCount : "";
                document.getElementById("reviewCount").textContent = shelter.reviewCount != null ? shelter.reviewCount : "";
                document.getElementById("shelterType").textContent = shelter.shelterType;

                if (shelter.shelterAddress && shelter.shelterAddressDetail) {
                    const fullAddress = shelter.shelterAddress;
                    initMapByAddress(fullAddress);
                }
            }
            // 보호시설 봉사 컨텐츠 조회 함수
            async function sheltevolunteer(cp = 1) {
                showContainer('volunteerContainer');
                document.getElementById("search").style.display = "none";
                toggleReviewTabs(false);

                const wrapper = document.getElementById("volunteerContainer");
                wrapper.innerHTML = "";

                const boardWrap = document.createElement("div");
                boardWrap.className = "board-container";
                wrapper.appendChild(boardWrap);

                const table = document.createElement("table");
                table.className = "board-table";
                boardWrap.appendChild(table);

                const thead = document.createElement("thead");
                table.appendChild(thead);
                const theadRow = thead.insertRow();
                theadRow.innerHTML =
                    "<th>NO</th>" +
                    "<th>제목</th>" +
                    "<th>보호시설</th>" +
                    "<th>지역</th>" +
                    "<th>상태</th>" +
                    "<th>봉사시간</th>" +
                    "<th>작성일</th>";

                const tbody = document.createElement("tbody");
                table.appendChild(tbody);

                const result = await API.get('/care/api/shelters/' + idx + '/volunteers?cp=' + cp);
                if (result.status != 200) {
                    const tbodyRow = tbody.insertRow();
                    tbodyRow.innerHTML = "<td colspan='7'>등록되어 있는 봉사가 없습니다.</td>";
                    return;
                }
                const volunteers = result.data;
                const pageInfo = result.pageInfo;

                for (const volunteer of volunteers) {
                    const tbodyRow = tbody.insertRow();
                    tbodyRow.setAttribute("onclick", "location.href='/care/shelters/" + idx + "/volunteers/" + volunteer.idx + "'");
                    tbodyRow.innerHTML =
                        "<td>" + volunteer.idx + "</td>" +
                        "<td>" + volunteer.title + "</td>" +
                        "<td>" + volunteer.shelterName + "</td>" +
                        "<td>" + volunteer.location + "</td>" +
                        "<td>" + volunteer.volunteerStatus + "</td>" +
                        "<td>" + volunteer.volunteerTime + "</td>" +
                        "<td>" + volunteer.createdAT + "</td>";
                }

                const paging = document.createElement("div");
                paging.id = "volPaging";
                paging.className = "paging";
                wrapper.appendChild(paging);

                makePaging(
                    pageInfo.totalCnt,
                    pageInfo.listSize,
                    pageInfo.pageSize,
                    pageInfo.cp,
                    "volPaging",
                    sheltevolunteer
                );
            }

            // 보호시설 유기동물 컨텐츠 조회 함수
            async function shelteAnimals(cp = 1) {
                showContainer('animalContainer');

                document.getElementById("search").style.display = "block";
                toggleReviewTabs(false);

                const listBox = document.getElementById("animalListContainer");
                const pagingBox = document.getElementById("aniPaging");
                listBox.innerHTML = "";
                pagingBox.innerHTML = "";

                const form = document.getElementById("searchForm");
                const params = new URLSearchParams(new FormData(form));
                params.set("cp", cp);

                const result = await API.get('/care/api/shelters/' + idx + '/animals?' + params.toString());
                if (result.status != 200) {
                    location.reload(true);
                    return;
                }
                const animals = result.data;
                const pageInfo = result.pageInfo;

                for (const animal of animals) {
                    const card = document.createElement("div");
                    card.innerHTML =
                        '<div class="badge">' + animal.adoptionStatus + '</div>' +
                        "<a href='/care/shelters/" + idx + "/animals/" + animal.idx + "'>" +
                        '<img src="${pageContext.request.contextPath}' + animal.imagePath + '" alt="' + animal.name + '" />' +
                        '</a>' +
                        '<div class="animal-name">' + animal.name + '</div>' +
                        '<div class="animal-info">' +
                        animal.type + ' / ' + animal.breed + '<br>' +
                        (animal.gender === 'F' ? '암컷' : '수컷') + '(중성화 ' + (animal.neuter === 1 ? 'O' : 'X') + ')<br>' +
                        animal.age + '세 / ' + animal.size + 'kg' +
                        '</div>';
                    listBox.appendChild(card);
                }

                makePaging(
                    pageInfo.totalCnt,
                    pageInfo.listSize,
                    pageInfo.pageSize,
                    pageInfo.cp,
                    "aniPaging", // 페이지 버튼이 들어갈 div id
                    shelteAnimals
                );
            }

            function searchAnimals() {
                shelteAnimals(1); // 검색 시 1페이지로 리셋해서 호출
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

            function viewSearch() {
                const btn = document.getElementById("toggleBtn");
                const form = document.getElementById("searchForm");
                const hide = form.style.display === "none" || !form.style.display;
                form.style.display = hide ? "grid" : "none";
                btn.value = hide ? "검색 조건 닫기" : "검색 조건 열기";
            }

            // 보호시설 게시글 컨텐츠 조회 함수
            async function shelteBoards(cp = 1) {
                showContainer('boardContainer');
                document.getElementById("search").style.display = "none";
                toggleReviewTabs(false);

                const wrapper = document.getElementById('boardContainer');
                wrapper.innerHTML = '';

                const boardWrap = document.createElement('div');
                boardWrap.className = 'board-container';
                wrapper.appendChild(boardWrap);

                const table = document.createElement('table');
                table.className = 'board-table';
                boardWrap.appendChild(table);
                const thead = document.createElement("thead");
                table.appendChild(thead);
                const theadRow = thead.insertRow();
                theadRow.innerHTML =
                    "<th>NO</th>" +
                    "<th>제목</th>" +
                    "<th>보호시설</th>" +
                    "<th>작성일</th>" +
                    "<th>조회수</th>";

                const tbody = document.createElement("tbody");
                table.appendChild(tbody);

                const result = await API.get('/care/api/shelters/' + idx + '/boards?cp=' + cp);
                if (result.status != 200) {
                    const tbodyRow = tbody.insertRow();
                    tbodyRow.innerHTML = "<td colspan='5'>등록되어 있는 게시글이 없습니다.</td>";
                    return;
                }
                const boards = result.data;
                const pageInfo = result.pageInfo;

                for (const board of boards) {
                    const tbodyRow = tbody.insertRow();
                    tbodyRow.setAttribute("onclick", "location.href='/care/shelters/" + idx + "/boards/" + board.idx + "'");
                    tbodyRow.innerHTML =
                        "<td>" + board.idx + "</td>" +
                        "<td>" +
                        "<a href='" + "/care/shelters/" + idx + "/boards/" + board.idx + "'>" +
                        board.title +
                        "</a>" +
                        "</td>" +
                        "<td>" + board.shelterName + "</td>" +
                        "<td>" + board.createdAt + "</td>" +
                        "<td>" + board.views + "</td>";
                }

                const paging = document.createElement('div');
                paging.id = 'boardPaging';
                paging.className = 'paging';
                wrapper.appendChild(paging);
                makePaging(
                    pageInfo.totalCnt,
                    pageInfo.listSize,
                    pageInfo.pageSize,
                    pageInfo.cp,
                    "boardPaging",
                    shelteBoards
                );
            }

            // 보호시설 봉사리뷰 조회 함수
            async function shelteVolunteerReviews(cp = 1) {
                showContainer("reviewContainer");
                document.getElementById("search").style.display = "none";
                toggleReviewTabs(true);

                const wrapper = document.getElementById("reviewContainer");
                wrapper.innerHTML = "";

                const result = await API.get('/care/api/shelters/' + idx + '/reviews/voluntees?cp=' + cp);
                if (result.status != 200) {
                    location.reload(true);
                    return;
                }
                const reviews = result.data;
                const pageInfo = result.pageInfo;

                for (const review of reviews) {
                    const card = document.createElement("div");
                    card.className = "review-card";

                    card.innerHTML =
                        '<img src="${pageContext.request.contextPath}' + review.imagePath + '" ' +
                        '     alt="' + review.nickname + '" width="120" height="120">' +
                        '<div class="content" style="white-space:pre-wrap;">' + review.content + '</div>' +
                        '<div class="meta">' + review.nickname + ' · ' + review.createdAt + '</div>';

                    card.addEventListener("click", () => card.classList.toggle("expanded"));

                    if (review.turn != 0) {
                        const imgTag = card.querySelector("img");
                        if (imgTag) {
                            imgTag.remove();
                        }

                        card.style.marginLeft = (review.turn * 30) + "px";
                        card.style.backgroundColor = "#f9f9f9";
                        card.style.borderLeft = "3px solid #ccc";
                    }
                    wrapper.appendChild(card);
                }

                const pagingArea = document.createElement("div");
                pagingArea.id = "pagingArea";
                pagingArea.className = "paging";
                wrapper.appendChild(pagingArea);

                // 페이징 함수 실행
                makePaging(
                    pageInfo.totalCnt,
                    pageInfo.listSize,
                    pageInfo.pageSize,
                    pageInfo.cp,
                    "pagingArea", // 페이지 버튼이 들어갈 div id
                    shelteVolunteerReviews
                );
            }

            // 보호시설 입양리뷰 조회 함수
            async function shelteAdoptionReviews(cp = 1) {
                showContainer("reviewContainer");        // 탭 전환
                document.getElementById("search").style.display = "none";
                toggleReviewTabs(true);

                const wrapper = document.getElementById("reviewContainer");
                wrapper.innerHTML = "";

                const result = await API.get('/care/api/shelters/' + idx + '/reviews/adoptions?cp=' + cp);
                if (result.status != 200) {
                    location.reload(true);
                    return;
                }
                const reviews = result.data;
                const pageInfo = result.pageInfo;

                for (const review of reviews) {
                    const card = document.createElement("div");
                    card.className = "review-card";

                    card.innerHTML =
                        '<img src="${pageContext.request.contextPath}' + review.imagePath + '" ' +
                        '     alt="' + review.nickname + '" width="120" height="120">' +      // 처음엔 120px
                        '<div class="content" style="white-space:pre-wrap;">' + review.content + '</div>' +
                        '<div class="meta">' + review.nickname + ' · ' + review.createdAt + '</div>';

                    card.addEventListener("click", () => card.classList.toggle("expanded"));

                    if (review.turn != 0) {
                        const imgTag = card.querySelector("img");
                        if (imgTag) {
                            imgTag.remove();
                        }

                        card.style.marginLeft = (review.turn * 30) + "px"; // turn 값 × 30px 만큼 들여쓰기
                        card.style.backgroundColor = "#f9f9f9";             // 연한 배경
                        card.style.borderLeft = "3px solid #ccc";          // 구분선
                    }
                    wrapper.appendChild(card);
                }

                const pagingArea = document.createElement("div");
                pagingArea.id = "pagingArea";
                pagingArea.className = "paging";
                wrapper.appendChild(pagingArea);
                // 페이징 함수 실행
                makePaging(
                    pageInfo.totalCnt,
                    pageInfo.listSize,
                    pageInfo.pageSize,
                    pageInfo.cp,
                    "pagingArea", // 페이지 버튼이 들어갈 div id
                    shelteAdoptionReviews
                );
            }

            function showContainer(targetId) {
                ['volunteerContainer', 'animalContainer', 'boardContainer', 'reviewContainer']
                    .forEach(id => {
                        const el = document.getElementById(id);
                        if (el) el.style.display = (id === targetId) ? 'block' : 'none';
                    });
            }

            function tab(btn, targetId, fn) {
                document.querySelectorAll('.tab-btn').forEach(b => b.classList.remove('active'));
                btn.classList.add('active');

                showContainer(targetId);

                if (fn === shelteVolunteerReviews || fn === shelteAdoptionReviews) {
                    const btn = document.querySelector('#reviewSelect .sub-btn[value="봉사리뷰"]');
                    subTab(btn, fn);
                } else {
                    toggleReviewTabs(false);
                    fn(1);
                }
            }

            function subTab(btn, fn) {
                document.querySelectorAll('.sub-btn').forEach(b => b.classList.remove('active'));
                btn.classList.add('active');
                fn(1);
            }
            function toggleReviewTabs(show) {
                const el = document.getElementById('reviewSelect');
                el.classList.toggle('hidden', !show);   // show==true → 보임, false → 숨김
            }

            function initMapByAddress(address) {
                const geocoder = new kakao.maps.services.Geocoder();

                geocoder.addressSearch(address, function (result, status) {
                    let coord;

                    if (status === kakao.maps.services.Status.OK && result.length) {
                        const lat = parseFloat(result[0].y);
                        const lng = parseFloat(result[0].x);
                        coord = new kakao.maps.LatLng(lat, lng);
                    } else {
                        // 주소를 찾을 수 없을 때: 기본 좌표 (서울시청)
                        coord = new kakao.maps.LatLng(37.5665, 126.9780);
                    }

                    const map = new kakao.maps.Map(document.getElementById('map'), {
                        center: coord,
                        level: 3,
                    });

                    new kakao.maps.Marker({
                        position: coord,
                        map: map,
                    });
                });
            }
        </script>
    </head>

    <body id="body" style="display: none;">
        <%@ include file="/WEB-INF/views/common/index/indexHeader.jsp" %>
            <div class="shelter-detail">
                <div class="shelter-image">
                    <img id="shelterImage" src="" width="200" height="200" alt="보호소 사진">
                </div>
                <div class="shelter-info">
                    <h2 id="shelterName">보호소명</h2>
                    <div><span>주소: </span><span id="shelterAddress"></span> <span id="shelterAddressDetail"></span>
                    </div>
                    <div><span>보호소 유형: </span><span id="shelterType"></span></div>
                    <div><span>담당자: </span><span id="shelterPersonName"></span></div>
                    <div><span>연락처: </span><span id="shelterTel"></span></div>
                    <div><span>이메일: </span><span id="shelterEmail"></span></div>
                    <div><span>유기기 동물 수: </span><span id="animalCount"></span></div>
                    <div style="grid-column: 1 / 3;">
                        <div><span>후기 수: </span><span id="reviewCount"></span></div>
                        <input type="button" value="목록" onclick="location.href='/care/shelters'"
                            style="margin-top: 10px;">
                    </div>
                </div>
            </div>
            <div class="description-wrapper">
                <div class="label">소개</div>
                <div id="shelterDescription" style="white-space: pre-wrap;"></div>
            </div>
            <div class="shelter-map-wrapper">
                <div class="shelter-map-box">
                    <div class="shelter-map-text">
                        <strong>보호소 위치</strong>
                        <p>지도에서 보호소 위치를 확인할 수 있습니다.</p>
                    </div>
                    <div id="map"></div>
                </div>
            </div>

            <div class="tabs">
                <input type="button" class="tab-btn" value="봉사"
                    onclick="tab(this,'volunteerContainer',sheltevolunteer)">
                <input type="button" class="tab-btn" value="유기동물" onclick="tab(this,'animalContainer',shelteAnimals)">
                <input type="button" class="tab-btn" value="게시판" onclick="tab(this,'boardContainer',shelteBoards)">
                <input type="button" class="tab-btn" value="후기"
                    onclick="tab(this,'reviewContainer',shelteVolunteerReviews)">
            </div>
            <div id="reviewSelect" class="sub-tabs hidden">
                <input type="button" class="sub-btn" value="봉사리뷰" onclick="subTab(this, shelteVolunteerReviews)">
                <input type="button" class="sub-btn" value="입양리뷰" onclick="subTab(this, shelteAdoptionReviews)">
            </div>
            <div id="shelterContent">
                <div id="volunteerContainer" style="display: none;">
                </div>
                <div id="animalContainer" style="display: none;">
                    <div id="search">
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
                    </div>
                    <div id="animalListContainer"></div>
                    <div id="aniPaging" class="paging"></div>
                </div>
                <div id="boardContainer" style="display: none;">
                    <!-- 자유/보호소/공지 게시글 리스트 -->
                </div>
                <div id="reviewContainer" style="display: none;">
                    <!-- 입양 후기 리스트 -->
                </div>
            </div>
            <%@ include file="/WEB-INF/views/common/index/indexFooter.jsp" %>
                <script>
                    kakao.maps.load(() => {
                        shelterDetail();
                        document.querySelector('.tabs .tab-btn').classList.add('active');
                    });
                </script>
    </body>

    </html>