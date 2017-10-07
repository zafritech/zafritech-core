/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/* global bootbox */

function timeSince(date) {

    var seconds = Math.floor((new Date() - date) / 1000);

    var interval = Math.floor(seconds / 31536000);
    if (interval > 1) {
        
        return interval + " yrs ago";
    }
    
    interval = Math.floor(seconds / 2592000);
    if (interval > 1) {
        
        return interval + " mths ago";
    }
    
    interval = Math.floor(seconds / 86400);
    if (interval > 1) {
        
        return interval + " days ago";
    }
    interval = Math.floor(seconds / 3600);
    if (interval > 1) {
        
        return interval + " hrs ago";
    }
    
    interval = Math.floor(seconds / 60);
    if (interval > 1) {
        
        return interval + " mins ago";
    }
    
    return Math.floor(seconds) + " secs ago";
}

function RequirementsSearch() {
    
    $.ajax({
        
        global: false,
        type: "GET",
        url: '/modals/requirements/requirements-search-form.html',
        success: function (data) {
            
            bootbox.confirm({

                global: false,
                closeButton: false,
                title: "Search",
                message: data,
                buttons: {
                    cancel: {
                        label: "Cancel",
                        className: "btn-danger btn-fixed-width-100"
                    },
                    confirm: {
                        label: "Search",
                        className: "btn-success btn-fixed-width-100"
                    }
                },
                callback: function (result) {
                    
                    if (result) {
                        
                        var q = document.getElementById('q').value;
                        var size = document.getElementById('size').value;
                        var page = document.getElementById('page').value;
                        
                        window.location.href = '/search?q=' + encodeURI(q) + "&size=" + size + "&page=" + page;
                    }
                }
            });
        }   
    });
}

function notYetImplemented() {
    
    swal(
            
        'Not Yet Implemented!',
        'This application function has not been implements yet.',
        'warning'
    );
}


