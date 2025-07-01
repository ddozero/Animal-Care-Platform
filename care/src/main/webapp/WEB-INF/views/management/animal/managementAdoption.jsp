<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
    <!DOCTYPE html>
    <html>

    <head>
        <meta charset="UTF-8">
        <title>Insert title here</title>
        <script src="${pageContext.request.contextPath}/resources/web/common/commonUtils.js"></script>
        <script>
            async function shelterAdoptionList(cp = 1) {
                const adoptionList = document.getElementById("adoptionList");
                adoptionList.innerHTML = "";
                const table = document.createElement("table");
                table.setAttribute("border", "1");
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

                const result = await API.get('/care/api/management/animals/adoptions?cp=' + cp);
                if (result.errorCode === 401 || result.errorCode === 403) {
                    location.href = "/care/index";
                    return;
                }

                if (result.errorCode === 404) {
                    const tbodyRow = tbody.insertRow();
                    tbodyRow.innerHTML = "<td colspan='6'>등록되어 있는 게시글이 없습니다.</td>";
                    return;
                }

                if (result.status != 200) {
                    location.href = "/care/index";
                    return;
                }

                document.getElementById("body").style.display = "block";
                const adoptions = result.data;
                const pageInfo = result.pageInfo;

                for (const adoption of adoptions) {
                    const tbodyRow = tbody.insertRow();

                    let selectHtml = "<select name='statusIdx' required onchange='changeStatus(" + adoption.idx + ")'>";
                    selectHtml += "<option value='1'" + (adoption.statusIdx == 1 ? " selected" : "") + ">신청완료</option>";
                    selectHtml += "<option value='2'" + (adoption.statusIdx == 2 ? " selected" : "") + ">예약확정</option>";
                    selectHtml += "<option value='3'" + (adoption.statusIdx == 3 ? " selected" : "") + ">거절</option>";
                    selectHtml += "<option value='4'" + (adoption.statusIdx == 4 ? " selected" : "") + ">상담완료</option>";
                    selectHtml += "</select>";

                    tbodyRow.innerHTML =
                        "<td>" + adoption.idx + "</td>" +
                        "<td>" +
                        "<a href='#' onclick='adoptionInfo(" + adoption.animalIdx + "," + adoption.idx + ")'>" +
                        adoption.animalName +
                        "</a>" +
                        "</td>" +
                        "<td>" + adoption.name + "</td>" +
                        "<td>" + adoption.tel + "</td>" +
                        "<td>" + adoption.consultedAt + "</td>" +
                        "<td>" + selectHtml + "</td>";
                }

                const pagingArea = document.createElement("div");
                pagingArea.setAttribute("id", "pagingArea");
                adoptionList.appendChild(pagingArea);
                makePaging(
                    pageInfo.totalCnt,
                    pageInfo.listSize,
                    pageInfo.pageSize,
                    pageInfo.cp,
                    "pagingArea", // 페이지 버튼이 들어갈 div id
                    shelterAdoptionList
                );
            }

            async function changeStatus(idx) {
                const select = event.target;
                const selectedStatus = select.value;

                const changeStatus = {
                    statusIdx: Number(selectedStatus)
                };
                const result = await API.put("/care/api/management/animals/adoptions/" + idx, changeStatus);

                if (result === 200) {
                    alert(result.message);
                    location.reload();
                }
            }

            async function animalDetail(animalIdx) {
                const result = await API.get('/care/api/management/animals/' + animalIdx);
                if (result.status != 200) {
                    return;
                }
                const animal = result.data;
                document.getElementById("animalInfo").style.display = "block";
                // 조회 데이터
                document.getElementById("animalImage").src = '${pageContext.request.contextPath}' + animal.imagePath;
                document.getElementById("animalName").textContent = animal.name;
                document.getElementById("idx").textContent = animal.idx;
                document.getElementById("gender").textContent = animal.gender === "M" ? "남" : "여";
                document.getElementById("age").textContent = animal.age;
                document.getElementById("size").textContent = animal.size === 1 ? "소형" : animal.size === 2 ? "중형" : "대형";
                document.getElementById("neuter").textContent = animal.neuter === 1 ? "O" : "X";
                document.getElementById("breed").textContent = animal.breed;
                document.getElementById("type").textContent = animal.type;
                document.getElementById("personality").textContent = animal.personality;
                document.getElementById("adoptionStatus").textContent = animal.adoptionStatus;
            }

            async function consultInfo(consultIdx) {
                const result = await API.get('/care/api/management/animals/adoptions/' + consultIdx);
                if (result.status != 200) {
                    return;
                }
                const adoption = result.data;
                document.getElementById("adoptionInfo").style.display = "block";

                document.getElementById("consultIdxAdopt").textContent = adoption.idx;
                document.getElementById("nameAdopt").textContent = adoption.name;
                document.getElementById("emailAdopt").textContent = adoption.email;
                document.getElementById("telAdopt").textContent = adoption.tel;
                document.getElementById("zipCodeAdopt").textContent = adoption.zipCode;
                document.getElementById("addressAdopt").textContent = adoption.address;
                document.getElementById("addressDetailAdopt").textContent = adoption.addressDetail;
                document.getElementById("hasPetAdopt").textContent = adoption.hasPet ? "있음" : "없음";
                document.getElementById("consultedAtAdopt").textContent = adoption.consultedAt;
                document.getElementById("createdAtAdopt").textContent = adoption.createdAt;
                document.getElementById("statusAdopt").value = adoption.statusIdx;
                document.getElementById("statusAdopt").setAttribute("onchange", "changeStatus(" + adoption.idx + ")")
                document.getElementById("descriptionAdopt").textContent = adoption.description;
            }

            async function adoptionInfo(animalIdx, consultIdx) {
                animalDetail(animalIdx);
                consultInfo(consultIdx);
            }

            async function notViewInfo(){
                document.getElementById("animalInfo").style.display = "none";
                document.getElementById("adoptionInfo").style.display = "none";
            }
        </script>
    </head>

    <body id="body" style="display: none;">
        <%@ include file="/WEB-INF/views/common/management/managementHeader.jsp" %>
            <h1>입양상담 관리</h1>
            <div>
                <input type="button" value="유기동물 관리" onclick="location.href='/care/management/animals'">
                <input type="button" value="유기동물 등록" onclick="location.href='/care/management/animals/form'">
                <input type="button" value="입양상담 관리" onclick="location.href='/care/management/animals/adoptions'">
            </div>
            <div id="animalInfo" class="animal-detail" style="display: none;">
                <div class="animal-image">
                    <img id="animalImage" src="" width="200" height="200">
                </div>
                <div class="animal-info">
                    <div><span>관리번호:</span> <span id="idx"></span></div>
                    <div><span>이름:</span> <span id="animalName"></span></div>
                    <div><span>성별:</span> <span id="gender"></span></div>
                    <div><span>나이:</span> <span id="age"></span>세</div>
                    <div><span>크기:</span> <span id="size"></span></div>
                    <div><span>중성화:</span> <span id="neuter"></span></div>
                    <div><span>품종:</span> <span id="breed"></span></div>
                    <div><span>종류:</span> <span id="type"></span></div>
                    <div><span>성격:</span> <span id="personality"></span></div>
                    <div><span>입양상태:</span> <span id="adoptionStatus"></span></div>
                </div>
                <hr>
            </div>
            <div id="adoptionInfo" class="adoption-info" style="display: none;">
                <div><span>상담번호:</span> <span id="consultIdxAdopt"></span></div>
                <div><span>신청자 이름:</span> <span id="nameAdopt"></span></div>
                <div><span>이메일:</span> <span id="emailAdopt"></span></div>
                <div><span>전화번호:</span> <span id="telAdopt"></span></div>
                <div><span>우편번호:</span> <span id="zipCodeAdopt"></span></div>
                <div><span>주소:</span> <span id="addressAdopt"></span></div>
                <div><span>상세주소:</span> <span id="addressDetailAdopt"></span></div>
                <div><span>반려동물 유무:</span> <span id="hasPetAdopt"></span></div>
                <div><span>상담 예정일:</span> <span id="consultedAtAdopt"></span></div>
                <div><span>신청일:</span> <span id="createdAtAdopt"></span></div>
                <div>
                    <label for="statusAdopt">상태:</label>
                    <select id="statusAdopt" name="statusAdopt" required>
                        <option value="1">신청완료</option>
                        <option value="2">예약확정</option>
                        <option value="3">거절</option>
                        <option value="4">상담완료</option>
                    </select>
                </div>
                <div><span>입양 사유</span><br><span id="descriptionAdopt" style="white-space: pre-line;"></span></div>
                <input type="button" value="접기" onclick="notViewInfo()">
                <hr>
            </div>
            <div id="adoptionList"></div>
            <%@ include file="/WEB-INF/views/common/index/indexFooter.jsp" %>
            <script>
                window.addEventListener("DOMContentLoaded", shelterAdoptionList());
            </script>
    </body>

    </html>