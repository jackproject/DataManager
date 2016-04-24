'use strict';

/* Services */


var appServices = angular.module('appServices', ['ngResource']);

appServices.factory(
	'MenuService',
	[function() {

		var menuService = {};


		// 默认的首页
		menuService.currentMenu = 'records';

		menuService.setCurrentMenu = function(menu) {
			this.currentMenu = menu;
		}

		menuService.isActiveMenu = function (menu) {
			return menu == this.currentMenu;
		}
		

		return menuService;
	}]
);


// ---------
// item service
// ---------


appServices.factory(
	'ItemService',
	['$resource', function($resource) {
		var url = "item/item/:itemId";

		return $resource(url, {}, {
			query: {
				method:'GET',
				isArray: true,
				transformResponse: function(data, headers){
					var root = JSON.parse(data);

					if (!root.success) {
						return null;
					}

					return root.data;
				}
			},
			save: {
				method:'POST',
				isArray: true
			},
			remove: {
				method:'POST',
				isArray: true,
				transformResponse: function(data, headers){
					var root = JSON.parse(data);

					return root.success;
				}
			},
			update: {
				method:'PUT',
				isArray: true,
				transformResponse: function(data, headers){
					var root = JSON.parse(data);

					return root.success;
				}
			}
		});
	}]
);



// ---------
// record service
// ---------


appServices.factory(
	'RecordService',
	['$resource', function($resource) {
		var url = "record/record/:recordId";

		return $resource(url, {}, {
			query: {
				method:'GET',
				isArray: true,
				transformResponse: function(data, headers){
					var root = JSON.parse(data);

					if (!root.success) {
						return null;
					}

					return root.data;
				}
			},
			save: {
				method:'POST',
				isArray: true
			},
			remove: {
				method:'POST',
				isArray: true,
				transformResponse: function(data, headers){
					var root = JSON.parse(data);

					return root.success;
				}
			},
			update: {
				method:'PUT',
				isArray: true,
				transformResponse: function(data, headers){
					var root = JSON.parse(data);

					return root.success;
				}
			}
		});
	}]
);




// ---------
// login service
// ---------


appServices.factory(
	'AuthService',
	['$http', 'Session', '$cookieStore', function ($http, Session, $cookieStore) {
		var authService = {};

		authService.login = function (credentials) {

			var url = '';
			url = 'user/login';

			return $http
				.post(url, credentials)
				.then(function (res) {

					var user = null;

					if (res.data && res.data.data != null) {

						user = {};

						user.username = res.data.data.username;
						user.password = res.data.data.password;
						user.token = user.username;
					}
					return user;
				});
		};

		authService.setCurrentUser = function (user) {
			// 保存cookie
			var expireDate = new Date();
			expireDate.setDate(expireDate.getDate() + 1);
			$cookieStore.put("currentUser", user, {'expires': expireDate});

			if (user) {
				Session.create(
					user.username,
					user.password,
					user.token
				);

			} else {
				Session.destroy();
			}


		};

		authService.loadUser = function () {

			var user = $cookieStore.get("currentUser");

			if (user) {
				Session.create(
					user.username,
					user.password,
					user.token
				);
			}

			return user;
		};

		authService.isAuthenticated = function () {
			return !!Session.username;
		};

		authService.isAuthorized = function (authorizedRoles) {
			if (!angular.isArray(authorizedRoles)) {
				authorizedRoles = [authorizedRoles];
			}
			return (authService.isAuthenticated() &&
					authorizedRoles.indexOf(Session.userRole) !== -1);
		};

		return authService;
	}]
);



appServices.service(
	'Session',
	[function () {

		this.create = function (username, password, token) {
			this.username = username;
			this.password = password;
			this.token = token;
		};

		this.destroy = function () {
			this.username = null;
			this.password = null;
			this.token = null;
		};

		return this;
	}]
);

