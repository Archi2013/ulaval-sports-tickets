<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page session="false"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<div>
<h3>Critères de recherche :</h3>
Sports sélectionnés : <c:forEach items="${searchForm.selectedSports}" var="sport">
                        <span>${sport}, </span>
                      </c:forEach>
                      <br>
Période affichée : ${searchForm.displayedPeriod}
<br>
À domicile : <c:if test="${searchForm.localGame}">Vrai</c:if>
<br>
Types de billet : <c:forEach items="${searchForm.selectedTicketTypes}" var="type">
                      <span>${type}, </span>
                  </c:forEach>
</div>
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