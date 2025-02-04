const fetchData = (url, onSuccess, errorMessage) => {
    fetch(url, { method: "GET", headers: { "Content-Type": "application/json" } })
    .then(response => {
        if (response.ok) return response.json();
        alert(errorMessage + ' 상태 코드: ' + response.status);
        throw new Error('Failed to fetch');
    })
    .then(onSuccess)
    .catch(error => {
        console.error('Error:', error);
        alert('서버와 연결하는 데 실패했습니다. 네트워크를 확인해주세요.');
    });
};

function cancelHttpRequest(apiUrl, data) {
    fetch(apiUrl, {
        method: "DELETE",
        headers: { "Content-Type": "application/json" },
        body: data
    })
    .then(resp => {
        if (!resp.ok) {
            alert("실패");
        }
    })
    .catch(e => console.log(e));
}

const updateTable = (headers, rows, emptyMessage) => {
    const tableHead = document.querySelector('#list-table > thead');
    const tableBody = document.querySelector('#list-table > tbody');
    tableHead.innerHTML = `<tr>${headers.map(h => `<th${h.style ? ` style=\"${h.style}\"` : ''}>${h.text}</th>`).join('')}</tr>`;
    tableBody.innerHTML = rows.length ? rows.join('') : `<tr><th style="height: 100px;" colspan="${headers.length}">${emptyMessage}</th></tr>`;
};

function createPagination(pageData, getDataFunction) {
    if(pageData.content.length === 0) return;

    const paginationContainer = document.getElementById('pagination');
    paginationContainer.innerHTML = '';
    const pageRange = 5;
    const startPage = Math.floor(pageData.number / pageRange) * pageRange;
    const endPage = Math.min(startPage + pageRange - 1, pageData.totalPages - 1);
    const prevPage = startPage > 0 ? startPage - 1 : 0;
    const nextPage = endPage < pageData.totalPages - 1 ? endPage + 1 : pageData.totalPages - 1;

    paginationContainer.append(
        createPageLink('<<', 0, getDataFunction),
        createPageLink('<', prevPage, getDataFunction)
    );

    for (let i = startPage; i <= endPage; i++) {
        paginationContainer.append(i === pageData.number ? createCurrentPage(i) : createPageLink(i + 1, i, getDataFunction));
    }

    paginationContainer.append(
        createPageLink('>', nextPage, getDataFunction),
        createPageLink('>>', pageData.totalPages - 1, getDataFunction)
    );
}

function createPageLink(text, page, getDataFunction) {
    const li = document.createElement('li');
    const a = document.createElement('a');
    a.textContent = text;
    a.addEventListener('click', () => getDataFunction(page));
    li.append(a);
    return li;
}

function createCurrentPage(page) {
    const li = document.createElement('li');
    const a = document.createElement('a');
    a.classList.add('current');
    a.textContent = page + 1;
    li.append(a);
    return li;
}