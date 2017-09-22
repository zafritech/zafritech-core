/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/* global bootbox */

function CompanyContactsView(uuId, orgName) {

    $.ajax({
        
        global: false,
        type: "GET",
        url: '/modals/company/company-contacts-view.html',
        success: function (data) {

            var box = bootbox.alert({

                closeButton: false,
                title: orgName + ' Contacts',
                message: data,
                buttons: {
                    ok: {
                        label: "Close",
                        className: "btn-primary btn-fixed-width-100"
                    }
                },
                callback: function (result) {
                    
                    if (result) {
                        
                        // Do nothing - just close
                    }
                }
            });
            
            box.on("shown.bs.modal", function(e) {
                
                $.ajax({

                    global: false,
                    type: "GET",
                    contentType: "application/json",
                    url: "/api/admin/companies/contacts/" + uuId,
                    dataType: "json",
                    cache: false
                })
                .done(function (data) {

                    var contacts = '<div class="row">' +
                                    '<div class="col-md-12" style="padding-bottom: 10px;"><span style="font-size:18px; font-weight:bold;">' + data.companyName + '</span></div>' +
                                    '<div class="col-md-12">' + data.contact.address + '</div>' +
                                    '<div class="col-md-12" style="padding-bottom: 10px;">' + data.contact.city + ', ' + data.contact.country + ' ' + data.contact.postCode  + '</div>' +
                                    '<div class="col-md-4">Contact</div>' +
                                    '<div class="col-md-8">' + data.contact.firstName + ' ' + data.contact.lastName + '</div>' +
                                    '<div class="col-md-4">Telephone</div>' +
                                    '<div class="col-md-8">' + data.contact.phone + '</div>' +
                                    '<div class="col-md-4">Mobile</div>' +
                                    '<div class="col-md-8">' + data.contact.mobile + '</div>' +
                                    '<div class="col-md-4">Email</div>' +
                                    '<div class="col-md-8"><a href="mailto:' + data.contact.email + '">' + data.contact.email + '</a></div>' +
                                    '<div class="col-md-4">Website</div>' +
                                    '<div class="col-md-8"><a href="http://' + data.contact.website + '">' + data.contact.website + '</a></div>' +
                                    '<div class="row">';
                            
                    $('#company-contacts').append(contacts);

                });
            });
            
            box.modal('show');
        }
    });
}

function onLogoFileSelect() {
  
    document.getElementById('selectedFileRepeater').value = document.getElementById('selectedFile').value;
}