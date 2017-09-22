/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/* global d3 */
    
nv.addGraph(function() {
  
    $.ajax({

        global: false,
        type: "GET",
        contentType: "application/json",
        url: "/api/snapshots/requirements/1",
        dataType: "json",
        cache: false
    })
    .done(function (data) {

        var chart = nv.models.stackedAreaChart()
                        .x(function(d) { return d[0]; })
                        .y(function(d) { return d[1]; })
                        .clipEdge(true)
                        .useInteractiveGuideline(true);

        chart.xAxis
            .showMaxMin(true)
            .tickPadding(15)
            .tickFormat(function(d) { return d3.time.format('%b %Y')(new Date(d)); });

        chart.yAxis
            .tickPadding(10)
            .tickFormat(d3.format());

        d3.select('#projectSnapshotsChart svg')
            .datum(data)
            .transition().duration(500).call(chart);

        nv.utils.windowResize(chart.update);

        return chart;
    });
});

var ctxStatus = document.getElementById("requirementsStatusChart").getContext('2d');
    
var requirementsStatusChart = new Chart(ctxStatus, {
    
    type: 'bar',
    data: {
            labels: ["Open", "Selected", "Defined", "Confirmed", "Closed"],
            datasets: [{
                label: 'First Dataset', 
                data: [ 883, 1321, 2276, 1937, 2505 ],
                backgroundColor: [ '#FF7F0E', '#607D8B', '#AEC7E8', '#1F77B4', '#4CAF50' ]
            }]
        },
    options: {
        legend: {
                    display: false
                },
                tooltips: {
                    callbacks: {
                       label: function(tooltipItem) {
                              return tooltipItem.yLabel;
                }
            }
        },
        scales: {
            xAxes: [{
                display: true,
                scaleLabel: {
                    display: true,
                    labelString: 'Current Requirements Status',
                    fontSize: 14
                },
                barPercentage: 0.4
            }],
            yAxes: [{
                display: true,
                ticks: {
                    beginAtZero: true
                },
                scaleLabel: {
                    display: true,
                    labelString: 'Requirements Count',
                    fontSize: 14
                }
            }]
        }
    }
});
