/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/* global bootbox, swal */

var zTreeObj = null;

$(document).ready(function () {
    
    zTreeLibraryLoad();
});

function zTreeLibraryLoad() {

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

            beforeClick: zTreeLibraryBeforeClick,
            onClick: zTreeLibraryOnClick
        }
    };

    $.ajax({

        global: false,
        url: '/api/library/folders/tree/list',
        type: "GET",
        dataType: "json",
        success: function (data) {

            var currentNode = '';
            
            zTreeObj = $.fn.zTree.init($("#libraryFolderTree"), setting, data);
//            $('#mainTreeHeaderLabel').text("Library");
//            $('#subTreeHeaderElement').hide();
//            $('#noOpenProjects').hide();
            
            // Expand current zTree node
            if ($('#nodeId').length > 0 && $('#nodeId').val().length !== 0) {

                var nodeId =  document.getElementById('nodeId').value;
                currentNode = zTreeObj.getNodeByParam("id", nodeId, null);
                
            } else {
                
                currentNode = zTreeObj.getNodeByParam("pId", 0, null);
            }
            
            $('#folderId').prop('value', currentNode.id);
            $('#libraryFolderTitle').text(currentNode.name);
            zTreeObj.expandNode(currentNode, true, false, true);
            
            loadLibraryItems(currentNode.id);
        }
    });
}

// Toggle open/close on clicking a parent node
function zTreeLibraryBeforeClick(treeId, treeNode) {
    
    var treeObj = $.fn.zTree.getZTreeObj("libraryFolderTree");
    
    if (treeNode.isParent) {
        
        treeObj.expandNode(treeNode, true, null, null);
        $('#libraryFolderTitle').text(buildBreadCrumbsString(treeNode));
    }
}

function zTreeLibraryOnClick(event, treeId, treeNode, clickFlag) {
    
    var folderId = treeNode.id;
    
    $('#folderId').prop('value', folderId);
    
    loadLibraryItems(folderId);
}

function loadLibraryItems(folderId) {
    
    var url = "/api/library/folder/items/list/" + folderId;
    
    $('#libraryItemsList').load(url);
}

function openFolder(folderId) {
     
    var zTreeObj = $.fn.zTree.getZTreeObj("libraryFolderTree");
    var node = zTreeObj.getNodeByParam("id", folderId, null);
    
    $('#libraryFolderTitle').text(buildBreadCrumbsString(node));
    loadLibraryItems(folderId);
}

function buildBreadCrumbsString(treeNode) {
    
    var zTreeObj = $.fn.zTree.getZTreeObj("libraryFolderTree");
    var breadCrumbs = treeNode.name;
    var nodeId = treeNode.pId;
    
    while(nodeId > 0) {
        
        var parent = zTreeObj.getNodeByParam("id", nodeId, null);
        breadCrumbs = parent.name + " :: " + breadCrumbs;
        
        nodeId = parent.pId;
    }
    
    return breadCrumbs;
}

function LibraryAddReference() {
    
    var folderId = document.getElementById('folderId').value;
    
    $.ajax({
            
        global: false,
        type: "GET",
        url: '/modals/library/library-reference-create-new.html',
        success: function (data) {
            
            var box = bootbox.confirm({

                closeButton: false,
                message: data,
                size: 'large',
                title: "New Reference Item",
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
                        
                        var form = $('#referenceUploadForm')[0];
                        var data = new FormData(form);
                        
                        $.ajax({
                            type: "POST",
                            enctype: 'multipart/form-data',
                            url: "/api/library/reference/items/add",
                            data: data,
                            processData: false,
                            contentType: false,
                            cache: false,
                            timeout: 600000,
                            success: function (data) {
                                
                                 swal({

                                    title: "Success!",
                                    text: "New item has been successfully created.",
                                    type: "success"
                                });
                                
                                setTimeout(function() { loadLibraryItems(folderId); }, 2000);
                            },
                            error: function (e) {

                                swal({

                                    title: "Error uploading file!",
                                    text: e.responseText,
                                    type: "error"
                                });
                            }
                        });
                    }
                }
            });
            
            box.on("shown.bs.modal", function(e) {
                
                var referenceTypes = ["ARTICLE", "BOOK", "STANDARD", "DOCUMENT", "URL"];
                
                $('#referenceType').empty();

                for(i = 0; i < referenceTypes.length; i++) {

                    $('#referenceType').append('<option value="' + referenceTypes[i] + '">' + referenceTypes[i] + '</option>');
                };

                $('#referenceType').prop('value', 'STANDARD');
                $(e.currentTarget).find('input[name="folderId"]').prop('value', folderId);
            });
            
            box.modal('show');
        }
    });
}

function LibraryAddFolder() {
    
    var folderId = document.getElementById('folderId').value;
    
    $.ajax({
            
        global: false,
        type: "GET",
        url: '/modals/library/library-folder-create-new.html',
        success: function (data) {
            
            var box = bootbox.confirm({

                closeButton: false,
                message: data,
                size: 'small',
                title: "New Folder",
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
                        data['id'] =  document.getElementById('folderId').value;
                        data['itemName'] =  document.getElementById('folderName').value;
                        
                        console.log(data);
                        
                        $.ajax({

                            closeButton: false,
                            type: "POST",
                            contentType: "application/json",
                            url: "/api/library/reference/folder/add",
                            data: JSON.stringify(data),
                            dataType: "json",
                            timeout: 60000,
                            success: function (data) {
                    
                                openFolder(folderId);
                                
                                swal({
                                    
                                    title: "Success!",
                                    text: "New folder has been successfully created.",
                                    type: "success"
                                });
                            }
                        });
                    }
                }
            });
            
            box.on("shown.bs.modal", function(e) {
                
                $(e.currentTarget).find('input[name="parentId"]').prop('value', folderId);
            });
            
            box.modal('show');
        }
    });
}