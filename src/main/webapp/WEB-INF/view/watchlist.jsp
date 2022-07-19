<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html id="html">
<%@include file="/WEB-INF/tags/head.tag" %>


<body>
<n:navbar/>
<div class="container px-5 py-5">
    <div id="box-container" class="box-container">
        <c:choose>
        <c:when test="${watchlistContent!=null}">
        <c:forEach items="${watchlistContent}" var="item">
            <c:set var="movie" value="${item.key}"/>
            <c:set var="rating" value="${item.value.userRating}"/>

            <div id="col" class="col mx-2 watchlist_movie" movie="${movie.id}" rating="${rating!=null?rating:0}">
                <button type="button" id="close" class="btn-close btn-close-white remove" style="{text-align-all: left}"
                        movie="${movie.id}"></button>
                <img src="${movie.picture}" alt="" width="200" height="300">
                <p>${movie.name} </p>
                <p>Movie rating:
                    <c:choose>
                        <c:when test="${movie.rating!=null&&movie.rating>0}">
                            ${movie.rating} <i class='fa-solid fa-star' style='color: #f3da35'></i>
                        </c:when>
                        <c:otherwise>
                            No rating
                        </c:otherwise>
                    </c:choose>
                </p>
                <div class="stars" movie="${movie.id}" rating="${rating!=null?rating:0}">
                    Your rating:<br>
                    <c:forEach var="mark" begin="1" end="5">
                        <i class="fas fa-star" data-rate="${mark}" movie="${movie.id}"
                           rating="${rating!=null?rating:0}"></i>
                    </c:forEach>
                </div>
                <div class="btn-holder">
                    <button class="watchlist-btn" type="submit" movie="${movie.id}" rating="">Add rating</button>
                </div>
            </div>


        </c:forEach>
    </div>
    <div class="pagination">
        <li class="page-item previous-page">
            <a href="javascript:void(0)" class="page-link" aria-label="Previous">
                <span aria-hidden="true">«</span>
            </a>
        </li>
        <li class="page-item current-page"><a class="page-link" href="#">1</a></li>
        <li class="page-item dots"><a class="page-link" href="#">...</a></li>
        <li class="page-item current-page"><a class="page-link" href="#">5</a></li>
        <li class="page-item current-page"><a class="page-link" href="#">6</a></li>
        <li class="page-item dots"><a class="page-link" href="#">...</a></li>
        <li class="page-item current-page"><a class="page-link" href="#">10</a></li>
        <li class="page-item next-page">
            <a href="javascript:void(0)" class="page-link" aria-label="Next">
                <span aria-hidden="true">»</span>
            </a>
        </li>
        </c:when>
        <c:otherwise>
            <h1 style="size: A5" align="center">You don't have any movies added to your watchlist yet!</h1>
        </c:otherwise>
        </c:choose>
    </div>
</div>

<n:footer/>

<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
<script src="../../js/watchlist.js"></script>
</body>
</html>