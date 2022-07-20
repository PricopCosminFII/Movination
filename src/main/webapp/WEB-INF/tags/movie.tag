<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<div class="container px-5 py-5">
    <div id="box-container" class="box-container">
        <c:forEach items="${movies}" var="movie">
            <div id="col" class="col mx-2">
                <img src="${movie.picture}" alt="" width="200" height="300">
                <div class="movie-name">${movie.name}</div>
                <div class="movie-rating">Movie rating: ${movie.rating} <i class='fa-solid fa-star' style='color: #f3da35'></i></div>
                <div class="btn-holder">
                    <button class="details-btn" type="submit" value="${movie.id}">See more details</button>
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