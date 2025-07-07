<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>당신에게 다시가는 길 - 마이페이지</title>
<style>
  * {
    box-sizing: border-box;
  }

  body {
    font-family: 'Noto Sans KR', sans-serif;
    margin: 0;
    background-color: #ffffff;
  }

  .wrap {
  max-width: 1280px;
  margin: 30px auto;
  padding: 0 20px;
  border-radius: 12px;
  box-shadow: rgba(60, 64, 67, 0.3) 0px 1px 2px 0px, rgba(60, 64, 67, 0.15) 0px 1px 3px 1px;
  }
  .container {
    display: flex;
    max-width: 1200px;
    margin: 0 auto;
    padding: 40px 20px;
  }

  /* 왼쪽 네비게이션 */
  .sidebar {
    width: 200px;
    margin-right: 40px;
  }

  .sidebar button {
    display: block;
    width: 100%;
    margin-bottom: 16px;
    padding: 12px 20px;
    background-color: #fff;
    border: 2px solid #53D9C1;
    color: #333;
    font-size: 16px;
    border-radius: 8px;
    cursor: pointer;
    transition: background-color 0.2s ease;
  }

  .sidebar button:hover {
    background-color: #53D9C1;
    color: #fff;
  }

  /* 우측 메인 */
  .main-content {
    flex: 1;
  }

  .user-greeting {
    display: flex;
    justify-content: space-between;
    align-items: center;
    margin-bottom: 30px;
  }

  .user-greeting h2 {
    font-size: 24px;
    color: #333;
  }

  .user-greeting .edit-btn {
    background-color: #53D9C1;
    color: #fff;
    border: none;
    padding: 8px 16px;
    border-radius: 6px;
    cursor: pointer;
  }

  /* 통계 카드 */
  .stats-box {
    background-color: #fff;
    border-radius: 12px;
    padding: 20px 24px;
    box-shadow: 0 2px 6px rgba(0, 0, 0, 0.05);
    margin-bottom: 30px;
    line-height: 1.8;
  }

  .stats-box p {
    margin: 0;
    font-size: 16px;
    color: #333;
  }

  /* 활동 히스토리 */
  .history-box {
    background-color: #fff;
    border-radius: 12px;
    padding: 20px 24px;
    box-shadow: 0 2px 6px rgba(0, 0, 0, 0.05);
  }

  .history-box h3 {
    margin-top: 0;
    font-size: 18px;
    color: #333;
    margin-bottom: 12px;
  }

  .history-box ul {
    list-style: none;
    padding: 0;
    margin: 0;
  }

  .history-box li {
    padding: 6px 0;
    font-size: 15px;
    color: #555;
  }

  .volunteer-list {
  max-height: 500px;
  overflow-y: auto;
  }

  /* 봉사 상세 슬라이드 패널 */
  .volunteer-detail {
  position: fixed;
  top: 0;
  right: 0;
  width: 460px;
  height: 100%;
  background: #fff;
  box-shadow: -2px 0 8px rgba(0, 0, 0, 0.1);
  z-index: 999;
  transform: translateX(100%);
  transition: transform 0.3s ease;
}

.volunteer-detail.show {
  transform: translateX(0%);
}

.volunteer-detail-content {
  padding: 24px;
  overflow-y: auto;
  height: 100%;
}

.volunteer-detail .close-btn {
  position: absolute;
  top: 12px;
  right: 12px;
  background: none;
  border: none;
  font-size: 22px;
  cursor: pointer;
  color: #555;
}

/* **************    */
/*내가 쓴 글 */
.board-table {
    width: 100%;
    border-collapse: collapse;
    table-layout: fixed;
  }

  .board-table th, .board-table td {
    padding: 10px;
    text-align: center;
    border-bottom: 1px solid #ddd;
    font-size: 14px;
  }

  .board-table tr:hover {
    background-color: #f9f9f9;
  }

  .pagination {
    text-align: center;
    margin-top: 12px;
  }

  .pagination button {
    margin: 0 3px;
    padding: 5px 10px;
    border: none;
    background-color: #eee;
    cursor: pointer;
    border-radius: 5px;
  }

  .pagination button:hover {
    background-color: #ddd;
  }

  .pagination button[style*="bold"] {
    background-color: #ffeedf;
    font-weight: bold;
  }
/****************************/

/*내 정보 수정*/


#info-edit-form label {
  display: inline-block;
  width: 100%;
  font-weight: bold;
}

#info-edit-form label span {
  display: inline-block;
  width: 100px;           /* 라벨 너비 고정 */
  vertical-align: middle;
}

#info-edit-form input[type="text"],
#info-edit-form input[type="password"],
#info-edit-form input[type="date"],
#info-edit-form input[type="email"] {
  display: inline-block;
  width: 200px;           /* input 고정 너비 */
  padding: 3px 10px;
  font-size: 14px;
  border: 1px solid #ccc;
  border-radius: 4px;
  vertical-align: middle;
}

#info-edit-form input[readonly] {
  background-color: #f5f5f5;
}

#info-edit-form button {
  display: inline-block;
  margin-left: 10px;
  padding: 6px 10px;
  font-size: 13px;
  cursor: pointer;
  border: none;
  border-radius: 4px;
  background-color: #e0e0e0;
  vertical-align: middle;
}

#info-edit-form .submit-btn {
  display: block;
  margin-top: 20px;
  padding: 10px 16px;
  background-color: #53D9C1;
  color: white;
  font-weight: bold;
  width: 100%;
  border: none;
  border-radius: 4px;
}


/*********************/


</style>
<script src="https://t1.daumcdn.net/mapjsapi/bundle/postcode/prod/postcode.v2.js"></script>
<script src="https://www.google.com/recaptcha/api.js?onload=onloadRecaptcha&render=explicit" async defer></script>
</head>
<body>
<%@ include file="/WEB-INF/views/common/index/indexHeader.jsp" %>
<div class="wrap">
	<div class="container">
    <!-- 왼쪽 사이드 메뉴 -->
    <div class="sidebar">
      <button id="btn-volunteer">봉사내역</button>
      <button id="btn-adoption">입양내역</button>
      <button id="btn-donation">기부내역</button>
      <button id="btn-activity">활동내역</button>
    </div>
		
		<!-- 오른쪽 콘텐츠 -->
		<div id="main-content" class="main-content"></div>
 
    <!-- 봉사 상세 슬라이드 패널 -->
    <div id="volunteer-detail-panel" class="volunteer-detail hidden">
      <div class="volunteer-detail-content">
        <button class="close-btn">X</button>
        <!-- 여기에 JS로 내용이 들어감 -->
        <div id="volunteer-detail-body"></div>
      </div>
    </div>
    <!-- 입양 상세 슬라이드 패널 -->
    <div id="adoption-detail-panel" class="volunteer-detail hidden">
      <div class="volunteer-detail-content">
        <button class="close-btn">X</button>
        <div id="adoption-detail-body"></div>
      </div>
    </div>


	</div>
</div>
<!--도로명 주소 레이어 -->
<div id="addressLayer" style="display:none;position:fixed;top:50%;left:50%;transform:translate(-50%,-50%);z-index:999;width:400px;height:500px;border:1px solid #888;box-shadow:0 0 10px rgba(0,0,0,0.3);background:#fff;"></div>

<%@ include file="/WEB-INF/views/common/index/indexFooter.jsp" %>
<script>
  // 0. context-path 주입 (EL 충돌 방지)
  const root = '<%= request.getContextPath() %>';
  let currentAdoptionIdx = null;

  // 1. 유저 정보 수정 버튼에 쓰일 userInfo 변수 (updateMypage에서 할당)
  let userInfo = null;

  // 2. 마이페이지 초기 정보 로드
  window.addEventListener("DOMContentLoaded", () => {
    fetch(root + '/api/mypage/screen/info')
      .then(res => res.json())
      .then(payload => {
        if (payload.status === 200) {
          userInfo = payload.data;
          updateMypage(payload.data);
        } else {
          console.error("초기 마이페이지 정보 로드 실패:", payload);
        }
      })
      .catch(err => console.error(err));

    // 상세 패널 닫기 버튼 바인딩 (한 번만)
    document.querySelector(".volunteer-detail .close-btn")
      .addEventListener("click", () => {
        const panel = document.getElementById("volunteer-detail-panel");
        panel.classList.remove("show");
        panel.classList.add("hidden");
      });
  });

  // 2-1. 마이페이지 상단 통계/히스토리 렌더 함수 (기존 코드 그대로)
  function updateMypage(info) {

  // info 구조분해
  const {
    username = '사용자',
    volunteerCount = 0,
    adoptionCount = 0,
    totalDonationAmount = 0,
    point = 0,
    activityHistory = []
  } = info;

  // 최근 활동 히스토리 HTML 생성
  const activityHtml = activityHistory.length
    ? activityHistory.map(item =>
        '<li>'
        + item.createdAt
        + ' – '
        + item.activityText
        + '</li>'
      ).join('')
    : '<li>최근 활동이 없습니다.</li>';

  // 메인 콘텐츠 완성
  let mainHtml = ''
    + '<div class="user-greeting">'
    +   '<h2>' + username + ' 님, 오늘도 따뜻한 하루 보내세요 ☀️</h2>'
    +   '<button class="edit-btn">내 정보 수정</button>'
    + '</div>'
    + '<div class="stats-box">'
    +   '<p>총 봉사 횟수: ' + volunteerCount + '회</p>'
    +   '<p>입양 완료: ' + adoptionCount + '건</p>'
    +   '<p>누적 기부 포인트: ' + totalDonationAmount.toLocaleString() + 'P</p>'
    +   '<p>보유 포인트: ' + point.toLocaleString() + ' P</p>'
    + '</div>'
    + '<div class="history-box">'
    +   '<h3>최근 활동 히스토리</h3>'
    +   '<ul class="activity-list">'
    +     activityHtml
    +   '</ul>'
    + '</div>';

    //화면에 렌더
  document.querySelector(".main-content").innerHTML = mainHtml;
}



  // 3. 봉사내역 버튼 클릭 → 리스트 호출
  document.getElementById("btn-volunteer").addEventListener("click", () => {
    fetch(root + '/api/mypage/volunteers')
      .then(res => res.json())
      .then(payload => {
        if (payload.status === 200) {
          renderVolunteerList(payload.data);
        } else {
          alert(payload.errorMsg || "봉사 내역을 불러올 수 없습니다.");
        }
      })
      .catch(err => console.error("봉사내역 불러오기 실패:", err));
  });

  // 4. 리스트 렌더링 + 클릭 이벤트 바인딩
  function renderVolunteerList(list) {
    const main = document.querySelector(".main-content");
    // html 변수를 반드시 선언·초기화!
    let html = ''
      + '<div class="user-greeting">'
      + '<h2> 나의 봉사 활동 기록 ☀️</h2>'
      + '<button class="edit-btn">내 정보 수정</button>'
      + '</div>'
      + '<div class="history-box">'
      + '<h3>봉사 내역</h3>'
      + '<ul class="volunteer-list">';

    if (list && list.length > 0) {
      html += list.map(item => {
        return ''
          + '<li class="volunteer-item" data-idx="' + item.volunteerRequestIdx + '">'
          +   '<div style="display:flex; gap:16px; align-items:center; margin-bottom:12px;">'
          +     '<img src="' + root + item.imagePath + '" alt="썸네일" '
          +          'style="width:100px;height:100px;object-fit:cover;border-radius:8px;" />'
          +     '<div>'
          +       '<p><strong>' + item.title + '</strong></p>'
          +       '<p>' + item.shelterName + ' | ' + item.location + '</p>'
          +       '<p>' + item.statusText + ' | 신청일: ' + item.participatedAt + '</p>'
          +     '</div>'
          +   '</div>'
          + '</li>';
      }).join('');
    } else {
      html += '<li>봉사 내역이 없습니다.</li>';
    }

    html += '</ul></div>';
    main.innerHTML = html;

    // 클릭 시 상세보기
    document.querySelectorAll(".volunteer-item").forEach(item => {
      item.addEventListener("click", () => {
        const idx = Number(item.dataset.idx);
        openVolunteerDetail(idx);
      });
    });
  }

  // 5. 상세 API 호출 및 렌더
  function openVolunteerDetail(volunteerRequestIdx) {
    if (!volunteerRequestIdx || isNaN(volunteerRequestIdx)) return;

    const url = root + '/api/mypage/volunteers/' + volunteerRequestIdx;

    fetch(url)
      .then(res => res.json())
      .then(payload => {
        if (payload.status === 200 && payload.data) {
          renderVolunteerDetail(payload.data);
        } else {
          alert(payload.errorMsg || "상세 정보를 불러올 수 없습니다.");
        }
      })
      .catch(err => console.error("상세 fetch 에러:", err));
  }

  // 6. 상세내용 패널에 표시
  function renderVolunteerDetail(detail) {
    const panel = document.getElementById("volunteer-detail-panel");
    const body  = document.getElementById("volunteer-detail-body");

    const imgTag = detail.imagePaths?.length
      ? '<img src="' + root + detail.imagePaths[0] + '" alt="이미지" '
        + 'style="width:100%;height:200px;object-fit:cover;border-radius:8px;margin-bottom:16px;" />'
      : '<div style="width:100%;height:200px;background:#eee;border-radius:8px;margin-bottom:16px;"></div>';

    body.innerHTML = ''
      + imgTag
      + '<h2>' + detail.title + '</h2>'
      + '<p><strong>보호소:</strong> ' + detail.shelterName + '</p>'
      + '<p><strong>장소:</strong> ' + detail.location + '</p>'
      + '<p><strong>상태:</strong> ' + detail.statusText + '</p>'
      + '<p><strong>봉사 일시:</strong> ' + detail.volunteerDate + '</p>'
      + '<p><strong>신청일:</strong> ' + detail.participatedAt + '</p>'
      + '<p><strong>모집 인원:</strong> ' + detail.capacity + '명</p>'
      + '<p><strong>신청 인원:</strong> ' + detail.applicants + '명</p>'
      + '<p><strong>연락처:</strong> ' + detail.contact + '</p>'
      + '<p style="margin-top:16px;">' + detail.content + '</p>';

  // (2) “참여완료” 상태일 때만 후기 폼 추가
  if (detail.statusText === '참여완료') {
    body.innerHTML += `
      <div class="review-box" style="margin-top:24px; padding:16px; border:1px solid #e2e8f0; border-radius:8px;">
        <h3 style="margin-top:0;">후기 작성</h3>
        <textarea id="review-content" rows="4" style="width:100%; padding:8px;" placeholder="후기를 입력하세요"></textarea>
        <input type="file" id="review-image" accept="image/*" style="margin-top:8px;"/>
        <button id="review-submit" style="
          margin-top:12px;
          padding:8px 16px;
          background:#53D9C1;color:#fff;border:none;border-radius:4px;
          cursor:pointer;
        ">등록하기</button>
      </div>
    `;
    // (3) 등록 버튼 이벤트 바인딩
    document.getElementById('review-submit').addEventListener('click', () => {
      const content = document.getElementById('review-content').value.trim();
      if (!content) {
        return alert('후기 내용을 입력해주세요.');
      }
      const fileInput = document.getElementById('review-image');
      const file = fileInput.files[0] || null;

      const formData = new FormData();
      formData.append('volunteerRequestIdx', detail.applyRequestIdx);
      formData.append('content', content);
      if (file) formData.append('image', file);

      fetch(root + '/api/mypage/volunteers/reviews', {
        method: 'POST',
        body: formData
      })
      .then(res => res.json())
      .then(json => {
        // 1) 성공 케이스: status 필드가 200일 때
        if (json.status === 200) {
          alert('후기 작성이 완료되었습니다.');
          document
            .getElementById('volunteer-detail-panel')
            .classList.replace('show','hidden');
        }
        // 2) 실패 케이스: errorCode 필드가 있을 때
        else if (json.errorCode) {
          alert(json.errorMsg || '후기 작성에 실패했습니다.');
        }
        // 3) 그 외
        else {
          alert(json.message || '알 수 없는 오류가 발생했습니다.');
        }
      })
      .catch(err => {
        console.error(err);
        alert('서버 오류로 후기 작성에 실패했습니다.');
      });
  });
  }

    panel.classList.remove("hidden");
    panel.classList.add("show");
  }
</script>

<!--입양 내역-->
<script>
  // ---------------------------
  // 2) 입양 내역 추가
  // ---------------------------
  document.getElementById("btn-adoption").addEventListener("click", () => {
    fetch(root + '/api/mypage/adoptions')
      .then(res => res.json())
      .then(payload => {
        if (payload.status === 200) {
          renderAdoptionList(payload.data);
        } else {
          alert(payload.errorMsg || "입양 내역을 불러올 수 없습니다.");
        }
      })
      .catch(err => console.error("입양내역 불러오기 실패:", err));
  });

  function renderAdoptionList(list) {
    const main = document.querySelector(".main-content");
    let html = ''
    + '<div class="user-greeting">'
      +   '<h2> 내가 입양한 동물 친구들 ☀️</h2>'
      +   '<button class="edit-btn">내 정보 수정</button>'
      + '</div>'
      + '<div class="history-box">'
      +   '<h3>입양 내역</h3>'
      +   '<ul class="volunteer-list">';

    if (list && list.length > 0) {
    html += list.map(item => {
    return ''
        + '<li class="adoption-item" data-idx="' + item.adoptionConsultIdx + '">'
        +   '<div style="display:flex; gap:16px; align-items:center; margin-bottom:12px;">'
        +     '<img src="' + root + item.imagePath + '" alt="썸네일" '
        +          'style="width:100px;height:100px;object-fit:cover;border-radius:8px;" />'
        +     '<div style="margin-left:12px;">'
        +       '<p><strong>' + item.animalName + '</strong></p>'
        +       '<p>' + item.statusText + '</p>'
        +       '<p> 신청일: ' + item.appliedAt +'</p>'
        +     '</div>'
        +   '</div>'
        + '</li>';
      }).join('');
    } else {
      html += '<li>입양 내역이 없습니다.</li>';
    }

    html += '</ul></div>';
    main.innerHTML = html;

    // (Optional) 클릭 시 상세보기 바인딩
    document.querySelectorAll(".adoption-item").forEach(item => {
      item.addEventListener("click", () => {
        const idx = Number(item.dataset.idx);
        if (!idx) {
      return alert("잘못된 항목입니다. idx:", item.dataset.idx);
       }
        openAdoptionDetail(idx);
      });
    });
  }

  // 3) 입양 상세 API 호출 및 모달/패널 렌더
  function openAdoptionDetail(adoptionConsultIdx) {
    if (!adoptionConsultIdx || isNaN(adoptionConsultIdx)) return;
    currentAdoptionIdx = adoptionConsultIdx; 

    const url = root + '/api/mypage/adoptions/' + adoptionConsultIdx;

    fetch(url)
      .then(res => res.json())
      .then(payload => {
        if (payload.status === 200 && payload.data) {
          renderAdoptionDetail(payload.data);
        } else {
          alert(payload.errorMsg || "입양 상세 정보를 불러올 수 없습니다.");
        }
      })
      .catch(err => console.error("입양 상세 fetch 에러:", err));
  }

  function renderAdoptionDetail(detail) {
    const panel = document.getElementById("adoption-detail-panel");
    const body  = document.getElementById("adoption-detail-body");

    const imgTag = detail.imagePaths?.length
    ? '<img src="' + root + detail.imagePaths[0] + '" alt="이미지" '
        + 'style="width:100%;height:200px;object-fit:cover;border-radius:8px;margin-bottom:16px;" />'
    : '<div style="width:100%;height:200px;background:#eee;border-radius:8px;margin-bottom:16px;"></div>';

    body.innerHTML = ''
      + imgTag
      + '<h2>' + detail.animalName + '</h2>'
      + '<p><strong>품종번호:</strong> ' + detail.breedIdx + '</p>'
      + '<p><strong>보호소 이름:</strong> ' + detail.shelterName + '</p>'
      + '<p><strong>입양 상태:</strong> ' + detail.statusText + '</p>'
      + '<p><strong>품종 이름:</strong> ' + detail.breed + '</p>'
      + '<p><strong>성별:</strong> ' + (detail.gender === 'M' ? '남' : '여') + '</p>'
      + '<p><strong>나이:</strong> ' + detail.age + ' 살</p>'
      + '<p><strong>크기(체중):</strong> ' + detail.size + ' kg</p>'
      + '<p><strong>중성화 여부:</strong> ' + (detail.neuter ? '유' : '무') + '</p>'
      + '<p><strong>성격:</strong> ' + detail.personality + '</p>'
      + '<p style="margin-top:16px;">' + detail.description + '</p>';

      // (2) “참여완료” 상태일 때만 후기 폼 추가
      if (detail.statusText === '상담완료') {
        body.innerHTML += `
          <div class="review-box" style="margin-top:24px; padding:16px; border:1px solid #e2e8f0; border-radius:8px;">
            <h3 style="margin-top:0;">후기 작성</h3>
            <textarea id="review-content" rows="4" style="width:100%; padding:8px;" placeholder="후기를 입력하세요"></textarea>
            <input type="file" id="review-image" accept="image/*" style="margin-top:8px;"/>
            <button id="review-submit" style="
              margin-top:12px;
              padding:8px 16px;
              background:#53D9C1;color:#fff;border:none;border-radius:4px;
              cursor:pointer;
            ">등록하기</button>
          </div>
        `;

            // (3) 등록 버튼 이벤트 바인딩
    document.getElementById('review-submit').addEventListener('click', () => {
      const content = document.getElementById('review-content').value.trim();
      if (!content) {
        return alert('후기 내용을 입력해주세요.');
      }
      const fileInput = document.getElementById('review-image');
      const file = fileInput.files[0] || null;

      const formData = new FormData();
      formData.append('adoptionConsultIdx', currentAdoptionIdx);
      formData.append('content', content);
      if (file) formData.append('image', file);

      fetch(root + '/api/mypage/adoptions/reviews', {
        method: 'POST',
        body: formData
      })
      .then(res => res.json())
      .then(json => {
        // 1) 성공 케이스: status 필드가 200일 때
        if (json.status === 200) {
          alert('후기 작성이 완료되었습니다.');
          document
            .getElementById('adoption-detail-panel')
            .classList.replace('show','hidden');
        }
        // 2) 실패 케이스: errorCode 필드가 있을 때
        else if (json.errorCode) {
          alert(json.errorMsg || '후기 작성에 실패했습니다.');
        }
        // 3) 그 외
        else {
          alert(json.message || '알 수 없는 오류가 발생했습니다.');
        }
      })
      .catch(err => {
        console.error(err);
        alert('서버 오류로 후기 작성에 실패했습니다.');
      });
  });
  }
      panel.classList.remove("hidden");
      panel.classList.add("show");
}

// 닫기 버튼 바인딩(한 번만)
document.querySelectorAll("#adoption-detail-panel .close-btn").forEach(btn => {
  btn.addEventListener("click", () => {
    const panel = document.getElementById("adoption-detail-panel");
    panel.classList.remove("show");
    panel.classList.add("hidden");
  });
});
</script>


<!-- 기부 내역 -->
<script>
  // 1) 기부내역 버튼 클릭 → 리스트 호출
  document.getElementById("btn-donation").addEventListener("click", () => {
    fetch(root + '/api/mypage/donation')
      .then(res => res.json())
      .then(payload => {
        if (payload.status === 200) {
          renderDonationList(payload.data);
        } else {
          alert(payload.errorMsg || "기부 내역을 불러올 수 없습니다.");
        }
      })
      .catch(err => console.error("기부내역 불러오기 실패:", err));
  });

  // 2) 기부리스트 렌더링
  function renderDonationList(list) {
    const main = document.querySelector(".main-content");
    let html = ''
      + '<div class="user-greeting">'
      + '<h2> 내가 후원한 이야기들 ☀️</h2>'
      + '<button class="edit-btn">내 정보 수정</button>'
      + '</div>'
      + '<div class="history-box">'
      + '<h3>기부 내역</h3>'
      + '<ul class="volunteer-list">'; 

      if (list && list.length > 0) {
      html += list.map(item => {
        return ''
          + '<li class="donation-item" style="padding:12px 0; border-bottom:1px solid #eee;">'
          +   '<div style="display:flex; gap:16px; align-items:center;">'
          +     '<img src="' + root + item.imagePath + '" alt="썸네일" '
          +          'style="width:100px;height:100px;object-fit:cover;border-radius:8px;" />'
          +     '<div style="flex:1;">'
          +       '<p><strong>' + item.donationName + '</strong></p>'
          +       '<p>' + item.donationStatusText
          +         ' | 기간: ' + item.startDate + ' ~ ' + item.endDate + '</p>'
          +       '<p>목표 모금액: ' + item.amount +' 원 | 누적 모금액: ' + item.completionAmount + '원</p>'          
          +       '<p>모금율: ' + item.completionRate + '%</p>'
          +       '<p>주최: '+ item.sponsor + '</p>'
          +     '</div>'
          +     '<button class="view-btn" data-idx="'
          +       item.donationIdx
          +     '" style="'
          +       'padding:6px 12px;'
          +       'background:#53D9C1;'
          +       'color:#fff;'
          +       'border:none;'
          +       'border-radius:4px;'
          +       'cursor:pointer;'
          +     '">본문보기</button>'
          +   '</div>'
          + '</li>';
      }).join('');
    } else {
      html += '<li>기부 내역이 없습니다.</li>';
    }
    html += '</ul></div>';
    main.innerHTML = html;

      // 3) “본문보기” 버튼 클릭 시 이동
      document.querySelectorAll(".view-btn").forEach(btn => {
      btn.addEventListener("click", () => {
      const idx = btn.dataset.idx;
        // 원하는 상세페이지 경로로 변경하세요
        window.location.href = root + '/donations/' + idx;
      });
    });

  }       
</script>


<!-- 활동내역 -->
<script>
  document.getElementById("btn-activity").addEventListener("click", () => {
    loadActivityPage();
  });

  function loadActivityPage(pageWritten = 1, pageLiked = 1) {
    fetch(root + '/api/mypage/board/activity?pageWritten=' + pageWritten + '&pageLiked=' + pageLiked)
      .then(res => res.json())
      .then(payload => {
        if (payload.status === 200 && payload.data) {
          renderActivityHistory(payload.data, pageWritten, pageLiked);
        } else {
          alert(payload.errorMsg || "활동 내역을 불러올 수 없습니다.");
        }
      })
      .catch(err => console.error("활동 내역 불러오기 실패:", err));
  }

  function createPaginationHTML(currentPage, totalPages, btnClass) {
    if (totalPages <= 1) return ''; // 페이지 수 1 이하면 페이징 없음

    var html = '';
    var maxPages = 5;
    var startPage = Math.floor((currentPage - 1) / maxPages) * maxPages + 1;
    var endPage = Math.min(startPage + maxPages - 1, totalPages);

    // ◀ 이전 그룹
    if (startPage > 1) {
      html += '<button class="' + btnClass + '" data-page="' + (startPage - 1) + '">&lt;</button>';
    }

    // 숫자 버튼들
    for (var i = startPage; i <= endPage; i++) {
      html += '<button class="' + btnClass + '" data-page="' + i + '"'
           + (i === currentPage ? ' style="font-weight:bold;"' : '') + '>' + i + '</button>';
    }

    // ▶ 다음 그룹
    if (endPage < totalPages) {
      html += '<button class="' + btnClass + '" data-page="' + (endPage + 1) + '">&gt;</button>';
    }

    return html;
  }

  function renderActivityHistory(data, pageWritten, pageLiked) {
    var written = data.written;
    var liked = data.liked;

    var writtenTotalPages = Math.ceil((written?.totalCount || 0) / 5); // 5개씩 
    var likedTotalPages = Math.ceil((liked?.totalCount || 0) / 5); 

    var main = document.querySelector(".main-content");
    var html = ''
      + '<div class="user-greeting">'
      +   '<h2> 내가 남긴 따뜻한 발자국들 ☀️</h2>'
      +   '<button class="edit-btn">내 정보 수정</button>'
      + '</div>';

    // ✏️ 내가 쓴 글
    html += '<div class="history-box" >'
         + '<h3>✏️ 내가 쓴 글</h3>'
         + '<table class="board-table">'
         + '<thead><tr><th>작성번호</th><th>글 제목</th><th>작성자</th><th>작성날짜</th><th>조회수</th></tr></thead>'
         + '<tbody>';

    if (written?.list?.length > 0) {
      html += written.list.map(function(item) {
        return '<tr class="activity-item" data-idx="' + item.boardIdx + '" data-type="' + item.boardType + '">'
             + '<td>' + item.boardIdx + '</td>'
             + '<td>' + item.title + '</td>'
             + '<td>' + item.nickname + '</td>'
             + '<td>' + item.createdAt + '</td>'
             + '<td>' + item.views + '</td>'
             + '</tr>';
      }).join('');
    } else {
      html += '<tr><td colspan="5">작성한 글이 없습니다.</td></tr>';
    }

    html += '</tbody></table>'
     + '<div class="pagination" id="written-pagination">'
     + createPaginationHTML(pageWritten, writtenTotalPages, "written-page-btn")
     + '</div>';

    // ❤️ 좋아요한 글
    html += '<div class="history-box" style="margin-top:40px;">'
         + '<h3>❤️ 좋아요한 글</h3>'
         + '<table class="board-table">'
         + '<thead><tr><th>작성번호</th><th>글 제목</th><th>작성자</th><th>작성날짜</th><th>조회수</th></tr></thead>'
         + '<tbody>';

    if (liked?.list?.length > 0) {
      html += liked.list.map(function(item) {
        return '<tr class="activity-item" data-idx="' + item.boardIdx + '" data-type="' + item.boardType + '">'
             + '<td>' + item.boardIdx + '</td>'
             + '<td>' + item.title + '</td>'
             + '<td>' + item.nickname + '</td>'
             + '<td>' + item.createdAt + '</td>'
             + '<td>' + item.views + '</td>'
             + '</tr>';
      }).join('');
    } else {
      html += '<tr><td colspan="5">좋아요한 글이 없습니다.</td></tr>';
    }

    html += '</tbody></table>'
     + '<div class="pagination" id="liked-pagination">'
     + createPaginationHTML(pageLiked, likedTotalPages, "liked-page-btn")
     + '</div>';

    main.innerHTML = html;

    // 본문 이동
    document.querySelectorAll(".activity-item").forEach(function(row) {
      row.addEventListener("click", function() {
        var boardIdx = row.dataset.idx;
        var boardType = row.dataset.type;
        location.href = root + '/boards/' + boardType + '/' + boardIdx;
      });
    });

    // 페이징 이벤트
    document.querySelectorAll(".written-page-btn").forEach(function(btn) {
      btn.addEventListener("click", function() {
        var page = Number(btn.dataset.page);
        loadActivityPage(page, pageLiked);
      });
    });

    document.querySelectorAll(".liked-page-btn").forEach(function(btn) {
      btn.addEventListener("click", function() {
        var page = Number(btn.dataset.page);
        loadActivityPage(pageWritten, page);
      });
    });
  }
</script>

<!-- 내 정보 수정-->
<script>

// 내 정보 수정 버튼 클릭 시 이벤트
document.addEventListener("click", function(e) {
  if (e.target.classList.contains("edit-btn")) {
    renderPasswordCheckForm();  // 비밀번호 확인 화면 표시
  }
});

//비밀번호 확인 화면 렌더 
function renderPasswordCheckForm() {
  const main = document.querySelector(".main-content");
  let html = ''
    + '<div class="user-greeting">'
    +   '<h2>' + (userInfo?.username || '사용자') + ' 님, 본인 확인을 먼저 해주세요 🔒</h2>'
    + '</div>'
    + '<div class="history-box">'
    +   '<h3>비밀번호 확인</h3>'
    +   '<input type="password" id="password-check" placeholder="현재 비밀번호 입력" style="padding:10px; width: 100%; max-width: 300px;" />'
    +   '<button id="btn-password-verify" style="margin-top:12px; padding:10px 16px; background:#53D9C1; color:#fff; border:none; border-radius:4px; cursor:pointer;">확인</button>'
    + '</div>';
  main.innerHTML = html;

  document.getElementById("btn-password-verify").addEventListener("click", () => {
    const password = document.getElementById("password-check").value.trim();
    if (!password) return alert("비밀번호를 입력해주세요.");

    fetch(root + '/api/mypage/information/modify', {
      method: 'POST',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify({ password })
    })
    .then(res => res.json())
    .then(json => {
      if (json.status === 200) {
        userInfo = json.data;
        renderUserInfoEditForm(); //  성공 시 정보 수정 폼 출력
      } else {
        alert(json.errorMsg || "비밀번호가 일치하지 않습니다.");
      }
    })
    .catch(err => {
      console.error(err);
      alert("서버 오류입니다.");
    });
  });
}

// 내 정보 수정 화면 렌더
function renderUserInfoEditForm() {
  const main = document.querySelector(".main-content");

  const {
    id, email, password, name, nickname,
    birthDate, tel, zipCode, address, addressDetail, gender
  } = userInfo;

  let html = ''
    + '<div class="user-greeting">'
    +   '<h2>' + name + ' 님, 정보를 수정하세요 🛠️</h2>'
    + '</div>'
    + '<div class="history-box">'
    +   '<h3>내 정보 수정</h3>'
    +   '<form id="info-edit-form">'

    // ID (수정 불가)
    +   '<label>아이디: <input type="text" value="' + id + '" readonly style="background-color:#f5f5f5;" /></label><br><br>'

    // 비밀번호 (텍스트 + 버튼)
    +     '<label>비밀번호: '
    +       '<input type="password" value="********" readonly style="background-color:#f5f5f5;" /> '
    +       '<button type="button" id="btn-password-change" style="margin-left:10px;">비밀번호 변경</button>'
    +     '</label><br><br>'

    // 이름
    +     '<label>이름: <input type="text" name="name" value="' + name + '" required /></label><br><br>'

    // 닉네임
    +     '<label>닉네임: <input type="text" name="nickname" value="' + nickname + '" required /></label><br><br>'

    // 생년월일
    +     '<label>생년월일: <input type="date" name="birthDate" value="' + birthDate + '" required /></label><br><br>'

    // 전화번호
    +     '<label>전화번호: <input type="text" name="tel" value="' + tel + '" required /></label><br><br>'

    // 성별
    +     '<label>성별: '
    +       '<input type="radio" name="gender" value="M" ' + (gender === 'M' ? 'checked' : '') + '> 남 '
    +       '<input type="radio" name="gender" value="F" ' + (gender === 'F' ? 'checked' : '') + '> 여'
    +     '</label><br><br>'

    // 이메일 (텍스트 + 버튼)
    +     '<label>이메일: '
    +       '<input type="text" value="' + email + '" readonly style="background-color:#f5f5f5;" /> '
    +       '<button type="button" id="btn-email-change" style="margin-left:10px;">이메일 변경</button>'
    +     '</label><br><br>'

    // 주소
    + '<label>우편번호: '
    +   '<input type="text" id="zipCode" name="zipCode" value="' + zipCode + '" readonly /> '
    +   '<button type="button" id="btn-address-search">우편번호 찾기</button>'
    + '</label><br><br>'

    + '<label>주소: <input type="text" id="address" name="address" value="' + address + '" readonly /></label><br><br>'

    + '<label>상세주소: <input type="text" name="addressDetail" value="' + addressDetail + '" /></label><br><br>'

    // 제출
    +     '<button type="submit" style="padding:10px 16px; background:#53D9C1; color:#fff; border:none; border-radius:4px;">수정 완료</button>'
    +   '</form>'
    + '</div>';

  main.innerHTML = html;

  // (1) 주소 검색 버튼 바인딩
  document.getElementById("btn-address-search").addEventListener("click", function() {
  new daum.Postcode({
    oncomplete: function(data) {
      document.getElementById("zipCode").value = data.zonecode;
      document.getElementById("address").value = data.roadAddress;
    }
  }).open();
});


  //  수정 전송
  document.getElementById("info-edit-form").addEventListener("submit", function(e) {
    e.preventDefault();
    const formData = new FormData(e.target);
    const data = Object.fromEntries(formData.entries());

    fetch(root + '/api/mypage/information/update', {
      method: 'PUT',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify(data)
    })
    .then(res => res.json())
    .then(json => {
      if (json.status === 200) {
        alert("정보가 성공적으로 수정되었습니다.");
        location.reload(); // 또는 updateMypage(json.data) 호출
      } else {
        alert(json.errorMsg || "수정에 실패했습니다.");
      }
    })
    .catch(err => {
      console.error(err);
      alert("서버 오류입니다.");
    });
  });

}


// 비밀번호 변경 
document.addEventListener("click", function (e) {
  if (e.target.id === "btn-password-change") {
    renderPasswordChangeForm();
  }
});

function renderPasswordChangeForm() {
  const main = document.querySelector(".main-content");

  // HTML 구성
  const html = `
    <div class="user-greeting">
      <h2>비밀번호 변경 🔐</h2>
    </div>
    <div class="history-box">
      <form id="password-change-form">
        <label>현재 비밀번호<br>
          <input type="password" name="currentPassword" required />
        </label><br><br>
        <label>새 비밀번호<br>
          <input type="password" name="newPassword" required />
        </label><br><br>
        <label>새 비밀번호 확인<br>
          <input type="password" name="confirmPassword" required />
        </label><br><br>
        <!-- explicit 렌더용 컨테이너 -->
        <div id="recaptcha-container"></div><br>
        <button type="submit"
                style="padding:10px 16px; background:#53D9C1; color:#fff; border:none; border-radius:4px;">
          변경하기
        </button>
      </form>
    </div>`;

  main.innerHTML = html;

  // 스크립트가 이미 로드되었으면 바로 렌더,
  // 아니면 onloadRecaptcha 콜백에서 렌더
  if (window.grecaptcha) {
    onloadRecaptcha();
  }

    // 3) onloadRecaptcha 콜백(전역)에 렌더 로직 정의
  function onloadRecaptcha() {
  grecaptcha.render('recaptcha-container', {
    'sitekey': '6LfDx2grAAAAAKaMfsjIo7JaJrGEkaNFeYLlC4GB'
  });
}

  // 폼 제출 처리
  document.getElementById("password-change-form").addEventListener("submit", async function (e) {
    e.preventDefault();

    const formData = new FormData(e.target);
    const currentPassword = formData.get("currentPassword").trim();
    const newPassword = formData.get("newPassword").trim();
    const confirmPassword = formData.get("confirmPassword").trim();

    // 유효성 검사
    if (!currentPassword || !newPassword || !confirmPassword) {
      alert("모든 비밀번호 항목을 입력해주세요.");
      return;
    }

    if (newPassword !== confirmPassword) {
      alert("새 비밀번호와 확인 비밀번호가 일치하지 않습니다.");
      return;
    }

    // ✅ grecaptcha 토큰
    const captcha = grecaptcha.getResponse();
    if (!captcha) {
      alert("보안문자를 완료해주세요.");
      return;
    }

    try {
      const res = await fetch(root + "/api/mypage/information/password", {
        method: "PUT",
        headers: { "Content-Type": "application/json" },
        credentials: "include",
        body: JSON.stringify({
          currentPassword,
          newPassword,
          confirmPassword,
          captcha
        })
      });

      const json = await res.json();
      if (res.ok && json.status === 200) {
        alert("비밀번호가 성공적으로 변경되었습니다.");
        location.reload();
      } else {
        alert(json.errorMsg || "비밀번호 변경 실패");
        grecaptcha.reset(); // 실패 시 보안문자 리셋
      }
    } catch (err) {
      console.error("서버 오류:", err);
      alert("서버 오류가 발생했습니다.");
    }
  });
}

//////////////////////////////
// 1) 이메일 변경 버튼 클릭 바인딩
document.addEventListener("click", function(e) {
  if (e.target.id === "btn-email-change") {
    renderEmailChangeForm();
  }
});

function renderEmailChangeForm() {
  const main = document.querySelector(".main-content");
  
  // userInfo.email 은 이전에 /api/mypage/screen/info 로 불러왔던 userInfo 객체의 email 필드
  const current = userInfo.email || "";

  // 2) HTML 구성: 현재 이메일, 새 이메일, 인증번호 받기, 코드 입력, 제출 버튼
  main.innerHTML = `
    <div class="user-greeting">
      <h2>이메일 변경 </h2>
    </div>
    <div class="history-box">
      <form id="email-change-form">
        <label>현재 이메일<br>
          <input type="email" name="currentEmail" />
        </label><br><br>
        <label>새 이메일<br>
          <input type="email" id="newEmail" name="newEmail" required />
          <button type="button" id="send-email-code-btn" style="margin-left:8px;">
            인증번호 받기
          </button>
        </label><br><br>
        <label>인증번호 입력<br>
          <input type="text" id="emailCode" name="code" required />
        </label><br><br>
        <button type="submit"
                style="padding:8px 16px; background:#53D9C1; color:#fff; border:none; border-radius:4px;">
          이메일 변경
        </button>
      </form>
    </div>
  `;

  // 3) “인증번호 받기” 클릭 → 코드 전송
  document
    .getElementById("send-email-code-btn")
    .addEventListener("click", async () => {
      const newEmail = document.getElementById("newEmail").value.trim();
      if (!newEmail || !/^[^\s@]+@[^\s@]+\.[^\s@]+$/.test(newEmail)) {
        return alert("올바른 새 이메일을 입력해주세요.");
      }
      try {
        const res = await fetch(`${root}/api/email/find/change/send-code`, {
          method: "POST",
          headers: { "Content-Type": "application/json" },
          body: JSON.stringify({ email: newEmail })
        });
        const json = await res.json();
        if (res.ok && json.status === 200) {
          alert("이메일 변경용 인증번호가 전송되었습니다.");
        } else {
          alert(json.errorMsg || "인증번호 전송 실패");
        }
      } catch (err) {
        console.error(err);
        alert("서버 오류로 인증번호 전송에 실패했습니다.");
      }
    });

  // 4) 폼 제출 → 최종 이메일 변경
  document
    .getElementById("email-change-form")
    .addEventListener("submit", async function(e) {
      e.preventDefault();
      const currentEmail = this.currentEmail.value.trim();
      const newEmail     = this.newEmail.value.trim();
      const code         = this.code.value.trim();

      if (!newEmail || !code) {
        return alert("새 이메일과 인증번호를 모두 입력해주세요.");
      }

      try {
        const res = await fetch(`${root}/api/mypage/information/email`, {
          method: "PUT",
          headers: { "Content-Type": "application/json" },
          body: JSON.stringify({ currentEmail, newEmail, code })
        });
        const json = await res.json();
        if (res.ok && json.status === 200) {
          alert("이메일이 성공적으로 변경되었습니다.");
          location.reload();
        } else {
          alert(json.errorMsg || "이메일 변경에 실패했습니다.");
        }
      } catch (err) {
        console.error(err);
        alert("서버 오류가 발생했습니다.");
      }
    });
}


</script>
  
</body>
</html>