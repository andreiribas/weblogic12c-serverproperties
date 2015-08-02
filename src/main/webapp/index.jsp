<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">
<!-- 
The MIT License (MIT)
Copyright (c) 2015 Andrei Gonçalves Ribas <andrei.g.ribas@gmail.com>
Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:
The above copyright notice and this permission notice shall be included in
all copies or substantial portions of the Software.
THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
THE SOFTWARE.
-->
<html>
<head>
	<title>Weblogic 12c Server Properties</title>
	
	<style type="text/css">
		table {
			border: 1px solid black;
			word-wrap: break-word;
            table-layout: fixed;
            width: 100%;
            border-collapse: collapse;
        }
        
        table th, table td {
        	border: 1px solid black;
			width: 100%;
        }
        
        .center {
        	text-align: center;
        }
	</style>
</head>
<body>
	<h1 class="center">
		Weblogic 12c Server Properties
	</h1>

	<h3 class="center">
		Server Details
	</h3>

	<table>
		<tr>
			<td style="font-weight: bold;">Server name:</td>
			<td><c:out value="${serverProperties.name}" /></td>
		</tr>
		<tr>
			<td style="font-weight: bold;">Domains:</td>
			<td></td>
		</tr>
		<c:forEach var="domain" items="${serverProperties.domains}">
			<tr>
				<td></td>
				<td><c:out value="${domain}" /></td>
			</tr>
		</c:forEach>
		<tr>
			<td style="font-weight: bold;">Deault Domain:</td>
			<td><c:out value="${serverProperties.defaultDomain}" /></td>
		</tr>
	</table>

	<h3 class="center">
		Server Properties
	</h3>

	<table>
		<tr>
			<th>Name</th>
			<th>Type</th>
			<th>Value</th>
			<th>Description</th>
		</tr>
		<c:forEach var="info"
			items="${serverProperties.serverPropertiesInfoCollection}">
			<tr>
				<td><c:out value="${info.attributeName}" /></td>
				<td><c:out value="${info.attributeType}" /></td>
				<td><c:out value="${info.attributeValue}" /></td>
				<td><c:out value="${info.attributeDescription}" /></td>

			</tr>
		</c:forEach>
	</table>

	<div style="margin-top: 20px;">
		<h3>Exception?</h3>
		<div>
			<c:choose>
				<c:when test="${empty exceptionStackTrace}">
        			No
    			</c:when>
				<c:otherwise>
					<c:out value="${exceptionStackTrace}" />
				</c:otherwise>
			</c:choose>
		</div>
	</div>

</body>
</html>