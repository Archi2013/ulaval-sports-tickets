<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ page session="false" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<c:set var="pageTitle" value="Accueil" />

<!DOCTYPE html>
<html lang="fr">
<%@include file="layout/head.jsp"%>
<body>
    <%@include file="layout/header.jsp" %>

    <c:if test="${fn:length(user.username) > 0}">
        <h2>Bienvenue : ${user.username}</h2>
    </c:if>
    
    <nav>
        <ul>
            <li><a href="/session/signin">Se connecter ▷</a></li>
            <li><a href="/sport">Liste des sports disponibles ▷</a></li>
            <li><a href="/admin">Administration ▷</a></li>
        </ul>
    </nav>
    
    <%@include file="layout/footer.jsp" %>
    <p>Sur le serveur, c'est le ${serverTime}.</p>

</body>
</html>
