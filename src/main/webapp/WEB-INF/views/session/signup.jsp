<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="false" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    

<!DOCTYPE html>
<html lang="fr">
<%@include file="../layout/head.jsp"%>
<body>
    <%@include file="../layout/header.jsp" %>
    


    <h2> S'inscrire sur UL Sport </h2>
    <form action="/session/save" method="post">  
		<%@ include file="../session/authForm.jsp" %>
	</form>  
    
    
    <%@include file="../layout/footer.jsp" %>
</body>
</html>