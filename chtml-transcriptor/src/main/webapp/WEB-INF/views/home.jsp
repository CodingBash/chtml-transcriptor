<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page session="false"%>
<html>
<head>
<title>CHTML TRANSCRIPTOR</title>
<link rel="stylesheet" type="text/css"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css" />
<link rel="stylesheet" type="text/css" href="../styles/common.css" />
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
</body>
</html>
