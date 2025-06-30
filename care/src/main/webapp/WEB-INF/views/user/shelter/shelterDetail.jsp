<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
    <!DOCTYPE html>
    <html>

    <head>
        <meta charset="UTF-8">
        <title>Insert title here</title>
        <script src="${pageContext.request.contextPath}/resources/web/common/commonUtils.js"></script>
        <script>
            const idx = location.pathname.split("/").pop();

            // 보호시설의 상세 정보 조회 함수
            async function shelterDetail() {
                const result = await API.get('/care/api/shelters/' + idx);
                if (result.status != 200) {
                    location.href = '/care/shelters';
                    return;
                }

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
            }
            // 보호시설 봉사 컨텐츠 조회 함수
            async function sheltevolunteer(cp = 1) {
                document.getElementById("search").style.display = "none";
                document.getElementById("reviewSelect").style.display = "none";
                const shelterContent = document.getElementById("shelterContent");
                shelterContent.innerHTML = "";
                const table = document.createElement("table");
                table.setAttribute("border", "1");
                shelterContent.appendChild(table);
                const thead = document.createElement("thead");
                table.appendChild(thead);
                const theadRow = thead.insertRow();
                theadRow.innerHTML =
                    "<th>번호</th>" +
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
                    tbodyRow.innerHTML =
                        "<td>" + volunteer.idx + "</td>" +
                        "<td>" +
                        "<a href='/care/shelters/" + idx + "/volunteers/" + volunteer.idx + "'>" +
                        volunteer.title +
                        "</a>" +
                        "</td>" +
                        "<td>" + volunteer.shelterName + "</td>" +
                        "<td>" + volunteer.location + "</td>" +
                        "<td>" + volunteer.volunteerStatus + "</td>" +
                        "<td>" + volunteer.volunteerTime + "</td>" +
                        "<td>" + volunteer.createdAT + "</td>";
                }

                const pagingArea = document.createElement("div");
                pagingArea.setAttribute("id", "pagingArea");
                shelterContent.appendChild(pagingArea);
                makePaging(
                    pageInfo.totalCnt,
                    pageInfo.listSize,
                    pageInfo.pageSize,
                    pageInfo.cp,
                    "pagingArea", // 페이지 버튼이 들어갈 div id
                    sheltevolunteer    // 페이지 번호 클릭 시 다시 animalList 호출
                );
            }

            // 보호시설 유기동물 컨텐츠 조회 함수
            async function shelteAnimals(cp = 1) {
                document.getElementById("search").style.display = "block";
                document.getElementById("reviewSelect").style.display = "none";
                const shelterContent = document.getElementById("shelterContent");
                shelterContent.innerHTML = "";
                const searchForm = document.createElement("div");
                changeBreedOptions();
                const container = document.createElement("div");
                shelterContent.appendChild(container);

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
                        '<a href="/care/shelters/' + idx + '/animals/' + animal.idx + '">' +
                        '<img src="' + '${pageContext.request.contextPath}' + animal.imagePath + '" alt="' + animal.name + '" width="150" height="150" />' +
                        '</a>' +
                        '<div>' + animal.name + '</div>' +
                        '<div>상태: ' + animal.adoptionStatus + '</div>' +
                        '<div>품종: ' + animal.breed + '</div>';
                    container.appendChild(card);
                }

                const pagingArea = document.createElement("div");
                pagingArea.setAttribute("id", "pagingArea");
                shelterContent.appendChild(pagingArea);
                // 페이징 함수 실행
                makePaging(
                    pageInfo.totalCnt,
                    pageInfo.listSize,
                    pageInfo.pageSize,
                    pageInfo.cp,
                    "pagingArea", // 페이지 버튼이 들어갈 div id
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
            // 보호시설 게시글 컨텐츠 조회 함수
            async function shelteBoards(cp = 1) {
                document.getElementById("search").style.display = "none";
                document.getElementById("reviewSelect").style.display = "none";
                const shelterContent = document.getElementById("shelterContent");
                shelterContent.innerHTML = "";
                const table = document.createElement("table");
                table.setAttribute("border", "1");
                shelterContent.appendChild(table);
                const thead = document.createElement("thead");
                table.appendChild(thead);
                const theadRow = thead.insertRow();
                theadRow.innerHTML =
                    "<th>번호</th>" +
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

                const pagingArea = document.createElement("div");
                pagingArea.setAttribute("id", "pagingArea");
                shelterContent.appendChild(pagingArea);
                makePaging(
                    pageInfo.totalCnt,
                    pageInfo.listSize,
                    pageInfo.pageSize,
                    pageInfo.cp,
                    "pagingArea", // 페이지 버튼이 들어갈 div id
                    shelteBoards
                );
            }

            // 보호시설 봉사리뷰 조회 함수
            async function shelteVolunteerReviews(cp = 1) {
                document.getElementById("search").style.display = "none";
                document.getElementById("reviewSelect").style.display = "block";
                const shelterContent = document.getElementById("shelterContent");
                shelterContent.innerHTML = "";
                const container = document.createElement("div");
                shelterContent.appendChild(container);

                const result = await API.get('/care/api/shelters/' + idx + '/reviews/voluntees?cp=' + cp);
                if (result.status != 200) {
                    location.reload(true);
                    return;
                }
                const reviews = result.data;
                const pageInfo = result.pageInfo;

                for (const review of reviews) {
                    const card = document.createElement("div");
                    card.innerHTML =
                        '<img src="' + '${pageContext.request.contextPath}' + review.imagePath + '" alt="' + review.nickname + '" width="150" height="150" />' +
                        '</a>' +
                        '<div style="white-space: pre-wrap;">' + review.content + '</div>' +
                        '<div>' + review.createdAt + '</div>' +
                        '<div>' + review.nickname + '</div>';

                    if (review.turn != 0) {
                        card.style.marginLeft = "30px"; // 들여쓰기
                        card.style.backgroundColor = "#f0f0f0"; // 연한 배경색
                        card.style.borderLeft = "3px solid #ccc"; // 구분선
                    }
                    container.appendChild(card);
                }

                const pagingArea = document.createElement("div");
                pagingArea.setAttribute("id", "pagingArea");
                shelterContent.appendChild(pagingArea);
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
                document.getElementById("search").style.display = "none";
                document.getElementById("reviewSelect").style.display = "block";
                const shelterContent = document.getElementById("shelterContent");
                shelterContent.innerHTML = "";
                const container = document.createElement("div");
                shelterContent.appendChild(container);

                const result = await API.get('/care/api/shelters/' + idx + '/reviews/adoptions?cp=' + cp);
                if (result.status != 200) {
                    location.reload(true);
                    return;
                }
                const reviews = result.data;
                const pageInfo = result.pageInfo;

                for (const review of reviews) {
                    const card = document.createElement("div");
                    card.innerHTML =
                        '<img src="' + '${pageContext.request.contextPath}' + review.imagePath + '" alt="' + review.nickname + '" width="150" height="150" />' +
                        '</a>' +
                        '<div style="white-space: pre-wrap;">' + review.content + '</div>' +
                        '<div>' + review.createdAt + '</div>' +
                        '<div>' + review.nickname + '</div>';
                    if (review.turn != 0) {
                        card.style.marginLeft = "30px"; // 들여쓰기
                        card.style.backgroundColor = "#f0f0f0"; // 연한 배경색
                        card.style.borderLeft = "3px solid #ccc"; // 구분선
                    }
                    container.appendChild(card);
                }

                const pagingArea = document.createElement("div");
                pagingArea.setAttribute("id", "pagingArea");
                shelterContent.appendChild(pagingArea);
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
                <div><span>주소: </span><span id="shelterAddress"></span> <span id="shelterAddressDetail"></span></div>
                <div><span>보호소 유형: </span><span id="shelterType"></span></div>
                <div><span>담당자: </span><span id="shelterPersonName"></span></div>
                <div><span>연락처: </span><span id="shelterTel"></span></div>
                <div><span>이메일: </span><span id="shelterEmail"></span></div>
                <div><span>유기기 동물 수: </span><span id="animalCount"></span></div>
                <div><span>후기 수: </span><span id="reviewCount"></span></div>
                <hr>
                <div><span>보호소 설명: </span><br><span id="shelterDescription" style="white-space: pre-wrap;"></span></div>
            </div>
            <hr>
            <div>
                <input type="button" value="봉사" onclick="sheltevolunteer(1)">
                <input type="button" value="유기동물" onclick="shelteAnimals(1)">
                <input type="button" value="게시판" onclick="shelteBoards(1)">
                <input type="button" value="후기" onclick="shelteVolunteerReviews(1)">
            </div>
        </div>
        <div id="search" style="display: none;">
            <form id="searchForm" onsubmit="searchAnimals(); return false;">
                이름: <input type="text" name="name">
                성별:
                <select name="gender">
                    <option value="">전체</option>
                    <option value="M">남</option>
                    <option value="F">여</option>
                </select>
                중성화:
                <select name="neuter">
                    <option value="0">전체</option>
                    <option value="1">중성화 O</option>
                    <option value="2">중성화 X</option>
                </select>
                나이: <input type="number" name="age" min="0" style="width: 60px;">세
                크기:
                <select name="size">
                    <option value="0">전체</option>
                    <option value="1">소형</option>
                    <option value="2">중형</option>
                    <option value="3">대형</option>
                </select>
                입양상태:
                <select name="adoptionStatus">
                    <option value="">전체</option>
                    <option value="입양가능">입양가능</option>
                    <option value="입양대기">입양대기</option>
                    <option value="입양완료">입양완료</option>
                </select>
                종류:
                <select id="typeSelect" name="type" onchange="changeBreedOptions()">
                    <option value="">전체</option>
                    <option value="개">개</option>
                    <option value="고양이">고양이</option>
                    <option value="기타">기타</option>
                </select>
                품종: <select id="breedSelect" name="breed">
                    <option value="">전체</option>
                </select>
                성격:
                <select name="personality">
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
                </select>
                <input type="submit" value="검색">
            </form>
        </div>
        <div id="reviewSelect" style="display: none;">
            <input type="button" value="봉사리뷰" onclick="shelteVolunteerReviews(1)">
            <input type="button" value="입양리뷰" onclick="shelteAdoptionReviews(1)">
        </div>
        <div id="shelterContent"></div>
        </div>
        <input type="button" value="목록" onclick="location.href='/care/shelters'">
        <script>
            window.addEventListener("DOMContentLoaded", function () {
                shelterDetail();
                sheltevolunteer(1);
            });
        </script>
    </body>

    </html>