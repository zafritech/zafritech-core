
function checkLogin() {
    
    $.ajax({
        
        global: false,
        url: '/api/login/check',
        type: "GET",
        dataType: "json",
        success: function() {
            
            window.location.replace('/login');
        }
    });
}