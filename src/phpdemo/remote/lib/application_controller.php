<?php
/**
 * @class ApplicationController
 */
class ApplicationController {
    public $request, $id, $params;

    public function decodeUnicode($str) {
        return preg_replace_callback('/\\\\u([0-9a-f]{4})/i', create_function( '$matches', 'return mb_convert_encoding(pack("H*", $matches[1]), "UTF-8", "UCS-2BE");' ), $str);
    }

    /**
     * dispatch
     * Dispatch request to appropriate controller-action by convention according to the HTTP method.
     */
    public function dispatch($request) {
        $this->request = $request;
        $this->id = $request->id;
        $this->params = $request->params;

        /* if ($this->params != null) { */

        /*     /\* echo "params: " . $this->params; *\/ */

        /*     /\* echo "params: " . $this->params . "<br>"; *\/ */

        /*     echo "in "; */

        /*     $this->params = $this->decodeUnicode($this->params); */

        /*     /\* echo "this->params: " . $this->params . "<br>"; *\/ */
        /* } */

        /* echo "isRestful: " . $request->isRestful() . "<br>"; */

        if ($request->isRestful()) {
            return $this->dispatchRestful();
        }
        if ($request->action) {
            return $this->{$request->action}();
        }
    }

    protected function dispatchRestful() {
        switch ($this->request->method) {
            case 'GET':
                return $this->view();
                break;
            case 'POST':
                return $this->create();
                break;
            case 'PUT':
                return $this->update();
                break;
            case 'DELETE':
                return $this->destroy();
                break;
        }
    }
}

