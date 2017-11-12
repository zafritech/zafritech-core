/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

$(document).ready(function () {
    
    loadAllConfigurationSections();
});

function loadAllConfigurationSections() {
    
    $.ajax({

        global: false,
        type: "GET",
        contentType: "application/json",
        url: "/api/configuration/entity/keytypes/list",
        dataType: "json",
        cache: false
    })
    .done(function (data) {
        
        $.each(data, function (key, index) {
            
            loadConfigurationSection(index);
        });
        
        $('#collapseProjectTypes').collapse('show');
    });
}

function loadConfigurationSection(entityKey) {
   
    var keyparts = entityKey.split("_");
    var entityName = keyparts[0];
    
    var url = "/api/configuration/entity/type/list/" + entityKey;
    
    $('#' + entityName.toLowerCase() + "EntityTypes").load(url);
}

function configEntityTypeCreate(typeKey) {

    var typeName = typeKey.split("_")[0];
    typeName = typeName.charAt(0).toUpperCase() + typeName.slice(1).toLowerCase();
    
    $.ajax({
            
        global: false,
        type: "GET",
        url: '/modals/configuration/configuration-entitytype-create-new.html',
        success: function (data) {
            
            var box = bootbox.confirm({

                closeButton: false,
                message: data,
                title: "Create " + typeName + " Type",
                buttons: {
                    cancel: {
                        label: "Cancel",
                        className: "btn-danger btn-fixed-width-100"
                    },
                    confirm: {
                        label: "Save",
                        className: "btn-success btn-fixed-width-100"
                    }
                },
                callback: function (result) {
                    
                    if (result) {
                        
                        var data = {};

                        data['key'] = document.getElementById('entityTypeKey').value;
                        data['code'] = document.getElementById('entityTypeCode').value;
                        data['name'] = document.getElementById('entityTypeName').value;
                        data['description'] = document.getElementById('entityTypeDescription').value;
                        
                        var name = document.getElementById('entityTypeName').value;
                        
                        $.ajax({

                            closeButton: false,
                            type: "POST",
                            contentType: "application/json",
                            url: "/api/configuration/entitytype/new/save",
                            data: JSON.stringify(data),
                            dataType: "json",
                            timeout: 60000,
                            success: function (data) {
                                
                                swal({
                                    
                                    title: "Success!",
                                    text: name + " " + typeName + " type has been successfully created.",
                                    type: "success"
                                });
                                
                                loadConfigurationSection(data.entityTypeKey);
                            }
                        });
                    }
                }
            });
            
            box.on("shown.bs.modal", function(e) {
                
                $(e.currentTarget).find('input[name="entityTypeKey"]').prop('value', typeKey);
            });
            
            box.modal('show');
        }
    });
}

function configEntityTypeEdit(entityTypeId) {
    
    $.ajax({
            
        global: false,
        type: "GET",
        url: '/modals/configuration/configuration-entitytype-create-new.html',
        success: function (data) {
            
            var box = bootbox.confirm({

                closeButton: false,
                message: data,
                title: "Update Entity Type",
                buttons: {
                    cancel: {
                        label: "Cancel",
                        className: "btn-danger btn-fixed-width-100"
                    },
                    confirm: {
                        label: "Update",
                        className: "btn-success btn-fixed-width-100"
                    }
                },
                callback: function (result) {
                    
                    if (result) {
                        
                        var data = {};

                        data['key'] = document.getElementById('entityTypeKey').value;
                        data['code'] = document.getElementById('entityTypeCode').value;
                        data['name'] = document.getElementById('entityTypeName').value;
                        data['description'] = document.getElementById('entityTypeDescription').value;
                        
                        $.ajax({

                            closeButton: false,
                            type: "POST",
                            contentType: "application/json",
                            url: "/api/configuration/entitytype/update/" + entityTypeId,
                            data: JSON.stringify(data),
                            dataType: "json",
                            timeout: 60000,
                            success: function (data) {
                                
                                swal({
                                    
                                    title: "Success!",
                                    text: data.entityTypeName + " type has been successfully updated.",
                                    type: "success"
                                });
                                
                                loadConfigurationSection(data.entityTypeKey);
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
                    url: "/api/configuration/entity/type/" + entityTypeId,
                    dataType: "json",
                    cache: false
                })
                .done(function (data) {
                    
                    $(e.currentTarget).find('input[name="entityTypeKey"]').prop('value', data.entityTypeKey);
                    $(e.currentTarget).find('input[name="entityTypeCode"]').prop('value', data.entityTypeCode);
                    $(e.currentTarget).find('input[name="entityTypeName"]').prop('value', data.entityTypeName);
                    $(e.currentTarget).find('textarea[name="entityTypeDescription"]').prop('value', data.entityTypeDescription);
                });
            });
            
            box.modal('show');
        }
    });
}

function showEntityTypeDescription(entityTypeId) {
    
    $.ajax({

        global: false,
        type: "GET",
        contentType: "application/json",
        url: "/api/configuration/entity/type/" + entityTypeId,
        dataType: "json",
        cache: false
    })
    .done(function (data) {
        
        var description = data.entityTypeDescription;

        if(description) {
            
            bootbox.alert(description);
        }
    });
}