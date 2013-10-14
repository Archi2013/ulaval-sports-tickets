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
    
    <nav>
        <ul>
            <li><a href="/admin/match">Ajout d'un match ▷</a></li>
        </ul>
    </nav>
    
    <%@include file="../layout/footer.jsp" %>
</body>
</html>