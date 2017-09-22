/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

function timeSince(date) {

    var seconds = Math.floor((new Date() - date) / 1000);

    var interval = Math.floor(seconds / 31536000);
    if (interval > 1) {
        
        return interval + " yrs ago";
    }
    
    interval = Math.floor(seconds / 2592000);
    if (interval > 1) {
        
        return interval + " mths ago";
    }
    
    interval = Math.floor(seconds / 86400);
    if (interval > 1) {
        
        return interval + " days ago";
    }
    interval = Math.floor(seconds / 3600);
    if (interval > 1) {
        
        return interval + " hrs ago";
    }
    
    interval = Math.floor(seconds / 60);
    if (interval > 1) {
        
        return interval + " mins ago";
    }
    
    return Math.floor(seconds) + " secs ago";
}


