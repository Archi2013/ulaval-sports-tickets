<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="false" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<c:set var="pageTitle" value="${sportName} : Aucun match disponible" />
    
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="utf-8">
    <title>${pageTitle} | UL Sport</title>
    <link href="/resources/style/standard.css" title="standard" media="screen" rel="stylesheet">
</head>
<body>
    <%@include file="../layout/header.jsp" %>
    
    <h2>${sportName}</h2>
    
    <div class="information-block information-block-error-skin">
        Aucun match n'est disponible pour ce sport. Vous pouvez cependant rechercher des matchs pour un autre sport en retournant à la <a href="/sport">liste des sports</a>.
    </div>
    
    <%@include file="../layout/footer.jsp" %>
</body>
</html>