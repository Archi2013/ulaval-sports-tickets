<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ page session="false" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<c:set var="pageTitle"
    value="Page d'erreur" />

<!DOCTYPE html>
<html lang="fr">
<%@include file="../layout/head.jsp"%>
<body>
    <%@include file="../layout/header.jsp" %>
    
    <div class="error-page-background"></div>
    
    <div class="error-page-content"><spring:message code="info-message.404-page" /></div>
    
    <%@include file="../layout/footer.jsp" %>
</body>
</html>
