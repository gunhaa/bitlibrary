const pagination = document.getElementById('pagination');

const getLikeBooks = () => {
    pagination.innerHTML = '';

    fetchData("/api/study/books/loans/current", data => {
        updateTable(
            [
                { text: "도서명" },
                { text: "저자"},
                { text: "발행처" },
                { text: "발행년도", style: "width: 80px;" },
                { text: "ISBN" },
                { text: "즐겨찾기", style: "width: 80px;" }
            ],
            data.map(item => `<tr>
                <td><a href="/search/books/31/?query=${item.title}&key=t">${item.title}</a></td>
                <td>${item.author}</td>
                <td>${item.publisher}</td>
                <td>${item.publicationDate}</td>
                <td>${item.isbn}</td>
                <td><span th:onclick="cancelBookLike(${book.id})" class="bookMarkStar">★</span></td>
            </tr>`),
            "즐겨찾기 내역이 없습니다"
        );
    }, "즐겨찾기 내역를 불러오는 데 실패했습니다.");
};

const cancelBookLike = id => cancelHttpRequest("/api/study/book/like", id);

window.onload = () => getLikeBooks();
