/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/* global bootbox */

var zMainDocTreeObj = null;

$(document).ready(function () {
    
    zMainDocTreeLoad();
    zOpenDocTreeLoad();
});

function zMainDocTreeLoad() {
    
    if ($('#docTreeValid').length > 0 && $('#documentId').length > 0) {
    
        var documentId = document.getElementById('documentId').value;
        
        var zMainDocTreeObj;
        var setting = {
            data: {
                simpleData: {
                    enable: true,
                    idKey: "id",
                    pIdKey: "pId",
                    rootPId: 0
                }
            },
            view: {
                showIcon: showIconForTree,
                dblClickExpand: true
            },
            callback: {

                beforeClick: zMainTreeDocBeforeClick
            }
        };
  
        $.ajax({

            global: false,
            url: '/api/requirements/document/tree/' + documentId,
            type: "GET",
            dataType: "json",
            cache: true,
            success: function (data) {
                
                $('#docTreeHeaderLabel').text("Documents");
                
                zMainDocTreeObj = $.fn.zTree.init($("#mainDocumentTree"), setting, data);
                zMainDocTreeObj.expandAll(false);   // Collapse all nodes
                
                // Expand Table of Contents node (Node ID: 0)
                var toc = zMainDocTreeObj.getNodeByParam("id", 0, null);
                zMainDocTreeObj.expandNode(toc, true, false, true);
            }
        });
    }
}

function zOpenDocTreeLoad() {

    var zOpenDocTreeObj;
    var setting = {
        data: {
            simpleData: {
                enable: true,
                idKey: "id",
                pIdKey: "pId",
                rootPId: 0
            }
        },
        view: {
            dblClickExpand: true
        },
        callback: {

            beforeClick: zOpenTreeDocBeforeClick
        }
    };

    if ($('#documentId').length > 0) {

        var documentId = document.getElementById('documentId').value;

        $.ajax({

            global: false,
            url: '/api/requirements/document/opendocs/tree/' + documentId,
            type: "GET",
            dataType: "json",
            cache: true,
            success: function (data) {

                $('#noOpenDocuments').hide();

                zOpenDocTreeObj = $.fn.zTree.init($("#openDocumentsTree"), setting, data);
                zOpenDocTreeObj.expandAll(false);
            }
        });

    } else {

        $.ajax({

            global: false,
            url: '/api/requirements/document/opendocs/tree/all',
            type: "GET",
            dataType: "json",
            cache: true,
            success: function (data) {

                if (data.length > 0) {

                    $('#noOpenDocuments').hide();

                    zOpenDocTreeObj = $.fn.zTree.init($("#openDocumentsTree"), setting, data);
                    zOpenDocTreeObj.expandAll(false);

                } else {

                    $('#noOpenDocuments').show();
                }
            }
        });
    }
}

function zTreeExpandMainDocByIdNode(nodeId) {
    
    var treeObj = $.fn.zTree.getZTreeObj("mainDocumentTree");
    var treeNode = treeObj.getNodeByParam("id", nodeId, null);
    
    if (treeNode !== null && treeNode.length > 0) {
    
        treeObj.expandNode(treeNode, true, true, true);
    }
}

function showIconForTree(treeId, treeNode) {
    
    return treeNode.id === 0;
}

function zOpenTreeDocBeforeClick(treeId, treeNode) {
    
    $.ajax({

        global: false,
        type: "GET",
        contentType: "application/json",
        url: "/api/documents/document/" + treeNode.linkId,
        dataType: "json",
        cache: false
    })
    .success(function (data) {

        window.location.href = "/" + data.contentDescriptor.urlPathString + "/document/" + data.uuId;
    });
}
                
function zMainTreeDocBeforeClick(treeId, treeNode) {
    
    var zMainDocTreeObj = $.fn.zTree.getZTreeObj("mainDocumentTree");
    var documentId = document.getElementById('documentId').value;

    // Expand Table of Contents node (Node ID: 0)
    var toc = zMainDocTreeObj.getNodeByParam("id", 0, null);
    zMainDocTreeObj.expandNode(toc, true, false, true);
    
    // Handle the selected node
    if (treeNode.isParent) {
        
        zMainDocTreeObj.expandNode(treeNode, true, null, null);
        $('#documentNodeId').prop('value', treeNode.id);
        $('#sectionId').prop('value', treeNode.id);
        
        loadRequirementsItems(documentId, treeNode.id);
    }
}

function UserAdminChangeDocumentOwner(projectUuId, documentUuId) {
    
    $.ajax({
        
        global: false,
        type: "GET",
        url: '/modals/document/document-admin-owner-change.html',
        success: function (data) {
            
            var box = bootbox.confirm({

                global: false,
                closeButton: false,
                title: 'Document Author',
                size: 'small',
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
                        
                        data = document.getElementById('documentOwner').value;
                        
                        $.ajax({

                            global: false,
                            type: "POST",
                            contentType: "application/json",
                            url: "/api/documents/document/owner/add/" + documentUuId,
                            data: JSON.stringify(data),
                            dataType: "json",
                            timeout: 60000,
                            success: function () {
                              
                                swal({
                                    
                                    title: "Author changed!",
                                    text: "Documemt auther has been successfully changed.",
                                    type: "success"
                                },
                                function () {

                                    window.location.reload();
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
                    url: '/api/admin/projects/project/members/list/' + projectUuId,
                    dataType: "json",
                    cache: false
                })
                .done(function (data) {

                    var selectUsers = '';

                    $.each(data, function (key, index) {

                        selectUsers = selectUsers + '<option value="' + index.id + '">' + index.name + '</option>';
                    });

                    $('#documentOwner').append(selectUsers);
                    
                    $.ajax({

                        global: false,
                        type: "GET",
                        contentType: "application/json",
                        url: "/api/documents/document/owner/get/" + documentUuId,
                        dataType: "json",
                        cache: false
                    })
                    .success(function (data) {

                        $('#documentOwner').prop('value', data.id);
                    });
                });
            });
            
            box.modal('show');
        }
    });
}

function UserAddDocumentApprover(projectUuId, documentUuId) {
    
    $.ajax({
        
        global: false,
        type: "GET",
        url: '/modals/document/document-approver-add.html',
        success: function (data) {
            
            var box = bootbox.confirm({

                global: false,
                closeButton: false,
                title: 'Document Approver',
                size: 'small',
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
                        
                        data = document.getElementById('documentApprover').value;
                        
                        $.ajax({

                            global: false,
                            type: "POST",
                            contentType: "application/json",
                            url: "/api/documents/document/approver/add/" + documentUuId,
                            data: JSON.stringify(data),
                            dataType: "json",
                            timeout: 60000,
                            success: function (data) {
                              
                                swal({
                                    
                                    title: "Members updated!",
                                    text: "Project members list has been successfully updated.",
                                    type: "success"
                                },
                                function () {

                                    window.location.reload();
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
                    url: '/api/admin/projects/project/members/list/' + projectUuId,
                    dataType: "json",
                    cache: false
                })
                .done(function (data) {

                    var selectUsers = '';

                    $.each(data, function (key, index) {

                        selectUsers = selectUsers + '<option value="' + index.id + '">' + index.name + '</option>';
                    });

                    $('#documentApprover').append(selectUsers);
                    
                    $.ajax({

                        global: false,
                        type: "GET",
                        contentType: "application/json",
                        url: "/api/documents/document/approver/get/" + documentUuId,
                        dataType: "json",
                        cache: false
                    })
                    .success(function (data) {

                        $('#documentApprover').prop('value', data.id);
                    });
                });
            });
            
            box.modal('show');
        }
    });
}

function UserAddDocumentEditors(projectUuId, documentUuId) {
    
    $.ajax({
        
        global: false,
        type: "GET",
        url: '/modals/document/document-editors-add.html',
        success: function (data) {
            
            var box = bootbox.confirm({

                global: false,
                closeButton: false,
                title: 'Document Editiors',
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
                       
                        var membersCount = $('#selectedUsersList option').length;
                        
                        var members = [];
                        
                        for (i = 0; i < membersCount; i++) {
                            
                            var member = {};
                            
                            member['id'] = parseInt($('#selectedUsersList option').eq(i).val(), 10);
                            member['itemValue'] = $('#selectedUsersList option').eq(i).html();
                            members.push(member);
                        }
                        
                        $.ajax({

                            global: false,
                            type: "POST",
                            contentType: "application/json",
                            url: "/api/documents/document/editors/add/" + documentUuId,
                            data: JSON.stringify(members),
                            dataType: "json",
                            timeout: 60000,
                            success: function () {
                              
                                swal({
                                    title: "Members updated!",
                                    text: "Project members list has been successfully updated.",
                                    type: "success"
                                },
                                function () {

                                    window.location.reload();
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
                    url: '/api/projects/project/members/list/' + projectUuId,
                    dataType: "json",
                    cache: false
                })
                .done(function (data) {
                  
                    var selectUsers = '';

                    $.each(data, function (key, index) {

                        selectUsers = selectUsers + '<option value="' + index.id + '">' + index.name + '</option>';
                    });

                    $('#availableUsersList').append(selectUsers);
                    
                    $.ajax({

                        global: false,
                        type: "GET",
                        contentType: "application/json",
                        url: "/api/documents/document/editors/list/" + documentUuId,
                        dataType: "json",
                        cache: false
                    })
                    .done(function (data) {
                   
                        var selectMembersOptions = '';

                        $.each(data, function (key, index) {

                            selectMembersOptions = selectMembersOptions + '<option value="' + index.id + '">' + index.name + '</option>';

                            // Remove option from main list
                            $('#availableUsersList option[value="' + index.id + '"]').remove();
                        });

                        $('#selectedUsersList').append(selectMembersOptions);

                    });
                });
            });
            
            box.modal('show');
        }
    });    
}

function UserAddDocumentReviewers(projectUuId, documentUuId) {
    
    $.ajax({
        
        global: false,
        type: "GET",
        url: '/modals/document/document-reviewers-add.html',
        success: function (data) {
            
            var box = bootbox.confirm({

                global: false,
                closeButton: false,
                title: 'Document Reviewers',
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
                       
                        var membersCount = $('#selectedUsersList option').length;
                        
                        var members = [];
                        
                        for (i = 0; i < membersCount; i++) {
                            
                            var member = {};
                            
                            member['id'] = parseInt($('#selectedUsersList option').eq(i).val(), 10);
                            member['itemValue'] = $('#selectedUsersList option').eq(i).html();
                            members.push(member);
                        }
                        
                        console.log(members);
                        console.log(JSON.stringify(members));
                        
                        $.ajax({

                            global: false,
                            type: "POST",
                            contentType: "application/json",
                            url: "/api/documents/document/reviewers/add/" + documentUuId,
                            data: JSON.stringify(members),
                            dataType: "json",
                            timeout: 60000,
                            success: function () {
                              
                                swal({
                                    title: "Members updated!",
                                    text: "Project members list has been successfully updated.",
                                    type: "success"
                                },
                                function () {

                                    window.location.reload();
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
                    url: '/api/admin/projects/project/members/list/' + projectUuId,
                    dataType: "json",
                    cache: false
                })
                .done(function (data) {

                    var selectUsers = '';

                    $.each(data, function (key, index) {

                        selectUsers = selectUsers + '<option value="' + index.id + '">' + index.name + '</option>';
                    });

                    $('#availableUsersList').append(selectUsers);
                    
                    $.ajax({

                        global: false,
                        type: "GET",
                        contentType: "application/json",
                        url: "/api/documents/document/reviewers/list/" + documentUuId,
                        dataType: "json",
                        cache: false
                    })
                    .done(function (data) {

                        var selectMembersOptions = '';

                        $.each(data, function (key, index) {

                            selectMembersOptions = selectMembersOptions + '<option value="' + index.id + '">' + index.name + '</option>';

                            // Remove option from main list
                            $('#availableUsersList option[value="' + index.id + '"]').remove();
                        });

                        $('#selectedUsersList').append(selectMembersOptions);
                    });
                });
            });
            
            box.modal('show');
        }
    });    
}

function UserCreateDistributionList(projectUuId, documentUuId) {
    
    $.ajax({
        
        global: false,
        type: "GET",
        url: '/modals/document/document-distribution-list.html',
        success: function (data) {
            
            var box = bootbox.confirm({

                global: false,
                closeButton: false,
                title: 'Document Distribution List',
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
                       
                        var membersCount = $('#selectedUsersList option').length;
                        
                        var members = [];
                        
                        for (i = 0; i < membersCount; i++) {
                            
                            var member = {};
                            
                            member['id'] = parseInt($('#selectedUsersList option').eq(i).val(), 10);
                            member['itemValue'] = $('#selectedUsersList option').eq(i).html();
                            members.push(member);
                        }
                       
                        $.ajax({

                            global: false,
                            type: "POST",
                            contentType: "application/json",
                            url: "/api/documents/document/ditribution/list/update/" + documentUuId,
                            data: JSON.stringify(members),
                            dataType: "json",
                            timeout: 60000,
                            success: function () {
                              
                                swal({
                                    title: "Members updated!",
                                    text: "Document Distribution List has been successfully updated.",
                                    type: "success"
                                },
                                function () {

                                    window.location.reload();
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
                    url: '/api/admin/projects/project/members/list/' + projectUuId,
                    dataType: "json",
                    cache: false
                })
                .done(function (data) {

                    var selectUsers = '';

                    $.each(data, function (key, index) {

                        selectUsers = selectUsers + '<option value="' + index.id + '">' + index.name + '</option>';
                    });

                    $('#availableUsersList').append(selectUsers);
                    
                    $.ajax({

                        global: false,
                        type: "GET",
                        contentType: "application/json",
                        url: "/api/documents/document/distribution/list/" + documentUuId,
                        dataType: "json",
                        cache: false
                    })
                    .done(function (data) {

                        var selectMembersOptions = '';

                        $.each(data, function (key, index) {

                            selectMembersOptions = selectMembersOptions + '<option value="' + index.id + '">' + index.name + '</option>';

                            // Remove option from main list
                            $('#availableUsersList option[value="' + index.id + '"]').remove();
                        });

                        $('#selectedUsersList').append(selectMembersOptions);
                    });
                });
            });
            
            box.modal('show');
        }
    });
}

function AddRemoveUsers(btn) {

    if (btn === 'RoleToRight') {

        var selectedOpts = $('#availableUsersList option:selected');
        if (selectedOpts.length === 0) {
            return false;
        }

        $('#selectedUsersList').append($(selectedOpts).clone());
        $(selectedOpts).remove();
    }

    if (btn === 'RolesAllToRight') {
        
        var selectedOpts = $('#availableUsersList option');
        if (selectedOpts.length === 0) {
            return false;
        }

        $('#selectedUsersList').append($(selectedOpts).clone());
        $(selectedOpts).remove();
    }

    if (btn === 'RoleToLeft') {
        
        var selectedOpts = $('#selectedUsersList option:selected');
        if (selectedOpts.length === 0) {
            return false;
        }

        $('#availableUsersList').append($(selectedOpts).clone());
        $(selectedOpts).remove();
    }

    if (btn === 'RolesAllToLeft') {
        
        var selectedOpts = $('#selectedUsersList option');
        if (selectedOpts.length === 0) {
            return false;
        }

        $('#availableUsersList').append($(selectedOpts).clone());
        $(selectedOpts).remove();
    }
}

function OpenDocument() {
    
    $.ajax({
        
        global: false,
        type: "GET",
        url: '/modals/document/document-document-open.html',
        success: function (data) {
            
            var box = bootbox.confirm({

                closeButton: false,
                title: 'Open Document',
                message: data,
                buttons: {
                    cancel: {
                        label: "Cancel",
                        className: "btn-danger btn-fixed-width-100"
                    },
                    confirm: {
                        label: "Open",
                        className: "btn-success btn-fixed-width-100"
                    }
                },
                callback: function (result) {
                    
                    if (result) {
                        
                        $.ajax({

                            global: false,
                            type: "GET",
                            contentType: "application/json",
                            url: "/api/documents/document/" + document.getElementById('selectedDocumentId').value,
                            dataType: "json",
                            cache: false
                        })
                        .success(function (data) {

                            window.location.href = "/" + data.documentType.contentDescriptor.componentName + "/document/" + data.uuId;
                        });
                    }
                }
            });
            
            box.on("shown.bs.modal", function(e) {
                
                $.ajax({

                    type: "GET",
                    contentType: "application/json",
                    url: "/api/projects/list/open",
                    dataType: "json",
                    cache: false
                })
                .done(function (data) {
                    
                    var selectOptions = '';
                    
                    $.each(data, function (key, index) {

                        selectOptions = selectOptions + '<option value="' + index.id + '">' + index.projectNumber + ": " + index.projectName + '</option>';
                    });

                    $('#selectedProjectId').empty();
                    $('#selectedProjectId').append(selectOptions);
                    
                    onSelectedProjectChange();
                });
            });
            
            box.modal('show');
        }
    });
}

function CreateDocumentBaseLine(documentId) {
    
    $.ajax({
        
        global: false,
        type: "GET",
        url: '/modals/document/document-baseline-create.html',
        success: function (data) {
            
            var box = bootbox.confirm({

                closeButton: false,
                title: 'Create Document Baseline',
                message: data,
                buttons: {
                    cancel: {
                        label: "Cancel",
                        className: "btn-danger btn-fixed-width-100"
                    },
                    confirm: {
                        label: "Create",
                        className: "btn-success btn-fixed-width-100"
                    }
                },
                callback: function (result) {
                    
                    if (result) {
                        
                        $('#modalBusyControl').prop('value', 'ON');
                        
                        var data = {};
                        
                        data['documentId'] = documentId;
                        data['entityTypeId'] = document.getElementById('baseLineTypeId').value;
                        data['baseLineName'] = document.getElementById('baseLineName').value;
                        data['baseLineDescription'] = document.getElementById('baseLineDescription').value;
                        
                        $.ajax({
                            
                            type: "POST",
                            contentType: "application/json",
                            url: "/api/documents/baselines/create/one",
                            data: JSON.stringify(data),
                            dataType: "json",
                            timeout: 60000,
                            success: function (data) {
                                
                                var id = data.id;
                                var uuId = data.uuId;
                                var ident = data.identifier;
                                var baseUrl = data.contentDescriptor.urlPathString;
                                
                                $.ajax({

                                    type: "GET",
                                    contentType: "application/json",
                                    url: "/api/" + baseUrl + "/baseline/items/" + id,
                                    dataType: "json",
                                    cache: false
                                })
                                .done(function (data) {

                                    swal({

                                        title: "Baseline Created",
                                        text: "Baseline has been successfully created for document " + ident + ".",
                                        type: "success"
                                    },
                                    function() {

                                        window.location.href = "/" + data.contentDescriptor.urlPathString + "/document/" + uuId;
                                    });
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
                    url: "/api/documents/baselines/list/types",
                    dataType: "json",
                    cache: false
                })
                .done(function (data) {

                    var baseLineId = '';
            
                    $(e.currentTarget).find('select[name="baseLineTypeId"]').empty();
            
                    $.each(data, function (key, index) {

                        $(e.currentTarget).find('select[name="baseLineTypeId"]').append('<option value="' + index.id + '">' + index.entityTypeName + '</option>');
                        if (index.entityTypeCode === "BASELINE_MILESTONE") { baseLineId = index.id; }
                    });

                    $(e.currentTarget).find('select[name="baseLineTypeId"]').prop('value', baseLineId);
                });
            });
            
            box.modal('show');
        }
    });
}

function CloseDocument(documentId) {

    $.ajax({

        global: false,
        type: "GET",
        contentType: "application/json",
        url: "/api/documents/document/close/" + documentId,
        dataType: "json",
        cache: false
    })
    .success(function (data) {

        window.location.href = "/projects/" + data.uuId;
    });
}

function DocumentTemplates() {
    
    $.ajax({
        
        global: false,
        type: "GET",
        url: '/modals/document/document-document-templates.html',
        success: function (data) {
            
            var box = bootbox.confirm({

                closeButton: false,
                title: 'Open Template',
                message: data,
                buttons: {
                    cancel: {
                        label: "Cancel",
                        className: "btn-danger btn-fixed-width-100"
                    },
                    confirm: {
                        label: "Open",
                        className: "btn-success btn-fixed-width-100"
                    }
                },
                callback: function (result) {
                    
                    if (result) {
                        
                        notYetImplemented();
                    }
                }
            });
            
            box.on("shown.bs.modal", function(e) {
                
                $.ajax({

                    type: "GET",
                    contentType: "application/json",
                    url: "/api/documents/types/list",
                    dataType: "json",
                    cache: false
                })
                .done(function (data) {
                    
                    $(e.currentTarget).find('select[name="templateTypeId"]').empty();

                    $.each(data, function (key, index) {

                        $(e.currentTarget).find('select[name="templateTypeId"]').append('<option value="' + index.id + '">' + index.entityTypeName + ' (' + index.entityTypeCode + ')</option>');
                    });
                    
                    onTemplateDocumentTypeChange();
                });
            });
            
            box.modal('show');
        }
    });
}

function onSelectedProjectChange() {
    
    var projectId = document.getElementById('selectedProjectId').value;
    
    $.ajax({

        type: "GET",
        contentType: "application/json",
        url: "/api/documents/byproject/" + projectId,
        dataType: "json",
        cache: false
    })
    .done(function (data) {
        
        var selectOptions = '';

        $.each(data, function (key, index) {

            selectOptions = selectOptions + '<option value="' + index.id + '">' 
                                          + index.identifier + ": " + index.documentLongName
                                          + '</option>';
        });

        $('#selectedDocumentId').empty();
        $('#selectedDocumentId').append(selectOptions);
    });
}

function RecentDocuments() {
    
    $.ajax({
        
        global: false,
        type: "GET",
        url: '/modals/document/document-recent-documents.html',
        success: function (data) {
            
            var box = bootbox.confirm({

                closeButton: false,
                title: 'Recent Documents',
                message: data,
                buttons: {
                    cancel: {
                        label: "Cancel",
                        className: "btn-danger btn-fixed-width-100"
                    },
                    confirm: {
                        label: "Open",
                        className: "btn-success btn-fixed-width-100"
                    }
                },
                callback: function (result) {
                    
                    if (result) {
                        
                        $.ajax({

                            global: false,
                            type: "GET",
                            contentType: "application/json",
                            url: "/api/documents/document/" + document.getElementById('recentDocumentId').value,
                            dataType: "json",
                            cache: false
                        })
                        .success(function (data) {
                            
                            window.location.href = "/" + data.contentDescriptor.urlPathString + "/document/" + data.uuId;
                        });
                    }
                }
            });
            
            box.on("shown.bs.modal", function(e) {
                
                $.ajax({

                    type: "GET",
                    contentType: "application/json",
                    url: "/api/documents/recent/documents/list",
                    dataType: "json",
                    cache: false
                })
                .done(function (data) {
                    
                    var selectOptions = '';
                    
                    $.each(data, function (key, index) {

                        selectOptions = selectOptions + '<option value="' + index.id + '">' + index.identifier + ": " + index.documentLongName + '</option>';
                    });

                    $('#recentDocumentId').empty();
                    $('#recentDocumentId').append(selectOptions);
                });
            });
            
            box.modal('show');
        }
    });
}

function SaveAsTemplate(documentId, sourceDocumentType, format) {
    
    $.ajax({
        
        global: false,
        type: "GET",
        url: '/modals/document/document-template-create.html',
        success: function (data) {
            
            var box = bootbox.confirm({

                closeButton: false,
                title: 'Create Template',
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
                        
                        $('#modalBusyControl').prop('value', 'ON');
                        
                        var data = {};
                        
                        data['documentId'] = documentId;
                        data['documentTypeId'] = document.getElementById('documentType').value;
                        data['templateName'] = document.getElementById('templateName').value;
                        data['templateLongName'] = document.getElementById('templateLongName').value;
                        data['templateDescription'] = document.getElementById('templateDescription').value;
                        data['templateFormat'] = document.getElementById('templateFormat').value;
                        
                        $.ajax({
                            
                            type: "POST",
                            contentType: "application/json",
                            url: "/api/documents/template/create/new",
                            data: JSON.stringify(data),
                            dataType: "json",
                            timeout: 60000,
                            success: function (data) {
                                
                                swal({
                                    
                                    title: "Template Save",
                                    text: "Template " + data.templateName + " successfully created!",
                                    type: "success"
                                },
                                function() {
                                   
                                    window.location.reload();
                                });
                            },
                            error: function(e) {
                                
                                console.log(e);
                            }
                        });
                    }
                }
            });
            
            box.on("shown.bs.modal", function(e) {
                 
                $(e.currentTarget).find('input[name="documentId"]').prop('value', documentId);
                $(e.currentTarget).find('input[name="sourceDocumentType"]').prop('value', sourceDocumentType);
                $(e.currentTarget).find('input[name="templateFormat"]').prop('value', format);
                 
                $.ajax({

                    type: "GET",
                    contentType: "application/json",
                    url: "/api/documents/types/list",
                    dataType: "json",
                    cache: false
                })
                .done(function (data) {
                    
                    $(e.currentTarget).find('select[name="documentType"]').empty();

                    $.each(data, function (key, index) {

                        $(e.currentTarget).find('select[name="documentType"]').append('<option value="' + index.id + '">' + index.entityTypeName + ' (' + index.entityTypeCode + ')</option>');
                    });
            
                    $(e.currentTarget).find('select[name="documentType"]').prop('value', sourceDocumentType);
                });
            });
             
             box.modal('show');
        }
    });
}

function ImportFromTemplate(documentId, documentTypeId) {
    
    $.ajax({
        
        global: false,
        type: "GET",
        url: '/modals/document/document-import-from-template.html',
        success: function (data) {
            
            var box = bootbox.confirm({

                closeButton: false,
                title: 'Import from Template',
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
                        
                        $('#modalBusyControl').prop('value', 'ON');
                        
                        var data = {};
                        
                        data['documentId'] = documentId;
                        data['templateId'] = document.getElementById('templateId').value;
                        
                        $.ajax({
                            
                            type: "POST",
                            contentType: "application/json",
                            url: "/api/requirements/import/items/from/template/",
                            data: JSON.stringify(data),
                            dataType: "json",
                            timeout: 60000,
                            success: function (data) {
                                
                                swal({
                                    
                                    title: "Template Saved",
                                    text: "Template successfully imported to document!",
                                    type: "success"
                                },
                                function() {
                                   
                                    window.location.href = "/" + data.contentDescriptor.urlPathString + "/document/" + data.uuId;
                                });
                            }
                        });
                    }
                }
            });
            
            box.on("shown.bs.modal", function(e) {
                
                $(e.currentTarget).find('input[name="documentId"]').prop('value', documentId);
                $(e.currentTarget).find('input[name="documentTypeId"]').prop('value', documentTypeId);
                 
                $.ajax({

                    type: "GET",
                    contentType: "application/json",
                    url: "/api/documents/types/list",
                    dataType: "json",
                    cache: false
                })
                .done(function (data) {
                    
                    $(e.currentTarget).find('select[name="templateTypeId"]').empty();

                    $.each(data, function (key, index) {

                        $(e.currentTarget).find('select[name="templateTypeId"]').append('<option value="' + index.id + '">' + index.entityTypeName + ' (' + index.entityTypeCode + ')</option>');
                    });
            
                    $(e.currentTarget).find('select[name="templateTypeId"]').prop('value', documentTypeId);
                    
                    onTemplateDocumentTypeChange();
                });
            });
            
            box.modal('show');
        }
    });
}

function onTemplateDocumentTypeChange() {
    
    var documentTypeId = document.getElementById('templateTypeId').value;
    
    $.ajax({

        type: "GET",
        contentType: "application/json",
        url: "/api/documents/template/list/doctype/" + documentTypeId,
        dataType: "json",
        cache: false
    })
    .done(function (data) {
        
        $('#templateId').empty();

        if (data.length > 0) {
            
            $.each(data, function (key, index) {

                $('#templateId').append('<option value="' + index.id + '">' + index.documentType.entityTypeCode + ' - ' + index.templateName + '</option>');
            });
            
        } else {
            
            $('#templateId').append('<option value="0">No templates found for document type.</option>');
        }
    });
}