<%@ page language="java" pageEncoding="UTF-8" isELIgnored="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
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
<body>
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

	<div class="container" id="login">
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
			<div class="col-xs-12 col-sm-8 col-sm-offset-2 col-md-6 col-md-offset-3 col-lg-4 col-lg-offset-4">
				<div class="well">
					<form action="<fmt:message key="app.urlBase" />/auth" method="post">
						<fieldset>
							<legend>Inscrições - Login</legend>
							
							<div class="form-group">
								<label for="username" class="control-label">Usuário</label>
								<input type="text" id="username" name="username" class="form-control" placeholder="Usuário ou E-mail" required>
							</div>
							
							<div class="form-group">
								<label for="password" class="control-label">Senha</label>
								<input type="password" id="password" name="password" class="form-control" placeholder="Umas bolotinhas" required>
							</div>
							
							<div class="form-group">
								<div class="checkbox">
									<label><input type="checkbox" name="remember" value="true" /> Lembrar de Mim :)</label>
								</div>
							</div>
							
							<div class="row">
								<div class="col-sm-9 col-md-offset-3">
									<button type="reset" class="btn btn-default">Cancelar</button>
									<button type="submit" class="btn btn-primary">Go go go!</button>
								</div>
							</div>
						</fieldset>
					</form>
				</div>
			</div>
		</div>
	</div>
</body>
</html>