
'use strict';


appControllers.controller(
	'RecordCtrl',
	['$scope', '$http', 'MenuService', 'RecordService', function($scope, $http, MenuService, RecordService) {

		MenuService.setCurrentMenu("records");


		// ---------
		// 一些功能函数
		// ---------

		$scope.findItemNameByItemId = function(itemId) {
			var name = '';
			for (var i = 0; i < $scope.itemDefaultList.length; i++) {
				var item = $scope.itemDefaultList[i];
				if (item.item_id == itemId) {
					name = item.name;
					break;
				}
			}

			return name;
		}

		$scope.findItemByItemId = function(itemId) {
			var result = null;
			for (var i = 0; i < $scope.itemDefaultList.length; i++) {
				var item = $scope.itemDefaultList[i];
				if (item.item_id == itemId) {
					result = item;
					break;
				}
			}

			return result;
		}

		$scope.findItemByItemName = function(itemName) {
			var result = null;
			for (var i = 0; i < $scope.itemDefaultList.length; i++) {
				var item = $scope.itemDefaultList[i];
				if (item.name == itemName) {
					result = item;
					break;
				}
			}

			return result;
		}

		$scope.updateItemList = function() {

			var pick = $scope.pick;

			var defaultItemName = ["日期", "报告编号", "工程名称", "委托单位"];

			var itemList = [];
			for (var i = 0; i < defaultItemName.length; i++) {
				var item = $scope.findItemByItemName(defaultItemName[i]);

				if (item) {
					itemList.push(item);
				}
			}

			for (var i = 0; i < pick.pick_item.length; i++) {
				var item = $scope.findItemByItemId(pick.pick_item[i].item_id);

				if (item) {

					var flag = true;
					for (var j = 0; j < itemList.length; j++) {
						if (item.item_id == itemList[j].item_id) {
							flag = false;
							break;
						}
					}

					if (flag) {
						itemList.push(item);
					}
				}
			}

			$scope.itemList = itemList;
		}

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


		// ---------
		$scope.viewAll = function() {
			$scope.pick = null;

			angular.copy($scope.itemDefaultList, $scope.itemList);

			var url = '';

			// url = 'json/record.json';
			url = 'record/record'

			$http.get(url).success(function(res) {
				$scope.recordList = $scope.assembleRecordList(res.data);
			});

		}

		// 分页相关功能
		$scope.page = {};

		$scope.page.pageAmount = 20;
		$scope.page.currentPage = 1;
		$scope.page.totalCount = 100;
		$scope.page.pageCount = 1;
		$scope.page.pageGoTo = 1;

		// 刷新分页数值
		$scope.refreshPage = function() {
			$scope.page.pageList = [];
			var pageCount = $scope.page.pageCount = Math.ceil($scope.page.totalCount/$scope.page.pageAmount);

			var k = parseInt(($scope.page.currentPage-1) / 10);

			var start = 10*k;
			var end = 10*(k+1);

			if (end > pageCount) {
				end = pageCount;
			}

			for (var i = start; i < end; i++) {
				$scope.page.pageList.push(i+1);
			}

			$scope.page.pageCount = pageCount;
		}
		$scope.refreshPage();

		$scope.viewAllData = function() {
			$scope.pick = null;

			// 还原之前的字段列表
			angular.copy($scope.itemDefaultList, $scope.itemList);

			$scope.viewByPage(1);
		}

		// 点击具体页面的查看信息
		$scope.viewByPage = function(page, pick) {
			$scope.recordList = [];

			if (pick) {
				$scope.updateItemList();
			}

			if (page > $scope.page.pageCount) {
				page = $scope.page.pageCount;
			}

			if (page < 1) {
				page = 1;
			}

			var url = '';

			if (!pick) {
				url = 'record/recordbypage';
			} else {
				url = 'record/pickrecordbypage/' + pick.pick_id;
			}
			// console.log("url: " + url);
			// console.log("page: " + page);

			var pageParam = {};

			pageParam.pageAmount = $scope.page.pageAmount;
			pageParam.currentPage = page;
			pageParam.totalCount = $scope.page.totalCount;

			$http.post(url, pageParam).success(function(res) {

				$scope.recordList = $scope.assembleRecordList(res.data.data);

				$scope.page.totalCount = res.data.totalCount;

				$scope.page.currentPage = page;

				$scope.refreshPage();
			});
		}


		$http.get('item/item').success(function(res) {

			// 默认数组元素
			$scope.itemDefaultList = res.data;

			$scope.itemList = [];

			angular.copy($scope.itemDefaultList, $scope.itemList);
			
			$scope.viewByPage(1);
			
			var url = '';

			// 加载筛选信息
			// url = 'json/pick.json';
			url = 'pick/pick';
			$http.get(url).success(function(res) {
				$scope.pickList = res;				
			});


		});

		$scope.viewByPick = function(pickId) {

			$scope.recordList = [];
			
			var url = '';
			url = 'record/pickrecord/' + pickId;
			$http.get(url).success(function(res) {
				$scope.recordList = $scope.assembleRecordList(res.data);
			});

		}


		// 筛选按钮相关功能
		$scope.openPick = function(pick) {
			$scope.pick = pick;

			$scope.page.pageGoTo = 1;

			// $scope.viewByPick(pick.pick_id);
			$scope.viewByPage(1, pick);
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
		// 筛选条件功能
		// ---------


		$scope.currentPickItem = {};

		$scope.itemChange = function(itemId) {
			if (!itemId) {
				return ;
			}

			var item = $scope.findItemByItemId(itemId);
			if (item == null) {
				return ;
			}

			$scope.creatWarning = "";

			if (item.type == "字符串") {
				$scope.creatWarning = "格式: 字符串(如有多个匹配值请用【英文状态的逗号】隔开)，";
				$scope.creatWarning += "长度不能超过";
				$scope.creatWarning += item.maxlength;
			} else if (item.type == "数值") {
				$scope.creatWarning = "格式: 数字";
			} else if (item.type == "系统项") {
			} else if (item.type == "日期") {
				$scope.creatWarning = "格式: 日期，形如：2016-01-01";
			}
		}

		$scope.creatPickItem = function() {
			// $scope.currentPickItem = {};

			$scope.creatWarning = '';

			$scope.itemChange($scope.currentPickItem.item_id);

			$('#createPickItemModal').modal('show');
		}

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
			// console.log('recordId: ' + recordId);

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
