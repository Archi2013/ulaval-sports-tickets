<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page session="false"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<c:if test="${preferencesSaved}">
	<div class="information-block-succes-skin margin-25-50">Vos
		préférences de recherche ont été sauvegardées.</div>
</c:if>

<table class="standard-table margin-25">
	<thead>
		<tr>
			<th>Sport</th>
			<th>Opposants</th>
			<th>Lieu</th>
			<th>Date</th>
			<th>Section</th>
			<th>Nombre de billets</th>
			<th>Prix (${currency})</th>
			<th>Action</th>
		</tr>
	</thead>
	<tbody>
		<c:forEach items="${sections}" var="section">
			<tr>
				<td>${section.sport}</td>
				<td>${section.opponents}</td>
				<td>${section.location}</td>
				<td>${section.date}</td>
				<td>${section.section}</td>
				<td>${section.numberOfTicket}</td>
				<td>${section.price}</td>
				<td class="action-column"><a href="${section.url}"
					class="standard-button-round orange-button">Consulter</a></td>

			</tr>
		</c:forEach>
	</tbody>
</table>