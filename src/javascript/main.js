//Globals used for data/title selection
ramIndex = 2;
cpuIndex = 2;
cpuTitle = "Clock Rate";
ramTitle = "Memory Available";

//Change cpu display settings. Changes title and data to display
document.getElementById("cpu_options").onchange = function() {
   cpuIndex = parseInt(this.value.slice(-1));
   cpuTitle = this.value.replace(" "+this.value.slice(-1), "");
};

//Change ram display settings. Changes title and data to display
document.getElementById("ram_options").onchange = function() {
   ramIndex = parseInt(this.value.slice(-1));
   ramTitle = this.value.replace(" "+this.value.slice(-1), "");
};

//Matrix : A 2D array
//col : The index of the column of interest
// Returns a specific column in a matrix
 getCol = function (matrix, col){
       var column = [];
       for(var i=0; i<matrix.length; i++){
          column.push(matrix[i][col]);
       }
       return column;
 }

//Renders RAM metrics to the screen
displayRamMetrics = function(){
 var DATECOL = 5
 $.ajax({
   type: 'get',
   url: 'getData.php',
   data: 'table=volatile_mem_stats',
      success: function(data) {
       var allData = CSVToArray(data, "," );
       jQuery('.tables').html('');
       $("h3").text("Ram Metric: " + ramTitle);
       headers = ['index', 'total memory', 'memory available', 'swap size', 'swap available', 'date & time'];
       allData.splice(-4);
       $(".tables").append(arrayToTable(allData, headers));
       new Chartist.Line('.ct-chart', {
  labels: getCol(allData, DATECOL),
    series: [
     getCol(allData,ramIndex)
     ]
      }, {
        fullWidth: true,
        height: "400px",
        chartPadding: {
        bottom:100,
        left:50,
         }
       });
    }
      
  });
}

//tableData : The 2D array to be displayed in the table
//headers : The title of each column in the table
//Turns a 2D array into a table
function arrayToTable(tableData, headers) {
    var table = $('<table align="center" border="1"></table>');
    var headerRow = $('<tr></tr>');
    for(i=0; i<headers.length; i++){
    headerRow.append('<th>' +headers[i] + '</th>');
    }
    table.append(headerRow);
    $(tableData).each(function (i, rowData) {
        var row = $('<tr></tr>');
        $(rowData).each(function (j, cellData) {
            row.append($('<td>'+cellData+'</td>'));
        });
        table.append(row);
    });
    return table;
}


//Creates a chart containing the number of network connections
displayNetworkMetrics = function(){
 var DATECOL = 2;
 var DATACOL = 1;
 $.ajax({
   type: 'get',
   url: 'numNetworkConns.php',
   data: 'table=c',
      success: function(data) {
       var allData = CSVToArray(data, "," );
       console.log(allData);
       jQuery('.tables').html('');
        $("h3").text("Number of connections");
       headers = ['index','number of connections', 'date & time'];
       allData.splice(-4);
       $(".tables").append(arrayToTable(allData, headers));
       new Chartist.Line('.ct-chart', {
  labels: getCol(allData, DATECOL),
    series: [
     getCol(allData,DATACOL)
     ]
      }, {
        fullWidth: true,
        height: "400px",
        chartPadding: {
        bottom:100,
         }
       });
    }
      
  });
}

//id : The cpu id
//rData : The recursively accumulated data
//numThreads : The number of threads available on the machine (how many recursive iterations)
//Renders all cpu data through a recursive call in order to maintain sequential dependency
displayAllCPUs = function(id, rData, numThreads){
if(id < numThreads){
 $.ajax({
   type: 'get',
   url: 'getData.php',
   data: 'table=cpu_time_performance&where=cpu_number='+id,
      success: function(data) {
       var allData = CSVToArray(data, "," );
       allData.splice(-4);
       id++;
       rData.push(getCol(allData,cpuIndex));
       return displayAllCPUs(id,rData, numThreads);
  }
});
}else if(id == numThreads){
   $.ajax({
   type: 'get',
   url: 'getData.php',
   data: 'table=cpu_time_performance&where=cpu_number='+id,
      success: function(data) {
       var allData = CSVToArray(data, "," );
       allData.splice(-4);
       jQuery('.tables').html('');
       $("h3").text("CPU "+cpuTitle+ " (All Threads)");
        new Chartist.Line('.ct-chart', {
  labels: getCol(allData, cpuIndex),
    series:
     rData
      }, {
        fullWidth: true,
        height: "400px",
        chartPadding: {
        bottom:100,
         },
       });
   }

});
id++;
return displayAllCPUs(id, [], numThreads);
}else{
 $.ajax({
   type: 'get',
   url: 'getData.php',
   data: 'table=cpu_time_performance',
      success: function(data) {
       var allData = CSVToArray(data, "," );
       allData.splice(-4);
       jQuery('.tables').html('');
       headers = ['index','cpu number', 'current clock rate (mhz)', 'current temperature (c)','date & time'];
       $(".tables").append(arrayToTable(allData, headers));

    }
  });

 }

}

//id : The thread id
//col : Column indicating which cpu data to display
//Displays the cpu chart
displayCPUChart = function(id, col){
 $.ajax({
   type: 'get',
   url: 'getData.php',
   data: 'table=cpu_time_performance&where=cpu_number='+id,
      success: function(data) {
       var allData = CSVToArray(data, "," );
       jQuery('.tables').html('');
       $("h3").text("CPU "+cpuTitle +" (CPU Thread " +id +")");
       headers = ['index','cpu number', 'current clock rate (mhz)', 'current temperature (c)','date & time'];
       allData.splice(-4);
       $(".tables").append(arrayToTable(allData, headers));
       new Chartist.Line('.ct-chart', {
  labels: getCol(allData, col),
    series: [
     getCol(allData,cpuIndex)
     ]
      }, {
        fullWidth: true,
        height: "400px",
        chartPadding: {
        bottom:100,
         }
       });
    }
      
  });
}

//id : The thread id
//col : Column indicating which hard drive data to display
//Displays the hard drive chart
displayHDChart = function(id, col){
 $.ajax({
   type: 'get',
   url: 'getData.php',
   data: "table=persistent_storage_stats&where=disk_name='"+id.replace("-","/").replace("-","/").substring(3,id.length)+"'",
      success: function(data) {
       var allData = CSVToArray(data, "," );
       allData.splice(-4);
       jQuery('.tables').html('');
       $("h3").text("Available Space (Disk Partition " + id.replace("-","/").replace("-","/").substring(3,id.length) + ")");
       headers = ['index','partition name','space used', 'space available', 'date & time'];
       $(".tables").append(arrayToTable(allData, headers));
       new Chartist.Line('.ct-chart', {
  labels: getCol(allData, col),
    series: [
     getCol(allData,2)
     ]
      }, {
        fullWidth: true,
        height: "400px",
        chartPadding: {
        bottom:100,
         }
       });
    }
      
  });
}


//Creates the buttons dynamically for each cpu thread. Renders to the screen.
initCPUS = function(){
  $.ajax({
    type: 'get',
    url: 'numCpus.php',
    data: 'table=cpu_info',
  	  success: function(data) {
        lines = data.split("|");
        for(i =0; i<parseInt(lines[0]); i++){
             $('#images')
               .prepend("<img id='cpu_"+i+"' src='cpu.jpg' width='160px' height='128px'/>");

             $('#cpu_'+i)
               .click(function(){ displayCPUChart(this.id.split("_")[1], 4);});

            
        }
       $('#images').prepend("<img id='all_cpus' src='multi-core-cpu.jpg' width='160px' height='128px'/>");
       numCPUs = parseInt(lines[0]);
       console.log(numCPUs);
       $('#all_cpus').click(function(){displayAllCPUs(0,[],numCPUs);});
    }
});
}

//Creates the buttons dynamically for each hard drive partition. Renders to the screen.
initHDS = function(){
  $.ajax({
    type: 'get',
    url: 'numHDs.php',
    data: 'table=cpu_info',
          success: function(data) {
        lines = CSVToArray(data, ",");
        for(i=0; i<(lines.length-1); i++){
         console.log(i);
             $('#images')
               .prepend("<img id='hd_"+lines[i][0]+"' src='hd.png' width='160px' height='128px'/>");

             $('#hd_'+lines[i][0])
               .click(function(){ displayHDChart(this.id, 4);});
          }
        }
    
});
}


//Initialize page
window.onload = function(){
initCPUS();
initHDS();
}


//Helper function for turning csv into 2D array (courtesy of stackoverflow)
function CSVToArray( strData, strDelimiter ){
    strDelimiter = (strDelimiter || ",");
        var objPattern = new RegExp(
            (
                "(\\" + strDelimiter + "|\\r?\\n|\\r|^)" +
                "(?:\"([^\"]*(?:\"\"[^\"]*)*)\"|" +
                "([^\"\\" + strDelimiter + "\\r\\n]*))"
            ),
            "gi"
            );
        var arrData = [[]];
        var arrMatches = null;
        while (arrMatches = objPattern.exec( strData )){
            var strMatchedDelimiter = arrMatches[ 1 ];
            if (
                strMatchedDelimiter.length &&
                strMatchedDelimiter !== strDelimiter
                ){
                arrData.push( [] );
            }
            var strMatchedValue;
            if (arrMatches[ 2 ]){
                strMatchedValue = arrMatches[ 2 ].replace(
                    new RegExp( "\"\"", "g" ),
                    "\""
                    );
            } else {
                strMatchedValue = arrMatches[ 3 ];
            }
            arrData[ arrData.length - 1 ].push( strMatchedValue );
        }
        return( arrData );
    }
