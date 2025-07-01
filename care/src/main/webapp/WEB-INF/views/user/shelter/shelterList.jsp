<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
    <!DOCTYPE html>
    <html>

    <head>
        <meta charset="UTF-8">
        <title>Insert title here</title>
        <style>
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
                max-width: 1200px;
                margin: 0 auto;
                padding-bottom: 20px;
                box-sizing: border-box;
                text-align: center;
            }

            .search-bar {
                display: flex;
                align-items: center;
                gap: 10px;
                flex-wrap: wrap;
                font-size: 14px;
            }

            .search-bar label {
                display: flex;
                align-items: center;
                gap: 4px;
                font-weight: 500;
                color: #333;
            }

            .search-bar span {
                display: inline-block;
                min-width: auto;
            }

            .search-bar select,
            .search-bar input[type="text"] {
                padding: 6px 10px;
                font-size: 14px;
                border: 1px solid #ccc;
                border-radius: 6px;
                outline: none;
            }

            .search-bar input[type="submit"] {
                background-color: #3acdb2;
                border: none;
                color: white;
                padding: 7px 16px;
                border-radius: 6px;
                font-weight: bold;
                cursor: pointer;
                transition: 0.3s;
            }

            .search-bar input[type="submit"]:hover {
                background-color: #2ba293;
            }


            .container {
                max-width: 1200px;
                margin: 0 auto;
                padding: 20px;
            }

            /* 카드 그리드 컨테이너 */
            #shelterListContainer {
                margin-top: 18px;
                display: grid;
                grid-template-columns: repeat(5, 1fr);
                gap: 24px;
            }

            #shelterListContainer>div {
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

            #shelterListContainer>div {
                background: #ffffff;
                border-radius: 12px;
                overflow: hidden;
                box-shadow: 0 2px 8px rgba(0, 0, 0, 0.06);
                transition: transform 0.2s;
                text-align: left;
                display: flex;
                flex-direction: column;
            }

            #shelterListContainer>div:hover {
                transform: translateY(-4px);
            }

            #shelterListContainer img {
                width: 100%;
                aspect-ratio: 1 / 1;
                object-fit: cover;
                display: block;
                border-radius: 0;
            }

            .shelter-name {
                font-weight: 600;
                font-size: 14.5px;
                line-height: 1.6;
                padding: 12px;
                white-space: normal;
                word-break: keep-all;
                color: #333;
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
                        '<div class="badge">' + shelter.shelterType + '</div>' +
                        '<a href="shelters/' + shelter.idx + '">' +
                        '<img src="' + '${pageContext.request.contextPath}' + shelter.imagePath + '" alt="' + shelter.shelterName + '" />' +
                        '</a>' +
                        '<div class="shelter-name">' + shelter.shelterName + '</div>';
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
            <div class="header-title">보호시설</div>
            <div class="title-detail">전국의 다양한 보호시설을 만나보세요!</div>
            <div class="container">
                <form id="searchForm" onsubmit="searchShelters(); return false;" style="margin-bottom: 20px;">
                    <div class="search-bar">
                        <label><span>주소</span></label>
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
                        <label><span>구분</span></label>
                        <select name="shelterType">
                            <option value="">전체</option>
                            <option value="공공">공공</option>
                            <option value="민간">민간</option>
                            <option value="사설">사설</option>
                        </select>
                        <label><span>이름</span></label>
                        <input type="text" name="shelterName">
                        <input type="submit" value="검색">
                    </div>
                </form>
                <div id="shelterListContainer"></div>
                <div id="pagingArea" class="paging"></div>'
            </div>
            <%@ include file="/WEB-INF/views/common/index/indexFooter.jsp" %>
                <script>
                    window.addEventListener("DOMContentLoaded", shelterList(1));
                </script>
    </body>

    </html>