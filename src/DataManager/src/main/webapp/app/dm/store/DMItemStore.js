
Ext.define('dm.store.DMItemStore', {

    extend: 'Ext.data.Store',

	xtype: 'dmItemStore',

    requires: [
        'Ext.data.Store'
    ],

	
    constructor: function (config) {
		var me = this;

		me.initConfig(config);

        me.callParent(arguments); 
    }	
});



