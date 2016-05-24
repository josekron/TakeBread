app.controller("CtrlBread", function($scope, $http, $location, $window, $mdToast, $document, ngNotify) {

	var x = location.origin;
	$scope.currentDay = "";//"Monday, 11 January";
	$scope.breadResume = "";//"3 normal bread and 1 wholemeal bread"
	$scope.user = {isBread:true, breadType:"NORMAL", userCode: ""}
	
	updateCurrentDay();
	updateBread();	
	 
	$scope.sendBread = function(user) {
		if (user == undefined || user.userCode == undefined || user.userCode == ''){
			ngNotify.config({
			    theme: 'pitchy',
			    position: 'bottom',
			    duration: 3000,
			    type: 'warn',
			    sticky: false,
			    button: true,
			    html: false
			});
			ngNotify.set("Please, fill all fields.");
		}else{
			var json = angular.toJson(user);
			$http({
			    method: 'PUT',
			    url: x + '/TakeBread/user/',
			    data: json
			})
			.success (function(data) {
				updateBread();
				var message = "Bread has been sent"
				ngNotify.config({
				    theme: 'pitchy',
				    position: 'bottom',
				    duration: 3000,
				    type: 'success',
				    sticky: false,
				    button: true,
				    html: false
				});
				ngNotify.set(message);
			})
			.error (function(response, status, headers, config) {
				var message;
				ngNotify.config({
				    theme: 'pitchy',
				    position: 'bottom',
				    duration: 3000,
				    type: 'error',
				    sticky: false,
				    button: true,
				    html: false
				});
				
				if(status == 400)
					message = "Fields incorrects.";
				if(status == 401 || status == 404)
					message = "User not found with this userCode";
				if(status == 500)
					message = "Opss! Something has been failed.";
					
				ngNotify.set(message);
			});		
		}
	 };
	 
	 function updateBread() {
		 $http({
			    method: 'GET',
			    url: x + '/TakeBread/user/resume',
			})
			.success (function(data) {
				$scope.breadResume = data;
			})
			.error (function(response, status, headers, config) {
				var message;
				ngNotify.config({
				    theme: 'pitchy',
				    position: 'bottom',
				    duration: 3000,
				    type: 'error',
				    sticky: false,
				    button: true,
				    html: false
				});
				
				if(status == 400)
					message = "Fields incorrects.";
				if(status == 401 || status == 404)
					message = "User not found with this userCode";
				if(status == 500)
					message = "Opss! Something has been failed.";
					
				ngNotify.set(message);
			});	
	 };
	 
	 function updateCurrentDay() {
		 $http({
			    method: 'GET',
			    url: x + '/TakeBread/CurrentDayServlet',
			})
			.success (function(data) {
				$scope.currentDay = data;
			})
			.error (function(response, status, headers, config) {
				var message;
				ngNotify.config({
				    theme: 'pitchy',
				    position: 'bottom',
				    duration: 3000,
				    type: 'error',
				    sticky: false,
				    button: true,
				    html: false
				});
				
				if(status == 400)
					message = "Fields incorrects.";
				if(status == 401 || status == 404)
					message = "User not found with this userCode";
				if(status == 500)
					message = "Opss! Something has been failed.";
					
				ngNotify.set(message);
			});
		
	 };
});