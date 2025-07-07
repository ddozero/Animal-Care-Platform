<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
    <!DOCTYPE html>
    <html>

    <head>
        <meta charset="UTF-8">
        <title>당신에게 다시가는 길 - 입양상담 신청 관리</title>

        <link rel="stylesheet" href="https://cdn.jsdelivr.net/gh/orioncactus/pretendard/dist/web/static/pretendard.css">

        <style>
            .container {
                max-width: 1200px;
                margin: 0 auto;
                padding: 0 20px 60px;
            }

            /* ───── 테이블 공통 디자인 ───── */
            .board-table {
                width: 100%;
                border-collapse: collapse;
                font-size: 15px;
            }

            .board-table th {
                padding: 14px 10px;
                border-bottom: 2px solid #DBDBDB;
                text-align: center;
                color: #333;
                background-color: #fcfcfc;
            }

            .board-table td {
                padding: 14px 10px;
                border-bottom: 1px solid #eee;
                text-align: center;
                color: #333;
            }

            .board-table td a {
                color: #007bff;
                text-decoration: none;
            }

            .board-table td a:hover {
                text-decoration: underline;
            }

            .board-table tbody tr:hover {
                background-color: #fafafa;
            }

            /* ───── 페이징 버튼 ───── */
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

            .animal-image {
                width: 280px;
                aspect-ratio: 1/1;
                background: #e5e5e5;
                border-radius: 12px;
                overflow: hidden;
                flex-shrink: 0;
            }

            .animal-image img {
                width: 100%;
                height: 100%;
                object-fit: cover;
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


            .details-container {
                margin: 40px 0;
                display: flex;
                gap: 40px;
                /* 양쪽 컬럼 사이 간격 */
                font-size: 15px;
                line-height: 1.6;
                padding: 32px;
                border: 1px solid #e0e0e0;
                border-radius: 16px;
                background-color: #ffffff;
                position: relative;
                box-shadow: 0 4px 12px rgba(0, 0, 0, 0.05);
            }

            .info-column {
                flex: 1;
                min-width: 0;
            }

            .info-column h3 {
                font-size: 22px;
                font-weight: 700;
                margin: 0 0 20px 0;
                padding-bottom: 15px;
                border-bottom: 2px solid #333;
                color: #333;
            }

            .animal-detail-layout {
                display: flex;
                flex-direction: column;
                gap: 24px;
            }

            .adoption-info-layout {
                display: flex;
                flex-direction: column;
                gap: 16px;
            }

            .animal-info-grid {
                display: grid;
                grid-template-columns: 80px 1fr 80px 1fr;
                /* 라벨-값 2쌍을 위한 4열 그리드 */
                gap: 12px 16px;
                /* 행, 열 간격 */
                align-items: center;
            }

            .adoption-info-grid {
                display: grid;
                grid-template-columns: 100px 1fr;
                /* 라벨-값 1쌍을 위한 2열 그리드 */
                gap: 12px 16px;
                align-items: center;
            }

            .info-label {
                font-weight: 600;
                color: #555;
            }

            .info-value {
                color: #333;
            }

            .full-width-item {
                grid-column: 1 / -1;
                /* 그리드 전체 너비 차지 */
            }

            .full-width-item .info-label {
                margin-bottom: 8px;
                display: block;
            }

            .full-width-item .info-value {
                white-space: pre-line;
                background-color: #f9f9f9;
                padding: 12px;
                border-radius: 8px;
                min-height: 80px;
                border: 1px solid #eee;
            }

            .close-button {
                position: absolute;
                top: 24px;
                right: 24px;
                background: transparent;
                border: none;
                font-size: 28px;
                line-height: 1;
                cursor: pointer;
                color: #888;
                transition: all .2s;
            }

            .close-button:hover {
                color: #333;
                transform: rotate(90deg);
            }

            #statusAdopt {
                padding: 6px 10px;
                border-radius: 6px;
                border: 1px solid #ccc;
                font-size: 14px;
            }
        </style>

        <script src="${pageContext.request.contextPath}/resources/web/common/commonUtils.js"></script>
        <script>
            async function shelterAdoptionList(cp) {
                cp = cp || 1;

                const adoptionList = document.getElementById("adoptionList");
                adoptionList.innerHTML = "";

                /* ─── 목록 테이블 ─── */
                const table = document.createElement("table");
                table.className = "board-table";
                adoptionList.appendChild(table);

                const thead = document.createElement("thead");
                table.appendChild(thead);
                const theadRow = thead.insertRow();
                theadRow.innerHTML =
                    "<th>번호</th>" +
                    "<th>동물명</th>" +
                    "<th>신청자</th>" +
                    "<th>연락처</th>" +
                    "<th>상담예정일</th>" +
                    "<th>상태</th>";

                const tbody = document.createElement("tbody");
                table.appendChild(tbody);

                const result = await API.get("/care/api/management/animals/adoptions?cp=" + cp);

                if (result.errorCode === 401 || result.errorCode === 403) {
                    location.href = "/care/index";
                    return;
                }
                if (result.errorCode === 404) {
                    tbody.insertRow().innerHTML = "<td colspan='6'>등록되어 있는 게시글이 없습니다.</td>";
                    return;
                }
                if (result.status !== 200) {
                    location.href = "/care/index";
                    return;
                }

                document.getElementById("body").style.display = "block";
                const adoptions = result.data;
                const pageInfo = result.pageInfo;

                for (const adoption of adoptions) {
                    const tr = tbody.insertRow();
                    tr.setAttribute("onclick", "event.preventDefault(); adoptionInfo(" + adoption.animalIdx + "," + adoption.idx + ")");


                    let selectHtml = "<select name='statusIdx' required onchange='changeStatus(" + adoption.idx + ")' style='padding: 5px; border-radius: 5px; border: 1px solid #ccc;'>";
                    selectHtml += "<option value='1'" + (adoption.statusIdx == 1 ? " selected" : "") + ">신청완료</option>";
                    selectHtml += "<option value='2'" + (adoption.statusIdx == 2 ? " selected" : "") + ">예약확정</option>";
                    selectHtml += "<option value='3'" + (adoption.statusIdx == 3 ? " selected" : "") + ">거절</option>";
                    selectHtml += "<option value='4'" + (adoption.statusIdx == 4 ? " selected" : "") + ">상담완료</option>";
                    selectHtml += "</select>";

                    tr.innerHTML =
                        "<td>" + adoption.idx + "</td>" +
                        "<td>" + adoption.animalName + "</td>" +
                        "<td>" + adoption.name + "</td>" +
                        "<td>" + adoption.tel + "</td>" +
                        "<td>" + adoption.consultedAt + "</td>" +
                        "<td>" + selectHtml + "</td>";
                tr.querySelector("select").addEventListener("click", e => e.stopPropagation());
                }

                /* ─── 페이징 영역 ─── */
                const pagingArea = document.createElement("div");
                pagingArea.id = "pagingArea";
                pagingArea.className = "paging";
                adoptionList.appendChild(pagingArea);

                makePaging(
                    pageInfo.totalCnt,
                    pageInfo.listSize,
                    pageInfo.pageSize,
                    pageInfo.cp,
                    "pagingArea",
                    shelterAdoptionList
                );
            }

            async function changeStatus(idx) {
                const ps = Number(event.target.value);
                const result = await API.put("/care/api/management/animals/adoptions/" + idx, { statusIdx: ps });
                if (result.status != 200) {
                    alert("상태 변경에 실패했습니다.");
                }
            }

            async function animalDetail(animalIdx) {
                const r = await API.get("/care/api/management/animals/" + animalIdx);
                if (r.status !== 200) return;
                const a = r.data;
                document.getElementById("animalImage").src = '${pageContext.request.contextPath}' + a.imagePath;
                document.getElementById("animalImage").onerror = function () { this.src = 'https://placehold.co/280x280/e5e5e5/333?text=No+Image'; };
                document.getElementById("animalName").textContent = a.name || "-";
                document.getElementById("idx").textContent = a.idx || "-";
                document.getElementById("gender").textContent = a.gender === "M" ? "남" : "여";
                document.getElementById("age").textContent = a.age || "-";
                document.getElementById("size").textContent = a.size === 1 ? "소형" : a.size === 2 ? "중형" : "대형";
                document.getElementById("neuter").textContent = a.neuter === 1 ? "O" : "X";
                document.getElementById("breed").textContent = a.breed || "-";
                document.getElementById("type").textContent = a.type || "-";
                document.getElementById("personality").textContent = a.personality || "-";
                document.getElementById("adoptionStatus").textContent = a.adoptionStatus || "-";
            }

            async function consultInfo(consultIdx) {
                const r = await API.get("/care/api/management/animals/adoptions/" + consultIdx);
                if (r.status !== 200) return;
                const d = r.data;
                document.getElementById("nameAdopt").textContent = d.name || "-";
                document.getElementById("emailAdopt").textContent = d.email || "-";
                document.getElementById("telAdopt").textContent = d.tel || "-";
                document.getElementById("zipCodeAdopt").textContent = d.zipCode || "";
                document.getElementById("addressAdopt").textContent = d.address || "-";
                document.getElementById("addressDetailAdopt").textContent = d.addressDetail || "";
                document.getElementById("hasPetAdopt").textContent = d.hasPet ? "있음" : "없음";
                document.getElementById("consultedAtAdopt").textContent = d.consultedAt || "-";
                document.getElementById("createdAtAdopt").textContent = d.createdAt || "-";
                const sel = document.getElementById("statusAdopt");
                sel.value = d.statusIdx;
                sel.onchange = function () { changeStatus(d.idx); };
                document.getElementById("descriptionAdopt").textContent = d.description || "-";
            }

            function adoptionInfo(animalIdx, consultIdx) {
                animalDetail(animalIdx);
                consultInfo(consultIdx);
                document.getElementById("detailsContainer").style.display = "flex";
                window.scrollTo(0, 0); // 상세 정보 표시 시 페이지 상단으로 스크롤
            }

            function notViewInfo() {
                document.getElementById("detailsContainer").style.display = "none";
            }
        </script>
    </head>

    <body id="body" style="display:none;">
        <%@ include file="/WEB-INF/views/common/management/managementHeader.jsp" %>

            <div class="submenu-buttons">
                <input type="button" value="유기동물 조회" onclick="location.href='/care/management/animals'">
                <input type="button" value="유기동물 등록" onclick="location.href='/care/management/animals/form'">
                <input type="button" value="입양상담 관리" class="active"
                    onclick="location.href='/care/management/animals/adoptions'">
            </div>
            <div class="container">


                <div id="detailsContainer" class="details-container" style="display:none;">

                    <div class="info-column">
                        <h3>유기동물 정보</h3>
                        <div class="animal-detail-layout">
                            <div class="animal-image"><img id="animalImage" src="" alt="동물 사진"></div>
                            <div class="animal-info-grid">
                                <div class="info-label">관리번호</div>
                                <div class="info-value"><span id="idx"></span></div>
                                <div class="info-label">이름</div>
                                <div class="info-value"><span id="animalName"></span></div>
                                <div class="info-label">성별</div>
                                <div class="info-value"><span id="gender"></span></div>
                                <div class="info-label">나이</div>
                                <div class="info-value"><span id="age"></span>세</div>
                                <div class="info-label">품종</div>
                                <div class="info-value"><span id="breed"></span></div>
                                <div class="info-label">종류</div>
                                <div class="info-value"><span id="type"></span></div>
                                <div class="info-label">크기</div>
                                <div class="info-value"><span id="size"></span></div>
                                <div class="info-label">중성화</div>
                                <div class="info-value"><span id="neuter"></span></div>
                                <div class="info-label">입양상태</div>
                                <div class="info-value"><span id="adoptionStatus"></span></div>
                                <div class="info-label">성격</div>
                                <div class="info-value"><span id="personality"></span></div>
                            </div>
                        </div>
                    </div>


                    <div class="info-column">
                        <h3>신청인 정보</h3>
                        <div class="adoption-info-layout">
                            <div class="adoption-info-grid">
                                <div class="info-label">신청자 이름</div>
                                <div class="info-value"><span id="nameAdopt"></span></div>
                                <div class="info-label">신청자 이메일</div>
                                <div class="info-value"><span id="emailAdopt"></span></div>
                                <div class="info-label">신청자 연락처</div>
                                <div class="info-value"><span id="telAdopt"></span></div>
                                <div class="info-label">신청자 주소</div>
                                <div class="info-value"><span id="zipCodeAdopt"></span> <span id="addressAdopt"></span>
                                    <span id="addressDetailAdopt"></span>
                                </div>
                                <div class="info-label">반려동물 유무</div>
                                <div class="info-value"><span id="hasPetAdopt"></span></div>
                                <div class="info-label">상담 예정일</div>
                                <div class="info-value"><span id="consultedAtAdopt"></span></div>
                                <div class="info-label">신청일</div>
                                <div class="info-value"><span id="createdAtAdopt"></span></div>
                                <div class="info-label">상태</div>
                                <div class="info-value">
                                    <select id="statusAdopt" name="statusAdopt" required>
                                        <option value="1">신청완료</option>
                                        <option value="2">예약확정</option>
                                        <option value="3">거절</option>
                                        <option value="4">상담완료</option>
                                    </select>
                                </div>
                            </div>
                            <div class="full-width-item">
                                <div class="info-label">입양 사유</div>
                                <div class="info-value"><span id="descriptionAdopt"></span></div>
                            </div>
                        </div>
                    </div>
                    <button type="button" class="close-button" onclick="notViewInfo()" title="닫기">&times;</button>
                </div>


                <div id="adoptionList"></div>
            </div>
            <%@ include file="/WEB-INF/views/common/index/indexFooter.jsp" %>

                <script>
                    window.addEventListener("DOMContentLoaded", function () { shelterAdoptionList(); });
                </script>
    </body>

    </html>