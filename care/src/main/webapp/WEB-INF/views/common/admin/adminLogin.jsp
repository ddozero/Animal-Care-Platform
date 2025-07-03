<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
  <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
    <!DOCTYPE html>
    <html>

    <head>
      <meta charset="UTF-8">
      <title>로그인 페이지</title>
      <style>
        /* 전체 초기화 */
        html,
        body {
          margin: 0;
          padding: 0;
          box-sizing: border-box;
          font-family: 'Noto Sans KR', sans-serif;
          background-color: #f5f5f5;
        }

        *,
        *::before,
        *::after {
          box-sizing: inherit;
        }

        /* float 정리용 */
        .clearfix::after {
          content: "";
          display: block;
          clear: both;
        }

        /* 전체 wrapper를 화면 가운데 정렬 */
        .login-wrapper {
          display: flex;
          justify-content: center;
          align-items: center;
          height: calc(100vh - 200px);
          /* header/footer 여백 고려 */
          background-color: #ffffff;
        }

        /* 로그인 박스 */
        .login-container {
          background-color: #fff;
          padding: 40px 30px;
          border-radius: 12px;
          box-shadow: 0 2px 12px rgba(0, 0, 0, 0.1);
          width: 360px;
        }

        .login-container h2,
        .login-btn,
        .login-links {
          text-align: center;
        }

        /* 제목 */
        .login-container h2 {
          margin-bottom: 30px;
          font-size: 24px;
          color: #333;
        }

        /* 입력창 */
        .login-input {
          width: 100%;
          padding: 12px 14px;
          margin-bottom: 30px;
          border: 1px solid #ccc;
          border-radius: 8px;
          font-size: 15px;
          outline-color: #53D9C1;
          display: block;
          text-align: left;
        }

        /* 로그인 버튼 */
        .login-btn {
          width: 100%;
          padding: 12px;
          background-color: #53D9C1;
          border: none;
          border-radius: 8px;
          color: white;
          font-size: 16px;
          cursor: pointer;
          margin-top: 10px;
        }

        .login-btn:hover {
          background-color: #3bc0aa;
        }

        /* 링크 */
        .login-links {
          margin-top: 20px;
          font-size: 14px;
          color: #555;
        }

        .login-links a {
          color: #2e7f90;
          text-decoration: none;
        }

        .login-links a:hover {
          text-decoration: underline;
        }

        /* 에러 메시지 */
        .error-message {
          color: red;
          font-size: 14px;
          margin-top: 10px;
          min-height: 20px;
          text-align: center;
        }
      </style>
    </head>

    <body>
        <div class="login-wrapper">
          <div class="login-container">
            <h2>관리자 로그인</h2>

            <input type="text" id="id" name="id" placeholder="아이디 입력" class="login-input" />
            <input type="password" id="password" name="password" placeholder="비밀번호 입력" class="login-input" />

            <button id="loginBtn" class="login-btn">로그인</button>

            <div id="errorMessage" class="error-message"></div>
          </div>
        </div>

        <script>
          document.getElementById('loginBtn').addEventListener('click', async function () {
            const data = {
              id: document.getElementById('id').value,
              password: document.getElementById('password').value
            };

            try {
              const response = await fetch('/care/api/auth/admin/login', {
                method: 'POST',
                headers: {
                  'Content-Type': 'application/json'
                },
                body: JSON.stringify(data)
              });

              const result = await response.json();

              if (response.ok && (result.status === 'OK' || result.status === 200)) {
                alert('로그인 성공');
                window.location.href = '/care/admin/members';
              } else {
                document.getElementById('errorMessage').innerText = result.errorMsg || '로그인 실패';
              }
            } catch (error) {
              console.error('서버 오류:', error);
              document.getElementById('errorMessage').innerText = '서버 오류 발생';
            }
          });
        </script>
    </body>

    </html>