
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
	['$scope', '$http', 'MenuService', 'ItemService', function($scope, $http, MenuService, ItemService) {

		// $http.get('json/item.json').success(function(res) {
		// 	$scope.itemList = res.data;
		// });

		MenuService.setCurrentMenu("items");


		$scope.itemList = ItemService.query();

		$scope.edit = true;

		// 默认的值
		$scope.item = {
			name: '',
			type: '字符串',
			other_name: '',
			validate: '',
			order_num: 1, 
			maxlength: 20
		};

		$scope.findItemIndex = function(itemId) {
			var itemIndex = -1;

			for (var i = 0; i < $scope.itemList.length; i++) {
				var t = $scope.itemList[i];
				
				if (t.item_id == itemId) {
					itemIndex = i;
					break;
				}
			}

			return itemIndex;
		};

		$scope.findItem = function(itemId) {
			var itemIndex = $scope.findItemIndex(itemId);

			var item = {};

			if (itemIndex >= 0 && itemIndex < $scope.itemList.length) {
				item = $scope.itemList[itemIndex];
			}

			return item;
		};

		$scope.editItem = function(itemId) {
			if (itemId == 'new') {
				$scope.edit = true;
			} else {
				$scope.edit = false;

				// $scope.item = ItemService.get({item_id: itemId});
				$scope.item = $scope.findItem(itemId);
			}
		};

		$scope.removeItem = function(itemId) {
			$scope.item.item_id = itemId;

			ItemService.remove({itemId: itemId}, $scope.item);

			var itemIndex = $scope.findItemIndex(itemId);

			if (itemIndex >= 0 && itemIndex < $scope.itemList.length) {
				$scope.itemList.splice(itemIndex, 1);

				console.log('remove data');
			}

		};
		
		$scope.saveItem = function() {
			var itemId = $scope.item.item_id;

			var state = true;

			if (!$scope.edit) {
				ItemService.update({itemId: itemId}, $scope.item);
			} else {
				ItemService.save($scope.item);

				var item = angular.copy($scope.item);

				$scope.itemList.push(item);
			}

			$('#itemModal').modal('hide');


			// if (state) {
			// 	// var itemIndex = $scope.findItemIndex(itemId);

			// 	// if (itemIndex >= 0 && itemIndex < $scope.itemList.length) {
			// 	// 	angular.copy($scope.itemList[itemIndex], $scope.item);
			// 	// }

			// 	$('#itemModal').modal('hide');
			// } else {
			// 	alert('保存失败!');
			// }
			
		};
		

	}]
);

appControllers.controller(
	'RecordCtrl',
	['$scope', '$http', 'MenuService', 'RecordService', function($scope, $http, MenuService, RecordService) {

		MenuService.setCurrentMenu("records");

		$scope.findRecordIndex = function(recordId) {
			var recordIndex = -1;

			for (var i = 0; i < $scope.recordList.length; i++) {
				var t = $scope.recordList[i];
				
				if (t.record_id == recordId) {
					recordIndex = i;
					break;
				}
			}

			return recordIndex;
		};

		$http.get('item/item').success(function(res) {
			
			$scope.itemList = res.data;

			$http.get('record/record').success(function(res) {
				// $scope.recordList = res.data;
				var recordList = [];

				var data = res.data;
				for (var i = 0; i < data.length; i++) {

					var r = [];

					for (var j = 0; j < $scope.itemList.length; j++) {
						var itemId = $scope.itemList[j].item_id + ""; 

						r.push(data[i][itemId]);
					}

					recordList.push(r);
				}

				$scope.recordList = recordList;
			});
		});

		$scope.removeRecord = function(recordId) {
			var record = {};


			console.log('recordId: ' + recordId);


			record.record_id = recordId;

			RecordService.remove({recordId: recordId}, record);

			var recordIndex = $scope.findRecordIndex(recordId);

			if (recordIndex >= 0 && recordIndex < $scope.recordList.length) {
				$scope.recordList.splice(recordIndex, 1);

				console.log('remove data');
			}

		};
		


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
