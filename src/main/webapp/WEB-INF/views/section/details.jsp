<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ page session="false"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<c:set var="pageTitle"
	value="Fiche de la section: ${section.admissionType}, ${section.sectionName}" />

<!DOCTYPE html>
<html lang="fr">
<%@include file="../layout/head.jsp"%>
<body>
	<%@include file="../layout/header.jsp"%>

	<h2>Fiche du billet</h2>

	<div class="wrapper-ticket-card">
		<div class="ticket-card">
			<div class="ticket-admin-info">
			<span class="half-admin-info">Nombre de billets : <strong>${section.numberOfTickets}</strong></span><!-- important
			--><span class="half-admin-info text-align-right">Prix : <strong>${section.price} CDN$</strong></span>
			</div>
			<h3>
			    <img class="rouge-et-or-logo" alt="Logo du Rouge et Or" src="/resources/image/rouge-et-or-logo.gif"/> <strong>VS</strong> ${section.opponents}
	        </h3>
			<div class="ticket-date-info">${section.date}</div>
			<div class="ticket-seat-info">
				Type d'admission : <strong>${section.admissionType}</strong><br>
				Section : <strong>${section.sectionName}</strong>
			</div>
		</div>
	</div>
	<div class="wrapper-choose-tickets-form padding-10">
	   <c:url value="/recherche/sauvegarde-preferences" var="searchAction"/>
	   <form:form id="choose-tickets-form" commandName="chooseTicketsForm" action="${searchAction}" method="POST">
	       <c:choose>
	           <c:when test="${chooseTicketsForm.isGeneralAdmission()}">
	               <form:label path="numberOfTicketsToBuy">Choisir le nombre de billets :</form:label> <form:input type="number" size="4" min="1" max="${section.numberOfTickets}" path="numberOfTicketsToBuy"/>
	           </c:when>
	           <c:otherwise>
	               <label>Choisir des billets suivant les sièges :</label>
	               <div class="wrapper-seats">
	                   <form:checkboxes items="${section.seats}" path="selectedSeats" delimiter="<br>"/>
	               </div>
	           </c:otherwise>
	       </c:choose>
	       <input type="submit" value="Sélectionner" class="standard-button-rounded-border orange-button"/>
	   </form:form>
	</div>

	<%@include file="../layout/footer.jsp"%>
</body>
</html>
