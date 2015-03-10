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
	
	var app = angular.module("inscricaoLabirasApp", ["ngMaterial"]);
	
	app.controller("PageController", ["$scope", "$http", "$mdToast", function ($scope, $http, $mdToast) {
		$scope.inscrito = { uf: "PI", nivelGraduacao: "MEDIO_INCOMPLETO", comoFicouSabendoDoEvento: "NENHUM" };
		
		$scope.submeter = function (evt) {
			if (evt) { evt.preventDefault(); }
			$http({
				method: "post",
				url: document.forms[0].action,
				data: buildFormData({ i: $scope.inscrito }),
				cache: false,
				headers: { "Content-Type": "application/x-www-form-urlencoded; charset=UTF-8" }
			}).success(function (data) {
				if (data.success) {
					
				}
				else {
					for (var i = 0; i < data.errors.length; i++) {
						$mdToast.show($mdToast.simple().content(data.errors[i]).position("top left").hideDelay(10000));
					}
				}
			}).error(function (response) {
				console.log(arguments);
				$mdToast.show($mdToast.simple().content("Não foi possível efetuar sua inscrição neste momento. Por favor, tente novamente mais tarde!").position("top left").hideDelay(10000));
			});
		};
	}]);
})(angular, window.jQuery ? jQuery : null, window);