<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>

<nav class="navbar  navbar-dark navbar-expand-lg bg-dark" id="navbar">
    <div class="container-fluid">
        <a class="navbar-brand" href="" id="logo">
            <img src="images/logomovie.png" width="120" height="120" alt="">
        </a>
        <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
            <span class="navbar-toggler-icon"></span>
        </button>
        <div class="collapse navbar-collapse" id="navbarSupportedContent">
            <ul class="navbar-nav me-auto mb-2 mb-lg-0">
                <li class="nav-item">
                    <a class="nav-link" href="<spring:url value='/' />" >Home</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="<spring:url value='/login' />" >Login</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="<spring:url value='/register' />" >Register</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="#">Logout</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="#">My list</a>
                </li>
            </ul>
            <form class="d-flex" role="search">
                <input class="form-control me-2" type="search" placeholder="Search" aria-label="Search">
                <button class="btn btn-outline-success searchbtn" type="submit">Search</button>
            </form>
        </div>
    </div>
</nav>