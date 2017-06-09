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

<script type="text/javascript">
	var choix="";
	var compteur=0;
	function choice(){
		/*compteur++;
		if(objet.checked === true){
			
			if(compteur===1){
				choix+=objet.value;
				
			}else{
				choix+=","+objet.value;
			}
		}*/
		for (i = 1; i < 27; i++) {
			if (eval("document.formulaire.rule" + i
					+ ".checked == true")) {
				compteur++;
				
				if (compteur === 1) {
					choix += document.getElementById('rule' + i).value;
					
					
				} else if (compteur > 1) {
					choix += '-' + document.getElementById('rule' + i).value;
							
				}
				
			}
		}
		
		document.formulaire.rulesChoice.value =choix;
		document.formulaire.submit();
		//document.formulaire.file.value =document.formulaire.file.value;
		
	}	
	
	
</script>
<style>
	.table-outer {
				height:140px;
				overflow:hidden;
		}
			
	.table-outer.open {
				height:unset;
				overflow:visible;
	}
</style>
</head>
<body style="">
<jsp:include page="header.jsp"/>
		<c:if test="${data.msg!=null}">
				<div class="alert alert-danger">
					  <em>
					  	${data.msg}
					 </em>
				</div>
				
		</c:if>
		<form id="upload_form" action="result" method="post" onSubmit="return false" name="formulaire" enctype="multipart/form-data" class="form-horizontal">
		<div class="jumbotron">
			
				<label for="file"><em><font size="3">Choose your file</font></em></label>
					<div class="fileinput fileinput-new input-group" data-provides="fileinput" id="file">
				  		<div class="form-control" data-trigger="fileinput">
				  			<i class="glyphicon glyphicon-file fileinput-exists"></i> 
				  			<span class="fileinput-filename"></span>
				  		</div>
					  <span class="input-group-addon btn btn-default btn-file"><span class="fileinput-new">Select file</span>
					  <span class="fileinput-exists">Change</span><input type="file" required name="file" ></span>
					  <a href="#" class="input-group-addon btn btn-default fileinput-exists" data-dismiss="fileinput">Remove</a>
					</div>
					<input name="rulesChoice" id="rulesChoice"><br>
					<button class="btn btn-info" type="submit" onclick="choice()">valider</button>
			
			
		</div>
		<div class="panel-group" id="myAccordion">
				<div class="panel panel-default">
	   				<div class="panel-heading">
	   					<a class="accordion-toggle " data-toggle="collapse" data-parent="#myAccordion" href="#collapse1"><h4>Advanced options</h4></a>
	   				</div>
	   				<div id="collapse1" class="panel-collapse collapse in" >
	   					<div class="panel-body">
	   						<c:forEach items="${applicationData.rulesList}" var="rule">	
								 	<div class="form-group">
										<c:set var="compteur" scope="session" value="${compteur+1}" />
										<div class="col-sm-8">
											<input type="checkbox"  name="rule${compteur}"  id="rule${compteur}" value="${rule}"
											<c:if test="${rule!='bl'}">checked</c:if>
											>
											<label class="col-sm-8">
											<a href="${rule.link}">${rule.label} (${rule})</a>
											</label>
										</div>
										
									</div>
							</c:forEach>
							
	   					</div>
	   				</div>
	   			</div><!-- end accordion -->
	   			<br>
	   	</div>
	   	</form>
	
</body>
<script type="text/javascript">
	$(document).ready(function() {
		var newinput = document.getElementById('rulesChoice');
		newinput.style.visibility = "hidden";
		// add the toggle link behavior
        $( ".accordion-toggle" ).click();
		
	});
</script>
</html>