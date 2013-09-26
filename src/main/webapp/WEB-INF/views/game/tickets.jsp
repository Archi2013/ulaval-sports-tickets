<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="false" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<c:set var="pageTitle" value="Match ${gameId} : liste des billets disponibles" />

<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="utf-8">
    <title>${pageTitle} | UL Sport</title>
    <%@include file="../utility/css-import.jsp" %>
</head>
<body>
    <%@include file="../layout/header.jsp" %>
    
    <h2>${pageTitle}</h2>
    
    <div>
        <table>
            <thead>
                <tr>
                    	<th>Identifiant</th>
                    	<th>Prix</th>
                    	<th>Opposants</th>
                    	<th>Type d'admission</th>
                    	<th>Section</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach items="${tickets}" var="ticket">
                    <tr>
                        <td><a href="billet/${ticket.ticketId}">${ticket.ticketId}</a></td>
                        <td>${ticket.price}</td>
                        <td>${ticket.opponents}</td>
                        <td>${ticket.admissionType}</td>
                        <td>${ticket.section}</td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
    </div>
    
    <%@include file="../layout/footer.jsp" %>
</body>
</html>