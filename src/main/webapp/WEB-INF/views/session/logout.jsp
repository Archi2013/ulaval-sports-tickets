<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="false" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    

<!DOCTYPE html>
<html lang="fr">
<%@include file="../layout/head.jsp"%>
<body>
    <%@include file="../layout/header.jsp" %>
    
    <h2> Vous êtes déconnecté !</h2>
    <nav>
        <ul>
            <li><a href="/">Retourner à l'accueil ▷</a></li>
            <li><a href="/session/signin">Se reconnecter ▷</a></li>
        </ul>
    </nav>
</body>
</html>