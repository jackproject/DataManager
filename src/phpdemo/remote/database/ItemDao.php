<?php
require_once 'BaseDao.php';

class ItemDao extends BaseDao {

    //插入数据库
    public function insert($values_arr){
        $sql = "INSERT INTO t_item(name,type) VALUES(?,?)";

        /* $values_arr[0] = ['字段1', 2]; */



        $data = array();

        $data[0] = [$values_arr[0][0], $values_arr[0][1]];

        $status = $this->execSql($sql, $data);

        if (!$status) {

            return 0;
        }

        // 返回新创建的数据id
        $newId =  $this->getMaxId();

            
        $status = $this->insertOtherNameItems($newId, $values_arr[0][2]);

        if (!$status) {
            return 0;
        }

        // 插入验证数据列表
        $status = $this->insertValidateItems($newId, $values_arr[0][3]);

        if (!$status) {
            return 0;
        }

            
        return $newId;


    }

    //修改数据库
    public function update($values_arr){
        $sql = "update t_item set name=?, type=? where item_id=?";

        $data = array();

        $data[0] = [$values_arr[0][0], $values_arr[0][1], $values_arr[0][2]];

        $status = $this->execSql($sql, $data);

        if (!$status) {
            return 0;
        }
        
        $data = array();

        $data[0] = [$values_arr[0][2]];

        $this->destroyReferenceTableByItemId($data);


        // update itemID
        $itemId = $values_arr[0][2];
            
        $status = $this->insertOtherNameItems($itemId, $values_arr[0][3]);

        if (!$status) {
            return 0;
        }

        // 插入验证数据列表
        $status = $this->insertValidateItems($itemId, $values_arr[0][4]);

        if (!$status) {
            return 0;
        }
            
        return $status;
    }

    //删除与itemId相关的所有表内容
    public function destroyReferenceTableByItemId($values_arr){
        // 删除 t_othername 表中的记录
        $sql = "delete from t_othername where item_id=?";

        $status = $this->execSql($sql, $values_arr);

        if (!$status) {
            return 0;
        }

        // 删除 t_validate 表中的记录
        $sql = "delete from t_validate where item_id=?";

        $status = $this->execSql($sql, $values_arr);

        if (!$status) {
            return 0;
        }

        return $status;
    }

    //删除记录
    public function destroy($values_arr){

        $this->destroyReferenceTableByItemId($values_arr);

        // 删除 t_item 表中的记录
        $sql = "delete from t_item where item_id=?";

        $status = $this->execSql($sql, $values_arr);

        if (!$status) {
            return 0;
        }

        return 1;
    }


    // 插入别名列表数据
    public function insertOtherNameItems($item_id, $text) {
        
        // 插入别名数据列表
        $values_arr = array();

        $separator = ',';

        $text = str_replace("，", $separator, $text);

        $arr = explode($separator, $text);

        $len = count($arr);
        for ($i = 0; $i < $len; $i++) {

            if (empty($arr[$i])) {
                continue;
            }

            $values_arr[$i] = [$item_id, trim($arr[$i])];
        }

        // insert into t_othername(item_id, name) values(56, '项目编号');
        $sql = "insert into t_othername(item_id, name) values(?,?)";

        $status = $this->execSql($sql, $values_arr);

        return  $status;
    }


    // 插入验证项数据
    public function insertValidateItems($item_id, $text) {
        
        // 插入别名数据列表
        $values_arr = array();

        $separator = ',';

        $text = str_replace("，", $separator, $text);

        $arr = explode($separator, $text);

        $len = count($arr);
        for ($i = 0; $i < $len; $i++) {

            if (empty($arr[$i])) {
                continue;
            }

            $values_arr[$i] = [$item_id, trim($arr[$i])];
        }

        // insert into t_validate(item_id, validate_item) values(56, '验证项1');
        $sql = "insert into t_validate(item_id, validate_item) values(?,?)";

        $status = $this->execSql($sql, $values_arr);

        return  $status;
    }

    // 获取所有的字段信息
    public function findAllItem(){
        $sql = "SELECT * FROM t_item";

        $params = array();
        return $this->select($sql, $params);
    }


    //分页查找
    public function findByPage($page, $pageSize){
        $sql = "SELECT * FROM student LIMIT ?,?";
        $start = ($page - 1) * $pageSize;
        $params = array($start, $pageSize);
        return $this->select($sql, $params);
    }

    //查找总记录数
    public function getCount(){
        $sql = "SELECT COUNT(*) AS cn FROM t_item";
        $res = $this->select($sql, null);
        if($res && count($res) > 0)
            return $res[0]['cn'];
        return 0;
    }

    //查找总记录数
    public function getMaxId(){
        $sql = "select max(item_id) as maxid from t_item";
        $res = $this->select($sql, null);
        if($res && count($res) > 0)
            return $res[0]['maxid'];
        return 0;
    }


}

