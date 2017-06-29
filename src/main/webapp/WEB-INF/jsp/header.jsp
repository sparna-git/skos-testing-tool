<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<c:set var="sessionData" value="${sessionScope['fr.sparna.rdf.skos.testtool.SessionData']}" />
<fmt:setLocale
	value="${sessionScope['fr.sparna.rdf.skos.testtool.SessionData'].userLocale}" />
<fmt:setBundle basename="fr.sparna.rdf.skos.testtool.properties.Bundle"/>

<nav class="navbar navbar-default hidden-print">

	<div class="container-fluid">
		<div class="navbar-header">
			<a class="navbar-brand" href="home"><fmt:message key="menu.brand"/></a>
		</div>
		<ul class="nav navbar-nav pull-right" style="padding-right:60px;">
			<li >
				<a href="home">
					<span class="glyphicon glyphicon-home" aria-hidden="true"></span>
					&nbsp;
					<em><fmt:message key="menu.home"/></em>
				</a>
			</li>
			<li id="header-pill-lang"><a class="dropdown-toggle"
				data-toggle="dropdown" href="#"> <c:choose>
						<c:when 
							test="${sessionData.userLocale == 'fr'}">fr</c:when>
						<c:when
							test="${sessionData.userLocale == 'en'}">en</c:when>
					</c:choose> <b class="caret"></b>
			</a>
				<ul class="dropdown-menu">
					<c:choose>
						<c:when
							test="${sessionData.userLocale== 'fr'}">
							<li><a href="home?lang=en">en</a></li>
						</c:when>
						<c:when
							test="${sessionData.userLocale == 'en'}">
							<li><a href="home?lang=fr">fr</a></li>
						</c:when>
					</c:choose>
				</ul>
			</li>
			<li>
				<a href="https://github.com/tfrancart/skos-testing-tool">
					<img style="position: absolute; top: 0; left: 0; border: 0;" 
						src="https://camo.githubusercontent.com/121cd7cbdc3e4855075ea8b558508b91ac463ac2/
						68747470733a2f2f73332e616d617a6f6e6177732e636f6d2f6769746875622f726962626f6e732f
						666f726b6d655f6c6566745f677265656e5f3030373230302e706e67" 
						alt="Fork me on GitHub" data-canonical-src="https://s3.amazonaws.com/github/ribbons/forkme_left_green_007200.png">
				</a>
			</li>
		</ul>
	</div>
</nav>