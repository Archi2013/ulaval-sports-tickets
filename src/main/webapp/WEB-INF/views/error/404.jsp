<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="false" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="utf-8">
    <title>Page d'erreur | UL Sport</title>
    <%@include file="../utility/css-import.jsp" %>
</head>
<body>
    <%@include file="../layout/header.jsp" %>
    
    <div class="error-page-background"></div>
    
    <div class="error-page-content"><strong>404 : stagiaire en pause</strong><br>Pourquoi ne pas retourner à l'<a href="/">accueil</a>.</div>
    
    <%@include file="../layout/footer.jsp" %>
</body>
</html>
