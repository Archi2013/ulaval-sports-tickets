<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="false" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<c:set var="pageTitle" value="Liste des sports disponibles" />

<!DOCTYPE html>
<html lang="fr">
<%@include file="../layout/head.jsp"%>
<body>
    <%@include file="../layout/header.jsp" %>
    <%@include file="../layout/menu.jsp" %>
    
    <h2>${pageTitle}</h2>
    
    <nav class="horizontal-button-menu margin-50">
        <c:forEach items="${sports.sports}" var="sport">
            <div class="menu-element"><a href="/sport/${sport.url}/matchs" class="standard-button-round blue-button">${sport.name}</a></div>
        </c:forEach>
    </nav>
    
    <%@include file="../layout/footer.jsp" %>
</body>
</html>
