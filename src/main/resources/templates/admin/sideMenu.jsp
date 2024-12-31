<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<link rel="stylesheet" href="/resources/css/admin/side.css">

<article class="side-menu">
	<div>관리자</div>
	<ul>
		<li class="select-menu">
			<a href="/admin/1">관리</a>
		</li>
		<li>
			<div class="side-wrap">
				<a href="/admin/1">회원 관리</a>
				<a href="/admin/2">게시글 관리</a>
				<a href="/admin/3">댓글 관리</a>
			</div>
		</li>
	</ul>
</article>