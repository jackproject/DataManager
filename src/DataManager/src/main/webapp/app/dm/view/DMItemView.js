
Ext.define('dm.view.DMItemView', {

    extend: 'Ext.grid.Panel',

	xtype: 'dmItemView',

    requires: [
        'Ext.data.Store',
		'Ext.selection.CellModel',
		'Ext.grid.*',
		'Ext.data.*',
		'Ext.util.*',
		'Ext.state.*',
		'Ext.form.*',
        'dm.model.DMItemModel'
    ],

    constructor: function (config) {
		var me = this;

		me.initConfig(config);

        me.callParent(arguments); 
    },

    initComponent: function() {
		var me = this;

		// var cellEditing = Ext.create('Ext.grid.plugin.CellEditing', {
		// 	clicksToEdit: 1
		// });

		// me.plugins = [cellEditing];


		var rowEditing = Ext.create('Ext.grid.plugin.RowEditing');		
		me.plugins = [rowEditing];


		me.getSelectionModel().on('selectionchange', function(selModel, selections){
			me.down('#delete').setDisabled(selections.length === 0);
		});

		// create the Data Store
		me.store = Ext.create('Ext.data.Store', {
			autoLoad: true,
			autoSync: true,
			model: 'dm.model.DMItemModel',
			proxy: {
				type: 'rest',
				url: 'item/item',
				reader: {
					type: 'json',
					root: 'data'
				},
				writer: {
					type: 'json'
				}
			},
			listeners: {
				// read: function(store, operation){
				// 	// var record = operation.getRecords()[0];
				// 	console.log(store);

				// 	var records = store.data.items;

				// 	for (var i = 0; i < records.length; i++) {
				// 		records[i].set('other_name', '123');
				// 	}

				// 	console.log(store);
				// },
				write: function(store, operation) {
					var record = operation.getRecords()[0];
					var name = Ext.String.capitalize(operation.action);
					var verb;

					if (name == 'Destroy') {
						record = operation.records[0];
						verb = 'Destroyed';
					} else {
						verb = name + 'd';
					}

					Ext.example.msg(
						name,
						Ext.String.format(
							"{0} item: {1}",
							verb,
							record.get('item_id')
						)
					);
					
				}
			}

		});

		me.columns = [{
            id: 'name',
            header: '字段名',
            dataIndex: 'name',
            flex: 1,
            editor: {
                allowBlank: false
            }
        }, {
            header: '别名列表',
            width: 200,
            dataIndex: 'other_name',
            editor: {
                allowBlank: true
            }
        }, {
            header: '验证项',
            width: 200,
            dataIndex: 'validate',
            editor: {
                allowBlank: true
            }
        }, {
            header: '类型',
            dataIndex: 'type',
            width: 100,
            editor: new Ext.form.field.ComboBox({
                typeAhead: true,
                triggerAction: 'all',
                selectOnTab: true,
				editable: false,
				// displayField: 'name',
				// valueField: 'name',
                // store: states,
				store: [
					['字符串', '字符串'],
 					['数值', '数值'],
					['序列', '序列'],
					['日期', '日期']
				],
                lazyRender: true,
                listClass: 'x-combo-list-small'
            })
        }, {
            header: '排序值',
            width: 200,
            dataIndex: 'order_num',
            editor: {
                allowBlank: true
            }
        }];

        me.title = '字段管理';
        me.frame = true;

		me.dockedItems = [{
            xtype: 'toolbar',
            items: [{
                text: '添加',
                handler: function(){
					var r = Ext.create('dm.model.DMItemModel', {
						name: 'item 1',
						type: '字符串'
					});

					me.store.insert(0, r);
                    rowEditing.startEdit(0, 0);
                }

            }, '-', {
                itemId: 'delete',
                text: '删除',
                disabled: true,
                handler: function(){
                    var selection = me.getView().getSelectionModel().getSelection()[0];
                    if (selection) {
                        me.store.remove(selection);
                    }
                }
            }]
        }];

        this.callParent(arguments);
    },

	// 删除字段
    delItem: function(grid, rowIndex) {
        grid.store.removeAt(rowIndex);
	},

	// 添加新字段
    addItem: function(grid) {
		// 新增的记录 id 为 -1
        var r = Ext.create('dm.model.DMItemModel', {
			item_id: -1, 
            name: '新字段 1',
            type: '字符串'
        });

        grid.store.insert(0, r);

		var cellEditing = grid.plugins[0];

		cellEditing.startEdit(0, 0);

        // cellEditing.startEditByPosition({row: 0, column: 0});
	},

	// 从服务器请求数据
    refreshData: function(grid) {

		var me = grid;

		me.store.sync();

		// me.store.loadData([
		// 	{item_id: '1', name: '字段名1', type: "日期"},
		// 	{item_id: '2', name: '字段名2', type: "序列"},
		// 	{item_id: '3', name: '字段名3', type: "序列"},
		// 	{item_id: '4', name: '字段名4', type: "序列"},
		// 	{item_id: '5', name: '字段名5', type: "字符串"}
 		// ]);

	},

	// 更新数据到服务器
    commitData: function(grid) {

		var data = [];

		var store = grid.store;

		var count = store.getCount();

		for (var i = 0; i < count; i++) {
			var record = store.getAt(i);

			data.push(record.getData());
		}		

		console.log(data);

		// alert('提交');
	}
});





