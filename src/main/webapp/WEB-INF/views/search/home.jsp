<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page session="false"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<c:set var="pageTitle" value="Recherche de billets" />

<!-- Ne pas reformater cette page sinon la suppressions des espaces blancs ne fonctionnera pas -->

<!DOCTYPE html>
<html lang="fr">
<%@include file="../layout/head.jsp"%>
<body>
	<%@include file="../layout/header.jsp"%>
	<%@include file="../layout/menu.jsp"%>

	<h2>${pageTitle}</h2>

	<section class="search-block">
		<c:url value="/recherche/sauvegarde-preferences" var="searchAction" />
		<form:form id="search-form" commandName="ticketSearchForm"
			action="${searchAction}" method="POST">
			<div class="search-criterion">
				<h4 class="search-criterion-title">Sport(s)</h4>
				<div class="search-criterion-elements">
					<form:checkboxes delimiter="<br>" items="${sportList}"
						path="selectedSports" onclick="doAjaxPost();" />
				</div>
			</div><!-- important supprime espace blanc
            --><div class="search-criterion">
				<h4 class="search-criterion-title">Période affichée</h4>
				<div class="search-criterion-elements">
					<c:forEach items="${displayedPeriods}" var="period">
						<form:radiobutton path="displayedPeriod" value="${period.name()}"
							label="${period.toString()}" onchange="doAjaxPost();" />
						<br>
					</c:forEach>
				</div>
			</div><!-- important supprime espace blanc
            --><div class="search-criterion">
				<h4 class="search-criterion-title">Localisation</h4>
				<div class="search-criterion-elements">
					<form:checkbox path="localGameOnly" onclick="doAjaxPost();"
						id="localGameOnly" />
					<form:label path="localGameOnly">à domicile seulement</form:label>
				</div>
			</div><!-- important supprime espace blanc
            --><div class="search-criterion">
				<h4 class="search-criterion-title">Type de billet</h4>
				<div class="search-criterion-elements">
					<c:forEach items="${ticketKinds}" var="kind">
						<form:checkbox path="selectedTicketKinds" value="${kind.name()}"
							label="${kind.toString()}" onclick="doAjaxPost();" />
						<br>
					</c:forEach>
				</div>
			</div>
			<c:if test="${currentUser.isLogged()}">
				<input type="submit" value="Sauvegarder"
					class="standard-button-rounded-border orange-button" />
			</c:if>
		</form:form>
	</section>
	<section id="ajax-table"><%@include file="list.jsp"%></section>

	<%@include file="../utility/javascript-import.jsp"%>
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

	<%@include file="../layout/footer.jsp"%>
</body>
</html>
