/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/* global bootbox, swal */

function UserNewCreate() {
    
    $.ajax({
        
        global: false,
        type: "GET",
        url: '/modals/user/user-new-create.html',
        success: function (data) {
            
            var box = bootbox.confirm({

                closeButton: false,
                title: 'Create New User',
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
                        
                        var data = {};
                        
                        data['email'] = document.getElementById('email').value;
                        data['password'] = document.getElementById('password').value;
                        data['confirmPassword'] = document.getElementById('confirmPassword').value;
                        
                        $.ajax({
                            
                            global: false,
                            type: "POST",
                            contentType: "application/json",
                            url: "/api/users/create/new",
                            data: JSON.stringify(data),
                            dataType: "json",
                            timeout: 60000,
                            success: function (data) {

                                swal({

                                    title: "Success",
                                    text: "New user has been created!.",
                                    type: "success"
                                    
                                },
                                function() {

                                    UserBasicProfileUpdate(data.uuId);
                                });
                            }, 
                            statusCode: {
                                
                                400: function() {
                                    
                                    swal({
                                        
                                        title: "Password error",
                                        text: "Password and confirmation do not match.",
                                        type: "error"
                                    },
                                    function() {

                                        UserNewCreate();
                                    });
                                },
                                409: function() {

                                    swal({
                                        
                                        title: "Duplicate email address",
                                        text: "User with this email already exists.",
                                        type: "error"
                                    },
                                    function() {

                                        UserNewCreate();
                                    });
                                }
                            }
                        });
                    }
                }
            });
            
            box.on("shown.bs.modal", function(e) {
                
                // TBD
            });
            
            box.modal('show');
        }
    });
}

function UserBasicProfileUpdate(uuId) {
    
    $.ajax({
        
        global: false,
        type: "GET",
        url: '/modals/user/user-details-update.html',
        success: function (data) {
            
            var box = bootbox.confirm({

                closeButton: false,
                title: 'Update Basic User Information',
                message: data,
                buttons: {
                    cancel: {
                        label: "Cancel",
                        className: "btn-danger btn-fixed-width-100"
                    },
                    confirm: {
                        label: "Update",
                        className: "btn-success btn-fixed-width-100"
                    }
                },
                callback: function (result) {
                    
                    if (result) {
                        
                        var data = {};
                        
                        data['uuid'] = uuId;
                        data['firstName'] = document.getElementById('firstName').value;
                        data['lastName'] = document.getElementById('lastName').value;
                        data['mainRole'] = document.getElementById('userJobTitle').value;
                        
                        $.ajax({
                            
                            global: false,
                            type: "POST",
                            contentType: "application/json",
                            url: "/api/user/update/" + uuId,
                            data: JSON.stringify(data),
                            dataType: "json",
                            timeout: 60000,
                            success: function () {

                                swal({

                                    title: "Success",
                                    text: "Profile for successfully updated!",
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
                
                GetUserInfo(uuId);
            });
            
            box.modal('show');
        }
    });
}

function UserContactDetailsUpdate(uuId) {
    
    $.ajax({
        
        global: false,
        type: "GET",
        url: '/modals/user/user-contacts-update.html',
        success: function (data) {
            
            var box = bootbox.confirm({

                closeButton: false,
                title: 'Update Contact Details',
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
                        
                        data['uuid'] = uuId;
                        data['phone'] = document.getElementById('phone').value;
                        data['mobile'] = document.getElementById('mobile').value;
                        data['website'] = document.getElementById('website').value;
                        data['address'] = document.getElementById('address').value;
//                        data['country'] = $('#country option:selected').html();
                        data['country'] = document.getElementById('country').value;
                        data['state'] = document.getElementById('state').value;
                        data['city'] = document.getElementById('city').value;
                        data['postCode'] = document.getElementById('postCode').value;
                        
                        $.ajax({
                            
                            global: false,
                            type: "POST",
                            contentType: "application/json",
                            url: "/api/admin/users/contact/save/" + uuId,
                            data: JSON.stringify(data),
                            dataType: "json",
                            timeout: 60000,
                            success: function () {

                                swal({
                                    title: "Contact updated!",
                                    text: "Contact details have been successfully updated.",
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
            
            box.on("shown.bs.modal", function() {
                
                $.ajax({

                    global: false,
                    type: "GET",
                    contentType: "application/json",
                    url: "/api/contacts/countries/list",
                    dataType: "json",
                    cache: false
                })
                .done(function (data) {
                   
                    var selectCountryOptions = '';
                    
                    $.each(data, function (key, index) {

                        selectCountryOptions = selectCountryOptions + '<option value="' + index.iso3 + '">' + index.countryName+ '</option>';
                    });
                    
                    $('#country').empty();
                    $('#country').append(selectCountryOptions);
                
                    GetUserContactDetails(uuId);
                });
            });
        }
    });
}

function GetUserInfo(uuId) {
    
    $.ajax({

        global: false,
        type: "GET",
        contentType: "application/json",
        url: "/api/user/byuuid/" + uuId,
        dataType: "json",
        cache: false
    })
    .done(function (data) {

        console.log(data);

        if (data.email !== null) {
            
            $('#email').prop('value', data.email);
        }

        if (data.firstName !== null) {
            
            $('#firstName').prop('value', data.firstName);
        }

        if (data.lastName !== null) {
            
            $('#lastName').prop('value', data.lastName);
        }

        if (data.mainRole !== null) {
            
            $('#userJobTitle').prop('value', data.mainRole);
        }
    });
}

function GetUserContactDetails(uuId) {
    
    $.ajax({

        global: false,
        type: "GET",
        contentType: "application/json",
        url: "/api/admin/users/contact/" + uuId,
        dataType: "json",
        cache: false
    })
    .done(function (data) {

        if (data.phone !== null) {
            
            $('#phone').prop('value', data.phone);
        }

        if (data.mobile !== null) {
            
            $('#mobile').prop('value', data.mobile);
        }

        if (data.address !== null) {
            
            $('#address').prop('value', data.address);
        }

        if (data.website !== null) {
            
            $('#website').prop('value', data.website);
        }

        if (data.country !== null) {
            
            $('#country').prop('value', data.country.iso3);
        }
        
        if (data.state !== null) {
                                            
            $('#state').prop('value', data.state);
        }
        
        if (data.city !== null) {
                                            
            $('#city').prop('value', data.city);
        }
        
        if (data.city !== null) {
                                            
            $('#postCode').prop('value', data.postCode);
        }
    });
}

function UserAdminPwdReset(uuId) {
    
    $.ajax({
        
        global: false,
        type: "GET",
        url: '/modals/user/user-admin-password-reset.html',
        success: function (data) {

            bootbox.confirm({

                closeButton: false,
                title: 'Reset Password',
                message: data,
                size: 'small',
                buttons: {
                    cancel: {
                        label: "Cancel",
                        className: "btn-danger btn-fixed-width-100"
                    },
                    confirm: {
                        label: "Reset",
                        className: "btn-success btn-fixed-width-100"
                    }
                },
                callback: function (result) {

                    if (result) {

                        var data = {};

                        data['uuId'] = uuId;
                        data['newPassword'] = document.getElementById('newPassword').value;
                        data['newPasswordConfirm'] = document.getElementById('confirmNewPassword').value;
                        
                        $.ajax({
                            
                            type: "POST",
                            contentType: "application/json",
                            url: "/api/admin/user/password/reset",
                            data: JSON.stringify(data),
                            dataType: "json",
                            timeout: 60000,
                            success: function () {

                                swal({

                                    title: "Success",
                                    text: "Password has been successfully reset!",
                                    type: "success"
                                    
                                });
                            }
                        });
                    }
                }
            }); 
        }
    });
}

function UserPwdChange(uuId) {
    
    $.ajax({
        
        global: false,
        type: "GET",
        url: '/modals/user/user-password-change.html',
        success: function (data) {

            bootbox.confirm({

                closeButton: false,
                title: 'Change Password',
                message: data,
                size: 'small',
                buttons: {
                    cancel: {
                        label: "Cancel",
                        className: "btn-danger btn-fixed-width-100"
                    },
                    confirm: {
                        label: "Change",
                        className: "btn-success btn-fixed-width-100"
                    }
                },
                callback: function (result) {

                    if (result) {

                        var data = {};

                        data['uuId'] = uuId;
                        data['currentPassword'] = document.getElementById('currentPassword').value;
                        data['newPassword'] = document.getElementById('newPassword').value;
                        data['newPasswordConfirm'] = document.getElementById('newPasswordConfirm').value;
                        
                        if (data['newPassword'] !== data['newPasswordConfirm']) {
                            
                            swal({
                                title: "Passwords do not match!",
                                text: "The new password and its confirmation must match.",
                                type: "error"
                            });

                        } else {
                        
                            $.ajax({

                                globql: false,
                                type: "POST",
                                contentType: "application/json",
                                url: "/api/user/password/change",
                                data: JSON.stringify(data),
                                dataType: "json",
                                timeout: 60000,
                                success: function () {

                                    swal({
                                        title: "Password changed!",
                                        text: "Password has been successfully changed.",
                                        type: "success"
                                    });
                                }
                            });
                        }
                    }
                }
            }); 
        }
    });
}

function DemoUsersUpload() {
    
    $.ajax({

        type: "GET",
        contentType: "application/json",
        url: '/data/people.json',
        dataType: "json",
        cache: false
    })
    .done(function (data) {
        
        var users = [];

        $.each(data, function (key, index) {

            var user = {};
            
            user['id'] = index.id;
            user['firstName'] = index.firstName;
            user['lastName'] = index.lastName;
            user['email'] = index.email;
            user['country'] = index.country;
            user['mainRole'] = index.mainRole;
               
            users.push(user);
        });

        $.ajax({
                            
            type: "POST",
            contentType: "application/json",
            url: "/api/users/demo/update",
            data: JSON.stringify(users),
            dataType: "json",
            timeout: 60000,
            success: function (count) {

                if (count === 1) {
                    
                    swal({
                        title: "Password changed!",
                        text: "Demo user data successfully updated!",
                        type: "success"
                    });
                    
                } else {
                    
                    swal({
                        title: "Password changed!",
                        text: "Demo user data is already up to date!",
                        type: "error"
                    });
                }
            }
        });
    });
}

function UserAdminDelete(uuId, user) {
    
    swal({
        
        title: "Do you want to delete " + user + "?",
        text: "You will not be able to recover this user profile!",
        type: "warning",
        showCancelButton: true,
        confirmButtonColor: "#DD6B55",
        confirmButtonText: "Yes, delete",
        cancelButtonText: "No, cancel",
        closeOnConfirm: false,
        closeOnCancel: false},
        function (isConfirm) {
            if (isConfirm) {

                $.ajax({

                    global: false,
                    type: "GET",
                    contentType: "application/json",
                    url: '/api/admin/users/delete/' + uuId,
                    dataType: "json",
                    cache: false
                })
                .done(function () {

                    swal("Deleted!", "The user has been successfully deleted.", "success");

                    setTimeout(function () {
                        history.go(-1);
                    }, 2000);
                })
                .error(function() {

                    swal("Failed!", "Failed to delete the user.", "error");
                });

            } else {

                swal("Cancelled", user + " was not deleted.", "error");
            }
    });
}

function UserAdminManageRoles(uuId) {
    
    $.ajax({
        
        global: false,
        type: "GET",
        url: '/modals/user/user-admin-roles-manage.html',
        success: function (data) {
            
            var box = bootbox.confirm({

                closeButton: false,
                title: 'Manage User Roles',
                message: data,
                buttons: {
                    cancel: {
                        label: "Cancel",
                        className: "btn-danger btn-fixed-width-100"
                    },
                    confirm: {
                        label: "Update",
                        className: "btn-success btn-fixed-width-100"
                    }
                },
                callback: function (result) {
                    
                    if (result) {
                       
                       var rolesListPara = '';
                        var rolesCount = $('#userRolesList option').length;
                        
                        var roles = [];
                        
                        for (i = 0; i < rolesCount; i++) {
                            
                            var role = {};
                            role['roleName'] = $('#userRolesList option').eq(i).val();
                            role['roleDisplayName'] = $('#userRolesList option').eq(i).html();
                            roles.push(role);
                            
                            rolesListPara = rolesListPara + '<p style="line-height: 90%">' + $('#userRolesList option').eq(i).html() + '</p>';
                        }
                        
                        $.ajax({

                            type: "POST",
                            contentType: "application/json",
                            url: "/api/users/roles/add/" + uuId,
                            data: JSON.stringify(roles),
                            dataType: "json",
                            timeout: 60000,
                            success: function (data) {
                              
                                swal({
                                    title: "Roles updated!",
                                    text: "Roles for " + data.firstName + " have been successfully updated.",
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
                    url: '/api/admin/users/roles/list',
                    dataType: "json",
                    cache: false
                })
                .done(function (data) {

                    var selectRoles = '';

                    $.each(data, function (key, index) {

                        selectRoles = selectRoles + '<option value="' + index.roleName + '">' + index.roleDisplayName + '</option>';
                    });

                    $('#availableRolesList').append(selectRoles);
                    
                    $.ajax({

                        type: "GET",
                        contentType: "application/json",
                        url: "/api/user/roles/" + uuId,
                        dataType: "json",
                        cache: false
                    })
                    .done(function (data) {

                        var selectRoleOptions = '';

                        $.each(data, function (key, index) {

                            selectRoleOptions = selectRoleOptions + '<option value="' + index.roleName + '">' + index.roleDisplayName + '</option>';

                            // Remove option from main list
                            $('#availableRolesList option[value="' + index.roleName + '"]').remove();
                        });

                        $('#userRolesList').append(selectRoleOptions);

                    });
                });
            });
            
            box.modal('show');
        }
    });
}

function AddRemoveRoles(btn) {

    if (btn === 'RoleToRight') {

        var selectedOpts = $('#availableRolesList option:selected');
        if (selectedOpts.length === 0) {
            return false;
        }

        $('#userRolesList').append($(selectedOpts).clone());
        $(selectedOpts).remove();
    }

    if (btn === 'RolesAllToRight') {
        
        var selectedOpts = $('#availableRolesList option');
        if (selectedOpts.length === 0) {
            return false;
        }

        $('#userRolesList').append($(selectedOpts).clone());
        $(selectedOpts).remove();
    }

    if (btn === 'RoleToLeft') {
        
        var selectedOpts = $('#userRolesList option:selected');
        if (selectedOpts.length === 0) {
            return false;
        }

        $('#availableRolesList').append($(selectedOpts).clone());
        $(selectedOpts).remove();
    }

    if (btn === 'RolesAllToLeft') {
        
        var selectedOpts = $('#userRolesList option');
        if (selectedOpts.length === 0) {
            return false;
        }

        $('#availableRolesList').append($(selectedOpts).clone());
        $(selectedOpts).remove();
    }
}

function UserAdminAssignProjects(uuId) {
    
    $.ajax({
        
        global: false,
        type: "GET",
        url: '/modals/user/user-admin-project-assign-user.html',
        success: function (data) {
            
            var box = bootbox.confirm({

                message: data,
                size: 'small',
                title: "Project Membership",
                buttons: {
                    cancel: {
                        label: "Cancel",
                        className: "btn-danger btn-fixed-width-100"
                    },
                    confirm: {
                        label: "Add Claim",
                        className: "btn-success btn-fixed-width-100"
                    }
                },
                callback: function (result) {
                    
                    if (result) {
                    
                        data = document.getElementById('userClaimProject').value;
                        
                        $.ajax({

                            global: false,
                            type: "POST",
                            contentType: "application/json",
                            url: "/api/admin/projects/add/user/" + uuId,
                            data: JSON.stringify(data),
                            dataType: "json",
                            timeout: 60000,
                            success: function () {

                                swal({
                                    title: "Project membership update!",
                                    text: "User has been successfully added to the project.",
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
                
                // Populate projects list
                $.ajax({

                    global: false,
                    type: "GET",
                    contentType: "application/json",
                    url: "/api/admin/projects/list",
                    dataType: "json",
                    cache: false
                })
                .done(function (data) {

                    var selectProjectsOptions = '';

                    $.each(data, function (key, index) {

                        selectProjectsOptions = selectProjectsOptions + '<option value="' + index.id + '">' + index.projectShortName + '</option>';
                    });

                    $('#userClaimProject').empty();
                    $('#userClaimProject').append(selectProjectsOptions);

                });
            });
            
            box.modal('show');
        }
    });
}
