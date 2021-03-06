<?php

if (isset($_GET["table"])) {
  // Connect to an existing database, if not create a new database
   class MyDB extends SQLite3
   {
      function __construct()
      {
         $this->open('linux_metrics.db');
      }
   }
   $db = new MyDB();
   if (!$db) {
      echo $db->lastErrorMsg();
   }
   if (isset($_GET["where"])) {
    // Use underscores for the where
         $sql = "SELECT * from " .$_GET["table"] . " where " . $_GET["where"] . " order by insert_num DESC limit 30";
   } else {
         $sql = "SELECT * from " .$_GET["table"] . " order by insert_num DESC limit 30";
   }
  // Fetch and display records from the table
   $ret = $db->query($sql);
   while ($row = $ret->fetchArray(SQLITE3_ASSOC) ) {
       /*Everything except the last element */
         foreach (array_slice($row, 0, -1) as $col) {
         echo $col . ",";
        };
      /*Last element plus newline*/
        echo array_values(array_slice($row, -1))[0] . "\n";
   }
   $db->close();
}
?>



