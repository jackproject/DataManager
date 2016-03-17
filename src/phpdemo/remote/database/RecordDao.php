

<?php
require_once 'BaseDao.php';

class RecordDao extends BaseDao {


    //插入数据库
    public function insert($values_arr){
        // INSERT INTO t_record(remark) VALUES('备注1')
        // INSERT INTO t_record(remark) VALUES('')
        $sql = "INSERT INTO t_record(remark) VALUES(?)";

        $data = array();

        $remark = $values_arr[0][$len-1];
        settype($remark, 'string');

        $data[0] = [$remark];

        $status = $this->execSql($sql, $data);

        if (!$status) {
            return 0;
        }

        // 返回新创建的数据id
        $newId =  $this->getMaxId();

        $values_arr[0]['record_id'] = $newId;

        
        $status = $this->insertRecordData($values_arr[0]);
                    
        /* if (!$status) { */
        /*     return 0; */
        /* } */

        return $newId;
    }
    
    // 插入到数据表中
    public function insertRecordData($record) {
        
        // 插入别名数据列表
        $values_arr = array();

        $i = 0;

        $record_id = $record['record_id'];
        settype($val, 'int');

        foreach($record as $key=>$val) {

            if ($key == 'remark' || $key == 'record_id') {
                continue;
            }

            settype($val, 'string');

            $values_arr[$i] = [$key, $record_id, $val];

            $i++;
        }

        $sql = "insert into t_data(item_id, record_id, content) values(?,?,?)";

        $status = $this->execSql($sql, $values_arr);

        return  $status;
    }

    public function update($values_arr){

        $sql = "update t_record set remark=? where record_id=?";

        $data = array();

        $record = $values_arr[0];

        $record_id = $values_arr[0]['record_id'];
        $remark = $values_arr[0]['remark'];

        settype($record_id, 'int');
        settype($remark, 'int');


        $data[0] = [$remark, $record_id];

        $status = $this->execSql($sql, $data);


        if (!$status) {
            return 0;
        }

        
        $data_arr = array();


        $i = 0;
        foreach($record as $key=>$val) {

            if ($key == 'remark' || $key == 'record_id') {
                continue;
            }

            settype($val, 'string');

            $data_arr[$i] = [$val, $record_id, $key];

            $i++;
        }

        /* $sql = "update t_data set content=? where record_id=? and item_id=?"; */

        /* $status = $this->execSql($sql, $data_arr); */

        $len = $i;

        for ($i = 0; $i < $len; $i++) {
            $record_id = $data_arr[$i][1];
            $item_id = $data_arr[$i][2];


            $param = [];
            $param[0] = $data_arr[$i];


            if ($this->isDataExist($record_id, $item_id)) {

                $sql = "update t_data set content=? where record_id=? and item_id=?";

                $status = $this->execSql($sql, $param);

            } else {

                $sql = "insert into t_data(content, record_id, item_id) values(?,?,?)";

                $status = $this->execSql($sql, $param);
            }
        }

        if (!$status) {
            return 0;
        }

        return $status;
    }

    //查找满足记录的内容
    public function isDataExist($record_id, $item_id){

        $sql = "SELECT data_id FROM t_data where record_id=? and item_id=? ";

        $params = [$record_id, $item_id];

        $records = $this->select($sql, $params);

        return count($records);
    }


    //删除记录
    public function destroy($values_arr){

        // 删除 t_data 表中的记录
        $sql = "delete from t_data where record_id=?";

        $status = $this->execSql($sql, $values_arr);

        if (!$status) {
            return 0;
        }

        // 删除 t_record 表中的记录
        $sql = "delete from t_record where record_id=?";

        $status = $this->execSql($sql, $values_arr);

        if (!$status) {
            return 0;
        }

        return 1;
    }

    // 获取所有的字段信息
    public function findAllRecords(){
        $sql = "SELECT * FROM t_record";

        $params = array();

        $records = $this->select($sql, $params);

        $arrLen = count($records);
        for ($i = 0; $i < $arrLen; $i++) {
            $record_id = $records[$i]['record_id'];


            /* echo "record_id: " . $record_id . "<br>"; */


            // 查询与之对应的数据项
            $sql = "SELECT * FROM t_data where record_id=?";

            $params = array();

            $params[0] = $record_id;

            $record_data = $this->select($sql, $params);

            $len = count($record_data);

            for ($j = 0; $j < $len; $j++) {

                $item_id = $record_data[$j]['item_id'];
                $item_content = $record_data[$j]['content'];

                /* echo "item_id: " . $item_id . "<br>"; */
                /* echo "item_content: " . $item_content . "<br>"; */


                // 将记录添加到 records 中
                $records[$i][$item_id] = $item_content;
            }
        }

        return $records;
    }

    // 返回当前记录中的最大id
    public function getMaxId(){
        $sql = "select max(record_id) as maxid from t_record";
        $res = $this->select($sql, null);
        if($res && count($res) > 0)
            return $res[0]['maxid'];
        return 0;
    }


}


