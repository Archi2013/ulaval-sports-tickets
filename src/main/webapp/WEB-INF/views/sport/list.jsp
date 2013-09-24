<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="false" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<c:set var="pageTitle" value="Liste des sports disponibles" />
    
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="utf-8">
    <title>${pageTitle} | UL Sport</title>
    <link href="/resources/style/standard.css" title="standard" media="screen" rel="stylesheet">
</head>
<body>
    <%@include file="../layout/header.jsp" %>
    
    <h2>Liste des sports disponibles</h2>
    
    <div class="vertical-normal-list">
        <ul>
            <c:forEach items="${sports}" var="sport">
                <li><a href="/sport/${sport.name}/matchs">${sport.name}</a></li>
            </c:forEach>
        </ul>
    </div>
    
    <%@include file="../layout/footer.jsp" %>
</body>
</html>
