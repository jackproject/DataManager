
'use strict';

/* Controllers */


// ---------
// Application 
// ---------


var applicationControllers = angular.module('applicationControllers', []);

applicationControllers.controller(
	'ApplicationCtrl',
	['$scope', '$http', 'AuthService', function ($scope, $http, AuthService) {

		$scope.currentUser = AuthService.loadUser();
		$scope.isAuthorized = AuthService.isAuthorized;

		$scope.setCurrentUser = function (user) {
			$scope.currentUser = user;

			AuthService.setCurrentUser(user);
		};
	}]
);



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
		}

		$scope.assembleRecordList = function(data) {

			var recordList = [];


			if (!data || !data.length ) {
				return recordList;
			}

			for (var i = 0; i < data.length; i++) {

				var r = [];

				for (var j = 0; j < $scope.itemList.length; j++) {
					var itemId = $scope.itemList[j].item_id + ""; 

					r.push(data[i][itemId]);
				}

				recordList.push(r);
			}

			return recordList;
		}

		$scope.viewAll = function() {
			$scope.pick = null;

			var url = '';

			// url = 'json/record.json';
			url = 'record/record'

			$http.get(url).success(function(res) {
				$scope.recordList = $scope.assembleRecordList(res.data);
			});

		}

		$http.get('item/item').success(function(res) {
			
			$scope.itemList = res.data;

			$scope.viewAll();

			var url = '';

			// 加载筛选信息
			// url = 'json/pick.json';
			url = 'pick/pick';
			$http.get(url).success(function(res) {
				$scope.pickList = res;				
			});


		});

		$scope.viewByPick = function(pickId) {
			var url = '';
			url = 'record/pickrecord/' + pickId;
			$http.get(url).success(function(res) {
				$scope.recordList = $scope.assembleRecordList(res.data);
			});

		}


		// ---------

		$scope.findItemNameByItemId = function(itemId) {
			var name = '';
			for (var i = 0; i < $scope.itemList.length; i++) {
				var item = $scope.itemList[i];
				if (item.item_id == itemId) {
					name = item.name;
					break;
				}
			}

			return name;
		}


		$scope.openPick = function(pick) {
			$scope.pick = pick;

			$scope.viewByPick(pick.pick_id);
		}

		$scope.currentPick = {
		};

		$scope.savePick = function(pick) {

			var url = '';
			url = 'pick/pick';
			$http.post(url, pick).success(function(res) {
				$scope.pickList = res;				
			});
		}

		$scope.removePick = function(pickId) {

			var url = '';
			url = 'pick/pick/' + pickId;
			$http.post(url).success(function(res) {
				$scope.pickList = res;				
			});


			console.log($scope.pickItem);
		}


		// ---------


		$scope.currentPickItem = {
		};

		$scope.savePickItem = function(pickItem) {

			var url = '';
			url = 'pick/pickitem/' + $scope.pick.pick_id;
			$http.post(url, pickItem).success(function(res) {
				$scope.pick.pick_item = res;				
			});
		}


		$scope.removePickItem = function(pickItemId) {

			var url = '';
			url = 'pick/pickitem/' + $scope.pick.pick_id + "/" + pickItemId;
			$http.post(url).success(function(res) {
				$scope.pick.pick_item = res;				
			});
		}

		// ---------



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
	['$scope', '$http', 'FileUploader', 'MenuService', function($scope, $http, FileUploader, MenuService) {

		MenuService.setCurrentMenu("upload");

		var url = "file/upload";

		$scope.uploader = new FileUploader({
			url: url
		});

		$scope.uploader.onSuccessItem = function(fileItem, response, status, headers) {
			$scope.uploadList = response.data;

			if (response.success) {
				alert('上传成功!');
			} else {
				alert('上传失败!');
			}
		};

		$scope.param = {
			itemNameRow: 2
		};
	}]
);



// ---------
// login
// ---------

appControllers.controller(
	'LoginCtrl',
	['$scope', '$rootScope', '$state', 'AUTH_EVENTS', 'AuthService',
	 function ($scope, $rootScope, $state, AUTH_EVENTS, AuthService) {

		 $scope.setCurrentUser(null);

		 $scope.credentials = {
			 username: '',
			 password: ''
		 };

		 $scope.verifycode = "";

		 $scope.refreshVerifycode = function () {

			 var verifycode="";
			 verifycode += Math.floor(Math.random()*9) + 1;
			 for (var i = 1; i < 5; i++) {
				 verifycode += Math.floor(Math.random()*10);
			 }
			 console.log(verifycode);

			 $scope.verifycode = verifycode;

		 }

		 $scope.refreshVerifycode();

		 $scope.login = function (credentials) {

			 AuthService.login(credentials).then(function (user) {

				 if (user) {
					 $rootScope.$broadcast(AUTH_EVENTS.loginSuccess);
					 $scope.setCurrentUser(user);

					 $state.go('records', {}, {reload: true});
				 } else {
					 $('#mod-login').modal('show');
				 }

				 // window.location.href = 'index.html#/general';
				 // $window.location.href = '/general';
				 // $location.path('/general');

			 }, function () {
				 $rootScope.$broadcast(AUTH_EVENTS.loginFailed);
			 });

		 }

	 }]
);

