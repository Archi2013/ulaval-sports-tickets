<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="false" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<c:set var="pageTitle" value="Recherche de billets" />

<!DOCTYPE html>
<html lang="fr">
<%@include file="../layout/head.jsp"%>
<body>
    <%@include file="../layout/header.jsp" %>
    
    <h2>${pageTitle}</h2>
    
    <section class="search-block">
        <c:url value="/recherche/execution" var="searchAction"/>
        <form:form id="search-form" commandName="ticketSearchForm" action="${searchAction}" method="POST">
            <div class="search-criterion">
                <h4 class="search-criterion-title">Sport(s)</h4>
                <div class="search-criterion-elements">
                    <form:checkboxes delimiter="<br>" items="${sportsList}" path="selectedSports" onclick="doAjaxPost();"/>
                </div>
            </div><!-- important supprime espace blanc
            --><div class="search-criterion">
                <h4 class="search-criterion-title">Période affichée</h4>
                <div class="search-criterion-elements">
                    <c:forEach items="${displayedPeriods}" var="period">
                        <form:radiobutton path="displayedPeriod" value="${period.name()}" label="${period.toString()}"  onchange="doAjaxPost();"/><br>
                    </c:forEach>
                </div>
            </div><!-- important supprime espace blanc
            --><div class="search-criterion">
                <h4 class="search-criterion-title">Localisation</h4>
                <div class="search-criterion-elements">
                    <form:checkbox path="localGame" onclick="doAjaxPost();" id="localGame"/><form:label path="localGame">à domicile</form:label>
                </div>
            </div><!-- important supprime espace blanc
            --><div class="search-criterion">
                <h4 class="search-criterion-title">Type de billet</h4>
                <div class="search-criterion-elements">
                    <c:forEach items="${ticketTypes}" var="type">
                        <form:checkbox path="selectedTicketTypes" value="${type.name()}" label="${type.toString()}"  onclick="doAjaxPost();"/><br>
                    </c:forEach>
                </div>
            </div>
        </form:form>
    </section>
    <section id="ajax-table"></section>
    
    <%@include file="../utility/javascript-import.jsp" %>
	<script type="text/javascript">
	    function doAjaxPost() {
	    	var search_form = $("#search-form").serialize();
	        $.ajax({
	            type: "POST",
	            data: search_form,
	            url: "/recherche/list",
	            success: function(response) {
	                $("#ajax-table").html(response);
	            }
	        });
	    }
	</script>
    
    <%@include file="../layout/footer.jsp" %>
</body>
</html>
