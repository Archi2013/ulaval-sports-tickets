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
    
    <div>
        <table class="standard-table margin-25">
            <thead>
                <tr>
                    <th>Sport</th>
                    <th>Date</th>
                    <th>Type d'admission</th>
                    <th>Section</th>
                    <th>Nombre de billets</th>
                </tr>
            </thead>
            <tbody>
                <tr>
                    <td>${payment.sectionForPaymentViewModel.sport}</td>
                    <td>${payment.sectionForPaymentViewModel.date}</td>
                    <td>${payment.sectionForPaymentViewModel.admissionType}</td>
                    <td>${payment.sectionForPaymentViewModel.sectionName}</td>
                    <td>${payment.sectionForPaymentViewModel.numberOfTicketsToBuy}</td>
                </tr>
            </tbody>
        </table>
    </div>
    Total : ${payment.cumulatedPrice}
    
    <%@include file="../layout/footer.jsp" %>
</body>
</html>
