<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ page session="false"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<c:set var="pageTitle" value="Ajout de billets avec siège" />

<!DOCTYPE html>
<html lang="fr">
<%@include file="../layout/head.jsp"%>
<body>
<<<<<<< HEAD
    <%@include file="../layout/header.jsp" %>
    <%@include file="../layout/menu.jsp" %>
    
    <h2>${pageTitle}</h2>
    
    <form:form action="/admin/ajout-billets-seated" method="POST" class="margin-25 form-admin">
        <form:label path="sportName"> Sport : </form:label><form:select path="sportName">
    		<form:option value = "${sportName}"> ${sportName} </form:option>
    	</form:select><br>
        <form:label path="gameDate">Match : </form:label><form:select path="gameDate">
            <c:forEach items="${gameSelectionVM}" var="game">
                <form:option value="${game.gameDate}">Partie du ${game.gameDate} contre ${game.opponents }</form:option>
            </c:forEach>
        </form:select><br>
               <form:label path="price">Prix : </form:label><form:input path="price"/><br>
        <form:label path="section">Section : </form:label><form:input path="section"/><br>
        <form:label path="seat">Siège : </form:label><form:input path="seat"/><br>
        <input type="submit" value="Ajouter" class="standard-button-rounded-border orange-button"/>
    </form:form>
    
    <%@include file="../layout/footer.jsp" %>
</body>
</html>
