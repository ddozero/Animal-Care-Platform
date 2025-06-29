const API = {
    async request(url, method = 'GET', body = null) {
        try {
            const options = {
                method,
                headers: {
                    'Content-Type': 'application/json'
                }
            };

            if (body) {
                options.body = JSON.stringify(body);
            }

            const res = await fetch(url, options);

            const result = await res.json();

            if (!res.ok) {
                alert(result.errorMsg);
            }

            return result;
        } catch (err) {
            console.error(err);
            alert("에러: " + err.message);
            throw err;
        }
    },

    get(url) {
        return this.request(url, 'GET');
    },

    post(url, body) {
        return this.request(url, 'POST', body);
    },

    put(url, body) {
        return this.request(url, 'PUT', body);
    },

    delete(url) {
        return this.request(url, 'DELETE');
    }
};


const FileAPI = {
    async upload(url, formData) {
        try {
            const res = await fetch(url, {
                method: 'POST',
                body: formData
            });

            const result = await res.json();

            if (!res.ok) {
                alert(result.errorMsg);
            }

            return result;
        } catch (err) {
            console.error(err);
            alert("파일 업로드 에러: " + err.message);
            throw err;
        }
    }
};

function getJsonFromForm(formId) {
    const form = document.getElementById(formId);
    if (!form) throw new Error(`폼 ID를 찾을 수 없음`);

    const formData = new FormData(form);
    return Object.fromEntries(formData.entries()); // JSON 변환용
}

function getFormDataFromForm(formId) {
    const form = document.getElementById(formId);
    if (!form) throw new Error(`폼 ID를 찾을 수 없음`);

    return new FormData(form); // 파일 전송용
}

function makePaging(totalCnt, listSize, pageSize, cp, pageId, onPageClick) {
    const container = document.getElementById(pageId);
    container.innerHTML = "";

    // 전체 페이지 수 계산
    let totalPage = parseInt(totalCnt / listSize) + 1;
    if (totalCnt % listSize === 0) totalPage--;

    // 현재 유저가 속한 그룹 계산 
    let userGroup = parseInt(cp / pageSize);
    if (cp % pageSize === 0) userGroup--;

    // ◀ 처음 버튼
    if (cp > 1) {
        const firstBtn = document.createElement("button");
        firstBtn.textContent = "«";
        firstBtn.onclick = function () {
            onPageClick(1);
        };
        container.appendChild(firstBtn);
    }

    // ◁ 이전 그룹 버튼
    if (userGroup !== 0) {
        const prevBtn = document.createElement("button");
        prevBtn.textContent = "‹";
        const temp = (userGroup - 1) * pageSize + pageSize;
        prevBtn.onclick = function () {
            onPageClick(temp);
        };
        container.appendChild(prevBtn);
    }

    // 페이지 번호 버튼들
    for (let i = userGroup * pageSize + 1; i <= userGroup * pageSize + pageSize; i++) {
        const btn = document.createElement("button");
        btn.textContent = i;
        btn.disabled = (i === cp);
        btn.onclick = function () {
            onPageClick(i);
        };
        container.appendChild(btn);

        if (i === totalPage) break;
    }

    // ▷ 다음 그룹 버튼
    let maxGroup = parseInt(totalPage / pageSize);
    if (totalPage % pageSize === 0) maxGroup--;
    if (userGroup !== maxGroup) {
        const nextBtn = document.createElement("button");
        nextBtn.textContent = "›";
        const temp = (userGroup + 1) * pageSize + 1;
        nextBtn.onclick = function () {
            onPageClick(temp);
        };
        container.appendChild(nextBtn);
    }

    // ▶ 마지막 버튼
    if (cp < totalPage) {
        const lastBtn = document.createElement("button");
        lastBtn.textContent = "»";
        lastBtn.onclick = function () {
            onPageClick(totalPage);
        };
        container.appendChild(lastBtn);
    }
}


