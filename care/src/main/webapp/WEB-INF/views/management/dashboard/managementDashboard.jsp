<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
    <!DOCTYPE html>
    <html>

    <head>
        <meta charset="UTF-8">
        <title>당신에게 다시가는 길 - 보호시설 통계</title>
        <link rel="stylesheet" href="https://cdn.jsdelivr.net/gh/orioncactus/pretendard/dist/web/static/pretendard.css">
        <script src="https://cdnjs.cloudflare.com/ajax/libs/Chart.js/4.4.0/chart.umd.min.js"></script>
        <script src="${pageContext.request.contextPath}/resources/web/common/commonUtils.js"></script>

        <style>
            /* ───── 기본 & 레이아웃 ───── */
            body {
                font-family: 'Pretendard', sans-serif;
                background-color: #f8f9fa;
                color: #333;
                margin: 0;
                padding: 0;
            }

            .container {
                max-width: 1200px;
                margin: 0 auto;
                padding: 40px 20px;
            }

            h1 {
                font-size: 28px;
                font-weight: 700;
                margin-bottom: 32px;
                color: #212529;
                text-align: center;
                /* Title centered */
            }

            .dashboard-layout {
                display: grid;
                grid-template-columns: repeat(auto-fit, minmax(400px, 1fr));
                gap: 24px;
            }

            /* ───── 카드 공통 스타일 ───── */
            .card {
                background-color: #ffffff;
                border-radius: 16px;
                padding: 24px;
                box-shadow: 0 8px 16px rgba(0, 0, 0, 0.05);
                transition: transform 0.2s ease, box-shadow 0.2s ease;
                display: flex;
                flex-direction: column;
            }

            .card:hover {
                transform: translateY(-5px);
                box-shadow: 0 12px 24px rgba(0, 0, 0, 0.08);
            }

            .card strong {
                font-size: 18px;
                font-weight: 600;
                color: #495057;
                display: block;
                margin-bottom: 20px;
                flex-shrink: 0;
            }

            /* ───── 개별 카드 스타일 ───── */

            /* 봉사 인원 통계 카드 */
            #volCard {
                grid-column: 1 / -1;
                /* 전체 너비 차지 */
            }

            /* 유기동물 통계 카드 */
            #adoptCard {
                flex-direction: row;
                align-items: center;
                gap: 24px;
            }

            #adoptCard .chart-container {
                position: relative;
                width: 160px;
                height: 160px;
                flex-shrink: 0;
            }

            #adoptCard .chart-text {
                flex-grow: 1;
                display: flex;
                flex-direction: column;
                gap: 12px;
                text-align: center;
                /* Card content centered */
            }

            #adoptCard .chart-text-labels,
            #adoptCard .chart-values {
                display: flex;
                justify-content: space-around;
                text-align: center;
            }

            #adoptCard .chart-text-labels span {
                font-size: 14px;
                color: #6c757d;
            }

            #adoptCard .chart-values span {
                font-size: 24px;
                font-weight: 700;
                color: #212529;
            }

            /* 차트 래퍼 */
            .chart-wrapper {
                position: relative;
                flex-grow: 1;
                min-height: 250px;
            }

            canvas {
                max-width: 100%;
                height: auto !important;
            }
        </style>

        <script>
            // JavaScript is unchanged as requested
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
                            backgroundColor: '#3ACDB2',
                            borderRadius: 6
                        }]
                    },
                    options: {
                        responsive: true,
                        maintainAspectRatio: false,
                        scales: {
                            x: { grid: { display: false } },
                            y: { beginAtZero: true, ticks: { precision: 0 }, grid: { color: '#e9ecef' } }
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
                            backgroundColor: ['#343a40', '#e9ecef'],
                            borderWidth: 0
                        }]
                    },
                    options: {
                        responsive: true,
                        maintainAspectRatio: false,
                        cutout: '75%',
                        plugins: {
                            legend: { display: false },
                            tooltip: { enabled: false }
                        }
                    },
                    plugins: [{
                        id: 'centerText',
                        beforeDraw(chart) {
                            const { ctx, chartArea: { width, height } } = chart;
                            ctx.save();
                            ctx.textAlign = 'center';
                            ctx.textBaseline = 'middle';

                            ctx.font = '600 14px Pretendard';
                            ctx.fillStyle = '#6c757d';
                            ctx.fillText('입양율', width / 2, height / 2 - 10);

                            ctx.font = 'bold 22px Pretendard';
                            ctx.fillStyle = '#212529';
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
                            pointBackgroundColor: '#fff',
                            pointBorderColor: '#3ACDB2',
                            pointBorderWidth: 2,
                            pointRadius: 4,
                            tension: 0.4
                        }]
                    },
                    options: {
                        responsive: true,
                        maintainAspectRatio: false,
                        scales: {
                            x: { grid: { display: false } },
                            y: { beginAtZero: true, ticks: { precision: 0 }, grid: { color: '#e9ecef' } }
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
        <%@ include file="/WEB-INF/views/common/management/managementHeader.jsp" %>

            <div class="container">
                <h1>보호시설 통계</h1>

                <div class="dashboard-layout">
                    <div id="volCard" class="card">
                        <strong>월별 봉사 인원 통계</strong>
                        <div class="chart-wrapper">
                            <canvas id="volChart"></canvas>
                        </div>
                    </div>

                    <div id="adoptCard" class="card">
                        <div class="chart-container">
                            <canvas id="adoptChart"></canvas>
                        </div>
                        <div class="chart-text">
                            <strong>연간 유기동물 통계</strong>
                            <div class="chart-text-labels">
                                <span id="yearText">2025년 유기동물 수</span>
                                <span>입양된 유기동물 수</span>
                            </div>
                            <div class="chart-values">
                                <span id="totalCount">0</span>
                                <span id="adoptedCount">0</span>
                            </div>
                        </div>
                    </div>

                    <div id="viewCard" class="card">
                        <strong>월별 방문자수 통계</strong>
                        <div class="chart-wrapper">
                            <canvas id="viewChart"></canvas>
                        </div>
                    </div>
                </div>
            </div>

            <%@ include file="/WEB-INF/views/common/index/indexFooter.jsp" %>

                <script>
                    window.addEventListener("DOMContentLoaded", function () {
                        volunteerDashboard();
                        adoptionDashboard();
                        viewDashboard();
                    });
                </script>
    </body>

    </html>