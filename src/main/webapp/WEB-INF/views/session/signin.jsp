<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page session="false"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<c:set var="pageTitle" value="Se connecter" />

<!DOCTYPE html>
<html lang="fr">
<%@include file="../layout/head.jsp"%>
<body>
	<%@include file="../layout/header.jsp"%>
	<%@include file="../layout/menu.jsp"%>

	<h2>${pageTitle}</h2>

	<form action="/session/auth" method="post">
		<%@ include file="../session/authForm.jsp"%>
	</form>
	<div class="text-align-center margin-25">
		<p class="">Vous n'avez pas de compte ?</p>
		<a href="/session/signup" class="standard-button-round orange-button margin-25">S'inscrire !</a>
	</div>


	<%@include file="../layout/footer.jsp"%>
</body>
</html>