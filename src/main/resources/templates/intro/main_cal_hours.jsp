<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<script src="https://kit.fontawesome.com/58046189b2.js" crossorigin="anonymous"></script>
<script src='https://cdn.jsdelivr.net/npm/fullcalendar@6.1.15/index.global.min.js'></script>

<section class="main-cal-hours-section">
    <article class="main-cal-article">
        <div class="main-cal-suround">
            <div id="date-div"></div>

            <div class="calendar-btn-area">
                <div id="prev-btn">&lt;</div>
                <div id="today-btn">오늘</div>
                <div id="next-btn">&gt;</div>
            </div>

            <div class="calendar-category-suround">
                <span class="calendar-category" onclick="typeCalendar(1)"> <span class="category-color gong"></span> <span>공휴일</span></span>
                <span class="calendar-category" onclick="typeCalendar(2)"> <span class="category-color hue"></span> <span>휴관일</span></span>
                <span class="calendar-category" onclick="typeCalendar(4)"> <span class="category-color classDay"></span> <span>클래스</span></span>
                <span class="calendar-category" onclick="typeCalendar(3)"> <span class="category-color eventDay"></span> <span>행사</span></span>
            </div>

        </div>
        <div class="surround-calendar-area">
            <div id="goToLibraryCal"> <a href="/intro/1/8/30"><i class="fa-solid fa-plus"></i></a></div>
            <div id='calendar'></div>
        </div>
    </article>

    <article class="main-hour-article">
        <div class="hour-title">
            <i class="fa-regular fa-clock"></i> 
            <span>자바도서관 운영시간</span>
            <div id="goToLibraryHour"><a href="/intro/1/8/29"><i class="fa-solid fa-plus"></i></a></div>
        </div>

        <div class="hour-table-area">
            <div>
                <table>
                    <thead>
                        <tr>
                            <td>실별</td>
                            <td>평일</td>
                            <td>주말</td>
                        </tr>
                    </thead>

                    <tbody>
                        <tr>
                            <td>문헌정보실</td>
                            <td>09:00 ~ 20:00</td>
                            <td>09:00 ~ 18:00</td>
                        </tr>
                        <tr>
                            <td>열람실</td>
                            <td>09:00 ~ 23:00</td>
                            <td>09:00 ~ 23:00</td>
                        </tr>
                    </tbody>
                </table>
            </div>
        </div>

        <div class="hour-holiday">※ 휴관일: 매주 금요일,설날 및 추석연휴, 공휴일 ※</div>
    </article>

</section>



