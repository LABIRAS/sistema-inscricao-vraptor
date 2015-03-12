<%@ page language="java" pageEncoding="UTF-8" isELIgnored="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html ng-app="inscricaoLabirasApp">
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="description" content="<fmt:message key="website.header.description" />">
<meta name="keywords" content="<fmt:message key="website.header.keywords" />">
<meta name="viewport" content="width=device-width, initial-scale=1">

<title><fmt:message key="app.name" /></title>
<!-- Add to homescreen for Chrome on Android -->
<meta name="mobile-web-app-capable" content="yes">
<link rel="icon" sizes="196x196" href="<fmt:message key="website.header.app.icon196" />">
<meta name="theme-color" content="<fmt:message key="website.header.app.color" />">

<!-- Add to homescreen for Safari on iOS -->
<meta name="apple-mobile-web-app-capable" content="yes">
<meta name="apple-mobile-web-app-status-bar-style" content="black">
<meta name="apple-mobile-web-app-title" content="<fmt:message key="app.name" />">

<!-- Tile icon for Win8 (144x144 + tile color) -->
<meta name="msapplication-TileImage" content="<fmt:message key="website.header.app.icon144" />">
<meta name="msapplication-TileColor" content="<fmt:message key="website.header.app.color" />">

<script>(function(i,s,o,g,r,a,m){i['GoogleAnalyticsObject']=r;i[r]=i[r]||function(){(i[r].q=i[r].q||[]).push(arguments)},i[r].l=1*new Date();a=s.createElement(o),m=s.getElementsByTagName(o)[0];a.async=1;a.src=g;m.parentNode.insertBefore(a,m)})(window,document,'script','//www.google-analytics.com/analytics.js','ga');ga('create','<fmt:message key="app.googleAnalyticsID" />','auto');ga('set','appName','<fmt:message key="app.name" />');</script>
<link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Open+Sans:300,400,600">
<link rel="stylesheet" href="https://bootswatch.com/flatly/bootstrap.min.css">
<link rel="stylesheet" href="<fmt:message key="app.urlBase" />/res/css/app.css">
<script src="//ajax.googleapis.com/ajax/libs/angularjs/1.3.6/angular.min.js"></script>
<script src="<fmt:message key="app.urlBase" />/res/js/app.js"></script>
</head>
<body ng-controller="PageController">
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

	<div class="container" id="profile">
		<div class="row">
			<div class="col-xs-12 col-sm-3" id="profile-image-container">
				<img src="https://gravatar.com/avatar/${inscrito.gravatar}?s=150&d=retro" id="profile-image" width="150" height="150" alt="Gravatar" />
			</div>
			<div class="col-xs-12 col-sm-7">
				<h1>${inscrito.nome}</h1>
				<p id="profile-dados"><small>${inscrito.email} - ${inscrito.idade} anos, ${inscrito.cidade} &mdash; ${inscrito.uf}</small></p>
			</div>
		</div>
		
		<div class="row">
			<div class="col-xs-12">
				<div class="well" id="profile-extra">
					<c:if test="${inscrito.estudante}">
						<h2>Estudante</h2>
						<div class="row">
							<div class="col-sm-3"><strong>Curso:</strong></div>
							<div class="col-sm-9">${inscrito.curso}</div>
						</div>
						<div class="row">
							<div class="col-sm-3"><strong>Instituição:</strong></div>
							<div class="col-sm-9">${inscrito.ultimaInstituicao}</div>
						</div>
					</c:if>
					
					<c:if test="${inscrito.profissional}">
						<h2>Profissional</h2>
						<div class="row">
							<div class="col-sm-3"><strong>Empresa:</strong></div>
							<div class="col-sm-9">${inscrito.empresa}</div>
						</div>
					</c:if>
				</div>
			</div>
		</div>
	</div>
</body>
</html>