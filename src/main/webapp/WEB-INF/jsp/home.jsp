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
<c:set var="applicationData" value="${applicationScope.applicationData}" />
<c:set var="compteur" scope="session" value="0" />
<fmt:setLocale
	value="${sessionScope['fr.sparna.validator.SessionData'].userLocale}" />
<fmt:setBundle basename="fr.sparna.validator.properties.Bundle"/>

<html>
<head>

<title>Validator</title>
<meta http-equiv="content-type" content="text/html; charset=UTF-8">
<link href="resources/bootstrap/css/bootstrap.min.css" rel="stylesheet" />
<link href="resources/jasny-bootstrap/css/jasny-bootstrap.min.css"
	rel="stylesheet" />
<link href="resources/css/style.css" rel="stylesheet" />
<script src="resources/js/jquery-1.11.3.js"></script>
<script src="resources/bootstrap/js/bootstrap.min.js"></script>
<script src="resources/jasny-bootstrap/js/jasny-bootstrap.min.js"></script>

<script type="text/javascript">
	var choix="";
	
	function choice(){
		
		$(".ruleCheckbox:checked").each(function( index ) {
			choix += $( this ).val();
			choix += ",";
		});
		// on enleve le dernier caractere
		choix = choix.substr(0, choix.length-1);
		document.formulaire.rulesChoice.value =choix;
		document.formulaire.submit();		
	}
	
	function enabledInput(selected) {
		document.getElementById('source-' + selected).checked = true;
		document.getElementById('url').disabled = selected != 'url';
		document.getElementById('file').disabled = selected != 'file';
		
	}	
	
	
</script>
<style>
.table-outer {
	height: 140px;
	overflow: hidden;
}

.table-outer.open {
	height: unset;
	overflow: visible;
}
</style>
</head>
<body style="">
	<jsp:include page="header.jsp" />
	
	<div class="container">

		<c:if test="${data.msg!=null}">
			<div class="alert alert-danger">
				<em> ${data.msg} </em>
			</div>c	
		</c:if>
	
		<em style="font-size: larger;"><fmt:message key="app.description"/></em><br><br>

		<form id="upload_form" action="result" method="post"
			onSubmit="return false" name="formulaire"
			enctype="multipart/form-data" class="form-horizontal">
			
			<div class="jumbotron" style="padding-bottom: 80px;">			
				<h4><fmt:message key="choice" /></h4>
				<br />
				<div class="form-group">
					<div class="col-sm-1" style="text-align:right">
						<input type="radio" name="source" id="source-file"
							value="file" onchange="enabledInput('file')" />
					</div>
						<label class="col-sm-2 control-label" for="source-file"> <fmt:message key="localFile" />
					</label>
					<div class="col-sm-8">
						<div class="fileinput fileinput-new input-group "
							data-provides="fileinput" id="files">
	
							<div class="form-control" data-trigger="fileinput">
								<i class="glyphicon glyphicon-file fileinput-exists"></i> <span
									class="fileinput-filename"></span>
							</div>
							<span class="input-group-addon btn btn-default btn-file"> <span
								class="fileinput-new"> <fmt:message key="file" /></span> <span
								class="fileinput-exists"><fmt:message key="change" /></span><input
								onchange="enabledInput('file');" type="file" id="file" required
								name="file">
							</span> <a href="#"
								class="input-group-addon btn btn-default fileinput-exists"
								data-dismiss="fileinput"><fmt:message key="remove" /> </a>
								
						</div>
						<span class="help-block"><fmt:message key="file.help" /></span>
					</div>
				</div>
	
				<div class="form-group">
					<div class="col-sm-1" style="text-align:right">
						<input type="radio" name="source" id="source-url"
							value="url" onchange="enabledInput('url')" />
					</div>
						<label class="col-sm-2 control-label" for="source-url"> <fmt:message key="remoteUrl" /></label>
					<div class="col-sm-8">
						<input type="text" id="url" name="url" value=""
							placeholder="http://..." class="form-control"
							onchange="enabledInput('url');" /> 
					</div>
				</div>

				<br />
				<div class="col-sm-offset-3 col-sm-8">
					<button class="btn-lg btn-primary" type="submit" onclick="choice()">
						<fmt:message key="valid" />
					</button>
				</div>
				
			</div>
	
	
			<div class="panel-group" id="myAccordion">
				<div class="panel panel-default">
					<div class="panel-heading">
						<a class="accordion-toggle " data-toggle="collapse"
							data-parent="#myAccordion" href="#collapse1">
							<h4>
								<fmt:message key="option" />
							</h4>
						</a>
					</div>
					<div id="collapse1" class="panel-collapse collapse in">
						<div class="panel-body">
							<c:forEach items="${applicationData.issueDescriptions}" var="rule">
								<div class="form-group">
									<c:set var="compteur" scope="session" value="${compteur+1}" />
									<label class="col-sm-3"> <c:choose>
											<c:when test="${sessionData.userLocale== 'fr'}">
												<c:forEach items="${rule.lbconcept}" var="label">
													<c:if test="${label.key=='fr'}">
														<a href="${rule.link}" target="_blank">${rule.id} - ${label.value}</a>
													</c:if>
												</c:forEach>
											</c:when>
											<c:when test="${sessionData.userLocale == 'en'}">
												<c:forEach items="${rule.lbconcept}" var="label">
													<c:if test="${label.key=='en'}">
														<a href="${rule.link}" target="_blank">${rule.id} - ${label.value}</a>
													</c:if>
												</c:forEach>
											</c:when>
											<c:otherwise></c:otherwise>
										</c:choose>
									</label>
									<div class="col-sm-9">
										<input class="ruleCheckbox" type="checkbox" name="rule${compteur}"
											id="rule${compteur}" value="${rule.id}"
											<c:if test="${rule.checked==true}"> checked</c:if>> <span
											class="help-block"> <c:choose>
												<c:when test="${sessionData.userLocale== 'fr'}">
													<c:forEach items="${rule.description}" var="desc">
														<c:if test="${desc.key=='fr'}">
																		${desc.value}
																	</c:if>
													</c:forEach>
												</c:when>
												<c:when test="${sessionData.userLocale == 'en'}">
													<c:forEach items="${rule.description}" var="desc">
														<c:if test="${desc.key=='en'}">
																		${desc.value}
																	</c:if>
													</c:forEach>
												</c:when>
												<c:otherwise></c:otherwise>
											</c:choose>
	
										</span>
									</div>
	
								</div>
							</c:forEach>
						</div>
					</div>
				</div>
				<!-- end accordion -->
				<br>
			</div>
			
			<input type="hidden" name="rulesChoice" id="rulesChoice" />
		</form>
	
	</div>
	<jsp:include page="footer.jsp" />
</body>

<script type="text/javascript">
	$(document).ready(function() {
			
	});
</script>
</html>