<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page session="false"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<c:set var="pageTitle"
	value="Fiche du billet No ${ticket.id}" />

<!DOCTYPE html>
<html lang="fr">
<%@include file="../layout/head.jsp"%>
<body>
	<%@include file="../layout/header.jsp"%>

	<h2>Fiche du billet</h2>

    <div class="wrapper-ticket-card">
		<div class="ticket-card">
			<div class="ticket-admin-info">
			<span class="half-admin-info">No : <strong>${ticket.id}</strong></span><!-- important
			--><span class="half-admin-info text-align-right">Prix : <strong>${ticket.price} CDN$</strong></span>
			</div>
			<h3>
			    <img class="rouge-et-or-logo" alt="Logo du Rouge et Or" src="/resources/image/rouge-et-or-logo.gif"/> <strong>VS</strong> ${ticket.opponents}
	        </h3>
			<div class="ticket-date-info">${ticket.date}</div>
			<div class="ticket-seat-info">
			Type d'admission : <strong>${ticket.admissionType}</strong><br>
			Section : <strong>${ticket.section}</strong>
			</div>
		</div>
	</div>

	<%@include file="../layout/footer.jsp"%>
</body>
</html>
