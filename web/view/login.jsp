<%--
  Created by IntelliJ IDEA.
  User: 78544
  Date: 2016/11/24
  Time: 16:58
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    Object userName = request.getAttribute("userName");
    Object password = request.getAttribute("password");

%>
<html>
<head>
    <title>Title</title>
</head>
<body>
<h3>userName:<%=userName%></h3>
<h3>password:<%=password%></h3>

</body>
</html>
