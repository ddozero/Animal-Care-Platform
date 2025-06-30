<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
    <!DOCTYPE html>
    <html>

    <head>
        <meta charset="UTF-8">
        <title>Insert title here</title>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/Chart.js/4.4.0/chart.umd.min.js"></script>
        <script src="${pageContext.request.contextPath}/resources/web/common/commonUtils.js"></script>
        <script>
            async function volunteerDashboard() {
                const res = await API.get('/care/api/management/dashboards/volunteers');
                if (res.status !== 200) { alert(res.errorMsg); return; }

                const labels = res.data.map(d => d.month.slice(5).replace(/^0/, '') + '월');
                const counts = res.data.map(d => d.count);

                new Chart(document.getElementById('volChart'), {
                    type: 'bar',
                    data: {
                        labels,
                        datasets: [{
                            data: counts,
                            backgroundColor: '#3ACDB2'
                        }]
                    },
                    options: {
                        responsive: false,
                        scales: {
                            x: { grid: { display: false } },
                            y: { beginAtZero: true, ticks: { precision: 0 }, grid: { display: false } }
                        },
                        plugins: {
                            legend: { display: false },
                            tooltip: { callbacks: { label: v => v.raw + ' 명' } }
                        }
                    }
                });
            }

            async function adoptionDashboard() {
                const res = await API.get('/care/api/management/dashboards/animals');
                if (res.status !== 200) { alert(res.errorMsg); return; }

                const { year, totalAnimals, adoptedAnimals, adoptionRate } = res.data;
                const remainAnimals = totalAnimals - adoptedAnimals;

                document.getElementById("yearText").innerText = year + "년 유기동물 수";
                document.getElementById("totalCount").innerText = totalAnimals;
                document.getElementById("adoptedCount").innerText = adoptedAnimals;

                new Chart(document.getElementById('adoptChart'), {
                    type: 'doughnut',
                    data: {
                        labels: ['입양됨', '미입양'],
                        datasets: [{
                            data: [adoptedAnimals, remainAnimals],
                            backgroundColor: ['#2d2f38', '#e0e0e0'],
                            borderWidth: 0
                        }]
                    },
                    options: {
                        responsive: false,
                        cutout: '75%',
                        plugins: {
                            legend: { display: false },
                            tooltip: { enabled: false },
                            title: {
                                display: true,
                                text: '입양율\n' + adoptionRate.toFixed(1) + '%',
                                position: 'center',
                                color: '#fff',
                                font: {
                                    size: 16,
                                    weight: 'bold'
                                }
                            }
                        }
                    },
                    plugins: [{
                        id: 'centerText',
                        beforeDraw(chart) {
                            const { ctx, chartArea: { width, height } } = chart;
                            ctx.save();
                            ctx.fillStyle = '#2d2f38';
                            ctx.textAlign = 'center';
                            ctx.font = 'bold 16px sans-serif';
                            ctx.fillText('입양율', width / 2, height / 2 - 5);
                            ctx.font = 'bold 18px sans-serif';
                            ctx.fillText(adoptionRate.toFixed(1) + '%', width / 2, height / 2 + 15);
                        }
                    }]
                });
            }

            async function viewDashboard() {
                 const res = await API.get('/care/api/management/dashboards/views');
                if (res.status !== 200) { alert(res.errorMsg); return; }

                const labels = res.data.map(d => d.month.slice(5).replace(/^0/, '') + '월');
                const views = res.data.map(d => d.views);

                new Chart(document.getElementById('viewChart'), {
                    type: 'line',
                    data: {
                        labels: labels,
                        datasets: [{
                            data: views,
                            fill: true,
                            borderColor: '#3ACDB2',
                            backgroundColor: 'rgba(58, 205, 178, 0.1)',
                            pointBackgroundColor: '#3ACDB2',
                            tension: 0.3
                        }]
                    },
                    options: {
                        responsive: false,
                        scales: {
                            x: { grid: { display: false } },
                            y: { beginAtZero: true, ticks: { precision: 0 }, grid: { display: false } }
                        },
                        plugins: {
                            legend: { display: false },
                            tooltip: {
                                callbacks: {
                                    label: ctx => ctx.raw + '회'
                                }
                            }
                        }
                    }
                });
            }
        </script>
    </head>

    <body>
        <%@ include file="/WEB-INF/views/common/index/indexHeader.jsp" %>
            <h1>보호시설 통계</h1>
            <div id="volCard">
                <strong style="display:block;margin-bottom:6px;">봉사 인원 통계</strong>
                <canvas id="volChart" width="360" height="160"></canvas>
            </div>
            <div id="adoptcard">
                <div>
                    <strong>유기동물 통계</strong>
                    <canvas id="adoptChart" width="100" height="100"></canvas>
                </div>
                <div class="chart-text">
                    <div>
                        <span id="yearText">2025년 유기동물 수</span>
                        <span style="margin-left: 20px;">입양된 유기동물 수</span>
                    </div>
                    <div class="chart-values">
                        <span id="totalCount">0</span>
                        <span id="adoptedCount">0</span>
                    </div>
                </div>
            </div>
            <div id="viewCard">
                <strong>방문자수 통계</strong>
                <canvas id="viewChart" width="300" height="100"></canvas>
            </div>
            <script>
                window.addEventListener("DOMContentLoaded", function () {
                    volunteerDashboard();
                    adoptionDashboard();
                    viewDashboard();
                });
            </script>
    </body>

    </html>