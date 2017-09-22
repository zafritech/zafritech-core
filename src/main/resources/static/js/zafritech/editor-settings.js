/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
$(function () {

    // Initialize summernote plugin
    $('.summernote').summernote({
        height: 300,
        toolbar: [
            ['font', ['fontname', 'fontsize', 'style', 'color']],
            ['style', ['bold', 'italic', 'underline', 'clear']],
            ['alignment', ['ul', 'ol', 'paragraph', 'lineheight']],
            ['insert', ['table', 'link', 'picture', 'hr']],
            ['help', ['help']]
        ]
    });

});

function editorClear() {
    
    $('#templateName').prop('value', '');
    $('#summernote').summernote('reset');
}