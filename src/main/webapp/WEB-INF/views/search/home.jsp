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
                    <form:radiobuttons delimiter="<br>" items="${displayedPeriods}" path="displayedPeriod" onchange="doAjaxPost();"/>
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
                    <form:checkboxes delimiter="<br>" items="${ticketTypes}" path="selectedTicketTypes" onclick="doAjaxPost();"/>
                </div>
            </div>
        </form:form>
    </section>
    <section id="ajax-table">
        <table class="standard-table margin-25">
            <thead>
                <tr>
                    <th>Sport</th>
                    <th>Opposants</th>
                    <th>Date</th>
                    <th>Type d'admission</th>
                    <th>Section</th>
                    <th>Nombre de billets</th>
                    <th>Prix (CDN$)</th>
                    <th>Action</th>
                </tr>
            </thead>
            <tbody>
                
                    <tr>
                        <td>Volleyball Féminin</td>
                        <td>Radiants</td>
                        <td>10/11/2013 à 14h30 EST</td>
                        <td>VIP</td>
                        <td>Front Row</td>
                        <td>2</td>
                        <td>35,0</td>
                        <td><a href="/sport/volleyball-feminin/match/1/billets/vip-front-row" class="standard-button-round orange-button">Consulter</a></td>

                    </tr>
                
                    <tr>
                        <td>Volleyball Féminin</td>
                        <td>Radiants</td>
                        <td>10/11/2013 à 14h30 EST</td>
                        <td>Générale</td>
                        <td>Générale</td>
                        <td>2</td>
                        <td>15,5</td>
                        <td><a href="/sport/volleyball-feminin/match/1/billets/general-general" class="standard-button-round orange-button">Consulter</a></td>

                    </tr>
                
                    <tr>
                        <td>Basketball Masculin</td>
                        <td>Endormis</td>
                        <td>10/11/2013 à 15h30 EST</td>
                        <td>VIP</td>
                        <td>Rouges</td>
                        <td>5</td>
                        <td>20,0</td>
                        <td><a href="/sport/volleyball-feminin/match/1/billets/vip-rouges" class="standard-button-round orange-button">Consulter</a></td>

                    </tr>
                
            </tbody>
        </table>
    </section>
    
    <%@include file="../utility/javascript-import.jsp" %>
	<script type="text/javascript">
	    function doAjaxPost() {
	    	var str = $("#search-form").serialize();
	        $.ajax({
	            type: "POST",
	            data: str,
	            url: "/recherche/list",
	            success: function(response) {
	                $("#ajax-table").html(response);
	            }
	        });
	    }
	</script>
	<input type="button" value="GO!" onclick="doAjaxPost();" />
    
    <%@include file="../layout/footer.jsp" %>
</body>
</html>
