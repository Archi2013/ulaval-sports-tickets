<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="false" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<c:set var="pageTitle" value="Aucun match disponible" />

<!DOCTYPE html>
<html lang="fr">
<%@include file="../layout/head.jsp"%>
<body>
    <%@include file="../layout/header.jsp" %>
    <%@include file="../layout/menu.jsp" %>
    
    <div class="information-block-error-skin margin-25-50">
        Aucun ticket n'est disponible pour ce match. Vous pouvez toujours chercher un autre match afin de trouver des billets.
    </div>
    
    <%@include file="../layout/footer.jsp" %>
</body>
</html>