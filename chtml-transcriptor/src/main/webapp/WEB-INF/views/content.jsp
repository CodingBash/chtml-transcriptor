<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<html xmlns:th="http://www.thymeleaf.org">
<body>
	<div class="text-center">
		<h1>Composite HTML Converter</h1>
		<h4>CHTML is a new technology able to bring the composite pattern to static web resources.
			CHTML makes the development of static HTML websites easy and simple.</h4>
	</div>
	<form enctype="multipart/form-data"
		action="<c:url value="/uploadFile"/>?${_csrf.parameterName}=${_csrf.token} " method="POST">

		<label for="file">CHTML File(s) to upload</label>
		<input type="file" name="file" id="file" />
	</form>
	<table class="table" id="file-table">
		<thead>
			<tr>
				<td>File Name</td>
				<td>File Size</td>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${fileList}" var="files" varStatus="file">
				<tr>
					<c:out value="${file.name}" />
				</tr>
				<tr>
					<c:out value="${file.size}" />
				</tr>
			</c:forEach>
		</tbody>
	</table>
	<a href="<c:url value="/compile"/>" class="btn btn-primary">Compile</a>
	<a href="<c:url value="/download"/>" class="btn btn-primary">Download</a>
	<div>
		<ul>
			<c:forEach items="${files}" var="files" varStatus="file">
				<li><c:out value="${file}"></c:out></li>
			</c:forEach>
		</ul>
	</div>


	<%--
	<div th:if="${message}">
		<h2 th:text="${message}" />
	</div>

	<div>
		<form method="POST" enctype="multipart/form-data" action="/upload">
			<table>
				<tr>
					<td>File to upload:</td>
					<td><input type="file" name="file" /></td>
				</tr>
				<tr>
					<td>Name:</td>
					<td><input type="text" name="name" /></td>
				</tr>
				<tr>
					<td></td>
					<td><input type="submit" value="Upload" /></td>
				</tr>
			</table>
		</form>
	</div>

	<div>
		<ul>
			<li th:each="file : ${files}" th:text="${file}"></li>
		</ul>
	</div>
	--%>

</body>
</html>