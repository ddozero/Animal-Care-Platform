<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
    </main>
    </div>

    <!-- 사이드바 -->
    <aside class="sidebar">
        <div class="logo" style="margin-top: 20px; padding-bottom: 20px;">
            <img src="${pageContext.request.contextPath}/resources/web/images/indexheader/logo.png" alt="로고" style="height: 50px; vertical-align: middle; transform: scale(1.5); transform-origin: left center; margin-right: 20px;">
            <div style="display: flex; flex-direction: column; line-height: 1.2; font-family: 'Pretendard';">
	      <span style="font-size: 10px; color: #dbdbdb; letter-spacing: 0.5px;">당신에게</span>
	      <span style="font-size: 28px; font-weight: 800; color: #ffffff; letter-spacing: -1px;">다시 가는 길</span>
	    </div>
        </div>
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