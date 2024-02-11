$(document).ready(function() {
    var options = {
        chart: {
            renderTo: 'container-ogoto',
            type: 'spline',
        },
        title: {
            text: '雄琴'
        },
        xAxis: {
            categories: [],
        },
        yAxis: {
            title: {text: "水温(℃)"}
        },
        subtitle: {
            float: 'true',
            align: 'left',
        },
        series: []
    };
    $.get('../biwako-data/ogoto-current.csv', function(data2) {
        // Split the lines
         var lines = data2.split('\n');

        // Iterate over the lines and add categories or series
        $.each(lines, function(lineNo, line) {
            var items = line.split(',');

            // header line contains categories
            if (lineNo == 0) {
                $.each(items, function(itemNo, item) {
                    options.xAxis.categories.push(item);
                });
            }
            // the rest of the lines contain data with their name in the first position
            else if (lineNo ==1){
                var series = {
                    name: '24時間前～現在',
                    color: '#1E4083',
                    dataLabels: {
                        enabled: true
                    },
                    data: []
                };

                $.each(items, function(itemNo, item) {
                    series.data.push(parseFloat(item));
                });

                options.series.push(series);
            }
            else{
            }
        });

        // Create the chart
        var chart = new Highcharts.Chart(options);
    });
    $.get('../biwako-data/ogoto-yesterday.csv', function(data1) {
        var lines = data1.split('\n');
       $.each(lines, function(lineNo, line) {
           var items = line.split(',');

           if (lineNo == 0) {
               $.each(items, function(itemNo, item) {
                   options.xAxis.categories.push(item);
               });
           }
           // the rest of the lines contain data with their name in the first position
           else if (lineNo ==1){
               var series = {
                   name: '48時間～24時間',
                   color: '#C0C0C0',
                   dashStyle: 'shortdot',
                   dataLabels: {
                       enabled: true,
                       color: '#808080'
                   },
                   data: []
               };

               $.each(items, function(itemNo, item) {
                   series.data.push(parseFloat(item));
               });

               options.series.push(series);
           }
           else{
           }
       });
       // Create the chart
       var chart = new Highcharts.Chart(options);
   });
});
