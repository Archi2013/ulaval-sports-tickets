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
    <%@include file="layout/menu.jsp" %>
    
    <c:if test="${fn:length(user.username) > 0}">
        <h2>Bienvenue : ${user.username}</h2>
    </c:if>
    
    <blockquote class="featured-quote margin-50">
        <p>
        La jeunesse est un sport que l’on peut - que dis-je : que l’on doit pratiquer toute sa vie.<br><br>
        <span class="quote-author">— Henri Jeanson</span>
        </p>
    </blockquote>
    
    <blockquote class="featured-quote margin-50">
        <p>
        Il faut vider son esprit, être informe, sans contours - comme de l'eau.<br><br>
        <span class="quote-author">— Bruce Lee</span>
        </p>
    </blockquote>
    
    <blockquote class="featured-quote margin-50">
        <p>
        Qui n'a pas d'imagination n'a pas d'ailes.<br><br>
        <span class="quote-author">— Mohammed Ali</span>
        </p>
    </blockquote>
    
    <%@include file="layout/footer.jsp" %>
</body>
</html>
