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

            console.log(data);
            
            zTreeObj = $.fn.zTree.init($("#libraryTree"), setting, data);

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
    
    var treeObj = $.fn.zTree.getZTreeObj("libraryTree");
    
    if (treeNode.isParent) {
        
        treeObj.expandNode(treeNode, true, null, null);
        $('#libraryFolderTitle').text(buildBreadCrumbsString(treeNode));
    }
}

function zTreeLibraryOnClick(event, treeId, treeNode, clickFlag) {
    
    $('#folderId').prop('value', treeNode.id);
    
    $.ajax({

        global: false,
        type: "GET",
        contentType: "application/json",
        url: "/api/library/folder/items/" + treeNode.id,
        dataType: "json",
        cache: false
    })
    .success(function (data) {

        if (data.length > 0) {
            
            $('#libraryItemsList').text("");
            
        } else {
            
            $('#libraryItemsList').text("No items found.");
        }
    });
}

function buildBreadCrumbsString(treeNode) {
    
    var zTreeObj = $.fn.zTree.getZTreeObj("libraryTree");
    var breadCrumbs = treeNode.name;
    var nodeId = treeNode.pId;
    
    while(nodeId > 0) {
        
        var parent = zTreeObj.getNodeByParam("id", nodeId, null);
        breadCrumbs = parent.name + " :: " + breadCrumbs;
        
        nodeId = parent.pId;
    }
    
    return breadCrumbs;
}