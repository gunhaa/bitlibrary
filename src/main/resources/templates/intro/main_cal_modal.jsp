<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<!--알림창 모달-->
<div id="main_popup_layer">
    <div class="main_popup_box">
        <div class="main_popup_header">
            <p></p> <span>일정</span>
        </div>
            
        <!--팝업 컨텐츠 영역-->
        <div class="main_popup_content">
            <div>
                <table>
                    <thead>
                        <tr>
                            <td>구분</td>
                            <td>일정</td>
                        </tr>
                    </thead>
                    <tbody>
                        <tr>
                            <td> <div class="cat_color gong"></div> <span>공휴일</span></td>
                            <td>추석</td>
                        </tr>
                    </tbody>
                </table>
            </div>
        </div>

        <!--팝업 버튼 영역-->
        <div class="main_popup_btn">
            <button id="main_confirm_btn">확인</button>
        </div>

    </div>
</div>

<script src="/resources/js/intro/main_cal_hour.js"></script>