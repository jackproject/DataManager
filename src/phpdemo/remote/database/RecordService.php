

<?php
require_once 'RecordDao.php';

class RecordService{

    public function insert($values_arr){
        $recordDao = new RecordDao();
        return $recordDao->insert($values_arr);
    }

    public function update($values_arr){
        $recordDao = new RecordDao();
        return $recordDao->update($values_arr);
    }

    public function destroy($values_arr){
        $recordDao = new RecordDao();
        return $recordDao->destroy($values_arr);
    }


    public function findAllRecords(){
        $recordDao = new RecordDao();
        return $recordDao->findAllRecords();
    }
}




