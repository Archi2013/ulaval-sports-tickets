<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="false" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<c:set var="pageTitle" value="Ajout d'un match" />

<!DOCTYPE html>
<html lang="fr">
<%@include file="../layout/head.jsp"%>
<body>
    <%@include file="../layout/header.jsp" %>
    
    <h2>${pageTitle}</h2>
    
    <form action="" method="post">
        <label for="sport">Sport : </label> 
        <select name="sport">
            <option value="basketball-masculin">Basketball Masculin</option>
            <option value="soccer-masculin">Soccer Masculin</option>
            <option value="volleyball-feminin">Volleyball Féminin</option>
        </select><br>
        <label for="opponents">Opposants : </label><input type="text" name="opponents"/><br>
        <label for="date">Date de la rencontre : </label><input type="date" name="date"/>
    </form>
    
    <%@include file="../layout/footer.jsp" %>
    
    <p>Sur le serveur, c'est le ${serverTime}.</p>
</body>
</html>
