<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<article class="side-menu-article">
	<c:forEach items="${category }" var="cat">
		<c:if test="${catLevel1 == cat.CAT_CODE }">
			<div class="category">${cat.CAT_NAME}</div>

			<ul class="side-menu">
				<c:forEach items="${category }" var="cat2">

					<c:if test="${cat.CAT_CODE == cat2.PARENT_CODE }">

						<c:choose>
							<c:when test="${catLevel2 == 9 && catLevel2 == cat2.CAT_CODE }">
								<li class="side-current"><a href="/intro/${cat.CAT_CODE}/${cat2.CAT_CODE}" class="side-current-a">${cat2.CAT_NAME }</a></li>
							</c:when>
						
							<c:when test="${catLevel2 == cat2.CAT_CODE }">
								<li class="side-current"><a href="/intro/${cat.CAT_CODE}/${cat2.CAT_CODE}" class="side-current-a">${cat2.CAT_NAME }</a>
									<ul>
										<c:forEach items="${category}" var="cat3">
											<c:if test="${catLevel2 == cat3.PARENT_CODE }">
												<c:choose>
													<c:when test="${catLevel3 == cat3.CAT_CODE }">
														<li><a href="/intro/${cat.CAT_CODE}/${cat2.CAT_CODE}/${cat3.CAT_CODE}" class="side-side-current">${cat3.CAT_NAME }</a></li>
													</c:when>

													<c:otherwise>
														<li><a href="/intro/${cat.CAT_CODE}/${cat2.CAT_CODE}/${cat3.CAT_CODE}">${cat3.CAT_NAME }</a></li>
													</c:otherwise>
												</c:choose>

											</c:if>
										</c:forEach>
									</ul>
								</li>

							</c:when>

							<c:otherwise>
								<li><a href="/intro/${cat.CAT_CODE}/${cat2.CAT_CODE}">${cat2.CAT_NAME }</a></li>
							</c:otherwise>
						</c:choose>


					</c:if>

				</c:forEach>
		</c:if>

	</c:forEach>

</article>



