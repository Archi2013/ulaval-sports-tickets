<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ page session="false"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<!-- Ne pas reformater ce fichier sinon la suppression des espaces blancs ne fonctionnera plus -->

<c:set var="pageTitle"
	value="Fiche de la section: ${section.sectionName}" />

<!DOCTYPE html>
<html lang="fr">
<%@include file="../layout/head.jsp"%>
<body>
	<%@include file="../layout/header.jsp"%>
	<%@include file="../layout/menu.jsp"%>

	<h2>Fiche du billet</h2>

	<c:choose>
		<c:when test="${currentUser.isLogged()}"></c:when>
		<c:otherwise>
			<div class="information-block-error-skin margin-25-50">
				<spring:message
					code="error-message.section.details.not-connected-user" />
			</div>
		</c:otherwise>
	</c:choose>

	<div class="wrapper-ticket-card">
		<div class="ticket-card">
			<div class="ticket-admin-info">
				<span class="half-admin-info">Nombre de billets : <strong>${section.numberOfTickets}</strong>
				</span><!-- important supprime espace blanc
			--><span class="half-admin-info text-align-right">Prix : <strong>${section.price}
						${currency}</strong></span>
			</div>
			<h3>
				<img class="rouge-et-or-logo" alt="Logo du Rouge et Or"
					src="/resources/image/rouge-et-or-logo.gif" /> <strong>VS</strong>
				${section.opponents}
			</h3>
			<div class="ticket-date-info">${section.date}</div>
			<div class="ticket-location-info">${section.location}</div>
			<div class="ticket-seat-info">
				<c:choose>
					<c:when test="${section.isGeneralAdmission()}">Admission : <strong>Générale</strong>
					</c:when>
					<c:otherwise>
				        Section : <strong>${section.sectionName}</strong>
					</c:otherwise>
				</c:choose>
			</div>
		</div>
	</div>
	<c:choose>
		<c:when test="${currentUser.isLogged()}">
			<div class="wrapper-choose-tickets-form padding-20">
				<c:choose>
					<c:when test="${section.isGeneralAdmission()}">
						<c:url value="/panier/ajout-billets-generaux" var="addActionUrl" />
						<c:set value="chosenGeneralTicketsForm" var="addFormCommandName" />
					</c:when>
					<c:otherwise>
						<c:url value="/panier/ajout-billets-avec-siege" var="addActionUrl" />
						<c:set value="chosenWithSeatTicketsForm" var="addFormCommandName" />
					</c:otherwise>
				</c:choose>
				<form:form id="choose-tickets-form"
					commandName="${addFormCommandName}" action="${addActionUrl}" method="POST">
					<form:hidden path="section" />
					<form:hidden path="sport" />
					<form:hidden path="gameDate" />
					<c:choose>
						<c:when test="${section.isGeneralAdmission()}">
							<form:label path="numberOfTicketsToBuy">Choisir le nombre de billets :</form:label>
							<form:input type="number" size="4" min="1"
								max="${section.numberOfTickets}" path="numberOfTicketsToBuy" />
						</c:when>
						<c:otherwise>
							<label>Choisir des billets suivant les sièges :</label>
							<div class="wrapper-seats">
								<form:checkboxes items="${section.seats}" path="selectedSeats"
									delimiter="<br>" />
							</div>
						</c:otherwise>
					</c:choose>
					<span style="padding-left: 20px; padding-right: 20px;"></span>
					<input type="submit" value="Sélectionner"
						class="standard-button-rounded-border orange-button" />
				</form:form>
			</div>
		</c:when>
	</c:choose>

	<%@include file="../layout/footer.jsp"%>
</body>
</html>
