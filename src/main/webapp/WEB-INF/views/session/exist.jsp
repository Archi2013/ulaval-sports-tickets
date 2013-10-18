<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="false" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    

<!DOCTYPE html>
<html lang="fr">
<%@include file="../layout/head.jsp"%>
<body>
    <%@include file="../layout/header.jsp" %>
    
    <h2> Essaie encore! </h2>
    <form action="/session/save" method="post"> 
  		<div class="information-block-error-skin margin-25-50"> Le nom d'utilisateur est déjà pris !. </div>
		<%@ include file="../session/authForm.jsp" %>
	</form>  
      
    <%@include file="../layout/footer.jsp" %>
</body>
</html>