<?php
   class MyDB extends SQLite3
   {
      function __construct()
      {
         $this->open('test.db');
      }
   }
   $db = new MyDB();
   if(!$db){
      echo $db->lastErrorMsg();
   } else {
      echo "Opened database successfully\n";
   }

   $sql =<<<EOF
      SELECT * from cpu_interrupts;
EOF;

   $ret = $db->query($sql);
   while($row = $ret->fetchArray(SQLITE3_ASSOC) ){
       /*Everything except the last element */
       foreach(array_slice($row, 0, -1) as $col){
       echo $col . ",";
       };
      /*Last element plus newline*/
      echo array_values(array_slice($row, -1))[0] . "\n";
   }
   echo "Operation done successfully\n";
   $db->close();
?>



