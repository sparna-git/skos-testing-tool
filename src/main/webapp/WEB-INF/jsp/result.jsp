<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<c:set var="data" value="${requestScope['fr.sparna.validator.ValidatorData']}" />
<c:set var="sessionData" value="${sessionScope['fr.sparna.validator.SessionData']}" />
<c:set var="compteur" scope="session" value="0" />
<html>
<head>

<title>Validator</title>
<meta http-equiv="content-type" content="text/html; charset=UTF-8">
<link href="resources/jasny-bootstrap/css/jasny-bootstrap.min.css" rel="stylesheet" />
<link href="resources/bootstrap/css/bootstrap.min.css"   rel="stylesheet" />
<link href="resources/css/media.css" rel="stylesheet" />
<link href="resources/css/style.css" rel="stylesheet" />
<script src="resources/js/jquery-1.11.3.js"></script>
<script src="resources/bootstrap/js/bootstrap.min.js"></script>
<script src="resources/bootstrap/js/bootstrap-table-expandable.js"></script>
<style>
	.table-outer {
				height:100px;
				overflow:hidden;
		}
			
	.table-outer.open {
				height:unset;
				overflow:visible;
	}
	.navigation {position:fixed;}
</style>
<script type="text/javascript">

</script>
</head>
<body id="resultpage">
<jsp:include page="header.jsp"/>
 <div class="navigation hidden-print" >
 <ul id="sidebar" class="nav col-xs-12 left">
 	<c:forEach items="${data.errorList}" var="error">	  
		  	 <c:set var="compteur" scope="session" value="${compteur+error.number}" />
		  	 <li class="nav-item">
			    <a  href="#${error.id}"
			    	<c:if test="${error.success==true}"> class="nav-link hidden-print success" </c:if>
			 		<c:if test="${error.success==false}">class="nav-link hidden-print danger" </c:if>
			 	>${error.ruleName} : ${error.state}</a>
			 </li>
	</c:forEach>
</ul>
</div>
  <div class="panel panel-info content" id="stat">
      <div class="panel-heading">Details</div>
      	<div class="panel-body">
		  <table id="table" data-show-print="true" class="table table-hover table-expandable table-striped">
		    <thead>
		      <tr>
		        <th>Rules</th>
		        <th>Description</th>
		        <th>State</th>
		      </tr>
		    </thead>
		    <tbody>
		    	<c:forEach items="${data.errorList}" var="error">
					<tr
						<c:if test="${error.success==true}"> class="success" </c:if>
			        	<c:if test="${error.success==false}">class="danger" </c:if>
					>
				        <td><a href="${error.weblink}" id="${error.id}">${error.ruleName}(${error.id})</a></td>
				        <td>${error.description}</td>
				        <td>${error.state}</td>
			        </tr>
			        <c:if test="${!empty error.errorList}">
					     <tr id="content" class="default">
			        	<td colspan="3">
				          <div class="table-outer">
				          	<c:forEach items="${error.errorList}" var="list">
								  ${list}<br>
							</c:forEach>
				           </div>
				           <div>
					           <c:if test="${error.number>4}">
					           		<a class="toggleTableLink" href="#"><i>show/hide all ${error.number} results</i></a>
					           </c:if> 
					       </div>
				        </td>
			      	</tr>       		
					</c:if> 
			          
				</c:forEach>
		      </tbody>
		    </table>
		 </div>
    </div>
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