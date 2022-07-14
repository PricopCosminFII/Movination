<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<%@include file="/WEB-INF/tags/head.tag"%>
<body>
<n:navbar/>
<div class="container px-5 py-5">
    <div id="box-container" class="box-container">
        <c:forEach items="${movies}" var="movie">
            <div id="col" class="col mx-2">
                <img src="${movie.picture}" alt="" width="200" height="300">
                <p>${movie.rating} <i class='fa-solid fa-star' style='color: #f3da35'></i></p>
                <p>${movie.name}</p>
                <div class="btn-holder">
                    <button class="watchlist-btn" type="submit">See more details</button>
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
    </div>
</div>

<n:footer/>
<script src="js/main.js"></script>
</body>
</html>