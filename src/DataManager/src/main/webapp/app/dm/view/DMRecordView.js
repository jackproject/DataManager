
Ext.define('dm.view.DMRecordView', {

	extend: 'Ext.grid.Panel',

	xtype: 'dmRecordView',

	config: {
		itemInfo: null
	},

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

    createEditorByType: function(type) {

		var editor = {allowBlank: true};

		return editor;

		if (type == '数值') {
			editor = {
                xtype: 'numberfield',
                allowBlank: false,
                minValue: 0,
                maxValue: 100000
			};
			
		} if (type == '日期') {
			editor = {
                xtype: 'datefield',
                format: 'm/d/y',
                minValue: '01/01/06'
			};
			
		}

		return editor;

    },

    initComponent: function() {
		var me = this;

		var rowEditing = Ext.create('Ext.grid.plugin.RowEditing');		
		me.plugins = [rowEditing];


		me.getSelectionModel().on('selectionchange', function(selModel, selections){
			me.down('#delete').setDisabled(selections.length === 0);
		});

		var itemInfo = me.itemInfo;


		// // 记录从服务器传递回来的字段列表
		// var itemInfo = [
		// 	{name: '字段1', item_id: 66, type: 0},
 		// 	{name: '字段2', item_id: 68, type: 0},
 		// 	{name: '备注', item_id: 'remark', type: 0}
  		// ];



		// 数据模型的字段
		var modelFields = [];

		// 添加隐藏的第一列数据
		modelFields.push({name: 'record_id', type: 'int'});

		for (var i = 0; i < itemInfo.length; i++) {
			var obj = {};

			obj['name'] = itemInfo[i]['item_id'];
			obj['type'] = 'string';

			modelFields.push(obj);
		}


		Ext.define('Record', {
			extend: 'Ext.data.Model',
			idProperty: 'record_id',
			fields: modelFields
		});


		// create the Data Store
		me.store = Ext.create('Ext.data.Store', {
			model: 'Record',
			autoLoad: true,
			autoSync: true,
			proxy: {
				type: 'rest',
				url: 'record/record',
				reader: {
					type: 'json',
					root: 'data'
				},
				writer: {
					type: 'json'
				}
			}
		});



		// grid的字段

		me.columns = [];

		for (var i = 0; i < itemInfo.length; i++) {
			var obj = {};

			obj['header'] = itemInfo[i]['name'];
			obj['dataIndex'] = itemInfo[i]['item_id'];
			obj['width'] = 200;
			obj['editor'] = me.createEditorByType(itemInfo[i]['type']);

			// if (itemInfo[i]['type'] == '日期') {
			// 	obj['renderer'] = me.formatDate;
			// }

			me.columns.push(obj);
		}

		// 最后一列为自动变宽的一列
		me.columns[itemInfo.length-1]['flex'] = 1;


        me.title = '记录列表';
        me.frame = true;
		
		me.dockedItems = [{
            xtype: 'toolbar',
            items: [{
                text: '添加',
                handler: function(){
					me.store.insert(0, new Record());
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

    formatDate: function(value){
        return value ? Ext.Date.dateFormat(value, 'Y-M-d') : '';
    }


});
