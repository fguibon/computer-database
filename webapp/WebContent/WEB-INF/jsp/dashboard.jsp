<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ page contentType="text/html; charset=UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<title><spring:message code="app.title" /></title>
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	<meta charset="utf-8">
	<!-- Bootstrap -->
	<link href="<%=request.getContextPath()%>/static/css/bootstrap.min.css" rel="stylesheet" media="screen">
	<link href="<%=request.getContextPath()%>/static/css/font-awesome.css" rel="stylesheet" media="screen">
	<link href="<%=request.getContextPath()%>/static/css/main.css" rel="stylesheet" media="screen">
</head>
<body>
	<header class="navbar navbar-inverse navbar-fixed-top">
		<div class="container">
			<a class="navbar-brand" href="computers"> <spring:message code="app.nav" /> </a>
			<!-- Dropdown for selecting language -->
			<div class="dropdown pull-right btn-group btn-group-sm">
				<button class="btn btn-danger dropdown-toggle" type="button"
					id="dropdownMenuButton" data-toggle="dropdown" aria-haspopup="true"
					aria-expanded="false">
					<spring:message code="app.lang.title" />
				</button>
				<div class="dropdown-menu" aria-labelledby="dropdownMenuButton">
					<a class="dropdown-item btn btn-default" href="?lang=en"><spring:message
							code="app.lang.english" /></a> <a class="dropdown-item btn btn-default"
						href="?lang=fr"><spring:message code="app.lang.french" /></a>
				</div>
			</div>
		</div>
		
	</header>
	<section id="main">
		<div class="container">
			<h1 id="homeTitle">${number} <spring:message code="app.homeTitle" /></h1>
			
			<div id="actions" class="form-horizontal">
				<div class="pull-left">
					<form id="searchForm" action="computers" method="GET"
						class="form-inline">
						<input type="search" id="searchbox" name="filter"
							class="form-control" placeholder="<spring:message code="filter.placeholder" />"
							value="${sorting.filter}" /> <input type="submit"
							id="searchsubmit" value="<spring:message code="filter.button" />" 
							class="btn btn-primary" />
					</form>
				</div>
				<div class="pull-right">
					<a class="btn btn-success" id="addComputer" href="computers/add">
					<spring:message code="dashboard.button.add" /> 
					</a> <a class="btn btn-default" id="editComputer" href="#"
						onclick="$.fn.toggleEditMode();"><spring:message code="dashboard.button.edit" /></a>
				</div>
			</div>
		</div>

		<form id="deleteForm" action="computers/delete" method="POST">
			<input type="hidden" name="selection" value="">
		</form>

		<div class="container" style="margin-top: 10px;">
			<table id="table" class="table table-striped table-bordered">
				<thead>
					<tr>
						<!-- Variable declarations for passing labels as parameters -->
						<!-- Table header for Computer Name -->

						<th class="editMode" style="width: 60px; height: 22px;"><input
							type="checkbox" id="selectall" /> <span
							style="vertical-align: top;"> - <a href="#"
								id="deleteSelected" onclick="$.fn.deleteSelected();"> <em
									class="fa fa-trash-o fa-lg"></em>
							</a>
						</span></th>
						<th class="th-sm"><spring:message code="field.name" /><a
							title="<spring:message code="field.orderby" /> <spring:message code="field.name" /> <spring:message code="field.asc" />"
							<c:url value="/computers" var="url">
 								<c:param name="field" value="name"/>
 								<c:param name="order" value="asc"/>
							</c:url>
							href="${url}"> <em class="fa fa-arrow-down"></em></a> <a
							title="<spring:message code="field.orderby" /> <spring:message code="field.name" /> <spring:message code="field.desc" />"
							<c:url value="/computers" var="url">
 								<c:param name="field" value="name"/>
 								<c:param name="order" value="desc"/>
							</c:url>
							href="${url}"> <em class="fa fa-arrow-up"></em></a>
						</th>

						<th class="th-sm"><spring:message code="field.intro" /> <a
							title="<spring:message code="field.orderby" /> <spring:message code="field.intro" /> <spring:message code="field.asc" />"
							<c:url value="/computers" var="url">
 								<c:param name="field" value="intro"/>
 								<c:param name="order" value="asc"/>
							</c:url>
							href="${url}"><em class="fa fa-arrow-down"></em></a> <a
							title="<spring:message code="field.orderby" /> <spring:message code="field.intro" /> <spring:message code="field.desc" />"
							<c:url value="/computers" var="url">
 								<c:param name="field" value="intro"/>
 								<c:param name="order" value="desc"/>
							</c:url>
							href="${url}"> <em class="fa fa-arrow-up"></em></a>
						</th>

						<th class="th-sm"><spring:message code="field.disco" /><a
							title="<spring:message code="field.orderby" /> <spring:message code="field.disco" /> <spring:message code="field.asc" />"
							<c:url value="/computers" var="url">
 								<c:param name="field" value="disco"/>
 								<c:param name="order" value="asc"/>
							</c:url>
							href="${url}"> <em class="fa fa-arrow-down"></em></a> <a
							title="<spring:message code="field.orderby" /> <spring:message code="field.disco" /> <spring:message code="field.desc" />"
							<c:url value="/computers" var="url">
 								<c:param name="field" value="disco"/>
 								<c:param name="order" value="desc"/>
							</c:url>
							href="${url}"> <em class="fa fa-arrow-up"></em></a>
						</th>

						<th class="th-sm"><spring:message code="field.company" /><a
							title="<spring:message code="field.orderby" /> <spring:message code="field.company" /> <spring:message code="field.asc" />"
							<c:url value="/computers" var="url">
 								<c:param name="field" value="company"/>
 								<c:param name="order" value="asc"/>
							</c:url>
							href="${url}"> <em class="fa fa-arrow-down"></em></a> <a
							title="<spring:message code="field.orderby" /> <spring:message code="field.company" /> <spring:message code="field.desc" />"
							<c:url value="/computers" var="url">
 								<c:param name="field" value="company"/>
 								<c:param name="order" value="desc"/>
							</c:url>
							href="${url}"> <em class="fa fa-arrow-up"></em>
						</a>
						</th>
					</tr>
				</thead>
				<!-- Browse attribute computers -->
				<tbody id="results">
					<c:forEach var="computer" items="${computers}">
						<tr>
							<td class="editMode"><input type="checkbox" name="cb"
								class="cb" value="${computer.getId()}"></td>
							<td><a href="computers/edit?id=${computer.getId()}"
								onclick="">${computer.getName()}</a></td>
							<td>${computer.getIntroduced()}</td>
							<td>${computer.getDiscontinued()}</td>
							<td>${computer.getCompanyName()}</td>
						</tr>
					</c:forEach>

				</tbody>
			</table>
		</div>
	</section>

	<footer class="navbar-fixed-bottom">
		<div class="container text-center">
			

			<ul class="pagination">
				<li><a
					<c:url value="/computers" var="url">
						<c:param name="page" value="${sorting.page - 1}"/>
					</c:url>
					href="${url}" aria-label="Previous"> <span aria-hidden="true">&laquo;</span>
				</a></li>
				<c:forEach var="currentPage" items="${pageList}">
					<li><a
						<c:url value="/computers" var="url">
								<c:param name="page" value="${currentPage}"/>
							</c:url>
						href="${url}">${currentPage}</a></li>
				</c:forEach>
				<li><a
					<c:url value="/computers" var="url">
						<c:param name="page" value="${sorting.page + 1}"/>
					</c:url>
					href="${url}" aria-label="Next"> <span aria-hidden="true">&raquo;</span>
				</a></li>
			</ul>


			<div class="btn-group btn-group-sm pull-right" role="group">
				<a class="btn btn-default"
					<c:url value="/computers" var="url">
						<c:param name="limit" value="10"/>
					</c:url>
					href="${url}">10</a> <a class="btn btn-default"
					<c:url value="/computers" var="url">
						<c:param name="limit" value="50"/>
					</c:url>
					href="${url}">50</a> <a class="btn btn-default"
					<c:url value="/computers" var="url">
						<c:param name="limit" value="100"/>
					</c:url>
					href="${url}">100</a>
			</div>
		</div>

	</footer>
	<script type="text/javascript">
		var edit = "<spring:message code="string.js.edit"/>";
		var view = "<spring:message code="string.js.view"/>";
		var alertMsg = "<spring:message code="string.js.alertMsg"/>";
	</script>
	<script src="<%=request.getContextPath()%>/static/js/jquery.min.js"></script>
	<script src="<%=request.getContextPath()%>/static/js/bootstrap.min.js"></script>
	<script src="<%=request.getContextPath()%>/static/js/dashboard.js"></script>

</body>
</html>