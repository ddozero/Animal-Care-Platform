<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
    <!DOCTYPE html>
    <html>

    <head>
        <meta charset="UTF-8">
        <title>Insert title here</title>
        <link rel="stylesheet" as="style" crossorigin
            href="https://cdn.jsdelivr.net/gh/orioncactus/pretendard@v1.3.9/dist/web/static/pretendard.min.css" />
        <style>
            * {
                box-sizing: border-box;
                margin: 0;
                padding: 0;
            }

            html,
            body {
                height: 100%;
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

            .container {
                display: flex;
                height: 100vh;
            }

            .sidebar {
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

            .main-content {
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

            .page-content h1 {
                font-size: 1.875rem;
                font-weight: 700;
            }

            .page-content hr {
                margin: 16px 0;
                border: 0;
                border-top: 1px solid #e2e8f0;
            }

            .content-wrapper {
                background-color: #ffffff;
                padding: 32px;
                border-radius: 8px;
                box-shadow: 0 4px 6px -1px rgba(0, 0, 0, 0.1), 0 2px 4px -1px rgba(0, 0, 0, 0.06);
            }
        </style>
    </head>

    <body>

        <div class="container">
            <aside class="sidebar">
                <div class="logo">로고</div>

                <nav class="sidebar-nav">
                    <div class="menu-category">
                        <h3>회원관리</h3>
                        <a href="#">사이트 회원</a>
                    </div>
                    <div class="menu-category">
                        <h3>보호소</h3>
                        <a href="#" class="active">보호소 관리</a>
                        <a href="#">봉사 관리</a>
                        <a href="#">입양 관리</a>
                    </div>
                    <div class="menu-category">
                        <h3>기부</h3>
                        <a href="#">지원사업 현황</a>
                        <a href="#">지원사업 관리</a>
                    </div>
                    <div class="menu-category">
                        <h3>커뮤니티</h3>
                        <a href="#">게시물 관리</a>
                        <a href="#">공지사항</a>
                    </div>
                    <div class="menu-category">
                        <h3>통계</h3>
                        <a href="#">통계</a>
                    </div>
                </nav>
            </aside>

            <div class="main-content">
                <header class="header">
                    <div class="user-info">
                        <span>관리자</span>
                        <button class="logout-button">로그아웃</button>
                    </div>
                </header>

                <main class="page-content">
                    <h1>페이지 제목</h1>
                    <hr>
                    <div class="content-wrapper">
                        <p>콘텐츠 영역입니다.</p>
                    </div>
                </main>
            </div>
        </div>

    </body>

    </html>