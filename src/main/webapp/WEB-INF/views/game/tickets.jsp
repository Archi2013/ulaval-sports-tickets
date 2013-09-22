<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="false" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="utf-8">
    <title>Liste des billets disponibles pour le match: ${gameId}</title>
    <link href="/resources/style/standard.css" title="standard" media="screen" rel="stylesheet">
</head>
<body>
    <%@include file="../layout/header.jsp" %>
    
    <h2>Liste des billets disponibles pour le match: ${gameId}</h2>
    
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
                        <td>${ticket.ticketId}</td>
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