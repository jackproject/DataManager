
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
	'UploadCtrl',
	['$scope', '$http', '$cookieStore', 'FileUploader', 'MenuService', function($scope, $http, $cookieStore, FileUploader, MenuService) {

		MenuService.setCurrentMenu("upload");

		var url = "file/upload";

		$scope.uploader = new FileUploader({
			url: url,
			queueLimit: 1,   //文件个数 
			removeAfterUpload: true  //上传后删除文件
		});

        $scope.uploader.onBeforeUploadItem = function(item) {
			// 清空日志提示
			$scope.uploadList = [];

			var url = '';
			url = 'file/config';

			var param = {};

			param.itemNameRow = $scope.param.itemNameRow;
			
			$http.post(url, param).success(function(res) {
				var expireDate = new Date();
				expireDate.setDate(expireDate.getDate() + 10000);

				$cookieStore.put("itemNameRow", param.itemNameRow, {'expires': expireDate});
			});
        };


		$scope.uploader.onSuccessItem = function(fileItem, response, status, headers) {
			$scope.uploadList = response.data;

			if (response.success) {
				$scope.logUrl = "upload/" + response.message;

				alert('上传成功!');
			} else {
				alert('上传失败!');
			}
		};


		$scope.downloadLog = function(logUrl) {
			window.open(logUrl);
		}

		$scope.uploadList = [];
		$scope.logUrl = null;

		$scope.param = {
			itemNameRow: 2
		};


		var itemNameRow = $cookieStore.get("itemNameRow");
		if (itemNameRow) {
			$scope.param.itemNameRow = itemNameRow;
		}

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

