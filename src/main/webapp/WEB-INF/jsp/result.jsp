<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<c:set var="data"
	value="${requestScope['fr.sparna.validator.ValidatorData']}" />
<c:set var="sessionData"
	value="${sessionScope['fr.sparna.validator.SessionData']}" />
<c:set var="compteur" scope="session" value="0" />
<fmt:setLocale
	value="${sessionScope['fr.sparna.validator.SessionData'].userLocale}" />
<fmt:setBundle basename="fr.sparna.validator.properties.Bundle"/>
<html>
<head>
<title>Validator</title>
<meta http-equiv="content-type" content="text/html; charset=UTF-8">
<link href="resources/jasny-bootstrap/css/jasny-bootstrap.min.css"
	rel="stylesheet" />
<link href="resources/bootstrap/css/bootstrap.min.css" rel="stylesheet" />
<link rel="stylesheet" href="resources/css/style.css" type="text/css">
<link rel="stylesheet" href="resources/css/print.css" type="text/css"
	media="print">
<link rel="stylesheet" href="resources/css/print-preview.css"
	type="text/css" media="screen">
<script src="resources/js/jquery-1.11.3.js"></script>
<script src="resources/bootstrap/js/bootstrap.min.js"></script>
<script src="http://code.jquery.com/jquery-1.9.0.js"></script>
<script src="http://code.jquery.com/jquery-migrate-1.0.0.js"></script>
<script src="resources/js/jquery.print-preview.js"
	type="text/javascript"></script>
<style type="text/css">
</style>
<script type="text/javascript">

$(function() {
    
	$('#print').prepend('<a href="" class="print-preview "><span class=\"glyphicon glyphicon-print\" aria-hidden="true"></span>&nbsp;<fmt:message key="print"/></a>');
    $('a.print-preview').printPreview();
    
});
</script>
</head>
<body id="resultpage">
	<jsp:include page="header.jsp" />
	<div class="navigation hidden-print">
		<ul id="sidebar" class="list-group">
			<c:forEach items="${data.errorList}" var="error">
				<c:set var="compteur" scope="session"
					value="${compteur+error.number}" />
				<li class="list-group-item list-group-item-${error.cssClass}"><a href="#${error.id}"><font
						size="2">${error.ruleName} : ${error.state}</font></a></li>
			</c:forEach>
		</ul>
	</div>
	<div class="container" id="result-container">
		<div  id="exe" class="panel panel-default">
		  <div class="panel-heading">
		  	<fmt:message key="file.validate" var="filevalidated" >
			   <fmt:param value="${data.fileName}"/>
			   <fmt:param value="${data.issueDate}"/>
			</fmt:message>
			<p>${filevalidated}</p>
		  </div>
		  <div class="panel-body">
		  	
		  	<fmt:message key="statistic" var="value" >
			   <fmt:param value="${data.rulesNumber}"/>
			   <fmt:param value="${data.executionTimeString}"/>
			   <fmt:param value="${data.rulesFail}"/>
			</fmt:message>
			<fmt:message key="validate.data" var="validate" >
			   <fmt:param value="${data.allconcepts}"/>
			   <fmt:param value="${data.allcollections}"/>
			   <fmt:param value="${data.allconceptschemes}"/>
			</fmt:message>
		  	<p>${value}</p>
		  	<p>${validate}</p>
		  	<p class="hidden-print"><span id="print"></span></p>
		  	</div>
		</div>
		<table id="table" data-show-print="true"
			class="table table-hover table-expandable table-striped">
			<thead>
				<tr>
					<th><fmt:message key="entete.table.rule" /></th>
					<th><fmt:message key="entete.table.description"/></th>
					<th><fmt:message key="entete.table.state" /></th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${data.errorList}" var="error">
					<tr id="line" class="${error.cssClass}">
						<td><a href="${error.weblink}" target="_blank" id="${error.id}">${error.id} - ${error.ruleName}</a></td>
						<td>${error.description}</td>
						<td><h6>${error.state}</h6></td>
					</tr>
					<c:if test="${!empty error.errorList}">
						<tr id="content" class="default ">
							<td colspan="3">
								<div class="table-outer">
									<c:forEach items="${error.errorList}" var="list">
										<em>${list}</em>
										<br>
									</c:forEach>
								</div>
								<div>
									<c:if test="${error.number>4}">
										<a class="toggleTableLink" href="#"><i>show/hide all
												${error.number} results</i></a>
									</c:if>
								</div>
							</td>
						</tr>
					</c:if>
	
				</c:forEach>
			</tbody>
		</table>
		<br>
	</div>
	<jsp:include page="footer.jsp" />
	<script type="text/javascript">
    $(document).ready(function() {
    	// add the toggle link behavior
        $( ".toggleTableLink" ).click(function(event) {
    		$(this).parent().prev(".table-outer").toggleClass("open");
    		return false;
        });
    });
    </script>
</body>
</html>