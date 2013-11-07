<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="false" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<c:set var="pageTitle" value="Réessayer" />

<!DOCTYPE html>
<html lang="fr">
<%@include file="../layout/head.jsp"%>
<body>
    <%@include file="../layout/header.jsp" %>
    <%@include file="../layout/menu.jsp" %>
    
    <h2>${pageTitle}</h2>
    
    <form action="/session/auth" method="post"> 
  		<div class="information-block-error-skin margin-25-50"> Mauvaise combinaison de nom d'utilisateur et mot de passe. </div>
		<%@ include file="../session/authForm.jsp" %>
	</form>  
      
    <%@include file="../layout/footer.jsp" %>
</body>
</html>