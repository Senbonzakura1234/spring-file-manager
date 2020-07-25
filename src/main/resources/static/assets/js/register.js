$(document).ready(function(){
    let check = $('#check-password');
    let iconOn = $('#check-password #icon-on');
    let iconOff = $('#check-password #icon-off');
    check.click(function(){
        let inputTypePassword  = $('#register-form :password');
        let password = $('#password');
        if (inputTypePassword.length) {
            password.attr('type', 'text');
            iconOn.removeClass("d-none")
            iconOff.addClass("d-none");
        } else {
            password.attr('type', 'password');
            iconOn.addClass("d-none");
            iconOff.removeClass("d-none");
        }
    });
});
