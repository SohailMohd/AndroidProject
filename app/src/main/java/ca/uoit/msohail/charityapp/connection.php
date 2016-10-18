

<? php

require_once 'config.php';

    class DB_Connection{
        private $connect;

        function _construct(){
            this->connect = mysqli_connect(hostname, username, password, db_name) or die("There is a DB connection error");
        }

        public function get_connection(){

            return this->connect;
        }

    }


?>