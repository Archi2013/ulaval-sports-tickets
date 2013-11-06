<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="false" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<c:set var="pageTitle" value="Choix du mode de paiement" />

<!DOCTYPE html>
<html lang="fr">
<%@include file="../layout/head.jsp"%>
<body>
    <%@include file="../layout/header.jsp" %>
    
    <h2>${pageTitle}</h2>
    
    <c:if test="${not empty errorMessage}">
        <div class="information-block-error-skin margin-25-50">${errorMessage}</div>
    </c:if>
    
    ${x}
    
    <%@include file="../layout/footer.jsp" %>
</body>
</html>
