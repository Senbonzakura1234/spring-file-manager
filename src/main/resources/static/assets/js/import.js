$(function(){
    let form = $("#import-form");
    let fileInput = $("#fileInput");
    let csrf = $('input[name=\'_csrf\']');

    form.submit(function( event ) {
        event.preventDefault();



        if (fileInput.val() != null) {
            try {
                let file = jQuery.parseJSON(fileInput.val());
                $.ajax({
                    type: "POST",
                    headers: {"X-CSRF-TOKEN": csrf.val()},
                    contentType: "application/json",
                    url: "/data/import",
                    data: JSON.stringify(file),
                    dataType: 'json',
                    cache: false,
                    timeout: 600000,
                    success: function () {
                        window.location.href = "/";
                    },
                    error: function () {
                        $("#error").fadeIn();
                    }
                });
            } catch (e) {
                $("#error").fadeIn();
                console.log(e)
            }
        }else {
            $("#error").fadeIn();
        }
    });
});
