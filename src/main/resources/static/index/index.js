
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
		var all = {};
		all.code = "";
		all.text = "全部";
		$scope.seasons = data.seasons;
		$scope.addFirst($scope.seasons, all);
		$scope.serials = data.serials;
		$scope.addFirst($scope.serials, all);
		$scope.watches = data.watches;
		$scope.addFirst($scope.watches, all);
	});
	$scope.addFirst = function(arr, o){
		var reverse = arr.reverse();
		arr.push(o);
		return reverse.reverse();
	}
	$scope.params = new Array();
	$scope.seasonsClick = function(season){
		$scope.params["season"] = season;
		$scope.reload();
	}
	$scope.animes;
	$scope.pagination;
	$scope.reload = function(){
		var data = $scope.resolveParams($scope.params);
		$http.post('anime/page', data).then(function(resp){
			var data = resp.data;
			$scope.animes = data.list;
			$scope.pagination = data.pagination;
			$scope.pageSize = data.pageSize;
		});
	}
	$scope.resolveParams = function(arr){
		var data = '';
		for(var i in arr){
			data += i + '=' + arr[i] + '&';
		}
		return data;
	}
	$scope.reload();
});