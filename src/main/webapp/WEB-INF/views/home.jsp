<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="false" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="utf-8">
    <title>Accueil | UL Sport</title>
    <link href="/resources/style/standard.css" title="standard" media="screen" rel="stylesheet">
</head>
<body>
    <%@include file="layout/header.jsp" %>
    
    <nav>
        <ul>
            <li><a href="/sport">Liste des sports disponibles ▷</a></li>
        </ul>
    </nav>
    
    <%@include file="layout/footer.jsp" %>
    
    <p>Sur le serveur, c'est le ${serverTime}.</p>
</body>
</html>
