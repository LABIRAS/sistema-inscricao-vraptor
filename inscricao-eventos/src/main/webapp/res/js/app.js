(function (angular, $, window) {
	function buildParam(name, value) {
		if (value) {
			if (angular.isArray(value)) {
				for (var i = 0; i < value.length; i++) {
					if (angular.isObject(value[i])) {
						for (var attr in value[i]) {
							if (attr.indexOf("$") < 0 && !angular.isObject(value[i][attr])) {
								param = param + (i > 0 ? "&" : "") + buildParam(name + "[]." + attr, value[i]);
							}
						}
					}
					else {
						param = param + (i > 0 ? "&" : "") + buildParam(name + "[]", value[i]);
					}
				}
			}
			else if (angular.isDate(value)) {
				return encodeURIComponent(name) + "=" + encodeURIComponent(value.getDate() + "/" + (value.getMonth() + 1) + "/" + value.getFullYear());
			}
			else {
				return encodeURIComponent(name) + "=" + encodeURIComponent(value);
			}
		}
		
		return "";
	};
	
	function buildFormData(obj, prefix) {
		var formData = "";
		
		if (angular.isElement(obj) && obj.tagName === "FORM") {
			var elements = obj.getElementsByTagName("input"), element;
			for (var i = 0; i < elements.length; i++) {
				element = elements[i];
				if (!element.disabled) {
					if (element.type === "checkbox" || element.type === "radio") {
						if (element.checked) {
							formData = formData + "&" + buildParam(element.name, element.value);
						}
					}
					else {
						formData = formData + "&" + buildParam(element.name, element.value);
					}
				}
			}
			
			elements = obj.getElementsByTagName("select");
			for (var i = 0; i < elements.length; i++) {
				element = elements[i];
				if (!element.disabled) {
					formData = formData + "&" + buildParam(element.name, element.options[element.selectedIndex].value || element.options[element.selectedIndex].text);
				}
			}
			
			elements = obj.getElementsByTagName("textarea");
			for (var i = 0; i < elements.length; i++) {
				element = elements[i];
				if (!element.disabled) {
					formData = formData + "&" + buildParam(element.name, element.value);
				}
			}
		}
		else if (angular.isArray(obj)) {
			var currentPrefix = prefix || "";
			for (var i = 0, max = obj.length; i < max; i++) {
				formData = formData + "&" + buildFormData(obj[i], currentPrefix[currentPrefix.length - 1] == "." ? (currentPrefix.substring(0, currentPrefix.length - 1) + "[" + i +"].") : currentPrefix);
			}
		}
		else {
			var currentPrefix = prefix || "";
			
			for (var attr in obj) {
				// Ignore angular "private" stuff (starts with "$" or "$$")
				if (attr.indexOf("$") < 0) {
					if (angular.isObject(obj[attr])) {
						formData = formData + "&" + buildFormData(obj[attr], currentPrefix + attr + ".");
					}
					else {
						formData = formData + "&" + buildParam(currentPrefix + attr, obj[attr]);
					}
				}
			}
		}
		
		return formData.length > 1 ? formData.substring(1) : formData;
	};
	
	var app = angular.module("inscricaoLabirasApp", []);
	
	app.controller("PageController", ["$scope", "$http", function ($scope, $http) {
		var inscrito = { uf: "PI", nivelGraduacao: "MEDIO_INCOMPLETO", comoFicouSabendoDoEvento: "NENHUM" };
		var inscritoBusca = { email: "", cpf: "" };
		$scope.inscrito = inscrito;
		$scope.inscritoBusca = inscritoBusca;
		$scope.acaoSubmit = "cadastrar";
		$scope.submetendoDados = false;
		
		$scope.jaConheco = 0;
		$scope.setJaConheco = function () {
			document.getElementById("i_jaConheceArduino").value = "";
			document.getElementById("i_jaUsouArduino").value = "";
			
			if ($scope.jaConheco == 1) {
				inscrito.jaConhecoArduino = true;
				document.getElementById("i_jaConheceArduino").value = "true";
			}
			if ($scope.jaConheco == 2) {
				inscrito.jaConheceArduino = true;
				inscrito.jaUsouArduino = true;
				document.getElementById("i_jaConheceArduino").value = "true";
				document.getElementById("i_jaUsouArduino").value = "true";
			}
		};
		
		$scope.alterar = function (base) {
			if (inscritoBusca.email && inscritoBusca.cpf) {
				$scope.submetendoDados = true;
				$http({
					method: "post",
					url: base + "/api/inscrito",
					data: buildFormData(inscritoBusca) + "&ajax=1",
					cache: false,
					headers: { "Content-Type": "application/x-www-form-urlencoded; charset=UTF-8" }
				}).success(function (data) {
					if (data && data.id) {
						inscrito = data;
						document.getElementById("i_id").value = data.id;
						$scope.jaConheco = 0;
						if (inscrito.jaConheceArduino) {
							$scope.jaConheco++;
							document.getElementById("i_jaConheceArduino").value = "true";
						}
						else { document.getElementById("i_jaConheceArduino").value = ""; }
						if (inscrito.jaUsouArduino) {
							$scope.jaConheco++;
							document.getElementById("i_jaUsouArduino").value = "true";
						}
						else { document.getElementById("i_jaUsouArduino").value = ""; }
						$scope.inscrito = inscrito;
						$scope.acaoSubmit = "alterar";
						$scope.submetendoDados = false;
					}
					else {
						$scope.submetendoDados = false;
						setTimeout(function () { alert("Não foi possível verificar suas informações neste momento. Aguarde alguns instantes e tente novamente.") }, 10);
					}
				}).error(function () {
					$scope.submetendoDados = false;
					setTimeout(function () { alert("Não foi possível verificar suas informações neste momento. Aguarde alguns instantes e tente novamente.") }, 10);
				});
			}
			else {
				alert("Preencha ambos campos antes de alterar seus dados");
			}
		};
	}]);
})(angular, window.jQuery ? jQuery : null, window);