<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
    <%@ page session="true" %>
        <%@ page import="com.animal.api.auth.model.response.LoginResponseDTO" %>
            <% int loginUserId=0; if (session.getAttribute("loginUser") !=null) { loginUserId=((LoginResponseDTO)
                session.getAttribute("loginUser")).getIdx(); } %>
                <!DOCTYPE html>
                <html>

                <head>

                    <meta charset="UTF-8">
                    <title>봉사 신청자 목록</title>
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

                        #applicationList {
                            max-width: 1000px;
                            margin: 0 auto;
                            display: flex;
                            flex-direction: column;
                            gap: 20px;
                        }

                        .application-card {
                            background: #f9f9fb;
                            padding: 24px;
                            border-radius: 12px;
                            box-shadow: 0 1px 6px rgba(0, 0, 0, 0.05);
                            border: 1px solid #eee;
                            line-height: 1.6;
                            font-size: 15px;
                            color: #333;
                        }

                        .application-card strong {
                            color: #222;
                        }

                        .application-card pre {
                            background-color: #f6f6f8;
                            padding: 12px;
                            border-radius: 8px;
                            white-space: pre-line;
                            font-size: 14px;
                        }

                        .application-card button {
                            margin-top: 12px;
                            margin-right: 8px;
                            padding: 8px 16px;
                            background-color: #3acdb2;
                            color: #fff;
                            border: none;
                            border-radius: 8px;
                            font-size: 14px;
                            font-weight: bold;
                            cursor: pointer;
                            transition: background 0.2s;
                        }

                        .application-card button:hover {
                            background-color: #32b8a1;
                        }

                        .paging {
                            margin: 40px 0;
                            text-align: center;
                        }
                    </style>

                    <script>
                        async function fetchApplications(cp = 1) {
                            const volunteerIdx = location.pathname.split("/")[4];
                            const result = await API.get("/care/api/management/volunteers/" + volunteerIdx + "/applications?cp=" + cp);

                            if (result.status !== 200) {
                                alert(result.errorMsg || "신청자 목록을 불러오지 못했습니다.");
                                return;
                            }

                            const data = result.data;
                            const pageInfo = result.pageInfo;
                            const container = document.getElementById("applicationList");
                            container.innerHTML = "";

                            for (const app of data) {
                                const div = document.createElement("div");
                                div.className = "application-card";

                                div.innerHTML =
                                    '<div><strong>이름:</strong> ' + app.name + '</div>' +
                                    '<div><strong>이메일:</strong> ' + app.email + '</div>' +
                                    '<div><strong>연락처:</strong> ' + app.tel + '</div>' +
                                    '<div><strong>생년월일:</strong> ' + app.birthDate + '</div>' +
                                    '<div><strong>단체명:</strong> ' + (app.groupName || "-") + '</div>' +
                                    '<div><strong>단체 연락처:</strong> ' + (app.groupTel || "-") + '</div>' +
                                    '<div><strong>남자:</strong> ' + app.male + '명 / <strong>여자:</strong> ' + app.female + '명</div>' +
                                    '<div><strong>신청일:</strong> ' + app.createdAt + '</div>' +
                                    '<div><strong>상태:</strong> <span>' + app.volunteerRequestStatusName + '</span></div>' +
                                    '<div><strong>신청내용:</strong><br><pre>' + (app.description || '') + '</pre></div>' +
                                    '<div>' +
                                    '<button onclick="approve(' + app.volunteerIdx + ',' + app.idx + ')">승인</button>' +
                                    '<button onclick="cancel(' + app.volunteerIdx + ',' + app.idx + ')">승인 취소</button>' +
                                    '</div>';

                                container.appendChild(div);
                            }

                            makePaging(
                                pageInfo.totalCnt,
                                pageInfo.listSize,
                                pageInfo.pageSize,
                                pageInfo.cp,
                                "pagingArea",
                                fetchApplications
                            );
                        }

                        async function approve(volunteerIdx, applicationIdx) {
                            const confirmed = confirm("정말 이 신청서를 승인하시겠습니까?");
                            if (!confirmed) return;

                            const result = await API.put("/care/api/management/volunteers/" + volunteerIdx + "/applications/" + applicationIdx + "/approval");

                            if (result.status === 200) {
                                alert("승인 완료");
                                fetchApplications(1);
                            } else {
                                alert(result.errorMsg || "승인 실패");
                            }
                        }

                        async function cancel(volunteerIdx, applicationIdx) {
                            const confirmed = confirm("정말 이 신청서의 승인을 취소하시겠습니까?");
                            if (!confirmed) return;

                            const result = await API.put("/care/api/management/volunteers/" + volunteerIdx + "/applications/" + applicationIdx + "/cancellation");

                            if (result.status === 200) {
                                alert("승인 취소 완료");
                                fetchApplications(1);
                            } else {
                                alert(result.errorMsg || "승인 취소 실패");
                            }
                        }

                        window.addEventListener("DOMContentLoaded", function () {
                            fetchApplications(1);
                        });
                    </script>

                </head>

                <body data-login-user-id="<%= loginUserId %>">
                    <%@ include file="/WEB-INF/views/common/management/managementHeader.jsp" %>

                        <h2>봉사 신청자 목록</h2>

                        <div id="applicationList"></div>
                        <div id="pagingArea" class="paging"></div>

                        <%@ include file="/WEB-INF/views/common/index/indexFooter.jsp" %>
                </body>

                </html>