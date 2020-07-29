$(function(){
    let input = $('#queryName');
    let inputNavbar = $('#queryNameNavbar');
    let csrf = $('input[name=\'_csrf\']');
    console.log(csrf.val());
    if(input.length){
        // noinspection JSUnusedGlobalSymbols
        input.autocomplete({
            source: function( request, response ) {
                let search = {};
                search["fileName"] = input.val();
                search['csrf'] = csrf.val();
                // noinspection JSUnusedGlobalSymbols
                $.ajax( {
                    type: "POST",
                    headers: {"X-CSRF-TOKEN" : csrf.val()},
                    contentType: "application/json",
                    url: "/api/search",
                    data: JSON.stringify(search),
                    dataType: 'json',
                    cache: false,
                    timeout: 600000,
                    success: function( data ) {
                        response( data.content );
                    }
                } );
            },
            minLength: 3,
        });
    }
    if(inputNavbar.length){
        // noinspection JSUnusedGlobalSymbols
        inputNavbar.autocomplete({
            source: function( request, response ) {
                let search = {};
                search["fileName"] = inputNavbar.val();
                search['csrf'] = csrf.val();
                // noinspection JSUnusedGlobalSymbols
                $.ajax( {
                    type: "POST",
                    headers: {"X-CSRF-TOKEN" : csrf.val()},
                    contentType: "application/json",
                    url: "/api/search",
                    data: JSON.stringify(search),
                    dataType: 'json',
                    cache: false,
                    timeout: 600000,
                    success: function( data ) {
                        response( data.content );
                    }
                } );
            },
            minLength: 3,
        });
    }
});
