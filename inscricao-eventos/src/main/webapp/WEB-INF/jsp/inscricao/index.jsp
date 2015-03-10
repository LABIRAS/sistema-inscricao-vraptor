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
<script type="text/javascript">var INSCRITO = ${}</script>
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
				<p>Your bones don't break, mine do. That's clear. Your cells react to bacteria and viruses differently than mine. You don't get sick, I do. That's also clear. But for some reason, you and I react the exact same way to water. We swallow it too fast, we choke. We get some in our lungs, we drown. However unreal it may seem, we are connected, you and I. We're on the same curve, just on opposite ends.</p>
			</div>
		</div>
		<div class="row">
			<div class="col-xs-12 col-md-8">
				<div class="well">
					<form name="formInscricao" action="<fmt:message key="app.urlBase" />/cadastrar" method="post" class="form-horizontal">
						<fieldset>
							<legend>Nova Inscrição</legend>
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
								<div class="col-xs-12 col-sm-6">
									<div class="checkbox">
										<h4><label><input type="checkbox" name="i.estudante" ng-model="inscrito.estudante" id="estudante" ng-required="!inscrito.estudante && !inscrito.profissional"> Sou Estudante</label></h4>
									</div>
								</div>
								<div class="col-xs-12 col-sm-6">
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
								<label class="col-sm-3 control-label">Conhece o Arduino? *</label>
								<div class="col-sm-9">
									<div class="checkbox">
										<label><input type="checkbox" name="i.jaConheceArduino" ng-model="inscrito.jaConhecoArduino" id="jaConhecoArduino" ng-change="setJaConheco()"> <strong>Sim</strong>, já conheço o Arduino :)</label>
									</div>
									<div class="checkbox">
										<label><input type="checkbox" name="i.jaUsouArduino" ng-model="inscrito.jaUsouArduino" id="jaUsouArduino" ng-disabled="!inscrito.jaConhecoArduino"> Não só conheço, como já <strong>uso</strong> o Arduino ;D</label>
									</div>
								</div>
							</div>
							
							<div class="row">
								<div class="col-sm-9 col-offset-md-3">
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
			</div>
		</div>
		<h1></h1>
	</div>
	<%--
	<md-toolbar layout="row">
		<h1><fmt:message key="website.title" /></h1>
	</md-toolbar>
	<md-content md-scroll-y flex class="md-padding">
		<md-whiteframe class="md-whiteframe-z1" layout="column">
			<md-content class="md-padding">
				<h1>Olá!</h1>
				<p>Para se inscrever no Arduino Day 2015, basta preencher este formulário:</p>
			</md-content>
			
			<form name="formInscricao" id="formInscricao" data-urlbase="<fmt:message key="app.urlBase" />" action="<fmt:message key="app.urlBase" />/api/cadastrar" ng-submit="formInscricao.$valid && !submetendoDados && submeter($event)" novalidate>
				<md-content class="md-padding" layout="row" layout-sm="column" style="font-size:1.5em;padding-bottom: 0;">
					<md-input-container md-no-float style="padding-bottom:0" flex>
						<label>Seu Nome*</label>
						<input type="text" ng-model="inscrito.nome" required ng-disabled="submetendoDados">
					</md-input-container>
					<md-input-container md-no-float style="padding-bottom:0" flex>
						<label>E-mail*</label>
						<input type="email" ng-model="inscrito.email" required ng-disabled="submetendoDados">
					</md-input-container>
				</md-content>
				
				<md-content class="md-padding" layout="row" layout-sm="column">
					<md-input-container flex>
						<label>CPF*</label>
						<input type="text" ng-model="inscrito.cpf" required ng-disabled="submetendoDados">
					</md-input-container>
					
					<md-input-container flex>
						<label>Idade*</label>
						<input type="number" ng-model="inscrito.idade" min="0" max="150" required ng-disabled="submetendoDados">
					</md-input-container>
					
					<md-input-container flex>
						<label>Cidade*</label>
						<input type="text" ng-model="inscrito.cidade" required ng-disabled="submetendoDados">
					</md-input-container>
					
					<md-input-container flex>
						<md-select ng-model="inscrito.uf" ng-required="true" style="margin-top:25px" ng-disabled="submetendoDados">
							<md-option value="AC">Acre</md-option>
							<md-option value="AL">Alagoas</md-option>
							<md-option value="AM">Amazônia</md-option>
							<md-option value="AP">Amapá</md-option>
							<md-option value="BA">Bahia</md-option>
							<md-option value="CE">Ceará</md-option>
							<md-option value="DF">Distrito Federal</md-option>
							<md-option value="ES">Espírito Santo</md-option>
							<md-option value="GO">Goiás</md-option>
							<md-option value="MA">Maranhão</md-option>
							<md-option value="MG">Minas Gerais</md-option>
							<md-option value="MS">Mato Grosso do Sul</md-option>
							<md-option value="MT">Mato Grosso</md-option>
							<md-option value="PA">Pará</md-option>
							<md-option value="PB">Paraíba</md-option>
							<md-option value="PE">Pernambuco</md-option>
							<md-option value="PI">Piauí</md-option>
							<md-option value="PR">Paraná</md-option>
							<md-option value="RJ">Rio de Janeiro</md-option>
							<md-option value="RN">Rio Grande do Norte</md-option>
							<md-option value="RO">Rondônia</md-option>
							<md-option value="RR">Roraima</md-option>
							<md-option value="RS">Rio Grande do Sul</md-option>
							<md-option value="SC">Santa Catarina</md-option>
							<md-option value="SE">Sergipe</md-option>
							<md-option value="SP">São Paulo</md-option>
							<md-option value="TO">Tocantins</md-option>
						</md-select>
					</md-input-container>
				</md-content>
				
				<hr size="1" color="#f5f5f5" noshade />
				
				<md-content class="md-padding" layout="row" layout-sm="column">
					<md-content flex layout="column">
						<md-input-container style="padding-bottom:0">
							<md-checkbox ng-model="inscrito.estudante" style="margin-top:0;margin-bottom:0" aria-label="Sou Estudante" ng-disabled="submetendoDados">Sou um Estudante</md-checkbox>
						</md-input-container>
						
						<md-content flex layout="row" layout-sm="column">
							<md-input-container flex>
								<label>Qual seu Curso?*</label>
								<input type="text" ng-model="inscrito.curso" ng-required="inscrito.estudante" ng-disabled="!inscrito.estudante || submetendoDados">
							</md-input-container>
							
							<md-input-container flex>
								<md-select ng-model="inscrito.nivelGraduacao" style="margin-top:25px" ng-disabled="submetendoDados">
									<md-option value="FUNDAMENTAL_INCOMPLETO">Ensino Fundamental Incompleto</md-option>
									<md-option value="FUNDAMENTAL_COMPLETO">Ensino Fundamental Completo</md-option>
									<md-option value="MEDIO_INCOMPLETO">Ensino Médio Incompleto</md-option>
									<md-option value="MEDIO_COMPLETO">Ensino Médio Completo</md-option>
									<md-option value="TECNICO">Técnico</md-option>
									<md-option value="LICENCIATURA">Licenciatura</md-option>
									<md-option value="TECNOLOGO">Tecnólogo</md-option>
									<md-option value="BACHAREL">Bacharel</md-option>
									<md-option value="ESPECIALIZACAO">Especialização</md-option>
									<md-option value="MESTRADO">Mestrado</md-option>
									<md-option value="DOUTORADO">Doutorado</md-option>
									<md-option value="POS_DOUTORADO">Pós-Doutorado</md-option>
								</md-select>
							</md-input-container>
						</md-content>
						
						<md-content flex layout="row" layout-sm="column">
							<md-input-container flex>
								<label>Em que Instituição de Ensino você está hoje?*</label>
								<input type="text" ng-model="inscrito.ultimaInstituicao" ng-required="inscrito.estudante" ng-disabled="!inscrito.estudante || submetendoDados">
							</md-input-container>
						</md-content>
					</md-content>
					
					<md-content flex layout="column">
						<md-input-container style="padding-bottom:0">
							<md-checkbox ng-model="inscrito.profissional" style="margin-top:0;margin-bottom:0" aria-label="Sou um Profissional" ng-disabled="submetendoDados">Sou um Profissional</md-checkbox>
						</md-input-container>
						<md-content flex layout="row" layout-sm="column">
							<md-input-container flex>
								<label>Empresa*</label>
								<input type="text" ng-model="inscrito.empresa" ng-required="inscrito.profissional" ng-disabled="!inscrito.profissional || submetendoDados">
							</md-input-container>
						</md-content>
						<md-content flex layout="row" layout-sm="column"></md-content>
					</md-content>
				</md-content>
				
				<hr size="1" color="#f5f5f5" noshade />
				
				<md-content class="md-padding" layout="row" layout-sm="column">
					<md-input-container flex>
						<p>Como você descobriu sobre o Arduino Day 2015?</p>
						<md-select ng-model="inscrito.comoFicouSabendoDoEvento" ng-disabled="submetendoDados">
							<md-option value="NENHUM">Selecione...</md-option>
							<md-option value="SITE_EVENTO">Site do Evento</md-option>
							<md-option value="FACEBOOK">Post no Facebook</md-option>
							<md-option value="TWITTER">Twitter</md-option>
							<md-option value="OUTDOOR">Outdoor</md-option>
							<md-option value="AMIGO">Fiquei sabendo por Amigos</md-option>
							<md-option value="MATERIA_INTERNET">Matéria em Portal de Notícias</md-option>
							<md-option value="MATERIA_JORNAL">Matéria em Jornal Impresso</md-option>
							<md-option value="MATERIA_TV">TV</md-option>
							<md-option value="OUTROS">Outras</md-option>
						</md-select>
					</md-input-container>
					
					<md-input-container flex>
						<md-checkbox ng-model="inscrito.jaConheceArduino" aria-label="Já conheço Arduino" ng-disabled="submetendoDados">Já <strong>conheço</strong> o Arduino</md-checkbox>
					</md-input-container>
					
					<md-input-container flex>
						<md-checkbox ng-model="inscrito.jaUsouArduino" aria-label="Já usei Arduino" ng-disabled="submetendoDados">Já <strong>usei</strong> o Arduino</md-checkbox>
					</md-input-container>
				</md-content>
				
				<md-content class="md-padding" layout="row" layout-sm="column">
					<md-button type="submit" class="md-raised" ng-disabled="formInscricao.$invalid || submetendoDados" ng-click="formInscricao.$valid && submeter($event)">Efetuar Inscrição!</md-button>
				</md-content>
			</form>
		</md-whiteframe>
	</md-content>
	--%>
</body>
</html>