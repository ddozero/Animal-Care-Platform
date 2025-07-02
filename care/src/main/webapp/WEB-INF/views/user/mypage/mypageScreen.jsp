<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>마이페이지</title>
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
</style>
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
<%@ include file="/WEB-INF/views/common/index/indexFooter.jsp" %>

<script>
  // 0. context-path 주입 (EL 충돌 방지)
  const root = '<%= request.getContextPath() %>';

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
      +   '<h2>' + (userInfo?.username || '사용자') + ' 님, 오늘도 따뜻한 하루 보내세요 ☀️</h2>'
      +   '<button class="edit-btn">내 정보 수정</button>'
      + '</div>'
      + '<div class="history-box">'
      +   '<h3>봉사 내역</h3>'
      +   '<ul class="volunteer-list">';

    if (list && list.length > 0) {
      html += list.map(item => {
        // VolunteerListResponseDTO: volunteerRequestIdx, imagePath, title, shelterName, location, statusText, participatedAt :contentReference[oaicite:0]{index=0}
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
    // VolunteerDetailResponseDTO 필드: volunteerRequestIdx, title, content, shelterName, location, statusText,
    // volunteerDate, createdAt, capacity, applicants, contact, participatedAt, imagePaths :contentReference[oaicite:1]{index=1}
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
      formData.append('volunteerRequestIdx', detail.volunteerRequestIdx);
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
    const main = document.getElementById("main-content");
    let html = ''
      + '<div class="user-greeting">'
      +   `<h2>${userInfo.username} 님, 오늘도 따뜻한 하루 보내세요 ☀️</h2>`
      +   '<button class="edit-btn">내 정보 수정</button>'
      + '</div>'
      + '<div class="history-box">'
      +   '<h3>입양 내역</h3>'
      +   '<ul class="volunteer-list">';

    if (list && list.length > 0) {
      list.forEach(item => {
        html += ''
          + '<li class="adoption-item" data-idx="' + item.adoptionConsultIdx + '">'
          +   '<div style="display:flex; gap:16px; align-items:center; padding:12px 0;">'
          +     `<img src="${root}${item.imagePath}" alt="썸네일" `
          +          'style="width:100px;height:100px;object-fit:cover;border-radius:8px;" />'
          +     '<div style="margin-left:12px;">'
          +       `<p><strong>${item.animalName}</strong></p>`
          +       `<p>${item.statusText} | 신청일: ${item.appliedAt}</p>`
          +     '</div>'
          +   '</div>'
          + '</li>';
      });
    } else {
      html += '<li>입양 내역이 없습니다.</li>';
    }

    html += '</ul></div>';
    main.innerHTML = html;

    // (Optional) 클릭 시 상세보기 바인딩
    document.querySelectorAll(".adoption-item").forEach(el => {
      el.addEventListener("click", () => {
        const idx = Number(el.dataset.idx);
        openAdoptionDetail(idx);
      });
    });
  }

  // 3) 입양 상세 API 호출 및 모달/패널 렌더
  function openAdoptionDetail(adoptionConsultIdx) {
    fetch(root + '/api/mypage/adoptions/' + adoptionConsultIdx)
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

    const imgTag = detail.imagePath
    ? `<img src="${root}${detail.imagePath}" 
            alt="썸네일"
            style="width:100%; height:200px; object-fit:cover;
                   border-radius:8px; margin-bottom:16px;" />`
    : `<div style="width:100%; height:200px; background:#eee;
                   border-radius:8px; margin-bottom:16px;"></div>`;

    body.innerHTML = `
    ${imgTag}
    <h2>${detail.animalName}</h2>
    <p><strong>보호소:</strong> ${detail.shelterName}</p>
    <p><strong>상태:</strong> ${detail.statusText}</p>
    <p><strong>신청일:</strong> ${detail.appliedAt}</p>
    <p><strong>품종:</strong> ${detail.breed}</p>
    <p><strong>성별:</strong> ${detail.gender}</p>
    <p><strong>나이:</strong> ${detail.age}살</p>
    <p><strong>체중:</strong> ${detail.size}kg</p>
    <p><strong>중성화:</strong> ${detail.neuter ? '완료' : '미완료'}</p>
    <p style="margin-top:16px;">${detail.description}</p>
  `;

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

</body>
</html>