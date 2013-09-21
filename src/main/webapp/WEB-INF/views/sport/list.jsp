<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="false" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="utf-8">
    <title>Liste des sports disponibles</title>
</head>
<body>
    <%@include file="../layout/header.jsp" %>
    
    <h2>Liste des sports disponibles</h2>
    
    <div>
        <table>
            <thead>
                <tr>
                    <th>Nom</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach items="${sports}" var="element">
                    <tr>
                        <td><a href="${element.name}">${element.name}</a></td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
    </div>
    ${serverTime}
    
    <%@include file="../layout/footer.jsp" %>
</body>
</html>