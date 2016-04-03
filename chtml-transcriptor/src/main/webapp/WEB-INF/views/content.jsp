<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<html xmlns:th="http://www.thymeleaf.org">
<body>
	<div class="section">
		<div class="text-center">
			<h1>Composite HTML Converter</h1>
			<h4>CHTML is a new technology able to bring the composite pattern to static web resources.
				CHTML makes the development of static HTML websites easy and simple.</h4>
		</div>
	</div>
	<br />
	<div class="section">
		<div class="row">
			<div class="col-sm-6">
				<h5>Upload a file</h5>
				<form enctype="multipart/form-data"
					action="<c:url value="/uploadFile"/>?${_csrf.parameterName}=${_csrf.token} " method="POST"
					style="">
					<label for="file">CHTML file(s) to upload</label>
					<input type="file" name="file" id="file" />
					<input type="submit" value="Upload" />
				</form>
			</div>
		</div>
	</div>
	<div class="section">
		<table class="table" id="file-table">
			<thead>
				<tr>
					<td>File Name</td>
					<td></td>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${fileList}" var="files" varStatus="file">
					<tr>
						<c:set var="fileName" value="${files}" />

						<c:set var="beginningIndex" value="${fn:indexOf(fileName, 'chtml\') + 6} " />
						<fmt:parseNumber var="beginningIndexInt" integerOnly="true" type="number"
							value="${beginningIndex}" />

						<c:set var="endingIndex" value="${fn:indexOf(fileName, '.chtml') + 6}" />
						<fmt:parseNumber var="endingIndexInt" integerOnly="true" type="number" value="${endingIndex}" />

						<c:set var="fileNameCut" value="${fn:substring(fileName, beginningIndexInt, endingIndexInt)}" />
						<td><c:out value="${fileNameCut}" /></td>
						<td><button class="btn btn-danger">Delete</button></td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
	</div>
	<div class="section bordered">
		<div class="text-center padded">
			<a href="<c:url value="/compile"/>" class="btn btn-primary">Compile</a> <a
				href="<c:url value="/download"/>" class="btn btn-primary">Download</a> <a
				href="<c:url value="/delete"/>" class="btn btn-danger">Delete All</a>
		</div>
	</div>
</body>
</html>