/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/* global bootbox */

var zDocTreeObj = null;

$(document).ready(function () {
    
    zDocTreeLoad();
});

function zDocTreeLoad() {
    
    if ($('#docTreeValid').length > 0 && $('#documentId').length > 0) {
        
        var zDocTreeObj;
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
                showIcon: false,
                dblClickExpand: true
            },
            callback: {

                beforeClick: zTreeDocBeforeClick
//                onClick: zTreeDocOnClick,
//                beforeRightClick: zTreeDocBeforeRightClick,
//                onRightClick: zTreeDocOnRightClick,
//                onDblClick: zTreeDocOnDblClick
            }
        };
        
        // Reset project folder node
        // Not needed since a document has been selected
        $('nodeId').prop('value', "");
        
        $.ajax({

            global: false,
            url: '/api/requirements/document/tree/' + document.getElementById('documentId').value,
            type: "GET",
            dataType: "json",
            cache: true,
            success: function (data) {
                
                zDocTreeObj = $.fn.zTree.init($("#document-tree"), setting, data);
                zDocTreeObj.expandAll(false);   // Collapse all nodes
                
                // Expand Table of Contents node (Node ID: 0)
                var toc = zDocTreeObj.getNodeByParam("id", 0, null);
                zDocTreeObj.expandNode(toc, true, false, true);
            }
        });
    }
}

function zTreeDocBeforeClick(treeId, treeNode) {
    
    var zDocTreeObj = $.fn.zTree.getZTreeObj("document-tree");
    var documentId = document.getElementById('documentId').value;
     
    // Collapse all open nodes first
    zDocTreeObj.expandAll(false);

    // Expand Table of Contents node (Node ID: 0)
    var toc = zDocTreeObj.getNodeByParam("id", 0, null);
    zDocTreeObj.expandNode(toc, true, false, true);
    
    // Handle the selected node
    if (treeNode.isParent) {
        
        zDocTreeObj.expandNode(treeNode, true, null, null);
        $('#documentNodeId').prop('value', treeNode.id);
        
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
                        
                        console.log(members);
                        console.log(JSON.stringify(members));
                        
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