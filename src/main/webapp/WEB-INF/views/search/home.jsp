<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="false" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<c:set var="pageTitle" value="Recherche de billets" />

<!DOCTYPE html>
<html lang="fr">
<%@include file="../layout/head.jsp"%>
<body>
    <%@include file="../layout/header.jsp" %>
    
    <h2>${pageTitle}</h2>
    
    <section class="search-left-block">
        <div class="search-criterion">
            <h4 class="search-criterion-title">Sport(s)</h4>
            <div class="search-criterion-elements">
                <input type="checkbox" name="soccer-masculin" id="soccer-masculin"/> <label for="soccer-masculin">Soccer Masculin</label><br />
                <input type="checkbox" name="volleyball-feminin" id="volleyball-feminin"/> <label for="volleyball-feminin">Volleyball Féminin</label><br />
                <input type="checkbox" name="basketball-masculin" id="basketball-masculin"/> <label for="basketball-masculin">Basketball Masculin</label>
            </div>
        </div><!-- important supprime espace blanc
        --><div class="search-criterion">
            <h4 class="search-criterion-title">Période</h4>
            <div class="search-criterion-elements">
                <label for="start-date">Date de début :</label> <input type="date" id="start-date" name="start-date" value="2013-01-01" size="11"/><br>
                <label for="end-date">Date de fin :</label> <input type="date" id="end-date" name="end-date" value="2014-01-01" size="11"/>
            </div>
        </div><!-- important supprime espace blanc
        --><div class="search-criterion">
            <h4 class="search-criterion-title">Localisation</h4>
            <div class="search-criterion-elements">
                <input type="checkbox" name="local-game" id="local-game"/> <label for="local-game">match à domicile</label>
            </div>
        </div><!-- important supprime espace blanc
        --><div class="search-criterion">
            <h4 class="search-criterion-title">Type de billet</h4>
            <div class="search-criterion-elements">
                <input type="checkbox" name="general-admission" id="general-admission"/> <label for="general-admission">admission générale</label><br>
                <input type="checkbox" name="seat-admission" id="seat-admission"/> <label for="seat-admission">avec siège</label>
            </div>
        </div>
    </section>
    <section class="search-right-block">
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
                        <td>Général</td>
                        <td>Général</td>
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
    
    <%@include file="../layout/footer.jsp" %>
</body>
</html>
