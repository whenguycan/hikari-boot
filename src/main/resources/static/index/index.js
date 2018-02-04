
var app = angular.module('app', [], function($httpProvider){
	$httpProvider.defaults.headers.post['Content-Type'] = 'application/x-www-form-urlencoded;charset=utf-8';
});
app.controller('appController', function($scope, $http){
	$scope.pageNo = 1;
	$scope.pageSize = 20;
	$scope.head = 1;
	$scope.tail = 1;
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
	$scope.params['season'] = '';
	$scope.params['serials'] = '';
	$scope.params['watches'] = '';
	$scope.params["pageSize"] = $scope.pageSize;
	$scope.seasonsClick = function(season){
		$scope.params['season'] = season;
		$scope.reload();
	}
	$scope.serialsClick = function(serials){
		$scope.params['serials'] = serials;
		$scope.reload();
	}
	$scope.watchesClick = function(watches){
		$scope.params['watches'] = watches;
		$scope.reload();
	}
	$scope.animes;
	$scope.pagination;
	$scope.url = 'anime/page';
	$scope.reload = function(){
		var data = $scope.resolveParams($scope.params);
		$http.post($scope.url, data).then(function(resp){
			var data = resp.data;
			$scope.tail = data.pageCount;
			$scope.animes = data.list;
			$scope.pagination = data.pagination;
			$scope.pageSize = data.pageSize;
		});
	}
	$scope.go = function(pn){
		if(!isNaN(pn)){
			$scope.pageNo = pn;
			var arr = $scope.url.split('/');
			$scope.url = arr[0] + '/' + arr[1] + '/' + pn;
			$scope.reload();
		}else{
			console.log(pn + 'is not number');
		}
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