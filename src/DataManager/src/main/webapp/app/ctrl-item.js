
'use strict';

/* Controllers */

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

