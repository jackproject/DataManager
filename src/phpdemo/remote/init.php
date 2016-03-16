<?php
    session_start();

    // base framework
    require(dirname(__FILE__).'/lib/session_db.php');
    require(dirname(__FILE__).'/lib/application_controller.php');
    require(dirname(__FILE__).'/lib/model.php');
    require(dirname(__FILE__).'/lib/request.php');
    require(dirname(__FILE__).'/lib/response.php');

    // require /models (Should iterate app/models and auto-include all files there)
    require(dirname(__FILE__).'/app/models/user.php');


    // 导入数据库文件
    require(dirname(__FILE__).'/database/BaseDao.php');
    require(dirname(__FILE__).'/database/ItemDao.php');
    require(dirname(__FILE__).'/database/ItemService.php');
    require(dirname(__FILE__).'/database/RecordDao.php');
    require(dirname(__FILE__).'/database/RecordService.php');

    // Fake a database connection using _SESSION
    $dbh = new SessionDB();


