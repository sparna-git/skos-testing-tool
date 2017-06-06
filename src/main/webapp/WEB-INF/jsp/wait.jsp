<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>


<c:set var="data" value="${requestScope['fr.sparna.validator.ValidatorData']}" />
<c:set var="applicationData" value="${applicationScope.applicationData}" />
<c:set var="compteur" scope="session" value="0" />
<html>
<head>

<title>Validator</title>
<meta http-equiv="content-type" content="text/html; charset=UTF-8">
<link href="resources/bootstrap/css/bootstrap.min.css" rel="stylesheet" />
<link href="resources/jasny-bootstrap/css/jasny-bootstrap.min.css" rel="stylesheet" />
<link href="resources/css/style.css" rel="stylesheet" />
<script src="resources/js/jquery-1.11.3.js"></script>
<script src="resources/bootstrap/js/bootstrap.min.js"></script>
<script src="resources/jasny-bootstrap/js/jasny-bootstrap.min.js"></script>


</head>
<body style="">
<jsp:include page="header.jsp"/>
		<h5 id="TITLE"><em>Skos file validator</em></h5>
		<br>
		<br>
		<div class="jumbotron">
			<textarea name="log" id="log" rows="15" class="form-control" cols="30"></textarea>
		</div>
</body>
</html>