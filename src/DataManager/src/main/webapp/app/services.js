'use strict';

/* Services */


var appServices = angular.module('appServices', []);

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

