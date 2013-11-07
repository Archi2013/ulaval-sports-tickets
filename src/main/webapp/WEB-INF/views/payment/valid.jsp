<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="false" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<c:set var="pageTitle" value="Achat effectué" />

<!DOCTYPE html>
<html lang="fr">
<%@include file="../layout/head.jsp"%>
<body>
    <%@include file="../layout/header.jsp" %>
    
    <h2>${pageTitle}</h2>
    
    <div class="information-block-succes-skin margin-25-50">
        Votre achat de ${cumulativePrice} ${currency} a bien été effectué. Retourner à l'<a href="/">accueil</a>.
    </div>
    
    <%@include file="../layout/footer.jsp" %>
</body>
</html>
