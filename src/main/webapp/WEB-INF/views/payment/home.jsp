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
    Type de ticket : ${chooseTicketsForm.ticketKind}<br>
    Type d'admission : ${chooseTicketsForm.admissionType}<br>
    Nom de section : ${chooseTicketsForm.sectionName}<br>
    Date : ${chooseTicketsForm.date}<br>
    Opposants : ${chooseTicketsForm.opponents}<br>
    Sport : ${chooseTicketsForm.sport}<br><br>
    Sièges : ${chooseTicketsForm.selectedSeats.size()}<br>
    Nombre de tickets : ${chooseTicketsForm.numberOfTicketsToBuy}
    </div>
    
    <%@include file="../layout/footer.jsp" %>
</body>
</html>
