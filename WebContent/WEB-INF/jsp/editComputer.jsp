<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page contentType="text/html; charset=UTF-8" %>
<!DOCTYPE html>
<html>
<head>
	<title>Computer Database</title>
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	<!-- Bootstrap -->
	<link href="static/css/bootstrap.min.css" rel="stylesheet" media="screen">
	<link href="static/css/font-awesome.css" rel="stylesheet" media="screen">
	<link href="static/css/main.css" rel="stylesheet" media="screen">
</head>
<body>
    <header class="navbar navbar-inverse navbar-fixed-top">
        <div class="container">
            <a class="navbar-brand" href="dashboard"> Application - Computer Database </a>
        </div>
    </header>
    <section id="main">
        <div class="container">
            <div class="row">
                <div class="col-xs-8 col-xs-offset-2 box">
                    <div class="label label-default pull-right">
                        id: ${computer.id}
                    </div>
                    <h1>Edit Computer</h1>

                    <form action="edit-computer" method="POST">
                        <input type="hidden" value="${computer.id}" id="id" name="id"/>
                        <fieldset>
                        	<legend>Computer Data</legend>
                            <div class="form-group">
                                <label for="computerName">Computer Name</label>
                                <input type="text" class="form-control"  id="name" 
                                name="name" placeholder="${computer.name}"
                                pattern="^[\w'\-,.0-9][^_!¡?÷?¿/\\+=@#$%ˆ&*(){}|~:[\]]{2,}$"
								value="${computer.name}" required>
                            </div>
                            <div class="form-group">
                                <label for="introduced">Introduced date</label>
                                <input type="date" class="form-control" id="introduced" 
                                name="introduced"  placeholder="${computer.introduced}"
                                min="1970-12-12" value="${computer.introduced}"
								pattern="(0[1-9]|1[0-9]|2[0-9]|3[01]).(0[1-9]|1[012]).[0-9]{4}">
                            </div>
                            <div class="form-group">
                                <label for="discontinued">Discontinued date</label>
                                <input type="date" class="form-control" id="discontinued" 
                                name="discontinued"  placeholder="${computer.discontinued}"
                                min="1970-12-12" value="${computer.discontinued}"
								pattern="(0[1-9]|1[0-9]|2[0-9]|3[01]).(0[1-9]|1[012]).[0-9]{4}">
                            </div>
                            <div class="form-group">
                                <label for="companyId">Company</label>
                                <select class="form-control" id="companyId" name="companyId">
									<option value="">None</option>
									<c:forEach items="${companies}" var="company">
										<option value="${company.id}" 
										${computer.companyId==company.id?"selected='selected'":""}>${company.name}</option>   
									</c:forEach>
								</select>
                            </div>            
                        </fieldset>
                        <div class="actions pull-right">
                            <input type="submit" value="Edit" class="btn btn-primary">
                            or
                            <a href="dashboard" class="btn btn-default">Cancel</a>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </section>
    <script src="static/js/jquery.min.js"></script>
	<script src="static/js/bootstrap.min.js"></script>
	<script src="static/js/editComputer.js"></script>
</body>
</html>