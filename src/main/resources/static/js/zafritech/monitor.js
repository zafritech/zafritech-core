/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

$(document).ready(function () {

    if ($('#monitoring').length > 0) {
      
        $.ajax({

            global: false,
            type: "GET",
            contentType: "application/json",
            url: "/api/admin/monitor/metrics",
            dataType: "json",
            cache: false
        })
        .done(function (data) {

            console.log(data);
        });
    }
});

