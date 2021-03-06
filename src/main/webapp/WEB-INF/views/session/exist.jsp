<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page session="false"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<c:set var="pageTitle" value="Réessayer" />

<!DOCTYPE html>
<html lang="fr">
<%@include file="../layout/head.jsp"%>
<body>
	<%@include file="../layout/header.jsp"%>
	<%@include file="../layout/menu.jsp"%>

	<h2>${pageTitle}</h2>

	<div class="information-block-error-skin margin-25-50">Le nom
		d'utilisateur est déjà pris !</div>

    <c:set var="formButtonName" value="S'inscrire" />
	<form action="/usager/enregistrer" method="post">
		<%@ include file="../session/authForm.jsp"%>
	</form>

	<%@include file="../layout/footer.jsp"%>
</body>
</html>