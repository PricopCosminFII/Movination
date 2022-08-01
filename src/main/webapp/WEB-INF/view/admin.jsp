<%--
  Created by IntelliJ IDEA.
  User: copr1512
  Date: 7/26/2022
  Time: 7:02 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="n" %>
<%@ taglib prefix="js" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Admin Movination</title>

    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
    <link href="https://fonts.googleapis.com/css2?family=Anton&display=swap" rel="stylesheet">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.1.1/css/all.min.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/animate.css/4.1.1/animate.min.css"/>
    <link href="https://fonts.googleapis.com/css2?family=Anton&display=swap" rel="stylesheet">
    <meta charset="utf-8">
    <link rel="stylesheet" href="css/style.css">
    <link rel="stylesheet" href="css/admin.css">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <link rel="stylesheet" type="text/css" href="http://cdnjs.cloudflare.com/ajax/libs/toastr.js/latest/toastr.min.css">
</head>
<body>
<n:navbar/>
<div class="admin">
    <h1 style="justify-content: center; align-self: center; display: flex">Admin Page</h1><br>
    <h3 class="idNullError"></h3>
    <button type="button" class="btn btn-success add-admin" data-bs-toggle="modal" data-bs-target="#newMovie">Add
        movie
    </button>
    <div class="modal" id="newMovie" tabindex="-1" aria-labelledby="modalContainer" aria-hidden="true">
        <div class="modal-dialog modal-lg modal-dialog-centered">
            <div class="modal-content">
                <div class="modal-header">
                    <h4 id="title"> Add New Movie </h4>
                    <button type="button" class="btn-close closeUp" data-bs-dismiss="modal" aria-label="Close"></button>
                </div>
                <h5 class="greenSuccess"></h5>
                <h5 class="updateErrorMessage"></h5>
                <form:form class="addNewMovieForm">
                    <div class="modal-body">
                        <div class="container-fluid">
                            <label for="movieName1">Movie name:</label>
                            <input type="text" value="" name="name" class="form-control" id="movieName1"
                                   placeholder="Enter movie name" required>
                        </div>
                        <div class="container-fluid">
                            <label for="movieDescription1">Movie description:</label>
                            <input type="text" value="" name="description" class="form-control" id="movieDescription1"
                                   placeholder="Enter movie description" required>
                        </div>
                        <div class="container-fluid">
                            <label for="movieDuration1">Duration:</label>
                            <input type="number" value="" min="0" name="minutes" class="form-control"
                                   id="movieDuration1" placeholder="Enter movie duration in minutes" required>
                        </div>
                        <div class="container-fluid">
                            <label for="moviePicture1">Movie's picture:</label>
                            <input type="text" value="" name="picture" class="form-control" id="moviePicture1"
                                   placeholder="Enter the movie's picture link" required>
                        </div>
                        <div class="container-fluid">
                            <label for="movieYear1">Movie's year:</label>
                            <input type="number" value="" min="1878" name="year" class="form-control" id="movieYear1"
                                   placeholder="Enter the year the movie was released" required>
                        </div>
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-secondary closeDown" data-bs-dismiss="modal">Close</button>
                        <button type="submit" class="btn btn-primary">Add movie</button>
                    </div>
                </form:form>
            </div>
        </div>
    </div>
    <br>
    <br>
    <select class="form-select" id="chooseCategory" aria-label="Default select example">
        <option selected>Choose category</option>
        <option value="All">All movies</option>
        <c:forEach items="${adminCategories}" var="category">
            <option value="${category.name}">${category.name}</option>
        </c:forEach>

    </select>
    <br>
    <br>
    <c:choose>
        <c:when test="${adminError!=null}">
            <br> <br>  <h4 style="display: flex;justify-content: center">${adminError}</h4>
        </c:when>
        <c:otherwise>
            <table id="tableData" class="table table-bordered table-striped">
                <thead>
                <tr>
                    <th hidden>
                        Id
                    </th>
                    <th>
                        Picture
                    </th>
                    <th>
                        Name
                    </th>
                    <th>
                        Description
                    </th>
                    <th>
                        Duration
                    </th>
                    <th>
                        Year
                    </th>
                    <th>
                        Categories
                    </th>
                    <th>
                        Actions
                    </th>
                </tr>
                </thead>
                <tbody>
                <c:forEach items="${adminMovies}" var="item">
                    <c:set var="movie" value="${item.key}"/>
                    <c:set var="categories" value="${item.value}"/>
                    <tr id="items">
                        <td hidden>
                            <p> ${movie.id}</p>
                        </td>
                        <td>
                            <img src="${movie.picture}" width="50" height="80" alt=""/>
                        </td>
                        <td>
                            <p> ${movie.name}</p>
                        </td>
                        <td>
                            <p> ${movie.description}</p>
                        </td>
                        <td>
                            <p> ${movie.minutes} minutes</p>
                        </td>

                        <td>
                            <p>${movie.year}</p>
                        </td>
                        <td>
                            <c:forEach items="${categories}" var="category">
                                <p>${category.name}</p> <br>
                            </c:forEach>
                        </td>
                        <td>
                            <button type="button" class="btn btn-primary btn-admin" value="${movie.id}"
                                    data-bs-toggle="modal" data-bs-target="#movie${movie.id}">Update
                            </button>
                            <br>
                            <div class="modal" id="movie${movie.id}" tabindex="-1" aria-labelledby="modalContainer"
                                 aria-hidden="true">
                                <div class="modal-dialog modal-lg modal-dialog-centered">
                                    <div class="modal-content">
                                        <div class="modal-header">
                                            <h4 id="movieTitle"> Update ${movie.name} </h4>
                                            <button type="button" class="btn-close closeUp" data-bs-dismiss="modal"
                                                    aria-label="Close"></button>
                                        </div>
                                        <h5 class="displayedMessage"></h5>
                                        <form:form class="updateMovieForm">
                                            <div class="modal-body">
                                                <div class="container-fluid">
                                                    <input type="number" min="0" value="${movie.id}" name="id"
                                                           class="form-control" hidden>
                                                </div>
                                                <div class="container-fluid">
                                                    <label for="movieName">Movie name:</label>
                                                    <input type="text" value="${movie.name}" name="name"
                                                           class="form-control" id="movieName"
                                                           placeholder="Enter movie name" required>
                                                </div>
                                                <div class="container-fluid">
                                                    <label for="movieDescription">Movie description:</label>
                                                    <input type="text" value="${movie.description}" name="description"
                                                           class="form-control" id="movieDescription"
                                                           placeholder="Enter movie description" required>
                                                </div>
                                                <div class="container-fluid">
                                                    <label for="movieDuration">Duration:</label>
                                                    <input type="number" value="${movie.minutes}" min="0" name="minutes"
                                                           class="form-control" id="movieDuration"
                                                           placeholder="Enter movie duration in minutes" required>
                                                </div>
                                                <div class="container-fluid">
                                                    <label for="moviePicture">Movie's picture:</label>
                                                    <input type="text" value="${movie.picture}" name="picture"
                                                           class="form-control" id="moviePicture"
                                                           placeholder="Enter the movie's picture link" required>
                                                </div>
                                                <div class="container-fluid">
                                                    <label for="movieYear">Movie's year:</label>
                                                    <input type="number" value="${movie.year}" min="1878" name="year"
                                                           class="form-control" id="movieYear"
                                                           placeholder="Enter the year the movie was released" required>
                                                </div>
                                            </div>
                                            <div class="modal-footer">
                                                <button type="button" class="btn btn-secondary closeDown"
                                                        data-bs-dismiss="modal">Close
                                                </button>
                                                <button type="submit" class="btn btn-primary">Save changes</button>
                                            </div>
                                        </form:form>
                                    </div>
                                </div>
                            </div>
                            <br>
                            <button type="button" class="btn btn-danger delete btn-admin" movie="${movie.id}">Delete
                            </button>
                            <br>
                            <br>
                            <button type="button" class="btn btn-info addCategories" value="${movie.id}"
                                    data-bs-toggle="modal" data-bs-target="#modal${movie.id}">Add categories
                            </button>
                            <br>
                            <div class="modal" id="modal${movie.id}" tabindex="-1" aria-labelledby="modalContainer"
                                 aria-hidden="true">
                                <div class="modal-dialog modal-lg modal-dialog-centered">
                                    <div class="modal-content">
                                        <div class="modal-header">
                                            <h4 id="addCateg"> Add categories to ${movie.name} </h4>
                                            <button type="button" class="btn-close closeUp" data-bs-dismiss="modal"
                                                    aria-label="Close"></button>
                                        </div>
                                        <h5 class="displayedMessage"></h5>
                                        <form:form class="movieCategories">
                                            <div class="modal-body">
                                                <div class="container-fluid">
                                                    <div class="dropdown">
                                                        <button class="btn btn-secondary dropdown-toggle" type="button"
                                                                id="dropdownMenuButton1" data-bs-toggle="dropdown"
                                                                aria-expanded="false">
                                                            Categories
                                                        </button>
                                                        <ul class="dropdown-menu" aria-labelledby="dropdownMenuButton1">
                                                            <li><a class="dropdown-item">
                                                                <c:forEach items="${adminCategories}" var="category">
                                                                    <c:set value="false" var="isCategoryOfMovie"/>
                                                                    <c:forEach items="${categories}" var="categ">
                                                                        <c:if test="${categ.name.equals(category.name)}">
                                                                            <c:set value="true"
                                                                                   var="isCategoryOfMovie"/>
                                                                        </c:if>
                                                                    </c:forEach>
                                                                    <c:choose>
                                                                        <c:when test="${isCategoryOfMovie==true}">
                                                                            <div class="form-check">
                                                                                <input type="checkbox"
                                                                                       class="form-check-input"
                                                                                       id="check${movie.id}"
                                                                                       name="chosenCategories"
                                                                                       value="${category.name}" checked>
                                                                                <label class="form-check-label"
                                                                                       for="check${movie.id}">${category.name}</label>
                                                                            </div>
                                                                        </c:when>
                                                                        <c:otherwise>
                                                                            <div class="form-check">
                                                                                <input type="checkbox"
                                                                                       class="form-check-input"
                                                                                       id="check${movie.id}"
                                                                                       name="chosenCategories"
                                                                                       value="${category.name}">
                                                                                <label class="form-check-label"
                                                                                       for="check${movie.id}">${category.name}</label>
                                                                            </div>
                                                                        </c:otherwise>
                                                                    </c:choose>
                                                                </c:forEach>
                                                            </a></li>
                                                        </ul>
                                                    </div>
                                                </div>
                                                <div class="container-fluid">
                                                    <label for="addNewCategory${movie.id}">Add new category:</label>
                                                    <input type="text" class="form-control textCategory"
                                                           id="addNewCategory${movie.id}"
                                                           placeholder="Enter a new category:">
                                                </div>
                                            </div>
                                            <div class="modal-footer">
                                                <button type="button" class="btn btn-secondary closeDown"
                                                        data-bs-dismiss="modal">Close
                                                </button>
                                                <button type="submit" value="${movie.id}" name="id"
                                                        class="btn btn-primary saveCategories">Save changes
                                                </button>
                                            </div>
                                        </form:form>
                                    </div>
                                </div>
                            </div>
                        </td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>


        </c:otherwise>
    </c:choose>

</div>
<n:footer/>
<script type="text/javascript" src="http://ajax.googleapis.com/ajax/libs/jquery/1.11.2/jquery.min.js"></script>
<script src="http://ajax.googleapis.com/ajax/libs/jqueryui/1.11.2/jquery-ui.min.js"></script>
<script src="../../js/admin.js"></script>
<script src="../../js/updateMovie.js"></script>
<script src="../../js/addMovie.js"></script>


</body>


</html>
