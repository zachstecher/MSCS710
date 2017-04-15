<?php
   class MyDB extends SQLite3
   {
      function __construct()
      {
         $this->open('linux_metrics.db');
      }
   }
   $db = new MyDB();
   if(!$db){
      echo $db->lastErrorMsg();
   }
    $sql = "SELECT count(*) from (select distinct cpu_number from cpu_info) a ";

   $ret = $db->query($sql);
   while($row = $ret->fetchArray(SQLITE3_ASSOC) ){
       /*Everything except the last element */
       foreach(array_slice($row, 0, -1) as $col){
       echo $col . ",";
       };
      /*Last element plus newline*/
      echo array_values(array_slice($row, -1))[0] . "|";
   }
   $db->close();
?>
