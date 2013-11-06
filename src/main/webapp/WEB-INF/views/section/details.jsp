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
	
	<c:choose>
	   <c:when test="${connectedUser}"></c:when>
	   <c:otherwise>
	       <div class="information-block-error-skin margin-25-50">
               Vous devez être connecté pour pouvoir sélectionner des billets à acheter. Pour ce faire, <a href="/session/signin">cliquez ici</a>.
           </div>
	   </c:otherwise>
	</c:choose>

	<div class="wrapper-ticket-card">
		<div class="ticket-card">
			<div class="ticket-admin-info">
			<span class="half-admin-info">Nombre de billets : <strong>${section.numberOfTickets}</strong></span><!-- important
			--><span class="half-admin-info text-align-right">Prix : <strong>${section.price} ${currency}</strong></span>
			</div>
			<h3>
			    <img class="rouge-et-or-logo" alt="Logo du Rouge et Or" src="/resources/image/rouge-et-or-logo.gif"/> <strong>VS</strong> ${section.opponents}
	        </h3>
			<div class="ticket-date-info">${section.date}</div>
			<div class="ticket-seat-info">
				Type d'admission : <strong>${section.admissionType}</strong><br>
				<c:choose>
				    <c:when test="${section.isGeneralAdmission()}"></c:when>
				    <c:otherwise>
				        Section : <strong>${section.sectionName}</strong>
				    </c:otherwise>
				</c:choose>
			</div>
		</div>
	</div>
	<c:choose>
        <c:when test="${connectedUser}">
			<div class="wrapper-choose-tickets-form padding-20">
			   <c:url value="/paiement" var="chooseAction"/>
			   <form:form id="choose-tickets-form" commandName="chooseTicketsForm" action="${chooseAction}" method="POST">
			       <form:hidden path="sectionName"/>
			       <form:hidden path="gameId"/>
			       <c:choose>
			           <c:when test="${section.isGeneralAdmission()}">
			               <form:hidden path="selectedSeats"/>
			               <form:label path="numberOfTicketsToBuy">Choisir le nombre de billets :</form:label> <form:input type="number" size="4" min="1" max="${section.numberOfTickets}" path="numberOfTicketsToBuy"/>
			           </c:when>
			           <c:otherwise>
			               <form:hidden path="numberOfTicketsToBuy"/>
			               <label>Choisir des billets suivant les sièges :</label>
			               <div class="wrapper-seats">
			                   <form:checkboxes items="${section.seats}" path="selectedSeats" delimiter="<br>"/>
			               </div>
			           </c:otherwise>
			       </c:choose>
			       <span style="padding-left: 20px; padding-right:20px;"></span>
			       <input type="submit" value="Sélectionner" class="standard-button-rounded-border orange-button"/>
			   </form:form>
			</div>
	    </c:when>
    </c:choose>

	<%@include file="../layout/footer.jsp"%>
</body>
</html>
