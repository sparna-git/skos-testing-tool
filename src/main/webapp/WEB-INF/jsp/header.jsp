<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<fmt:setLocale value="${sessionScope['fr.sparna.validator.SessionData'].userLocale}"/>
<fmt:setBundle basename="Bundle" />
<c:set var="sessionData" value="${sessionScope['fr.sparna.validator.SessionData']}" />
<nav class="navbar navbar-default">

	<div class="container-fluid">
		<div class="navbar-header">
			<a class="navbar-brand" href="home">Skos Validator</a>
		</div>
		<ul class="nav navbar-nav">
			<li class="active">
				<a href="home">
					<em>
						<fmt:message key="menu.home"/>
					
					</em>

				</a></li>
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
							<li><a href="?lang=en">en</a></li>
						</c:when>
						<c:when
							test="${sessionData.userLocale == 'en'}">
							<li><a href="?lang=fr">fr</a></li>
						</c:when>
					</c:choose>
				</ul>
			</li>
			<li>
				<a href="https://github.com/cmader/qSKOS"><em>QSkos documentation</em></a>
			</li>
		</ul>
	</div>
</nav>