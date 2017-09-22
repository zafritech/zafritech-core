/* global toastr, moment */

/* Show busy modal for all ajax requests */
$( document ).ajaxStart(function() { 
    if ($('#modalBusyControl').val() !== "OFF") {
   
        modalBusy.open();
    }
}).ajaxStop(function() { 
    if ($('#modalBusyControl').val() !== "OFF") {
        modalBusy.close(); 
    }
});

var modalBusy = new function() {
    
    this.open = function(){

        $('#modal-busy').modal({

            backdrop: 'static',
            keyboard: false
        });
    };
    
    this.close = function(){
        
        $('#modal-busy').modal('toggle');
    };
};


/* Refresh UI with notifications and messages even without user activity */
$(document).ready(function () {
 
    /* On page load */
    RefreshUI();
    
    /* Repeat every 5 minutes */
    window.setInterval(RefreshUI, 5*60*1000);
    
});

/* Update User Interface (UI) notifications */
function RefreshUI() {
  
    /* Update User toolbar with messages */
    UpdateUserMessageAlerts();
//    UpdateUserNotificationAlerts();
//    UpdateUserTasksAlerts();
}

/* Update User toolbar with messages */
/* Requires moment.js */
function UpdateUserMessageAlerts() {
    
    $.ajax({
        
        global: false,
        url: '/api/messages/unread/list',
        type: "GET",
        dataType: "json",
        success: function (data) {
            
            if (data.length > 0) {
                
                var msgCount = (data.length > 10) ? '10+' : data.length;

                $('#userMessageCount').html(msgCount);
                $('#userMessageSummaries').empty();

                var summeriesTitle = '<div class="title" style="font-weight: bold;">' +
                                     'You have ' + data.length + ' new messages' +
                                     '</div>';
                             
                $('#userMessageSummaries').append(summeriesTitle);
                
                $.each(data, function (key, index) {

                    var date = new Date(index.statusDate);
                    var when = moment(date).fromNow();
                    var calendar = moment(date).calendar();
                                 
                    var message  =  '<li>' +
                                    '<div class="dropdown-messages-box">' +
                                    '<a href="/messages/' + index.uuId + '" class="pull-left" style="margin-right: 8px;">' +
                                    '<img alt="image" class="img-circle" src="/images/profile.png">' +
                                    '</a>' +
                                    '<div class="media-body">' +
                                    '<small class="pull-right text-navy" style="margin-left: 8px;">' + when + '</small>' +
                                    '<strong>' + index.message.sender + '</strong> sent you a message.<br>' +
                                    '<small class="text-muted">'+ calendar + '</small>' +
                                    '</div>' +
                                    '</div>';
                                    '</li>';

                    $('#userMessageSummaries').append(message);
                    
                    if (key === 3) { return false; }
                });

                var footer = '<li class="message-footer">' +
                             '<div class="text-center link-block">' +
                             '<a href="/messages">' +
                             '<i class="fa fa-envelope"></i> <strong>Read All Messages</strong>' +
                             '</a>' +
                             '</div>' +
                             '</li>';
                
                $('#userMessageSummaries').append(footer);
                $('#userMessages').show();
                 
            } else {
                
                $('#userMessages').hide();
            }
        },
        error: function() {
            
            return;
        }
    });
}

/* Update User toolbar with notifications */
function UpdateUserNotificationAlerts() {
    
    $.ajax({
        
        global: false,
        url: '/api/notifications/unread',
        type: "GET",
        dataType: "json",
        success: function (data) {
             
            if (data.length > 0) {
                
                var msgCount = (data.length > 10) ? '10+' : data.length;

                $('#userNotificationsCount').html(msgCount);
                $('#userNotificationDetails').empty();
                
                $.each(data, function (key, index) {

                    var name = index.name;
                    var alertClass = '';

                    if (index.notificationPriority === "HIGH") { alertClass = 'danger'; }
                    if (index.notificationPriority === "NORMAL") { alertClass = 'success'; }
                    if (index.notificationPriority === "MEDIUM") { alertClass = 'warning'; }

                    var simpleAlert  =  '<div class="notice notice-' + alertClass + '" >' +
                                        '<strong>' + name + '</strong> ' + index.notification + 
                                        '</div>';

                    $('#userNotificationDetails').append(simpleAlert);

                });

                $('#userNotificationDetails').append('<li class="divider"></li>');
                $('#userNotificationDetails').append('<li class="message-footer"><a href="/notifications">View all</a></li>');
                $('#userNotifications').show();
            
            } else {
                
                $('#userNotifications').hide();
            }
        },
        error: function() {
            
            return;
        }
    });
}


/* Update User toolbar with tasks */
function UpdateUserTasksAlerts() {
    
    $.ajax({
        
        global: false,
        url: '/api/tasks/all',
        type: "GET",
        dataType: "json",
        success: function (data) {
            
            if (data.length > 0) {
                
                var taskCount = (data.length > 10) ? '10+' : data.length;
                
                $('#userTasksCount').html(taskCount);
                $('#userTasksDetails').empty();
                
                var age = timeSince(data[0].creationDate);
                var total = data.length;
                var complete = 0;
                var outstanding = 0;
                var taskDetails = '';
                 
                $.each(data, function (key, index) {
                    
                    if (index.completed === true) {
                        
                        complete++;
                    }
                    
                    if (index.completed === false) {
                        
                        outstanding++;
                        
                        var action = '';
                        if (index.taskAction === "CONFIRM") { action = 'Confirmation'; }
                        if (index.taskAction === "ACKNOWLEDGE") { action = 'Acknowlegement'; }
                            
                        taskDetails  =  taskDetails +
                                        '<li>' +
                                        '<a href="javascript:void(0);" onclick="javascript:GoToTaskId(\'' + index.id + '\');">' +
                                        '<div style="padding: 10px; border-top: 1px solid #c0c0c0;">' +
                                        '<span><strong>' + action +' Request</strong></span>' +
                                        '<span class="pull-right" style="font-size: 75%">' + index.sysId + '</span>' +
                                        '<div style="margin-top: 5px;">Confirm requirement requirement with System ID: <strong>' + index.sysId + '</strong></div>' +
                                        '</div>' +
                                        '</a>';
                                        '</li>';
                    }
                });
                
                var pecentageComplete = Math.round(complete / total * 100);
                    
                var task =  '<li>' +
                            '<a href="/tasks/list">' +
                            '<div style="padding: 10px; border-top: 6px solid #369;">' +
                            '<span><strong>Requirements Requests</strong></span>' +
                            '<span class="pull-right" style="font-size: 75%">' + age + '</span>' +
                            '<div style="margin-top: 5px;">You have ' + outstanding + ' confirmation requests.</div>' +
                            '<div style="font-size: 85%; margin-top: 10px;">Completion with: ' + pecentageComplete + '%</div>' +
                            '<div class="progress" style="margin-top: 4px;">' +
                            '<div class="progress-bar progress-bar-success" role="progressbar" style="width: ' + pecentageComplete + '%" arial-valuenow="' + pecentageComplete + '" arial-valuemin="0" arial-valuemax="100"></div>' +
                            '</div>' +
                            '</div>' +
                            '</a>';
                            '</li>';
                            
                task = task + taskDetails;

                $('#userTasksDetails').append(task);

                $('#userTasks').show();
                
            } else {
                
                $('#userTasks').hide();
            }
        },
        error: function() {
            
            return;
        }
    });
}

function showToastr(msgType, message) {

    toastr.options = {
        "closeButton": true,
        "debug": false,
        "newestOnTop": false,
        "progressBar": false,
        "positionClass": "toast-top-center",
        "toastClass": "animated fadeInDown",
        "preventDuplicates": false,
        "onclick": null,
        "showDuration": "300",
        "hideDuration": "1000",
        "timeOut": "5000",
        "extendedTimeOut": "1000",
        "showEasing": "swing",
        "hideEasing": "linear",
        "showMethod": "fadeIn",
        "hideMethod": "fadeOut"
    };
    
    toastr[msgType](message);
}