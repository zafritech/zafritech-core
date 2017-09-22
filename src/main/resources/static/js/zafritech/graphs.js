/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/* global d3 */
    
nv.addGraph(function() {
  
    var projectId = document.getElementById('projectId').value;
            
    $.ajax({

        global: false,
        type: "GET",
        contentType: "application/json",
        url: "/api/snapshots/requirements/" + projectId,
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
var ctxProgress = document.getElementById("projectProgressChart").getContext('2d');
    
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

var projectProgressChart = new Chart(ctxProgress, {
    
    type: 'line',
    data: {
        datasets: [{
            data: [0, 10, 10, 25, 25, 30, 40, 40, 40, 60, 60, 60],
            
            fill: true,
            backgroundColor: "#c8e6c9",
            borderWidth: 1,
            borderColor: "#4caf50",
            pointRadius: 1
        }],
        labels: ['J', 'F', 'M', 'A', 'M', 'J', 'J', 'A', 'S', 'O', 'N', 'D']
        
    },
    options: {
        elements: {
                line: {
                    tension: 0
                }
        },
        legend: {
                    display: false
                },
                tooltips: {
                    callbacks: {
                       label: function(tooltipItem) {
                              return tooltipItem.yLabel + '%';
                }
            }
        },
        scales: {
            xAxes: [{
                display: true,
                scaleLabel: {
                    display: true,
                    labelString: 'Month 2017',
                    fontSize: 12
                }
            }],
            yAxes: [{
                display: true,
                ticks: {
                    beginAtZero: true,
                    min: 0,
                    max: 100
                },
                scaleLabel: {
                    display: true,
                    labelString: '% Complete',
                    fontSize: 12
                }
            }]
        }
    }
});