<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<%@include file="/WEB-INF/tags/head.tag" %>
<body>
<n:navbar/>
<div class="content search">
    <c:choose>
        <c:when test="${count == 0}">
            <div class="search-text-info">
                 <h2>NO RESULTS WERE FOUND:</h2>
                 <h3>Your search for "${searchTerm}" returned ${count} results.</h3>
            </div>
    </c:when>
        <c:otherwise >
            <div class="search-text-info">
                <c:if test="${count == 1}">
                    <h2>Search results for "${searchTerm}" - ${count} movie</h2>
                </c:if>
                <c:if test="${count > 1}">
                    <h2>Search results for "${searchTerm}" - ${count} movies</h2>
                </c:if>
            </div>
             <n:movie/>
        </c:otherwise>
    </c:choose>
</div>
<n:footer/>
</body>
</html>