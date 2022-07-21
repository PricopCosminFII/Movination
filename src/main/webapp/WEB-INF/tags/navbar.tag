<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<nav class="navbar  navbar-dark navbar-expand-lg bg-dark" id="navbar">
    <div class="container-fluid">
        <a class="navbar-brand" href="" id="logo">
            <img src="images/logomovie.png" width="120" height="120" alt="">
        </a>
        <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarSupportedContent"
                aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
            <span class="navbar-toggler-icon"></span>
        </button>
        <div class="collapse navbar-collapse" id="navbarSupportedContent">
            <ul class="navbar-nav me-auto mb-2 mb-lg-0">
                <li class="nav-watchlistItem">
                    <a class="nav-link" href="<spring:url value='/' />">Home</a>
                </li>
                <li class="nav-watchlistItem">
                    <a class="nav-link" href="<spring:url value='/watchlist'/>">My list</a>
                </li>
                <c:choose>
                    <c:when test="${sessionScope.SPRING_SECURITY_CONTEXT.authentication.principal == null}">
                        <security:authorize access="isAnonymous()">
                            <li class="nav-watchlistItem">
                                <a class="nav-link" href="<spring:url value='/login' />">Login</a>
                            </li>
                            <li class="nav-watchlistItem">
                                <a class="nav-link" href="<spring:url value='/register' />">Register</a>
                            </li>
                        </security:authorize>
                    </c:when>
                    <c:otherwise>
                        <li class="nav-watchlistItem">
                            <a class="nav-link" href="<spring:url value='/j_spring_security_logout' />">Logout</a>
                        </li>
                    </c:otherwise>
                </c:choose>
            </ul>
            <div class="custom-search">
            <form:form class="d-flex" role="search" method="get" action="search" id="searchform">
                <input class="form-control me-2" type="search" placeholder="Search" aria-label="Search" name="searchTerm">
                <button class="fa btn btn-outline-success searchbtn" type="submit">&#xf002</button>
            </form:form>
            </div>
        </div>
    </div>
</nav>