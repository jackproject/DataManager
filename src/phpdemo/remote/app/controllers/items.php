<?php
/**
 * @class Items
 * A simple application controller extension
 */
class Items extends ApplicationController {
    /**
     * view
     * Retrieves rows from database.
     */
    public function view() {

        $itemService = new ItemService();

        $res = new Response();
        $res->success = true;
        $res->message = "Loaded data";

        $records = $itemService->findAllItem();

        // 将数据库中的数字转换成对应的文本输出
        $arrLen = count($records);
        for ($i = 0; $i < $arrLen; $i++) {
            $text = $this->findTypeText($records[$i]['type']);
            $records[$i]['type'] = $text;
        }
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
        $type = $this->findTypeValue($rec['type']);

        $itemService = new ItemService();

        $data = array();

        $data[0] = [$rec['name'], $type, $rec['other_name'], $rec['validate']];

        $rec['item_id'] = $itemService->insert($data);

        if ($rec['item_id'] > 0) {
            $res->success = true;
            $res->message = "Created new Item" . $rec['item_id'];
            $res->data = $rec;

        } else {
            $res->message = "Failed to create Item";
        }
        return $res->to_json();
    }
    /**
     * update
     */
    public function update() {
        $res = new Response();

        $rec = get_object_vars($this->params);
        $type = $this->findTypeValue($rec['type']);

        $data = array();

        $data[0] = [$rec['name'], $type, $rec['item_id'], $rec['other_name'], $rec['validate']];

        $itemService = new ItemService();
        $ret = $itemService->update($data);

        if ($ret > 0) {
            $res->success = true;
            $res->message = 'Updated Item ' . $rec['item_id'];
            $res->data = $rec;

        } else {
            $res->message = "Failed to update Item";
        }

        return $res->to_json();
    }

    /**
     * destroy
     */
    public function destroy() {
        $res = new Response();

        $rec = get_object_vars($this->params);

        $itemService = new ItemService();

        $data = array();

        $data[0] = [$rec['item_id']];

        $ret = $itemService->destroy($data);

        if ($ret > 0) {
            $res->success = true;
            $res->message = 'Destroyed Item ' . $rec['item_id'];
        } else {
            $res->message = "Failed to destroy Item";
        }

        return $res->to_json();
    }

    public function findTypeValue($typeParam) {
        $type = 0;

        if ($typeParam == '数值') {
            $type = 1;

        } else if ($typeParam == '序列') {
            $type = 2;

        } else if ($typeParam == '日期') {
            $type = 3;
        }

        return $type;
    }

    public function findTypeText($typeParam) {
        $text = '字符串';

        if ($typeParam == 1) {
            $text = '数值';

        } else if ($typeParam == 2) {
            $text = '序列';

        } else if ($typeParam == 3) {
            $text = '日期';
        }

        return $text;
    }


}

