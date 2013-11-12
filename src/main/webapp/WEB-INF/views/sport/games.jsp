<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="false" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<c:set var="pageTitle" value="${games.sportName} : liste des matchs disponibles" />

<!DOCTYPE html>
<html lang="fr">
<%@include file="../layout/head.jsp"%>
<body>
    <%@include file="../layout/header.jsp" %>
    <%@include file="../layout/menu.jsp" %>
    
    <h2>${pageTitle}</h2>
    
    <div>
        <table class="standard-table margin-25">
            <thead>
                <tr>
                    <th>Date</th>
                    <th>Opposants</th>
                    <th>Lieu</th>
                    <th>Billets disponibles</th>
                    <th>Action</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach items="${games.games}" var="game">
                    <tr>
                        <td><span style="display:none;">${game.id}</span>${game.date}</td>
                        <td>${game.opponents}</td>
                        <td>${game.location}</td>
                        <td>${game.getNumberOfTickets()}</td>
                        <td class="action-column"><a href="match/${game.id}/billets" class="standard-button-round orange-button">Consulter</a></td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
    </div>
    
    <%@include file="../layout/footer.jsp" %>
</body>
</html>