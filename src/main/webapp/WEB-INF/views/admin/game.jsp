<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ page session="false"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<c:set var="pageTitle" value="Ajout d'un match" />

<!DOCTYPE html>
<html lang="fr">
<%@include file="../layout/head.jsp"%>
<body>
	<%@include file="../layout/header.jsp"%>
	<%@include file="../layout/menu.jsp"%>

	<h2>${pageTitle}</h2>

	<form:form action="/admin/ajout-match" method="POST"
		class="margin-25 form-admin">
		<form:label path="sport">Sport : </form:label>
		<form:select path="sport">
			<c:forEach items="${sportsVM.sports}" var="sport">
				<form:option value="${sport.name}">${sport.name}</form:option>
			</c:forEach>
		</form:select>
		<br>
		<form:label path="opponents">Opposants : </form:label>
		<form:input path="opponents" />
		<br>
		<form:label path="location"> Lieu: </form:label>
		<form:input path="location" />
		<br>
		<form:label path="date">Date de la rencontre(aaaa-mm-jj hh:mm) : </form:label>
		<form:input path="date" type="datetime" value="2014-01-01 13:00" />

		<input type="submit" value="Ajouter"
			class="standard-button-rounded-border orange-button" />
	</form:form>

	<%@include file="../layout/footer.jsp"%>
</body>
</html>
