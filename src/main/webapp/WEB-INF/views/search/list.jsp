<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page session="false"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<table class="standard-table margin-25">
	<thead>
		<tr>
			<th>Sport</th>
			<th>Opposants</th>
			<th>Date</th>
			<th>Type d'admission</th>
			<th>Section</th>
			<th>Nombre de billets</th>
			<th>Prix (CDN$)</th>
			<th>Action</th>
		</tr>
	</thead>
	<tbody>
	    <c:forEach items="${tickets}" var="ticket">
		<tr>
			<td>${ticket.sport}</td>
			<td>${ticket.opponents}</td>
			<td>${ticket.date}</td>
			<td>${ticket.admissionType}</td>
			<td>${ticket.section}</td>
			<td>${ticket.numberOfTicket}</td>
			<td>${ticket.price}</td>
			<td><a
				href="/sport/volleyball-feminin/match/1/billets/vip-front-row"
				class="standard-button-round orange-button">Consulter</a></td>

		</tr>
		</c:forEach>
	</tbody>
</table>