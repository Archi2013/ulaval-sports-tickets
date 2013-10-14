<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="false" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    

<!DOCTYPE html>
<html lang="fr">
<%@include file="../layout/head.jsp"%>
<body>
    <%@include file="../layout/header.jsp" %>
    


    <h2> Sign up</h2>
    <form action="/session/save" method="post">  
		<table>  
			<tbody>
				<tr>  
					<td>Username:</td>  
					<td><input type="text" name="usernameParam"></td>  
				</tr>
				<tr>
					<td>Password:</td> 
					<td><input type="text" name="passwordParam"></td>  
				</tr>
				<tr> 
					<td><input type="submit"></td>  
					<td></td>  
				</tr>  
			</tbody>
		</table>  
	</form>  
    
    
    <%@include file="../layout/footer.jsp" %>
</body>
</html>