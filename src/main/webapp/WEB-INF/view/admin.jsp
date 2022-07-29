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

</head>
<body>
<n:navbar/>
<div class="admin">
<h1 style="justify-content: center; align-self: center; display: flex">Admin Page</h1><br>
<button type="button" class="btn btn-success add-admin">Add movie</button>
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
            <th>
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
                <td>
                       <p> ${movie.id}</p>
                </td>
                <td>
                    <img src="${movie.picture}" width="50" height="80" alt=""/>
                </td>
                <td>
                       <p> ${movie.name}</p>
                </td>
                <td>
                      <p>  ${movie.description}</p>
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
                    <button type="button" class="btn btn-primary btn-admin">Update</button>
                    <br>
                    <br>
                    <button type="button" class="btn btn-danger delete btn-admin" movie="${movie.id}">Delete</button>
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


</body>


</html>
