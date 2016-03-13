<?php
require_once 'BaseDao.php';

class ValidateDao extends BaseDao {

    // 获取所有的字段信息
    public function findAllByItemId($itemId){
        $sql = "SELECT * FROM t_validate where item_id=?";

        $data = [$itemId];

        return $this->select($sql, $data);
    }
}

