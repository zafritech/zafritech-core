/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/* global bootbox, swal */

var zTreeObj = null;

$(document).ready(function () {
    
    zTreeProjectLoad();
});

function zTreeProjectLoad() {
    
    if ($('#treeValid').length > 0 && $('#projectId').length > 0) {
        
        var zTreeObj;
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

                beforeClick: zTreeProjectBeforeClick,
                onClick: zTreeProjectOnClick,
                beforeRightClick: zTreeProjectBeforeRightClick,
                onRightClick: zTreeProjectOnRightClick,
                onDblClick: zTreeProjectOnDblClick
            }
        };

        $.ajax({

            global: false,
            url: '/api/folders/tree/' + document.getElementById('projectId').value,
            type: "GET",
            dataType: "json",
            success: function (data) {

                zTreeObj = $.fn.zTree.init($("#mainFolderTree"), setting, data);
                
                var projectFolderNode = zTreeObj.getNodeByParam("pId", 0, null);
                $('#mainTreeHeaderLabel').text(projectFolderNode.name + " Documents");
                
                // Expand current zTree node
                if (($('#nodeId').length > 0 && $('#nodeId').val().length !== 0) &&
                    (($('#documentNodeId').length === 0) || $('#documentNodeId').val().length === 0)) {
                    
                    var nodeId =  document.getElementById('nodeId').value;
                    var currentNode = zTreeObj.getNodeByParam("id", nodeId, null);
                    
                    zTreeObj.expandNode(currentNode, true, false, true);
                    
                    $('#subTreeHeaderElement').hide();
                }
            }
        });
    }
}

function zTreeExpandByIdNode(nodeId) {
    
    var treeObj = $.fn.zTree.getZTreeObj("mainFolderTree");
    var treeNode = treeObj.getNodeByParam("id", nodeId, null);
    
    if (treeNode !== null && treeNode.length > 0) {
    
        treeObj.expandNode(treeNode, true, true, true);
    }
}

// Toggle open/close on clicking a parent node
function zTreeProjectBeforeClick(treeId, treeNode) {
    
    var treeObj = $.fn.zTree.getZTreeObj("mainFolderTree");
    
    if (treeNode.isParent) {
        
        treeObj.expandNode(treeNode, null, null, null);
    }
}

// Toggle open/close on clicking a parent node
function zTreeProjectBeforeRightClick(treeId, treeNode) {
    
    var treeObj = $.fn.zTree.getZTreeObj("mainFolderTree");
    
    if (treeNode.isParent) {
        
        zTreeParentContextMenu(treeNode);
        
    } else {
        
        zTreeLeafContextMenu(treeNode);
    }
}

// Toggle open/close on clicking a parent node
function zTreeProjectOnRightClick(event, treeId, treeNode, clickFlag) {
    
    var treeObj = $.fn.zTree.getZTreeObj("mainFolderTree");
    
    if (treeNode.isParent) {
        
        alert('Right clicked on node ' + treeNode.id);
    }
}

// Toggle open/close on clicking a parent node
function zTreeProjectOnDblClick(event, treeId, treeNode, clickFlag) {
    
    if (treeNode.isParent) {
        
        if (treeNode.pId !== null && treeNode.pId > 0) {
            
            $.ajax({

                global: false,
                url: '/api/folders/folder/uuid/' + treeNode.id,
                type: "GET",
                dataType: "text",
                success: function (data) {

                    window.location.href = '/admin/projects/folder/' + data;
                },
                error: function (e) {

                   console.log(e);
                }
            });
            
        } else {
            
            $.ajax({

                global: false,
                url: '/api/folders/project/uuid/' + treeNode.id,
                type: "GET",
                dataType: "text",
                success: function (data) {

                    window.location.href = '/admin/projects/' + data;
                },
                error: function (e) {

                   console.log(e);
                }
            });
        }
    }
}

function zTreeProjectOnClick(event, treeId, treeNode, clickFlag) {
    
     if (!treeNode.isParent) {
         
        $.ajax({

            global: false,
            type: "GET",
            contentType: "application/json",
            url: "/api/documents/document/" + treeNode.linkId,
            dataType: "json",
            cache: false
        })
        .success(function (data) {

            window.location.href = "/" + data.documentType.contentDescriptor.componentName + "/document/" + data.uuId;
        });
     }
}

function zTreeParentContextMenu(treeNode) {
    
    $.ajax({
        
        global: false,
        type: "GET",
        url: '/modals/project/project-folder-context-menu.html',
        success: function (data) {
            
            var box = bootbox.confirm({

                closeButton: false,
                title: 'Folder Menu',
                size: 'small',
                message: data,
                buttons: {
                    cancel: {
                        label: "Cancel",
                        className: "btn-danger btn-fixed-width-100"
                    },
                    confirm: {
                        label: "Ok",
                        className: "btn-success btn-fixed-width-100"
                    }
                },
                callback: function (result) {
                    
                    if (result) {
                        
                        var data = {};
                        data['folderId'] = document.getElementById('folderId').value;
                        data['folderMenuOption'] = $("input[name='folderMenuOption']:checked").val();
                        
                        switch(data.folderMenuOption) {
                            
                            case 'rename':
                                
                                RenameFolder(treeNode.id, treeNode.name);
                                break;
                            case 'duplicate':
                                
                                DuplicateFolderSubTree(treeNode.id);
                                break;
                            
                            case 'subfolder':
                                
                                CreateFolder(treeNode.id);
                                break;
                                
                            case 'document':
                                
                                CreateDocument(treeNode.id);
                                break;
                                
                            case 'delete':
                                
                                DeleteFolder(treeNode.id);
                                break;
                        };
                    }
                }
            });
            
            box.on("shown.bs.modal", function(e) {

                document.getElementById('folderId').value = treeNode.id;
                $('#folderName').append(' - ' + treeNode.name);
            });
            
            box.modal('show');
        }
    });
}

function zTreeLeafContextMenu(treeNode) {
    
    $.ajax({
        
        global: false,
        type: "GET",
        url: '/modals/project/project-document-context-menu.html',
        success: function (data) {
            
            var box = bootbox.confirm({

                closeButton: false,
                title: 'Document Menu',
                size: 'small',
                message: data,
                buttons: {
                    cancel: {
                        label: "Cancel",
                        className: "btn-danger btn-fixed-width-100"
                    },
                    confirm: {
                        label: "Ok",
                        className: "btn-success btn-fixed-width-100"
                    }
                },
                callback: function (result) {
                    
                    if (result) {
                        
                        var data = {};
                        data['documentId'] = document.getElementById('documentId').value;
                        data['documentMenuOption'] = $("input[name='documentMenuOption']:checked").val();
                        
                        switch(data.documentMenuOption) {
                            
                            case 'rename':
                                
                                RenameDocument(treeNode.linkId, treeNode.name);
                                break;
                            case 'duplicate':
                                
                                DuplicateDocument(treeNode.linkId);
                                break;
                            
                            case 'edit':
                                
                                EditDocumentProperties(treeNode.linkId);
                                break;
                                
                            case 'delete':
                                
                                DeleteDocument(treeNode.linkId);
//                                ConfirmDocumentDeleted(treeNode.id);
                                break;
                        };
                    }
                }
            });
            
            box.on("shown.bs.modal", function(e) {

                document.getElementById('documentId').value = treeNode.linkId;
                $('#documentName').append(treeNode.name);
            });
            
            box.modal('show');
        }
    });
}

function RenameFolder(folderId, folderName) {
    
    $.ajax({
        
        global: false,
        type: "GET",
        url: '/modals/project/project-folder-rename.html',
        success: function (data) {
            
            var box = bootbox.confirm({

                closeButton: false,
                title: 'Rename Folder',
                size: 'small',
                message: data,
                buttons: {
                    cancel: {
                        label: "Cancel",
                        className: "btn-danger btn-fixed-width-100"
                    },
                    confirm: {
                        label: "Rename",
                        className: "btn-success btn-fixed-width-100"
                    }
                },
                callback: function (result) {
                    
                    if (result) {
                        
                        var data = {};

                        data['Id'] = document.getElementById('folderId').value;
                        data['itemName'] = document.getElementById('newFolderName').value;

                        $.ajax({
                            
                            global: false,
                            type: "POST",
                            contentType: "application/json",
                            url: "/api/admin/folders/rename/" + folderId,
                            data: JSON.stringify(data),
                            dataType: "json",
                            timeout: 60000,
                            success: function (data) {
                                
                                swal({
                                    
                                    title: "Rename successful",
                                    text: "Folder successfully renamed to " + data.folderName + "!",
                                    type: "success"
                                },
                                function() {
                                   
                                    zTreeProjectLoad();
                                    zTreeExpandByIdNode(data.id);
                                });
                            }
                        });
                    }
                }
            });
            
            box.on("shown.bs.modal", function(e) {
                
                document.getElementById('folderId').value = folderId;
                document.getElementById('currentName').value = folderName;
                document.getElementById('currentName').disabled = true;
            });
            
            box.modal('show');
        }
    });
}

function RenameDocument(documentId, documentName) {
    
    $.ajax({
        
        global: false,
        type: "GET",
        url: '/modals/project/project-document-rename.html',
        success: function (data) {
            
            var box = bootbox.confirm({

                closeButton: false,
                title: 'Rename Document',
                size: 'small',
                message: data,
                buttons: {
                    cancel: {
                        label: "Cancel",
                        className: "btn-danger btn-fixed-width-100"
                    },
                    confirm: {
                        label: "Rename",
                        className: "btn-success btn-fixed-width-100"
                    }
                },
                callback: function (result) {
                    
                    if (result) {
                        
                        var data = {};

                        data['Id'] = document.getElementById('documentId').value;
                        data['itemName'] = document.getElementById('newDocumentName').value;
                        
                        console.log(data);

                        $.ajax({
                            
                            global: false,
                            type: "POST",
                            contentType: "application/json",
                            url: "/api/admin/documents/rename/" + documentId,
                            data: JSON.stringify(data),
                            dataType: "json",
                            timeout: 60000,
                            success: function (data) {
                                
                                swal({
                                    
                                    title: "Rename successful",
                                    text: "Document successfully renamed to " + data.documentName + "!",
                                    type: "success"
                                },
                                function() {
                                   
                                    zTreeProjectLoad();
                                });
                            }
                        });
                    }
                }
            });
            
            box.on("shown.bs.modal", function(e) {
                
                document.getElementById('documentId').value = documentId;
                document.getElementById('currentName').value = documentName;
                document.getElementById('currentName').disabled = true;
                document.getElementById('newDocumentName').value = documentName;
            });
            
            box.modal('show');
        }
    });
}

function CreateFolder(folderId) {
    
    $.ajax({
        
        global: false,
        type: "GET",
        url: '/modals/project/project-folder-create-new.html',
        success: function (data) {
            
            var box = bootbox.confirm({

                closeButton: false,
                title: 'Create Folder',
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

                        data['parentId'] = folderId;
                        data['projectId'] = document.getElementById('projectId').value;
                        data['folderTypeId'] = document.getElementById('folderTypeId').value;
                        data['folderName'] = document.getElementById('folderName').value;
                        
                        $.ajax({
                            
                            global: false,
                            type: "POST",
                            contentType: "application/json",
                            url: "/api/admin/folders/create/new",
                            data: JSON.stringify(data),
                            dataType: "json",
                            timeout: 60000,
                            success: function (data) {
                                
                                swal({
                                    
                                    title: "Folder created",
                                    text: "Folder " + data.folderName + " successfully created!",
                                    type: "success"
                                },
                                function() {
                                   
                                    zTreeProjectLoad();
                                    zTreeExpandByIdNode(data.id);
                                });
                            }
                        });
                    }
                }
            });
            
            box.on("shown.bs.modal", function(e) {
                
                $.ajax({

                        type: "GET",
                        contentType: "application/json",
                        url: "/api/folders/folder/" + folderId,
                        dataType: "json",
                        cache: false
                    })
                    .done(function (data) {
                        
                        $(e.currentTarget).find('input[name="project"]').prop('value', data.project.projectName);
                        $(e.currentTarget).find('input[name="parentFolder"]').prop('value', data.folderName);
                        
                        $(e.currentTarget).find('input[name="projectId"]').prop('value', data.project.id);
                        $(e.currentTarget).find('input[name="parentId"]').prop('value', data.parent !== null ? data.parent.id : '');
                        
                        $.ajax({

                            type: "GET",
                            contentType: "application/json",
                            url: "/api/folders/foldertypes/list",
                            dataType: "json",
                            cache: false
                        })
                        .done(function (data) {
                            
                            var selected = '';
                    
                            $(e.currentTarget).find('select[name="folderTypeId"]').empty();
                            
                            $.each(data, function (key, index) {

                                $(e.currentTarget).find('select[name="folderTypeId"]').append('<option value="' + index.id + '">' + index.typeName + ' Folder</option>');
                                
                                if (index.typeKey === "FOLDER_DOCUMENT") { selected = index.id; }
                            });
                            
                            $(e.currentTarget).find('select[name="folderTypeId"]').prop('value', selected);
                        });
                    });
            });
            
            box.modal('show');
        }
    });
}

function CreateDocument(treeNodeId) {
    
    $.ajax({
        
        global: false,
        type: "GET",
        url: '/modals/project/project-document-create-new.html',
        success: function (data) {
            
            var box = bootbox.confirm({

                closeButton: false,
                title: 'Create New Document',
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
                        
                        data['projectId'] = document.getElementById('projectId').value;
                        data['folderId'] = treeNodeId;
                        data['typeId'] = document.getElementById('documentType').value;
                        data['infoClassId'] = document.getElementById('infoClassId').value;
                        data['identifier'] = document.getElementById('documentNumber').value;
                        data['documentName'] = document.getElementById('documentName').value;
                        data['documentLongName'] = document.getElementById('documentLongName').value;
                        data['documentDescription'] = document.getElementById('documentDescription').value;
                       
                        $.ajax({
                            
                            global: false,
                            type: "POST",
                            contentType: "application/json",
                            url: "/api/documents/document/create/new",
                            data: JSON.stringify(data),
                            dataType: "json",
                            timeout: 60000,
                            success: function (data) {
                                
                                swal({
                                    
                                    title: "Document created",
                                    text: "Document " + data.documentName + " successfully created!",
                                    type: "success"
                                },
                                function() {
                                   
                                    zTreeProjectLoad();
                                });
                            }
                        });
                    }
                }
            });
            
            box.on("shown.bs.modal", function(e) {
             
                $.ajax({

                    type: "GET",
                    contentType: "application/json",
                    url: "/api/folders/folder/" + treeNodeId,
                    dataType: "json",
                    cache: false
                })
                .done(function (data) {
            
                    var docCodeRoot = data.project.projectCode + '-' + data.project.projectSponsor.companyCode + '-';

                    $(e.currentTarget).find('input[name="documentProject"]').prop('value', data.project.projectName);
                    $(e.currentTarget).find('input[name="documentFolder"]').prop('value', data.folderName);
                    $(e.currentTarget).find('input[name="documentNumber"]').prop('value', docCodeRoot);

                    $(e.currentTarget).find('input[name="folderId"]').prop('value', data.parent.id);
                    $(e.currentTarget).find('input[name="projectId"]').prop('value', data.project.id);
                    $(e.currentTarget).find('input[name="docCodeRoot"]').prop('value', docCodeRoot);
 
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

                            $(e.currentTarget).find('select[name="documentType"]').append('<option value="' + index.id + '">' + index.documentTypeName + ' (' + index.typeCode + ')</option>');
                        });
                        
                        $(e.currentTarget).find('input[name="documentNumber"]').prop('value', docCodeRoot + data[0].typeCode+ '-#####');
                    });
                });
                
                getInformationClassesList();
            });
            
            box.modal('show');
        }
    });
}

function EditDocumentProperties(documentId) {
    
    $.ajax({
        
        global: false,
        type: "GET",
        url: '/modals/project/project-document-edit.html',
        success: function (data) {
            
            var box = bootbox.confirm({

                closeButton: false,
                title: 'Edit Document Properties',
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
                        
                        data['id'] = documentId;
                        data['projectId'] = document.getElementById('projectId').value;
                        data['folderId'] = document.getElementById('folderId').value;
                        data['typeId'] = document.getElementById('documentType').value;
                        data['infoClassId'] = document.getElementById('infoClassId').value;
                        data['identifier'] = document.getElementById('documentNumber').value;
                        data['documentName'] = document.getElementById('documentName').value;
                        data['documentLongName'] = document.getElementById('documentLongName').value;
                        data['documentDescription'] = document.getElementById('documentDescription').value;
                        data['ownerId'] = document.getElementById('ownerId').value;
                        data['status'] = document.getElementById('status').value;
                        data['documentIssue'] = document.getElementById('documentIssue').value;
                       
                        $.ajax({
                            
                            global: false,
                            type: "POST",
                            contentType: "application/json",
                            url: "/api/documents/document/update",
                            data: JSON.stringify(data),
                            dataType: "json",
                            timeout: 60000,
                            success: function (data) {
                                
                                swal({
                                    
                                    title: "Document Updated",
                                    text: "Document " + data.documentName + " successfully created!",
                                    type: "success"
                                },
                                function() {
                                   
                                    zTreeProjectLoad();
                                });
                            }
                        });
                    }
                }
            });
            
            box.on("shown.bs.modal", function(e) {
             
                var projectUuId = '';
                var ownerId = '';
                var docStatus = '';
                var infoClassId = '';
                var docTypeId = '';
                
                $.ajax({

                    type: "GET",
                    contentType: "application/json",
                    url: "/api/documents/document/" + documentId,
                    dataType: "json",
                    cache: false
                })
                .done(function (data) {
                    
                    $(e.currentTarget).find('input[name="documentProject"]').prop('value', data.project.projectName);
                    $(e.currentTarget).find('input[name="documentFolder"]').prop('value', data.folder.folderName);
                    $(e.currentTarget).find('input[name="documentNumber"]').prop('value', data.identifier);
                    $(e.currentTarget).find('input[name="documentName"]').prop('value', data.documentName);
                    $(e.currentTarget).find('input[name="documentIssue"]').prop('value', data.documentIssue);
                    $(e.currentTarget).find('input[name="documentLongName"]').prop('value', data.documentLongName);
                    $(e.currentTarget).find('textarea[name="documentDescription"]').prop('value', data.documentDescription);

                    $(e.currentTarget).find('input[name="projectId"]').prop('value', data.project.id);
                    $(e.currentTarget).find('input[name="folderId"]').prop('value', data.folder.id);

                    projectUuId = data.project.uuId;
                    ownerId = data.owner.id;
                    docTypeId = data.documentType.id;
                    infoClassId = data.infoClass.id;
                    docStatus = data.status;
                    
                    // Pupolate document types
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

                            $(e.currentTarget).find('select[name="documentType"]').append('<option value="' + index.id + '">' + index.documentTypeName + ' (' + index.typeCode + ')</option>');
                        });
                        
                        $(e.currentTarget).find('select[name="documentType"]').prop('value', docTypeId);
                    });
 
                    // Pupolate project members
                    $.ajax({

                        type: "GET",
                        contentType: "application/json",
                        url: "/api/admin/projects/project/members/list/" + projectUuId,
                        dataType: "json",
                        cache: false
                    })
                    .done(function (data) {

                        $(e.currentTarget).find('select[name="ownerId"]').empty();

                        $.each(data, function (key, index) {

                            $(e.currentTarget).find('select[name="ownerId"]').append('<option value="' + index.id + '">' + index.email + '</option>');
                        });
                        
                        $(e.currentTarget).find('select[name="ownerId"]').prop('value', ownerId);
                    });
 
                    // Pupolate documents status list
                    $.ajax({

                        type: "GET",
                        contentType: "application/json",
                        url: "/api/documents/status/list",
                        dataType: "json",
                        cache: false
                    })
                    .done(function (data) {

                        var selectOptions = '';

                        $.each(data, function (key, index) {

                            selectOptions = selectOptions + '<option value="' + index + '">' + index + '</option>';
                        });

                        $('#status').empty();
                        $('#status').append(selectOptions);
                        
                        $(e.currentTarget).find('select[name="status"]').prop('value', docStatus);
                    });

                    $.ajax({

                        type: "GET",
                        contentType: "application/json",
                        url: "/api/project/infoclass/list",
                        dataType: "json",
                        cache: false
                    })
                    .done(function (data) {

                        var selectOptions = '';

                        $.each(data, function (key, index) {

                            selectOptions = selectOptions + '<option value="' + index.id + '">' + index.className + '</option>';
                        });

                        $('#infoClassId').empty();
                        $('#infoClassId').append(selectOptions);

                        $(e.currentTarget).find('select[name="infoClassId"]').prop('value', infoClassId);
                    });
                });
            });
            
            box.modal('show');
        }
    });
}

function DeleteFolder(folderId) {
    
    $.ajax({

        global: false,
        type: "GET",
        contentType: "application/json",
        url: "/api/folders/folder/emptycheck/" + folderId,
        dataType: "json",
        cache: false,
        success: function (data) {
            
           if (data > 0) {
               
                swal({
                    
                    title: "Detele failed.",
                    text: "The folder cannot be deleted because it is not empty!",
                    type: "error"
                });
               
           } else {
               
                $.ajax({

                    global: false,
                    type: "GET",
                    contentType: "application/json",
                    url: "/api/folders/folder/delete/" + folderId,
                    dataType: "json",
                    cache: false,
                    success: function() {
                        
                        swal({

                             title: "Folder deleted.",
                             text: "The folder has been successfully deleted!",
                             type: "success"
                        },
                        function() {

                            zTreeProjectLoad();
                        });
                    }
                });
           }
        },
        error: function (e) {
            
            console.log(e);
        }
    });
}

function DeleteDocument(documentId) {
    
    swal({
        
        title: "Are you sure?",
        text: "You will not be able to recover this document version!",
        type: "warning",
        showCancelButton: true,
        confirmButtonColor: '#3085d6',
        cancelButtonColor: '#d33',
        confirmButtonText: 'Yes, Delete',
        cancelButtonText: 'No, Cancel',
        confirmButtonClass: 'btn btn-success',
        cancelButtonClass: 'btn btn-danger',
        buttonsStyling: false,
        closeOnConfirm: false,
        closeOnCancel: false
    },
    function(isConfirm) {
        
        if (isConfirm) {
            
            swal({
                
                title: 'Deleteing',
                text: 'The document will be deleted!',
                type: 'success'
                
            }, function() {
                
                $.ajax({

                    global: false,
                    type: "GET",
                    contentType: "application/json",
                    url: "/api/documents/document/delete/" + documentId,
                    dataType: "json",
                    cache: false,
                    success: function() {

                        swal({

                             title: "Document deleted.",
                             text: "The document has been successfully deleted!",
                             type: "success"
                        },
                        function() {

                            zTreeProjectLoad();
                        });
                    }
                });
            });

        } else {
            
            swal("Cancelled", "The document was not deleted.", "error");
        }
    });
    
}

function ConfirmDocumentDeleted(documentId) {
    
     $.ajax({

        global: false,
        type: "GET",
        contentType: "application/json",
        url: "/api/documents/document/{id}" + documentId,
        dataType: "json",
        cache: false
    })
    .success(function () {

        swal({

            title: "Failure!",
            text: "Document deltion failed!.",
            type: "error"

        },
        function() {

            window.location.reload();
        });
    })
    .error(function(){

        swal({

            title: "Success!",
            text: "Document has been deleted!.",
            type: "success"

        },
        function() {

            window.location.reload();
        });
    });
}

function onDocumentTypeChange() {
    
    var docTypeId = document.getElementById('documentType').value;
    var docCodeRoot = document.getElementById('documentNumber').value;

    $.ajax({

        type: "GET",
        contentType: "application/json",
        url: "/api/documents/types/byid/" + docTypeId,
        dataType: "json",
        cache: false
    })
    .done(function (data) {

       document.getElementById('documentNumber').value = docCodeRoot.substr(0, 12) + data.typeCode.toUpperCase() + '-#####';
    });
}

function DuplicateFolderSubTree(treeNodeId) {
    
    $.ajax({

        type: "GET",
        contentType: "application/json",
        url: "/api/folders/folder/duplicate/subtree/" + treeNodeId,
        dataType: "json",
        cache: false
    })
    .done(function (data) {
        
        console.log(data);

        swal({
                                    
            title: "Folder tree compied",
            text: "Folder " + data.folderName + " has been successfully duplicated!",
            type: "success"
        },
        function() {

            zTreeProjectLoad();
        });
    });
}

function DuplicateDocument(documentId) {
    
    $.ajax({

        type: "GET",
        contentType: "application/json",
        url: "/api/documents/document/duplicate/subtree/" + documentId,
        dataType: "json",
        cache: false
    })
    .done(function (data) {
        
        console.log(data);

        swal({
                                    
            title: "Document duplicated",
            text: "Document " + data.documentName + " has been successfully duplicated!",
            type: "success"
        },
        function() {

            zTreeProjectLoad();
        });
    });
}

function getInformationClassesList() {
    
    $.ajax({

        type: "GET",
        contentType: "application/json",
        url: "/api/project/infoclass/list",
        dataType: "json",
        cache: false
    })
    .done(function (data) {

        var selectOptions = '';

        $.each(data, function (key, index) {

            selectOptions = selectOptions + '<option value="' + index.id + '">' + index.className + '</option>';
        });

        $('#infoClassId').empty();
        $('#infoClassId').append(selectOptions);
    });
}

function ProjectCreateNew() {
 
    $.ajax({
        
        global: false,
        type: "GET",
        url: '/modals/project/project-admin-create-new.html',
        success: function (data) {
            
            var box = bootbox.confirm({

                closeButton: false,
                title: 'Create New Project',
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
                        
                        data['projectName'] = document.getElementById('projectName').value;
                        data['projectShortName'] = document.getElementById('projectShortName').value;
                        data['companyId'] = document.getElementById('companyId').value;
                        data['projectTypeId'] = document.getElementById('projectTypeId').value;
                        data['infoClassId'] = document.getElementById('infoClassId').value;
                        data['projectNumber'] = document.getElementById('projectNumber').value;
                        data['projectDescription'] = document.getElementById('projectDescription').value;
                        
                        $.ajax({
                            
                            global: false,
                            type: "POST",
                            contentType: "application/json",
                            url: "/api/admin/projects/project/new",
                            data: JSON.stringify(data),
                            dataType: "json",
                            timeout: 60000,
                            success: function (data) {

                                swal({
                                    
                                    title: "Project created",
                                    text: "New project " + data.projectName + " has been created!",
                                    type: "success"
                                },
                                function() {
                                   
                                    ProjectEditProperties(data.uuId);
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
                    url: "/api/admin/companies/list",
                    dataType: "json",
                    cache: false
                })
                .done(function (data) {

                    var selectOptions = '';

                    $.each(data, function (key, index) {

                        selectOptions = selectOptions + '<option value="' + index.id + '">' + index.companyName + '</option>';
                    });

                    $('#companyId').empty();
                    $('#companyId').append(selectOptions);
                    
                    $.ajax({

                        global: false,
                        type: "GET",
                        contentType: "application/json",
                        url: "/api/itemtypes/projecttype/list",
                        dataType: "json",
                        cache: false
                    })
                    .done(function (data) {

                        var selectOptions = '';

                        $.each(data, function (key, index) {

                            selectOptions = selectOptions + '<option value="' + index.id + '">' + index.typeName + '</option>';
                        });

                        $('#projectTypeId').empty();
                        $('#projectTypeId').append(selectOptions);
                        
                        $.ajax({

                            global: false,
                            type: "GET",
                            contentType: "application/json",
                            url: "/api/itemtypes/infoclass/list",
                            dataType: "json",
                            cache: false
                        })
                        .done(function (data) {

                            var selectOptions = '';

                            $.each(data, function (key, index) {

                                selectOptions = selectOptions + '<option value="' + index.id + '">' + index.className + '</option>';
                            });

                            $('#infoClassId').empty();
                            $('#infoClassId').append(selectOptions);
                        });
                    });
                });
            });
            
            box.modal('show');
        }
    });
}

function ProjectEditProperties(uuId) {
    
    $.ajax({
        
        global: false,
        type: "GET",
        url: '/modals/project/project-admin-properties-edit.html',
        success: function (data) {
            
            var box = bootbox.confirm({

                closeButton: false,
                title: 'Edit Project Properties',
                size:'large',
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
                        
                        data['projectNumber'] = document.getElementById('projectNumber').value;
                        data['projectName'] = document.getElementById('projectName').value;
                        data['projectShortName'] = document.getElementById('projectShortName').value;
                        data['companyId'] = document.getElementById('companyId').value;
                        data['contactId'] = document.getElementById('contactId').value;
                        data['managerId'] = document.getElementById('managerId').value;
                        data['projectTypeId'] = document.getElementById('projectTypeId').value;
                        data['infoClassId'] = document.getElementById('infoClassId').value;
                        data['projectDescription'] = document.getElementById('projectDescription').value;
                        data['startDate'] = document.getElementById('startDate').value;
                        data['endDate'] = document.getElementById('endDate').value;
                        data['status'] = document.getElementById('status').value;
                        
                        console.log(data);
                        
                        $.ajax({
                            
                            global: false,
                            type: "POST",
                            contentType: "application/json",
                            url: "/api/admin/projects/update/" + uuId,
                            data: JSON.stringify(data),
                            dataType: "json",
                            timeout: 60000,
                            success: function () {

                                swal({
                                    
                                    title: "Project updated",
                                    text: "The project " + data.projectName + " has been updated!",
                                    type: "success"
                                },
                                function() {
                                   
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
                    url: "/api/admin/projects/project/members/list/" + uuId,
                    dataType: "json",
                    cache: false
                })
                .done(function (data) {

                    var selectOptions = '';

                    $.each(data, function (key, index) {

                        selectOptions = selectOptions + '<option value="' + index.id + '">' + index.name + '</option>';
                    });

                    $('#managerId').empty();
                    $('#managerId').append(selectOptions);
                    
                    $.ajax({

                        global: false,
                        type: "GET",
                        contentType: "application/json",
                        url: "/api/admin/projects/project/members/list/" + uuId,
                        dataType: "json",
                        cache: false
                    })
                    .done(function (data) {

                        var selectOptions = '';

                        $.each(data, function (key, index) {

                            selectOptions = selectOptions + '<option value="' + index.id + '">' + index.name + '</option>';
                        });

                        $('#contactId').empty();
                        $('#contactId').append(selectOptions);

                        $.ajax({

                            global: false,
                            type: "GET",
                            contentType: "application/json",
                            url: "/api/admin/companies/list",
                            dataType: "json",
                            cache: false
                        })
                        .done(function (data) {

                            var selectOptions = '';

                            $.each(data, function (key, index) {

                                selectOptions = selectOptions + '<option value="' + index.id + '">' + index.companyName + '</option>';
                            });

                            $('#companyId').empty();
                            $('#companyId').append(selectOptions);
                            
                            $.ajax({

                                global: false,
                                type: "GET",
                                contentType: "application/json",
                                url: "/api/itemtypes/projecttype/list",
                                dataType: "json",
                                cache: false
                            })
                            .done(function (data) {

                                var selectOptions = '';

                                $.each(data, function (key, index) {

                                    selectOptions = selectOptions + '<option value="' + index.id + '">' + index.typeName + '</option>';
                                });

                                $('#projectTypeId').empty();
                                $('#projectTypeId').append(selectOptions);
                                
                                $.ajax({

                                    global: false,
                                    type: "GET",
                                    contentType: "application/json",
                                    url: "/api/itemtypes/infoclass/list",
                                    dataType: "json",
                                    cache: false
                                })
                                .done(function (data) {

                                    var selectOptions = '';

                                    $.each(data, function (key, index) {

                                        selectOptions = selectOptions + '<option value="' + index.id + '">' + index.className + '</option>';
                                    });

                                    $('#infoClassId').empty();
                                    $('#infoClassId').append(selectOptions);
                                    
                                    $.ajax({

                                        global: false,
                                        type: "GET",
                                        contentType: "application/json",
                                        url: "/api/admin/projects/status/list",
                                        dataType: "json",
                                        cache: false
                                    })
                                    .done(function (data) {

                                        var selectOptions = '';

                                        $.each(data, function (key, index) {

                                            selectOptions = selectOptions + '<option value="' + index + '">' + index.substring(7) + '</option>';
                                        });

                                        $('#status').empty();
                                        $('#status').append(selectOptions);
                                    
                                        $.ajax({

                                            global: false,
                                            type: "GET",
                                            contentType: "application/json",
                                            url: "/api/admin/projects/get/" + uuId,
                                            dataType: "json",
                                            cache: false
                                        })
                                        .done(function (data) {

                                            // Load date picker
                                            $('#startDate').datepicker({ format: 'yyyy-mm-dd', orientation: 'bottom left' });
                                            $('#endDate').datepicker({ format: 'yyyy-mm-dd', orientation: 'bottom left' });
                                            $('#startDate').datepicker( "update", new Date(data.startDate).toISOString().substring(0, 10));
                                            $('#endDate').datepicker('update', new Date(data.endDate).toISOString().substring(0, 10));

                                            // Load other controls
                                            $('#projectNumber').prop('value', data.projectNumber);
                                            $('#projectName').prop('value', data.projectName);
                                            $('#projectShortName').prop('value', data.projectShortName);
                                            $('#companyId').prop('value', data.projectSponsor.id);
                                            $('#projectTypeId').prop('value', data.projectType.id);
                                            $('#infoClassId').prop('value', data.infoClass.id);
                                            $('#contactId').prop('value', data.projectContact.id);
                                            $('#managerId').prop('value', data.projectManager.id);
                                            $('#status').prop('value', data.status);
                                            $('#projectDescription').prop('value', data.projectDescription);
                                        });
                                    });
                                });
                            });
                        });
                    });
                });
            });
            
            box.modal('show');
        }
    });
}

function ProjectManageMembers(uuId) {
    
    $.ajax({
        
        global: false,
        type: "GET",
        url: '/modals/project/project-admin-project-members-add.html',
        success: function (data) {
            
            var box = bootbox.confirm({

                global: false,
                closeButton: false,
                title: 'Project Membership',
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
                       
                        var membersCount = $('#projectMembersList option').length;
                        
                        var members = [];
                        
                        for (i = 0; i < membersCount; i++) {
                            
                            var member = {};
                            
                            member['id'] = parseInt($('#projectMembersList option').eq(i).val(), 10);
                            member['itemValue'] = $('#projectMembersList option').eq(i).html();
                            members.push(member);
                        }
                        
                        console.log(members);
                        console.log(JSON.stringify(members));
                        
                        $.ajax({

                            global: false,
                            type: "POST",
                            contentType: "application/json",
                            url: "/api/admin/projects/members/add/" + uuId,
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
                    url: '/api/admin/users/user/list',
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
                        url: "/api/admin/projects/project/members/list/" + uuId,
                        dataType: "json",
                        cache: false
                    })
                    .done(function (data) {

                        console.log(data);
                        
                        var selectMembersOptions = '';

                        $.each(data, function (key, index) {

                            selectMembersOptions = selectMembersOptions + '<option value="' + index.id + '">' + index.name + '</option>';

                            // Remove option from main list
                            $('#availableUsersList option[value="' + index.id + '"]').remove();
                        });

                        $('#projectMembersList').append(selectMembersOptions);

                    });
                });
            });
            
            box.modal('show');
        }
    });    
}

function getProjectUsersList() {
    
    $.ajax({

        global: false,
        type: "GET",
        contentType: "application/json",
        url: "/api/admin/projects/members/list",
        dataType: "json",
        cache: false
    })
    .done(function (data) {

        console.log(data);

        var selectOptions = '';

        $.each(data, function (key, index) {

            selectOptions = selectOptions + '<option value="' + index.id + '">' + index.firstName + ' ' + index.lastName + '</option>';
        });

        $('#managerId').empty();
        $('#managerId').append(selectOptions);
    });
}

function AddRemoveMembers(btn) {

    if (btn === 'RoleToRight') {

        var selectedOpts = $('#availableUsersList option:selected');
        if (selectedOpts.length === 0) {
            return false;
        }

        $('#projectMembersList').append($(selectedOpts).clone());
        $(selectedOpts).remove();
    }

    if (btn === 'RolesAllToRight') {
        
        var selectedOpts = $('#availableUsersList option');
        if (selectedOpts.length === 0) {
            return false;
        }

        $('#projectMembersList').append($(selectedOpts).clone());
        $(selectedOpts).remove();
    }

    if (btn === 'RoleToLeft') {
        
        var selectedOpts = $('#projectMembersList option:selected');
        if (selectedOpts.length === 0) {
            return false;
        }

        $('#availableUsersList').append($(selectedOpts).clone());
        $(selectedOpts).remove();
    }

    if (btn === 'RolesAllToLeft') {
        
        var selectedOpts = $('#projectMembersList option');
        if (selectedOpts.length === 0) {
            return false;
        }

        $('#availableUsersList').append($(selectedOpts).clone());
        $(selectedOpts).remove();
    }
}