<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
    </main>
    </div>

    <!-- 사이드바 -->
    <aside class="sidebar">
        <div class="logo">로고</div>
        <nav class="sidebar-nav">
            <div class="menu-category">
                <h3>회원관리</h3>
                <a href="/care/admin/shelters" class="<%= "member_shelter".equals(currentPage) ? "active" : "" %>">보호소 회원 관리</a>
            </div>
            <div class="menu-category">
                <h3>기부</h3>
                <a href="/care/admin/donations" class="<%= "donation_list".equals(currentPage) ? "active" : "" %>">지원사업 관리</a>
                <a href="/care/admin/donations/add" class="<%= "donation_form".equals(currentPage) ? "active" : "" %>">지원사업 등록</a>
            </div>
            <div class="menu-category">
                <h3>공지사항</h3>
                <a href="/care/admin/notices" class="<%= "notices".equals(currentPage) ? "active" : "" %>">공지사항
                    관리</a>
                <a href="/care/admin/notices/form" class="<%= "notice_form".equals(currentPage) ? "active" : ""
                    %>">공지사항 등록</a>
            </div>
        </nav>
    </aside>
    </div>
    </body>

    </html>