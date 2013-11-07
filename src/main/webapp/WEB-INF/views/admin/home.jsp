<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="false" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<c:set var="pageTitle" value="Administration : accueil" />

<!DOCTYPE html>
<html lang="fr">
<%@include file="../layout/head.jsp"%>
<body>
    <%@include file="../layout/header.jsp" %>
    <%@include file="../layout/menu.jsp" %>
    
    <nav class="horizontal-button-menu margin-50">
        <div class="menu-element"><a href="/admin/match" class="standard-button-round orange-button">Ajout d'un match</a></div>
        <div class="menu-element"><a href="/admin/billets/choisir-sport" class="standard-button-round orange-button">Ajout de billets</a></div>
    </nav>
    
    <%@include file="../layout/footer.jsp" %>
</body>
</html>
