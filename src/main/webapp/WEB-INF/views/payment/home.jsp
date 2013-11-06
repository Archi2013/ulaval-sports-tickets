<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="false" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<c:set var="pageTitle" value="Paiement des billets" />

<!DOCTYPE html>
<html lang="fr">
<%@include file="../layout/head.jsp"%>
<body>
    <%@include file="../layout/header.jsp" %>
    
    <h2>${pageTitle}</h2>
    
    <c:if test="${not empty errorMessage}">
        <div class="information-block-error-skin margin-25-50">${errorMessage}</div>
    </c:if>
    
    <div>
        <table class="standard-table margin-25">
            <c:set var="section" value="${payment.sectionForPaymentViewModel}" />
            <thead>
                <tr>
                    <th>Sport</th>
                    <th>Date</th>
                    <th>Opposants</th>
                    <th>Type d'admission</th>
                    <c:choose>
                        <c:when test="${section.isGeneralAdmission()}">
                            <th>Nombre de billets</th>
                        </c:when>
                        <c:otherwise>
                            <th>Section</th>
                            <th>Sièges</th>
                        </c:otherwise>
                    </c:choose>
                    <th>Sous-total (${currency})</th>
                </tr>
            </thead>
            <tbody>
                <tr>
                    <td>${section.sport}</td>
                    <td>${section.date}</td>
                    <td>${section.opponents}</td>
                    <td>${section.admissionType}</td>
                    <c:choose>
                        <c:when test="${section.isGeneralAdmission()}">
                            <td>${section.numberOfTicketsToBuy}</td>
                        </c:when>
                        <c:otherwise>
                            <td>${section.sectionName}</td>
                            <td>${section.selectedSeats}</td>
                        </c:otherwise>
                    </c:choose>
                    <td>${section.subtotal}</td>
                </tr>
            </tbody>
        </table>
    </div>
    <div class="total-price-container padding-20">
        <span>Total : </span><strong>${payment.cumulativePrice} ${currency}</strong><span style="padding-left: 20px; padding-right:20px;"></span>
        <a href="/paiement/mode-de-paiement" class="standard-button-rounded-border orange-button">Valider la commande</a>
    </div>
    
    <%@include file="../layout/footer.jsp" %>
</body>
</html>
