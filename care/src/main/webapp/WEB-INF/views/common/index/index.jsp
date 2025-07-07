<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>메인 페이지</title>
<script>const root = "${pageContext.request.contextPath}";</script>
<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/swiper@11/swiper-bundle.min.css" />
<link rel="stylesheet" href="https://unpkg.com/aos@2.3.4/dist/aos.css" />
<style>
  .welcome-banner {
     background-color: #f0fbf9;
     border-left: 6px solid #53D9C1;
     padding: 15px 30px;
     margin: 20px auto;
     max-width: 1200px;
     font-size: 16px;
   }
   .swiper-row-wrapper {
     max-width: 1200px;
     margin: 30px auto;
     padding: 0 30px;
   }
   .swiper {
     height: 220px;
     position: relative;
   }
   .swiper-slide {
     width: 300px !important;
     height: 200px;
     overflow: hidden;
     border-radius: 12px;
     position: relative;
   }
   .swiper-slide::before {
     content: '';
     position: absolute;
     top: 0; left: 0;
     width: 100%; height: 100%;
     background-color: rgba(0,0,0,0.2);
     z-index: 1;
   }
   .swiper-slide video {
     width: 100%;
     height: 100%;
     object-fit: cover;
     border-radius: 12px;
     filter: brightness(0.8);
   }
   .slide-caption {
     position: absolute;
     bottom: 20px;
     left: 20px;
     color: white;
     font-size: 16px;
     font-weight: bold;
     z-index: 2;
     text-shadow: 1px 1px 4px rgba(0,0,0,0.6);
   }
   .section {
     max-width: 1200px;
     margin: 40px auto;
     padding: 0 30px;
   }
   .section h2 {
     margin-bottom: 20px;
     color: #222;
     text-align: center;
   }
   .card-container {
     display: flex;
     gap: 20px;
   }
   .card {
     background-color: white;
     flex: 1;
     border-radius: 10px;
     box-shadow: 0 2px 6px rgba(0,0,0,0.1);
     padding: 20px;
     text-align: center;
     transition: transform 0.2s, box-shadow 0.2s;
     cursor: pointer;
   }
   .card:hover {
     transform: translateY(-4px);
     box-shadow: 0 6px 15px rgba(0,0,0,0.1);
   }
   /* 공지사항 섹션 */
	.notice-section {
	  display: flex;
	  gap: 30px;
	  margin: 40px auto;
	  max-width: 1200px;
	  padding: 0 30px;
	}
	
	.notice-left {
	  flex: 2;
	}
	
	.notice-left h3 {
	  font-size: 20px;
	  margin-bottom: 15px;
	}
	
	.notice-list li {
	  margin-bottom: 20px;
	  border-bottom: 1px solid #eee;
	  padding-bottom: 10px;
	}
	
	.notice-list a {
	  font-weight: bold;
	  color: #222;
	  text-decoration: none;
	}
	
	.notice-list p {
	  font-size: 14px;
	  color: #555;
	}
	
	.notice-list .date {
	  font-size: 12px;
	  color: #999;
	}
	
	.notice-right {
	  flex: 1;
	  background-color: #f0fbf9;
	  border-left: 6px solid #53D9C1;
	  padding: 20px;
	  border-radius: 8px;
	}
	
	.info-box h4 {
	  font-size: 16px;
	  margin-bottom: 10px;
	  color: #333;
	}
	
	.info-box p {
	  font-size: 14px;
	  color: #444;
	}
	
	.go-link {
	  display: inline-block;
	  margin-top: 10px;
	  color: #53D9C1;
	  font-weight: bold;
	  text-decoration: none;
	}
  /* 슬로건 */
  .slogan {
  margin-top: 12px;
  font-weight: bold;
  font-size: 14px;
  color: #53D9C1;
}
  /************************/

  /*등록된 보호시설*/
.shelter-map-section {
  margin-top: 60px;
}

.shelter-map-wrapper {
  display: flex;
  gap: 40px;
  align-items: flex-start;
  padding: 0 30px;
  max-width: 1200px;
  margin: 0 auto;
}

.shelter-map-left {
  display: flex;
  flex-direction: row;
  gap: 20px;
  align-items: flex-start;
}

.shelter-stats {
  margin-top: 15px;
  font-size: 16px;
  color: #333;
  text-align: center;
}

.shelter-map-right {
  display: flex;
  flex-direction: column;
  gap: 20px;
  justify-content: space-between;
  width: 280px; 
  height: 400px; 
}

.shelter-list-panel {
  background-color: #fff;
  border: 1px solid #ddd;
  border-radius: 8px;
  padding: 15px;
  max-height: 400px;
  overflow-y: auto;
  flex: 1;
}

.info-card {
  border-radius: 8px;
  padding: 20px;
  font-size: 14px;
  line-height: 1.6;
}

.info-card.dark {
  background-color: #2e7f90;
  color: white;
}

.info-card.dark .go-link {
  color: white;
  font-weight: bold;
}

.info-card.light {
  background-color: #f9f9f9;
  color: #222;
  border: 1px solid #eee;
}

#shelter-list-box {
  flex: 1;
  background-color: #fff;
  border: 1px solid #ddd;
  border-radius: 8px;
  padding: 15px;
  max-height: 460px;    
  min-width: 480px;       
  overflow-y: auto;
}
/*지도 */
.korea-map-wrapper {
  flex-shrink: 0;
  position: relative;
}

.korea-map {
  width: 100%;
  max-width: 500px;
}

.region-label {
  position: absolute;
  background-color: rgba(255,255,255,0.8);
  border: 1px solid #ddd;
  padding: 3px 8px;
  font-size: 13px;
  border-radius: 4px;
  color: #2e7f90;
  font-weight: bold;
  cursor: pointer;
  transition: all 0.2s;
}
.region-label:hover {
  background-color: #ddd;
  color: #53D9C1;
}

.shelter-item:hover {
  background-color: #ddd;
  color: #53D9C1;
  border-radius: 6px;
}
.shelter-item {
  transition: background-color 0.2s ease;
}
  /************************/
</style>
</head>
<body>
<%@ include file="/WEB-INF/views/common/index/indexHeader.jsp" %>

<c:if test="${not empty loginUser}">
  <div class="welcome-banner">
    <strong>${loginUser.nickname}</strong>님, 오늘도 함께해주셔서 감사합니다!
  </div>
</c:if>

<h2 style="text-align:center; font-size:24px; margin:40px 0 10px;">입양을 기다리는 아이들을 만나보세요</h2>

<!-- 슬라이드 줄 1 -->
<div class="swiper-row-wrapper">
  <div class="swiper swiper-top">
    <div class="swiper-wrapper">
      <div class="swiper-slide">
        <video autoplay muted loop playsinline src="${root}/resources/web/images/indexbody/video1.mp4"></video>
        <div class="slide-caption">유기동물 보호소의 아침</div>
      </div>
      <div class="swiper-slide">
        <video autoplay muted loop playsinline src="${root}/resources/web/images/indexbody/video3.mp4"></video>
        <div class="slide-caption">새로운 가족을 기다리는 눈빛</div>
      </div>
      <div class="swiper-slide">
        <video autoplay muted loop playsinline src="${root}/resources/web/images/indexbody/video5.mp4"></video>
        <div class="slide-caption">따뜻한 손길이 필요해요</div>
      </div>
      <div class="swiper-slide">
        <video autoplay muted loop playsinline src="${root}/resources/web/images/indexbody/video7.mp4"></video>
        <div class="slide-caption">우리는 준비되어 있어요</div>
      </div>
      <div class="swiper-slide">
        <video autoplay muted loop playsinline src="${root}/resources/web/images/indexbody/video9.mp4"></video>
        <div class="slide-caption">입양은 새로운 시작입니다</div>
      </div>
    </div>
  </div>
</div>

<!-- 슬라이드 줄 2 -->
<div class="swiper-row-wrapper">
  <div class="swiper swiper-bottom">
    <div class="swiper-wrapper">
      <div class="swiper-slide">
        <video autoplay muted loop playsinline src="${root}/resources/web/images/indexbody/video2.mp4"></video>
        <div class="slide-caption">희망을 전하는 하루</div>
      </div>
      <div class="swiper-slide">
        <video autoplay muted loop playsinline src="${root}/resources/web/images/indexbody/video4.mp4"></video>
        <div class="slide-caption">산책하며 교감해요</div>
      </div>
      <div class="swiper-slide">
        <video autoplay muted loop playsinline src="${root}/resources/web/images/indexbody/video6.mp4"></video>
        <div class="slide-caption">작은 관심이 큰 변화를 만듭니다</div>
      </div>
      <div class="swiper-slide">
        <video autoplay muted loop playsinline src="${root}/resources/web/images/indexbody/video8.mp4"></video>
        <div class="slide-caption">마음을 나눌 시간이에요</div>
      </div>
      <div class="swiper-slide">
        <video autoplay muted loop playsinline src="${root}/resources/web/images/indexbody/video10.mp4"></video>
        <div class="slide-caption">우리 함께해요</div>
      </div>
    </div>
  </div>
</div>


<!-- 공지사항 섹션 -->
<div class="section notice-section">
  <div class="notice-left">
    <h3>공지사항</h3>
    <ul class="notice-list">
      <!-- JS로 채움 -->
    </ul>
  </div>
  <div class="notice-right">
    <div class="info-box" id="info-box">
      <h4>이 사이트에서 무엇을 할 수 있나요?</h4>
      <p>유기동물 보호와 입양 문화를 더 건강하게 만들기 위해 만들어졌습니다.

        봉사 참여부터 기부, 입양, 후기 작성까지
        하나의 플랫폼에서 쉽고 투명하게 연결됩니다.
        
        민간 보호소도 홈페이지 없이 동물 등록과 봉사 일정을 공유하고
        필요한 사람들과 자연스럽게 소통할 수 있습니다.</p>
        <div class="slogan">당신의 작은 관심이 한 생명을 구합니다.</div>
        <div style="margin-top:15px; font-size:12px; color:#aaa; text-align: right;"><br/>
          #유기동물 #입양문화 #보호소연결 #함께사는사회
        </div><br/><br/><br/><br/><br/>
        <a href="${root}/volunteers" class="go-link" style="color: #515134; display:block; margin-top:20px;">봉사 신청하러 가기 →</a>
        <a href="${root}/animals" class="go-link" style="color: #515134; display:block; margin-top:20px;">입양 가능한 아이들 보기 →</a>
    </div>
  </div>
</div>


<!-- 카드형 섹션들 -->
<div class="section">
  <h2>오늘의 유기동물</h2>
  <div class="card-container" id="animal-card-container">
      <!-- JS로 채움 -->
  </div>
</div>


<div class="section">
  <h2>진행 중인 봉사</h2>
  <div class="card-container" id="volunteer-card-container">
      <!-- JS로 채움 -->
  </div>
</div>


<div class="section shelter-map-section" data-aos="fade-up" data-aos-delay="200">
  <h2>등록된 보호시설</h2>
  <div class="shelter-map-wrapper">
    <!-- 좌측: 지도 및 숫자 -->
    <div class="shelter-map-left">
      <div class="korea-map-wrapper">
        <img src="${root}/resources/web/images/indexbody/korea-map.png" class="korea-map" alt="지역지도">
        
        <div class="shelter-stats">등록된 보호시설 총:&ensp;<strong id="shelter-count">0</strong> 개소</div>
        <!-- 라벨 -->
        <div class="region-label" style="top: 40px; left: 80px;">서울</div>
        <div class="region-label" style="top: 70px; left: 45px;">경기</div>
        <div class="region-label" style="top: 60px; left: 140px;">강원</div>
        <div class="region-label" style="top: 100px; left: 100px;">충북</div>
        <div class="region-label" style="top: 130px; left: 55px;">충남</div>
        <div class="region-label" style="top: 140px; left: 155px;">경북</div>
        <div class="region-label" style="top: 180px; left: 65px;">전북</div>
        <div class="region-label" style="top: 210px; left: 130px;">경남</div>
        <div class="region-label" style="top: 240px; left: 50px;">전남</div>
        <div class="region-label" style="top: 330px; left: 60px;">제주</div>
      </div>
      
    </div>
     
      <div class="info-card light" id="shelter-list-box" style="max-height: 300px; overflow-y: auto;">
        <!-- JS로 채워짐 -->
      </div>
  

    <!-- 우측: 안내 박스 2개 -->
    <div class="shelter-map-right">
      <div class="info-card dark">
        <h4>동물사랑배움터</h4>
        <p>동물 관련 영업자, 명예감시원, 맹견소유자는 연1회 법정의무교육을 이수해야 합니다.</p>
        <a href="https://apms.epis.or.kr/home/kor/main.do" class="go-link">바로가기 →</a>
      </div>

      <div class="info-card light">
        <h4>동물보호 상담센터</h4>
        <p>1577-0954<br>시스템 문의: 054-810-8626<br>상담시간: 평일 09:00 ~ 18:00</p>
      </div>
    </div>
  </div>
</div>


<script src="https://cdn.jsdelivr.net/npm/swiper@11/swiper-bundle.min.js"></script>
<script src="https://unpkg.com/aos@2.3.4/dist/aos.js"></script>
<script>
  AOS.init();

const swiperTop = new Swiper('.swiper-top', {
  loop: true,
  speed: 6000,
  slidesPerView: 'auto',
  spaceBetween: 20,
  autoplay: {
    delay: 0,
    disableOnInteraction: false,
  },
  freeMode: {
    enabled: true,
    momentum: false,
  },
});

const swiperBottom = new Swiper('.swiper-bottom', {
  loop: true,
  speed: 5000,
  slidesPerView: 'auto',
  spaceBetween: 20,
  autoplay: {
    delay: 0,
    disableOnInteraction: false,
  },
  freeMode: {
    enabled: true,
    momentum: false,
  },
});

</script>

<!-- 오늘의 유기동물 로직 -->
<script>
  document.addEventListener("DOMContentLoaded", function () {
    fetch(root +"/api/main/animals/recent")
    .then(function (res) { return res.json(); })
    .then(function (data) {
      if (data.status === 200) {
        var container = document.querySelector("#animal-card-container");
        container.innerHTML = "";

        for (var i = 0; i < data.data.length; i++) {
          let animal = data.data[i];

          var card = document.createElement("div");
          card.className = "card";
          card.setAttribute("data-aos", "fade-up");
          if (i > 0) {
            card.setAttribute("data-aos-delay", i * 100);
          }

          card.innerHTML =
          "<img src='" + root + animal.thumbnailPath + "' alt='" + animal.name + "' style='width:100%; height:160px; object-fit:cover; border-radius:8px;'>" +
          "<div style='margin-top:10px; font-weight:bold;'>" + animal.name + "</div>" +
          "<div style='font-size:13px; color:#666;'>" + animal.breedName + " / " + animal.gender + "</div>";

          // 카드 클릭 시 상세 페이지 이동
          card.addEventListener("click", function () {
            window.location.href = root + "/animals/" + animal.idx;
          });

          container.appendChild(card);
        }
      } else {
        console.error("API 오류:", data.message);
      }
    })
    .catch(function (err) {
      console.error("요청 실패:", err);
    });
});

//진행 중인 봉사 로직 
document.addEventListener("DOMContentLoaded", function () {
  // 진행 중인 봉사 가져오기
  fetch(root + "/api/main/volunteers/recent")
    .then((res) => res.json())
    .then((data) => {
      if (data.status === 200) {
        const container = document.querySelector("#volunteer-card-container");
        container.innerHTML = "";

        data.data.forEach((v, i) => {
          const card = document.createElement("div");
          card.className = "card";
          card.setAttribute("data-aos", "fade-up");
          if (i > 0) card.setAttribute("data-aos-delay", i * 100);

          card.innerHTML =
            "<img src='" + root + v.thumbnailPath + "' alt='" + v.title + "' style='width:100%; height:160px; object-fit:cover; border-radius:8px;'>" +
            "<div style='margin-top:10px; font-weight:bold;'>" + v.title + "</div>" +
            "<div style='font-size:13px; color:#666;'>" + v.location + " / " + v.formattedDate + "</div>";
          
            // 카드 클릭 시 상세 페이지 이동
          card.addEventListener("click", function () {
            window.location.href = root + "/volunteers/" + v.idx;
          });

          container.appendChild(card);
        });
      } else {
        console.error("봉사 API 오류:", data.message);
      }
    })
    .catch((err) => console.error("봉사 요청 실패:", err));
});

document.addEventListener("DOMContentLoaded", function () {
  // 공지사항 목록 불러오기
  fetch(root + "/api/main/notices/recent")
    .then(res => res.json())
    .then(data => {
      if (data.status === 200) {
        const container = document.querySelector(".notice-list");
        container.innerHTML = "";

        for (let i = 0; i < data.data.length; i++) {
          const notice = data.data[i];
          const summary = notice.contentSummary.replace(/\n/g, " ");
          const trimmed = summary.length > 80 ? summary.substring(0, 80) + "..." : summary;

          container.innerHTML +=
            "<li>" +
              "<a href='" + root + "/support/" + notice.idx + "'>" + notice.title + "</a>" +
              "<p>" + trimmed + "</p>" +
              "<span class='date'>" + notice.createdDate + "</span>" +
            "</li>";
        }
      } else {
        console.error("공지사항 API 실패:", data.message);
      }
    })
    .catch(err => {
      console.error("공지사항 요청 실패:", err);
    });
  });

//등록된 보호소 전체 수
document.addEventListener("DOMContentLoaded", function () {
  fetch(root + "/api/main/shelters/count")
    .then(res => res.json())
    .then(data => {
      if (data.status === 200) {
        document.querySelector("#shelter-count").innerText = data.data;
      } else {
        console.error("보호소 개수 API 오류:", data.message);
      }
    })
    .catch(err => console.error("보호소 개수 요청 실패:", err));
});


const regionLabels = document.querySelectorAll(".region-label");
const shelterListBox = document.querySelector("#shelter-list-box");

// 보호소 리스트 렌더링 함수
function renderShelters(regionName) {
  fetch(root + "/api/main/shelters/by-region?region=" + encodeURIComponent(regionName))
    .then(res => res.json())
    .then(data => {
      if (data.status === 200) {
      shelterListBox.innerHTML = "<div style='font-size: 16px; font-weight: bold; margin-bottom: 12px; border-bottom: 1px solid #ccc; padding-bottom: 5px; color: #2e7f90;'>" + regionName + " 보호소 목록</div>";
      
        if (data.data.shelters.length === 0) {
          shelterListBox.innerHTML += "<div style='font-size: 16px; margin-bottom: 12px; padding-bottom: 5px;'>등록된 보호시설이 없습니다.</div>";
          return;
        }
        data.data.shelters.forEach(shelter => {
          const div = document.createElement("div");
          div.className = "shelter-item";
          div.style.marginBottom = "12px";
          div.style.paddingBottom = "12px";
          div.style.borderBottom = "1px solid #eee";  
          div.style.cursor = "pointer";

        div.innerHTML =
          "<strong style='font-size: 15px;'>" + shelter.name + "</strong><br>" +
          "<span style='font-size:13px; color:#666;'>" + shelter.address + "</span>";

        div.addEventListener("click", function () {
          window.location.href = root + "/shelters/" + shelter.idx;
        });

        shelterListBox.appendChild(div);
      });
      } else {
        shelterListBox.innerHTML = "<p>데이터를 불러오는 데 실패했습니다.</p>";
      }
    })
    .catch(err => {
      console.error("보호소 리스트 오류:", err);
      shelterListBox.innerHTML = "<p>오류가 발생했습니다.</p>";
    });
}

// 지도 라벨 클릭 시 해당 지역 보호소 불러오기
regionLabels.forEach(label => {
  label.addEventListener("click", function () {
    const regionName = this.textContent.trim();
    renderShelters(regionName);
  });
});

// 기본 선택: 서울
document.addEventListener("DOMContentLoaded", function () {
  renderShelters("서울");
});

</script>
  
<%@ include file="/WEB-INF/views/common/index/indexFooter.jsp" %>
</body>
</html>
