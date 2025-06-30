<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
    <!DOCTYPE html>
    <html>

    <head>
        <meta charset="UTF-8">
        <title>Insert title here</title>
        <script src="${pageContext.request.contextPath}/resources/web/common/commonUtils.js"></script>
        <script>
            // 보호시설 조회 함수
            async function shelterList(cp) {
                const container = document.getElementById("shelterListContainer");
                container.innerHTML = ""; // 초기화

                const form = document.getElementById("searchForm");
                const params = new URLSearchParams(new FormData(form));
                params.set("cp", cp);

                const result = await API.get('/care/api/shelters?' + params.toString());
                if (result.status != 200) {
                    location.reload(true);
                    return;
                }

                document.getElementById("body").style.display = "block";
                const shelters = result.data;
                const pageInfo = result.pageInfo;

                for (const shelter of shelters) {
                    const card = document.createElement("div");
                    card.innerHTML =
                        '<a href="shelters/' + shelter.idx + '">' +
                        '<img src="' + '${pageContext.request.contextPath}' + shelter.imagePath + '" alt="' + shelter.shelterName + '" width="150" height="150" />' +
                        '</a>' +
                        '<div>' + shelter.shelterName + '</div>'+
                        '<div>' + shelter.shelterType + '</div>';
                    container.appendChild(card);
                }

                // 페이징 함수 실행
                makePaging(
                    pageInfo.totalCnt,
                    pageInfo.listSize,
                    pageInfo.pageSize,
                    pageInfo.cp,
                    "pagingArea", // 페이지 버튼이 들어갈 div id
                    shelterList    // 페이지 번호 클릭 시 다시 animalList 호출
                );
            }
            function searchShelters() {
                shelterList(1);
            }

        </script>
    </head>

    <body id="body" style="display: none;">
        <%@ include file="/WEB-INF/views/common/index/indexHeader.jsp" %>
        <form id="searchForm" onsubmit="searchShelters(); return false;" style="margin-bottom: 20px;">
            주소:
            <select name="shelterAddress">
                <option value="">전체</option>
                <option value="서울특별시">서울특별시</option>
                <option value="부산광역시">부산광역시</option>
                <option value="대구광역시">대구광역시</option>
                <option value="인천광역시">인천광역시</option>
                <option value="광주광역시">광주광역시</option>
                <option value="대전광역시">대전광역시</option>
                <option value="울산광역시">울산광역시</option>
                <option value="세종특별자치시">세종특별자치시</option>
                <option value="경기도">경기도</option>
                <option value="강원도">강원도</option>
                <option value="충청북도">충청북도</option>
                <option value="충청남도">충청남도</option>
                <option value="전라북도">전라북도</option>
                <option value="전라남도">전라남도</option>
                <option value="경상북도">경상북도</option>
                <option value="경상남도">경상남도</option>
                <option value="제주특별자치도">제주특별자치도</option>
            </select>
            구분:
            <select name="shelterType">
                <option value="">전체</option>
                <option value="공공">공공</option>
                <option value="민간">민간</option>
                <option value="사설">사설</option>
            </select>
            이름: <input type="text" name="shelterName">
            <input type="submit" value="검색">
        </form>
        <div id="shelterListContainer"></div>
        <div id="pagingArea" class="paging"></div>'
        <script>
            window.addEventListener("DOMContentLoaded", shelterList(1));
        </script>
    </body>

    </html>