<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="false" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="utf-8">
    <title>Liste des jeux disponibles pour : ${sportName}</title>
</head>
<body>
    <%@include file="../layout/header.jsp" %>
    
    <h2>Liste des jeux disponibles pour : ${sportName}</h2>
    
    <div>
        <table>
            <thead>
                <tr>
                    <th>Identifiant</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach items="${games}" var="game">
                    <tr>
                        <td>${game.id}</td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
    </div>
    
    <%@include file="../layout/footer.jsp" %>
</body>
</html>