<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" 	prefix="fmt" 	%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" 	prefix="c" 		%>

<!-- setup the locale for the messages based on the language in the session -->
<fmt:setLocale value="${sessionScope['fr.sparna.validator.SessionData'].userLocale}"/>
<fmt:setBundle basename="fr.sparna.validator.properties.Bundle"/>

      	<footer id="footer">
      	<fmt:message key="source" />
      	&nbsp;|&nbsp;
      	<fmt:message key="developpedBy" />&nbsp;
      	<a href="http://sparna.fr" target="_blank"><img src="resources/images/sparna.jpeg" style="width:80px;"/></a>
      	<br />
      	<br />
      	</footer>