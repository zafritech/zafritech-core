/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/* global bootbox, swal */

var zTreeObj = null;

$(document).ready(function () {
    
    zTreeLibraryLoad();
    loadLibraryItems(0);
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
//            beforeRightClick: zTreeProjectBeforeRightClick,
//            onRightClick: zTreeProjectOnRightClick,
//            onDblClick: zTreeProjectOnDblClick
        }
    };

    $.ajax({

        global: false,
        url: '/api/library/folders/tree/list',
        type: "GET",
        dataType: "json",
        success: function (data) {

            zTreeObj = $.fn.zTree.init($("#mainFolderTree"), setting, data);
            $('#mainTreeHeaderLabel').text("Reference Library");
            $('#subTreeHeaderElement').hide();
            
            // Expand current zTree node
            if ($('#nodeId').length > 0 && $('#nodeId').val().length !== 0) {

                var nodeId =  document.getElementById('nodeId').value;
                var currentNode = zTreeObj.getNodeByParam("id", nodeId, null);

                zTreeObj.expandNode(currentNode, true, false, true);
                $('#libraryFolderTitle').text(currentNode.name);
                
            } else {
                
                var currentNode = zTreeObj.getNodeByParam("pId", 0, null);
                $('#libraryFolderTitle').text(currentNode.name);
            }
        }
    });
}

// Toggle open/close on clicking a parent node
function zTreeLibraryBeforeClick(treeId, treeNode) {
    
    var treeObj = $.fn.zTree.getZTreeObj("mainFolderTree");
    
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

function buildBreadCrumbsString(treeNode) {
    
    var zTreeObj = $.fn.zTree.getZTreeObj("mainFolderTree");
    var breadCrumbs = treeNode.name;
    var nodeId = treeNode.pId;
    
    while(nodeId > 0) {
        
        var parent = zTreeObj.getNodeByParam("id", nodeId, null);
        breadCrumbs = parent.name + " :: " + breadCrumbs;
        
        nodeId = parent.pId;
    }
    
    return breadCrumbs;
}