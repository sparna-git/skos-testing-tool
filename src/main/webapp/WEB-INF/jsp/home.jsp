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
		for (i = 1; i < 26; i++) {
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
				height:200px;
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
		<h5 id="TITLE"><em>Skos file validator</em></h5>
		<br>
		<br>
		<c:if test="${data.msg!=null}">
				<div class="alert alert-danger">
					  <em>
					  	${data.msg}
					 </em>
				</div>
				
		</c:if>
		<div class="jumbotron">
			<form id="upload_form" action="result" method="post" onSubmit="return false" name="formulaire" enctype="multipart/form-data" class="form-horizontal">
				
					<div class="fileinput fileinput-new input-group" data-provides="fileinput" id="file">
				  		<div class="form-control" data-trigger="fileinput">
				  			<i class="glyphicon glyphicon-file fileinput-exists"></i> 
				  			<span class="fileinput-filename"></span>
				  		</div>
					  <span class="input-group-addon btn btn-default btn-file"><span class="fileinput-new">Select file</span>
					  <span class="fileinput-exists">Change</span><input type="file" required name="file" ></span>
					  <a href="#" class="input-group-addon btn btn-default fileinput-exists" data-dismiss="fileinput">Remove</a>
					</div><br>
					<button class="btn btn-info" type="submit" onclick="choice()">valider</button>
					
			<br><br><br>
			<h5 id="TITLE"><em>Choose rules to check</em></h5>
			<div class="table-outer">
			<table id="box">
				<c:forEach items="${applicationData.rulesList}" var="rule">	
					 	<tr>
					 		<td>
					 			<c:set var="compteur" scope="session" value="${compteur+1}" />
							    <input type="checkbox"  name="rule${compteur}"  id="rule${compteur}" value="${rule}"
							    	<c:if test="${rule=='oc' || rule=='el'|| rule=='ml' || rule=='uc'}">checked</c:if>
								     >
							</td>
							<td>${rule.label} (${rule})</td>
						</tr>	
				</c:forEach>
			</table>
		   </div>
		   <div><a class="toggleTableLink" id="box" href="#"><i>show/hide all rules</i></a></div>
			<input name="rulesChoice" id="rulesChoice">
			</form>
		</div>
		
		<!--<textarea name="log" id="log" rows="15" class="form-control" cols="30"></textarea><c:if test="${data.errorList!=null}">
			<c:forEach items="${data.errorList}" var="error">
				<h5>${error.ruleName} : ${error.errorNumber}</h5>
				<div class="alert alert-success">
					  <em>
					  	<c:forEach items="${error.errorList}" var="list">
							${list}<br>
						</c:forEach>
					 </em>
				</div>
				<br>
			</c:forEach>
		</c:if>
		-->
	
</body>
<script type="text/javascript">
	$(document).ready(function() {
		var newinput = document.getElementById('rulesChoice');
		newinput.style.visibility = "hidden";
		// add the toggle link behavior
        $( ".toggleTableLink" ).click(function(event) {
    		$(this).parent().prev(".table-outer").toggleClass("open");
    		return false;
        });
		
	});
</script>
</html>