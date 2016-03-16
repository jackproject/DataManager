<?php
/**
 * @class Records
 * A simple application controller extension
 */
class Records extends ApplicationController {
    /**
     * view
     * Retrieves rows from database.
     */
    public function view() {

        $recordService = new RecordService();

        $res = new Response();
        $res->success = true;
        $res->message = "Loaded data";

        $records = $recordService->findAllRecords();
        $res->data = $records;

        return $res->to_json();
    }

    /**
     * create
     */
    public function create() {
        $res = new Response();
        // get_object_vars 返回该对象的所有属性，用数组的形式返回

        $rec = get_object_vars($this->params);

        $recordService = new RecordService();

        $data = array();

        /* $len = count($rec); */
        /* for ($i = 0; $i < $len; $i++) { */
        /*     echo "$i: " . $rec[$i]; */
        /* } */

        $data[0] = $rec;

        /* echo "start insert record..," . "<br>"; */

        $rec['record_id'] = $recordService->insert($data);

        if ($rec['record_id'] > 0) {
            $res->success = true;
            $res->message = "Created new Record" . $rec['record_id'];
            $res->data = $rec;

        } else {
            $res->message = "Failed to create Record";
        }
        return $res->to_json();
    }

    /**
     * update
     */
    public function update() {
        $res = new Response();

        $rec = get_object_vars($this->params);

        $data = array();

        $data[0] = $rec;

        $recordService = new RecordService();
        $ret = $recordService->update($data);

        if ($ret > 0) {
            $res->success = true;
            $res->message = 'Updated Record ' . $rec['record_id'];
            $res->data = $rec;

        } else {
            $res->message = "Failed to update Record";
        }

        return $res->to_json();
    }

    /**
     * destroy
     */
    public function destroy() {
        $res = new Response();

        $rec = get_object_vars($this->params);

        $recordService = new RecordService();

        $data = array();

        $data[0] = [$rec['record_id']];

        $ret = $recordService->destroy($data);

        if ($ret > 0) {
            $res->success = true;
            $res->message = 'Destroyed Record ' . $rec['record_id'];
        } else {
            $res->message = "Failed to destroy Record";
        }

        return $res->to_json();
    }
}






