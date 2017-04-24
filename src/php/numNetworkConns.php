<?php
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
    $sql = "SELECT count(*), timestamp from networking_stats group by timestamp";
  // Fetch and display data
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
?>
