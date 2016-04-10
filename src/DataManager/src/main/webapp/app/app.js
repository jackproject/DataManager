

'use strict';

/* App Module */

var app = angular.module('app', [
	'ngRoute',
	'appServices',
	'appControllers'
]);


app.config([
	'$routeProvider',
	function($routeProvider) {
		$routeProvider.
			when('/records', {
				templateUrl: 'partials/records.html',
				controller: 'RecordCtrl'
			}).
			when('/items', {
				templateUrl: 'partials/items.html',
				controller: 'ItemCtrl'
			}).
			otherwise({
				redirectTo: '/records'
			});
	}
]);
