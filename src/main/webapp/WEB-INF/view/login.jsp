<%--
  Created by IntelliJ IDEA.
  User: madu0805
  Date: 7/7/2022
  Time: 1:32 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html>
<html>
<%@include file="/WEB-INF/tags/head.tag"%>
<body>
<n:navbar/>
<div class="register-content">
    <form:form method="post" action="/login">
        <div class="form-content login">
            <h1 class="heading">Login</h1>
            <div class="input">
                <label for="email">Email:</label>
                <input type="email" pattern="[a-z0-9._%+-]+@[a-z0-9.-]+\.[a-z]{2,4}$" class="form-control" id="email" placeholder="Enter email" name="email" required>
            </div>
            <div class="input">
                <label for="password">Password:</label>
                <input type="password" pattern="^(?=.*[A-Za-z])(?=.*\d)[A-Za-z\d]{6,}$" class="form-control" id="password" placeholder="Enter password" name="password" required>
            </div>
            <button class="register-btn" type="submit">Login</button>
        </div>
    </form:form>
</div>
<n:footer/>
</body>
</html>
