<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html; charset=UTF-8" session="false"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<!DOCTYPE html>
<html>
<head>
    <title><spring:message code="app.title" /></title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <!-- Bootstrap -->
    <link href="<%=request.getContextPath()%>/static/css/bootstrap.min.css" rel="stylesheet" media="screen">
    <link href="<%=request.getContextPath()%>/static/css/font-awesome.css" rel="stylesheet" media="screen">
    <link href="<%=request.getContextPath()%>/static/css/main.css" rel="stylesheet" media="screen">
</head>
<body>
    <header class="navbar navbar-inverse navbar-fixed-top">
        <div class="container">
            <a class="navbar-brand" href="/ComputerDatabase/computers"> <spring:message code="app.nav" /> </a>
        </div>
    </header>

    <section id="main">
        <div class="container">
            <div class="alert alert-danger">
                <spring:message code="${errorMsg}" />
                <br/>
            </div>
        </div>
    </section>

    <script src="<%=request.getContextPath()%>/static/js/jquery.min.js"></script>
    <script src="<%=request.getContextPath()%>/static/js/bootstrap.min.js"></script>
    <script src="<%=request.getContextPath()%>/static/js/dashboard.js"></script>

</body>
</html>