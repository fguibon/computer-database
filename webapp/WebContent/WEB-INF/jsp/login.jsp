<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html>
<html>
<head>
<title><spring:message code="app.title" /></title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta charset="utf-8">
<!-- Bootstrap -->
<link href="<%=request.getContextPath()%>/static/css/bootstrap.min.css"
	rel="stylesheet" media="screen">
<link href="<%=request.getContextPath()%>/static/css/font-awesome.css"
	rel="stylesheet" media="screen">
<link href="<%=request.getContextPath()%>/static/css/main.css"
	rel="stylesheet" media="screen">
</head>
<body>
	<header class="navbar navbar-inverse navbar-fixed-top">
		<div class="container">
			<a class="navbar-brand" href="/ComputerDatabase/computers"> <spring:message
					code="app.nav" /></a>
		</div>
	</header>
	<section id="main">
		<div class="container">
			<div class="row">
				<div class="col-xs-8 col-xs-offset-2 box">
					<h1>
						<spring:message code="form.login.title" />
					</h1>
					<form:form action="/login" modelAttribute="user" method="POST" enctype="utf8">
						<div class="form-group">
							<form:label path="userName">
								<spring:message code="field.user.UserName"></spring:message>
							</form:label> 
							<form:input
								type="text" class="form-control"
								path="userName" required="required"/>
						</div>
						<div>
							<form:label path="password">
								<spring:message code="field.user.password"></spring:message>
							</form:label> 
							<form:input
								type="password" class="form-control" 
								path="password" required="required" />
						</div>
						<div class="actions pull-right">
							<input type="submit" value="<spring:message code="form.button.login" />" class="btn btn-primary">
							or <a href="/ComputerDatabase/computers" class="btn btn-default"><spring:message code="form.button.cancel" /></a>
						</div>
					</form:form>
				</div>
			</div>
		</div>
	</section>
</body>
</html>