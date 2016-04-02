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
	
	<form:form commandName="">
		<table class="table">
			<thead>
				<tr>
					<td></td>
					<td></td>
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
	</form:form>

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