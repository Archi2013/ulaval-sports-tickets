<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="false" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<c:set var="pageTitle" value="Liste des matchs disponibles pour : ${sportName}" />
    
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="utf-8">
    <title>${pageTitle} | UL Sport</title>
    <link href="/resources/style/standard.css" title="standard" media="screen" rel="stylesheet">
</head>
<body>
    <%@include file="../layout/header.jsp" %>
    
    <h2>Liste des matchs disponibles pour : ${sportName}</h2>
    
    <div>
        <table>
            <thead>
                <tr>
                    <th>Identifiant</th>
                    <th>Billets disponibles</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach items="${games}" var="game">
                    <tr>
                        <td><a href="/match/${game.id}/billets">${game.id}</a></td>
                        <td>${game.numberOfTickets}</td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
    </div>
    
    <%@include file="../layout/footer.jsp" %>
</body>
</html>