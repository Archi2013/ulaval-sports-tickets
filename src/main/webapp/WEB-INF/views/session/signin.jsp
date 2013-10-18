<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="false" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    

<!DOCTYPE html>
<html lang="fr">
<%@include file="../layout/head.jsp"%>
<body>
    <%@include file="../layout/header.jsp" %>
    
    <h2> Se connecter ! </h2>
    <form action="/session/auth" method="post">  
		<%@ include file="../session/authForm.jsp" %>
	</form> 
	<p class="margin-25"> Vous n'avez pas de compte? </p>
	<a href="/session/signup" class="standard-button-round orange-button margin-25">S'inscrire!</a> 
    
    
    <%@include file="../layout/footer.jsp" %>
</body>
</html>