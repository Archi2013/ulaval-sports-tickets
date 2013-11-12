<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page session="false"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<c:set var="pageTitle"
	value="${gameSections.opponents} : ${gameSections.date}" />

<!DOCTYPE html>
<html lang="fr">
<%@include file="../layout/head.jsp"%>
<body>
	<%@include file="../layout/header.jsp"%>
	<%@include file="../layout/menu.jsp" %>

	<h2>${pageTitle}</h2>

	<div>
		<table class="standard-table margin-25">
			<thead>
				<tr>
					<th>Section</th>
					<th>Nombre de billets</th>
					<th>Prix (${currency})</th>
					<th>Action</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${gameSections.sections}" var="section">
					<tr>
						<td>${section.sectionName}</td>
						<td>${section.numberOfTickets}</td>
						<td>${section.price}</td>
						<td class="action-column"><a href="billets/${section.url}"
							class="standard-button-round orange-button">Consulter</a></td>

					</tr>
				</c:forEach>
			</tbody>
		</table>
	</div>

	<%@include file="../layout/footer.jsp"%>
</body>
</html>