<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ page contentType="text/html; charset=UTF-8"%>
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
			<a class="navbar-brand" href="computers"> <spring:message
					code="app.nav" />
			</a>
			<!-- Dropdown for selecting language -->
			<div class="dropdown pull-right btn-group btn-group-sm">
				<button class="btn btn-danger dropdown-toggle" type="button"
					id="dropdownMenuButton" data-toggle="dropdown" aria-haspopup="true"
					aria-expanded="false">
					<spring:message code="app.lang.title" />
				</button>
				<div class="dropdown-menu" aria-labelledby="dropdownMenuButton">
					<a class="dropdown-item btn btn-default" href="?lang=en"><spring:message
							code="app.lang.english" /></a> <a
						class="dropdown-item btn btn-default" href="?lang=fr"><spring:message
							code="app.lang.french" /></a>
				</div>
			</div>
		</div>
	</header>

	<section id="main">
		<div class="container">
			<div class="row">
				<div class="col-xs-8 col-xs-offset-2 box">
					<h1>
						<spring:message code="form.login.title" />
					</h1>
					<form:form action="/login" modelAttribute="user" method="POST"
						enctype="utf8">
						<fieldset>
							<legend>
								<spring:message code="form.login.subtitle" />
							</legend>
							<div class="form-group">
								<form:label path="userName">
									<spring:message code="field.user.UserName"></spring:message>
								</form:label>
								<form:input type="text" class="form-control" path="userName"
									required="required" />
							</div>
							<div class="form-group">
								<form:label path="password">
									<spring:message code="field.user.password"></spring:message>
								</form:label>
								<form:input type="password" class="form-control" path="password"
									required="required" />
							</div>
						</fieldset>
						<div class="actions pull-right">
							<input type="submit"
								value="<spring:message code="form.button.login" />"
								class="btn btn-primary"> <a
								href="/ComputerDatabase/computers" class="btn btn-default"><spring:message
									code="form.button.cancel" /></a>
						</div>
					</form:form>
				</div>
			</div>
		</div>
	</section>
	<script src="<%=request.getContextPath()%>/static/js/jquery.min.js"></script>
	<script src="<%=request.getContextPath()%>/static/js/bootstrap.min.js"></script>
</body>
</html>