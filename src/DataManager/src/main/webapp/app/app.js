

'use strict';

/* App Module */

var app = angular.module('app', [
	'ngRoute',
	'ngCookies',
	'ui.router',
	'angularFileUpload',
	'appServices',
	'applicationControllers',
	'appControllers'
]);

app.constant('AUTH_EVENTS', {
	loginSuccess: 'auth-login-success',
	loginFailed: 'auth-login-failed',
	logoutSuccess: 'auth-logout-success',
	sessionTimeout: 'auth-session-timeout',
	notAuthenticated: 'auth-not-authenticated',
	notAuthorized: 'auth-not-authorized'
});

app.run(function ($rootScope, $state, AUTH_EVENTS, AuthService) {
	$rootScope.$on('$stateChangeStart', function (event, next) {

		console.log('next.name:' + next.name);

		if (next.name == 'login' || next.name == 'sign_up' || next.name == 'forgot_password') {
			// ��ʱ�ǿ��Է���ҳ���
			return ;
		}

		if (!AuthService.isAuthenticated()) {
			// û�е�¼

			// ȡ��Ĭ����ת��Ϊ
			event.preventDefault();
			// ��ת����¼����
			$state.go("login",{from:next.name,w:'notLogin'});
		}

	});
});


// ����������
app.config(function ($httpProvider) {
	$httpProvider.interceptors.push([
		'$injector',
		function ($injector) {
			return $injector.get('AuthInterceptor');
		}
	]);
})

app.factory(
	'AuthInterceptor',
	function ($rootScope, $q, AUTH_EVENTS, Session) {
	return {
		request:function(config){
			config.headers["Authorization"] = Session.token;
			return config;
		},
		responseError: function (response) { 
			$rootScope.$broadcast({
				401: AUTH_EVENTS.notAuthenticated,
				403: AUTH_EVENTS.notAuthorized,
				419: AUTH_EVENTS.sessionTimeout,
				440: AUTH_EVENTS.sessionTimeout
			}[response.status], response);

			return $q.reject(response);
		}
	};
});



// app.config(
// 	['$stateProvider', '$urlRouterProvider', function($stateProvider, $urlRouterProvider) {

// 		$urlRouterProvider.otherwise("/login");

// 		$stateProvider.
// 			state('login', {
// 				url: "/login",
// 				views: {
// 					"login": {
// 						templateUrl: "partials/login.html",
// 						controller: 'LoginCtrl'
// 					},
// 					"main": {template: ""}
// 				}			
// 			}).
// 			state('records', {
// 				url: '/records',
// 				views: {
// 					"login": {template: ""},
// 					"main": {
// 						templateUrl: 'partials/records.html',
// 						controller: 'RecordCtrl'
// 					}
// 				}
// 			}).
// 			state('items', {
// 				url: '/items',
// 				views: {
// 					"login": {template: ""},
// 					"main": {
// 						templateUrl: 'partials/items.html',
// 						controller: 'ItemCtrl'
// 					}
// 				}
// 			}).
// 			state('upload', {
// 				url: '/upload',
// 				views: {
// 					"login": {template: ""},
// 					"main": {
// 						templateUrl: 'partials/upload.html',
// 						controller: 'UploadCtrl'
// 					}
// 				}
// 			}).
// 			otherwise({
// 				redirectTo: '/login'
// 			});
// 	}]
// );



// app.config([
// 	'$routeProvider',
// 	function($routeProvider) {
// 		$routeProvider.
// 			when('/records', {
// 				templateUrl: 'partials/records.html',
// 				controller: 'RecordCtrl'
// 			}).
// 			when('/items', {
// 				templateUrl: 'partials/items.html',
// 				controller: 'ItemCtrl'
// 			}).
// 			when('/upload', {
// 				templateUrl: 'partials/upload.html',
// 				controller: 'UploadCtrl'
// 			}).
// 			otherwise({
// 				redirectTo: '/records'
// 			});
// 	}
// ]);



app.config(
	['$stateProvider', '$urlRouterProvider', function($stateProvider, $urlRouterProvider) {
		
		$urlRouterProvider.otherwise("/login");

		$stateProvider.
			state('login', {
				url: "/login",
				views: {
					"login": {
						templateUrl: "partials/login.html",
						controller: 'LoginCtrl'
					},
					"main": {template: ""}
				}			
			}).
			state('records', {
				url: '/records',
				views: {
					"login": {template: ""},
					"main": {
						templateUrl: 'partials/records.html',
						controller: 'RecordCtrl'
					}
				}
			}).
			state('items', {
				url: '/items',
				views: {
					"login": {template: ""},
					"main": {
						templateUrl: 'partials/items.html',
						controller: 'ItemCtrl'
					}
				}
			}).
			state('upload', {
				url: '/upload',
				views: {
					"login": {template: ""},
					"main": {
						templateUrl: 'partials/upload.html',
						controller: 'UploadCtrl'
					}
				}
			});

	}]
);

