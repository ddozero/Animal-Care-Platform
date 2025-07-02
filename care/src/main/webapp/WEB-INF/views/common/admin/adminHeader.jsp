<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%
    String currentPage = (String) request.getAttribute("currentPage");
    if (currentPage == null) {
        currentPage = "";
    }
%>
    <!DOCTYPE html>
    <html>

    <head>
        <meta charset="UTF-8">
        <title>Insert title here</title>
        <link rel="stylesheet" as="style" crossorigin
            href="https://cdn.jsdelivr.net/gh/orioncactus/pretendard@v1.3.9/dist/web/static/pretendard.min.css" />
        <style>
            /* --- 기본 리셋 및 전체 레이아웃 --- */
            * {
                box-sizing: border-box;
                margin: 0;
                padding: 0;
            }

            html,
            body {
                height: 100%;
                font-family: 'Pretendard', sans-serif;
                background-color: #f7fafc;
                color: #2d3748;
            }

            a {
                text-decoration: none;
                color: inherit;
            }

            button {
                border: none;
                cursor: pointer;
                background: none;
            }

            /* --- 메인 컨테이너 (Flexbox) --- */
            .container {
                display: flex;
                height: 100vh;
            }

            /* --- 사이드바 (order: 1 로 왼쪽에 표시) --- */
            .sidebar {
                order: 1;
                /* <<<<<<< 아이템 순서 1번 (왼쪽) */
                width: 256px;
                background-color: #1a202c;
                color: #ffffff;
                display: flex;
                flex-direction: column;
                flex-shrink: 0;
            }

            .sidebar .logo {
                height: 64px;
                display: flex;
                align-items: center;
                justify-content: center;
                font-size: 1.5rem;
                font-weight: 700;
                border-bottom: 1px solid #2d3748;
            }

            .sidebar-nav {
                flex-grow: 1;
                padding: 16px 8px;
            }

            .menu-category {
                margin-bottom: 8px;
            }

            .menu-category h3 {
                padding: 8px 16px;
                font-size: 0.75rem;
                font-weight: 600;
                color: #a0aec0;
                text-transform: uppercase;
                letter-spacing: 0.05em;
            }

            .menu-category a {
                display: block;
                padding: 8px 16px;
                margin-top: 4px;
                font-size: 0.875rem;
                border-radius: 4px;
                transition: background-color 0.2s;
            }

            .menu-category a:hover {
                background-color: #2d3748;
            }

            .menu-category a.active {
                background-color: #4a5568;
                color: #ffffff;
                border-left: 4px solid #3acdb2;
                padding-left: 12px;
            }

            /* --- 메인 콘텐츠 영역 (order: 2 로 오른쪽에 표시) --- */
            .main-content {
                order: 2;
                /* <<<<<<< 아이템 순서 2번 (오른쪽) */
                flex-grow: 1;
                display: flex;
                flex-direction: column;
                overflow: hidden;
            }

            .header {
                height: 64px;
                background-color: #ffffff;
                border-bottom: 1px solid #e2e8f0;
                display: flex;
                align-items: center;
                justify-content: flex-end;
                padding: 0 24px;
                flex-shrink: 0;
            }

            .header .user-info {
                display: flex;
                align-items: center;
            }

            .header .user-info span {
                margin-right: 12px;
                font-weight: 600;
            }

            .header .logout-button {
                padding: 8px 16px;
                font-size: 0.875rem;
                color: #ffffff;
                background-color: #3acdb2;
                border-radius: 4px;
                transition: background-color 0.2s;
            }

            .header .logout-button:hover {
                background-color: #2f9e88;
            }

            .page-content {
                flex-grow: 1;
                overflow-y: auto;
                padding: 24px;
            }
        </style>
        <script>
            function logout() {
                if (!confirm("로그아웃 하시겠습니까?")) return;
                const root = "${pageContext.request.contextPath}";
                fetch(`/care/api/auth/logout`, { method: 'POST' })
                    .then(res => res.ok ? res.json() : Promise.reject(res))
                    .then(result => {
                        if (result.status === 'OK' || result.status === 200) {
                            alert('로그아웃 되었습니다.');
                            location.href = `/care/index`;
                        } else {
                            alert(result.message || '로그아웃에 실패했습니다.');
                        }
                    })
                    .catch(err => {
                        console.error('로그아웃 처리 중 오류 발생:', err);
                        alert('서버에 연결할 수 없습니다.');
                    });
            }
        </script>
    </head>

    <body>
        <div class="container">
            <div class="main-content">
                <header class="header">
                    <div class="user-info">
                        <span>관리자</span>
                        <button class="logout-button" onclick="logout()">로그아웃</button>
                    </div>
                </header>
                <main class="page-content">