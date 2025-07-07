<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
    <!DOCTYPE html>
    <html>

    <head>
        <meta charset="UTF-8">
        <title>당신에게 다시가는 길 - 유기동물 상세 정보</title>
        <style>
            /* ───────── 전체 래퍼 ───────── */
            .animal-detail {
                max-width: 1200px;
                margin: 40px auto;
                display: flex;
                gap: 32px;
                font-size: 15px;
                line-height: 1.55;
            }

            /* ───────── 좌측 사진 ───────── */
            .animal-image {
                width: 280px;
                aspect-ratio: 1 / 1;
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

            /* ───────── 우측 정보 영역 ───────── */
            .animal-info {
                flex: 1 1 0;
                display: grid;
                grid-template-columns: 150px 1fr;
                row-gap: 8px;
                column-gap: 12px;
            }

            .animal-info h2 {
                grid-column: 1 / 3;
                margin: 0 0 12px 0;
                font-size: 22px;
                font-weight: 700;
            }

            .animal-info div span:first-child {
                font-weight: 600;
                color: #444;
            }

            .animal-info .btn-group {
                grid-column: 1 / 3;
                display: flex;
                gap: 12px;
                margin-top: 18px;
            }

            .animal-info input[type="button"] {
                grid-column: 1 / 3;
                width: 150px;
                height: 40px;
                border: none;
                border-radius: 20px;
                background: #d9d9d9;
                cursor: pointer;
                font-weight: 600;
            }

            .animal-info input[type="button"]:hover {
                background: #c4c4c4;
            }

            /* ───────── 소개 ───────── */
            .description-wrapper {
                max-width: 1200px;
                margin: 20px auto 0 auto;
            }

            .description-wrapper .label {
                font-weight: 600;
                color: #444;
                margin-bottom: 6px;
            }

            #description {
                padding: 14px 16px;
                background: #f6f6f8;
                border-radius: 12px;
                width: 100%;
                line-height: 1.6;
                white-space: pre-line;
                box-sizing: border-box;
            }

            /* ───────── Divider ───────── */
            .animal-detail+hr {
                max-width: 1200px;
                margin: 40px auto;
                border: none;
                height: 1px;
                background: #e0e0e0;
            }

            /* ───────── 보호소 정보 ───────── */
            .shelter-wrapper {
                max-width: 1200px;
                margin: 30px auto;
                display: flex;
                gap: 32px;
                align-items: flex-start;
                background: #f6f6f8;
                border-radius: 16px;
                padding: 24px;
                box-sizing: border-box;
            }

            .shelter-info {
                flex: 1;
                font-size: 14px;
                line-height: 1.6;
            }

            .shelter-vertical>div {
                margin-bottom: 8px;
            }

            .shelter-vertical span:first-child {
                font-weight: 600;
                color: #444;
                display: inline-block;
                width: 80px;
            }

            .shelter-map {
                width: 240px;
                height: 208px;
                border-radius: 12px;
                overflow: hidden;
                flex-shrink: 0;
            }

            /* ─── "자세히 보기" 버튼 (보호소 카드용) ─── */
            .shelter-info .btn-group input[type="button"] {
                width: 100px;
                height: 28px;
                padding: 0 12px;
                border: none;
                border-radius: 18px;
                background: #3ACDB2;
                color: #fff;
                font-weight: 600;
                cursor: pointer;
                transition: background 0.2s ease, transform 0.1s ease;
            }

            .shelter-info .btn-group input[type="button"]:hover {
                background: #32b9a1;
            }

            .shelter-info .btn-group input[type="button"]:active {
                transform: scale(0.97);
            }
        </style>
        <script src="${pageContext.request.contextPath}/resources/web/common/commonUtils.js"></script>

<%
    String kakaoApiKey = "LOAD_FAILED"; // 기본값 – 로드 실패 시 확인용
    try {
        java.util.Properties props = new java.util.Properties();
        // 클래스패스 기준으로 로드 (WAR 내부 /WEB-INF/classes)
        java.io.InputStream in = Thread.currentThread()
                                        .getContextClassLoader()
                                        .getResourceAsStream("mapKey.properties");
        if (in != null) {
            props.load(in);
            kakaoApiKey = props.getProperty("KAKAO_MAP_API_KEY", "LOAD_FAILED").trim();
        }
    } catch (Exception ignored) {}
    pageContext.setAttribute("kakaoApiKey", kakaoApiKey);
%>

  <script src="https://dapi.kakao.com/v2/maps/sdk.js?appkey=${kakaoApiKey}&autoload=false&libraries=services"></script>

        <script>
            // 유기동물 상세 조회 함수 
            async function animalDetail() {
                const idx = location.pathname.split("/").pop();
                const result = await API.get('/care/api/animals/' + idx);
                if (result.status != 200) {
                    location.href = '/care/animals';
                    return;
                }
                document.getElementById("body").style.display = "block";
                const animal = result.data;

                document.getElementById("animalImage").src = '${pageContext.request.contextPath}' + animal.imagePath;
                document.getElementById("animalName").textContent = animal.name;
                document.getElementById("idx").textContent = animal.idx;
                document.getElementById("gender").textContent = animal.gender === "M" ? "남" : "여";
                document.getElementById("age").textContent = animal.age;
                document.getElementById("size").textContent = animal.size + "kg";
                document.getElementById("neuter").textContent = animal.neuter === 1 ? "O" : "X";
                document.getElementById("breed").textContent = animal.breed;
                document.getElementById("type").textContent = animal.type;
                document.getElementById("personality").textContent = animal.personality;
                document.getElementById("adoptionStatus").textContent = animal.adoptionStatus;
                document.getElementById("createdAt").textContent = animal.createdAt;
                document.getElementById("adoption").setAttribute("onclick", "location.href='/care/animals/" + animal.idx + "/adoption'");
                document.getElementById("description").innerText = animal.description;

                document.getElementById("shelterName").textContent = animal.shelterName;
                document.getElementById("shelterPersonName").textContent = animal.shelterPersonName;
                document.getElementById("shelterTel").textContent = animal.shelterTel;
                document.getElementById("shelterZipCode").textContent = animal.shelterZipCode;
                document.getElementById("shelterAddress").textContent = animal.shelterAddress;
                document.getElementById("shelterAddressDetail").textContent = animal.shelterAddressDetail;

                document.querySelector('input[value="자세히 보기"]').onclick = function () {
                    location.href = '/care/shelters/' + animal.userIdx;
                };

                if (animal.shelterAddress && animal.shelterAddressDetail) {
                    const fullAddress = animal.shelterAddress;
                    initMapByAddress(fullAddress);
                }
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
            <div class="animal-detail">
                <div class="animal-image">
                    <img id="animalImage" src="">
                </div>
                <div class="animal-info">
                    <h2 id="animalName">이름</h2>
                    <div><span>관리번호:</span> <span id="idx"></span></div>
                    <div><span>성별:</span> <span id="gender"></span></div>
                    <div><span>나이:</span> <span id="age"></span>세</div>
                    <div><span>크기:</span> <span id="size"></span></div>
                    <div><span>중성화:</span> <span id="neuter"></span></div>
                    <div><span>품종:</span> <span id="breed"></span></div>
                    <div><span>종류:</span> <span id="type"></span></div>
                    <div><span>성격:</span> <span id="personality"></span></div>
                    <div><span>입양상태:</span> <span id="adoptionStatus"></span></div>
                    <div><span>등록일:</span> <span id="createdAt"></span></div>
                    <div class="btn-group">
                        <input type="button" id="adoption" value="입양상담신청">
                        <input type="button" value="목록" onclick="location.href='/care/animals'">
                    </div>
                </div>
            </div>
            <div class="description-wrapper">
                <div class="label">소개</div>
                <div id="description"></div>
            </div>
            <div class="shelter-wrapper">
                <div class="shelter-info">
                    <h3>보호소 정보</h3>
                    <div class="shelter-vertical">
                        <div><span>보호소명:</span> <span id="shelterName"></span></div>
                        <div><span>담당자:</span> <span id="shelterPersonName"></span></div>
                        <div><span>연락처:</span> <span id="shelterTel"></span></div>
                        <div>
                            <span>주소:</span>
                            <span id="shelterZipCode"></span>
                            <span id="shelterAddress"></span>
                            <span id="shelterAddressDetail"></span>
                        </div>
                        <div class="btn-group">
                            <input type="button" value="자세히 보기">
                        </div>
                    </div>
                </div>
                <div class="shelter-map">
                    <div id="map" style="width:100%; height:100%; background:#ddd;"></div>
                </div>
            </div>
            <%@ include file="/WEB-INF/views/common/index/indexFooter.jsp" %>
                <script>
                    kakao.maps.load(() => {
                        animalDetail();
                    });
                </script>
    </body>

    </html>