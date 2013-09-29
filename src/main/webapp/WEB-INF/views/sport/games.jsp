<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="false" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<c:set var="pageTitle" value="${sport.name} : liste des matchs disponibles" />

<!DOCTYPE html>
<html lang="fr">
<%@include file="../layout/head.jsp"%>
<body>
    <%@include file="../layout/header.jsp" %>
    
    <h2>${pageTitle}</h2>
    
    <div>
        <table>
            <thead>
                <tr>
                    <th>Identifiant</th>
                    <th>Date</th>
                    <th>Opposants</th>
                    <th>Billets disponibles</th>
                    <th>Action</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach items="${sport.games}" var="game">
                    <tr>
                        <td>${game.id}</td>
                        <td>${game.date}</td>
                        <td>${game.opponents}</td>
                        <td>${game.getNumberOfTickets()}</td>
                        <td><a href="match/${game.id}/billets" class="standard-button-round orange-button">Consulter</a></td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
    </div>
    
    <%@include file="../layout/footer.jsp" %>
</body>
</html>