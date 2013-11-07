<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="false" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<c:set var="pageTitle" value="Choix du mode de paiement" />

<!DOCTYPE html>
<html lang="fr">
<%@include file="../layout/head.jsp"%>
<body>
    <%@include file="../layout/header.jsp" %>
    <%@include file="../layout/menu.jsp" %>
    
    <h2>${pageTitle}</h2>
    
    <c:if test="${not empty errorMessage}">
        <div class="information-block-error-skin margin-25-50">${errorMessage}</div>
    </c:if>
    
    <div class="total-price-container padding-20 margin-25-0">
        <span>Total : </span><strong>${cumulativePrice} ${currency}</strong>
    </div>
    
    <div class="wrapper-payment-form padding-20">
        <c:url value="/paiement/validation-achat" var="validationAction"/>
	    <form:form id="payment-form" class="padding-20" commandName="paymentForm" action="${validationAction}" method="POST">
	        <form:label path="creditCardType">Type de carte de crédit : </form:label>
	        <form:select path="creditCardType">
	            <c:forEach items="${creditCardTypes}" var="type">
	                <form:option value="${type.name()}">${type.toString()}</form:option>
	            </c:forEach>
	        </form:select>
	        <form:errors path="creditCardType" cssClass="error"></form:errors>
	        <br>
	        <form:label path="creditCardNumber">No de carte de crédit : </form:label>
	        <form:input path="creditCardNumber" type="number"/>
	        <form:errors path="creditCardNumber" cssClass="error"></form:errors><br>
	        <form:label path="securityCode">Code de sécurité au verso : </form:label>
	        <form:input path="securityCode" type="number"/>
	        <form:errors path="securityCode" cssClass="error"></form:errors><br>
	        <form:label path="creditCardUserName">Nom sur la carte : </form:label>
	        <form:input path="creditCardUserName"/>
	        <form:errors path="creditCardUserName" cssClass="error"></form:errors><br>
	        <form:label path="expirationMonth" type="number">Mois d'expiration : </form:label>
            <form:select path="expirationMonth">
                <form:option value="1">janvier</form:option>
                <form:option value="2">février</form:option>
                <form:option value="3">mars</form:option>
                <form:option value="4">avril</form:option>
                <form:option value="5">mai</form:option>
                <form:option value="6">juin</form:option>
                <form:option value="7">juillet</form:option>
                <form:option value="8">août</form:option>
                <form:option value="9">septembre</form:option>
                <form:option value="10">octobre</form:option>
                <form:option value="11">novembre</form:option>
                <form:option value="12">décembre</form:option>
            </form:select>
            <form:errors path="expirationMonth" cssClass="error"></form:errors><br>
            <form:label path="expirationYear">Année d'expiration : </form:label>
            <form:select path="expirationYear">
                <form:option value="2014">2014</form:option>
                <form:option value="2015">2015</form:option>
                <form:option value="2016">2016</form:option>
                <form:option value="2017">2017</form:option>
                <form:option value="2018">2018</form:option>
                <form:option value="2019">2019</form:option>
            </form:select>
            <form:errors path="expirationYear" cssClass="error"></form:errors><br>
            <div class="text-align-center">
                <input type="submit" value="Payer le montant" class="standard-button-rounded-border orange-button"/>
            </div>
	    </form:form>
    </div>
    
    <%@include file="../layout/footer.jsp" %>
</body>
</html>
