<?php
class BaseDao {
	
    const host = '127.0.0.1';
    const port = '3306';
    const db = 'datamanager';

    const user = 'root';
    const password = 'yangjie';


    protected $conn = false;

    //连接mysql
    public function getConn(){

        $this->conn = new mysqli(self::host, self::user, self::password, self::db, self::port);


		$this->conn->query("set character set 'utf8'");//读库
		$this->conn->query("set names 'utf8'");//写库


        if(mysqli_connect_errno()){
            print('Connot connect to mysql: ' . mysqli_connect_error() . "\n");
            $this->conn = false;
        }
        return $this->conn;
    }

    //增删改
    public function execSql($sql, $values_arr){
        $stmt = $this->getConn()->prepare($sql);
        $status = false;
        foreach ($values_arr as $values){
            $status = $this->opDB($stmt, $values);
            if(!$status){
                /* echo "error: " . $status . " | "; */
            }
        }
        $stmt->close();
        $this->conn->close();
        return $status;
    }

    //操作数据库
    private function opDB($stmt, $params){

        $paramType = "";
        if(null != $params && 0 != count($params)){
            foreach ($params as $param){
                if (is_int($param)) $paramType.="i";
                if (is_double($param) || is_float($param)) $paramType.="d";
                if (is_string($param)) $paramType.="s";
            }
            array_unshift($params, $paramType);
            call_user_func_array(array($stmt,"bind_param"), $this->refValues($params));
        }
        $status = $stmt->execute();
        return $status;
    }

    //查询数据库，返回所有查询的记录
    public function select($sql, $params){

        $stmt = $this->getConn()->prepare($sql);

        $paramType = "";
        if(null != $params && 0 != count($params)){
            foreach ($params as $param){
                if (is_int($param)) $paramType.="i";
                if (is_double($param) || is_float($param)) $paramType.="d";
                if (is_string($param)) $paramType.="s";
            }
            array_unshift($params, $paramType);
            call_user_func_array(array($stmt,"bind_param"), $this->refValues($params));
        }
        $stmt->execute();
        $fields = array();
        $data = array();
        $meta = $stmt->result_metadata();
        while ($field = $meta->fetch_field()){
            $fields[] = &$data[$field->name];
        }
        call_user_func_array(array($stmt, 'bind_result'), $fields);
        $i = 0;
        $res = array();
        while ($stmt->fetch()) {
            $res[$i] = array();
            foreach ($data as $k => $v)
                $res[$i][$k] = $v;
            $i++;
        }
        $stmt->close();
        $this->conn->close();
        return $res;
    }

    private function refValues($arr){                                                 
        if (strnatcmp(phpversion(),'5.3') >= 0) //Reference is required for PHP 5.3+  
        {                                                                             
            $refs = array();                                                          
            foreach($arr as $key => $value)                                           
                $refs[$key] = &$arr[$key];                                            
            return $refs;                                                             
        }                                                                             
        return $arr;                                                                  
    } 

    public function testconn(){
        $this->getConn();
        if($this->conn)
            echo "success\n";
        else 
            echo "failed!!!\n";
    }    
}
