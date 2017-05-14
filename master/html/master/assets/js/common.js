/**
 * Created by SEELE on 2017/5/14.
 */
$.isBlank = function(obj){
    return(!obj || $.trim(obj) === "");
};

var toast_app = new Vue({
    el: '#my-alert',
    data: {
        message: 'Hello Vue!'
    }
});
function toast(msg){
    toast_app.message = msg ;
    $("#my-alert").modal('open');
}


function http_request(action, params) {
    var dict = {
        url: action,
        dataType: "json",
        beforeSend: function () {
            $("#my-modal-loading").modal('open');
            params.beforeSend && params.beforeSend()
        },success: function(result){
            if(result.code === 0){
                params.code_is_0 && params.code_is_0()
            }else{
                toast(result.msg);
                params.code_not_0 && params.code_not_0()
            }
            $("#my-modal-loading").modal('close');
        },
        error: function (e) {
            $("#my-modal-loading").modal('close');
            toast("登陆异常, HTTP Status: "+ e.status);
        }
    };
    if (params.data === undefined) {
        dict.type = "get";
    } else {
        dict.type = "post";
        dict.data = params.data;
    }
    $.ajax(dict);
}