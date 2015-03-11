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
<%--
<link rel="stylesheet" href="//ajax.googleapis.com/ajax/libs/angular_material/0.8.2/angular-material.min.css">
<script src="//ajax.googleapis.com/ajax/libs/angularjs/1.3.6/angular-animate.min.js"></script>
<script src="//ajax.googleapis.com/ajax/libs/angularjs/1.3.5/angular-aria.min.js"></script>
<script src="//ajax.googleapis.com/ajax/libs/angular_material/0.8.2/angular-material.min.js"></script>
--%>
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
					<!--
					<li><a href="https://wrapbootstrap.com/?ref=bsw" target="_blank">WrapBootstrap</a></li>
					-->
				</ul>
			</div>
		</div>
	</div>

	<div class="container" id="page">
		<div class="row">
			<div class="col-xs-12">
				<h1>Inscrições</h1>
			</div>
		</div>
		
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
			<div class="col-xs-12 col-md-8">
				<div class="well">
					<form name="formInscricao" action="{{'<fmt:message key="app.urlBase" />/' + acaoSubmit}}" method="post" class="form-horizontal">
						<fieldset>
							<legend ng-bind="acaoSubmit == 'cadastrar' ? 'Nova Inscrição' : 'Altere seus dados'"></legend>
							<div class="form-group">
								<label for="nome" class="col-sm-3 control-label">Nome Completo *</label>
								<div class="col-sm-9">
									<input type="text" id="nome" name="i.nome" ng-model="inscrito.nome" class="form-control" placeholder="Seu nome" required>
								</div>
							</div>
							
							<div class="form-group">
								<label for="email" class="col-sm-3 control-label">E-mail *</label>
								<div class="col-sm-9">
									<input type="email" id="email" name="i.email" ng-model="inscrito.email" class="form-control" placeholder="email@bem-legal.com" required>
								</div>
							</div>
							
							<div class="form-group">
								<label for="cpf" class="col-sm-3 control-label">CPF *</label>
								<div class="col-sm-4">
									<input type="text" id="cpf" name="i.cpf" ng-model="inscrito.cpf" class="form-control" placeholder="CPF" required>
								</div>
								
								<label for="idade" class="col-sm-2 control-label">Idade *</label>
								<div class="col-sm-3">
									<input type="number" id="idade" name="i.idade" ng-model="inscrito.idade" class="form-control" min="1" max="150" placeholder="Idade" required>
								</div>
							</div>
							
							<div class="form-group">
								<label for="cidade" class="col-sm-3 control-label">Cidade *</label>
								<div class="col-sm-4">
									<input type="text" id="cidade" name="i.cidade" ng-model="inscrito.cidade" class="form-control" placeholder="Onde você está morando hoje?" required>
								</div>
								
								<label for="estado" class="col-sm-2 control-label">Estado *</label>
								<div class="col-sm-3">
									<select name="i.uf" ng-model="inscrito.uf" id="estado" class="form-control" required>
										<option value="AC">Acre</option>
										<option value="AL">Alagoas</option>
										<option value="AM">Amazônia</option>
										<option value="AP">Amapá</option>
										<option value="BA">Bahia</option>
										<option value="CE">Ceará</option>
										<option value="DF">Distrito Federal</option>
										<option value="ES">Espírito Santo</option>
										<option value="GO">Goiás</option>
										<option value="MA">Maranhão</option>
										<option value="MG">Minas Gerais</option>
										<option value="MS">Mato Grosso do Sul</option>
										<option value="MT">Mato Grosso</option>
										<option value="PA">Pará</option>
										<option value="PB">Paraíba</option>
										<option value="PE">Pernambuco</option>
										<option value="PI" selected>Piauí</option>
										<option value="PR">Paraná</option>
										<option value="RJ">Rio de Janeiro</option>
										<option value="RN">Rio Grande do Norte</option>
										<option value="RO">Rondônia</option>
										<option value="RR">Roraima</option>
										<option value="RS">Rio Grande do Sul</option>
										<option value="SC">Santa Catarina</option>
										<option value="SE">Sergipe</option>
										<option value="SP">São Paulo</option>
										<option value="TO">Tocantins</option>
									</select>
								</div>
							</div>
							
							<div class="form-group">
								<div class="col-xs-12 col-sm-offset-3 col-sm-4">
									<div class="checkbox">
										<h4><label><input type="checkbox" name="i.estudante" ng-model="inscrito.estudante" id="estudante" ng-required="!inscrito.estudante && !inscrito.profissional"> Sou Estudante</label></h4>
									</div>
								</div>
								<div class="col-xs-12 col-sm-5">
									<div class="checkbox">
										<h4><label><input type="checkbox" name="i.profissional" ng-model="inscrito.profissional" id="profissional" ng-required="!inscrito.estudante && !inscrito.profissional"> Sou Profissional</label></h4>
									</div>
								</div>
							</div>
							
							<div id="dadosEstudante" ng-show="inscrito.estudante">
								<div class="form-group">
									<label for="curso" class="col-sm-3 control-label">Curso *</label>
									<div class="col-sm-9">
										<input type="text" id="curso" name="i.curso" ng-model="inscrito.curso" class="form-control" placeholder="Qual seu curso atualmente?" ng-required="inscrito.estudante">
									</div>
								</div>
								
								<div class="form-group">
									<label for="nivelGraduacao" class="col-sm-3 control-label">Nível de Graduação *</label>
									<div class="col-sm-9">
										<select id="nivelGraduacao" name="i.nivelGraduacao" ng-model="inscrito.nivelGraduacao" class="form-control" ng-required="inscrito.estudante">
											<option value="FUNDAMENTAL_INCOMPLETO">Ensino Fundamental Incompleto</option>
											<option value="FUNDAMENTAL_COMPLETO">Ensino Fundamental Completo</option>
											<option value="MEDIO_INCOMPLETO" selected>Ensino Médio Incompleto</option>
											<option value="MEDIO_COMPLETO">Ensino Médio Completo</option>
											<option value="TECNICO">Técnico</option>
											<option value="LICENCIATURA">Licenciatura</option>
											<option value="TECNOLOGO">Tecnólogo</option>
											<option value="BACHAREL">Bacharel</option>
											<option value="ESPECIALIZACAO">Especialização</option>
											<option value="MESTRADO">Mestrado</option>
											<option value="DOUTORADO">Doutorado</option>
											<option value="POS_DOUTORADO">Pós-Doutorado</option>
										</select>
									</div>
								</div>
								
								<div class="form-group">
									<label for="ultimaInstituicao" class="col-sm-3 control-label">Instituição de Ensino *</label>
									<div class="col-sm-9">
										<input type="text" id="ultimaInstituicao" name="i.ultimaInstituicao" ng-model="inscrito.ultimaInstituicao" class="form-control" placeholder="Instiuição de Ensino a qual você está matriculado(a)" ng-required="inscrito.estudante">
									</div>
								</div>
							</div>
							
							<div id="dadosEstudante" ng-show="inscrito.profissional">
								<div class="form-group">
									<label for="empresa" class="col-sm-3 control-label">Empresa *</label>
									<div class="col-sm-9">
										<input type="text" id="empresa" name="i.empresa" ng-model="inscrito.empresa" class="form-control" placeholder="Empresa na qual você trabalha" ng-required="inscrito.profissional">
									</div>
								</div>
							</div>
							
							<div class="form-group">
								<label for="comoFicouSabendoDoEvento" class="col-sm-3 control-label">Como você descobriu o Arduino Day 2015? *</label>
								<div class="col-sm-9">
									<select id="comoFicouSabendoDoEvento" name="i.comoFicouSabendoDoEvento" ng-model="inscrito.comoFicouSabendoDoEvento" class="form-control" required>
										<option value="NENHUM">Selecione...</option>
										<option value="SITE_EVENTO">Site do Evento</option>
										<option value="FACEBOOK">Post no Facebook</option>
										<option value="TWITTER">Twitter</option>
										<option value="OUTDOOR">Outdoor</option>
										<option value="AMIGO">Fiquei sabendo por Amigos</option>
										<option value="MATERIA_INTERNET">Matéria em Portal de Notícias</option>
										<option value="MATERIA_JORNAL">Matéria em Jornal Impresso</option>
										<option value="MATERIA_TV">TV</option>
										<option value="OUTROS">Outras</option>
									</select>
								</div>
							</div>
							
							<div class="form-group">
								<label for="jaConheco" class="col-sm-3 control-label">Conhece o Arduino? *</label>
								<div class="col-sm-9">
									<select ng-model="jaConheco" id="jaConheco" ng-change="setJaConheco()" class="form-control">
										<option value="0">Não conheço Arduino, vou para conhecer!</option>
										<option value="1">Já conheço Arduino, vou para mexer com ele!</option>
										<option value="2">Já conheço Arduino e mexo com Arduino! Quero me divertir ;D</option>
									</select>
									
									<!--
									<div class="checkbox">
										<label><input type="checkbox" name="i.jaConheceArduino" ng-model="inscrito.jaConhecoArduino" id="jaConhecoArduino" ng-change="setJaConheco()"> Já conheço o Arduino e estou indo para trabalhar com ele! :)</label>
									</div>
									<div class="checkbox">
										<label><input type="checkbox" name="i.jaUsouArduino" ng-model="inscrito.jaUsouArduino" id="jaUsouArduino" ng-disabled="!inscrito.jaConhecoArduino"> Não só conheço, como já <strong>uso</strong> o Arduino ;D</label>
									</div>
									-->
								</div>
							</div>
							
							<div class="row">
								<div class="col-sm-9 col-md-offset-3">
									<input type="hidden" name="i.id" id="i_id" />
									<input type="hidden" name="i.jaConheceArduino" id="i_jaConheceArduino" />
									<input type="hidden" name="i.jaUsouArduino" id="i_jaUsouArduino" />
									<button type="reset" class="btn btn-default">Cancelar</button>
									<button type="submit" class="btn btn-primary">Vamos lá!</button>
								</div>
							</div>
						</fieldset>
					</form>
				</div>
			</div>
			
			<div class="col-xs-12 col-md-4">
				<h4>Já está inscrito?</h4>
				<p>Quer alterar algo na sua inscrição? Preencha esses campos e pronto ;)</p>
				
				<div class="form-horizontal">
					<div class="form-group">
						<label for="emailInscrito" class="col-sm-3 control-label">E-mail *</label>
						<div class="col-sm-9">
							<input type="email" id="emailInscrito" ng-model="inscritoBusca.email" class="form-control" placeholder="email@bem-legal.com" ng-disabled="submetendoDados">
						</div>
					</div>
					
					<div class="form-group">
						<label for="cpfInscrito" class="col-sm-3 control-label">CPF *</label>
						<div class="col-sm-9">
							<input type="text" id="cpfInscrito" ng-model="inscritoBusca.cpf" class="form-control" placeholder="CPF" required ng-disabled="submetendoDados">
						</div>
					</div>
					
					<div class="row">
						<div class="col-sm-9 col-md-offset-3">
							<button type="button" class="btn btn-primary" ng-click="alterar('<fmt:message key="app.urlBase" />')" ng-disabled="submetendoDados">Alterar meus dados</button>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
</body>
</html>