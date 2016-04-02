<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<html xmlns:th="http://www.thymeleaf.org">
<body>
	<!-- jstl implementation -->
	<c:if test="${message}">
		<h2>
			<c:out value="${message}"></c:out>
		</h2>
	</c:if>
	<h2>Upload your file. You will receive a download of the final HTML directory</h2>

	<form enctype="multipart/form-data"
		action="<c:url value="/uploadFile"/>?${_csrf.parameterName}=${_csrf.token} " method="POST">
		<table class="table" id="file-table">
			<thead>
				<tr>
					<td>Label</td>
					<td>Input</td>
				</tr>
			</thead>
			<tbody>
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
			</tbody>
		</table>
	</form>
	<a href="<c:url value="/compile"/>" class="btn btn-primary">Compile</a>
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