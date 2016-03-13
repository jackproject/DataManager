
<?php
require_once 'ItemDao.php';
require_once 'ValidateDao.php';
require_once 'OtherNameDao.php';

class ItemService{

    public function insert($values_arr){
        $itemDao = new ItemDao();
        return $itemDao->insert($values_arr);
    }

    public function update($values_arr){
        $itemDao = new ItemDao();
        return $itemDao->update($values_arr);
    }

    public function destroy($values_arr){
        $itemDao = new ItemDao();
        return $itemDao->destroy($values_arr);
    }

    public function findAllItem(){

        /* echo "findAllItem items"; */

        $itemDao = new ItemDao();

        $otherNameDao = new OtherNameDao();

        $validateDao = new ValidateDao();


        $records = $itemDao->findAllItem();

        $arrLen = count($records);
        for ($i = 0; $i < $arrLen; $i++) {
            /* echo $i . ": " . "<br>"; */

            $itemId = $records[$i]['item_id'];

            /* echo $i . ": " . $itemId . "<br>"; */

            $otherNames = $otherNameDao->findAllByItemId($itemId);
            $validates = $validateDao->findAllByItemId($itemId);

            /* echo "count : " . count($otherNames) . "<br>"; */

            $arr = $otherNames;
            $count = count($arr);
            $text = "";

            if ($arr != null && $count > 0) {

                $text = $arr[0]['name'];
                for ($j = 1; $j < $count; $j++) {
                    $text = $text . ", " . $arr[$j]['name'];
                }
            }

            /* echo "othername count: " . $count . "<br>"; */
            /* echo "othername text: " . $text . "<br>"; */

            $records[$i]['other_name'] = $text;


            $arr = $validates;
            $count = count($arr);
            $text = "";

            if ($arr != null && $count > 0) {
                $text = $arr[0]['validate_item'];
                for ($j = 1; $j < $count; $j++) {
                    $text = $text . ", " . $arr[$j]['validate_item'];
                }
            }

            $records[$i]['validate'] = $text;

            /* echo $records[$i]; */
        }
        
        return $records;
    }

    //分页查找
    public function findByPage($page, $pageSize){
        $itemDao = new ItemDao();
        return $itemDao->findByPage($page, $pageSize);
    }

    //查找记录数
    public function getCount(){
        $itemDao = new ItemDao();
        return $itemDao->getCount();
    }
}


