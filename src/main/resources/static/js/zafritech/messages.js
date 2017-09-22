/* global bootbox, swal */

function SendMessage() {
    
    var message  =  '<form id="sendMessage" action="/messages/send" method="post">' +
                    '<input type="hidden" name="artifactId" id="artifactId" value="" />' +
                    '<div class="form-group">' +
                    '<div class="row">' +
                    '<div class="col-md-3" style="padding-top: 8px;">' +
                    '<label for="newMessageTemplate">From Template</label>' +
                    '</div>' +
                    '<div class="col-md-6">' +
                    '<select class="form-control file-loading" id="newMessageTemplate" name="newMessageTemplate" placeholder="Select template" onchange="onSendTemplateChange();"></select>' +
                    '</div>' +
                    '</div>' +
                    '</div>' +
                    '<div class="row">' +
                    '<div class="col-md-3" style="padding-top: 8px;">' +
                    '<label for="newMessageSubject">Message Subject</label>' +
                    '</div>' +
                    '<div class="col-md-9">' +
                    '<input type="text" class="form-control file-loading" id="newMessageSubject" name="newMessageSubject" placeholder="Message Subject" />' +
                    '</div>' +
                    '</div>' +
                    '</div>' +
                    '<div class="form-group" style="margin-top: 15px;">' +
                    '<div id="summernote" class="summernote"></div>' +
                    '</div>' +
                    '</form>';
    
    var box = bootbox.confirm({

        closeButton: false,
        title: "Send Global Message",
        message: message,
        size: "large",
        buttons: {
            confirm: {
                label: 'Send',
                className: 'btn-success btn-fixed-width-100'
            },
            cancel: {
                label: 'Cancel',
                className: 'btn-danger btn-fixed-width-100'
            }
        },
        callback: function (result) {

            if (result) {

                var data = {};

                data['subject'] = document.getElementById('newMessageSubject').value;
                data['message'] = $('#summernote').summernote('code');

                $.ajax({

                    type: "POST",
                    contentType: "application/json",
                    url: "/api/messages/message/new",
                    data: JSON.stringify(data),
                    dataType: "json",
                    timeout: 60000,
                    success: function () {

                        showToastr('success', 'Message sent!', 'Success');
                    }
                });
            }
        }
    });
    
    box.on("shown.bs.modal", function(e) { 
        
        $.ajax({

            global: false,
            type: "GET",
            contentType: "application/json",
            url: "/api/messages/templates/list",
            dataType: "json",
            cache: false
        })
        .done(function (data) {

            var selectTemplate = '<option value="">-- Choose message template --</option>';
                    
            $.each(data, function (key, index) {

                selectTemplate = selectTemplate + '<option value="' + index.id + '">' + index.templateName.toUpperCase() + '</option>';

            });

            $('#newMessageTemplate').append(selectTemplate);
            
            $(function () {

                // Initialize summernote plugin
                $('.summernote').summernote({
                    height: 150,
                    toolbar: [
                        ['font', ['fontname', 'fontsize', 'style', 'color']],
                        ['style', ['bold', 'italic', 'underline', 'clear']],
                        ['alignment', ['ul', 'ol', 'paragraph', 'lineheight']],
                        ['insert', ['table', 'link', 'picture', 'hr']],
                        ['help', ['help']]
                    ]
                });

            });
        });
    });
    
    box.modal('show');
    
    
}

function onSendTemplateChange() {
    
    var templateId = document.getElementById('newMessageTemplate').value;
    
    $.ajax({

        global: false,
        type: "GET",
        contentType: "application/json",
        url: "/api/messages/templates/find/" + templateId,
        dataType: "json",
        cache: false
    })
    .done(function (data) {
        
        console.log(data);

        document.getElementById('newMessageSubject').value = data.messageSubject;
        $('#summernote').summernote('code',  data.templateValue);
    });
}

function SendNotification() {
    
    var message  =  '<form id="sendNotification" action="/notifications/send" method="post">' +
                    '<input type="hidden" name="artifactId" id="artifactId" value="" />' +
                    '<div class="form-group">' +
                    '<label for="message">Notification Details</label>' +
                    '<textarea class="form-control"  rows="5" cols="50" id="message" name="message"></textarea>' +
                    '</div>' +
                    '</form>';
            
    bootbox.confirm({
        
        title: "Send global notification",
        message: message,
        buttons: {
            confirm: {
                label: 'Send',
                className: 'btn-success btn-fixed-width-100'
            },
            cancel: {
                label: 'Cancel',
                className: 'btn-danger btn-fixed-width-100'
            }
        },
        callback: function (result) {
            
            if (result) {
                
                var data = {};
                            
                data['name'] = "General Alert";
                data['priority'] = "NORMAL";
                data['message'] = document.getElementById('message').value;
                
                $.ajax({
                                
                    type: "POST",
                    contentType: "application/json",
                    url: "/api/messages/notification/new",
                    data: JSON.stringify(data),
                    dataType: "json",
                    timeout: 60000,
                    success: function () {

                        showToastr('success', 'Message sent!');
                    }
                });
            }
        }
    });
}

function saveMessageTemplate() {
    
    var data = {};
                        
    data['templateName'] = document.getElementById('templateName').value;
    data['messageSubject'] = document.getElementById('messageSubject').value;
    data['templateValue'] = $('#summernote').summernote('code');

    if (data['templateName'] === '') {
        
        swal({
            title: "Missing template name!",
            text: "A message template must have a name.",
            type: "error"
        });
        
    } else if (data['messageSubject'] === '') {

        swal({
            title: "Missing message subject!",
            text: "A template must have a message  subject.",
            type: "error"
        });
        
    } else if (data['templateValue'].length < 20) {
        
        swal({
            title: "Missing template body!",
            text: "A message template must have an html body.",
            type: "error"
        });
        
    } else {
        
        $.ajax({

            global: false,
            type: "POST",
            contentType: "application/json",
            url: "/api/messages/templates/new",
            data: JSON.stringify(data),
            dataType: "json",
            timeout: 60000,
            success: function () {
                
                swal({
                    title: "Template saved!",
                    text: "Your new new template has been saved.",
                    type: "success"
                });
                
                $('#saveButton').hide();
            },
            error: function() {
                
                swal({
                    title: "Failed to save template!",
                    text: "Template name already exists.",
                    type: "error"
                });
            }
        });
    }
}
