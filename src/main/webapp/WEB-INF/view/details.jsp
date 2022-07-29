<%--
  Created by IntelliJ IDEA.
  User: crbe1606
  Date: 7/14/2022
  Time: 8:42 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<%@include file="/WEB-INF/tags/head.tag" %>
<body>
<n:navbar/>
<div class="container moviedetails" id="moviedetails">
    <div class="animate__animated animate__fadeInDown">
        <div id="mainContainer">
            <div class="movie-cover">
                <img id="poster" src="${movieDetails.picture}" alt="img">
            </div>
            <div id="info">
                <h2>${movieDetails.name}</h2>
                <h5>
                    <c:choose>
                        <c:when test="${movieDetails.rating!=null&&movieDetails.rating>0}">
                            <p> Movie rating: ${movieDetails.rating} <i class='fa-solid fa-star'
                                                                         style='color: #f3da35'></i>
                            </p>
                        </c:when>
                        <c:otherwise>
                            <p> Movie rating: No rating
                            </p>
                        </c:otherwise>
                    </c:choose>
                    <p>Year: ${movieDetails.year}</p>
                    <p>Runtime: ${movieDetails.minutes} min </p>
                    <p> <c:forEach items="${moviecategory}" var="category" varStatus="status">
                        <c:choose>
                            <c:when test="${status.count < fn:length(moviecategory)}">
                                ${category.name} /
                            </c:when>
                            <c:otherwise>
                                ${category.name}
                            </c:otherwise>
                        </c:choose>
                    </c:forEach>
                    </p>
                </h5>
                <p>${movieDetails.description}</p>
                <c:choose>
                    <c:when test="${isInWatchlist==true}">
                        <button class="addlist" id="removeMovie" movie="${movieDetails.id}">Remove from Watchlist
                        </button>
                    </c:when>
                    <c:otherwise>
                        <button class="addlist" id="addMovie" movie="${movieDetails.id}">Add to Watchlist</button>
                    </c:otherwise>
                </c:choose>
            </div>
        </div>
    </div>
    <div class="recommendation">
        <c:if test="${moviesRecommendation.size()!=0}">
            <h3>Featured movies with the same categories as "${movieDetails.name}" </h3>
        </c:if>
    </div>
    <div class="hero-section animate__animated animate__slideInLeft">
    <c:forEach items="${moviesRecommendation}" var="movie" varStatus="status">
        <c:if test="${status.count <= 3 && fn:length(moviesRecommendation) >=status.count}">
        <div class="card-grid col mx-2">
            <div class="card-movie">
                <div class="card__background" style="background-image: url(${movie.picture})"></div>
                <div class="card__content">
                    <h3 class="card__heading">${movie.name}</h3>
                    <button class="details-btn" type="submit" value="${movie.id}">See more details</button>
                </div>
            </div>
        </div>
    </c:if>
    </c:forEach>
    </div>
</div>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
<script src="../../js/watchlist.js"></script>
<n:footer/>
</body>
</html>
