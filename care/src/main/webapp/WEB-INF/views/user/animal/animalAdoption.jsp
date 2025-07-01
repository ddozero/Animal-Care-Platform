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

            /* 전체 페이지 가운데 정렬 */
            .page-wrapper {
                max-width: 1000px;
                margin: 0 auto;
                padding: 40px 20px;
            }

            /* 좌우 배치 */
            .adoption-wrapper {
                display: flex;
                justify-content: center;
                gap: 60px;
                align-items: stretch;
            }

            /* 동물 카드 */
            .animal-card {
                border: 2px solid #3ACDB2;
                border-radius: 12px;
                padding: 16px;
                background-color: #fff;
                width: 300px;
                box-shadow: 0 0 10px rgba(0, 0, 0, 0.05);
            }

            .animal-card {
                height: 100%;
                /* 폼 높이와 같게 */
                display: flex;
                flex-direction: column;
                justify-content: flex-start;
            }

            .animal-card img {
                width: 100%;
                aspect-ratio: 1 / 1;
                object-fit: cover;
                border-radius: 8px;
                margin-bottom: 12px;
            }

            .animal-card h2 {
                text-align: center;
                margin-bottom: 10px;
                font-size: 22px;
            }

            .animal-card div {
                margin-bottom: 6px;
            }

            .animal-card span:first-child {
                font-weight: bold;
                display: inline-block;
                width: 80px;
            }

            /* 폼 디자인 */
            .adoption-form {
                flex: 1;
                background: #f9f9f9;
                padding: 24px;
                border-radius: 12px;
                box-shadow: 0 0 10px rgba(0, 0, 0, 0.05);
                display: flex;
                flex-direction: column;
                gap: 16px;
            }

            .adoption-form {
                min-width: 350px;
                max-width: 400px;
            }

            .adoption-form label {
                display: block;
                font-weight: 600;
                margin-bottom: 4px;
            }

            .adoption-form input[type="text"],
            .adoption-form input[type="email"],
            .adoption-form input[type="tel"],
            .adoption-form input[type="number"],
            .adoption-form input[type="datetime-local"],
            .adoption-form textarea,
            .adoption-form select {
                width: 100%;
                padding: 10px;
                font-size: 15px;
                border: 1px solid #ccc;
                border-radius: 6px;
                box-sizing: border-box;
            }

            .adoption-form textarea {
                resize: vertical;
            }

            .adoption-form input[type="submit"] {
                background-color: #3ACDB2;
                color: white;
                font-weight: bold;
                padding: 12px;
                border: none;
                border-radius: 6px;
                cursor: pointer;
                transition: background 0.2s ease;
            }

            .adoption-form input[type="submit"]:hover {
                background-color: #34b3a1;
            }

            .adoption-form .button-wrapper {
                text-align: center;
            }
        </style>
        <script src="${pageContext.request.contextPath}/resources/web/common/commonUtils.js"></script>
        <script>
            const path = location.pathname.split("/");
            const idx = path[3];

            // 유기동물 정보 조회 함수 
            async function animalInfo() {
                const result = await API.get('/care/api/animals/' + idx + '/adoption');
                if (result.status != 200) {
                    history.back();
                    return;
                }
                document.getElementById("body").style.display = "block";
                const animal = result.data;

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
                document.getElementById("createdAt").textContent = animal.createdAt;
                document.getElementById("shelterName").textContent = animal.shelterName;

            }
            // 입양 상담 신청 함수 
            async function submitAdoption() {
                const form = getJsonFromForm("adoptionForm");
                const result = await API.post('/care/api/animals/' + idx + '/adoption', form);

                if (result.status === 201) {
                    alert(result.message);
                    history.back();
                    return;
                }
            }
        </script>
    </head>

    <body id="body" style="display: none;">
        <%@ include file="/WEB-INF/views/common/index/indexHeader.jsp" %>
            <div class="header-title">입양 상담 신청</div>
            <div class="title-detail">유기동물의 입양 상담을 신청합니다.</div>
            <div class="page-wrapper">
                <div class="adoption-wrapper">
                    <div class="animal-card">
                        <div class="animal-card-inner">
                            <img id="animalImage" src="">
                            <div class="animal-text">
                                <h2 id="animalName">이름</h2>
                                <div><span>관리번호:</span> <span id="idx"></span></div>
                                <div><span>성별:</span> <span id="gender"></span></div>
                                <div><span>나이:</span> <span id="age"></span>세</div>
                                <div><span>크기:</span> <span id="size"></span></div>
                                <div><span>중성화:</span> <span id="neuter"></span></div>
                                <div><span>품종:</span> <span id="breed"></span></div>
                                <div><span>종류:</span> <span id="type"></span></div>
                                <div><span>성격:</span> <span id="personality"></span></div>
                                <div><span>등록일:</span> <span id="createdAt"></span></div>
                                <div><span>보호소명:</span> <span id="shelterName"></span></div>
                            </div>
                        </div>
                    </div>
                    <form id="adoptionForm" class="adoption-form" method="post"
                        onsubmit="submitAdoption(); return false;">
                        <div>
                            <label>이름:</label>
                            <input type="text" name="name" required>
                        </div>

                        <div>
                            <label>이메일:</label>
                            <input type="email" name="email" required>
                        </div>

                        <div>
                            <label>전화번호:</label>
                            <input type="tel" name="tel" required placeholder="01012345678">
                        </div>

                        <div>
                            <label>우편번호:</label>
                            <input type="number" name="zipCode" required>
                        </div>

                        <div>
                            <label>주소:</label>
                            <input type="text" name="address" required>
                        </div>

                        <div>
                            <label>상세주소:</label>
                            <input type="text" name="addressDetail" required>
                        </div>

                        <div>
                            <label>반려동물 유무:</label>
                            <select name="hasPet" required>
                                <option value="1">있음</option>
                                <option value="0">없음</option>
                            </select>
                        </div>

                        <div>
                            <label>상담 희망일시:</label>
                            <input type="datetime-local" name="consultedAt" required>
                        </div>

                        <div>
                            <label>상세 내용:</label>
                            <textarea name="description" rows="4" required></textarea>
                        </div>

                        <div class="button-wrapper">
                            <input type="submit" value="입양 상담 신청">
                        </div>
                    </form>
                </div>
            </div>
            <script>
                window.addEventListener("DOMContentLoaded", animalInfo);
            </script>
    </body>

    </html>