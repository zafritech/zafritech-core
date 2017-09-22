function MarkSearchTerms() {
    
    console.lod('module executed');
    
    if ($('#isSearchPage').length > 0) {
       
        var str = $('#searchQueryString').html().replace(/\+/g, " ");

        var words = str.split(" ");

        for (var i = 0; i < words.length; i++) {
            
            var regexUpper = new RegExp("\\b" + words[i].charAt(0).toUpperCase() + words[i].slice(1) + "\\b", "g");
            var regexLower = new RegExp("\\b" + words[i].toLowerCase()+ "\\b", "g");
            var regexAllUpper = new RegExp("\\b" + words[i].toUpperCase()+ "\\b", "g");
            var highlightUpper = '<span style="background-color: yellow;">' + words[i].charAt(0).toUpperCase() + words[i].slice(1) + '</span>';
            var highlightLower = '<span style="background-color: yellow;">' + words[i].toLowerCase() + '</span>';
            var highlightAllLower = '<span style="background-color: yellow;">' + words[i].toUpperCase() + '</span>';
            
            $('#searchResultsBody .searchable').children().each(function () {

                $(this).html( $(this).html().replace(regexUpper, highlightUpper) );
                $(this).html( $(this).html().replace(regexLower, highlightLower) );
                $(this).html( $(this).html().replace(regexAllUpper, highlightAllLower) );
            });
        }
    }
}


