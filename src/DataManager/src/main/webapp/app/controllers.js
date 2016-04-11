
'use strict';

/* Controllers */

// ---------
// data-manager
// ---------

var appControllers = angular.module('appControllers', []);

appControllers.controller(
	'MenuCtrl',
	['$scope', 'MenuService', function($scope, MenuService) {

		$scope.setMenu = function(menu) {
			$scope.menu = menu;
		}

		$scope.isActiveMenu = function(menu) {

			return MenuService.isActiveMenu(menu);
		}

	}]
);


appControllers.controller(
	'ItemCtrl',
	['$scope', '$http', 'MenuService', function($scope, $http, MenuService) {
		$http.get('json/item.json').success(function(res) {
			$scope.itemList = res.data;
		});

		MenuService.setCurrentMenu("items");


		$scope.edit = true;

		$scope.item = {};

		// $scope.itemName = '';
		// $scope.itemType = '';
		// $scope.itemOrder = '';

		$scope.findItem = function(itemId) {
			var item = {};

			for (var i = 0; i < $scope.itemList.length; i++) {
				var t = $scope.itemList[i];
				
				if (t.item_id == itemId) {
					item = t;
					break;
				}
			}

			return item;

		};

		$scope.editItem = function(itemId) {
			if (itemId == 'new') {
				$scope.edit = true;
			} else {
				$scope.edit = false;

				$scope.item = $scope.findItem(itemId);

				// ---------
				// return ;

				// var item = $scope.findItem(itemId);

				// if (item.name == null) {
				// 	return ;
				// }

				// $scope.itemName = item.name;
				// $scope.itemType = item.type;
				// $scope.itemOrder = item.order_num;
			}
		};
		

	}]
);

appControllers.controller(
	'RecordCtrl',
	['$scope', '$http', 'MenuService', function($scope, $http, MenuService) {

		$http.get('json/item.json').success(function(res) {
			$scope.itemList = res.data;

			$http.get('json/record.json').success(function(res) {
				// $scope.recordList = res.data;
				var recordList = [];

				var data = res.data;
				for (var i = 0; i < data.length; i++) {

					var r = [];


					for (var j = 0; j < $scope.itemList.length; j++) {
						var itemId = $scope.itemList[j].item_id + ""; 

						r.push(data[i][itemId]);
					}

					console.log(i);

					recordList.push(r);
				}

				$scope.recordList = recordList;
				
			});

		});


		MenuService.setCurrentMenu("records");

	}]
);



appControllers.controller(
	'UploadCtrl',
	['$scope', '$http', 'MenuService', function($scope, $http, MenuService) {
		$http.get('json/item.json').success(function(res) {
			$scope.itemList = res.data;
		});

		MenuService.setCurrentMenu("upload");


		$scope.edit = true;

		$scope.item = {};

		// $scope.itemName = '';
		// $scope.itemType = '';
		// $scope.itemOrder = '';

		$scope.findItem = function(itemId) {
			var item = {};

			for (var i = 0; i < $scope.itemList.length; i++) {
				var t = $scope.itemList[i];
				
				if (t.item_id == itemId) {
					item = t;
					break;
				}
			}

			return item;

		};

		$scope.editItem = function(itemId) {
			if (itemId == 'new') {
				$scope.edit = true;
			} else {
				$scope.edit = false;

				$scope.item = $scope.findItem(itemId);
			}
		};
		

	}]
);
