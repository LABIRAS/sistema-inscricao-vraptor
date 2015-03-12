<%@ page language="java" pageEncoding="UTF-8" isELIgnored="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html ng-app="adminInscricaoLabiras">
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="robots" content="NOINDEX, NOFOLLOW">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title><fmt:message key="app.name" /></title>
<script>(function(i,s,o,g,r,a,m){i['GoogleAnalyticsObject']=r;i[r]=i[r]||function(){(i[r].q=i[r].q||[]).push(arguments)},i[r].l=1*new Date();a=s.createElement(o),m=s.getElementsByTagName(o)[0];a.async=1;a.src=g;m.parentNode.insertBefore(a,m)})(window,document,'script','//www.google-analytics.com/analytics.js','ga');ga('create','<fmt:message key="app.googleAnalyticsID" />','auto');ga('set','appName','<fmt:message key="app.name" />');</script>
<link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Open+Sans:300,400,600">
<link rel="stylesheet" href="https://bootswatch.com/flatly/bootstrap.min.css">
<link rel="stylesheet" href="<fmt:message key="app.urlBase" />/res/css/app.css">
</head>
<body ng-controller="DashboardController">
	<div class="navbar navbar-default navbar-fixed-top">
		<div class="container">
			<div class="navbar-header">
				<a href="http://arduinoday.labiras.cc/" class="navbar-brand">Arduino Day 2015</a>
				<button class="navbar-toggle" type="button" data-toggle="collapse" data-target="#navbar-main">
					<span class="icon-bar"></span><span class="icon-bar"></span><span class="icon-bar"></span>
				</button>
			</div>
			<div class="navbar-collapse collapse" id="navbar-main">
				<ul class="nav navbar-nav">
					<li><a href="http://labiras.cc/arduinoday2015/programacao/" target="_blank">Programação</a></li>
					<li><a href="http://labiras.cc/photo_editor" target="_blank">Photo Editor</a></li>
					<li><a href="http://labiras.cc/arduinoday2015/facebook/" target="_blank">facebook</a></li>
				</ul>
				<ul class="nav navbar-nav navbar-right">
					<li><a href="http://labiras.cc/" target="_blank">LABIRAS</a></li>
				</ul>
			</div>
		</div>
	</div>

	<div class="container" id="dashboard">
		<div class="row">
			<div class="col-xs-12">
				<c:if test="${info ne null}">
					<div class="alert alert-info alert-dismissible" role="alert">
						<button type="button" class="close" data-dismiss="alert"><span aria-hidden="true">&times;</span><span class="sr-only">Fechar</span></button>
						<strong>Hey!</strong> ${info}
					</div>
				</c:if>
				
				<c:if test="${warn ne null}">
					<div class="alert alert-warning alert-dismissible" role="alert">
						<button type="button" class="close" data-dismiss="alert"><span aria-hidden="true">&times;</span><span class="sr-only">Fechar</span></button>
						<strong>Atenção:</strong> ${warn}
					</div>
				</c:if>
				
				<c:if test="${success ne null}">
					<div class="alert alert-success alert-dismissible" role="alert">
						<button type="button" class="close" data-dismiss="alert"><span aria-hidden="true">&times;</span><span class="sr-only">Fechar</span></button>
						<strong>Sucesso!</strong> ${success}
					</div>
				</c:if>
				
				<c:if test="${error ne null}">
					<div class="alert alert-danger alert-dismissible" role="alert">
						<button type="button" class="close" data-dismiss="alert"><span aria-hidden="true">&times;</span><span class="sr-only">Fechar</span></button>
						<strong>Erro!</strong> ${error}
					</div>
				</c:if>
				
				<c:if test="${errors ne null && !empty(errors)}">
					<div class="alert alert-danger alert-dismissible" role="alert">
						<button type="button" class="close" data-dismiss="alert"><span aria-hidden="true">&times;</span><span class="sr-only">Fechar</span></button>
						<h4>Por favor, corrija o seguinte:</h4> 
						<ol><c:forEach items="${errors}" var="item"><li>${item.message}</li></c:forEach></ol>
					</div>
				</c:if>
			</div>
		</div>
		
		<div class="row">
			<div class="col-xs-12">
				<h1>Inscritos</h1>
				<p>Depois vou implementar mais coisa, hehe...</p>
				
				<p>Total de Inscritos: <strong ng-bind="inscritos.length | number"></strong></p>
				<div class="well" id="dados-inscritos">
					<div class="row" ng-repeat="inscrito in inscritos">
						<div class="col-sm-2"><strong>Nome:</strong></div>
						<div class="col-sm-4">{{inscrito.nome}}</div>
						<div class="col-sm-2"><strong>Email:</strong></div>
						<div class="col-sm-4"><a href="mailto:{{inscrito.email}}">{{inscrito.email}}</a></div>
					</div>
				</div>
			</div>
		</div>
	</div>
</body>
</html>