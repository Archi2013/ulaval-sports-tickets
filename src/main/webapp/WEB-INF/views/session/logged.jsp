<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page session="false"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<c:set var="pageTitle" value="Vous êtes déjà connecté" />

<!DOCTYPE html>
<html lang="fr">
<%@include file="../layout/head.jsp"%>
<body>
	<%@include file="../layout/header.jsp"%>
	<%@include file="../layout/menu.jsp"%>

	<h2>${pageTitle} <span class="normal">${currentUser.username} !</span>
	</h2>

	<div class="information-block-error-skin margin-25-50">
		Vous ne pouvez pas vous connecter et/ou vous enregistrer si vous êtes
		déjà connecté dans le système. Vous devez vous déconnecté auparavant.
	</div>

	<%@include file="../layout/footer.jsp"%>

</body>
</html>