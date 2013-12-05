<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page session="false"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<c:set var="pageTitle" value="Panier d'achat" />

<!DOCTYPE html>
<html lang="fr">
<%@include file="../layout/head.jsp"%>
<body>
	<%@include file="../layout/header.jsp"%>
	<%@include file="../layout/menu.jsp"%>

	<h2>${pageTitle}</h2>

	<c:if test="${not empty errorMessage}">
		<div class="information-block-error-skin margin-25-50">${errorMessage}</div>
	</c:if>

	<div>
		<table class="standard-table margin-25">
			<thead>
				<tr>
					<th>Sport</th>
					<th>Date</th>
					<th>Opposants</th>
					<th>Lieu</th>
					<th>Nombre de billets</th>
					<th>Section</th>
					<th>Sièges</th>
					<th>Sous-total (${currency})</th>
				</tr>
			</thead>
			<tbody>
			     <c:forEach items="${payableItems.sections}" var="section">
				     <tr>
	                    <td>${section.sportName}</td>
	                    <td>${section.gameDate}</td>
	                    <td>${section.opponents}</td>
	                    <td>${section.location}</td>
	                    <td>${section.numberOfTicketsToBuy}</td>
	                    <td>${section.sectionName}</td>
	                    <c:choose>
	                        <c:when test="${section.generalAdmission}">
	                            <td>Libre</td>
	                        </c:when>
	                        <c:otherwise>
	                            <td>${section.selectedSeats}</td>
	                        </c:otherwise>
	                    </c:choose>
	                    <td>${section.subtotal}</td>
	                </tr>
			     </c:forEach>
			</tbody>
		</table>
	</div>
	<c:if test="${empty errorMessage}">
		<div class="total-price-container padding-20">
			<span>Total : </span><strong>${payableItems.cumulativePrice}
				${currency}</strong><span style="padding-left: 20px; padding-right: 20px;"></span>
			<a href="/paiement/mode-de-paiement"
				class="standard-button-rounded-border orange-button">Valider la
				commande</a>
		</div>
	</c:if>

	<%@include file="../layout/footer.jsp"%>
</body>
</html>
