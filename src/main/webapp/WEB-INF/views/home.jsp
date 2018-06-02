<%--
  Created by IntelliJ IDEA.
  User: monkey
  Date: 2018/6/1
  Time: 15:11
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="false" %>
<html>
<head>
    <title>Spitter</title>
    <link rel="stylesheet"
          type="text/css"
          href="<c:url value="/resources/style.css" />" >
</head>
<body>
<h1>Welcome to Spitter</h1>

<a href="<c:url value="/spittle/spittles" />">Spittles</a>
</body>
</html>

