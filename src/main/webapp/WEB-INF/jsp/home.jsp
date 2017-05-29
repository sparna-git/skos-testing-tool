<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

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
		<h3>Skos file validator</h3>
		<form id="upload_form" action="home" method="post" name="formulaire" enctype="multipart/form-data" class="form-horizontal">
			<div class="jumbotron">
				<div class="fileinput fileinput-new input-group" data-provides="fileinput" id="file">
			  		<div class="form-control" data-trigger="fileinput">
			  			<i class="glyphicon glyphicon-file fileinput-exists"></i> 
			  			<span class="fileinput-filename"></span>
			  		</div>
				  <span class="input-group-addon btn btn-default btn-file"><span class="fileinput-new">Select file</span>
				  <span class="fileinput-exists">Change</span><input type="file" name="file" ></span>
				  <a href="#" class="input-group-addon btn btn-default fileinput-exists" data-dismiss="fileinput">Remove</a>
				</div><br>
				<button class="btn btn-info" type="submit">valider</button>
			</div>
		</form>
</body>
</html>