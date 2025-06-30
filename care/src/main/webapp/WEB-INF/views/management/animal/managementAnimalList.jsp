<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
    <!DOCTYPE html>
    <html>
    <head>
        <meta charset="UTF-8">
        <title>Insert title here</title>
        <script src="${pageContext.request.contextPath}/resources/web/common/commonUtils.js"></script>
        <script>
            // 리스트 조회 함수
            async function animalList(cp) {
                const container = document.getElementById("animalListContainer");
                container.innerHTML = ""; // 초기화

                const form = document.getElementById("searchForm");
                const params = new URLSearchParams(new FormData(form));
                params.set("cp", cp);

                const result = await API.get('/care/api/management/animals?' + params.toString());
                if (result.status != 200) {
                    location.href="care/index";
                    return;
                }
                document.getElementById("body").style.display = "block";
                const animals = result.data;
                const pageInfo = result.pageInfo;

                for (const animal of animals) {
                    const card = document.createElement("div");
                    card.innerHTML =
                        '<a href="animals/' + animal.idx + '">' +
                        '<img src="' + '${pageContext.request.contextPath}' + animal.imagePath + '" alt="' + animal.name + '" width="150" height="150" />' +
                        '</a>' +
                        '<div>' + animal.name + '</div>' +
                        '<div>상태: ' + animal.adoptionStatus + '</div>' +
                        '<div>품종: ' + animal.breed + '</div>';
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
        <form id="searchForm" onsubmit="searchAnimals(); return false;" style="margin-bottom: 20px;">
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
        <div id="animalListContainer"></div>
        <div id="pagingArea" class="paging"></div>
        <script>
            window.addEventListener("DOMContentLoaded", function () {
                animalList(1);
                changeBreedOptions();
            });
        </script>
    </body>

    </html>