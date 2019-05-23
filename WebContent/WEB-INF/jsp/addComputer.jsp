<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
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
			<a class="navbar-brand" href="dashboard"> Application - Computer
				Database </a>
		</div>
	</header>

	<section id="main">
		<div class="container">
			<div class="row">
				<div class="col-xs-8 col-xs-offset-2 box">
					<h1>Add Computer</h1>
					<form:form action="add-computer" method="POST" modelAttribute="computer">
						<fieldset>
							<legend>Computer Data</legend>
							<div class="form-group">
								<form:label for="computerName" path="name">Computer name</form:label> 
								<form:input
									type="text" class="form-control" id="name"
									path="name" placeholder="Computer name"
									pattern="^[\w'\-,.0-9][^_!¡?÷?¿/\\+=@#$%ˆ&*(){}|~:[\]]{2,}$"
									required="required" />
							</div>
							<div class="form-group">
								<form:label for="introduced" path="introduced">Introduced date</form:label> 
								<form:input
									type="date" class="form-control" id="introduced"
									path="introduced" placeholder="Introduced date"
									min="1970-12-12"
									pattern="(0[1-9]|1[0-9]|2[0-9]|3[01]).(0[1-9]|1[012]).[0-9]{4}"/>
							</div>
							<div class="form-group">
								<form:label for="discontinued" path="discontinued">Discontinued date</form:label> 
								<form:input
									type="date" class="form-control" id="discontinued"
									path="discontinued" placeholder="Discontinued date"
									min="1970-12-12"
									pattern="(0[1-9]|1[0-9]|2[0-9]|3[01]).(0[1-9]|1[012]).[0-9]{4}"/>
							</div>
							<div class="form-group">
								<form:label for="companyId" path="companyId">Company</form:label> 
								<form:select class="form-control" id="companyId" path="companyId">
									<option value="">None</option>
									<c:forEach items="${companies}" var="company">
										<option value="${company.id}">${company.name}</option>
									</c:forEach>
								</form:select>
							</div>
						</fieldset>
						<div class="actions pull-right">
							<input type="submit" value="Add" class="btn btn-primary">
							or <a href="dashboard" class="btn btn-default">Cancel</a>
						</div>
					</form:form>
				</div>
			</div>
		</div>
	</section>
	<script src="static/js/jquery.min.js"></script>
	<script src="static/js/bootstrap.min.js"></script>
	<script src="static/js/addComputer.js"></script>
</body>
</html>