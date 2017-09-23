/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/* global bootbox */

$(document).ready(function () {
    
    var documentId = document.getElementById('documentId').value;
    
    loadRequirementsItems(documentId, 0);
});

function loadRequirementsItems(documentId, sectionId) {
    
    var url = "/api/requirements/document/items/list/view/" + documentId + "/" + sectionId;
    
    $('#documentDetails').load(url);
}

function RequirementItemCreateItem(documentId, parentId, itemLevel) {
    
    $.ajax({
            
        global: false,
        type: "GET",
        url: '/modals/requirements/requirements-item-create-new.html',
        success: function (data) {

            var box = bootbox.confirm({

                closeButton: false,
                message: data,
                size: 'large',
                title: "Create New Item",
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
                       
                        var itemClass = document.getElementById('itemClass').value;
                        var mediaType = document.getElementById('mediaType').value;
                        
                        var data = {};

                        data['documentId'] = document.getElementById('documentId').value;
                        data['parentId'] = document.getElementById('parentId').value;
                        data['itemClass'] = document.getElementById('itemClass').value;
                        data['itemLevel'] = document.getElementById('itemLevel').value;
                        data['mediaType'] = document.getElementById('mediaType').value;
                        
                        
                        if (itemClass === "HEADER") {
                            
                            data['itemValue'] = document.getElementById('itemValue').value;
                            
                        } else {
                            
                            data['itemValue'] = $('#summernote').summernote('code');
                        }
                        
                        if (itemClass === "REQUIREMENT") {
                            
                            data['itemTypeId'] = document.getElementById('itemTypeId').value;
                            data['identifier'] = document.getElementById('identifier').value;
                        }
                        
                        if (itemClass === "METADATA") {
                            
                            if (mediaType === "REFERENCES") {
                                
                                data['itemValue'] = document.getElementById('referenceType').value;
                                
                            } else {
                                
                                data['itemValue'] = mediaType;
                            }
                        }
                        
                        $.ajax({

                            closeButton: false,
                            type: "POST",
                            contentType: "application/json",
                            url: "/api/requirements/document/items/item/save",
                            data: JSON.stringify(data),
                            dataType: "json",
                            timeout: 60000,
                            success: function (data) {
                    
                                var section = data.item > 1 ? data.parent.id : 0;
                                
                                if (section === 0) { zDocTreeLoad(); }
                                loadRequirementsItems(data.document.id, section);
                                
                                swal({
                                    
                                    title: "Success!",
                                    text: "New item has been successfully created.",
                                    type: "success"
                                });
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
                    url: "/api/requirements/document/items/newitem/ref/dao/" + documentId,
                    dataType: "json",
                    cache: false
                })
                .done(function (data) {
                    
                    var refItem = data;
            
                    // Clear INPUT and TEXTAREA controls
                    $(e.currentTarget).find('select[name="itemTypeId"]').empty();
                    $(e.currentTarget).find('select[name="identTemplate"]').empty();
                    $(e.currentTarget).find('select[name="mediaType"]').empty();
                    $(e.currentTarget).find('select[name="itemClass"]').empty();
                    $(e.currentTarget).find('input[name="identifier"]').val('');
                    
                    // Populate Requirement Ident Templates SELECT
                    $.each(refItem.itemClasses, function (key, index) {

                        $(e.currentTarget).find('select[name="itemClass"]').append('<option value="' + index + '">' + index + '</option>');
                    });
                    
                    // Populate Requirement Ident Templates SELECT
                    $.each(refItem.mediaTypes, function (key, index) {

                        $(e.currentTarget).find('select[name="mediaType"]').append('<option value="' + index + '">' + index + '</option>');
                    });
                    
                    // Populate Requirement Types SELECT
                    $.each(refItem.itemTypes, function (key, index) {

                        $(e.currentTarget).find('select[name="itemTypeId"]').append('<option value="' + index.id + '">' + index.itemTypeLongName + '</option>');
                    });
                    
                    // Populate Requirement Ident Templates SELECT
                    $.each(refItem.identPrefices, function (key, index) {

                        $(e.currentTarget).find('select[name="identTemplate"]').append('<option value="' + index.variableValue + '">' + index.variableValue + '</option>');
                    });
                    
                    $(e.currentTarget).find('input[name="documentId"]').prop('value', documentId);
                    $(e.currentTarget).find('input[name="parentId"]').prop('value', parentId);
                    $(e.currentTarget).find('input[name="itemLevel"]').prop('value', itemLevel);
                    $(e.currentTarget).find('select[name="itemClass"]').prop('value', 'HEADER');
                    $(e.currentTarget).find('select[name="mediaType"]').prop('value', 'TEXT');
                    
                    // Initialize summernote plugin
                    $('.summernote').summernote({
                        height: 100,
                        toolbar: [
                            ['font', ['fontname', 'fontsize', 'style', 'color']],
                            ['style', ['bold', 'italic', 'underline', 'clear']],
                            ['alignment', ['ul', 'ol', 'paragraph', 'lineheight']],
                            ['insert', ['table', 'link', 'picture', 'hr']],
                            ['codeview', ['codeview']],
                            ['help', ['help']]
                        ]
                    });

                    $(e.currentTarget).find('div[id="summernote"]').summernote();
                    $('#summernote').summernote({ focus: true });
                    
                    onRequirementClassChange();
                });
            });
             
            box.modal('show');
        }
    });
}

function RequirementItemEditItem(itemId) {
    
    $.ajax({

        global: false,
        type: "GET",
        contentType: "application/json",
        url: "/api/requirements/document/items/item/" + itemId,
        dataType: "json",
        cache: false
    })
    .done(function (data) {

        var item = data;

        $.ajax({

            global: false,
            type: "GET",
            url: '/modals/requirements/requirements-item-create-new.html',
            success: function (data) {

                var box = bootbox.confirm({

                    closeButton: false,
                    message: data,
                    size: 'large',
                    title: "Edit Item",
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
                            
                            var itemClass = document.getElementById('itemClass').value;
                            
                            var data = {};

                            data['id'] = itemId;
                            data['documentId'] = document.getElementById('documentId').value;
                            data['itemClass'] = itemClass;
                            
                            if (itemClass === "HEADER") {
                                
                                data['itemValue'] = document.getElementById('itemValue').value;
                                
                            } else {
                                
                                data['itemValue'] = $('#summernote').summernote('code');
                                data['mediaType'] = document.getElementById('mediaType').value;
                            }
                            
                            if (itemClass === "REQUIREMENT") {

                                data['itemTypeId'] = document.getElementById('itemTypeId').value;
                                data['identifier'] = document.getElementById('identifier').value;
                            }
                            
                            $.ajax({

                                closeButton: false,
                                type: "POST",
                                contentType: "application/json",
                                url: "/api/requirements/document/items/edit/save",
                                data: JSON.stringify(data),
                                dataType: "json",
                                timeout: 60000,
                                success: function (data) {
                                    
                                    var section = data.item > 1 ? data.parent.id : 0;
                                    
                                    if (section === 0) { zDocTreeLoad(); }
                                    loadRequirementsItems(data.document.id, section);
                                    
                                    swal({

                                        title: "Success!",
                                        text: "New item has been successfully created.",
                                        type: "success"
                                    });
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
                        url: "/api/requirements/document/items/newitem/ref/dao/" + item.document.id,
                        dataType: "json",
                        cache: false
                    })
                    .done(function (data) {

                        var refItem = data;

                        // Clear INPUT and TEXTAREA controls
                        $(e.currentTarget).find('select[name="itemTypeId"]').empty();
                        $(e.currentTarget).find('select[name="identTemplate"]').empty();
                        $(e.currentTarget).find('select[name="mediaType"]').empty();
                        $(e.currentTarget).find('select[name="itemClass"]').empty();
                        $(e.currentTarget).find('input[name="identifier"]').val('');

                        // Populate Requirement Ident Templates SELECT
                        $.each(refItem.itemClasses, function (key, index) {

                            $(e.currentTarget).find('select[name="itemClass"]').append('<option value="' + index + '">' + index + '</option>');
                        });

                        // Populate Requirement Ident Templates SELECT
                        $.each(refItem.mediaTypes, function (key, index) {

                            $(e.currentTarget).find('select[name="mediaType"]').append('<option value="' + index + '">' + index + '</option>');
                        });

                        // Populate Requirement Types SELECT
                        $.each(refItem.itemTypes, function (key, index) {

                            $(e.currentTarget).find('select[name="itemTypeId"]').append('<option value="' + index.id + '">' + index.itemTypeLongName + '</option>');
                        });

                        // Populate Requirement Ident Templates SELECT
                        $.each(refItem.identPrefices, function (key, index) {

                            $(e.currentTarget).find('select[name="identTemplate"]').append('<option value="' + index.variableValue + '">' + index.variableValue + '</option>');
                        });

                        $(e.currentTarget).find('input[name="itemEdit"]').prop('value', 'true');
                        
                        $(e.currentTarget).find('input[name="itemId"]').prop('value', itemId);
                        $(e.currentTarget).find('input[name="documentId"]').prop('value', item.document.id);
                        $(e.currentTarget).find('input[name="parentId"]').prop('value', item.parent !== null ? item.parent.id : null);
                        $(e.currentTarget).find('input[name="itemLevel"]').prop('value', item.itemLevel);
                        $(e.currentTarget).find('select[name="itemClass"]').prop('value', item.itemClass);
                        $(e.currentTarget).find('select[name="mediaType"]').prop('value', item.mediaType);
                        
                        if (item.itemClass === "HEADER") {
                            
                            $(e.currentTarget).find('input[name="itemValue"]').prop('value', item.itemValue);
                            
                        } else {
                            
                            $('#summernote').summernote('code',  item.itemValue);
                        }
                        
                        if (item.itemClass === "REQUIREMENT") {
                          
                            $(e.currentTarget).find('select[name="itemTypeId"]').prop('value', item.itemType.id);
                            $(e.currentTarget).find('input[name="identifier"]').prop('value', item.identifier);
                        }

                        // Initialize summernote plugin
                        $('.summernote').summernote({
                            height: 100,
                            toolbar: [
                                ['font', ['fontname', 'fontsize', 'style', 'color']],
                                ['style', ['bold', 'italic', 'underline', 'clear']],
                                ['alignment', ['ul', 'ol', 'paragraph', 'lineheight']],
                                ['insert', ['table', 'link', 'picture', 'hr']],
                                ['codeview', ['codeview']],
                                ['help', ['help']]
                            ]
                        });

                        $(e.currentTarget).find('div[id="summernote"]').summernote();
                        $('#summernote').summernote({ focus: true });

                        onRequirementClassChange();
                        
                        if (item.itemClass === "HEADER") {
                        
                            document.getElementById('itemClass').disabled = true;
                            document.getElementById('mediaType').disabled = true;
                        }
                    });
                });

                box.modal('show');
            }
        });

    });
}

function RequirementItemEditRequirement(data) {
     
    var item = data;
    
    console.log(item);
    
    $.ajax({
            
        global: false,
        type: "GET",
        url: '/modals/requirements/requirements-item-edit-requirement.html',
        success: function (data) {
            
            var box = bootbox.confirm({

                closeButton: false,
                message: data,
                size: 'large',
                title: "Edit Requirement",
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

                        data['id'] = item.id;
                        data['itemClass'] = item.itemClass;
                        data['itemTypeId'] = document.getElementById('itemTypeId').value;
                        data['identifier'] = document.getElementById('identifier').value;
                        data['itemValue'] =  $('#summernote').summernote('code');
                        
                        console.log(data);
                    
                        $.ajax({

                            type: "POST",
                            contentType: "application/json",
                            url: "/api/requirements/document/items/edit/save",
                            data: JSON.stringify(data),
                            dataType: "json",
                            timeout: 60000,
                            success: function () {

                                swal({
                                    
                                    title: "Success!",
                                    text: "New item has been successfully updated.",
                                    type: "success"
                                });
                                
                                location.reload();
                            }
                        });
                    }
                }
            });
            
            box.on("shown.bs.modal", function(e) {
               
                $.ajax({

                    global: false,
                    type: "GET",
                    url: "/api/requirements/document/item/types/list",
                    dataType: "json",
                    timeout: 60000,
                    success: function (data) {

                        // Initialize summernote plugin
                        $('.summernote').summernote({
                            height: 150,
                            toolbar: [
                                ['font', ['fontname', 'fontsize', 'style', 'color']],
                                ['style', ['bold', 'italic', 'underline', 'clear']],
                                ['alignment', ['ul', 'ol', 'paragraph', 'lineheight']],
                                ['insert', ['table', 'link', 'picture', 'hr']],
                                ['codeview', ['codeview']],
                                ['help', ['help']]
                            ]
                        });

                        $(e.currentTarget).find('div[id="summernote"]').summernote();
                        $('#summernote').summernote('pasteHTML', item.itemValue);

                        // Clear INPUT and TEXTAREA controls
                        $(e.currentTarget).find('select[name="itemTypeId"]').empty();
                        $(e.currentTarget).find('input[name="identifier"]').val(item.identifier);

                        // Populate Requirement Types SELECT
                        $.each(data, function (key, index) {

                            $(e.currentTarget).find('select[name="itemTypeId"]').append('<option value="' + index.id + '">' + index.itemTypeLongName + '</option>');
                        });
                        
                        $(e.currentTarget).find('select[name="itemTypeId"]').prop('value', item.itemType.id);
                    }
                });
            });
            
            box.modal('show');
        }
    });
}

function RequirementItemMoveItem(itemId, direction) {

    $.ajax({

        global: false,
        type: "GET",
        contentType: "application/json",
        url: '/api/requirements/document/items/item/move',
        dataType: "json",
        data: {id: itemId, direction: direction},
        cache: false
    })
    .done(function () {
        
        window.location.reload();
    });
}

function onRequirementIdentTemplateChange() {
    
    var itemClass = document.getElementById('itemClass');
    
    if (itemClass.value === "REQUIREMENT") {

        document.getElementById('identifier').disabled = false;
        document.getElementById('itemTypeId').disabled = false;
        document.getElementById('identTemplate').disabled = false;
        
        $('#requirementRow').show();
        $('#identField').show();
        $('#headingNumberRow').hide();
        
        onRequirementClassChange();
        
    } else if (itemClass.value === "HEADER") {
        
        document.getElementById('identifier').disabled = true;
        document.getElementById('itemTypeId').disabled = true;
        document.getElementById('identTemplate').disabled = true;
        
        $('#requirementRow').hide();
        $('#identField').hide();
        $('#headingNumberRow').show();

    } else {

        document.getElementById('identifier').disabled = true;
        document.getElementById('itemTypeId').disabled = true;
        document.getElementById('identTemplate').disabled = true;
        
        $('#requirementRow').hide();
        $('#identField').hide();
        $('#headingNumberRow').hide();
    }
}

function onRequirementClassChange() {

    var itemClass = document.getElementById('itemClass');

    if (itemClass.value === "REQUIREMENT") {

        var mediaTypes = ["EQUATION", "IMAGE", "TABLE", "TEXT", "URL"];
        
        $('#mediaType').empty();
        
        for(i = 0; i < mediaTypes.length; i++) {

            $('#mediaType').append('<option value="' + mediaTypes[i] + '">' + mediaTypes[i] + '</option>');
        };
        
        $('#mediaType').prop('value', 'TEXT');
        
        document.getElementById('identTemplate').disabled = false;
        document.getElementById('itemTypeId').disabled = false;
        document.getElementById('identifier').disabled = false;

        $('#requirementRow').show();
        $('#summernoteRow').show();
        $('#referenceTypeInput').hide();
        $('#textRow').hide();

        var itemEdit = document.getElementById('itemEdit').value;
        
        if (itemEdit !== 'true') {
            
            $.ajax({

                global: false,
                type: "GET",
                url: "/api/requirements/document/items/identifier/next",
                data: {id: document.getElementById('documentId').value, template: document.getElementById('identTemplate').value},
                dataType: "text",
                timeout: 60000,
                success: function (responseText) {

                    document.getElementById('identifier').value = responseText;
                }
            });
        }
        
    } else if (itemClass.value === "HEADER") {
 
        $('#mediaType').empty();
        $('#mediaType').append('<option value="TEXT">TEXT</option>');
        $('#mediaType').prop('value', 'TEXT');
        
        document.getElementById('identTemplate').disabled = true;
        document.getElementById('itemTypeId').disabled = true;
        document.getElementById('identifier').disabled = true;

        $('#requirementRow').hide();
        $('#summernoteRow').hide();
        $('#referenceTypeInput').hide();
        $('#textRow').show();        
        
    } else if (itemClass.value === "METADATA") {
        
        var mediaTypes = ["ABBREVIATIONS", "ACRONYMS", "DEFINITIONS", "REFERENCES"];
        
        // Populate Requirement Ident Templates SELECT
        $('#mediaType').empty();
        
        for(i = 0; i < mediaTypes.length; i++) {

            $('#mediaType').append('<option value="' + mediaTypes[i] + '">' + mediaTypes[i] + '</option>');
        };
        
        $('#mediaType').prop('value', 'REFERENCES');
        
        $('#requirementRow').hide();
        $('#summernoteRow').hide();
        $('#textRow').hide();
        $('#referenceTypeInput').show();
        

    } else {

        var mediaTypes = ["EQUATION", "IMAGE", "TABLE", "TEXT", "URL"];
        
        $('#mediaType').empty();
        
        for(i = 0; i < mediaTypes.length; i++) {

            $('#mediaType').append('<option value="' + mediaTypes[i] + '">' + mediaTypes[i] + '</option>');
        };
        
        $('#mediaType').prop('value', 'TEXT');
        
        document.getElementById('identTemplate').disabled = true;
        document.getElementById('itemTypeId').disabled = true;
        document.getElementById('identifier').disabled = true;

        $('#requirementRow').hide();
        $('#summernoteRow').show();
        $('#referenceTypeInput').hide();
        $('#textRow').hide();
    }
    
    onRequirementMediaTypeChange();
}

function onRequirementMediaTypeChange() {
    
    var mediaType = document.getElementById('mediaType').value;
    
    if (mediaType === "REFERENCES") {
        
        $('#referenceTypeInput').show();
        
        $('#referenceType').empty();
        $('#referenceType').append('<option value="APPLICABLE_REFERENCES">Applicable References</option>');
        $('#referenceType').append('<option value="OTHER_REFERENCES">Other References</option>');
        
    } else {
        
        $('#referenceTypeInput').hide();
    }
}

function RequirementItemDelete(itemId, systemId) {
    
    swal({
        
        title: "Do you want to delete " + systemId + "?",
        text: "You will not be able to recover this item!",
        type: "warning",
        showCancelButton: true,
        confirmButtonColor: "#DD6B55",
        confirmButtonText: "Yes, delete",
        cancelButtonText: "No, cancel",
        closeOnConfirm: false,
        closeOnCancel: false},
        function (isConfirm) {
            
            if (isConfirm) {

                $.ajax({

                    global: false,
                    type: "DELETE",
                    contentType: "application/json",
                    url: '/api/requirements/document/items/item/delete/' + itemId,
                    dataType: "json",
                    cache: false
                })
                .done(function () {

                    swal("Deleted!", "The item has been successfully deleted.", "success");

                    setTimeout(function () {
                        
                        $('#' + systemId).remove();
                        
                    }, 2000);
                })
                .error(function() {

                    swal("Failed!", "Failed to delete the item. The item might be a title the contains sub-items", "error");
                });

            } else {

                swal("Cancelled", "The item has not been deleted.", "info");
            }
    });
}

function RequirementItemLinkCreate(itemId) {
    
    $.ajax({
            
        global: false,
        type: "GET",
        url: '/modals/requirements/requirements-link-create-new.html',
        success: function (data) {
            
            var box = bootbox.confirm({

                closeButton: false,
                message: data,
                title: "Create New Link",
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
                        
                        data['srcItemId'] = itemId;
                        data['srcDocumentId'] = document.getElementById('documentId').value;
                        data['dstDocumentId'] = document.getElementById('dstDocumentId').value;
                        data['linkTypeId'] = document.getElementById('linkTypeId').value;
                        data['dstItemId'] = document.getElementById('dstItemId').value;
                        
                        $.ajax({

                            closeButton: false,
                            type: "POST",
                            contentType: "application/json",
                            url: "/api/requirements/document/items/link/save",
                            data: JSON.stringify(data),
                            dataType: "json",
                            timeout: 60000,
                            success: function () {

                                swal({
                                    
                                    title: "Success!",
                                    text: "A new requirements link has been successfully created.",
                                    type: "success"
                                });
                                
                                loadRequirementsItems(data['srcDocumentId'], itemId);
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
                    url: "/api/requirements/document/items/links/ref/dao/" + itemId,
                    dataType: "json",
                    cache: false
                })
                .done(function (data) {
                    
                    var linkDao = data;
                    var defaultLinkType = 0;
            
                    // Clear INPUT and TEXTAREA controls
                    $(e.currentTarget).find('select[name="linkTypeId"]').empty();
                    $(e.currentTarget).find('select[name="dstDocumentId"]').empty();
                    $(e.currentTarget).find('select[name="dstItemId"]').empty();
                    
                    // Populate LinkTypes SELECT
                    $.each(linkDao.linkTypes, function (key, index) {

                        $(e.currentTarget).find('select[name="linkTypeId"]').append('<option value="' + index.id + '">' + index.linkTypeName + '</option>');
                        if (index.defaultType === true) { defaultLinkType = index.id; }
                    });
                    
                    $(e.currentTarget).find('select[name="linkTypeId"]').prop('value', defaultLinkType);
                    
                    // Populate project documents SELECT
                    $.each(linkDao.dstDocuments, function (key, index) {
                        
                        if (index.id !== linkDao.srcDocumentId) {
                           
                            $(e.currentTarget).find('select[name="dstDocumentId"]').append('<option value="' + index.id + '">' + index.identifier  + " " + index.documentName + '</option>');
                        }
                    });
                    
                    onItemLinkCreateDestChange();
                    
                });
            });
             
            box.modal('show');
        }
    });
}

function RequirementItemCommentCreate(itemId) {
    
    $.ajax({
            
        global: false,
        type: "GET",
        url: '/modals/requirements/requirements-comment-create-new.html',
        success: function (data) {
            
            var box = bootbox.confirm({

                closeButton: false,
                message: data,
                size: 'large',
                title: "Create New Comment",
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

                        var documentId = document.getElementById('itemId').value;
                        var data = {};
                        
                        data['itemId'] = document.getElementById('itemId').value;
                        data['comment'] = document.getElementById('commentValue').value;
                        
                        console.log(data);
                        
                        $.ajax({

                            global: false,
                            closeButton: false,
                            type: "POST",
                            contentType: "application/json",
                            url: "/api/requirements/document/items/comment/save",
                            data: JSON.stringify(data),
                            dataType: "json",
                            timeout: 60000,
                            success: function () {
                                
                                swal({
                                    
                                    title: "Success!",
                                    text: "A new requirements comment has been successfully created.",
                                    type: "success"
                                });
                                
                                loadRequirementsItems(documentId, itemId);
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
                    url: "/api/requirements/document/items/item/" + itemId,
                    dataType: "json",
                    cache: false
                })
                .done(function (data) {
                    
                    var item = data;
            
                    $(e.currentTarget).find('input[name="documentId"]').prop('value', item.document.id);
                    $(e.currentTarget).find('input[name="itemId"]').prop('value', item.id);
                    
                    $('#requirement').append('<div style="font-weight: bold; color: #C62828;">' + item.identifier + '</div><p>' + item.itemValue + '</p>');
                });
            });
            
            box.modal('show');
        }
    });
}

function onItemLinkCreateDestChange() {
    
    var dstDocumentId = document.getElementById('dstDocumentId').value;
           
    $.ajax({

        global: false,
        type: "GET",
        contentType: "application/json",
        url: "/api/requirements/document/requirements/list/" + dstDocumentId,
        dataType: "json",
        cache: false,
        success: function (data) {
            
            console.log();
            
            $('#dstItemId').empty();
            
            // Populate LinkTypes SELECT
            $.each(data, function (key, index) {

                $('#dstItemId').append('<option value="' + index.id + '">' + index.identifier + ' (' + index.systemId + ')</option>');
            });
            
        }
    });
}

function RequirementItemAddDefinition(documentId, itemId, definitionClass) {
    
    var definitionString = '';
    var windowTitle = '';
    var definitionType = '';
    
    switch(definitionClass) {
        
        case 'ABBREVIATIONS':
            
            definitionString = 'abbreviation';
            windowTitle = 'Abbreviations';
            definitionType = 'ABBREVIATION';
            
            break;
            
        case 'ACRONYMS':
            
            definitionString = 'acronym';
            windowTitle = 'Acronyms';
            definitionType = 'ACRONYM';
            
            break;
            
        case 'DEFINITIONS':
            
            definitionString = 'definition';
            windowTitle = 'Definitions';
            definitionType = 'DEFINITION';
            
            break;
            
        default:
            
            definitionString = 'abbreviation';
            windowTitle = 'Abbreviations';
            definitionType = 'ABBREVIATION';
    }
    
    $.ajax({
        
        global: false,
        type: "GET",
        url: '/modals/requirements/requirements-definitions-definition-add.html',
        success: function (data) {
            
            var box = bootbox.confirm({

                global: false,
                closeButton: false,
                title: windowTitle,
                message: data,
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

                        data['documentId'] = document.getElementById('documentId').value;
                        data['itemId'] = document.getElementById('itemId').value;
                        data['definitionId'] = document.getElementById('definitionsList').value;
                        data['newTerm'] = document.getElementById('newTerm').value;
                        data['newTermDefinition'] = document.getElementById('newTermDefinition').value;
                        data['definitionType'] = definitionType;
                        
                        // Create new term - dont't send selected item
                        if (data['newTerm'].length > 0 && data['newTermDefinition'].length > 0) {
                            
                            data['definitionId'] = null;
                            
                        }
                        
                        $.ajax({

                            closeButton: false,
                            type: "POST",
                            contentType: "application/json",
                            url: "/api/requirements/document/definition/add",
                            data: JSON.stringify(data),
                            dataType: "json",
                            timeout: 60000,
                            success: function (data) {

                                swal({

                                    title: "Success!",
                                    text: windowTitle + " successfully added.",
                                    type: "success"
                                });
                                
                                window.location.reload();
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
                    url: '/api/definitions/' + definitionString + '/list',
                    dataType: "json",
                    cache: false
                })
                .done(function (data) {
                    
                    var definitionsList = '';
                    
                    $('#documentId').prop('value', documentId);
                    $('#itemId').prop('value', itemId);
                    
                    $.each(data, function (key, index) {

                        definitionsList = definitionsList + '<option value="' + index.id + '">' + index.term + '</option>';
                    });
                    
                    $('#definitionsList').append(definitionsList);
                    $('#definitionsList').prop('value', data[0].id);
                    
                    onDefinitionsChange();
                });
            });
            
            box.modal('show');
        }
    });
}

function RequirementItemAddReference(documentId, itemId, referenceType) {
    
    $.ajax({
        
        global: false,
        type: "GET",
        url: '/modals/document/document-references-reference-add.html',
        success: function (data) {
            
            var box = bootbox.confirm({

                global: false,
                closeButton: false,
                title: 'Add Reference',
                message: data,
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
                        
                         data['source'] = $("input[name='referenceSource']:checked").val();
                         data['documentId'] = document.getElementById('documentId').value;
                         data['projectId'] = document.getElementById('projectId').value;
                         data['itemId'] = document.getElementById('itemId').value;
                         data['referenceType'] = document.getElementById('referenceType').value;
                         
                        if (data['source'] === "PROJECT") {
                             
                            data['projectRefId'] = document.getElementById('projectReferenceId').value;
                            data['libraryRefId'] = null;
                            data['linkRefId'] = null;
                            data['linkRefIdentifier'] = null;
                            data['linkRefTitle'] = null;
                            data['linkRefUrl'] = null;
                            data['linkRefAuthority'] = null;
                             
                        } else if (data['source'] === "LIBRARY") {
                             
                            // Issue specific jQuery POST 
                            data['projectRefId'] = null;
                            data['libraryRefId'] = document.getElementById('libraryRefId').value;
                            data['linkRefId'] = null;
                            data['linkRefIdentifier'] = null;
                            data['linkRefTitle'] = null;
                            data['linkRefUrl'] = null;
                            data['linkRefAuthority'] = null;
                             
                        } else if (data['source'] === "URL_LINK") {
                             
                            // Issue specific jQuery POST 
                            data['projectRefId'] = null;
                            data['libraryRefId'] = null;
                            data['linkRefId'] = document.getElementById('linkReferenceId').value;
                            data['linkRefIdentifier'] = document.getElementById('linkReferenceId').value;
                            data['linkRefTitle'] = document.getElementById('linkReferenceTitle').value;
                            data['linkRefUrl'] = document.getElementById('linkRefUrl').value;
                            data['linkRefAuthority'] = document.getElementById('linkReferenceAuthority').value;
                        }
                    }
                }
            });
            
            box.on("shown.bs.modal", function(e) {
              
                $.ajax({

                    global: false,
                    type: "GET",
                    contentType: "application/json",
                    url: "/api/documents/document/getproject/" + documentId,
                    dataType: "json",
                    cache: false
                })
                .done(function (data) {
            
                    console.log(data);
                    
                    var projectId = data.id;
                    
                    // Clear INPUT and TEXTAREA controls
                    $(e.currentTarget).find('select[name="projectReferenceId"]').empty();
                    $(e.currentTarget).find('select[name="libraryReferenceId"]').empty();
                    
                    $(e.currentTarget).find('input[name="projectId"]').prop('value', projectId);
                    $(e.currentTarget).find('input[name="documentId"]').prop('value', documentId);
                    $(e.currentTarget).find('input[name="itemId"]').prop('value', itemId);
                    $(e.currentTarget).find('input[name="referenceType"]').prop('value', referenceType);
                    
                    onReferenceSourceChange();
                });
            });
            
            box.modal('show');
        }
    });
}

function onDefinitionsChange() {
    
    var id = document.getElementById('definitionsList').value;
    
    $.ajax({
        
        global: false,
        type: "GET",
        contentType: "application/json",
        url: '/api/definitions/definition/' + id,
        dataType: "json",
        cache: false
    })
    .done(function (data) {
           
        $('#expendedMeaning').text(data.termDefinition);
        $('#newTerm').prop('value', "");
        $('#newTermDefinition').prop('value', "");
    });
}

function onReferenceSourceChange() {
    
    var projectId = document.getElementById('projectId').value;
    var documentId = document.getElementById('documentId').value;
    
    var source = $("input[name='referenceSource']:checked").val();
    
    if (source === "sourceProject") {
        
        $('#projectRow').show();
        $('#libraryRow').hide();
        $('#linkRow').hide();
        
        $('#libraryReferenceId').empty();
        $('#linkReferenceValue').prop('value', "");
        
        $.ajax({

            global: false,
            type: "GET",
            contentType: "application/json",
            url: "/api/documents/byproject/" + projectId,
            dataType: "json",
            cache: false
        })
        .done(function (data) {
            
            // Populate project documents SELECT
            $.each(data, function (key, index) {

                if (parseInt(index.id, 10) !== parseInt(documentId, 10)) {

                    $('#projectReferenceId').append('<option value="' + index.id + '">' + index.identifier  + " " + index.documentName + '</option>');
                }
            });
        });
        
    } else if (source === "sourceLibrary") {
        
        $('#projectRow').hide();
        $('#libraryRow').show();
        $('#linkRow').hide();
        
        $('#projectReferenceId').empty();
        $('#linkReferenceValue').prop('value', "");
        
    } else if (source === "sourceLink") {
        
        $('#projectRow').hide();
        $('#libraryRow').hide();
        $('#linkRow').show();
        
        $('#projectReferenceId').empty();
        $('#libraryReferenceId').empty();
    }
    
}