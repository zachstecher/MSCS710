<?php
  if(isset($_GET["table"])){
   class MyDB extends SQLite3
   {
      function __construct()
      {
         $this->open('tests.db');
      }
   }
   $db = new MyDB();
   if(!$db){
      echo $db->lastErrorMsg();
   }
   if(isset($_GET["WHERE"])){
    // Use underscores for the where
    $sql = "SELECT * from". $_GET["table"]." where ". str_replace($_GET["WHERE"],"_"," ");
   }else{
         $sql ="SELECT * from " .$_GET["table"];
   }

   $ret = $db->query($sql);
   while($row = $ret->fetchArray(SQLITE3_ASSOC) ){
       /*Everything except the last element */
       foreach(array_slice($row, 0, -1) as $col){
       echo $col . ",";
       };
      /*Last element plus newline*/
      echo array_values(array_slice($row, -1))[0] . "\n";
   }
   $db->close();
}
?>



