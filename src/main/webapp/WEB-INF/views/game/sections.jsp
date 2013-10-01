<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="false" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<c:set var="pageTitle" value="Match ${game.id} : liste des billets disponibles" />

<!DOCTYPE html>
<html lang="fr">
<%@include file="../layout/head.jsp"%>
<body>
    <%@include file="../layout/header.jsp" %>
    
    <h2>${pageTitle}</h2>
    
    <div>
        <table>
            <thead>
                <tr>
                    	<th>Opposants</th>
                    	<th>Type d'admission</th>
                    	<th>Section</th>
                    	<th>Nombre de billets</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach items="${game.sections}" var="section">
                    <tr>
                        <td>${game.opponents}</td>
                        <td>${section.admissionType}</td>
                        <td>${section.sectionName}</td>
                        <td>${section.numberOfTickets}</td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
    </div>
    
    <%@include file="../layout/footer.jsp" %>
</body>
</html>