<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>봉사</title>

<style>
.header-title {
	font-size: 28px;
  	font-weight: bold;
  	margin-top: 50px;
  	margin-bottom : 20px;
  	text-align: center;
  	color: #3ACDB2;
}

.title-detail {
    text-align: center;
    margin-bottom: 50px; 
    color: #666; 
    font-size: 16px; 
}

.board-container {
	max-width: 1300px;
	margin: 0 auto;
	margin-bottom: 100px;
}

.fc-event {
  cursor: pointer;
}

.fc-today-button {
  background-color: #3ACDB2 !important;
  color: #fff !important;
  font-weight: bold;
  padding: 8px 16px !important;
  font-size: 14px !important;
  border: none !important;         
  border-radius: 12px !important;
  box-shadow: none !important;      
  cursor: pointer;
  transition: background-color 0.2s;
}

.fc-today-button:hover {
  background-color: #32b8a1 !important;
}


h2.fc-toolbar-title {
  font-size: 23px !important;
  font-weight: bold;
  color: #3ACDB2 !important;
}

.fc-prev-button,
.fc-next-button {
  background: none !important;     
  border: none !important;         
  color: #333 !important;         
  font-size: 20px !important;      
  padding: 6px !important;
  cursor: pointer;
  box-shadow: none !important;
}

.fc-prev-button:hover,
.fc-next-button:hover {
  background: none !important;
  color: #3ACDB2 !important; 
}


.fc-event,
.fc-event-dot,
.fc-daygrid-event {
  font-weight: bold;
  font-size: 14px;
  border-radius: 6px;
  padding: 2px 6px;
  border: none !important;
  box-shadow: none !important;
  cursor: pointer;
}

.fc-event:focus {
  outline: none !important;
}


</style>

<link href="https://cdn.jsdelivr.net/npm/fullcalendar@6.1.10/index.global.min.css" rel="stylesheet" />
  <script src="https://cdn.jsdelivr.net/npm/fullcalendar@6.1.10/index.global.min.js"></script>
</head>
<body>
<%@ include file="/WEB-INF/views/common/index/indexHeader.jsp"%>


	<div class="board-container">
		<div class="header-title">봉사하기</div>
		<div class="title-detail">다양한 봉사활동에 참여해보세요.</div>
	
		<div id="calendar"></div>
	</div>

<script src="${pageContext.request.contextPath}/resources/web/common/commonUtils.js"></script>
<script>

document.addEventListener('DOMContentLoaded', async function () {
	    const calendarEl = document.getElementById('calendar');

	    const calendar = new FullCalendar.Calendar(calendarEl, {
	      initialView: 'dayGridMonth',
	      locale: 'ko',
	      headerToolbar: {
	        left: 'prev,next',
	        center: 'title',
	        right: 'today'
	      },
	      contentHeight: 'auto',
	      events: await loadVolunteerEvents(),

	    eventClick: function(info) {
	        const idx = info.event.id;
	        const contextPath = "${pageContext.request.contextPath}";
	        window.location.href = contextPath + "/volunteers/" + idx;
	      }
	    });

	    calendar.render();
	  });

	  async function loadVolunteerEvents() {
	    const result = await API.get("/care/api/volunteers/calendar");
	    if (result.status !== 200) {
	      alert("봉사일정 불러오기 실패: " + result.message);
	      return [];
	    }
	    return result.data.map(item => {
	    	  let bgColor = "#3EB4DA";  // 기본: 모집중
	    	  let textColor = "#fff";   // 기본 글자색

	    	  switch (item.status?.trim()) {
	    	    case "모집예정":
	    	      bgColor = "#87CEFA";  // 밝은 파랑
	    	      break;
	    	    case "모집완료":
	    	      bgColor = "#999";  // 연회색
	    	      break;
	    	    case "종료":
	    	      bgColor = "#ccc";  // 연한 빨강
	    	      break;
	    	  }

	    	  return {
	    	    id: item.idx,
	    	    title: item.title,
	    	    start: item.volunteerDate,
	    	    backgroundColor: bgColor,
	    	    textColor: textColor
	    	  };
	    });
	  }
</script>


<%@ include file="/WEB-INF/views/common/index/indexFooter.jsp" %>
</body>
</html>