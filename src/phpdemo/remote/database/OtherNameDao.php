<?php
require_once 'BaseDao.php';

class OtherNameDao extends BaseDao {

    // 获取所有的字段信息
    public function findAllByItemId($itemId){
        // SELECT * FROM t_othername where item_id=68
        $sql = "SELECT * FROM t_othername where item_id=?";

        $data = [$itemId];

        return $this->select($sql, $data);
    }
}

