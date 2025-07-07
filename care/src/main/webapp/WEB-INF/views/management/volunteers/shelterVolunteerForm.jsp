<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
    <%@ page session="true" %>
        <%@ page import="com.animal.api.auth.model.response.LoginResponseDTO" %>
            <% int loginUserId=0; if (session.getAttribute("loginUser") !=null) { loginUserId=((LoginResponseDTO)
                session.getAttribute("loginUser")).getIdx(); } %>
                <!DOCTYPE html>
                <html>

                <head>
                    <meta charset="UTF-8">
                    <title>당신에게 다시가는 길 - 봉사 등록/수정</title>
                    <script src="${pageContext.request.contextPath}/resources/web/common/commonUtils.js"></script>
                    <style>
                        body {
                            font-family: 'Pretendard', '맑은 고딕', sans-serif;
                            background-color: #fff;
                            padding: 40px 20px;
                        }

                        h2 {
                            text-align: center;
                            margin: 50px 0 30px;
                            font-size: 28px;
                            color: #3ACDB2;
                        }

                        .volunteer-detail-wrapper {
                            max-width: 1000px;
                            margin: 0 auto;
                        }

                        .volunteer-detail {
                            display: flex;
                            gap: 32px;
                            margin-bottom: 32px;
                        }

                        #preview-image {
                            width: 420px;
                            height: 420px;
                            object-fit: cover;
                            border-radius: 12px;
                            background-color: #eee;
                            flex-shrink: 0;
                        }

                        .volunteer-info {
                            flex: 1;
                            display: grid;
                            grid-template-columns: 120px 1fr;
                            row-gap: 10px;
                            column-gap: 16px;
                            align-items: center;
                        }

                        .volunteer-info label {
                            font-weight: 600;
                            color: #444;
                        }

                        .volunteer-info input,
                        .volunteer-info select,
                        .volunteer-info textarea {
                            padding: 8px 10px;
                            font-size: 15px;
                            border: 1px solid #ccc;
                            border-radius: 8px;
                        }

                        .volunteer-info textarea {
                            resize: vertical;
                            grid-column: 1 / -1;
                            min-height: 120px;
                        }

                        .btn-group {
                            grid-column: 1 / -1;
                            text-align: center;
                            margin-top: 24px;
                        }

                        .btn-group button {
                            padding: 10px 20px;
                            background-color: #3acdb2;
                            color: #fff;
                            border: none;
                            border-radius: 8px;
                            font-weight: bold;
                            font-size: 15px;
                            cursor: pointer;
                            margin: 0 6px;
                            transition: background 0.2s;
                        }

                        .btn-group button:hover {
                            background-color: #32b8a1;
                        }

                        .btn-area-footer-space {
                            height: 60px;
                        }
                    </style>
                </head>

                <body data-login-user-id="<%= loginUserId %>">
                    <%@ include file="/WEB-INF/views/common/management/managementHeader.jsp" %>

                        <h2>봉사 등록/수정</h2>

                        <div class="volunteer-detail-wrapper">
                            <form id="volunteerForm" enctype="multipart/form-data" class="volunteer-detail">
                                <img id="preview-image"
                                    src="${pageContext.request.contextPath}/resources/images/no-image.png" alt="미리보기">

                                <div class="volunteer-info">
                                    <label>제목</label>
                                    <input type="text" id="title" name="title" required>

                                    <label>봉사일</label>
                                    <input type="date" id="volunteerDate" name="volunteerDate" required>

                                    <label>시간</label>
                                    <input type="number" id="time" name="time" required>

                                    <label>장소</label>
                                    <input type="text" id="location" name="location" required>

                                    <label>모집인원</label>
                                    <input type="number" id="capacity" name="capacity" required>

                                    <label>대상 연령</label>
                                    <input type="text" id="ageTarget" name="ageTarget">

                                    <label>유형</label>
                                    <select name="volunteerTypeIdx" required>
                                        <option value="">선택</option>
                                        <option value="1">산책봉사</option>
                                        <option value="2">미용봉사</option>
                                        <option value="3">청소봉사</option>
                                        <option value="4">기타</option>
                                    </select>

                                    <label>상태</label>
                                    <select name="volunteerStatusIdx" required>
                                        <option value="">선택</option>
                                        <option value="1">모집대기</option>
                                        <option value="2">모집중</option>
                                        <option value="3">모집완료</option>
                                        <option value="4">종료</option>
                                    </select>

                                    <label>내용</label>
                                    <textarea id="content" name="content" required></textarea>

                                    <label>이미지</label>
                                    <input type="file" id="image" name="image" accept="image/*"
                                        onchange="previewImage(event)">

                                    <div class="btn-group">
                                        <button type="button" onclick="submitForm()">저장</button>
                                    </div>
                                </div>
                            </form>
                        </div>

                        <div class="btn-area-footer-space"></div>

                        <script>
                            function previewImage(e) {
                                const file = e.target.files[0];
                                if (file) {
                                    const reader = new FileReader();
                                    reader.onload = function (event) {
                                        document.getElementById("preview-image").src = event.target.result;
                                    };
                                    reader.readAsDataURL(file);
                                }
                            }

                            async function submitForm() {
                                const form = document.getElementById("volunteerForm");
                                const idx = form.dataset.idx;

                                const json = {
                                    title: form.title.value,
                                    volunteerDate: form.volunteerDate.value,
                                    time: form.time.value,
                                    location: form.location.value,
                                    capacity: form.capacity.value,
                                    ageTarget: form.ageTarget.value,
                                    volunteerTypeIdx: form.volunteerTypeIdx.value,
                                    volunteerStatusIdx: form.volunteerStatusIdx.value,
                                    content: form.content.value,
                                    userIdx: <%= loginUserId %>
        };

                            let result;
                            if (idx) {
                                json.volunteerIdx = parseInt(idx);
                                result = await API.put("/care/api/management/volunteers/" + idx, json);
                            } else {
                                result = await API.post("/care/api/management/volunteers", json);
                            }

                            if (result.status === 201 || result.status === 200) {
                                const volunteerIdx = idx || result.data?.createdIdx;
                                if (!volunteerIdx) {
                                    alert("봉사 번호를 확인할 수 없습니다.");
                                    return;
                                }

                                const fileInput = document.getElementById("image");
                                if (fileInput.files.length > 0) {
                                    const imageFormData = new FormData();
                                    imageFormData.append("files", fileInput.files[0]);

                                    try {
                                        const uploadRes = await fetch("/care/api/management/volunteers/upload/" + volunteerIdx, {
                                            method: "POST",
                                            body: imageFormData
                                        });

                                        const uploadResult = await uploadRes.json();
                                        if (uploadRes.status !== 201) {
                                            alert("이미지 업로드 실패: " + (uploadResult.message || ""));
                                            return;
                                        }
                                    } catch (err) {
                                        alert("이미지 업로드 중 오류 발생");
                                        console.error(err);
                                        return;
                                    }
                                }

                                alert("저장 성공");
                                location.href = "/care/management/volunteers/" + volunteerIdx;
                            } else {
                                alert(result.errorMsg || "저장 실패");
                            }
    }

                            window.addEventListener("DOMContentLoaded", async function () {
                                const params = new URLSearchParams(location.search);
                                const idx = params.get("idx");

                                if (idx) {
                                    const result = await API.get('/care/api/management/volunteers/' + idx);
                                    if (result.status === 200) {
                                        const data = result.data;
                                        const form = document.getElementById("volunteerForm");
                                        form.dataset.idx = data.idx;

                                        document.getElementById("title").value = data.title;
                                        document.getElementById("volunteerDate").value = data.volunteerDate;
                                        document.getElementById("time").value = data.time;
                                        document.getElementById("location").value = data.location;
                                        document.getElementById("capacity").value = data.capacity;
                                        document.getElementById("content").value = data.content;
                                        document.getElementById("ageTarget").value = data.ageTarget;
                                        document.querySelector("select[name='volunteerTypeIdx']").value = data.volunteerTypeIdx;
                                        document.querySelector("select[name='volunteerStatusIdx']").value = data.volunteerStatusIdx;

                                        const imgPath = data.imagePath
                                            ? '${pageContext.request.contextPath}' + data.imagePath
                                            : '${pageContext.request.contextPath}/resources/images/no-image.png';
                                        document.getElementById("preview-image").src = imgPath;
                                    } else {
                                        alert("데이터 로딩 실패");
                                        history.back();
                                    }
                                }
                            });
                        </script>

                        <%@ include file="/WEB-INF/views/common/index/indexFooter.jsp" %>
                </body>

                </html>