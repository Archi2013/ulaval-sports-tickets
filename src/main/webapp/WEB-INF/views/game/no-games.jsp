<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page session="false"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<c:set var="pageTitle"
	value="${games.sportName} : Aucun match disponible" />

<!DOCTYPE html>
<html lang="fr">
<%@include file="../layout/head.jsp"%>
<body>
	<%@include file="../layout/header.jsp"%>
	<%@include file="../layout/menu.jsp"%>

	<h2>${pageTitle}</h2>

	<div class="information-block-error-skin margin-25-50">
		Aucun match n'est disponible pour ce sport. Vous pouvez cependant
		rechercher des matchs pour un autre sport en retournant à la liste des sports.
	</div>

	<%@include file="../layout/footer.jsp"%>
</body>
</html>