<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<nav class="main-menu">
	<c:choose>
		<c:when test="${currentUser.admin}">
			<div class="menu-element">
				<a href="/admin">Administration</a>
			</div>
		</c:when>
	</c:choose>
	<div class="menu-element">
		<a href="/sport">Sports</a>
	</div>
	<div class="menu-element">
		<a href="/recherche">Recherche</a>
	</div>
	<c:choose>
		<c:when test="${currentUser.logged}">
			<div class="menu-element">
				<a href="/session/logout">Déconnexion ◀</a>
			</div>
		</c:when>
		<c:otherwise>
			<div class="menu-element">
				<a href="/session/signin">Connexion ▷</a>
			</div>
		</c:otherwise>
	</c:choose>

</nav>