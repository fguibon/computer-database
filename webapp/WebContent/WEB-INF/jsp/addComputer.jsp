<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ page contentType="text/html; charset=UTF-8" %>
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
			<a class="navbar-brand" href="/ComputerDatabase/computers"> <spring:message code="app.nav" /></a>
		</div>
	</header>

	<section id="main">
		<div class="container">
			<div class="row">
				<div class="col-xs-8 col-xs-offset-2 box">
					<h1><spring:message code="form.add.title" /></h1>
					<form:form action="add" method="POST" modelAttribute="computer">
						<fieldset>
							<legend><spring:message code="form.subtitle" /></legend>
							<div class="form-group">
								<form:label for="computerName" path="name"><spring:message code="field.name" /></form:label> 
								<form:input
									type="text" class="form-control" id="name"
									path="name" placeholder="Computer name"
									pattern="^[\w'\-,.0-9][^_!¡?÷?¿/\\+=@#$%ˆ&*(){}|~:[\]]{2,}$"
									required="required" />
							</div>
							<div class="form-group">
								<form:label for="introduced" path="introduced"><spring:message code="field.intro" /></form:label> 
								<form:input
									type="date" class="form-control" id="introduced"
									path="introduced" placeholder="Introduced date"
									min="1970-12-12"
									pattern="(0[1-9]|1[0-9]|2[0-9]|3[01]).(0[1-9]|1[012]).[0-9]{4}"/>
							</div>
							<div class="form-group">
								<form:label for="discontinued" path="discontinued"><spring:message code="field.disco" /></form:label> 
								<form:input
									type="date" class="form-control" id="discontinued"
									path="discontinued" placeholder="Discontinued date"
									min="1970-12-12"
									pattern="(0[1-9]|1[0-9]|2[0-9]|3[01]).(0[1-9]|1[012]).[0-9]{4}"/>
							</div>
							<div class="form-group">
								<form:label for="companyId" path="companyId"><spring:message code="field.company" /></form:label> 
								<form:select class="form-control" id="companyId" path="companyId">
									<option value=""><spring:message code="field.none" /></option>
									<c:forEach items="${companies}" var="company">
										<option value="${company.id}">${company.name}</option>
									</c:forEach>
								</form:select>
							</div>
						</fieldset>
						<div class="actions pull-right">
							<input type="submit" value="<spring:message code="form.button.add" />" class="btn btn-primary">
							<a href="/ComputerDatabase/computers" class="btn btn-default"><spring:message code="form.button.cancel" /></a>
						</div>
					</form:form>
				</div>
			</div>
		</div>
	</section>
	<script src="<%=request.getContextPath()%>/static/js/jquery.min.js"></script>
	<script src="<%=request.getContextPath()%>/static/js/bootstrap.min.js"></script>
	<script src="<%=request.getContextPath()%>/static/js/addComputer.js"></script>
</body>
</html>