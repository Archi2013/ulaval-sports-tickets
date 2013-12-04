<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<nav class="main-menu">
	<c:choose>
		<c:when test="${currentUser.isAdmin()}">
			<div class="menu-element">
				<a href="/admin"><spring:message
						code="label.main-menu.administration" /></a>
			</div>
		</c:when>
	</c:choose>
	<div class="menu-element">
		<a href="/sports"><spring:message code="label.main-menu.sports" /></a>
	</div>
	<div class="menu-element">
		<a href="/recherche"><spring:message code="label.main-menu.search" /></a>
	</div>
	<c:choose>
		<c:when test="${currentUser.isLogged()}">
			<div class="menu-element">
				<a href="/usager/deconnexion"><spring:message
						code="label.main-menu.logout" /> ◀</a>
			</div>
		</c:when>
		<c:otherwise>
			<div class="menu-element">
				<a href="/usager/connexion"><spring:message
						code="label.main-menu.login" /> ▷</a>
			</div>
		</c:otherwise>
	</c:choose>
	<c:choose>
        <c:when test="${currentUser.isLogged()}">
            <div class="menu-element">
                <a href="/panier"><spring:message code="label.main-menu.cart" /> ⊻</a>
            </div>
        </c:when>
    </c:choose>

</nav>