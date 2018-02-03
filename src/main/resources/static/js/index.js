
var app = angular.module('app', [], function($httpProvider){
	$httpProvider.defaults.headers.post['Content-Type'] = 'application/x-www-form-urlencoded;charset=utf-8';
});
app.controller('appController', function($scope, $http){
	$scope.pageSize = 20;
	$scope.seasons;
	$scope.serials;
	$scope.watches;
	$http.post('anime/conditions', {}).then(function(resp){
		var data = resp.data;
		$scope.seasons = data.seasons;
		$scope.serials = data.serials;
		$scope.watches = data.watches;
	});
	$scope.animes;
	$scope.pagination;
	$http.post('anime/page', 'pageSize=' + $scope.pageSize).then(function(resp){
		var data = resp.data;
		$scope.animes = data.list;
		$scope.pagination = data.pagination;
		$scope.pageSize = data.pageSize;
	});
});