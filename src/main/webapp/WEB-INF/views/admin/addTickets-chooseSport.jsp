<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ page session="false"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<c:set var="pageTitle" value="Selection du sport" />

<!DOCTYPE html>
<html lang="fr">
<%@include file="../layout/head.jsp"%>
<body>
	<%@include file="../layout/header.jsp"%>
	<%@include file="../layout/menu.jsp"%>

	<h2>${pageTitle}</h2>

	<form:form action="/admin/billets" method="POST"
		class="margin-25 form-admin">
		<form:label path="sport">Sport : </form:label>
		<form:select path="sport">
			<c:forEach items="${sportsVM.sports}" var="sport">
				<form:option value="${sport.name}">${sport.name}</form:option>
			</c:forEach>
		</form:select>
		<br>
		<form:label path="typeBillet">Type des billets : </form:label>
		<form:select path="typeBillet">
			<c:forEach items="${ticketKinds}" var="kind">
				<form:option value="${kind.name()}"> ${kind.toString()}</form:option>
			</c:forEach>
		</form:select>
		<br>
		<input type="submit" value="Continuer"
			class="standard-button-rounded-border orange-button" />
	</form:form>

	<%@include file="../layout/footer.jsp"%>
</body>
</html>
