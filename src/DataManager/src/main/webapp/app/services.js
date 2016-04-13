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

