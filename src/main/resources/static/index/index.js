var app = angular.module('app', [], function($httpProvider) {
	$httpProvider.defaults.headers.post['Content-Type'] = 'application/x-www-form-urlencoded;charset=utf-8';
});
app.controller('appController', function($scope, $http) {
	$scope.pageNo = 1;
	$scope.pageSize = 24;
	$scope.head = 1;
	$scope.tail = 1;
	$scope.seasons;
	$scope.serials;
	$scope.seasonsSelect;
	$scope.serialsSelect;
	$scope.watches;
	$scope.searchText = '';
	$scope.search = function() {
		$scope.params['sa_like_s_name'] = $scope.searchText;
		$scope.go(1);
	}
	$scope.reset0 = function() {
		$scope.searchText = '';
		$scope.params['sa_eq_i_serialState'] = '';
		$scope.params['sa_eq_i_watchState'] = '';
		$scope.params['sa_like_s_name'] = '';
		$scope.go(1);
	}
	$scope.pressEnter = function(e) {
		if(e.keyCode == 13){
			$scope.params['sa_like_s_name'] = $scope.searchText;
			$scope.go(1);
		}
	}
	$http.post('anime/conditions').then(function(resp) {
		var data = resp.data;
		var all = {};
		all.code = '';
		all.text = '全部';
		$scope.seasons = new Array();
		$scope.copy(data.seasons, $scope.seasons);
		$scope.serials = new Array();
		$scope.copy(data.serials, $scope.serials);
		$scope.serials.unshift(all);
		$scope.watches = new Array();
		$scope.copy(data.watches, $scope.watches);
		$scope.watches.unshift(all);
		$scope.seasonsSelect = new Array();
		$scope.copy(data.seasons, $scope.seasonsSelect);
		$scope.serialsSelect = new Array();
		$scope.copy(data.serials, $scope.serialsSelect);
	});
	$scope.copy = function(source, target){
		for(var i in source){
			target[i] = source[i];
		}
	}
	$scope.params = new Array();
	$scope.params['season'] = '';
	$scope.params['sa_eq_i_serialState'] = '';
	$scope.params['sa_eq_i_watchState'] = '';
	$scope.params['sa_like_s_name'] = '';
	$scope.params["pageSize"] = $scope.pageSize;
	$scope.seasonsClick = function(season) {
		$scope.params['season'] = season;
		$scope.reload();
	}
	$scope.serialsClick = function(serials) {
		$scope.params['sa_eq_i_serialState'] = serials;
		$scope.go(1);
	}
	$scope.watchesClick = function(watches) {
		$scope.params['sa_eq_i_watchState'] = watches;
		$scope.go(1);
	}
	$scope.animes;
	$scope.pagination;
	$scope.url = 'anime/page';
	$scope.reload = function() {
		var data = $scope.resolveParams($scope.params);
		$http.post($scope.url, data).then(function(resp) {
			var data = resp.data;
			$scope.tail = data.pageCount;
			$scope.animes = data.list;
			$scope.pagination = data.pagination;
			$scope.pageSize = data.pageSize;
		});
	}
	$scope.go = function(pn) {
		if (!isNaN(pn)) {
			$scope.pageNo = pn;
			var arr = $scope.url.split('/');
			$scope.url = arr[0] + '/' + arr[1] + '/' + pn;
			$scope.reload();
		} else {
			console.log(pn + 'is not number');
		}
	}
	$scope.resolveParams = function(arr) {
		var data = '';
		for ( var i in arr) {
			data += i + '=' + arr[i] + '&';
		}
		return data;
	}
	$scope.seasonSelected = '';
	$scope.serialSelected = '';
	$scope.shadowShow = false;
	$scope.editShow = false;
	$scope.entity = new Array();
	$scope.edit = function(id){
		var url = '/anime/detail/' + id;
		$http.post(url).then(function(resp){
			var data = resp.data;
			$scope.entity.id = data.id
			$scope.entity.name = data.name;
			$scope.entity.curr = data.curr;
			$scope.entity.total = data.total;
			$scope.entity.link = data.link;
			$scope.seasons.forEach(function(i){
				if(i.code == data['season']){
					$scope.seasonSelected = i;
				}
			});
			$scope.serials.forEach(function(i){
				if(i.code == data['serialState']){
					$scope.serialSelected = i;
				}
			});
			$scope.shadowShow = true;
			$scope.editShow = true;
		});
	}
	$scope.editDo = function(){
		$scope.entity['season'] = $scope.seasonSelected == null ? '' : $scope.seasonSelected['code'];
		$scope.entity['serialState'] = $scope.serialSelected == null ? '' : $scope.serialSelected['code'];
		var url = 'anime/edit';
		$http.post(url, $scope.resolveParams($scope.entity)).then(function(resp){
			$scope.reload();
		});
		$scope.shadowShow = false;
		$scope.editShow = false;
	}
	$scope.editCancel = function(){
		$scope.shadowShow = false;
		$scope.editShow = false;
	}
	$scope.add = function(){
		$scope.entity = new Array();
		$scope.shadowShow = true;
		$scope.editShow = true;
	}
	$scope.reload();
});