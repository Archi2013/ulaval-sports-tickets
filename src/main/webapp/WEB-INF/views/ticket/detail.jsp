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
<link href="/resources/style/standard.css" title="standard"
	media="screen" rel="stylesheet">
</head>
<body>
	<%@include file="../layout/header.jsp"%>

	<h2>Fiche du billet</h2>

	<div class="billet">
		<section class="ticket-admin-info">
			<span>ID: ${ticketId} </span></br> <span>Prix :
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
			<span>Type d'admission : ${ticket.admissionType}</span></br> <span>Section
				: ${ticket.section}</span>
		</section>
	</div>

	<a href="/match/${ticket.game.id}/billets">Retour au
			match</a>


	<!--
    <div class="fiche">
        <ul>
            <li>ID : ${ticketId}</li>
            <li>Opposants : ${ticket.opponents}</li>
            <li>Date et heure : ${ticket.getGameDateFormatted()}</li>
            <li>Type d'admission : ${ticket.admissionType}</li>
            <li>Section : ${ticket.section}</li>
            <li>Prix : ${ticket.getPriceFormatted()} $</li>
        </ul>
    </div>
    -->

	<%@include file="../layout/footer.jsp"%>
</body>
</html>
