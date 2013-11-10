<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<nav class="main-menu">
	<div class="menu-element"><a href="/admin"><spring:message code="label.main-menu.administration" /></a></div>
	<div class="menu-element"><a href="/sport"><spring:message code="label.main-menu.sports" /></a></div>
	<div class="menu-element"><a href="/recherche"><spring:message code="label.main-menu.search" /></a></div>
	<c:choose>
	   <c:when test="${connectedUser}">
	       <div class="menu-element"><a href="/session/logout"><spring:message code="label.main-menu.logout" /> ◀</a></div>
	   </c:when>
	   <c:otherwise>
	       <div class="menu-element"><a href="/session/signin"><spring:message code="label.main-menu.login" /> ▷</a></div>
	   </c:otherwise>
	</c:choose>
	
</nav>