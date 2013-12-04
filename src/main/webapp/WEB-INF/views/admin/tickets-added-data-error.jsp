<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ page session="false"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<c:set var="pageTitle"
	value="Erreur à l'enregistrement des nouveaux billets" />

<!DOCTYPE html>
<html lang="fr">
<%@include file="../layout/head.jsp"%>
<body>
	<%@include file="../layout/header.jsp"%>
	<%@include file="../layout/menu.jsp"%>

	<h2>${pageTitle}</h2>

	<div class="information-block-error-skin margin-25-50">Une erreur est survenue à l'enregistrement des données. Vérifiez la
	cohérence de la base de donnée xml et recommencez.</div>

	<%@include file="../layout/footer.jsp"%>
</body>
</html>