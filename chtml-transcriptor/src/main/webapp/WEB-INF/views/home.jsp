<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page session="false"%>
<html>
<head>
<title>CHTML TO HTML CONVERTER</title>
<link rel="stylesheet" type="text/css"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css" />
<link rel="stylesheet" href="https://cdn.datatables.net/1.10.11/css/jquery.dataTables.min.css">

<style>
.navbar {
	background-color: #2c3e50;
	border-radius: 0;
	color: #ecf0f1;
	border: 0;
}

.section {
	margin-top: 5px;
	margin-bottom: 5px;
	margin-left: 10px;
	margin-right: 10px;
}

.bordered {
	border: 1px solid rgba(0, 0, 0, 0.3);
}

.padded {
	padding-top: 5px;
	padding-bottom: 5px;
}
</style>
</head>
<body>
	<jsp:include page="header.jsp" />
	<jsp:include page="content.jsp" />
	<jsp:include page="footer.jsp" />

	<script src="https://code.jquery.com/jquery-2.2.2.min.js"
		integrity="sha256-36cp2Co+/62rEAAYHLmRCPIych47CvdM+uTBJwSzWjI=" crossorigin="anonymous"
		type="text/javascript"></script>
	<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/js/bootstrap.min.js"
		type="text/javascript"></script>
	<script src="//cdn.datatables.net/1.10.11/js/jquery.dataTables.min.js"></script>
	<script>
		$("#file-table").DataTable();
	</script>
</body>
</html>
