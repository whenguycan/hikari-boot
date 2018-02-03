
var app = angular.module('app', []);
app.controller('appController', function($scope, $http){
	$scope.seasons;
	$scope.serials;
	$scope.watches;
	$http.post('anime/conditions', {}).then(function(resp){
		var data = resp.data;
		console.log(data.seasons);
	});
	$scope.animes;
	$scope.pagination;
	$http.post('anime/page', {}).then(function(resp){
		var data = resp.data;
		$scope.animes = data.list;
		$scope.pagination = data.pagination;
	});
});