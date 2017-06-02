<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<c:set var="data" value="${requestScope['fr.sparna.validator.ValidatorData']}" />
<html>
<head>

<title>Validator</title>
<meta http-equiv="content-type" content="text/html; charset=UTF-8">
<link href="resources/bootstrap/css/bootstrap.min.css" rel="stylesheet" />
<link href="resources/jasny-bootstrap/css/jasny-bootstrap.min.css" rel="stylesheet" />
<link href="resources/css/style.css" rel="stylesheet" />
<script src="resources/js/jquery-1.11.3.js"></script>
<script src="resources/bootstrap/js/bootstrap.min.js"></script>
<link rel="stylesheet" href="resources/bootstrap/css/bootstrap-table-expandable.css">
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
</style>
</head>
<body >
		<h3 id="TITLE">Skos file validator</h3>
  <br><br>	
  
  <table id="table" class="table table-hover table-expandable table-striped">
    <thead>
      <tr>
        <th>Rules</th>
        <th>Description</th>
        <th>ID</th>
        <th>WebLink</th>
        <th>State</th>
      </tr>
    </thead>
    <tbody>
    	<c:forEach items="${data.errorList}" var="error">
			<tr
				<c:if test="${error.success==true}"> </c:if>
	        	<c:if test="${error.success==false}"> </c:if>
			>
		        <td>${error.ruleName}</td>
		        <td>${error.description}</td>
		        <td>${error.id}</td>
		        <td>${error.weblink}</td>
		        <td>${error.state}</td>
	        </tr>
	        <tr id="content"
	        <c:if test="${error.success==true}"> </c:if>
	        	<c:if test="${error.success==false}"> 
	        	</c:if>
	        >
	        
	        <td colspan="5"><em>Additional information</em><br>
		          <div class="table-outer">
		          	<c:forEach items="${error.errorList}" var="list">
						  ${list}<br>
					</c:forEach>
		           </div>
		           <div><a class="toggleTableLink" href="#"><i>show/hide all ${error.number} results</i></a></div>
		        </td>
		       
	      	</tr>   
		</c:forEach>
      </tbody>
    </table>
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