

Ext.define('dm.model.DMItemModel', {

    extend: 'Ext.data.Model',

	xtype: 'dmItemModel',

    fields: [
        {name: 'item_id', type: 'int'},
        {name: 'name', type: 'string'},
        {name: 'other_name', type: 'string'},
        {name: 'validate', type: 'string'},
        {name: 'type', type: 'string'}
    ],

	
    validations: [
        {type: 'inclusion', field: 'type',   list: ['字符串', '数值', '序列', '日期'] }
    ],

    requires: [
        'Ext.data.Model'
    ]
});

