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
<link rel="stylesheet" href="//ajax.googleapis.com/ajax/libs/angular_material/0.8.2/angular-material.min.css">
<link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Open+Sans:300,400,600">
<link rel="stylesheet" href="<fmt:message key="app.urlBase" />/res/css/app.css">
<script src="//ajax.googleapis.com/ajax/libs/angularjs/1.3.6/angular.min.js"></script>
<script src="//ajax.googleapis.com/ajax/libs/angularjs/1.3.6/angular-animate.min.js"></script>
<script src="//ajax.googleapis.com/ajax/libs/angularjs/1.3.5/angular-aria.min.js"></script>
<script src="//ajax.googleapis.com/ajax/libs/angular_material/0.8.2/angular-material.min.js"></script>
<script src="<fmt:message key="app.urlBase" />/res/js/app.js"></script>
</head>
<body ng-controller="PageController">
	<md-toolbar layout="row">
		<%--
		<button ng-click="toggleSidenav('left')" hide-gt-sm class="menuBtn">
			<span class="visually-hidden"><fmt:message key="website.aria.menu" /></span>
		</button>
		--%>
		<h1><fmt:message key="website.title" /></h1>
	</md-toolbar>
	<md-content md-scroll-y flex class="md-padding">
		<md-whiteframe class="md-whiteframe-z1" layout="column">
			<md-content class="md-padding">
				<h1>Olá!</h1>
				<p>Para se inscrever no Arduino Day 2015, basta preencher este formulário:</p>
			</md-content>
			
			<form name="formInscricao" id="formInscricao" action="<fmt:message key="app.urlBase" />/api/cadastrar" ng-submit="formInscricao.$valid && !submetendoDados && submeter($event)" novalidate>
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
							<md-option value="AMIGO">Fique sabendo por Amigos</md-option>
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
</body>
</html>