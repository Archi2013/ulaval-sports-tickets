<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page session="false"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<c:set var="pageTitle"
	value="Liste des billets disponibles pour le match: ${ticketId}" />

<!DOCTYPE html>
<html lang="fr">
<head>
<meta charset="utf-8">
<title>${pageTitle}| UL Sport</title>
<%@include file="../utility/css-import.jsp" %>
</head>
<body>
	<%@include file="../layout/header.jsp"%>

	<h2>Fiche du billet</h2>

	<div class="ticket">
		<section class="ticket-admin-info">
			<span>ID: ${ticketId} </span><br> <span>Prix :
				${ticket.getPriceFormatted()} $</span>
		</section>
		<section class="ticket-game-info">
			<h3>
				<img id="laval-logo" alt="picture1"
					src="http://www.plongeon.qc.ca/images/content/logos/clubs/Rouge_et_Or.jpg">
				VS ${ticket.opponents}
			</h3>
			<span>Date et heure : ${ticket.getGameDateFormatted()}</span>
		</section>
		<section class="ticket-place-info">
			<span>Type d'admission : ${ticket.admissionType}</span><br> <span>Section
				: ${ticket.section}</span>
		</section>
	</div>

	<a href="/match/${ticket.game.id}/billets">Retour au match</a>

	<%@include file="../layout/footer.jsp"%>
</body>
</html>
